package com.prospecthire.graphdb

import com.harmony.graph.Counter
import com.harmony.graph.Question
import com.harmony.graph.SubjectTag
import com.orientechnologies.orient.core.db.graph.OGraphDatabase

import com.orientechnologies.orient.core.record.impl.ODocument

import org.hypergraphdb.HGEnvironment
import org.hypergraphdb.HyperGraph
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

import org.hypergraphdb.HGHandle

import org.hypergraphdb.HGQuery
import org.hypergraphdb.HGValueLink
import org.hypergraphdb.algorithms.HGTraversal
import org.hypergraphdb.algorithms.HGBreadthFirstTraversal
import org.hypergraphdb.algorithms.DefaultALGenerator
import org.hypergraphdb.HGQuery.hg
import org.hypergraphdb.util.Pair
import org.hypergraphdb.algorithms.SimpleALGenerator

class HyperService {

    @Autowired
    MongoTemplate mongoTemplate

    def mongoCollectionFactoryService
    final static String DB_USER = "admin"
    final static String DB_PASSWORD = "admin"
    final static String ROOT_NODE = "ROOT"
    final static String SKILL_ID = "skillId"
    HyperGraph graph

    private String getGraphDBName(long customerId){
        return String.format("remote:localhost/graph%d",customerId)
    }

    class Relationships{
        public final  static  String HAS_SUBJECT = "HAS_CHILD_SUBJECT";
        public final  static  String HAS_QUESTION = "HAS_QUESTION";
    }

    @PostConstruct
    void init(){

        graph =  HGEnvironment.get("/usr/local/data");
     }

    @PreDestroy
    void destroy(){
        graph.close();
    }

    def createSkill(String companyName, String subjectTag){
        createSkill(companyName, subjectTag, null)
    }

    private long increaseCounter(String counterName){
        Query query = new Query(Criteria.where("name").is(counterName));
        Update update = new Update().inc("sequence", 1);
        Counter counter = mongoTemplate.findAndModify(query, update, Counter.class); // return old Counter object
        if (!counter){
            counter = new Counter()
            counter.name = counterName
            mongoTemplate.insert(counter)
            counter = mongoTemplate.findAndModify(query, update, Counter.class);
        }
        return counter.getSequence();
    }

    private long increaseNodeCounter(){
        return increaseCounter("nodeId")
    }

    private long increaseQuestionCounter(){
        return increaseCounter("questionId")
    }

    private void createLink(HGHandle startNode, HGHandle endNode, String linkName){
        SubjectLink subjectLink = new SubjectLink()
        subjectLink.name = linkName
        HGValueLink link = new HGValueLink(subjectLink, startNode, endNode);
        graph.add(link)
    }

    private void createQuestionLink(HGHandle skillNode, HGHandle questionNode){
        QuestionLink questionLink = new QuestionLink()
        HGValueLink link = new HGValueLink(questionLink, skillNode, questionNode);
        graph.add(link)
    }

    SubjectTag findSubjectInMongo(long companyId, String subjectTag){
        return mongoTemplate.findOne(new Query(Criteria.where("subjectName").is(subjectTag)), SubjectTag.class,
                mongoCollectionFactoryService.getSubjectCollName(companyId))
    }

    void createRootNode(long companyId){

        SubjectTag rootNode = findSubjectInMongo(companyId,ROOT_NODE)
        //Create subject in mongo if it does not exist already
        if (!rootNode){
            rootNode = new SubjectTag()
            rootNode.setSkillId(increaseCounter(SKILL_ID))
            rootNode.subjectName = ROOT_NODE
            mongoTemplate.insert(rootNode, mongoCollectionFactoryService.getSubjectCollName(companyId))
        }
        if (!findVertex(ROOT_NODE, 1)){
            graph.add(rootNode);
        }
    }

    /**
     *
     * @param companyName
     * @param subjectTag
     * @param parentTag
     */
    def createSkill(long companyId, String subjectTag,String parentTag){

        createRootNode(companyId)
        SubjectTag findSubject = findSubjectInMongo(companyId, subjectTag)
        SubjectTag parentSubject
        if (parentTag){
            parentSubject = findSubjectInMongo(companyId, subjectTag)
        }

        //Create subject in mongo if it does not exist already
        if (!findSubject){
            findSubject = new SubjectTag()
            findSubject.setSkillId(increaseCounter(SKILL_ID))
            findSubject.subjectName = subjectTag
            mongoTemplate.insert(findSubject, mongoCollectionFactoryService.getSubjectCollName(companyId))
        }

        try{
            HGHandle subjectNode = findVertex(subjectTag,companyId)
            SubjectTag existingSubject
            if(subjectNode){
                existingSubject = graph.get(subjectNode)
            }else{
                existingSubject = new SubjectTag()
                existingSubject.subjectName = subjectTag
                existingSubject.skillId = findSubject.skillId
                subjectNode = graph.add(existingSubject)
            }

            if (parentTag) {
                HGHandle parentNode = findVertex(parentTag, companyId)
                //database.createEdge(parentTag, subjectNode).save()
                createLink(parentNode, subjectNode,Relationships.HAS_SUBJECT)
            }else{
                if (!existingSubject.subjectName.equalsIgnoreCase("ROOT"))
                {
                    HGHandle rootNode = findVertex(ROOT_NODE,1)
                    //If no parent subject then attach it to root node
                    createLink(rootNode, subjectNode,Relationships.HAS_SUBJECT)
                }
            }

        } finally{

        }
        return findSubject
    }

    HGHandle findVertex(String subjectTag, long companyId){
        SubjectTag vertex = HGQuery.hg.getOne(graph, HGQuery.hg.and(HGQuery.hg.type(SubjectTag.class), HGQuery.hg.eq("subjectName", subjectTag)))
        return graph.getHandle(vertex)
    }

    HGHandle findVertex(long nodeId, long companyId){
        SubjectTag vertex = HGQuery.hg.getOne(graph, HGQuery.hg.and(HGQuery.hg.type(SubjectTag.class), HGQuery.hg.eq(SKILL_ID, nodeId)))
        return graph.getHandle(vertex)
    }

    HGHandle createNewSubject(String subjectTag,long nodeId, long companyId){

        SubjectTag subjectNode = new SubjectTag()
        subjectNode.subjectName = subjectTag
        subjectNode.skillId = nodeId
        HGHandle newNode
        try{
            subjectNode = findVertex(subjectTag, companyId)
            if (!subjectNode){
                newNode = graph.add(subjectNode)
            }
        }finally {

        }
        return newNode
    }

    def findLinksBetweenTags(OGraphDatabase database,ODocument firstTag, ODocument secondTag){


    }
    /**
     * For the current account lists all questions
     * @param company
     * @return
     */
    List<SubjectTag> getTopLevelSubjects(long companyId){
        //todo: This database has to be different for different accounts to facilitate mutli-tenent database
        List<SubjectTag> subjectTagList = new ArrayList<SubjectTag>()
        subjectTagList.add(graph.get(findVertex(ROOT_NODE, companyId)))
        return subjectTagList
    }

    List<SubjectTag> getSubjectsTillDepth(long companyId,long nodeId, int depth){
        //todo: This database has to be different for different accounts to facilitate mutli-tenent database
         List<SubjectTag> result = new ArrayList<SubjectTag>()
         HGHandle startNode = findVertex(nodeId,companyId)
         try{

             DefaultALGenerator algen = new DefaultALGenerator(graph,
                     hg.type(SubjectLink.class),
                     hg.type(SubjectTag.class),
                     false,
                     true,
                     false);
             SimpleALGenerator simpleGenerator = new SimpleALGenerator(graph)
             HGTraversal traversal = new HGBreadthFirstTraversal(startNode, algen,1);
             //SubjectTag currentArticle = startingArticle;
             while (traversal.hasNext())
             {
                 Pair<HGHandle, HGHandle> next = traversal.next();
                 SubjectTag nextArticle = graph.get(next.getSecond());
                 result.add(nextArticle)
             }

        }finally{

        }
        return result
    }
    /**
     * Add questions to database..DB has to be for the company from which user has logged in
     * @param questions
     */
    void addQuestions(List<Question> questions, long companyId){
        try{
            for (Question question : questions){
                //Question ID is globally unique
                question.setQuestionId(increaseQuestionCounter())
                mongoTemplate.insert(question, mongoCollectionFactoryService.getQuestionsCollName(companyId));
                //Save question in graph
                HGHandle questionHandle = graph.add(question)

                //For now assumption is that there is only one tag for one question
                for(String tag in question.tags){
                    HGHandle tagHandle = findVertex(tag,companyId)
                    if (tagHandle){
                        createQuestionLink(tagHandle, questionHandle)
                    }
                }
            }
        }finally {

        }
    }

    void validateTags(){

    }

    List<Question> getQuestions(String subjectTag, long companyId){
        //todo: make sure only owner of the questions should be able to see the questions
        List<Question> questions = new ArrayList<Question>()
        HGHandle startNode = findVertex(subjectTag, companyId)
        if (!startNode){
            log.error("Unable to find skill " + subjectTag)
            return questions
        }
        try {
            DefaultALGenerator algen = new DefaultALGenerator(graph,
                    hg.type(QuestionLink.class),
                    hg.type(Question.class),
                    false,
                    true,
                    false);
            SimpleALGenerator simpleGenerator = new SimpleALGenerator(graph)
            HGTraversal traversal = new HGBreadthFirstTraversal(startNode, algen,1);
            //SubjectTag currentArticle = startingArticle;
            while (traversal.hasNext())
            {
                Pair<HGHandle, HGHandle> next = traversal.next();
                Question nextQuestion = graph.get(next.getSecond());
                questions.add(nextQuestion)
            }
        } finally {
            //db.close();
        }
        return questions
    }

    List<Question> getQuestions(long companyId,long subjectTag){
        //todo: make sure only owner of the questions should be able to see the questions
        List<Question> questions = new ArrayList<Question>()
        HGHandle startNode = findVertex(subjectTag, companyId)
        if (!startNode){
            log.error("Unable to find skill " + subjectTag)
            return questions
        }
        try {
            DefaultALGenerator algen = new DefaultALGenerator(graph,
                    hg.type(QuestionLink.class),
                    hg.type(Question.class),
                    false,
                    true,
                    false);
            SimpleALGenerator simpleGenerator = new SimpleALGenerator(graph)
            HGTraversal traversal = new HGBreadthFirstTraversal(startNode, algen,1);
            //SubjectTag currentArticle = startingArticle;
            while (traversal.hasNext())
            {
                Pair<HGHandle, HGHandle> next = traversal.next();
                Question nextQuestion = graph.get(next.getSecond());
                questions.add(nextQuestion)
            }
        } finally {
            //db.close();
        }
        return questions
    }

}

class SubjectLink{
    String name
}

class QuestionLink{
    String name = "HAS_QUESTION"
}

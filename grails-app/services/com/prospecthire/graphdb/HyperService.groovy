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
import org.hypergraphdb.algorithms.HGDepthFirstTraversal
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import org.hypergraphdb.HGLink
import org.hypergraphdb.HGPlainLink
import org.hypergraphdb.query.DFSCondition
import org.hypergraphdb.indexing.ByPartIndexer

class HyperService {

    @Autowired
    MongoTemplate mongoTemplate

    def mongoCollectionFactoryService
    final static String DB_USER = "admin"
    final static String DB_PASSWORD = "admin"
    final static String ROOT_NODE = "ROOT"
    final static String SKILL_ID = "skillId"
    def concurrentHashMap
//    HyperGraph graph

    private HyperGraph getGraph(long customerId){
        String graphUrl = String.format("/usr/local/harmony/data/%d",customerId)
        File file = new File(graphUrl)
        file.mkdirs()
        if (!concurrentHashMap.containsKey(customerId)){
            HyperGraph hyperGraph = HGEnvironment.get(graphUrl)
            concurrentHashMap.put(customerId as Long, hyperGraph)
        }
        return concurrentHashMap.get(customerId)
    }

    class Relationships{
        public final  static  String HAS_SUBJECT = "HAS_CHILD_SUBJECT";
        public final  static  String HAS_QUESTION = "HAS_QUESTION";
    }

    @PostConstruct
    void init(){
        concurrentHashMap = new ConcurrentHashMap<Long,HyperGraph>()
        //graph =  HGEnvironment.get("/usr/local/data");
     }

    @PreDestroy
    void destroy(){
        //graph.close();
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

    private void createLink(long customerId, HGHandle startNode, HGHandle endNode, String linkName){
        SubjectLink subjectLink = new SubjectLink()
        subjectLink.name = linkName
        HGValueLink link = new HGValueLink(subjectLink, startNode, endNode);
        getGraph(customerId).add(link)
    }

    private void createQuestionLink(long customerId, HGHandle skillNode, HGHandle questionNode){
        QuestionLink questionLink = new QuestionLink()
        HGValueLink link = new HGValueLink(questionLink, skillNode, questionNode);
        getGraph(customerId).add(link)
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
            getGraph(companyId).add(rootNode);
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
                existingSubject = getGraph(companyId).get(subjectNode)
            }else{
                existingSubject = new SubjectTag()
                existingSubject.subjectName = subjectTag
                existingSubject.skillId = findSubject.skillId
                subjectNode = getGraph(companyId).add(existingSubject)
            }

            if (parentTag) {
                HGHandle parentNode = findVertex(parentTag, companyId)
                //database.createEdge(parentTag, subjectNode).save()
                createLink(companyId,parentNode, subjectNode,Relationships.HAS_SUBJECT)
            }else{
                if (!existingSubject.subjectName.equalsIgnoreCase("ROOT"))
                {
                    HGHandle rootNode = findVertex(ROOT_NODE,1)
                    //If no parent subject then attach it to root node
                    createLink(companyId, rootNode, subjectNode,Relationships.HAS_SUBJECT)
                }
            }

        } finally{

        }
        return findSubject
    }

    HGHandle findVertex(String subjectTag, long companyId){
        SubjectTag vertex = HGQuery.hg.getOne(getGraph(companyId), HGQuery.hg.and(HGQuery.hg.type(SubjectTag.class), HGQuery.hg.eq("subjectName", subjectTag)))
        return getGraph(companyId).getHandle(vertex)
    }

    HGHandle findVertex(long nodeId, long companyId){
        SubjectTag vertex = HGQuery.hg.getOne(getGraph(companyId), HGQuery.hg.and(HGQuery.hg.type(SubjectTag.class), HGQuery.hg.eq(SKILL_ID, nodeId)))
        return getGraph(companyId).getHandle(vertex)
    }

    HGHandle createNewSubject(String subjectTag,long nodeId, long companyId){

        SubjectTag subjectNode = new SubjectTag()
        subjectNode.subjectName = subjectTag
        subjectNode.skillId = nodeId
        HGHandle newNode
        try{
            subjectNode = findVertex(subjectTag, companyId)
            if (!subjectNode){
                newNode = getGraph(companyId).add(subjectNode)
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
        subjectTagList.add(getGraph(companyId).get(findVertex(ROOT_NODE, companyId)))
        return subjectTagList
    }

    List<SubjectTag> getAllSubjects(long companyId){
        HGHandle rootHandle = findVertex(ROOT_NODE, companyId)
        SubjectTag subjectTag1 = getGraph(companyId).get(rootHandle)
        return getSubjectsTillDepth(companyId, subjectTag1.getSkillId(), 10)
    }

    List<SubjectTag> getSubjectsTillDepth(long companyId,long nodeId, int depth){
        //todo: This database has to be different for different accounts to facilitate mutli-tenent database
         List<SubjectTag> result = new ArrayList<SubjectTag>()
         HGHandle startNode = findVertex(nodeId,companyId)
         try{

             DefaultALGenerator algen = new DefaultALGenerator(getGraph(companyId),
                     hg.type(SubjectLink.class),
                     hg.type(SubjectTag.class),
                     false,
                     true,
                     false);
             SimpleALGenerator simpleGenerator = new SimpleALGenerator(getGraph(companyId))
             HGTraversal traversal = new HGBreadthFirstTraversal(startNode, algen,depth);
             //SubjectTag currentArticle = startingArticle;
             while (traversal.hasNext())
             {
                 Pair<HGHandle, HGHandle> next = traversal.next();
                 SubjectTag nextArticle = getGraph(companyId).get(next.getSecond());
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
                HyperGraph graph = getGraph(companyId)
                //Save question in graph
                HGHandle questionHandle = graph.add(question)
                //graph.getIndexManager().register(new ByPartIndexer(questionHand))
                //For now assumption is that there is only one subject for one question
                //There can be many tags which will be used for Questions finding
                for(String tag in question.tags){
                    HGHandle tagHandle = findVertex(tag,companyId)
                    if (tagHandle){
                        createQuestionLink(companyId, tagHandle, questionHandle)
                    }
                }
            }
        }finally {

        }
    }

    void validateTags(){

    }

    List<Question> getQuestions(Map<String,Long> subjectTag, long companyId){
        Set<String> subjectMap = new HashSet<String>()
        subjectMap.addAll(subjectTag)
        List<Question> questions = new ArrayList<Question>()

        org.hypergraphdb.query.Or orQuery
        for (String subject : subjectTag.keySet()){
            HGHandle startNode = findVertex(subject, companyId)
            org.hypergraphdb.query.DFSCondition dfsCondition = new DFSCondition(startNode)
            orQuery = dfsCondition
        }

        for (String subject : subjectTag){
            HGHandle startNode = findVertex(subject, companyId)

            if (!startNode){
                log.error("Unable to find skill " + subjectTag)
                return questions
            }
            try {
                HyperGraph graph = getGraph(companyId)
                DefaultALGenerator algen = new DefaultALGenerator(graph,
                        null,
                        hg.not(hg.or(hg.dfs())),
                        false,
                        true,
                        false);
                SimpleALGenerator simpleGenerator = new SimpleALGenerator(graph)
                HGTraversal traversal = new HGDepthFirstTraversal(startNode, algen);

                //SubjectTag currentArticle = startingArticle;
                while (traversal.hasNext())
                {
                    Pair<HGHandle, HGHandle> next = traversal.next();
                    def a = graph.get(next.first)
                    HGLink hgLink;
                    def b = graph.get(next.second)
                    if (graph.get(next.getFirst()) instanceof SubjectLink){
                        SubjectTag findSubject = graph.get(next.getSecond())
                        String findSubjectName = findSubject.subjectName
                        if (subjectMap.contains(findSubjectName)){

                        }
                    }
                    if (graph.get(next.getFirst()) instanceof QuestionLink){

                        HGLink l = (HGLink)graph.get(next.getFirst());

                        Question nextQuestion = graph.get(next.getSecond());
                        questions.add(nextQuestion)
                    }
                }
            } finally {
                //db.close();
            }
        }
        return questions

    }

    List<Question> getQuestions(String subjectTag, long companyId){

        List<Question> questions = new ArrayList<Question>()
        HGHandle startNode = findVertex(subjectTag, companyId)
        if (!startNode){
            log.error("Unable to find skill " + subjectTag)
            return questions
        }
        try {
            HyperGraph graph = getGraph(companyId)
            DefaultALGenerator algen = new DefaultALGenerator(graph,
                    hg.type(QuestionLink.class),
                    hg.type(Question.class),
                    false,
                    true,
                    false);
            SimpleALGenerator simpleGenerator = new SimpleALGenerator(graph)
            HGTraversal traversal = new HGDepthFirstTraversal(startNode, algen);
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

        List<Question> questions = new ArrayList<Question>()
        HGHandle startNode = findVertex(subjectTag, companyId)
        HyperGraph graph = getGraph(companyId)
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


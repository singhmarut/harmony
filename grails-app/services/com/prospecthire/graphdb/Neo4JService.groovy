package com.prospecthire.graphdb

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.RelationshipType

import org.neo4j.graphdb.Node
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Update
import com.harmony.graph.Counter
import com.harmony.graph.SubjectTag
import com.orientechnologies.orient.core.db.graph.OGraphDatabase
import com.orientechnologies.orient.core.record.impl.ODocument
import com.harmony.graph.Question
import org.neo4j.graphdb.traversal.TraversalDescription
import org.neo4j.kernel.Traversal

import org.neo4j.graphdb.Direction
import org.neo4j.graphdb.traversal.Evaluators
import org.neo4j.graphdb.Path

import com.harmony.graph.GraphFactory

class Neo4JService {

    def mongoTemplate

    GraphDatabaseService graphDatabaseService

    def mongoCollectionFactoryService
    final static String DB_USER = "admin"
    final static String DB_PASSWORD = "admin"
    final static String ROOT_NODE = "ROOT"
    final static String SKILL_ID = "skillId"


    private GraphDatabaseService getGraph(long customerId){
        customerId = 10
        String graphUrl = String.format("/usr/local/harmony/data/%d",customerId)
        return GraphFactory.INSTANCE.getGraph(customerId)
    }

    def createSkill(String companyName, String subjectTag){
        createSkill(companyName, subjectTag, null)
    }

    private static enum RelTypes implements RelationshipType
    {
        HAS_CHILD_SUBJECT,
        HAS_QUESTION
    }

    private long increaseQuestionCounter(){
        return increaseCounter("questionId")
    }

    private void createLink(long customerId, Node startNode, Node endNode, RelTypes relTypes){
        startNode.createRelationshipTo(endNode, relTypes)
    }

    private void createQuestionLink(long customerId, Node skillNode, Node questionNode){
        skillNode.createRelationshipTo(questionNode, RelTypes.HAS_QUESTION)
    }

    SubjectTag findSubjectInMongo(long companyId, String subjectTag){
        return mongoTemplate.findOne(new Query(Criteria.where("subjectName").is(subjectTag)), SubjectTag.class,
                mongoCollectionFactoryService.getSubjectCollName(companyId))
    }

    SubjectTag findSubjectInMongo(long companyId, long skillId){
        return mongoTemplate.findOne(new Query(Criteria.where("skillId").is(skillId)), SubjectTag.class,
                mongoCollectionFactoryService.getSubjectCollName(companyId))
    }

    private SubjectTag getSubjectFromNode(long companyId, Node node){
        SubjectTag subjectTag = new SubjectTag()
        findSubjectInMongo(companyId, node.getProperty("subjectName"))
    }

    private void updateNodeProps(SubjectTag subjectTag,Node node){
        node.setProperty("subjectName", subjectTag.subjectName)
        node.setProperty("skillId", subjectTag.skillId)
    }

    def createNewSubject(long companyId, String subjectName){
        SubjectTag mongoNode = findSubjectInMongo(companyId,subjectName)
        //Create subject in mongo if it does not exist already
        if (!mongoNode){
            mongoNode = new SubjectTag()
            mongoNode.setSkillId(increaseCounter(SKILL_ID))
            mongoNode.subjectName = subjectName
            mongoTemplate.insert(mongoNode, mongoCollectionFactoryService.getSubjectCollName(companyId))
        }
        Node graphNode  = findVertex(subjectName, companyId)
        if (graphNode == null){
            graphNode = graphDatabaseService.createNode()
            graphDatabaseService.index().forNodes("subjects").putIfAbsent(graphNode, "subjectName",subjectName)
            updateNodeProps(mongoNode, graphNode)
        }

        return graphNode

    }

    def createRootNode(long companyId){
        createNewSubject(companyId, ROOT_NODE)
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
            Node subjectNode = findVertex(subjectTag,companyId)
            SubjectTag existingSubject
            if(subjectNode){
//                existingSubject = getGraph(companyId).get(subjectNode)
            }else{
                  subjectNode = createNewSubject(companyId, subjectTag)
            }

            if (parentTag) {
                Node parentNode = findVertex(parentTag, companyId)
                //database.createEdge(parentTag, subjectNode).save()
                createLink(companyId,parentNode, subjectNode,RelTypes.HAS_CHILD_SUBJECT)
            }else{
                if (!subjectTag.equalsIgnoreCase("ROOT"))
                {
                    Node rootNode = findVertex(ROOT_NODE,1)
                    //If no parent subject then attach it to root node
                    createLink(companyId, rootNode, subjectNode,RelTypes.HAS_CHILD_SUBJECT)
                }
            }

        } finally{

        }
        return findSubject
    }

    Node findVertex(String subjectTag, long companyId){
        Node node = graphDatabaseService.index().forNodes("subjects").get("subjectName", subjectTag).getSingle()
        return node;
    }

    Node findVertex(long nodeId, long companyId){
        return graphDatabaseService.getNodeById(nodeId)
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
        Node rootNode = findVertex(ROOT_NODE, companyId)
        SubjectTag subjectTag = getSubjectFromNode(companyId, rootNode)
        subjectTagList.add(subjectTag)
        return subjectTagList
    }

    List<SubjectTag> getAllSubjects(long companyId){
        Node rootHandle = findVertex(ROOT_NODE, companyId)
        SubjectTag subjectTag1 = getGraph(companyId).get(rootHandle)
        return getSubjectsTillDepth(companyId, subjectTag1.getSkillId(), 10)
    }

    List<SubjectTag> getSubjectsTillDepth(long companyId,long nodeId, int depth){
        //todo: This database has to be different for different accounts to facilitate mutli-tenent database
        List<SubjectTag> result = new ArrayList<SubjectTag>()
        SubjectTag subjectTag = findSubjectInMongo(companyId, nodeId)
        Node startNode = findVertex(subjectTag.subjectName,companyId)
        try{

            TraversalDescription td = Traversal.description()
                    .breadthFirst()
                    .relationships( RelTypes.HAS_CHILD_SUBJECT, Direction.OUTGOING )
                    .evaluator( Evaluators.excludeStartPosition());

            for (Path hackerPath : td.traverse(startNode) )
            {
                SubjectTag nextArticle = getSubjectFromNode(companyId, hackerPath.endNode())
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
                Node questionNode = getGraph(companyId).createNode()
                questionNode.setProperty("questionId", question.questionId)
                //For now assumption is that there is only one subject for one question
                //There can be many tags which will be used for Questions finding
                for(String tag in question.tags){
                    Node tagHandle = findVertex(tag,companyId)
                    if (tagHandle){
                        createQuestionLink(companyId, tagHandle, questionNode)
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

//        org.hypergraphdb.query.Or orQuery
//        for (String subject : subjectTag.keySet()){
//            HGHandle startNode = findVertex(subject, companyId)
//            org.hypergraphdb.query.DFSCondition dfsCondition = new DFSCondition(startNode)
//            orQuery = dfsCondition
//        }
//
//        for (String subject : subjectTag){
//            HGHandle startNode = findVertex(subject, companyId)
//
//            if (!startNode){
//                log.error("Unable to find skill " + subjectTag)
//                return questions
//            }
//            try {
//                HyperGraph graph = getGraph(companyId)
//                DefaultALGenerator algen = new DefaultALGenerator(graph,
//                        null,
//                        HGQuery.hg.not(HGQuery.hg.or(HGQuery.hg.dfs())),
//                        false,
//                        true,
//                        false);
//                SimpleALGenerator simpleGenerator = new SimpleALGenerator(graph)
//                HGTraversal traversal = new HGDepthFirstTraversal(startNode, algen);
//
//                //SubjectTag currentArticle = startingArticle;
//                while (traversal.hasNext())
//                {
//                    Pair<HGHandle, HGHandle> next = traversal.next();
//                    def a = graph.get(next.first)
//                    HGLink hgLink;
//                    def b = graph.get(next.second)
//                    if (graph.get(next.getFirst()) instanceof SubjectLink){
//                        SubjectTag findSubject = graph.get(next.getSecond())
//                        String findSubjectName = findSubject.subjectName
//                        if (subjectMap.contains(findSubjectName)){
//
//                        }
//                    }
//                    if (graph.get(next.getFirst()) instanceof QuestionLink){
//
//                        HGLink l = (HGLink)graph.get(next.getFirst());
//
//                        Question nextQuestion = graph.get(next.getSecond());
//                        questions.add(nextQuestion)
//                    }
//                }
//            } finally {
//                //db.close();
//            }
//        }
        return questions

    }

    List<Question> getQuestions(String subjectTag, long companyId){

        List<Question> questions = new ArrayList<Question>()
//        HGHandle startNode = findVertex(subjectTag, companyId)
//        if (!startNode){
//            log.error("Unable to find skill " + subjectTag)
//            return questions
//        }
//        try {
//            HyperGraph graph = getGraph(companyId)
//            DefaultALGenerator algen = new DefaultALGenerator(graph,
//                    HGQuery.hg.type(QuestionLink.class),
//                    HGQuery.hg.type(Question.class),
//                    false,
//                    true,
//                    false);
//            SimpleALGenerator simpleGenerator = new SimpleALGenerator(graph)
//            HGTraversal traversal = new HGDepthFirstTraversal(startNode, algen);
//            //SubjectTag currentArticle = startingArticle;
//            while (traversal.hasNext())
//            {
//                Pair<HGHandle, HGHandle> next = traversal.next();
//                Question nextQuestion = graph.get(next.getSecond());
//                questions.add(nextQuestion)
//            }
//        } finally {
//            //db.close();
//        }
        return questions
    }

    List<Question> getQuestions(long companyId,long subjectTag){

        List<Question> questions = new ArrayList<Question>()
        Node startNode = findVertex(subjectTag, companyId)
        //HyperGraph graph = getGraph(companyId)
        if (!startNode){
            log.error("Unable to find skill " + subjectTag)
            return questions
        }
        try {
            TraversalDescription td = Traversal.description()
                    .breadthFirst()
                    .relationships( RelTypes.HAS_CHILD_SUBJECT, Direction.OUTGOING )
                    .evaluator( Evaluators.excludeStartPosition());

            for (Path hackerPath : td.traverse(startNode) )
            {
                SubjectTag nextArticle = getSubjectFromNode(hackerPath.endNode())
                questions.add(nextArticle)
            }
        } finally {
            //db.close();
        }
        return questions
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

    def serviceMethod() {

    }
}

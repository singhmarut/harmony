package com.prospecthire.graphdb

import com.orientechnologies.orient.object.db.OObjectDatabaseTx
import com.orientechnologies.orient.object.db.OObjectDatabasePool
import javax.annotation.PostConstruct
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery
import com.harmony.graph.SubjectTag
import com.harmony.graph.Question

import com.orientechnologies.orient.core.metadata.schema.OClass
import com.orientechnologies.orient.core.metadata.schema.OType
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import com.orientechnologies.orient.core.db.graph.OGraphDatabasePool
import com.orientechnologies.orient.core.db.graph.OGraphDatabase
import com.orientechnologies.orient.core.record.impl.ODocument
import org.springframework.data.mongodb.core.query.Update
import com.harmony.graph.Counter

import com.orientechnologies.orient.graph.gremlin.OGremlinHelper
import com.orientechnologies.orient.graph.gremlin.OCommandGremlin

import javax.annotation.PreDestroy

class OrientDBService {

    @Autowired
    MongoTemplate mongoTemplate

    def mongoCollectionFactoryService
    final static String DB_USER = "admin"
    final static String DB_PASSWORD = "admin"

    private String getGraphDBName(long customerId){
        return String.format("remote:localhost/graph%d",customerId)
    }

    class Relationships{
        public final  static  String HAS_SUBJECT = "HAS_CHILD_SUBJECT";
        public final  static  String HAS_QUESTION = "HAS_QUESTION";
    }

    @PostConstruct
    void init(){
        OGremlinHelper.global().create()
    }

    @PreDestroy
    void destroy(){
        //graph.close();
    }


    /**
     * registers schema
     */
    @PostConstruct
    void register(){

        OObjectDatabaseTx db = OObjectDatabasePool.global().acquire("remote:localhost/test1","admin", "admin");
        try{
            //db.getMetadata().
            db.getEntityManager().registerEntityClass(com.harmony.graph.Question.class);
            db.getEntityManager().registerEntityClass(com.harmony.graph.SubjectTag.class);
            db.getEntityManager().registerEntityClass(com.harmony.graph.QuestionTag.class);
            OClass questionClass = db.getMetadata().getSchema().getClass(Question.class);
            if(!questionClass.existsProperty("text")){
                questionClass.createProperty("text", OType.STRING)
            }
            if(!questionClass.existsProperty("questionType")){
                questionClass.createProperty("questionType", OType.STRING)
            }
            if(!questionClass.existsProperty("userId")){
                questionClass.createProperty("userId", OType.INTEGER)
            }
            if(!questionClass.existsProperty("tags")){
                questionClass.createProperty("tags", OType.EMBEDDEDLIST, OType.STRING).createIndex(OClass.INDEX_TYPE.NOTUNIQUE)
            }
        }catch (Exception ex){
            log.error(ex)
        } finally{
            db.close()
        }
    }

    def createSkill(String companyName, String subjectTag){
        createSkill(companyName, subjectTag, null)
    }

    private long increaseCounter(String counterName){
        Query query = new Query(Criteria.where("name").is(counterName));
        Update update = new Update().inc("sequence", 1);
        Counter counter = mongoTemplate.findAndModify(query, update, Counter.class); // return old Counter object
        return counter.getSequence();
    }

    private long increaseNodeCounter(){
        Query query = new Query(Criteria.where("name").is("nodeId"));
        Update update = new Update().inc("sequence", 1);
        Counter counter = mongoTemplate.findAndModify(query, update, Counter.class); // return old Counter object
        return counter.getSequence();
    }

    private long increaseQuestionCounter(){
        Query query = new Query(Criteria.where("name").is("SubjectTag"));
        Update update = new Update().inc("sequence", 1);
        Counter counter = mongoTemplate.findAndModify(query, update, Counter.class); // return old Counter object
        return counter.getSequence();
    }

    private void createLink(OGraphDatabase database, ODocument startNode, ODocument endNode, String linkName){
       // ODocument parentNode = findVertex(parentTag, companyId)
        //database.createEdge(parentTag, subjectNode).save()
        database.createEdge(startNode, endNode).field(OGraphDatabase.LABEL, linkName).save()
    }

    /**
     *
     * @param companyName
     * @param subjectTag
     * @param parentTag
     */
    def createSkill(long companyId, String subjectTag,String parentTag){

        SubjectTag findSubject = mongoTemplate.findOne(new Query(Criteria.where("subjectName").is(subjectTag)), SubjectTag.class,
                mongoCollectionFactoryService.getSubjectCollName(companyId))
        SubjectTag parentSubject
        if (parentTag){
            parentSubject = mongoTemplate.findOne(new Query(Criteria.where("parentTag").is(subjectTag)), SubjectTag.class,
                    mongoCollectionFactoryService.getSubjectCollName(companyId))
        }

        //Create subject in mongo if it does not exist already
        if (!findSubject){
            findSubject = new SubjectTag()
            findSubject.setSkillId(increaseCounter("nodeId"))
            findSubject.subjectName = subjectTag
            mongoTemplate.insert(findSubject, mongoCollectionFactoryService.getSubjectCollName(companyId))
        }
        String graphDBUrl = getGraphDBName(companyId);
        OGraphDatabase database = OGraphDatabasePool.global().acquire(graphDBUrl, "admin", "admin");

        try{
            if (!database.exists()){
                database.create()
            }

            if (!database.getVertexType("SubjectNode")){
                OClass subjectClass = database.createVertexType("SubjectNode");
                if(!database.getVertexType("SubjectTag")){
                    database.createVertexType("SubjectTag", subjectClass );
                }
            }

            if (!database.getVertexType("QuestionNode")){
                OClass subjectClass = database.createVertexType("QuestionNode");
//                if(!database.getVertexType("SubjectTag")){
//                    database.createVertexType("SubjectTag", subjectClass );
//                }
            }

            ODocument rootNode = database.getRoot("graph");
            if (rootNode == null){
                rootNode = database.createVertex("SubjectTag").field("id", 0).field("subjectName", subjectTag).save();
                database.setRoot("graph", rootNode);
            }
            ODocument subjectNode = createNewSubject(subjectTag, findSubject.getSkillId(), companyId)
            if (parentTag) {

                ODocument parentNode = findVertex(parentTag, companyId)
                //database.createEdge(parentTag, subjectNode).save()
                database.createEdge(parentNode, subjectNode).field(OGraphDatabase.LABEL, Relationships.HAS_SUBJECT).save()
            }else{
                if (!subjectNode.field("subjectName").toString().equalsIgnoreCase(rootNode.field("subjectName").toString())){
                    //If no parent subject then attach it to root node
                    database.createEdge(rootNode, subjectNode).save()
                }
            }

        } finally{
            database.close();
        }
        return findSubject
    }

    ODocument findVertex(String subjectTag, long companyId){
        String graphDBUrl = getGraphDBName(companyId);
        OGraphDatabase database = OGraphDatabasePool.global().acquire(graphDBUrl, "admin", "admin");
        ODocument vertex
        try {
            String sql = String.format("SELECT FROM SubjectNode WHERE subjectName = '%s'",subjectTag)
            List<ODocument> resultset = database.query(new OSQLSynchQuery<ODocument>(sql))
            if(resultset.size() > 0){
                vertex = resultset.get(0)
            }
        }finally {
            database.close()
        }

        return vertex
    }

    ODocument createNewSubject(String subjectTag,long nodeId, long companyId){
        String graphDBUrl = getGraphDBName(companyId);
        OGraphDatabase database = OGraphDatabasePool.global().acquire(graphDBUrl, "admin", "admin");
        ODocument subjectNode
        try{
        subjectNode = findVertex(subjectTag, companyId)
        if (!subjectNode){
            subjectNode = database.createVertex("SubjectTag").field("subjectName", subjectTag).field("id", nodeId).save()
        }
        }finally {
            database.close()
        }
        return subjectNode
    }

    def findLinksBetweenTags(OGraphDatabase database,ODocument firstTag, ODocument secondTag){
        String sql = String.format("SELECT shortestpath(%s,%s)",firstTag.getIdentity(), secondTag.getIdentity())
        List<ODocument> resultset = database.query(new OSQLSynchQuery<ODocument>(sql))

        database.command(new OCommandGremlin("g.V[0..10]")).execute();

    }

    ODocument createNewQuestion(OGraphDatabase database,Question question,long nodeId, long companyId){
        String graphDBUrl = getGraphDBName(companyId);
        //OGraphDatabase database = OGraphDatabasePool.global().acquire(graphDBUrl, "admin", "admin");
        ODocument questionNode
        try{
            //OClass subjectClass = database.createVertexType("QuestionNode");
            questionNode = database.createVertex("QuestionNode").
                    field("id", nodeId).field("questionId", question.getQuestionId()).field("difficulty", question.getDifficultyLevel()).save()

            //Question has been saved now create a link between skill and question
            for (String tag in question.getTags()){
                ODocument subjectNode = findVertex(tag, companyId)
                //Only valid subjects to be considered
                if (subjectNode){
                    createLink(database, subjectNode, questionNode, Relationships.HAS_QUESTION)
                }
            }

        }finally {
            //database.close()
        }
        return questionNode
    }


    /**
     * For the current account lists all questions
     * @param company
     * @return
     */
    List<SubjectTag> getTopLevelSubjects(long companyId){
        //todo: This database has to be different for different accounts to facilitate mutli-tenent database
        List<SubjectTag> result = new ArrayList<SubjectTag>()

        String graphDBUrl = getGraphDBName(companyId);
        OGraphDatabase database = OGraphDatabasePool.global().acquire(graphDBUrl, "admin", "admin");
        try{
            String sql = String.format("select V from SubjectTag where subjectName = 'ROOT' ")
            //database.command(new OCommandSQL('select from V' ))
            List<ODocument> resultset = database.query(new OSQLSynchQuery<ODocument>(sql))
            ODocument oDocument = database.getRoot("graph")
            //for (ODocument oDocument in resultset)
            //{
                SubjectTag subjectTag = new SubjectTag()
                subjectTag.skillId = oDocument.field("id")
                subjectTag.subjectName =  oDocument.field("subjectName")
                result.add(subjectTag)
            //}
        }finally {
            database.close()
        }
        return result
    }

    List<SubjectTag> getSubjectsTillDepth(long companyId,long nodeId, int depth){
        //todo: This database has to be different for different accounts to facilitate mutli-tenent database
        List<SubjectTag> result = new ArrayList<SubjectTag>()

        String graphDBUrl = getGraphDBName(companyId);
        OGraphDatabase database = OGraphDatabasePool.global().acquire(graphDBUrl, "admin", "admin");
        try{
            String sql = String
                    .format("""select from (traverse V.out, E.in from (select from SubjectTag where id = %d) ) where \$depth =1 """,nodeId)
            List<ODocument> resultset = database.query(new OSQLSynchQuery<ODocument>(sql))

            for (ODocument oDocument in resultset){
                SubjectTag subjectTag = new SubjectTag()
                ODocument subjectDocument = oDocument.field("in")
                subjectTag.skillId = subjectDocument.field("id")
                subjectTag.subjectName =  subjectDocument.field("subjectName")
                result.add(subjectTag)
            }
        }finally {
            database.close()
        }
        return result
    }
    /**
     * Add questions to database..DB has to be for the company from which user has logged in
     * @param questions
     */
    void addQuestions(List<Question> questions, long companyId){
        String graphDBUrl = getGraphDBName(companyId);
        OGraphDatabase database = OGraphDatabasePool.global().acquire(graphDBUrl, "admin", "admin");
        try{
            for (Question question : questions){
                //Question ID is globally unique
                question.setQuestionId(increaseCounter("questionId"))
                long nodeID = increaseCounter("nodeId")
                mongoTemplate.insert(question, Question.class, mongoCollectionFactoryService.getQuestionsCollName(companyId));
            }
        }finally {
            database.close();
        }
    }

    List<Question> getQuestions(String subjectTag){
        //todo: make sure only owner of the questions should be able to see the questions
        OObjectDatabaseTx db = OObjectDatabasePool.global().acquire("remote:localhost/test1", "admin", "admin");
        List<Question> questions = new ArrayList<Question>()
        try {
            String query = String.format("select * from Question where tags in ['%s']",subjectTag)
            //Working in full schema mode it is must to store result of save otherwise on further saving a new object will be createdresult = db.query(
            questions = db.query(new OSQLSynchQuery<SubjectTag>(query));
        } finally {
            db.close();
        }
        return questions
    }

}

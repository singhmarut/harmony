package com.prospecthire.graphdb

import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Update
import com.harmony.graph.Counter

class MongoCollectionFactoryService {

    def mongoTemplate

    def serviceMethod() {

    }

    public String getSubjectCollName(long customerId){
        return "subjects" + "." + Long.toString(customerId)
    }

    public String getQuestionsCollName(long customerId){
        return "questions" + "." + Long.toString(customerId)
    }

    public String getQuestionPaperCollName(long customerId){
        return "questionPaper" + "." + Long.toString(customerId)
    }

    public String getAnswerCollName(long customerId){
        return "answerSheets"
    }

    public String getAnswerCollName(){
        return "answerSheets"
    }

    /**
     * For keys there will be only one table as it will be used to identify the test
     * @param customerId
     * @return
     */
    public String getTestKeyCollName(){
        return "testAuthKeys"
    }

    public long increaseCounter(String counterName){
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

    public long increaseQuestionPaperCounter(){
        final String counterName = "questionPaperId"
        return increaseCounter(counterName);
    }
}

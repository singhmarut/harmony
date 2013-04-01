package com.prospecthire.graphdb

class MongoCollectionFactoryService {

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

}

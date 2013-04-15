package com.harmony.questionPaper;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 31/03/13
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Answer sheet of candidate
 */
@Document
//@CompoundIndexes({
//        @CompoundIndex(name = "answer_idx", def = "{'lastName': 1, 'age': -1}")
//})
public class AnswerSheet {

    public List<QuestionResponse> candidateAnswers;
    TestStatus testStatus = TestStatus.NOT_STARTED;

    ObjectId _id = new ObjectId();

    /**
     * Authentication Key
     */
    @Indexed(unique= true)
    String authKey;

    Long candidateId;
    Long questionPaperId;
    Long companyId;
    /**
     * Flag to find if an answer sheet has already been scored..
     */
    boolean evaluated = false;

    public List<QuestionResponse> getCandidateAnswers() {
        return candidateAnswers;
    }

    public void setCandidateAnswers(List<QuestionResponse> candidateAnswers) {
        this.candidateAnswers = candidateAnswers;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Long getQuestionPaperId() {
        return questionPaperId;
    }

    public void setQuestionPaperId(Long questionPaperId) {
        this.questionPaperId = questionPaperId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public TestStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(TestStatus testStatus) {
        this.testStatus = testStatus;
    }

    public Object get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}


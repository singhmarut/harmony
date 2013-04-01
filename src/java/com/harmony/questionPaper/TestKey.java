package com.harmony.questionPaper;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 31/03/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
@Document
public class TestKey {

    @Indexed(unique = true)
    String authKey;

    long customerId;
    long questionPaperId;
    //One key can be associated only with one candidate
    @Indexed(unique = true)
    long candidateId;

    boolean isExpired = false;

    /**
     * Question paper has to be transient as there are different collections for different customers
     */
    @Transient
    QuestionPaper questionPaper;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public QuestionPaper getQuestionPaper() {
        return questionPaper;
    }

    public void setQuestionPaper(QuestionPaper questionPaper) {
        this.questionPaper = questionPaper;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public long getQuestionPaperId() {
        return questionPaperId;
    }

    public void setQuestionPaperId(long questionPaperId) {
        this.questionPaperId = questionPaperId;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }
}

enum TestStatus{
    NOT_STARTED,
    IN_PROGRESS,
    FINISHED,
    TIME_OUT
}

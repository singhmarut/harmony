package com.harmony.questionPaper;

import org.bson.types.ObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 13/04/13
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class CandidateReport {
    ObjectId _id = new ObjectId();
    String authKey;
    long questionPaperId;
    long customerId;
    long candidateId;
    String htmlReport;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }

    public String getHtmlReport() {
        return htmlReport;
    }

    public void setHtmlReport(String htmlReport) {
        this.htmlReport = htmlReport;
    }

    public long getQuestionPaperId() {
        return questionPaperId;
    }

    public void setQuestionPaperId(long questionPaperId) {
        this.questionPaperId = questionPaperId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}

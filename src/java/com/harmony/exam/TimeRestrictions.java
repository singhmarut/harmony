package com.harmony.exam;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 01/04/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
public class TimeRestrictions {
    long questionPaperId;
    //List of time when test is to be organized
    List<String> timeLine;

    public long getQuestionPaperId() {
        return questionPaperId;
    }

    public void setQuestionPaperId(long questionPaperId) {
        this.questionPaperId = questionPaperId;
    }

    public List<String> getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(List<String> timeLine) {
        this.timeLine = timeLine;
    }
}

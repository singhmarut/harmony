package com.harmony.questionPaper;

import com.harmony.questionPaper.Section;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
class QuestionPaper{

    String instruction;
    boolean active = false;
    //Checks whether a test can be taken within certain date/time range or not
    boolean timeRestriction = false;
    long companyId;
    List<Section> sectionList;

    Long questionPaperId;
    String title;

    public QuestionPaper(String title,long questionPaperId,long companyId, List<Section> sectionList){
        this.title = title;
        this.companyId = companyId;
        this.questionPaperId = questionPaperId;
        this.sectionList = sectionList;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public Long getQuestionPaperId() {
        return questionPaperId;
    }

    public void setQuestionPaperId(Long questionPaperId) {
        this.questionPaperId = questionPaperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTimeRestriction() {
        return timeRestriction;
    }

    public void setTimeRestriction(boolean timeRestriction) {
        this.timeRestriction = timeRestriction;
    }
}

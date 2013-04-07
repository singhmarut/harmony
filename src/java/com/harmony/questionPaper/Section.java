package com.harmony.questionPaper;

import com.harmony.graph.Question;
import org.springframework.data.annotation.Transient;

import java.util.List;


class Section{

    Double duration;
    String sectionName;
    String instruction;
    String questionList;

    List<SectionSubject> sectionSubjects;

    int totalQuestionCount;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String questionList) {
        this.questionList = questionList;
    }

    public int getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public void setTotalQuestionCount(int totalQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
    }

    public List<SectionSubject> getSectionSubjects() {
        return sectionSubjects;
    }

    public void setSectionSubjects(List<SectionSubject> subjects) {
        this.sectionSubjects = subjects;
    }
}

class SectionSubject{

    //List of questionsIds will be populated when test is created
    //These question Ids can be from master list as well as from company database
    List<Long> questionsIds;

    @Transient
    List<Question> questions;

    long subjectTagId;

    //Difficulty Level of questionsIds 1 - 10
    int difficultyLevel;
    //Number of questionsIds to be shown during test
    int questionCount;

    public List<Long> getQuestionsIds() {
        return questionsIds;
    }

    public void setQuestionsIds(List<Long> questionsIds) {
        this.questionsIds = questionsIds;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public long getSubjectTagId() {
        return subjectTagId;
    }

    public void setSubjectTagId(long subjectTagId) {
        this.subjectTagId = subjectTagId;
    }
}

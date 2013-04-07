package com.harmony.graph;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Question{

    String text;
    String option1;
    String option2;
    String option3;
    String option4;
    String option5;

    //Order does not matter in choices
    //List of correct choices
    Boolean choice1;
    Boolean choice2;
    Boolean choice3;
    Boolean choice4;
    Boolean choice5;

    String questionType;
    String companyShortName;
    //Marks associated with question
    Integer marks;
    Integer difficultyLevel;

    long questionId;

    //Who uploaded this question
    Long userId;
    List<String> tags;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getChoice1() {
        return choice1;
    }

    public void setChoice1(Boolean choice1) {
        this.choice1 = choice1;
    }

    public Boolean getChoice2() {
        return choice2;
    }

    public void setChoice2(Boolean choice2) {
        this.choice2 = choice2;
    }

    public Boolean getChoice3() {
        return choice3;
    }

    public void setChoice3(Boolean choice3) {
        this.choice3 = choice3;
    }

    public Boolean getChoice4() {
        return choice4;
    }

    public void setChoice4(Boolean choice4) {
        this.choice4 = choice4;
    }

    public Boolean getChoice5() {
        return choice5;
    }

    public void setChoice5(Boolean choice5) {
        this.choice5 = choice5;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }


}

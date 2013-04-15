package com.harmony.grading;

import com.harmony.graph.Question;
import com.harmony.questionPaper.AnswerSheet;
import com.harmony.questionPaper.QuestionResponse;
import harmony.questions.QuestionService;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 08/04/13
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryScoringStrategy {
    public void score(long companyId, AnswerSheet answerSheet, Map<Long, List<Integer>> questionCorrectAnswerMap,QuestionService questionService){

        for (QuestionResponse questionResponse : answerSheet.candidateAnswers){
            List<Integer> choices = questionCorrectAnswerMap.get(questionResponse.getQuestionId());
            Question findQuestion = questionService.findQuestion(companyId, questionResponse.getQuestionId());
            questionResponse.setMarksScored(0);
            if(choices.size() == questionResponse.getAnswers().size()){
                for (Integer correctChoice : choices){
                    if (questionResponse.getAnswers().contains(correctChoice)){
                        //Even if one choice is wrong just give 0 as we will give marks only if all choices are correct
                        //marksScored = 0;
                        questionResponse.setMarksScored(findQuestion.getMarks());
                    }else{
                        questionResponse.setMarksScored(0);
                        break;
                    }
                }
            }
        }

    }
}

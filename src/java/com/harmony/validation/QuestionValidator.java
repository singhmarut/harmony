package com.harmony.validation;

import com.harmony.graph.Question;
import harmony.QuestionType;
import harmony.QuestionTypeEnum;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 29/03/13
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionValidator implements InputValidator<Question> {

    @Override
    public boolean isValid(Question question, List<String> errors) {
        if (question.getQuestionType().equalsIgnoreCase(QuestionTypeEnum.MCQ.toString())){

        }
        return true;
    }
}

package grading

import com.harmony.questionPaper.QuestionPaper
import com.harmony.questionPaper.AnswerSheet
import com.harmony.questionPaper.QuestionResponse
import com.harmony.graph.Question
import com.harmony.questionPaper.TestStatus

class ScoringService {

    def mongoTemplate
    def answerSheetService
    def questionPaperService

    def score(long companyId, long questionPaperId){
       //Find all the test papers for this company and question Paper who are finished
        List<AnswerSheet> answerSheets = answerSheetService.findFinishedAnswerSheets(questionPaperId, companyId)
        QuestionPaper questionPaper = questionPaperService.findById(companyId, questionPaperId)

        //todo: right now assumption is that each candidate is getting same questions
        List<Question> questions = questionPaperService.getAllQuestionsForQuestionPaper()
        Map<Long, List<String>> questionCorrectAnswerMap = new HashMap<Long, List<String>>()
        Map<Long,Integer> maxMarksMap = new HashMap<Long, Integer>()

        for (Question question in questions){
            questionCorrectAnswerMap.put(question.getQuestionId(), question.choices);
            maxMarksMap.put(question.questionId, question.marks)
        }

        for (AnswerSheet answerSheet in answerSheets){
            //List<Long> questionIds = new ArrayList<>
            for (QuestionResponse questionResponse in answerSheet.candidateAnswers){
               List<String> choices = questionCorrectAnswerMap.get(questionResponse.getQuestionId())
               Question findQuestion = maxMarksMap.get(questionResponse.questionId)
               questionResponse.marksScored = 0
               if(choices.size() == questionResponse.answers.size()){
                   for (String choice in choices){
                       if (questionResponse.answers.contains(choice)){
                           //Even if one choice is wrong just give 0
                           //marksScored = 0;
                           questionResponse.marksScored = findQuestion.marks;
                       }else{
                           questionResponse.marksScored = 0;
                           break;
                       }
                   }
               }
            }
            answerSheet.testStatus = TestStatus.FINISHED
            mongoTemplate.save(answerSheet)
        }
    }
}

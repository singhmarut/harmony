package grading

import com.harmony.questionPaper.QuestionPaper
import com.harmony.questionPaper.AnswerSheet
import com.harmony.questionPaper.QuestionResponse
import com.harmony.graph.Question
import com.harmony.grading.BinaryScoringStrategy
import com.harmony.shiro.auth.User
import com.harmony.questionPaper.CandidateReport

class ScoringService {

    def mongoTemplate
    def answerSheetService
    def questionPaperService
    def questionService
    def reportingService
    def mongoCollectionFactoryService

    def score(long companyId, long questionPaperId){
       //Find all the test papers for this company and question Paper who are finished
        List<AnswerSheet> answerSheets = answerSheetService.findFinishedAnswerSheets(questionPaperId, companyId)
        QuestionPaper questionPaper = questionPaperService.findById(companyId, questionPaperId)

        //todo: right now assumption is that each candidate is getting same questions
        List<Question> questions = questionPaperService.getAllQuestionsForQuestionPaper(companyId, questionPaperId)
        Map<Long, List<Integer>> questionCorrectAnswerMap = new HashMap<Long, List<Integer>>()

        for (Question question in questions){
            questionCorrectAnswerMap.put(question.questionId, new ArrayList<Integer>())
            List<Integer> correctAnswers = questionCorrectAnswerMap.get(question.questionId)
            //questionCorrectAnswerMap.put(question.getQuestionId(), question.choices);
            if(question.choice1){
                correctAnswers.add(1)
            }
            if(question.choice2){
                correctAnswers.add(2)
            }
            if(question.choice3){
                correctAnswers.add(3)
            }
            if(question.choice4){
                correctAnswers.add(4)
            }
            if(question.choice5){
                correctAnswers.add(5)
            }
        }

        for (AnswerSheet answerSheet in answerSheets){
            BinaryScoringStrategy scoringStrategy = new BinaryScoringStrategy()
            scoringStrategy.score(companyId,answerSheet, questionCorrectAnswerMap,questionService)
            answerSheet.evaluated = true
            mongoTemplate.save(answerSheet, mongoCollectionFactoryService.getAnswerCollName())
            User user = User.findById(answerSheet.candidateId)
            String htmlReport = reportingService.createReport(companyId,user,answerSheet)
            CandidateReport candidateReport = reportingService.findCandidateReport(answerSheet.authKey)
            if (!candidateReport){
                candidateReport = new CandidateReport()
            }
            candidateReport.authKey = answerSheet.authKey
            candidateReport.candidateId = answerSheet.candidateId
            candidateReport.htmlReport = htmlReport
            mongoTemplate.save(candidateReport, mongoCollectionFactoryService.getReportsCollName())
        }
    }
}

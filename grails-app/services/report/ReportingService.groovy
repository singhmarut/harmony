package report

import com.harmony.report.FreeMarkerGenerator
import com.harmony.shiro.auth.User
import com.harmony.questionPaper.AnswerSheet
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import com.harmony.questionPaper.CandidateReport
import com.harmony.questionPaper.QuestionPaper

class ReportingService {

    def questionPaperService
    def mongoCollectionFactoryService
    def mongoTemplate

    def findCandidateReport(String authKey){
       return mongoTemplate.findOne(new Query(Criteria.where("authKey").is(authKey)), CandidateReport.class, mongoCollectionFactoryService.getReportsCollName());
    }

    def findTestReports(long customerId, long questionPaperId){
        return mongoTemplate.find(new Query(Criteria.where("customerId").is(customerId).and("questionPaperId").is(questionPaperId)),
                CandidateReport.class, mongoCollectionFactoryService.getReportsCollName());
    }

    /**
     * Creates report for a particular authentication Key
     * @param authKey
     */
    def createReport(long customerId, User user,QuestionPaper questionPaper, AnswerSheet answerSheet){

        Map templateValues = new HashMap();
        templateValues.put("questionPaper", questionPaper)
        templateValues.put("user", user);
        templateValues.put("answerSheet", answerSheet);

        FreeMarkerGenerator reportGenerator = new FreeMarkerGenerator()
        return reportGenerator.generateReport(templateValues)
    }

//    /**
//     * creates reports for all answer sheets for given question paper
//     * @param customerId
//     * @param questionPaperId
//     */
//    def createReport(long customerId, long questionPaperId){
//        mongoCollectionFactoryService.getAnswerCollName()
//    }
}

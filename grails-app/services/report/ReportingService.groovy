package report

import com.harmony.report.FreeMarkerGenerator
import com.harmony.shiro.auth.User
import com.harmony.questionPaper.AnswerSheet
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria

class ReportingService {

    def questionPaperService
    def mongoCollectionFactoryService
    def mongoTemplate

    def findCandidateReport(String authKey){
       return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("authKey").is(authKey), mongoCollectionFactoryService.getReportsCollName()));
    }

    /**
     * Creates report for a particular authentication Key
     * @param authKey
     */
    def createReport(long customerId, User user, AnswerSheet answerSheet){

        Map templateValues = new HashMap();
        templateValues.put("user", user);
        templateValues.put("answerSheet", answerSheet);

        FreeMarkerGenerator reportGenerator = new FreeMarkerGenerator()
        return reportGenerator.generateReport(templateValues)
    }

    /**
     * creates reports for all answer sheets for given question paper
     * @param customerId
     * @param questionPaperId
     */
    def createReport(long customerId, long questionPaperId){
        mongoCollectionFactoryService.getAnswerCollName()
    }
}
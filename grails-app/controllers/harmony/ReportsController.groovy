package harmony

import org.apache.shiro.authz.annotation.*
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils
import com.google.gson.Gson
import com.harmony.questionPaper.CandidateReport

class ReportsController {

    def reportingService
    def index() { }

    /**
     * returns all reports for all candidates
     */
    @RequiresRoles("ROLE_ADMIN")
    def showCorporateReports(){

    }

    /**
     * returns the reports for logged in candidate
     */
    @RequiresAuthentication
    def showCandidateReport(String authKey){
       CandidateReport candidateReport = reportingService.findCandidateReport(authKey)
       render text: candidateReport.htmlReport, contentType:"text/html", encoding:"UTF-8"
    }

    def showQuestionPaperReports(long questionPaperId){
        Subject subject = SecurityUtils.subject
        def userId = subject.getSession().getAttribute("userId")
        def reports = reportingService.findTestReports(userId, questionPaperId)
        render new Gson().toJson(reports)
    }

    def showCandidateReportsView(long questionPaperId){
        render view: "candidateReports", model: [questionPaperId: questionPaperId]
    }
}

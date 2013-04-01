package harmony

import org.apache.shiro.authz.annotation.*

class TestReportsController {

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
    def showCandidateReport(){

    }
}

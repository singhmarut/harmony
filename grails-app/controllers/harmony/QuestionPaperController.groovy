package harmony

import com.harmony.questionPaper.Section
import com.harmony.questionPaper.QuestionPaper
import org.apache.shiro.SecurityUtils
import com.google.gson.Gson
import org.apache.shiro.grails.annotations.PermissionRequired
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.apache.shiro.subject.Subject
import org.apache.shiro.authz.annotation.RequiresRoles
import com.harmony.graph.Question


class QuestionPaperController {

    def questionPaperService
    def testKeyGeneratorService

    def getSkills(){

    }

    def mainMenu(){
        render view: "index"
    }

    def createPaper(){
        render(view: "createQuestionPaper")
    }

    def list(){
        def principal = SecurityUtils.subject?.principal
        def questionPaperList =  questionPaperService.findByCompany(1)
        render new Gson().toJson(questionPaperList)
    }

    def showQuestionPapers(){
        render(view: 'questionPaperList')
    }

    //@RequiresPermissions("test:taker")
    def loadQuestionPaper(){
        render(view: 'questionPaper')
    }

    def getQuestionsForPaper(){
        Subject currentUser = SecurityUtils.getSubject()
        String authKey = currentUser.getSession().getAttribute("authKey")
        QuestionPaper questionPaper = testKeyGeneratorService.findQuestionPaper(authKey)
        List<Question> questionList = questionPaperService.getAllQuestionsForQuestionPaper(questionPaper.getCompanyId(), questionPaper.getQuestionPaperId())
        render(new Gson().toJson(questionList))
    }

    def create(){
        QuestionPaper paper = new Gson().fromJson(params['questionPaper'], QuestionPaper.class)
        Subject currentUser = SecurityUtils.getSubject()
        long userId = currentUser.getSession().getAttribute("userId")
        questionPaperService.createQuestionPaper(paper,userId)
        render view: "createSections"
    }

    @RequiresRoles("ROLE_ADMIN")
    def getQuestionPaperKeys(long questionPaperId){
        Subject subject = SecurityUtils.subject
        def userId = subject.getSession().getAttribute("userId")
        //todo: change this user id to proper user
        userId = 1
        testKeyGeneratorService.getValidKeysForTest(userId,questionPaperId)
    }

    def index() {
        render(view: "paperOptions")
    }
}

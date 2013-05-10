package harmony

import com.harmony.questionPaper.QuestionPaper
import org.apache.shiro.SecurityUtils
import com.google.gson.Gson

import org.apache.shiro.subject.Subject
import org.apache.shiro.authz.annotation.RequiresRoles

import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

import groovy.json.JsonSlurper
import grails.converters.JSON


class QuestionPaperController {

    def questionPaperService
    def testKeyGeneratorService
    def scoringService

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
        questionPaper = questionPaperService.populateQuestionsForPaper(questionPaper.getCompanyId(), questionPaper.getQuestionPaperId())
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC)
                .create();
        render(gson.toJson(questionPaper))
    }

    def create(){
        QuestionPaper paper = new Gson().fromJson(params['questionPaper'], QuestionPaper.class)
        Subject currentUser = SecurityUtils.getSubject()
        long userId = currentUser.getSession().getAttribute("userId")
        questionPaperService.createQuestionPaper(paper,userId)
        response.setStatus(200)
        render ''
    }

    def getPaper(){
        Subject currentUser = SecurityUtils.getSubject()
        long userId = currentUser.getSession().getAttribute("userId")
        questionPaperService.findById(userId,params['questionPaperId'] as long)
        response.setStatus(200)
        render(view: 'createQuestionPaper')
    }

    def submitTest(){

        String response = params['response']
        def json= new JsonSlurper().parseText(response)

        Subject subject = SecurityUtils.subject
        String authKey = subject.getSession().getAttribute("authKey")
        //long userId = subject.getSession().getAttribute("userId")
        //userId = 1
        testKeyGeneratorService.finishTest(authKey,1 ,json)
        //Test done leave the session
        subject.logout()
        render ''

    }

    @RequiresRoles("ROLE_ADMIN")
    def getQuestionPaperKeys(long questionPaperId){
        Subject subject = SecurityUtils.subject
        def userId = subject.getSession().getAttribute("userId")
        testKeyGeneratorService.getValidKeysForTest(userId,questionPaperId)
    }

    def showQuestionPaperKeys(){
        long questionPaperId = params['questionPaperId'] as Long
        Subject subject = SecurityUtils.subject
        def userId = subject.getSession().getAttribute("userId")
        render testKeyGeneratorService.getValidKeysForTest(userId,questionPaperId) as JSON
    }

    def showAuthKeys(long questionPaperId){
        render view: "authKeys", model: [questionPaperId: questionPaperId]
    }

    def showSuccess(){
      render view: "success"

    }

    def startScoring(long questionPaperId){
        Subject currentUser = SecurityUtils.subject
        long userId = currentUser.getSession().getAttribute("userId")
        scoringService.score(userId, questionPaperId)
    }

    def index() {
        render(view: "paperOptions")
    }
}

class TestResponseDto{
    List<QuestionResponseDto> candidateResponse
}
class QuestionResponseDto{
    long questionId;
    List<Integer> answers;
}

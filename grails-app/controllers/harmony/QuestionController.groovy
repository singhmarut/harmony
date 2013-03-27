package harmony

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import com.google.gson.Gson
import grails.converters.XML


class QuestionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //render(view: "questionList")
        //return Question.all as JSON
        [questionInstanceList: Question.list(params), questionInstanceTotal: Question.count()]
    }

    def showQuestionList(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render(view: "questionListDataTable")
        //return Question.all as JSON
        //[questionInstanceList: Question.list(params), questionInstanceTotal: Question.count()]
    }

    def listQuestions(){
        //String json = Question.all as JSON
        Question newQuestion = new Question()
        newQuestion.text = "hello"
        QuestionResult questionResult = new QuestionResult()
        def questionList = new ArrayList()
        questionList.add(newQuestion)
        questionResult.Records = Question.all
        questionResult.Records = questionList
        questionResult.Result = "OK"
        questionResult.data = questionResult.Records
        questionResult.curPage = 1
        questionResult.totalRecords = questionResult.Records.size()

        String json = new Gson().toJson(questionResult)

        http://www.jqwidgets.com/jquery-widgets-documentation/documentation/jqxbutton/jquery-button-api.htm
        render json
    }

    def create() {
        [questionInstance: new Question(params)]
    }

    def updateQuestionProps(Question questionInstance){
        questionInstance.text = params['text']
        questionInstance.option1 = params['option1']
        questionInstance.option2 = params['option2']
        questionInstance.option3 = params['option3']
        questionInstance.option4 = params['option4']
        questionInstance.option5 = params['option5']
        questionInstance.answer1 = params['answer']

        questionInstance.questionType = "MCQ"
        questionInstance.marks = 1
        return questionInstance
    }

    def save() {
        def questionInstance = new Question()
        questionInstance = updateQuestionProps(questionInstance)
                QuestionResult questionResult = new QuestionResult()
        def questionList = new ArrayList()
        questionList.add(questionInstance)
        questionResult.Records = questionList
        questionResult.Result = "OK"

        String json = new Gson().toJson(questionResult)

        if (!questionInstance.save(flush: true)) {
            questionResult.Result = "ERROR"
        }
        questionResult.data = questionList
        questionResult.curPage = 1
        questionResult.totalRecords = questionList.count()
        render json
    }

    def show(Long id) {
        def questionInstance = Question.get(id)
        if (!questionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), id])
            redirect(action: "list")
            return
        }
        [questionInstance: questionInstance]
    }

    def edit(Long id) {
        def questionInstance = Question.get(id)
        if (!questionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), id])
            redirect(action: "list")
            return
        }
        [questionInstance: questionInstance]
    }

    def update(Long id, Long version) {
        def questionInstance = Question.get(id)
    }

    def delete(Long id) {
        def questionInstance = Question.get(id)
        if (!questionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'question.label', default: 'Question'), id])
            redirect(action: "list")
            return
        }

        try {
            questionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'question.label', default: 'Question'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'question.label', default: 'Question'), id])
            redirect(action: "show", id: id)
        }
    }

    class QuestionResult implements Serializable{
        def totalRecords
        def curPage
        def data
        def Result = "OK"
        def Records
    }
}

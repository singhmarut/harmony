package harmony

import org.springframework.dao.DataIntegrityViolationException

import com.google.gson.Gson

import org.apache.shiro.authz.annotation.RequiresPermissions
import com.harmony.graph.Question
import com.orientechnologies.common.reflection.OReflectionHelper


class QuestionController {

    def skillsService
    def questionImportService
    def orientDBService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    @RequiresPermissions('ADD_QUESTION_PERMISSION')
    def showQuestionList(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render(view: "questionListDataTable")
    }

    def listQuestions(String tagName){
        QuestionResult questionResult = new QuestionResult()
        def questionsList = orientDBService.getQuestions(tagName)
        List<QuestionDto> questionDtoList = new ArrayList<QuestionDto>()
        for (Question question in questionsList){
            questionDtoList.add(QuestionDto.build(question))
        }
        questionResult.data = questionDtoList
        questionResult.curPage = 1
        questionResult.totalRecords = questionDtoList.size()
        String json = new Gson().toJson(questionResult)
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
        questionInstance.choice1 = params['choice1']
        questionInstance.choice2 = params['choice2']
        questionInstance.choice3 = params['choice3']
        questionInstance.choice4 = params['choice4']
        questionInstance.choice5 = params['choice5']

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

        String json = new Gson().toJson(questionResult)
        questionInstance.save(flush: true)

        questionResult.data = questionList
        questionResult.curPage = 1
        questionResult.totalRecords = questionList.count()
        render json
    }

    def showBulkUpload(){
        render view:  "bulkUpload", model: [skills : skillsService.getAllSubjects()]
    }

    def bulkUpload(){
        //def classes = OReflectionHelper.getClassesForPackage()
        def userFile = params['questionsFile']
        questionImportService.uploadQuestions(userFile)
    }

    class QuestionResult implements Serializable{
        def totalRecords
        def curPage
        def data
    }
}

class QuestionDto{
    String text;
    String option1;
    String option2;
    String option3;
    String option4;
    String option5;
    //Order does not matter in choices
    List<String> choices;

    String questionType;
    String companyShortName;
    //Marks associated with question
    Integer marks;
    //Who uploaded this question
    Long userId;
    List<String> tags;
    boolean isDirty = false

    static build(Question question){
        QuestionDto questionDto = new QuestionDto()
        questionDto.text = question.text
        questionDto.option1 = question.option1
        questionDto.option2 = question.option2
        questionDto.option3 = question.option3
        questionDto.option4 = question.option4
        questionDto.option5 = question.option5
        questionDto.questionType = question.questionType
        return questionDto
    }
}

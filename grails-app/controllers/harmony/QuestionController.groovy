package harmony

import org.springframework.dao.DataIntegrityViolationException

import com.google.gson.Gson

import org.apache.shiro.authz.annotation.RequiresPermissions
import com.harmony.graph.Question
import com.orientechnologies.common.reflection.OReflectionHelper


class QuestionController {

    def skillsService
    def questionImportService
    def hyperService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    @RequiresPermissions('ADD_QUESTION_PERMISSION')
    def showQuestionList(Integer max) {
        render(view: "questionListDataTable", model: [tagName: params['tagName']])
    }

    def listQuestions(String tagName){
        QuestionResult questionResult = new QuestionResult()
        long companyId = 1
        def questionsList = hyperService.getQuestions(tagName,companyId)
        List<QuestionDto> questionDtoList = new ArrayList<QuestionDto>()
        for (Question question in questionsList){
            questionDtoList.add(QuestionDto.build(question))
        }
        questionResult.data = questionsList
        questionResult.curPage = 1
        questionResult.totalRecords = questionsList.size()
        String json = new Gson().toJson(questionsList)
        render json
    }

    def create() {
        [questionInstance: new Question(params)]
    }

    def save() {

    }

    def showBulkUpload(){
        render view:  "bulkUpload"
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

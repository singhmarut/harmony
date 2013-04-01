package harmony

import com.harmony.questionPaper.Section
import com.harmony.questionPaper.QuestionPaper

class QuestionPaperController {

    def questionPaperService
    def paper(){

        if (QuestionPaper.count == 0){
            QuestionPaper paper = QuestionPaper.create()

        }
        QuestionPaper questionPaper = QuestionPaper.get(1)

        render view: "questionPaperView", model: [questionPaper: questionPaper]
    }

    def getSkills(){

    }

    def mainMenu(){
        render view: "index"
    }

    def createPaper(){
        render(view: "createQuestionPaper")
    }

    def create(){
        QuestionPaper paper = questionPaperService.createDummyQuestionPaper()
        session.setAttribute("questionPaperId", paper.id)
        session.setAttribute("questionPaper", paper)
        render view: "createSections"
    }

    def createSection(String sectionName, String questionLinks){
        Section section = Section.newInstance()
        section.duration = params['duration']
        section.instruction = params['instruction']
        section.questionList = questionLinks

        QuestionPaper questionPaper = QuestionPaper.get(session.getAttribute("questionPaperId") as Long)
        if (questionPaper){
            questionPaper.sections.add(section)
            session.setAttribute("questionPaper", questionPaper)
        }
    }

    def index() {
        render(view: "paperOptions")
    }

    def uploadQuestions(){

    }
}

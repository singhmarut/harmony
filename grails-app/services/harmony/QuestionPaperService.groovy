package harmony

class QuestionPaperService {

    def createQuestionPaper(String instruction, List<Section> sections, String companyName){
        QuestionPaper paper = QuestionPaper.create()
        paper.instruction = instruction
        paper.sections = sections
        paper.company = Company.findByShortName(companyName)
        paper.save()
    }

    def createDummyQuestionPaper(){
        QuestionPaper paper = new QuestionPaper()
        paper = paper.save(flush: true)
        return paper
    }

    def serviceMethod() {

    }
}

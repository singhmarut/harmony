package harmony

import com.harmony.questionPaper.Section
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import com.harmony.questionPaper.QuestionPaper
import com.harmony.graph.Question
import com.google.gson.Gson
import com.harmony.questionPaper.SectionSubject
import com.harmony.graph.SubjectTag

class QuestionPaperService {

    def mongoTemplate
    def mongoCollectionFactoryService
    def questionService
    def hyperService
    def skillsService

    def createQuestionPaper(QuestionPaper questionPaper,long customerId){

        //todo: get company Id from session
        questionPaper.companyId = customerId
        questionPaper.questionPaperId = mongoCollectionFactoryService.increaseQuestionPaperCounter()
        List<Section> sectionList = questionPaper.getSectionList()
        for (Section section in sectionList){
            for (SectionSubject sectionSubject in section.getSectionSubjects()){
                SubjectTag findSubject = skillsService.findSubjectById(questionPaper.companyId, sectionSubject.subjectTagId)
                List<Question> questions = hyperService.getQuestions(findSubject.subjectName, questionPaper.companyId)
                List<Long> questionIds = new ArrayList<Long>()
                for (Question question : questions){
                    questionIds.add(question.questionId)
                }
                sectionSubject.questionsIds = questionIds
            }
        }
        mongoTemplate.save(questionPaper, mongoCollectionFactoryService.getQuestionPaperCollName(questionPaper.companyId))
    }

    def createDummyQuestionPaper(){

    }
    /**
     * returns list of all questions papers for a company
     * @param companyId
     * @return
     */
    def findByCompany(long companyId){
        String coll = mongoCollectionFactoryService.getQuestionPaperCollName(companyId)
        return mongoTemplate.findAll(QuestionPaper.class, coll)
    }

    def findById(long companyId, long id){
       String coll = mongoCollectionFactoryService.getQuestionPaperCollName(companyId)
       return mongoTemplate.findOne(new Query(Criteria.where("questionPaperId").is(id)), QuestionPaper.class, coll)
    }

    def getAllQuestionsForQuestionPaper(long companyId, long questionPaperId){
        QuestionPaper questionPaper = findById(companyId, questionPaperId)
        List<Question> questionList = new ArrayList<Question>()
        List<Long> questionIds = new ArrayList<Long>()
        for (Section section in questionPaper.sectionList){
            for (SectionSubject sectionSubject : section.getSectionSubjects()){
                questionIds.addAll(sectionSubject.questionsIds)
            }
            List<Question> subjectQuestions = questionService.findQuestions(companyId, questionIds)
            questionList.addAll(subjectQuestions)
        }
        return questionList
    }
}

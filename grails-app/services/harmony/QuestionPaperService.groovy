package harmony

import com.harmony.questionPaper.Section
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import com.harmony.questionPaper.QuestionPaper
import com.harmony.graph.Question
import com.google.gson.Gson

class QuestionPaperService {

    def mongoTemplate
    def mongoCollectionFactoryService

    def createQuestionPaper(String questionPaperJson){
       QuestionPaper questionPaper = new Gson().fromJson(questionPaperJson, QuestionPaper.class)
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
        for (Section section in questionPaper.sectionList){
            questionList.addAll(section.getQuestionList())
        }
        return questionList
    }

    def serviceMethod() {

    }
}

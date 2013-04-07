package harmony.questions

import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import com.harmony.graph.Question

class QuestionService {

    def mongoTemplate
    def mongoCollectionFactoryService

    def findQuestions(long companyId, List<Long> questionIds){
        String coll = mongoCollectionFactoryService.getQuestionsCollName(companyId)

        List<Question> questions = new ArrayList<Question>()
        for (Long questionId : questionIds){
            Question question = mongoTemplate.findOne(new Query(Criteria.where("questionId").is(questionId)), Question.class, coll)
            questions.add(question)
        }


        return questions
    }

    def findQuestions(long companyId, long subjectId){
        mongoTemplate.find(new Query(Criteria.where("")))
    }
}

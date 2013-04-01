package harmony.questions

import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import com.harmony.graph.Question

class QuestionService {

    def mongoTemplate
    def mongoCollectionFactoryService

    def findQuestions(int companyId, List<String> questionIds){
        String coll = mongoCollectionFactoryService.getQuestionPaperCollName(companyId)

        List<Question> questionList = mongoTemplate.find(new Query(Criteria.where("questionId").in(questionIds)), Question.class, coll)

        return questionList
    }
}

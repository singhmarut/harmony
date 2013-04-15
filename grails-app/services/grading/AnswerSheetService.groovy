package grading

import com.harmony.questionPaper.AnswerSheet
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria
import com.harmony.questionPaper.QuestionResponse
import com.harmony.questionPaper.QuestionPaper
import com.harmony.questionPaper.TestKey
import com.harmony.questionPaper.TestStatus


class AnswerSheetService {

    def mongoTemplate
    def questionPaperService
    def testKeyGeneratorService
    def mongoCollectionFactoryService

    def saveAnswerSheet(String authKey, QuestionResponse questionResponse) {

        //todo: check if authKey here is same as CId in session
        TestKey testKey = testKeyGeneratorService.findTestKey(authKey)
        long companyId = testKey.getCustomerId()
        String coll = mongoCollectionFactoryService.getAnswerCollName(companyId)

        AnswerSheet answerSheet = findAnswerSheetByAuthKey(authKey,companyId)
        if (!answerSheet){
            answerSheet = new AnswerSheet()
            answerSheet.candidateAnswers = new ArrayList<QuestionResponse>()
            answerSheet.authKey = authKey
            answerSheet.testKey = testKey
            answerSheet.companyId = testKey.customerId
            answerSheet.candidateId = testKey.candidateId
            answerSheet.questionPaperId = testKey.questionPaperId
            answerSheet.candidateAnswers.add(questionResponse)
            mongoTemplate.save(answerSheet, coll)
        }else{
            answerSheet.candidateAnswers.add(questionResponse)
            mongoTemplate.save(answerSheet, coll)
        }
    }

    def findAnswerSheetForCandidateByCompany(long candidateId,long questionPaperId,long companyId){

        String coll = mongoCollectionFactoryService.getAnswerCollName(companyId)
        return mongoTemplate.findOne(new Query(Criteria.where("candidateId").is(candidateId).and("companyId").is(companyId)
                .andOperator(Criteria.where("questionPaperId").is(questionPaperId))), AnswerSheet.class, coll)

    }

    def findFinishedAnswerSheets(long questionPaperId,long companyId){

        String coll = mongoCollectionFactoryService.getAnswerCollName(companyId)
        return mongoTemplate.find(new Query(Criteria.where("companyId").is(companyId).and("testStatus").is(TestStatus.FINISHED)
                .and("evaluated").is(false)
                .andOperator(Criteria.where("questionPaperId").is(questionPaperId))), AnswerSheet.class, coll)

    }

    def findCandidateAnswerSheets(long candidateId){
        String coll = mongoCollectionFactoryService.getAnswerCollName()
        return mongoTemplate.findOne(new Query(Criteria.where("candidateId")), AnswerSheet.class, coll)
    }


    def findAnswerSheetByAuthKey(String authKey, long customerId){
        String coll = mongoCollectionFactoryService.getAnswerCollName(customerId)

        return mongoTemplate.findOne(new Query(Criteria.where("authKey").is(authKey)), AnswerSheet.class, coll)
    }
}

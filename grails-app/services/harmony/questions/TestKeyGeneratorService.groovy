package harmony.questions

import com.harmony.questionPaper.QuestionPaper
import com.harmony.questionPaper.TestKey
import harmony.QuestionPaperService
import org.apache.commons.lang.RandomStringUtils
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.apache.shiro.SecurityUtils
import org.springframework.data.mongodb.core.query.Update
import com.harmony.questionPaper.AnswerSheet
import com.harmony.questionPaper.TestStatus
import com.harmony.questionPaper.QuestionResponse


class TestKeyGeneratorService {

    def mongoTemplate
    def questionPaperService
    def mongoCollectionFactoryService

    def generateKeys(long companyId, long questionPaperId, int keyCount){

        def principal = SecurityUtils.subject?.principal
        QuestionPaper questionPaper = questionPaperService.findById(companyId, questionPaperId)
        TestKey testKey = new TestKey()
        testKey.customerId = companyId
        testKey.questionPaper = questionPaper
        testKey.questionPaperId =  questionPaperId
        String coll = mongoCollectionFactoryService.getTestKeyCollName()

        while(keyCount >= 0){
            String randomKey = getTestKey()
            //Save test key if it does not exist already
            def findTestKey = mongoTemplate.findOne(new Query(Criteria.where("authKey").is(randomKey)), TestKey.class, coll)
            int counter = 0
            while (findTestKey && counter < 10){
                randomKey = getTestKey()
                findTestKey = mongoTemplate.findOne(new Query(Criteria.where("authKey").is(randomKey)), TestKey.class, coll)
                counter++
            }
            if (findTestKey){
                log.error("Unable to generate random key for questionPaper" + questionPaperId.toString())
            }else{
                testKey.authKey = randomKey
                mongoTemplate.save(testKey, coll)
            }
            keyCount--
        }
    }

    def createPasswordForCandidate(){
        return RandomStringUtils.randomAlphanumeric(8)
    }

    def findQuestionPaper(String authKey){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        def findTest = mongoTemplate.findOne(new Query(Criteria.where("authKey").is(authKey)), TestKey.class, coll)
        def paper
        if (findTest){
            paper = questionPaperService.findById(findTest.getCustomerId(), findTest.getQuestionPaperId())
        }
        return paper
    }

    def getValidKeysForTest(long customerId, long testId){
        //todo: Get Customer id from Principal
        customerId = 1
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        def validKeys = mongoTemplate.find(new Query(Criteria.where("customerId").is(customerId)
                .and("questionPaperId").is(testId).and("isExpired").is(false)), TestKey.class, coll)
        return validKeys
    }

    def findTestKey(String authKey){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        TestKey findTest = mongoTemplate.findOne(new Query(Criteria.where("authKey").is(authKey).and("isExpired").is(false)), TestKey.class, coll)
        return findTest
    }

    def findExpiredTestKey(String authKey){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        TestKey findTest = mongoTemplate.findOne(new Query(Criteria.where("authKey").is(authKey)), TestKey.class, coll)
        return findTest
    }
    /**
     * We need to associate authKey with a candidate
     * @param authKey
     */
    def bindAuthKeyWithCandidate(String authKey, long cid){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        //Associate key to a candidate
        TestKey testKey = new TestKey()
        testKey.candidateId = cid
        testKey.authKey = authKey
        testKey.expired = true
        mongoTemplate.updateFirst(new Query(Criteria.where("authKey").is(authKey).and("isExpired").is(false)),
                Update.update("isExpired", true).set("candidateId", cid),coll)
    }

    def authorize(String authKey){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        TestKey findTest = findTestKey(authKey)
        return findTest
    }

    def getTestKey(){
        RandomStringUtils.randomAlphanumeric(6)
    }

    def finishTest(String authKey,long userId,def response){
        TestKey testKey = findExpiredTestKey(authKey)
        AnswerSheet answerSheet = new AnswerSheet()
        QuestionPaper  questionPaper = findQuestionPaper(authKey)
        answerSheet.authKey = authKey
        answerSheet.candidateId = userId
        answerSheet.companyId = testKey.getCustomerId()
        answerSheet.questionPaperId = testKey.questionPaperId
        answerSheet.testStatus = TestStatus.FINISHED
        List<QuestionResponse> questionResponses = new ArrayList<QuestionResponse>()
        for (def questionResponse in response){
            QuestionResponse qResponse = new QuestionResponse()
            qResponse.answers = questionResponse['answers']
            qResponse.questionId = questionResponse['questionId']
            questionResponses.add(qResponse)
        }
        answerSheet.candidateAnswers = questionResponses
        String coll = mongoCollectionFactoryService.getAnswerCollName(testKey.getCustomerId())
        mongoTemplate.save(answerSheet, coll)

    }
}

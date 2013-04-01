package harmony.questions

import com.harmony.questionPaper.QuestionPaper
import com.harmony.questionPaper.TestKey
import harmony.QuestionPaperService
import org.apache.commons.lang.RandomStringUtils
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query


class TestKeyGeneratorService {

    def mongoTemplate
    def questionPaperService
    def mongoCollectionFactoryService

    def createKey(long companyId, long questionPaperId){

        QuestionPaper questionPaper = questionPaperService.findById(companyId, questionPaperId)
        TestKey testKey = new TestKey()
        testKey.customerId = questionPaper.getCompanyId()
        testKey.questionPaper = questionPaper
        testKey.questionPaperId =  questionPaper.getCompanyId()
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
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
    }

    def findQuestionPaper(String authKey){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        def findTest = mongoTemplate.findOne(new Query(Criteria.where("authKey").is(authKey)), TestKey.class, coll)
        def paper = findTest.getQuestionPaper()
        return paper
    }

    def getValidKeysForCompany(long customerId){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        def validKeys = mongoTemplate.findOne(new Query(Criteria.where("customerId").is(customerId)), TestKey.class, coll)
        return validKeys
    }

    def findTestKey(String authKey){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        TestKey findTest = mongoTemplate.findOne(new Query(Criteria.where("authKey").is(authKey)), TestKey.class, coll)
        return findTest
    }
    /**
     * We need to associate authKey with a candidate
     * @param authKey
     */
    def authorize(String authKey, long cid){
        String coll = mongoCollectionFactoryService.getTestKeyCollName()
        TestKey findTest = findTestKey(authKey)
        //Once key has been authorized it can't be used anymore
        findTest.expired = true
        //Associate key to a candidate
        findTest.candidateId = cid
        mongoTemplate.save(findTest, coll)
    }

    def getTestKey(){
        RandomStringUtils.randomAlphanumeric(6)
    }
}

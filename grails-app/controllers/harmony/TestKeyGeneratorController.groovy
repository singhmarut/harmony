package harmony

import com.google.gson.Gson

class TestKeyGeneratorController {

    def testKeyGeneratorService

    def generate() {
        //todo: get from session
        long companyId = 1
        AuthKeyRequest authKeyRequest = new Gson().fromJson(params['authReq'], AuthKeyRequest.class)

        testKeyGeneratorService.generateKeys(companyId, authKeyRequest.id, authKeyRequest.keyCount)
    }

    def authorizeKey(String authKey){
        if (!testKeyGeneratorService.findQuestionPaper(authKey) ){
            flash.message = "Wrong Authroization Key. Please try again"
        }
    }
}

class AuthKeyRequest{
   long id  //question Paper Id
   int keyCount
}

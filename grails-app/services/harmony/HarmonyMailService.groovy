package harmony

import com.harmony.shiro.auth.User

class HarmonyMailService {
    def mailService
    def sendAdminAccountCreationMail(User user){
        try{
            mailService.sendMail {
                to user.username
                subject "Prospect Hire account created"
                text """Congratulations!! Your account on prospect hire has been successfully created.
                        Please click on this link to active your account """
            }
        }catch(Exception ex){
            log.error("Unable to account confirmation email to user ")
        }
    }

    def sendCandidateAccountCreationMail(User user,String password){

        String body = String.format("Congratulations!! Your account on prospect hire has been successfully created. Your password is %s", password)
        try{
            mailService.sendMail {
                to user.username
                bcc "singh.marut@gmail.com"
                subject "Prospect Hire account created"
                text body
            }
        }catch(Exception ex){
            log.error("Unable to account confirmation email to user ")
        }
    }
}

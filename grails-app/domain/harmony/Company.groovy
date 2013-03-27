package harmony

import harmony.auth.SecUser

class Company{

    String shortName
    String fullName
    String logoPath
    Boolean isEnabled
    //static hasOne = [user: SecUser]
    static hasMany = [answerSheets: AnswerSheet,questionPapers: QuestionPaper, candidates: Candidate, skillTags: SkillTag]

    static constraints = {
        fullName(nullable: true)
        logoPath(nullable: true)
    }
}

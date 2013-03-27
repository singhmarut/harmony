package harmony

class Candidate implements Serializable {

    String emailId
    String firstName
    String lastName
    Integer phoneNumber

    static hasMany = [comapany: Company, answerSheets: AnswerSheet]
    static belongsTo = [Company]

    static constraints = {
    }
}

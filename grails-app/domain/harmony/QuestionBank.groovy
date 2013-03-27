package harmony

class QuestionBank {

    String name
    static hasMany = [questions: Question]

    static constraints = {
        name(unique: true)
    }
}

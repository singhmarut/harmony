package harmony

class Section implements Serializable{

    Double duration
    String sectionName
    String instruction
    String questionList

    static belongsTo = [questionPaper: QuestionPaper]

    static hasMany = [questions: Question]

    static constraints = {

    }
}

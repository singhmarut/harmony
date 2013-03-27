package harmony

class AnswerSheet implements Serializable{

    static hasOne = [candidate: Candidate]

    static belongsTo = [Candidate]

    static constraints = {
    }
}

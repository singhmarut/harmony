package harmony

class QuestionType {

    QuestionTypeEnum questionTypeEnum
    static constraints = {
    }
}

enum QuestionTypeEnum{
    MCQ,
    MCA,
    FIB
}

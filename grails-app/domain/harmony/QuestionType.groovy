package harmony

class QuestionType {

    QuestionTypeEnum questionTypeEnum
    static constraints = {
    }
}

enum QuestionTypeEnum{
    MCQ,  //Multiple Choice Questions
    MCA,  //Multiple choice answers
    FIB   //Fill in the blanks
}

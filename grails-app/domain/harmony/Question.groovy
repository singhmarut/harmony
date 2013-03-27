package harmony

class Question{

    String text
    String option1
    String option2
    String option3
    String option4
    String option5

    Boolean answer1
    Boolean answer2
    Boolean answer3
    Boolean answer4
    Boolean answer5

    QuestionTypeEnum questionType
    String companyShortName
    //Marks associated with question
    Integer marks

    static constraints = {
       companyShortName(nullable: true)
       questionType(nullable: true)
    }
}

package harmony

class QuestionPaper implements Serializable {

    String instruction
    Boolean active = false
    static hasMany = [sections: Section]

    static hasOne = [company: Company]

    static belongsTo = [Company]

    static constraints = {
        instruction(nullable: true)
        sections(nullable: true)
        company(nullable: true)

    }
}

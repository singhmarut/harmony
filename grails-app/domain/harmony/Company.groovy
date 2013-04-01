package harmony

class Company{

    String shortName
    String fullName
    String logoPath
    Boolean isEnabled
    //static hasOne = [user: SecUser]
    static hasMany = [candidates: Candidate]

    static constraints = {
        fullName(nullable: true)
        logoPath(nullable: true)
    }
}

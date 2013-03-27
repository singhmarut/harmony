package harmony

class SkillTag implements Serializable {

    String skillName

    static belongsTo = [company: Company]

    static constraints = {
    }
}

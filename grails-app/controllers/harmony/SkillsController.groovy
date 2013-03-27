package harmony

import com.google.gson.Gson

class SkillsController {

    def index(){
        render view: "skills"
    }
    def list(){
        String companyName = session.getAttribute('companyName')
        def company = Company.findByShortName(companyName)
        List<String> skillTags = new ArrayList<String>()
        if (company){
             skillTags = company.getSkillTags()
        }
        SkillResult skillResult = new SkillResult()
        skillResult.data = skillTags
        skillResult.totalRecords = skillTags.size()
        skillResult.curPage = 1
        render new Gson().toJson(skillResult)
    }

    def create(){
        String companyName = session.getAttribute('companyName')
        def company = Company.findByShortName(companyName)
        String skillName = params['skillName']
        List<String> skillTags = new ArrayList<String>()
        if (company){
            SkillTag skillTag = new SkillTag()
            skillTag.skillName = skillName
            //If skill is not present with same name then add it
            if (!SkillTag.findBySkillName(skillName)){
               company.skillTags.add(skillTag)
               company.save(flush: true)
            }
            skillTags = company.getSkillTags()
        }
        return skillTags
    }

    def delete(){
        String companyName = session.getAttribute('companyName')
        def company = Company.findByShortName(companyName)
        String skillName = params['skillName']
        List<String> skillTags = new ArrayList<String>()
        if (company){
            SkillTag skillTag = SkillTag.findBySkillName(skillName)
            //If skill is not present with same name then add it
            if (skillTag){
                company.skillTags.remove(skillTag)
                company.save(flush: true)
            }
            skillTags = company.getSkillTags()
        }
        return skillTags
    }


    def rename(){
        String companyName = session.getAttribute('companyName')
        def company = Company.findByShortName(companyName)
        String skillName = params['skillName']
        String newName = params['newSkillName']
        List<String> skillTags = new ArrayList<String>()
        if (company){
            SkillTag skillTag = SkillTag.findBySkillName(skillName)
            //If skill is not present with same name then add it
            if (skillTag){
                skillTag.skillName = newName
                company.save(flush: true)
            }
            skillTags = company.getSkillTags()
        }
        return skillTags
    }

    class SkillResult implements Serializable{
        def totalRecords
        def curPage
        def data
    }
}

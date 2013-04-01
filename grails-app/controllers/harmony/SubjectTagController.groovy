package harmony

import com.google.gson.Gson
import org.apache.shiro.SecurityUtils
import grails.converters.JSON
import com.harmony.graph.SubjectTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

class SubjectTagController {

    def skillsService

    @Autowired
    MongoTemplate mongoTemplate


    def index(){
        //mongoTemplate.
        render view: "skills"
    }

    def list(){

        String companyName = session.getAttribute('companyName')?:'ProspectHire'
        //def company = Company.findByShortName(companyName)
        //List<String> skillTags = new ArrayList<String>()
        def skillTags
        if (params['id']){
           long id = params['id'] as Long
           skillTags  = skillsService.getChildrenSubjects(companyName, id)
        } else{
            skillTags = skillsService.getAllSubjects(companyName)
        }


        def allSubjects = new ArrayList<SubjectDto>()
        for (SubjectTag subjectTag in skillTags){
            SubjectDto subjectDto = new SubjectDto()
            subjectDto.company = subjectTag.companyId
            subjectDto.text = subjectTag.subjectName
            subjectDto.id = subjectTag.nodeId
            allSubjects.add(subjectDto)
        }

        //SkillResult skillResult = new SkillResult()
        String json = new Gson().toJson(allSubjects)
        render json
    }

    def create(){
        def principal = SecurityUtils.subject?.principal

        String companyName = session.getAttribute('companyName')?:'ProspectHire'
        //def company = Company.findByShortName(companyName)
        SubjectDto subjectDto

        if(params['so']){
            subjectDto = new Gson().fromJson(params['so'], SubjectDto.class)
        }
        List<String> skillTags = new ArrayList<String>()

        SubjectTag createdTag =  skillsService.createNewSubject(subjectDto.text, subjectDto.parentSkill)
        subjectDto.id = createdTag.nodeId
        //Just a dummy response
//        SkillResult skillResult = new SkillResult()
//        skillResult.data = skillTags
//        skillResult.totalRecords = skillTags.size()
//        skillResult.curPage = 1

        render new Gson().toJson(subjectDto)
    }

    def delete(){
        String companyName = session.getAttribute('companyName')
        def company = Company.findByShortName(companyName)
        String skillName = params['skillName']
        List<SubjectTag> skillTags = new ArrayList<SubjectTag>()
        if (company){
            SubjectTag skillTag = SubjectTag.findBySkillName(skillName)
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
            SubjectTag skillTag = SubjectTag.findBySkillName(skillName)
            //If skill is not present with same name then add it
            if (skillTag){
                skillTag.skillName = newName
                company.save(flush: true)
            }
            skillTags = company.getSkillTags()
        }
        return skillTags
    }

    class SubjectDto{

        String text
        String state = "closed"
        boolean checked = false
        long id
        String company
        String parentSkill
    }

    class SkillResult implements Serializable{
        List<SubjectDto> records
    }
}

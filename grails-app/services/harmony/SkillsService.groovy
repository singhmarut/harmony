package harmony

import com.harmony.graph.SubjectTag
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria

class SkillsService {

    def orientDBService

    def mongoTemplate

    SubjectTag findSubjectById(int companyId, long subjectId){
        mongoTemplate.find(new Query(Criteria.where("companyId").is(companyId).and("nodeId").is(subjectId)))
    }

    List<SubjectTag> getAllSubjects(String company){
        Company company1 = Company.findByShortName(company)
        return orientDBService.getTopLevelSubjects(1)
    }

    List<SubjectTag> getChildrenSubjects(String company,long startNode){
        Company company1 = Company.findByShortName(company)

        return orientDBService.getSubjectsTillDepth(1,startNode,1)
    }

    def createNewSubject(String subject,String parentSubject){
        orientDBService.createSkill(1,subject,parentSubject)
    }

    def serviceMethod() {

    }
}

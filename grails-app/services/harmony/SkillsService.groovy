package harmony

import com.harmony.graph.SubjectTag
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Criteria

class SkillsService {

    def neo4JService
    def mongoCollectionFactoryService

    def mongoTemplate

    SubjectTag findSubjectById(long companyId, long subjectId){
        String coll = mongoCollectionFactoryService.getSubjectCollName(companyId)
        SubjectTag subjectTag = mongoTemplate.findOne(new Query(Criteria.where("companyId").is(companyId).and("skillId").is(subjectId)),SubjectTag.class, coll)
        return subjectTag
    }

    SubjectTag findSubjectByName(long companyId, String subjectName){
        String coll = mongoCollectionFactoryService.getSubjectCollName(companyId)
        SubjectTag subjectTag = mongoTemplate.findOne(new Query(Criteria.where("companyId").is(companyId).and("subjectName").is(subjectName)),SubjectTag.class, coll)
        return subjectTag
    }

    List<SubjectTag> getAllSubjects(String company, int depth){
        Company company1 = Company.findByShortName(company)
        return neo4JService.getTopLevelSubjects(depth)
    }

    List<SubjectTag> getSubjects(long companyId){
        Company company1 = Company.findByShortName(companyId)
        return neo4JService.getAllSubjects(companyId)
    }

    List<SubjectTag> getChildrenSubjects(String company,long startNode){
        Company company1 = Company.findByShortName(company)

        return neo4JService.getSubjectsTillDepth(1,startNode,1)
    }

    def createNewSubject(String subject,String parentSubject){
        neo4JService.createSkill(1,subject,parentSubject)
    }

    def serviceMethod() {

    }
}

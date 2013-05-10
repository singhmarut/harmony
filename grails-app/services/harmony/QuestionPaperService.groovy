package harmony

import com.harmony.questionPaper.Section
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import com.harmony.questionPaper.QuestionPaper
import com.harmony.graph.Question
import com.google.gson.Gson
import com.harmony.questionPaper.SectionSubject
import com.harmony.graph.SubjectTag
import com.harmony.questionPaper.TestKey
import com.harmony.questionPaper.AnswerSheet
import com.harmony.questionPaper.TestStatus

class QuestionPaperService {

    def mongoTemplate
    def mongoCollectionFactoryService
    def questionService
    def hyperService
    def skillsService
    //def testKeyGeneratorService

    def createQuestionPaper(QuestionPaper questionPaper,long customerId){

        List<String> errors = validateQuestionPaper(questionPaper)
        if (!errors.size()){
            questionPaper.companyId = customerId
            questionPaper.questionPaperId = mongoCollectionFactoryService.increaseQuestionPaperCounter()
            List<Section> sectionList = questionPaper.getSectionList()
            Set<Long> questionIdSet = new HashSet<Long>()

            for (Section section in sectionList){
                List<String> subjectNames = new ArrayList<String>()
                for (SectionSubject sectionSubject in section.getSectionSubjects()){
                    subjectNames.add(sectionSubject.getSubjectName())
                }
                hyperService.getQuestions(subjectNames, customerId)
//                for (SectionSubject sectionSubject in section.getSectionSubjects()){
//                    SubjectTag findSubject = skillsService.findSubjectByName(questionPaper.companyId, sectionSubject.subjectName)
//                    sectionSubject.subjectTagId = findSubject.getSkillId()
//
//                    List<Question> questions = hyperService.getQuestions(findSubject.subjectName, questionPaper.companyId)
//                    int subjectQuestionCount = sectionSubject.getQuestionCount()
//                    int difficulty = sectionSubject.getDifficultyLevel()
//                    List<Long> questionIds = new ArrayList<Long>()
//
//                    for (Question question : questions){
//                        //Make sure only unique questions are picked up
//                        if (!questionIdSet.contains(question.questionId)){
//                            if ((question.getDifficultyLevel() == difficulty) && (questionIds.size() < subjectQuestionCount)){
//                                questionIds.add(question.questionId)
//                                questionIdSet.add(question.questionId)
//                            }
//                        }
//                    }
//                    sectionSubject.questionsIds = questionIds
//                }
            }
            mongoTemplate.save(questionPaper, mongoCollectionFactoryService.getQuestionPaperCollName(questionPaper.companyId))
        }
    }

    def validateQuestionPaper(QuestionPaper questionPaper){
        List<String> errors = new ArrayList<String>()
        List<Section> sectionList = questionPaper.getSectionList()
        Set<String> sections = new HashSet<String>();
        for (Section section in sectionList){
            if (sections.contains(section.getSectionName())){
                errors.add(String.format("Section %s is duplicate", section.getSectionName()))
            }
        }
        for (Section section in sectionList){
            Set<String> subjects = new HashSet<String>();
            for (SectionSubject sectionSubject in section.getSectionSubjects()){
                if (subjects.contains(sectionSubject.subjectName)){
                    errors.add(String.format("Subject %s is duplicate", sectionSubject.subjectName))
                }
            }
        }
        return errors
    }

    def createDummyQuestionPaper(){

    }
    /**
     * returns list of all questions papers for a company
     * @param companyId
     * @return
     */
    def findByCompany(long companyId){
        String coll = mongoCollectionFactoryService.getQuestionPaperCollName(companyId)
        return mongoTemplate.findAll(QuestionPaper.class, coll)
    }

    def findById(long companyId, long id){
       String coll = mongoCollectionFactoryService.getQuestionPaperCollName(companyId)
       return mongoTemplate.findOne(new Query(Criteria.where("questionPaperId").is(id)), QuestionPaper.class, coll)
    }

    def getAllQuestionsForQuestionPaper(long companyId, long questionPaperId){
        QuestionPaper questionPaper = findById(companyId, questionPaperId)

        List<Long> questionIds = new ArrayList<Long>()
        List<Question> allQuestions = new ArrayList<Question>()
        for (Section section in questionPaper.sectionList){
            List<Question> questionList = new ArrayList<Question>()
            for (SectionSubject sectionSubject : section.getSectionSubjects()){
                questionIds.addAll(sectionSubject.questionsIds)
                List<Question> questions = questionService.findQuestions(companyId, questionIds)
                //Populate section subject with questions
                sectionSubject.setQuestions(questions)
                questionList.addAll(questions)
                allQuestions.addAll(questions)
            }
            section.setSectionQuestions(questionList);
        }
        return allQuestions
        //return questionList
    }

    def populateQuestionsForPaper(long companyId, long questionPaperId){
        QuestionPaper questionPaper = findById(companyId, questionPaperId)


        for (Section section in questionPaper.sectionList){
            List<Question> questionList = new ArrayList<Question>()
            List<Long> questionIds = new ArrayList<Long>()
            for (SectionSubject sectionSubject : section.getSectionSubjects()){
                questionIds.addAll(sectionSubject.questionsIds)
                List<Question> questions = questionService.findQuestions(companyId, questionIds)
                //Populate section subject with questions
                sectionSubject.setQuestions(questions)
                questionList.addAll(questions)
            }
            section.setSectionQuestions(questionList);
        }
        return questionPaper
        //return questionList
    }
}

package com.prospecthire.graphdb

import com.harmony.graph.Question
import com.orientechnologies.orient.object.db.OObjectDatabaseTx
import com.orientechnologies.orient.object.db.OObjectDatabasePool
import com.harmony.graph.QuestionTag

class QuestionTagService {

    void createNewTag(String questionTagName){
        OObjectDatabaseTx db = OObjectDatabasePool.global().acquire("remote:localhost/test1", "admin", "admin");
        QuestionTag questionTag = new QuestionTag()
        questionTag.setTagName(questionTagName)
        try{
            db.save(questionTag)
        }finally {
            db.close();
        }
    }

    void tagQuestion(Question question, String questionTagName){
        List<String> existingTags = question.getTags()
        existingTags.add(questionTagName)
    }
}

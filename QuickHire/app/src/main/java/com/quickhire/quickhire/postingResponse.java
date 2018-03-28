package com.quickhire.quickhire;

import java.util.Vector;

/**
 * Created by onick on 2018-03-26.
 */

public class postingResponse {
    public String accessKey, company, description, title;
    public Integer postID, ownerID, appIdToUse;
    public Vector<questionResponse> questions = new Vector<questionResponse>();

    public jobPosting convert(){
        jobPosting posting = new jobPosting(company, title, description,postID, ownerID);
        posting.setAppIdToUse(appIdToUse);
        for(questionResponse q : questions){
            if (q.type == 2) {
                Question question = new multipleChoiceQuestion(q.question);
                question.setId(q.id);
                posting.questions.add(question);
            } else if (q.type == 0) {
                Question question = new videoQuestion(q.question, 0);
                question.setId(q.id);
                posting.questions.add(question);
            } else {
                Question question = new essayQuestion(q.question);
                question.setId(q.id);
                posting.questions.add(question);
            }
        }
        return posting;
    }
}

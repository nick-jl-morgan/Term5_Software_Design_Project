package com.quickhire.quickhire;

import java.util.Vector;

/** postingResponse ***************************************************************************
 * Created by onick on 2018-03-26.
 * Description: To create a object response to a posting from the database.
 * @param - String company: company name
 *          String description: description of the job posting
 *          String title: title of the job posting
 *          int postID: job posting id
 *          int ownerID: owner id of the employer that created the posting
 *          int appIdToUse: id to use when creating a application response
 *          Vector<questionResponse> questions: list of questions in the job posting
 ******************************************************************************************/
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

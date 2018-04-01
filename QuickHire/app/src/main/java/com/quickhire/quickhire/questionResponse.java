package com.quickhire.quickhire;

/** questionResponse ***********************************************************************************
 * Created by onick on 2018-03-26.
 * Description: question response from the database used in job posting response for list of questions.
 * @param - String question: question text
 *          Integer id: id for the question
 *          String[] options: the array of options if the question is a multiple choice.
 *          int type: type of question.
 *****************************************************************************************************/
public class questionResponse {
    public String question;
    public Integer id;
    public String[] options;
    public int type;
}

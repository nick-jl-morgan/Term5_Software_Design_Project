package com.quickhire.quickhire;

/**
 * Created by onick on 2018-03-27.
 */

public class Answer {
    private Integer applicationID;
    private String Answer;
    private int questionID, type;

    public Answer(int id, String value, int t){
        this.questionID = id;
        this.Answer = value;
        this.type = t;
    }

    public String toJSON() {
        String string = "{\"questionID\":"+ this.questionID+",\"answer\":\""+this.Answer+"\",\"type\":\"" + this.type + "\"}";
        return string;
    }

    public String getAnswer(){return Answer;}
    public int getQuestionID(){return this.questionID;}
    public int getApplicationID(){return this.applicationID;}
    public void setApplicationID(int id){this.applicationID=id;}

    protected void beginTransmitting() {
        //nothing
    }
}

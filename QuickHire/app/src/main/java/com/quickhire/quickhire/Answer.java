package com.quickhire.quickhire;

/**
 * Created by onick on 2018-03-27.
 */

public class Answer {
    private String Answer;
    private Integer questionID, type;

    public Answer(Integer id, String value, Integer t){
        this.questionID = id;
        this.Answer = value;
        this.type = t;
    }

    public String toJSON() {
        String string = "{\"questionID\":"+ this.questionID+",\"Answer\":\""+this.Answer+"\",\"type\":\"" + this.type + "\"}";
        return string;
    }

    public String getAnswer(){return Answer;}
}

package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-04.
 */

public class videoQuestion extends Question {
    private int length;

    public videoQuestion(String question, int length){
        super(question, questionType.VIDEO);
        this.length=length;
    }

    public String toJSON(){
        String string ="{'type':"+this.numericQuestionType+",'length':"+this.length+",'question':"+this.questionText+"'}"; //i.e 'type':0,length:60,question:Why do you want this position?'
        return string;
    }
}
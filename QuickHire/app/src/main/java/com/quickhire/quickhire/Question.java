package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-04.
 */



public abstract class Question {
    protected String questionText;
    private questionType qType;
    protected int numericQuestionType; //useful incase of changes to enumeration and also database interactions

    public Question(String questionText, questionType qType){
        this.questionText= questionText;
        this.qType = qType;
        this.numericQuestionType=qType.getNumVal();
    }


    public abstract String toJSON(); //for display and transport.
}

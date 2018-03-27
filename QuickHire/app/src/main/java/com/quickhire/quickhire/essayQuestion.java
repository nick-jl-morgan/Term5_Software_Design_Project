package com.quickhire.quickhire;
/**
 * Created by matth_000 on 2018-03-04.
 */


public class essayQuestion extends Question {

    int maxLength = 1000; //may change

    public essayQuestion(String question){
        super(question, questionType.ESSAY);
    }

   @Override public String toJSON(){
        String string = "{\"type\":"+ this.numericQuestionType+",\"question\":\""+this.questionText+"\"}"; //i.e 'type:2, question: What makes you the best candidate for this position?'
        return string;
    }

    @Override
    public int getPicture() {
        return R.drawable.icon_essay;
    }

    @Override public String getType(){return "Essay";}

    @Override
    public Integer intType() {
        return 1;
    }
}

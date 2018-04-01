package com.quickhire.quickhire;

/** Application ***********************************
 * Created by matth_000 on 2018-03-04.
 * Description: object for storing essay questions.
 **************************************************/
public class essayQuestion extends Question {
    public essayQuestion(String question){
        super(question, questionType.ESSAY);
    }

   @Override public String toJSON(){
        String string = "{\"type\":"+ this.numericQuestionType+",\"question\":\""+this.questionText+"\"}"; //i.e 'type:2, question: What makes you the best candidate for this position?'
        return string;
    }

    @Override
    public Answer toAnswer(){
       return new Answer(this.getId(), this.getAnswer(), this.intType());
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

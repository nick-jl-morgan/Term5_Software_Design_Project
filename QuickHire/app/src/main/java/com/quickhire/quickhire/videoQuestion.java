package com.quickhire.quickhire;

import android.net.Uri;

/**
 * Created by matth_000 on 2018-03-04.
 */

public class videoQuestion extends Question {
    private int length;
    private Uri video;

    public videoQuestion(String question, int length){
        super(question, questionType.VIDEO);
        this.length=length;
    }

    public String toJSON(){
        String string ="{\"type\":"+this.numericQuestionType+",\"length\":"+this.length+",\"question\":\""+this.questionText+"\"}"; //i.e 'type':0,length:60,question:Why do you want this position?'
        return string;
    }

    public void setTime(int time){length = time;}

    public int getTime(){return length;}

    public void setvideo(Uri v){this.video=v;}

    @Override
    public int getPicture() {
        return R.drawable.icon_video;
    }

    @Override public String getType(){return "Video";}

    @Override
    public Integer intType() {
        return 0;
    }
    @Override
    public Answer toAnswer(){
        return new videoAnswer(this.video,this.getId());
    }

}

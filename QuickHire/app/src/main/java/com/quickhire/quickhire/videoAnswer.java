package com.quickhire.quickhire;

import android.net.Uri;

/**
 * Created by matth_000 on 2018-03-27.
 */

public class videoAnswer extends Answer{
    int type = questionType.VIDEO.getNumVal();
    Uri vid;
    int qID;

    public videoAnswer(Uri video, int questionID){
        super(questionID,video.getPath(),questionType.VIDEO.getNumVal());
        this.vid = video;
    }

    public String toJSON(){
        String JSON="{\"questionID\":"+qID+","
                    +"\"Answer\":\"videoTransmitting\","
                    +"\"type\":"+type+"}";
        //connection.getConnection().uploadVideo(this);
        return JSON;
    }

    @Override
    protected void beginTransmitting(){
        connection.getConnection().uploadVideo(this);
    }

    protected Uri getUri(){return this.vid;}

}

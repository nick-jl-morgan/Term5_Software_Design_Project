package com.quickhire.quickhire;

import android.net.Uri;

/**
 * Created by matth_000 on 2018-03-27.
 */

public class videoAnswer {
    int type = questionType.VIDEO.getNumVal();
    Uri vid;
    int qID;

    public videoAnswer(Uri video, int questionID){
        this.vid = video;
        this.qID=questionID;
    }

    public String toJSON(){
        String JSON="{\"questionID\":"+qID+","
                    +"\"Answer\",\"videoTransmitting\","
                    +"\"type\""+type+"}";
        connection.getConnection().uploadVideo(this);
        return JSON;
    }

    private void beginTransmitting(){
        new videoStreamer().execute(this);
    }

    public Uri getUri(){return this.vid;}
}

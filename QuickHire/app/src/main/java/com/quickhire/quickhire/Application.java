package com.quickhire.quickhire;

import java.util.Vector;

/**
 * Created by onick on 2018-03-27.
 */

public class Application {
    private Integer applicationID;
    private Integer postID;
    private Integer hasVideo = 0;
    public Vector<Answer> answers = new Vector<Answer>();

    public Application(Integer id, Vector<Question> questions){
        for(Question q: questions){
            if(q.intType() == 0){
                this.hasVideo = 1;
            }
            Answer a = q.toAnswer();
            this.answers.add(a);
        }
        this.postID = id;
    }

    public String toJSON(){
        String temp = "{\"postID\":\"" + this.postID + "\","
                + "\"hasVideo\":\""+this.hasVideo  + "\"," +  "\"answers\":[";
        StringBuilder builder = new StringBuilder(temp);


        int size = answers.size();
        if(size > 0) {
            for (int i = 0; i < size; i++) {
                builder.append(answers.elementAt(i).toJSON());
                if (i < (size - 1))
                    builder.append(",");
            }
        }
        builder.append("]}");
        return builder.toString();
    }

    public void setApplicationID(int id){
        this.applicationID=id;
        for(Answer a: answers){
            a.setApplicationID(id);
        }
    }
    public void postResponse(){
        for(Answer a: answers){
            a.beginTransmitting();
        }

    }

}

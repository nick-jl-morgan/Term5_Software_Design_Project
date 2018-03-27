package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-04.
 */

import java.util.Iterator;
import java.util.Vector;

public class jobPosting {
    public String company, jobTitle, description,title;
    public Integer postID, ownerID;
    private String accessKey;

    public Vector<Question> questions = new Vector<Question>();

    public jobPosting(String company, String jobTitle, String description){
        this.company=company;
        this.jobTitle=jobTitle;
        this.description = description;
    }

    public jobPosting(String company, String jobTitle, String description, Integer postID, Integer ownerID){
        this.company=company;
        this.jobTitle=jobTitle;
        this.description = description;
        this.postID=postID;
        this.ownerID=ownerID;
    }

    //"\"postID\":" + this.postID+"," + "\"ownerID\":" + this.ownerID+","
    public String toJSON(){
        String temp = "{\"company\":\"" + this.company + "\","
                + "\"jobTitle\":\""+this.jobTitle+"\","
                + "\"description\":\""+this.description+"\","
                +"\"questions\":[";

        StringBuilder builder = new StringBuilder(temp);


        int size = questions.size();
        if(size > 0) {
            for (int i = 0; i < size; i++) {
                builder.append(questions.elementAt(i).toJSON());
                if (i < (size - 1))
                    builder.append(",");
            }
        }
        builder.append("]}");
        return builder.toString();
    }

    public void addQuestion(Question question){
        this.questions.add(question);
    }

    public Application toapplication(){
        Application apply = new Application(this.postID, this.questions);
        return apply;
    }
}

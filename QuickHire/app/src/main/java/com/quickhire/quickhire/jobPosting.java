package com.quickhire.quickhire;

/**
 * Created by matth_000 on 2018-03-04.
 */

import java.util.Iterator;
import java.util.Vector;

public class jobPosting {
    public String company, jobTitle, description;
    private int postID, ownerID;

    private Vector<Question> questions;

    public jobPosting(String Company, String jobTitle, String description){
        this.company=company;
        this.jobTitle=jobTitle;
        this.description = description;
    }

    public jobPosting(String Company, String jobTitle, String description, int postID, int ownerID){
        this.company=company;
        this.jobTitle=jobTitle;
        this.description = description;
        this.postID=postID;
        this.ownerID=ownerID;
    }

    public String toJSON(){
        String temp = "\"postID\":" + this.postID+","
                    + "\"ownerID\":" + this.ownerID+","
                    + "\"company\":\"" + this.company + "\","
                    + "\"jobTitle\":\""+this.jobTitle+"\","
                    + "\"description\":\""+this.description+"\","
                    +"\"questions\":[";

        StringBuilder builder = new StringBuilder();

//        Iterator iterator =
//        while(questions);

        return temp;
    }

}

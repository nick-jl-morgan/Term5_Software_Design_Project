package com.quickhire.quickhire;

import android.app.Application;

import com.quickhire.quickhire.User;
import com.quickhire.quickhire.jobPosting;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;


/**
 * Created by onick on 2018-03-04.
 */

public interface DatabaseConnection {

    public credentials registerUser(String username, String password);
    //public jobPosting getJobPosting(String postID);
    //public boolean saveApplication(jobApplication Application);

   // public credentials loginUser(String username, String password);
    
}

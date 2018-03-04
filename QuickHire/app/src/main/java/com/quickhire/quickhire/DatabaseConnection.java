package com.quickhire.quickhire;


import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;


/**
 * Created by onick on 2018-03-04.
 */

public interface DatabaseConnection {

    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("http://192.168.0.27:5000/API/")
            .build();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public String GetUser(String username, String password);

    public String doPostRequest(String json) throws IOException;


}

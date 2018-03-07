package com.quickhire.quickhire;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Created by onick on 2018-03-06.
 */

class OkhttpDataBase  implements DatabaseConnection  {

    OkHttpClient client = new OkHttpClient();
    String lastRequest = "";

    public final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private String postUrl = "http://192.168.0.10:5000/API/registration";

    public static String bodyJson(String username, String password) {
        return "{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";
    }

    public String doPostRequest(String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public credentials registerUser(String username, String password) {
        String JSON ="{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";;
        ConnectionTask task = new ConnectionTask(JSON, this);
        this.lastRequest = "RegisterUser";

        return null;
    }

    public void notifyDone(String response){

    }

}

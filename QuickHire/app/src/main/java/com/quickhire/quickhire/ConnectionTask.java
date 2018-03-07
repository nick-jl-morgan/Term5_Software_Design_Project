package com.quickhire.quickhire;

/**
 * Created by onick on 2018-03-06.
 */

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class ConnectionTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    public static String JSONTEXT = null;
    private OkhttpDataBase db;
    public final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = new OkHttpClient();

    String Response;

    private String postUrl = "http://192.168.0.27:5000/API/registration";

    ConnectionTask(String json, OkhttpDataBase db) {
        this.JSONTEXT=json;
        this.db = db;

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
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        String json = this.JSONTEXT;
        try {
            this.Response = doPostRequest(json);
            System.out.println(this.Response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

//        for (String credential : DUMMY_CREDENTIALS) {
//            String[] pieces = credential.split(":");
//            if (pieces[0].equals(mEmail)) {
//                // Account exists, return true if the password matches.
//                return pieces[1].equals(mPassword);
//            }
//        }

        // TODO: register the new account here.
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
//        mAuthTask = null;
//        showProgress(false);

//        if (success) {
//            RegisterLoginActivity2.finish();
//        } else {
//            mPasswordView.setError(getString(R.string.error_incorrect_password));
//            mPasswordView.requestFocus();
//        }
        db.notifyDone(this.Response);
    }

    @Override
    protected void onCancelled() {
//        mAuthTask = null;
//        showProgress(false);
    }
}

package com.quickhire.quickhire;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;


/**
 * Created by matth_000 on 2018-03-07.
 */

public class connection{

    public static Context appcontext;       //Lifespan = lifepan of app.
    private String URL;
    private RequestQueue queue = null;
    private static connection singleConnection = null;  //This is a singleton class
    private boolean external = false;

    //Constructor for default IP of server
    private connection(Context ctx){
        this.URL="http://99.253.59.150/API/"; //"http://192.168.0.10:5000/API/"; //local
        this.appcontext=ctx;
        this.queue = Volley.newRequestQueue(ctx);
    }

    //Con
    private connection(Context ctx,  String URL){
        this.URL=URL;
        this.appcontext=ctx;
        this.queue = Volley.newRequestQueue(ctx);
    }

    public synchronized static boolean createinitialConnection(Context ctx){
        if(singleConnection==null){
            singleConnection= new connection(ctx);
            return true;
        } else {
            return false;
        }
    }

    protected synchronized static connection getConnection() {
        return singleConnection;
    }


    protected void registerUser(String username, String password, Response.Listener<JSONObject> responseListener, Response.ErrorListener err) {

        String JSON="{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";
        String extension = "registration";

        generic(JSON, extension, responseListener, err);
    }

    protected void searchJobPosting(String id, Response.Listener<JSONObject> responseListener, Response.ErrorListener err) {

        String JSON="{\"AccessCode\":\"" + id + "\"}";
        String extension = "getPostingFromAccessCode";

        //In order to add additional onResponse functionality if desired.

        generic(JSON, extension, responseListener, err);
    }

    protected void loginUser(String username, String password, final Response.Listener<JSONObject> responseListener, Response.ErrorListener err) {

        String JSON="{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";
        String extension = "login";

        //In order to add additional onResponse functionality if desired.
        Response.Listener<JSONObject> r =new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseListener.onResponse(response);
                //DoStuff
            }
        };

        generic(JSON, extension, r, err);
    }

    protected void saveJobPosting(jobPosting posting, Response.Listener<JSONObject> responseListener, Response.ErrorListener err){
        String JSON = posting.toJSON();
        String extension = "AddPosting";
        generic(JSON, extension, responseListener, err);
    }

    protected void saveApplication(Application application, Response.Listener<JSONObject> responseListener, Response.ErrorListener err){
        String JSON = application.toJSON();
        String extension = "submitApplication";
        generic(JSON, extension, responseListener, err);
    }


    /**
     * This method is the main workhorse of the connection class. From here, all HTTP requests are finalized and added to the request queue.
     *
     * @param JSON the body of the HTTP request.
     * @param urlattachment which API is being hit
     * @param responseListener defines the bahaviour to be undertaken when a response is recieved.
     * @param err defines the behaviour to be undertaken when an erronious reponse is recieved.
     */
    private void generic(String JSON, String urlattachment, Response.Listener<JSONObject> responseListener, Response.ErrorListener err){
        JsonObjectRequest request=null;
        //String url = "http://99.253.59.150/API/registration";
        final Response.ErrorListener nextLayerError=err;

        JSONObject jsonObj = null;
        try{
            jsonObj = new JSONObject(JSON);
            request = new JsonObjectRequest(Request.Method.POST, this.URL + urlattachment, jsonObj,responseListener,err){
                @Override public Map<String, String> getHeaders() throws AuthFailureError{
                    Map<String, String> headers = new HashMap<String, String>();
//                    Map<String, String> headers = super.getHeaders();
                    if(User.getUser() != null) {
                        String credentials = User.getUser().getCredentials();
                        headers.put("Authorization", ("Bearer " + credentials));
                    }
                    return headers;
                }
            };
        }catch(Exception e){
            e.printStackTrace();
        }

        if(request != null){
            request.setRetryPolicy(new DefaultRetryPolicy(20*1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        } else {
            Log.d("ERROR", "Request NULL");
            return;
        }

    }
    //An intermediary function which allows other classes to bypass the intermediary functions if and only if the feature has been enabled.
    protected void publicgeneric(String JSON, String urlattachment, Response.Listener<JSONObject> responseListener, Response.ErrorListener err){
        if(this.external){  //Security feature
            generic(JSON,urlattachment,responseListener,err);
        }
    }
    protected void disableExternal(){this.external=false;}

    //Enabled third party upload and creates a new thread which handles video streaming to server.
    public void uploadVideo(videoAnswer answer){
        external=true;
        new videoStreamer().execute(answer);
    }
}


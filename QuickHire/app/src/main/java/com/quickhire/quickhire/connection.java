package com.quickhire.quickhire;

import android.content.Context;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matth_000 on 2018-03-07.
 */

public class connection{

    private Context appcontext;
    private String URL;
    private RequestQueue queue = null;
    private static connection singleConnection = null;

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
}


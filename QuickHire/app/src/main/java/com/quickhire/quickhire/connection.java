package com.quickhire.quickhire;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
        this.URL="http://192.168.0.10:5000/API/";
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


    protected void registerUser(String username, String password, Response.Listener<JSONObject> responseListener) {

        String JSON="{\"username\":\"" + username + "\","
                + "\"password\":\"" + password + "\"}";

        generic(JSON, responseListener);
    }

    protected void saveJobPosting(jobPosting posting, Response.Listener<JSONObject> responseListener){
        String JSON = posting.toJSON();
        generic(JSON, responseListener);
    }

    private void generic(String JSON,Response.Listener<JSONObject> responseListener){
        JsonObjectRequest request=null;
        String url = "http://99.253.59.150/API/registration";

        JSONObject jsonObj;
        try{
            jsonObj = new JSONObject(JSON);
            request = new JsonObjectRequest(Request.Method.POST, url, jsonObj,responseListener, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print(error.getMessage());
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        if(request != null){
            queue.add(request);
        } else {
            Log.d("ERROR", "Request NULL");
            return;
        }


    }
}


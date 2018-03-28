package com.quickhire.quickhire;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by matth_000 on 2018-03-24.
 */

class Authenticator{
    static private connection c = connection.getConnection();

    public static void attemptRegistration(String username, String password, String name, Response.Listener<JSONObject> rl,  Response.ErrorListener err){
        final String u = username;
        final Response.Listener<JSONObject> nl=rl;
        final Response.ErrorListener ne = err;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            private Response.Listener<JSONObject> nextLayerListener=nl;

            @Override
            public void onResponse(JSONObject response) {
                User.createUser(u);
                Gson g = new Gson();
                credentials creds = g.fromJson(response.toString(), credentials.class);
                User.getUser().setCreds(creds);

                nextLayerListener.onResponse(response);
            }
        };
        Response.ErrorListener errorListener =new Response.ErrorListener() {

            private Response.ErrorListener nextLayerError = ne;
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO error response handling for specfic error messages!
                //if(message == "500")

                nextLayerError.onErrorResponse(error);
            }
        };
        c.registerUser(username, password, name, listener, errorListener);
    }

    public static void attemptLogin(String username, String password, Response.Listener<JSONObject> rl, Response.ErrorListener err){
        final String u = username;
        final Response.Listener<JSONObject> nl=rl;
        final Response.ErrorListener ne = err;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            private Response.Listener<JSONObject> nextLayerListener=nl;

            @Override
            public void onResponse(JSONObject response) {
                User.createUser(u);
                Gson g = new Gson();
                credentials creds = g.fromJson(response.toString(), credentials.class);
                User.getUser().setCreds(creds);

                nextLayerListener.onResponse(response);
            }
        };
        Response.ErrorListener errorListener =new Response.ErrorListener() {

            private Response.ErrorListener nextLayerError = ne;
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO error response handling for specfic error messages!
                //if(message == "500")

                nextLayerError.onErrorResponse(error);
            }
        };
        c.loginUser(username,password,listener, errorListener);
    }
}

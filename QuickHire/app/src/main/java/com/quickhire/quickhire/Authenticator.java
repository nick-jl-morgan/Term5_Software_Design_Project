package com.quickhire.quickhire;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by matth_000 on 2018-03-24.
 * This class is responsible for creating the user class and ensuring the validty of login/registration.
 * The class acts as a middleman between the registerlogin activity, the connection class, and the User class.
 *
 */

class Authenticator{
    static private connection c = connection.getConnection();

    //This method is passed the user's username and password, a response listener and error listener that define the frontend behaviour for registration.
    // It also creates new listeners that define take care of user instantiation before calling the other listeners to take care of UI response.
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

    //This method is passed the user's username and password, a response listener and error listener that define the frontend behaviour for login.
    // It also creates new listeners that define take care of user instantiation before calling the other listeners to take care of UI response.
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

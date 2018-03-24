package com.quickhire.quickhire;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by matth_000 on 2018-03-24.
 */

class Authenticator {
    static private connection c = connection.getConnection();

    public static void attemptRegistration(String username, String password){
        final String u = username;
        c.registerUser(username, password, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User.createUser(u);
                Gson g = new Gson();
                credentials creds = g.fromJson(response.toString(), credentials.class);
                User.getUser().setCreds(creds);
                RegisterLoginActivity.myActivity.finish();
            }
        } );
    }

    public static void attemptLogin(String username, String password){


    }
}

package com.quickhire.quickhire;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by matth_000 on 2018-03-24.
 */

class Authenticator {
    static private connection c = connection.getConnection();

    public static void attemptRegistration(String username, String password){

        c.registerUser(username, password, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                RegisterLoginActivity.myActivity.finish();
            }
        } );
    }

    public static void attemptLogin(String username, String password){


    }
}

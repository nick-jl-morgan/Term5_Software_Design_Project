package com.quickhire.quickhire;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterLoginActivity extends AppCompatActivity{


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mTxtDisplay;

    public static Activity myActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        myActivity=this;

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button registerButton = (Button) findViewById(R.id.email_sign_in_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button loginButton = (Button) findViewById(R.id.sign_in_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                mTxtDisplay.setText("Test");
            }
        });



        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mTxtDisplay = (TextView) findViewById(R.id.editText4);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else{

            Response.Listener<JSONObject> r =new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    mTxtDisplay.setText("Registration Succesfull!");
                    try {
                        wait(2000);
                        myActivity.finish();
                    }
                    catch(java.lang.InterruptedException e){
                        myActivity.finish();
                    }
                }
            };
            Response.ErrorListener err =new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = error.getMessage();
                    mTxtDisplay.setText(message);
                    //TODO error response handling for specfic error messages!
                    //if(message == "500")
                }
            };
            Authenticator.attemptRegistration(email,password, r, err);

//           connection.getConnection().registerUser(email, password, new Response.Listener<JSONObject>() {
//
//                       @Override
//                       public void onResponse(JSONObject response) {
//                           myActivity.finish();
//                       }
//                   } );


//           connection.getConnection().registerUser(email, password, new Response.Listener<JSONObject>() {
//
//                       @Override
//                       public void onResponse(JSONObject response) {
//                           mTxtDisplay.setText("Response: " + response.toString());
//                           if(response != null){
//                               Gson g = new Gson();
//                               User.createUser(email);
//                               credentials creds = g.fromJson(response.toString(), credentials.class);
//                               User.getUser().setCreds(creds);
//                           }

//                            String AccessToken = response.getJSONObject("access_token").getString("access_token");
//                           User.getUser().setCreds();
//                           try {
//                               wait(15000);
//                           }catch(Exception e){
//                               e.printStackTrace();
//                           }
//                           myActivity.finish();
//                       }
//                   } );
        }

    }
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else{
            Response.Listener<JSONObject> r =new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    mTxtDisplay.setText("Login Successfull!");
                    try {
                        wait(2000);
                        myActivity.finish();
                    }
                    catch(java.lang.InterruptedException e){
                        myActivity.finish();
                    }
                }
            };
            Response.ErrorListener err =new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = error.getMessage();
                    mTxtDisplay.setText(message);
                    //TODO error response handling for specfic error messages!
                    //if(message == "500")
                }
            };
            Authenticator.attemptLogin(email,password, r, err);

        }

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    protected void onDestroy(){
        myActivity=null;        //this prevents potential memory leak introduced when assigning static instance of Activity class.
        super.onDestroy();
    }

}


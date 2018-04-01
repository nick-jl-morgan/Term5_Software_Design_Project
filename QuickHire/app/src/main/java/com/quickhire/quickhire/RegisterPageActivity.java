package com.quickhire.quickhire;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONObject;

/** RegisterLoginActivity ************************************************
 * Created by nick on 2018-03-27.
 * Description: register page to create a user.
 *********************************************************************/
public class RegisterPageActivity extends AppCompatActivity {
    //UI elements
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText passwordText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstNameText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameText = (EditText) findViewById(R.id.lastNameEditText);
        emailText = (EditText) findViewById(R.id.emailEditText);
        passwordText = (EditText) findViewById(R.id.passwordEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setText("Register");
        configureRegisterButton();
    }

    private void configureRegisterButton() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {

        // Reset errors.
        emailText.setError(null);
        passwordText.setError(null);

        // Store values at the time of the login attempt.
        final String firstName = firstNameText.getText().toString();
        final String lastName = lastNameText.getText().toString();
        final String name = firstName + " " + lastName;
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordText.setError(getString(R.string.error_invalid_password));
            focusView = passwordText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailText.setError(getString(R.string.error_field_required));
            focusView = emailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailText.setError(getString(R.string.error_invalid_email));
            focusView = emailText;
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
                    finish();
                }
            };
            Response.ErrorListener err =new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO error response handling for specfic error messages!
                    //if(message == "500")
                }
            };
            Authenticator.attemptRegistration(email,password,name, r, err); //register the user into the database
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

}

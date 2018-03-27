package com.quickhire.quickhire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

public class homeActivity extends AppCompatActivity {

    private EditText searchPostingText;
    public static Activity activity = null;
    public static jobPosting posting = null;
    public postingResponse postResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchPostingText = (EditText) findViewById(R.id.searchJobPostingText);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity=this;
        configureCreatePostButton();
        configureSearchPostButton();
    }

        private void configureCreatePostButton(){
            Button postButton = (Button) findViewById(R.id.createPostButton);
            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  startActivity(new Intent(homeActivity.this, CreateJobPosting2.class));
                }
            });
        }

        private void configureSearchPostButton(){
            final Button searchPostButton = (Button) findViewById(R.id.searchPostButton);
            searchPostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = searchPostingText.getText().toString();
//                    posting = new jobPosting("test", "test", "test");
//                    Question test = new essayQuestion("answer this?");
//                    posting.addQuestion(test);
//                    startActivity(new Intent(homeActivity.this, ApplyActivity.class));
                    connection.getConnection().searchJobPosting(id, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //Do something
                            homeActivity.display(response.toString());
                            Gson g = new Gson();
                            postResponse = g.fromJson(response.toString(), postingResponse.class);
                            posting = postResponse.convert();
                            startActivity(new Intent(homeActivity.this, ApplyActivity.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
            });
        }

    public static void display(String message){
        new AlertDialog.Builder(homeActivity.activity)
                .setTitle("Server Respose")
                .setMessage(message)
                .setCancelable(true)
                .show();
    }

    @Override
    protected void onDestroy(){
        activity = null;
        posting = null;
        super.onDestroy();
    }
}


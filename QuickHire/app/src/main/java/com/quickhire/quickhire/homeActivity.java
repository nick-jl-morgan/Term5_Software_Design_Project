package com.quickhire.quickhire;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.io.InputStream;

/** homeActivity ************************************************
 * Created by matt on 2018-03-27.
 * Description: This should be the most frequently visited activity.
 *              From here, the user creates post, searches for posts,
 *              and may check that their camera is compatable
 *********************************************************************/
public class homeActivity extends AppCompatActivity {
    File test;
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

        activity=this;      //Allows the activity to be referenced from the outside.

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
                        public void onResponse(JSONObject response) { //allows to search for a job posting from a ID
                            //Do something
//                            homeActivity.display(response.toString());
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

    //Prevent memory leaks
    @Override
    protected void onDestroy(){
        this.activity = null;
        this.posting = null;
        super.onDestroy();
    }
}


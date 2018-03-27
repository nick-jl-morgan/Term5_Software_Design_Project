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
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApplyActivity extends AppCompatActivity {

    private TextView jobTitle;
    private TextView jobCompany;
    private TextView jobDescription;
    public static jobPosting posting = homeActivity.posting;
    public static Activity activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity=this;

        jobTitle = (TextView)findViewById(R.id.applyJobTitleText);
        jobCompany = (TextView)findViewById(R.id.applyCompanyText);
        jobDescription = (TextView)findViewById(R.id.applyDescriptionText);
        jobTitle.setText(posting.jobTitle);
        jobCompany.setText(posting.company);
        jobDescription.setText(posting.description);
        configureJobQuestionButton();
        configureSaveApplication();
    }

    private void configureSaveApplication() {
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Application apply = posting.toapplication();
               String error = null;
               for(Answer a : apply.answers){
                   if(a.getAnswer() == ""){
                       error = "Please answer all questions";
                   }
               }
               if(error == null){
                   connection.getConnection().saveApplication(apply, new Response.Listener<JSONObject>() {

                       @Override
                       public void onResponse(JSONObject response) {
                           //Do something
                           ApplyActivity.display(response.toString());
                           activity.finish();
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {

                       }
                   });
               } else {
                   ApplyActivity.display(error);
               }
            }
        });
    }

    private void configureJobQuestionButton() {
        Button postButton = (Button) findViewById(R.id.applicationQuestionButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApplyActivity.this, applyQuestionList.class));
            }
        });
    }

    public static void display(String message){
        new AlertDialog.Builder(ApplyActivity.activity)
                .setTitle("Server Respose")
                .setMessage(message)
                .setCancelable(true)
                .show();
    }

    @Override protected void onDestroy(){
        ApplyActivity.activity=null;
        super.onDestroy();
    }
}


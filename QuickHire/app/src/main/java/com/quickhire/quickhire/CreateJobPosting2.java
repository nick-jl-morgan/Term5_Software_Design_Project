package com.quickhire.quickhire;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateJobPosting2 extends AppCompatActivity {

    Button SaveButton;
    EditText JobTitle;
    EditText Company;
    EditText Description;
    jobPosting post;
    public static List<Question> questionsList = new ArrayList<>();
    public static Activity activity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_posting2);


        activity=this; //important

        addKeyLister();
        configureCreatePostButton();
    }

    private void configureCreatePostButton() {
        Button postButton = (Button) findViewById(R.id.questions);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateJobPosting2.this, questionList.class));
            }
        });
    }


    private void addKeyLister() {
        JobTitle = (EditText) findViewById(R.id.JobTitleText);
        Company = (EditText) findViewById(R.id.CompanyText);
        Description = (EditText) findViewById(R.id.DescriptionText);
        SaveButton = (Button) findViewById(R.id.SaveButton);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(JobTitle.getText().toString().trim().length() != 0 && Company.getText().toString().trim().length() != 0 && Description.getText().toString().trim().length() != 0){
                    final Snackbar mySnackbar = Snackbar.make(view, "Post Sent", Snackbar.LENGTH_SHORT);

                    post = new jobPosting(Company.getText().toString(), JobTitle.getText().toString(), Description.getText().toString());

                    Iterator<Question> i = questionsList.iterator();

                    while(i.hasNext()){
                        post.addQuestion(i.next()); //Add questions to the posting
                    }

                    connection.getConnection().saveJobPosting(post, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //Do something
                            mySnackbar.show();
                            CreateJobPosting2.display(response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                } else {
                    final Snackbar mySnackbar = Snackbar.make(view, "Texts boxes are not full", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                }
            }
        });
    }

    public static void display(String message){
        new AlertDialog.Builder(CreateJobPosting2.activity)
                .setTitle("Server Respose")
                .setMessage(message)
                .setCancelable(true)
                .show();
    }

    @Override protected void onDestroy(){
        CreateJobPosting2.activity=null;
        super.onDestroy();
    }
}
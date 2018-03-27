package com.quickhire.quickhire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ApplyActivity extends AppCompatActivity {

    private TextView jobTitle;
    private TextView jobCompany;
    private TextView jobDescription;
    public static jobPosting posting = homeActivity.posting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jobTitle = (TextView)findViewById(R.id.applyJobTitleText);
        jobCompany = (TextView)findViewById(R.id.applyCompanyText);
        jobDescription = (TextView)findViewById(R.id.applyDescriptionText);
        jobTitle.setText(posting.jobTitle);
        jobCompany.setText(posting.company);
        jobDescription.setText(posting.description);
        configureJobQuestionButton();
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

}

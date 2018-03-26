package com.quickhire.quickhire;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configureCreatePostButton();
        configureTestVideoButton();
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

        private void configureTestVideoButton(){
            Button videoButton = (Button) findViewById(R.id.testVideoButton);
            videoButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(takeVideoIntent, 1);
                }
            });
        }

}

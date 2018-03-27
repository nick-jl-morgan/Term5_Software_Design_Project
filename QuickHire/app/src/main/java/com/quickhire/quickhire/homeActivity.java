package com.quickhire.quickhire;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;
import java.io.InputStream;

public class homeActivity extends AppCompatActivity {
    private VideoView videoV;
    File test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configureCreatePostButton();
        configureTestVideoButton();
        videoV = (VideoView) findViewById(R.id.videoView);
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
                    takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,60);
                    startActivityForResult(takeVideoIntent, 1);
                }
            });
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 1){
                if(resultCode == Activity.RESULT_OK){
                    Uri video = data.getData();
                    videoV.setVideoURI(video);
                    videoV.start();
//                    test = new File(video.getPath());
                   connection.getConnection().uploadVideo(video);
                }
            }
        }

}

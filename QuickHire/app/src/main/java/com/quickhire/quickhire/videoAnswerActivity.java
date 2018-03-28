package com.quickhire.quickhire;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class videoAnswerActivity extends AppCompatActivity {

    private Camera camera;
    private TextView textView,auxTextView;
    private boolean valid =false;
    private Uri video = null;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_answer);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            valid =false;

            configureCaptureButton();
            configureSaveButton();
            
            textView = (TextView) findViewById(R.id.questionText);
            textView.setText("Question text will appear after hitting the 'Record' button.");
            auxTextView=(TextView) findViewById(R.id.auxText);
            auxTextView.setText("Take note of the question once displayed.");
        }

    private void configureSaveButton(){
            Button saveButton = (Button) findViewById(R.id.saveButton);
            saveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(valid) {
//                        Intent returnIntent = new Intent();
//                        returnIntent.putExtra("video", video );
//                        setResult(RESULT_OK, returnIntent);
                       videoQuestion q = (videoQuestion) applyQuestionList.selectedQuestion;
                       q.setvideo(video);
                    }
                    finish();
                }
            });

    }
    private void configureCaptureButton(){
        Button captureButton = (Button) findViewById(R.id.recordButton);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                textView.setText(applyQuestionList.selectedQuestion.getQuestionText());
                auxTextView.setText("Recording will begin in 10 Seconds.");
                try{
                    wait(10000);
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
                    startActivityForResult(takeVideoIntent, 1);
                }catch(Exception e){
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
                    startActivityForResult(takeVideoIntent, 1);
                }
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            video = data.getData();
            valid=true;
            auxTextView.setText("Hit 'Save' to continue.");
        }
    }

}

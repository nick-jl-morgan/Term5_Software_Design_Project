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
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;

/*******************
 * Created by matth_000
 * This activity is used to record the video responses and then begins their upload.
 * As the name may imply, that was not the original intent of this activity.
 * ***********************/
public class videoTester extends AppCompatActivity {
    private VideoView videoV;
    private TextView question;
    private videoQuestion q = (videoQuestion) applyQuestionList.selectedQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tester);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        videoV = (VideoView) findViewById(R.id.videoView);
        question = (TextView) findViewById(R.id.textView2);
        question.setText(q.questionText);
        configureTestVideoButton();

    }
    private void configureTestVideoButton() {
        Button videoButton = (Button) findViewById(R.id.openCamera);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                takeVideoIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION,true);

                startActivityForResult(takeVideoIntent, 1);
            }
        });
    }
    //This is where the video is returned from the devices default video app.
    //The videoquestion is marked as repsponded to and the video begins transmission immediately because it is a very long process.
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Uri video = data.getData();
                videoV.setVideoURI(video);
                videoV.start();


                videoAnswer a=new videoAnswer(video,q.getId());
                a.setApplicationID(ApplyActivity.posting.getAppIdToUse());
                a.beginTransmitting();
            }
        }
    }
}

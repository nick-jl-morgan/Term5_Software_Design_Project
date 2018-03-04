package com.quickhire.quickhire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureCreatePostButton();
        configureRegisterButton();

    }
    private void configureCreatePostButton() {
        Button postButton = (Button) findViewById(R.id.createJobPostingButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreatePostingActivity2.class));
            }
        });
    }

    private void configureRegisterButton() {
        Button postButton = (Button) findViewById(R.id.registerLoginButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterLoginActivity.class));
            }
        });
    }
}


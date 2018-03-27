package com.quickhire.quickhire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class essayAnswerActivity extends AppCompatActivity {

    private TextView essayQuestionText;
    private Button saveEssayAnswer;
    private EditText essayAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        essayQuestionText = (TextView) findViewById(R.id.essayQuestionText);
        essayAnswer = (EditText) findViewById(R.id.essayAnswerText);
        configureSaveEssayButton();
    }

    private void configureSaveEssayButton() {
        saveEssayAnswer = (Button) findViewById(R.id.saveEssayAnswerButton);
        saveEssayAnswer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String answer = essayAnswer.getText().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("answer", answer);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}

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

public class essayQuestionActivity extends AppCompatActivity {

    private EditText questionText;

    public String text = questionList.questionText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questionText = (EditText) findViewById(R.id.questionText);
        questionText.append(text);

        Button saveQuestion = (Button) findViewById(R.id.saveEssayQuestion);

        saveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changedQuestion = questionText.getText().toString();
//                selectedQuestion.setText(changedQuestion);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("question", changedQuestion);
//                returnIntent.putExtra("position", selectedQuestion.getPosition());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


    }
}

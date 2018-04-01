package com.quickhire.quickhire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/** essayAnswerActivity ************************************************
 * Created by nick on 2018-03-27.
 * Description: Activity to show the UI for answering a essay question.
 *********************************************************************/
public class essayAnswerActivity extends AppCompatActivity {
    private TextView essayQuestionText;
    private Button saveEssayAnswer;
    private EditText essayAnswer;
    private String question = applyQuestionList.questionText;
    private Question selected = applyQuestionList.selectedQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        essayQuestionText = (TextView) findViewById(R.id.essayQuestionText);
        essayQuestionText.setText(question);
        essayAnswer = (EditText) findViewById(R.id.essayAnswerText);
        if(selected.getAnswer() != null){
            essayAnswer.append(selected.getAnswer());
        }
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

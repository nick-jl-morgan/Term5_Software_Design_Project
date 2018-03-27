package com.quickhire.quickhire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class videoQuestionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText questionText;

    private Spinner time;

    public String text = questionList.questionText;

    public int initialTime = questionList.videoTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        time = (Spinner) findViewById(R.id.video_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.video_time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(adapter);

        String intialSpinner = Integer.toString(initialTime) + " Seconds";
        int spinnerPosition = adapter.getPosition(intialSpinner);
        time.setSelection(spinnerPosition);


        questionText = (EditText) findViewById(R.id.questionTextVideo);
        questionText.append(text);

        Button saveQuestion = (Button) findViewById(R.id.saveVideoQuestion);

        saveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeValueSpinner = time.getSelectedItem().toString();
                String changedQuestion = questionText.getText().toString();
                String test = new StringBuilder().append(timeValueSpinner.charAt(0)).append(timeValueSpinner.charAt(1)).toString();
//                selectedQuestion.setText(changedQuestion);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("question", changedQuestion);
                returnIntent.putExtra("time", test);
//                returnIntent.putExtra("position", selectedQuestion.getPosition());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

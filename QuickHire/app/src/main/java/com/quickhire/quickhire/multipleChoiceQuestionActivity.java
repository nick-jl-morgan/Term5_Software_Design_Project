package com.quickhire.quickhire;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class multipleChoiceQuestionActivity extends AppCompatActivity {
    private ArrayList<String> element;
    private ArrayAdapter<String> adapter;
    private EditText question;
    private EditText option;
    private ListView listOptions;
    private Button add;
    private Button save;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_multiple_choice_question);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        listOptions = (ListView) findViewById(R.id.listViewMultipleChoice);
//        add = (Button) findViewById(R.id.addButton);
//        save = (Button) findViewById(R.id.saveMultipleChoiceQuestion);
//        question = (EditText) findViewById(R.id.questionText);
//        option = (EditText) findViewById(R.id.optionsText);
//
//        element = new ArrayList<String>();
//        adapter = new ArrayAdapter<String>(multipleChoiceQuestionActivity.this, R.layout.list_multiplechoice, R.id.textView5, element);
//        listOptions.setAdapter(adapter);
//        adapter.get
//        configureAddElementButton();
//    }
//
//    public void configureAddElementButton(){
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String result = option.getText().toString();
//                element.add(result);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }

}

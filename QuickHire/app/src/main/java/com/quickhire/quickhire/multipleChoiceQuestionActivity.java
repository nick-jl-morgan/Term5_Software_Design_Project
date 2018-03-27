package com.quickhire.quickhire;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class multipleChoiceQuestionActivity extends AppCompatActivity {
    private ArrayList<EditText> element;
    private ArrayAdapter<EditText> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ListView listView = (ListView) findViewById(R.id.listViewMultipleChoice);
//        EditText text = (EditText)findViewById(R.id.elementMCText);
//        EditText[] items = {text};
//        element = new ArrayList<>((Arrays.asList(items)));
//        adapter = new ArrayAdapter<EditText>( )



    }

}

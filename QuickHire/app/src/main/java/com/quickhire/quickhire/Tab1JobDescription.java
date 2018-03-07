package com.quickhire.quickhire;

/**
 * Created by onick on 2018-03-04.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.security.Key;

public class Tab1JobDescription extends Fragment{

    Button SaveButton;
    EditText JobTitle;
    EditText Company;
    EditText Description;
    jobPosting post;
    String snack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.description_tab_create_posting, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JobTitle = (EditText) getView().findViewById(R.id.JobTitleText);
        Company = (EditText) getView().findViewById(R.id.CompanyText);
        Description = (EditText) getView().findViewById(R.id.DescriptionText);
        SaveButton = (Button) getView().findViewById(R.id.SaveButton);


        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(JobTitle.getText().toString().trim().length() == 0 && Company.getText().toString().trim().length() == 0 && Description.getText().toString().trim().length() == 0){
                    final Snackbar mySnackbar = Snackbar.make(view, "All Text okay", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();

                    post = new jobPosting(Company.getText().toString(), JobTitle.getText().toString(), Description.getText().toString());
                } else {
                    final Snackbar mySnackbar = Snackbar.make(view, "Texts boxes are not full", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                }
            }
        });
    }
}

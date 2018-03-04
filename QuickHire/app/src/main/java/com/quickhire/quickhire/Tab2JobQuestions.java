package com.quickhire.quickhire;

/**
 * Created by onick on 2018-03-04.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Tab2JobQuestions extends Fragment{

    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };

    String[] infoArray = {
            "8 tentacled monster",
            "Delicious in rolls",
            "Great for jumpers",
            "Nice in a stew",
            "Great for shoes",
            "Scary."
    };

    Integer[] imageArray = {R.drawable.ic_media_play,
            R.drawable.ic_media_play,
            R.drawable.ic_media_play,
            R.drawable.ic_media_play,
            R.drawable.ic_media_play,
            R.drawable.ic_media_play};

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_list_tab_create_posting, container, false);
        return rootView;
    }
}

package com.quickhire.quickhire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;
import java.util.Vector;

/** applyQuestionList ***************************************************************************
 * Created by nick on 2018-03-27.
 * Resource: www.androidhive.info
 * Description: Gmail UI to show a list of questions for the applicant to answer.
 ******************************************************************************************/
public class applyQuestionList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, questionAdapter.MessageAdapterListener {
    private jobPosting post = ApplyActivity.posting; //application the applicant is applying to
    private RecyclerView recyclerView;
    private questionAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;
    private Integer questionPosition;
    public static String questionText;
    public static int videoTime;
    public static Question selectedQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_question_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (selectedQuestion != null) {
            Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new questionAdapter(this, post.questions, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

//        actionModeCallback = new ActionModeCallback();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search...", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onIconClicked(int position) {
    }

    @Override
    public void onIconImportantClicked(int position) {

    }

    @Override
    public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter.getSelectedItemCount() > 0) {
//            enableActionMode(position);
        } else {
            // read the message which removes bold from the row
            Question question = post.questions.get(position);
            questionPosition = position;
            questionText = question.getQuestionText();
            selectedQuestion = question;
            if (question.getType() == "Essay") {
                startActivityForResult(new Intent(applyQuestionList.this, essayAnswerActivity.class), 1);
            } else if (question.getType() == "Video") {
                videoTime = ((videoQuestion) question).getTime();
                startActivityForResult(new Intent(applyQuestionList.this, videoTester.class), 2);
            }
            Toast.makeText(getApplicationContext(), "Read: " + question.getType(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRowLongClicked(int position) {

    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String result = data.getStringExtra("answer");
            Question question = post.questions.get(questionPosition);
            question.setAnswer(result);
            mAdapter.removeData(questionPosition);
            post.questions.add(questionPosition,question);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
//            Uri result = (Uri) data.getExtras().getSerializable("video");
//            Question question = post.questions.get(questionPosition);
//            ((videoQuestion)question).setvideo(result);
        }
    }
}


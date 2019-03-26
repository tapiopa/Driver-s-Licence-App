package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultsActivity extends AppCompatActivity  {
    private TextView mTextMessage;
    // --Commented out by Inspection (19/03/2019, 10.09):public ArrayList<Question> questions = new ArrayList<>();
    // --Commented out by Inspection (19/03/2019, 10.09):public ArrayList<Answer> answers = new ArrayList<>();
    private MyDBHandler db;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_exam:
                    mTextMessage.setText(R.string.title_exam);
                    return true;
                case R.id.navigation_results:
                    mTextMessage.setText(R.string.title_results);
                    return true;
            }
            return false;
        }
    };

//    public void buttonToExam(View view) {
//        Log.i("BOTTOM NAVIGATION", "to exam button clicked");
//        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
//        intent.putExtra("extra", "extra");
//        startActivity(intent);
//    }
//
//    public void buttonToResults(View view) {
//        Log.i("BOTTOM NAVIGATION", "to results button clicked");
//        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
//        intent.putExtra("extra", "extra");
//        startActivity(intent);
//    }

    public void toHome(MenuItem menuItem) {
        Log.i("BOTTOM NAVIGATION", "to home navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void toExam(MenuItem menuItem) {
        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }

    public void toResults(MenuItem menuItem) {
        Log.i("BOTTOM NAVIGATION", "to results navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_results);

        final ListView resultsList = findViewById(R.id.results_list);
        final TextView emptyResults  = findViewById(R.id.empty_result);
        final Button newExamButton = findViewById(R.id.new_exam_button);
        final TextView scoreText = findViewById(R.id.score_text);



        db = new MyDBHandler(this);
//        db.onCreate(db.getWritableDatabase());
//        db.getWritableDatabase();
//        Log.i("DB", "going for questions");
//        questions = db.loadQuestions();
//        answers = db.loadAllAnswers();
//        db.deleteAllChoices();

//        for (Answer an : answers) {
//            Log.i("ANSWER", an.getAnswerString());
//        }


        final ArrayList<Choice> choices = db.loadAllChoices();

        final int numberOfQuestions = db.countNumberOfQuestions();
        final int numberOfRightAnswers = db.countRightChoices();

        if (choices == null || choices.isEmpty()) {
            emptyResults.setVisibility(View.VISIBLE);
            resultsList.setVisibility(View.GONE);
            newExamButton.setText(R.string.button_take_exam);
            scoreText.setVisibility(View.GONE);
        } else {
            final ArrayAdapter arrayAdapter = new ChoiceAdapter(this, choices);
            emptyResults.setVisibility(View.GONE);
            resultsList.setVisibility(View.VISIBLE);
            resultsList.setAdapter(arrayAdapter);
            newExamButton.setText(R.string.button_take_new_exam);
            scoreText.setVisibility(View.VISIBLE);
            String score = getString(R.string.title_score) + " " + LanguageHelper.convertNumber(numberOfRightAnswers, this) + " / " +
                    LanguageHelper.convertNumber(numberOfQuestions, this);
            scoreText.setText(score);
        }



//        for (Choice choice : choices) {
//            Log.i("RESULTS", "Question: " + choice.getQuestion().getQuestionString());
//            Log.i("RESULTS", "Answer: " + choice.getAnswer().getAnswerString());
//            Log.i("RESULTS", "Answer is right: " + (choice.getAnswer().isRightAnswer() != 0));
//        }

    }

    public void takeNewExam (View view) {
        Log.i("RESULTS", "Take Exam clicked");
        db.deleteAllChoices();
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        startActivity(intent);
    }

}
package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class ResultsActivity extends AppCompatActivity  {
//    private TextView mTextMessage;
    private MyDBHandler db;
    private boolean finnish = false;

//    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_exam:
//                    mTextMessage.setText(R.string.title_exam);
//                    return true;
////                case R.id.navigation_exam_se:
////                    return true;
//                case R.id.navigation_results:
//                    mTextMessage.setText(R.string.title_results);
//                    return true;
//            }
//            return false;
//        }
//    };

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

    //Go to home page
    public void toHome(View view) {
        Log.i("BOTTOM NAVIGATION", "to home navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //Go to Finnish exam page
    public void toExam(View view) {
        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", true);
        startActivity(intent);
    }

    //Go to Finnish or Swedish exam page
    public void toExamFiSe(View view) {
        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        boolean finnish = db.isFinnishQuestions();
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", finnish);
        startActivity(intent);
    }

    //Go to new Finnish exam
    public void takeNewExam (View view) {
        Log.i("RESULTS", "Take Exam clicked");
        db.deleteAllChoices();
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", true);
        startActivity(intent);
    }

    //Go to new Swedish exam
    public void takeNewExamSe (View view) {
        Log.i("RESULTS", "Take exam Se clicked");
        db.deleteAllChoices();
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", false);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar myToolbar = findViewById(R.id.toolbar_results);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
//            ab.setTitle("Driver's App");
            ab.setSubtitle(R.string.title_results);
        }

        finnish = getIntent().getBooleanExtra("finnish", finnish);

        final ListView resultsList = findViewById(R.id.results_list);
        final TextView emptyResults  = findViewById(R.id.empty_result);
        final Button newExamFIButton = findViewById(R.id.button_new_exam_FI);
        final Button newExamSEButton = findViewById(R.id.button_new_exam_SE);
        final Button changeAnswers = findViewById(R.id.button_change_exam);
        final TextView scoreText = findViewById(R.id.score_text);

        db = new MyDBHandler(this);

        final ArrayList<Choice> choices = db.loadAllChoices(finnish);

        final int numberOfQuestions = db.countNumberOfQuestions(finnish);
        final int numberOfRightAnswers = db.countRightChoices();

        //If there are no results, show a text view with a message to take test. Else show results
        if (choices == null || choices.isEmpty()) {
            emptyResults.setVisibility(View.VISIBLE);
            resultsList.setVisibility(View.GONE);
            newExamFIButton.setVisibility(View.VISIBLE);
            newExamSEButton.setVisibility(View.VISIBLE);
            changeAnswers.setVisibility(View.GONE);
            scoreText.setVisibility(View.GONE);

        } else {
            final ArrayAdapter arrayAdapter = new ResultsAdapter(this, choices);
            emptyResults.setVisibility(View.GONE);
            resultsList.setVisibility(View.VISIBLE);
            resultsList.setAdapter(arrayAdapter);
            newExamFIButton.setVisibility(View.GONE);
            newExamSEButton.setVisibility(View.GONE);
            changeAnswers.setVisibility(View.VISIBLE);
            scoreText.setVisibility(View.VISIBLE);
            String score = getString(R.string.title_score) + " " + LanguageHelper.convertNumber(numberOfRightAnswers, this) + " / " +
                    LanguageHelper.convertNumber(numberOfQuestions, this);
            scoreText.setText(score);
        }
    }

    //Create language options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.language, menu);
        return true;
    }

    //Handle selecting a language
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.english:
                Log.i("LANGUAGE SELECTION", "english");
                setLocale("en");
                return true;
            case R.id.finnish:
                Log.i("LANGUAGE SELECTION", "finnish");
                setLocale("fi");
                return true;
            case R.id.swedish:
                Log.i("LANGUAGE SELECTION", "swedish");
                setLocale("sv");
                return true;
            case R.id.hungarian:
                Log.i("LANGUAGE SELECTION", "hungarian");
                setLocale("hu");
                return true;
            case R.id.nepali:
                Log.i("LANGUAGE SELECTION", "nepali");
                setLocale("ne");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Set locale for selected language
    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, ResultsActivity.class);
        startActivity(refresh);
        finish();
    }


}

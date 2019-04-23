package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
//import android.graphics.Color;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GestureDetectorCompat;
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
//import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;

//import org.jetbrains.annotations.Nullable;
//import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ExamActivity extends AppCompatActivity {
//    private TextView mTextMessage;

    private ArrayList<Question> questions = new ArrayList<>();
    //    private ArrayList<Answer> answers = new ArrayList<>();
    private ArrayList<Choice> choices = new ArrayList<>();
    private ArrayAdapter arrayAdapter;// = new AnswerAdapter(this, answers);
    // This is the gesture detector compat instance.
    private GestureDetectorCompat gestureDetectorCompat = null;

    private MyDBHandler db;
    private int currentQuestion = -1;

    private boolean finnish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        db = new MyDBHandler(this);

        Toolbar myToolbar = findViewById(R.id.toolbar_exam);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
//        // Enable the Up button
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
            String abTitle = "Driver's | ";
            if (finnish) {
                abTitle += getText(R.string.title_exam_fi);
            } else {
                abTitle += getText(R.string.title_exam_SE);
            }
            ab.setTitle(abTitle);
        }

        // Create a common gesture listener object.
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        // Set activity in the listener.
        gestureListener.setActivity(this);
        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

        finnish = getIntent().getBooleanExtra("finnish", finnish);
//        db.setIsFinnishQuestions(finnish);

        Log.i("EXAM", "country is: " + (finnish ? "Finnish" : "Swedish"));

        loadStuff(finnish);
        currentQuestion = db.getLastAnsweredQuestion() - 1;
        loadAnswersForQuestions();
        updateQuestionsWithChoices();
        nextQuestion(null);
    }

    //Detect swiping to go to next or previous question
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass activity on touch event to the gesture detector.
        gestureDetectorCompat.onTouchEvent(event);
        // Return true to tell android OS that event has been consumed, do not pass it to other event listeners.
        return true;
    }

    //Load questions and choices and initialize some variables and stuff...
    private void loadStuff(boolean finnish) {
        Log.i("EXAM", "loadStuff, finnish: " + finnish);
        questions = db.loadQuestions(finnish);
        choices = db.loadAllChoices(finnish);

        currentQuestion -= 2;
        if (currentQuestion < -1) {
            currentQuestion = -1;
        }
    }

    //Create language options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.language, menu);
        MenuItem language = menu.getItem(0);
        language.setTitle(R.string.button_results);
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
            case R.id.language_selection:
                toResults(null);
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
        Intent refresh = new Intent(this, ExamActivity.class);
        startActivity(refresh);
        finish();
    }

    //Load answers for questions
    private void loadAnswersForQuestions() {
        for (Question question : questions) {
            question.setAnswers(db.loadAnswersForQuestion(question.getQuestionID(), finnish));
//            for (Answer answer : question.getAnswers()) {
//
//            }

        }
    }

    //Start a new Finnish exam
    public void startNewExam(View view) {
        db.deleteAllChoices();
        currentQuestion = -1;
//        for (Question question : questions) {
//            question.setChosenAnswer(null);
//            question.setAnswered(false);
//        }
        finnish = true;
        loadStuff(finnish);
        db.setLastAnsweredQuestion(-1);
        nextQuestion(null);

    }

    //Start new Swedish exam
    public void startNewExamSe(View view) {
        db.deleteAllChoices();
        currentQuestion = -1;
//        for (Question question : questions) {
//            question.setChosenAnswer(null);
//            question.setAnswered(false);
//        }
        finnish = false;
        db.setLastAnsweredQuestion(-1);
        loadStuff(finnish);

        nextQuestion(null);

    }

    //Go to home page
    public void toHome(View view) {
//        Log.i("BOTTOM NAVIGATION", "to home navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    //Go to Finnish exam (for bottom navigation)
    public void toExam(View view) {
//        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", true);
        startActivity(intent);
    }

    //Go to Swedish exam (for bottom navigation)
    public void toExamSe(View view) {
//        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", false);
        startActivity(intent);
    }

    //Go to results page
    public void toResults(View view) {
//        Log.i("BOTTOM NAVIGATION", "to results navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        intent.putExtra("finnish", finnish);
        startActivity(intent);
    }

    public void toExamFiSe(View view) {
        if (finnish) {
            toExam(view);
        } else {
            toExamSe(view);
        }
    }
//
//    public void buttonToExam(View view) {
//        Log.i("BOTTOM NAVIGATION", "to exam button clicked");
//        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
//        intent.putExtra("extra", "extra");
//        startActivity(intent);
//    }

    //Go to results page (button in layout)
    public void buttonToResults(View view) {
        Log.i("BOTTOM NAVIGATION", "to results button clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        intent.putExtra("finnish", finnish);
        startActivity(intent);
    }

    //Initialize next question in exam
    public void nextQuestion(View view) {
        Log.i("EXAM", "Next question");
        Log.i("EXAM", "Current question: " + (currentQuestion + 1));
        Log.i("EXAM", "Questions size: " + questions.size());
        Button nextButton = findViewById(R.id.nextButton);
        Button previousButton = findViewById(R.id.backButton);
        currentQuestion = currentQuestion + 1;
        if (currentQuestion < 0) {
            currentQuestion = 0;
        }

        if (currentQuestion < questions.size() - 1) {
            nextButton.setEnabled(true);
            if (currentQuestion == 0) {
//                Log.i("EXAM", "Current question: " + (currentQuestion) + " is zero");
                previousButton.setEnabled(false);
            } else {
                previousButton.setEnabled(true);
            }
            showQuestion(findQuestion(currentQuestion));
        } else {
            //End of exam
//            Log.i("EXAM END", "Current question: " + (currentQuestion));
//            Log.i("EXAM END", "Questions size - 1: " + (questions.size() - 1));
            if (currentQuestion >= questions.size() - 1) {
                nextButton.setEnabled(false);
            }
            previousButton.setEnabled(true);
            int rightAnswers = db.countRightChoices();
            Log.i("RESULT", Integer.toString(rightAnswers));
            showQuestion(findQuestion(currentQuestion));
        }
    }

    //Initialize previous question in exam
    public void previousQuestion(View view) {
        Log.i("EXAM", "Previous question");

        Button previousButton = findViewById(R.id.backButton);
        Button nextButton = findViewById(R.id.nextButton);
        currentQuestion = currentQuestion - 1;
        if (currentQuestion < 0) {
            currentQuestion = 0;
        }
        Log.i("EXAM", "Current question: " + (currentQuestion));
        if (currentQuestion > 0) {
            Log.i("EXAM", "Current question is > 0");
            previousButton.setEnabled(true);
            if (currentQuestion >= questions.size() - 1) {
                nextButton.setEnabled(false);
            } else {
                nextButton.setEnabled(true);
            }
            showQuestion(findQuestion(currentQuestion));
        } else {
            previousButton.setEnabled(false);
            nextButton.setEnabled(true);
            showQuestion(findQuestion(currentQuestion));
        }
    }

    //Show a question in layout
    private void showQuestion(final Question question) {
//        final TextView questionTitle = findViewById(R.id.questionTitle);
        final TextView questionString = findViewById(R.id.questionString);
        TextView chosenAnswerView = findViewById(R.id.user_answer);
        final ListView answerList = findViewById(R.id.answerList);
//        final Button resultsButton = findViewById(R.id.results);
//        final TextView progressText = findViewById(R.id.progressText);
        ImageView imageView = findViewById(R.id.imageView);
        String country = "";
        if (finnish) {
            country = "fi_";
        } else {
            country = "se_";
        }

        if (null != question && null != question.getPicture() &&
                question.getPicture().length() > 0) {
            final int imageSrc = getResources().getIdentifier(country + question.getPicture(), "drawable", getPackageName());
            //Set onClick listener to see the image full screen
            imageView.setImageResource(imageSrc);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), FullscreenImageActivity.class);
                    intent.putExtra("imageSource", imageSrc);
                    startActivity(intent);
                }
            });
        } else {
            imageView.setImageResource(0);
        }
        String stringQuestion = getString(R.string.question_progress) + " " + LanguageHelper.convertNumber(currentQuestion + 1, this);
        String stringOf = getString(R.string.progress_of);

        chosenAnswerView.setText("");

        if (question != null) {
            String stringProgress = stringQuestion + " " + stringOf + " " + LanguageHelper.convertNumber(questions.size(), this);
            questionString.setText(question.getQuestionString());
            ActionBar ab = getSupportActionBar();
            if (null != ab) {
                ab.setSubtitle(stringProgress);
            }

            arrayAdapter = new AnswerAdapter(this, question.getAnswers(), question);
            answerList.setAdapter(arrayAdapter);

            //Onclick listener for choosing an answer
            answerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("EXAM ACTIVITY", "onClickListener, position: " + position);
                    TextView chosenAnswerView = findViewById(R.id.user_answer);
                    Answer answer = question.getAnswers().get(position);
                    db.addChoice(question, answer);
                    answer.setQuestion(question);
                    answer.getQuestion().setAnswered(true);
                    question.setChosenAnswer(answer);
                    question.setAnswered(true);

                    chosenAnswerView.setText(convertToAlphabet(position));
//                    Log.d("EXAM",)
                    arrayAdapter.notifyDataSetChanged();
//                    chosenAnswerView.setVisibility(View.GONE);
                    Log.i("EXAM ACTIVITY", "END onClickListener");
                }
            });
        }

    }

    //Select an alphabet to show in front of an answer
    private String convertToAlphabet(int position) {
        String result = "";
        if (position >= 0 && position < 10) {
            switch (position) {
                case 0:
                    result += "a";
                    break;
                case 1:
                    result += "b";
                    break;
                case 2:
                    result += "c";
                    break;
                case 3:
                    result += "d";
                    break;
                case 4:
                    result += "e";
                    break;
                case 5:
                    result += "f";
                    break;
                case 6:
                    result += "g";
                    break;
                case 7:
                    result += "h";
                    break;
                case 8:
                    result += "i";
                    break;
                case 9:
                    result += "a";
                    break;
                default:
                    return result;
            }
//        result += ". ";
        }
        return result;
    }

    @org.jetbrains.annotations.Nullable
    private Question findQuestion(final int questionIndex) {
        if (questionIndex >= 0 && questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            question.setAnswers(db.loadAnswersForQuestion(question, finnish));
            return question;
        } else {
            return null;
        }
    }

    //
    private void updateQuestionsWithChoices() {
        //Loop all questions (either the Finnish or Swedish questions)
        for (Question question : questions) {
            //Loop all the choices
            for (Choice choice : choices) {
                //For choice's question, set right Answer object and update database accordingly
                if (choice.getQuestion().getQuestionID() == question.getQuestionID()) {
                    Answer answer = choice.getAnswer(choice.getAnswerID(), db, finnish);
                    question.setChosenAnswer(answer);
                    db.updateQuestion(question, finnish);
                }
            }
        }
    }

}

package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ExamActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Answer> answers = new ArrayList<>();
    private ArrayList<Choice> choices = new ArrayList<>();
    ArrayAdapter arrayAdapter;// = new AnswerAdapter(this, answers);
    // This is the gesture detector compat instance.
    private GestureDetectorCompat gestureDetectorCompat = null;

    private MyDBHandler db;
    private int currentQuestion = -1;

    boolean finnish = false;


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
//                case R.id.navigation_exam_se:
//                    mTextMessage.setText(R.string.title_exam_SE);
//                    return true;
                case R.id.navigation_results:
                    mTextMessage.setText(R.string.title_results);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Go Places>");
        actionBar.setHomeButtonEnabled(true);

        // Create a common gesture listener object.
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();

        // Set activity in the listener.
        gestureListener.setActivity(this);

        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_exam);
        finnish = getIntent().getBooleanExtra("finnish", finnish);

        Log.i("EXAM", "country is: " + (finnish? "Finnish" : "Swedish"));

        //arrayAdapter = new AnswerAdapter(this, answers);

        db = new MyDBHandler(this);
        loadStuff(finnish);
        currentQuestion = db.getLastAnsweredQuestion();
        loadAnswersForQuestions();
        updateQuestionsWithChoices();
        nextQuestion(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass activity on touch event to the gesture detector.
        gestureDetectorCompat.onTouchEvent(event);
        // Return true to tell android OS that event has been consumed, do not pass it to other event listeners.
        return true;
    }

    private void loadStuff(boolean finnish) {
        Log.i("EXA<", "loadStuff, finnish: " + finnish);
        questions = db.loadQuestions(finnish);
        answers = db.loadAllAnswers(finnish);
        choices = db.loadAllChoices(finnish);

        currentQuestion -= 2;
        if (currentQuestion < -1) {
            currentQuestion = -1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.language, menu);
        return true;
    }

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

    public void setLocale(String lang) {
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

    private void loadAnswersForQuestions() {
        for (Question question : questions) {
            question.setAnswers(db.loadAnswersForQuestion(question.getQuestionID(), finnish));

        }
    }

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

    public void toHome(MenuItem menuItem) {
//        Log.i("BOTTOM NAVIGATION", "to home navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    public void toExam(MenuItem menuItem) {
//        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", true);
        startActivity(intent);
    }

    public void toExamSe(MenuItem menuItem) {
//        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", false);
        startActivity(intent);
    }

    public void toResults(MenuItem menuItem) {
//        Log.i("BOTTOM NAVIGATION", "to results navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        intent.putExtra("finnish", finnish);
        startActivity(intent);
    }
//
//    public void buttonToExam(View view) {
//        Log.i("BOTTOM NAVIGATION", "to exam button clicked");
//        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
//        intent.putExtra("extra", "extra");
//        startActivity(intent);
//    }

    public void buttonToResults(View view) {
        Log.i("BOTTOM NAVIGATION", "to results button clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        intent.putExtra("finnish", finnish);
        startActivity(intent);
    }


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
                Log.i("EXAM", "Current question: " + (currentQuestion) + " is zero");
                previousButton.setEnabled(false);
            } else {
                previousButton.setEnabled(true);
            }
            showQuestion(findQuestion(currentQuestion));
        } else {
            //End of exam
            Log.i("EXAM END", "Current question: " + (currentQuestion));
            Log.i("EXAM END", "Questions size - 1: " + (questions.size() - 1));
            if (currentQuestion >= questions.size() - 1) {
                nextButton.setEnabled(false);
            }
            previousButton.setEnabled(true);
            //End of exam implied
            int rightAnswers = db.countRightChoices();
            Log.i("RESULT", Integer.toString(rightAnswers));
            showQuestion(findQuestion(currentQuestion));
        }
    }

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

    private void showQuestion(final Question question) {
        final TextView questionTitle = findViewById(R.id.questionTitle);
        final TextView questionString = findViewById(R.id.questionString);
        TextView chosenAnswerView = findViewById(R.id.user_answer);
        final ListView answerList = findViewById(R.id.answerList);
//        final Button resultsButton = findViewById(R.id.results);
        final TextView progressText = findViewById(R.id.progressText);
        ImageView imageView = findViewById(R.id.imageView);
        String country = "";
        if (finnish) {
            country = "fi_";
        } else {
            country = "se_";
        }

    if (null != question && null != question.getPicture() &&
            question.getPicture().length() > 0) {
            Log.d("EXAM", "picture: " + question.getPicture());
            int imageSrc = getResources().getIdentifier(country + question.getPicture(), "drawable", getPackageName());
            Log.d("EXAM", "image source: " + imageSrc);
            imageView.setImageResource(R.drawable.drive);
            imageView.setImageResource(imageSrc);
        } else {
            imageView.setImageResource(0);
        }
//        final Button newExamButton = findViewById(R.id.new_exam);

//        if (db.countChoices() > 0) {
//            newExamButton.setEnabled(true);
//        } else {
//            newExamButton.setEnabled(false);
//        }

        String stringQuestion = getString(R.string.question_progress) + " " + LanguageHelper.convertNumber(currentQuestion + 1, this);
        String stringOf = getString(R.string.progress_of);

        chosenAnswerView.setText("");

        if (question != null) {
            String stringProgress = stringQuestion + " " + stringOf + " " + LanguageHelper.convertNumber(questions.size(), this) ;
            progressText.setText(stringProgress);
            //Get answer texts for list view
//            final ArrayList<String> questionsAnswers = new ArrayList<>();
//            for (Answer a : question.getAnswers()) {
//                questionsAnswers.add(a.getAnswerString());
//            }
//            String stringQuestion = R.string.question_progress;

//            String title = stringQuestion + Integer.toString(question.getQuestionID());
            questionTitle.setText(stringQuestion);
//            Log.i("SHOW QUESTION, TITLE: ", questionTitle.getText().toString());
//            Log.i("SHOW QUESTION, STRING", question.getQuestionString());
            questionString.setText(question.getQuestionString());

//            chosenAnswerView.setText(convertToAlphabet(position));

//            final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionsAnswers) {
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    View row = super.getView(position, convertView, parent);
//
////                    boolean questionIsAnswerd = question.isAnswered();
//                    Answer chosenAnswer = question.getChosenAnswer();
////                    String chosenAnswersString = chosenAnswer.getAnswerString();
//
//                    Log.i("!!!!!!!!!ARRAY ADAPTER, getView", "chosen answer: " + chosenAnswer);
//                    String lineAnswer = getItem(position);
//
//                    if (chosenAnswer != null) {
//                        Log.i("ARRAY ADAPTER, getView", "chosen answer not null");
//
//                        String chosenString = chosenAnswer.getAnswerString();
//                        if (lineAnswer != null && lineAnswer.equals(chosenString)) {
//                            Log.i("ARRAY ADAPTER, getView", "set background color to RED ");
//                            row.setBackgroundColor(Color.RED);
//                        } else {
//                            Log.i("ARRAY ADAPTER, getView", "set background color to wh;ite ");
//                            row.setBackgroundColor(Color.WHITE);
//                        }
//                    } else {
//                        Log.i("ARRAY ADAPTER, getView", "chosen answer not null");
//                        row.setBackgroundColor(Color.WHITE);
//                    }
//                    return row;
//                }
//            };

//            final ArrayAdapter arrayAdapter = new AnswerAdapter(this, question.getAnswers());
            arrayAdapter = new AnswerAdapter(this, question.getAnswers());

            answerList.setAdapter(arrayAdapter);

            answerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView chosenAnswerView = findViewById(R.id.user_answer);
// Intent intent = new Intent(getApplicationContext(), this);
//                    Log.i("SHOW QUESTION, ###Item###", "Item clicked: " + Integer.toString(position));
//                    Log.i("SHOW QUESTION,, ###Item###", "Answer: " + questionsAnswers.get(position));
//                    Answer answer = findAnswerBy(question, questionsAnswers.get(position));
                    Answer answer = question.getAnswers().get(position);
//                    Log.i("SHOW QUESTION", "ANSWER IS RIGHT?: " + answerIsRight(answer));
                    db.addChoice(question, answer);
                    question.setChosenAnswer(answer);
                    question.setAnswered(true);
//                    if (isAllQuestionsAnswered()) {
////                        Log.i("SHOW QUESTION", "RESULTS: " + db.countRightChoices() + " / " + questions.size());
//                        resultsButton.setEnabled(true);
//                    }
//                    ((AnswerAdapter) arrayAdapter).markAnswer(position, view, parent);
//                    arrayAdapter.getView(position, view, parent).invalidate();
//                    setItems(arrayAdapter, position, view, parent);
//                    arrayAdapter.
//                    arrayAdapter.getView(position, view, parent).setBackgroundColor(getResources().getColor(R.color.lightBlue));
//                    nextQuestion(null);
//                    Log.i("EXAM", "chosenAnswer: " + chosenAnswerView.getText());
//                    chosenAnswerView.setText(answer.getAnswerString());
                    chosenAnswerView.setText(convertToAlphabet(position));
                    arrayAdapter.notifyDataSetChanged();
//                    arrayAdapter.getView(position, view, parent).invalidate();

                }
            });
        }
    }

//    private void setItems(ArrayAdapter arrayAdapter, int position, View view, ViewGroup parent) {
//        for (int i = 0; i < arrayAdapter.getCount(); i++) {
//            View listItem = arrayAdapter.getView(i, view, parent);
//            if (i == position) {
//                listItem.setBackgroundColor(getResources().getColor(R.color.lightBlue));
//            } else {
//                listItem.setBackgroundColor(Color.WHITE);
//            }
//        }
//    }
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

//    @Nullable
//    private Answer findAnswerBy(Question question, String answerString) {
//        for (Answer a : question.getAnswers()) {
//            if (a.getAnswerString() == answerString) {
//                return a;
//            }
//        }
//        return null;
//    }
//
//    private boolean answerIsRight(Answer answer) {
//        return answer.isRightAnswer() != 0;
//    }
//
//    private boolean isAllQuestionsAnswered() {
//        for (Question question : questions) {
//            if (!question.isAnswered()) {
//                return false;
//            }
//        }
//        return true;
//    }

    private void updateQuestionsWithChoices() {
//        Log.i("UPDATE QUESTIONS WITH CHOICES", "updating...");

        for (Question question : questions) {

//            Log.i("UPDATE QUESTION", "question string: " + question.getQuestionString());
            for (Choice choice : choices) {
//                Log.i("UPDATE CHOICE", "answer string: " + choice.getAnswer().getAnswerString());
                if (choice.getQuestion().getQuestionID() == question.getQuestionID()) {

                    Answer answer = choice.getAnswer(choice.getAnswerID(), db, finnish);
                    question.setChosenAnswer(answer);
                    db.updateQuestion(question, finnish);
//                    question.setAnswered(true);
//                    Log.i("UPDATE CHOICE", "question: " + question.getQuestionID() + ", answer: " + answer.getAnswerID() +
//                            ", choice question: " + choice.getQuestion().getQuestionID() + ", question chosen anser: " + question.getChosenAnswer().getAnswerID());

                }
            }
        }
    }

}

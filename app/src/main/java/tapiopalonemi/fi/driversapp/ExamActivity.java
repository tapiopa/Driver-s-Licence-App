package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    private MyDBHandler db;
    private int currentQuestion = -1;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_exam);

        //arrayAdapter = new AnswerAdapter(this, answers);
        db = new MyDBHandler(this);
//        db.onCreate(db.getWritableDatabase());
//        db.getWritableDatabase();
//        Log.i("DB", "going for questions");
        if (!db.isDataLoaded()) {
            Log.d("########EXAM", "data is not loaded");
            db.firstRun(this);
            questions = db.loadQuestions();

        }
        questions = db.loadQuestions();
        answers = db.loadAllAnswers();
        choices = db.loadAllChoices();

//        for (Answer an : answers) {
//            Log.i("ANSWER", an.getAnswerString());
//        }

//        Locale primaryLocale = this.getResources().getConfiguration().getLocales().get(0);
//        String locale = primaryLocale.getDisplayName();
//        String nepali = LanguageHelper.convertNumber(123, this);

//        Log.i("EXAM ACTIVITY", "Locale: " + primaryLocale);
//        Log.i("EXAM ACTIVITY", "is nepali: " + nepali);

        loadAnswersForQuestions();
        updateQuestionsWithChoices();
        nextQuestion(null);
    }

    private void loadAnswersForQuestions() {
        for (Question question : questions) {
            question.setAnswers(db.loadAnswersForQuestion(question.getQuestionID()));

        }
    }

    public void startNewExam(View view) {
        db.deleteAllChoices();
        currentQuestion = -1;
        for (Question question : questions) {
            question.setChosenAnswer(null);
            question.setAnswered(false);
        }
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
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }

    public void toResults(MenuItem menuItem) {
//        Log.i("BOTTOM NAVIGATION", "to results navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
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
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }


    public void nextQuestion(View view) {
        Log.i("EXAM", "Next question");
        Log.i("EXAM", "Current question: " + (currentQuestion + 1));
        Log.i("EXAM", "Questions size: " + questions.size());
        Button nextButton = findViewById(R.id.nextButton);
        Button previousButton = findViewById(R.id.backButton);
        currentQuestion = currentQuestion + 1;

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
        Log.i("EXAM", "Current question: " + (currentQuestion - 1));
        Button previousButton = findViewById(R.id.backButton);
        Button nextButton = findViewById(R.id.nextButton);
        currentQuestion = currentQuestion - 1;

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
        Log.d("EXAM", "picture: " + question.getPicture());
        if (null != question.getPicture() && question.getPicture().length() > 0) {
            ImageView imageView = findViewById(R.id.imageView);
            int imageSrc = getResources().getIdentifier("fi_" + question.getPicture(), "drawable", getPackageName());
            Log.d("EXAM", "image source: " + imageSrc);
            imageView.setImageResource(imageSrc);
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
                    chosenAnswerView.setText(answer.getAnswerString());
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

    @org.jetbrains.annotations.Nullable
    private Question findQuestion(final int questionIndex) {
        if (questionIndex >= 0 && questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            question.setAnswers(db.loadAnswersForQuestion(question));
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

                    Answer answer = choice.getAnswer(choice.getAnswerID(), db);
                    question.setChosenAnswer(answer);
                    db.updateQuestion(question);
//                    question.setAnswered(true);
//                    Log.i("UPDATE CHOICE", "question: " + question.getQuestionID() + ", answer: " + answer.getAnswerID() +
//                            ", choice question: " + choice.getQuestion().getQuestionID() + ", question chosen anser: " + question.getChosenAnswer().getAnswerID());

                }
            }
        }
    }

}

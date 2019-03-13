package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExamActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "driverdatabase3.db";
    public static final String TABLE_QUESTION = "question";
    public static final String TABLE_ANSWER = "answer";
    public static final String TABLE_USER_CHOICE = "userChoice";
    public static final String COLUMN_QUESTION_ID = "questionID";
    public static final String COLUMN_QUESTION_STRING = "questionString";
    public static final String COLUMN_ANSWER_ID = "answerID";
    public static final String COLUMN_ANSWER_STRING = "answerString";
    public static final String COLUMN_ANSWER_IS_RIGHT = "answerIsRight";
    public static final String COLUMN_ANSWER_CHOSEN = "answerChosen";
    public static final String COLUMN_CHOICE_ID = "choiceID";

    public final ArrayList<Question> questions = new ArrayList<>();
    public final ArrayList<Answer> answers = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        Intent intent = getIntent();
        Log.i("intent", intent.getStringExtra("extra"));
        SQLiteDatabase driversDatabase = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        driversDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION +
                "( " + COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_QUESTION_STRING + " TEXT )");
        driversDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ANSWER +
                "( " + COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ANSWER_STRING + " TEXT, " +
                COLUMN_ANSWER_IS_RIGHT + " INTEGER, " +
                COLUMN_QUESTION_ID + " INTEGER, " +
                COLUMN_ANSWER_CHOSEN + " INTEGER )");
        driversDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_CHOICE +
                "( " + COLUMN_CHOICE_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_QUESTION_ID + " INTEGER, " +
                COLUMN_ANSWER_ID + " INTEGER, " +
                COLUMN_ANSWER_IS_RIGHT + " INTEGER )");


//        driversDatabase.execSQL("INSERT INTO " + TABLE_QUESTION +
//                "(" +
////                COLUMN_QUESTION_ID + ", " +
//                COLUMN_QUESTION_STRING + ") " +
//                " VALUES('WHICH WAY IS LEFT?')");
//        driversDatabase.execSQL("INSERT INTO " + TABLE_QUESTION +
//                "(" +
////                COLUMN_QUESTION_ID + ", " +
//                COLUMN_QUESTION_STRING + ") " +
//                " VALUES('WHICH WAY IS RIGHT?')");
//
//        driversDatabase.execSQL("INSERT INTO " + TABLE_ANSWER +
//                " (" + COLUMN_ANSWER_STRING + ", " + COLUMN_ANSWER_IS_RIGHT + ", " + COLUMN_QUESTION_ID + ", " + COLUMN_ANSWER_CHOSEN + ") " +
//                " VALUES('UP', 1, 1, 0)");
//        driversDatabase.execSQL("INSERT INTO " + TABLE_ANSWER +
//                " (" + COLUMN_ANSWER_STRING + ", " + COLUMN_ANSWER_IS_RIGHT + ", " + COLUMN_QUESTION_ID + ", " + COLUMN_ANSWER_CHOSEN + ") " +
//                " VALUES('DOWN', 1, 1, -1)");
//        driversDatabase.execSQL("INSERT INTO " + TABLE_ANSWER +
//                " (" + COLUMN_ANSWER_STRING + ", " + COLUMN_ANSWER_IS_RIGHT + ", " + COLUMN_QUESTION_ID + ", " + COLUMN_ANSWER_CHOSEN + ") " +
//                " VALUES('NORTH', 1, 2, -1)");
//        driversDatabase.execSQL("INSERT INTO " + TABLE_ANSWER +
//                " (" + COLUMN_ANSWER_STRING + ", " + COLUMN_ANSWER_IS_RIGHT + ", " + COLUMN_QUESTION_ID + ", " + COLUMN_ANSWER_CHOSEN + ") " +
//                " VALUES('SOUTH', 1, 2, 0)");

        Cursor c = driversDatabase.rawQuery("SELECT * FROM " + TABLE_QUESTION, null);

        int questionIDIndex = c.getColumnIndex(COLUMN_QUESTION_ID);
        int questionStringIndex = c.getColumnIndex(COLUMN_QUESTION_STRING);

        ArrayList<Integer> questionIDs = new ArrayList<>();
        c.moveToFirst();
        do {
//            Log.i("Database Contents ID", Integer.toString(c.getInt(questionIDIndex)));
//            Log.i("Database contents question string", c.getString(questionStringIndex));
            int ID = c.getInt(questionIDIndex);
            String questionString = c.getString(questionStringIndex);
            final Question addQuestion = new Question(ID, questionString);
            questions.add(addQuestion);
            questionIDs.add(addQuestion.getQuestionID());

        } while (c.moveToNext());

        StringBuilder idList = new StringBuilder();

        for (Question q : questions) {
            idList.append(Integer.toString(q.getQuestionID()));
            idList.append(",");
        }
        idList.deleteCharAt(idList.length() - 1);
        Log.i("IDLIST", idList.toString());

        Cursor a = driversDatabase.rawQuery("SELECT * FROM " + TABLE_ANSWER, null);// + " WHERE " + COLUMN_QUESTION_ID + " IN (1, 2)", null);

        int answerIDIndex = a.getColumnIndex(COLUMN_ANSWER_ID);
        int answerStringIndex = a.getColumnIndex(COLUMN_ANSWER_STRING);
        int answerRightIndex = a.getColumnIndex(COLUMN_ANSWER_IS_RIGHT);
        int answerQuestionIDIndex = a.getColumnIndex(COLUMN_QUESTION_ID);
        int answerChosen = a.getColumnIndex(COLUMN_ANSWER_CHOSEN);

        a.moveToFirst();

        do {
            answers.add(new Answer(a.getInt(answerIDIndex),
                                a.getString(answerStringIndex),
                                a.getInt(answerRightIndex),
                                a.getInt(answerQuestionIDIndex),
                                a.getInt(answerChosen))
            );
        } while (a.moveToNext());

        for (Answer an : answers) {
            Log.i("ANSWER", an.getAnswerText());
        }
        showQuestion(1);
    }

    private void showQuestion(int questionId) {
        TextView questionTitle = (TextView) findViewById(R.id.questionTitle);
        TextView questionString = (TextView) findViewById(R.id.questionString);
        ListView answerList = (ListView) findViewById(R.id.answerList);

        int questionIndex = -1;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestionID() == questionId) {
                questionIndex = i;
            }
        }

        if (questionIndex >= 0) {
            final Question question = questions.get(questionIndex);
            ArrayList<String> questionsAnswers = new ArrayList<>();
            for (Answer a : answers) {
                if (a.getQuestionID() == question.getQuestionID()) {
                    questionsAnswers.add(a.getAnswerText());
                }
            }

            String title = "Question " + Integer.toString(question.getQuestionID());
            questionTitle.setText(title);
            Log.i("QUESTION STRING", question.getQuestionString());
            questionString.setText(question.getQuestionString());

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionsAnswers);
            answerList.setAdapter(arrayAdapter);
        }
    }
}

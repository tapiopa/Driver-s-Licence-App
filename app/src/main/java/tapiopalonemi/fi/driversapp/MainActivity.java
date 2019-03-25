package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "driverdatabase.db";
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

    public void toExam(View view) {
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("extra", "extra");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    public void Exam(View view) {
    }
}

package tapiopalonemi.fi.driversapp;

//import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;


//import java.nio.LongBuffer;

public class Choice /*extends AppCompatActivity*/ {
    private int ID;
    private int questionID;
    private int answerID;
    private int answerIsRight;
    private Question question;
    private Answer answer;

    public Choice(int ID, int questionID, int answerID, int answerIsRight) {
        this.ID = ID;
        this.questionID = questionID;
        this.answerID = answerID;
        this.answerIsRight = answerIsRight;
    }

    public Choice(int ID, int questionID, int answerID, int answerIsRight,
                  MyDBHandler db, boolean finnish) {
        this.ID = ID;
        this.questionID = questionID;
        this.answerID = answerID;
        this.answerIsRight = answerIsRight;
        this.question = this.getQuestion(questionID, db, finnish);
        this.answer =  this.getAnswer(answerID, db, finnish);
    }


    public int getAnswerID() {
        return answerID;
    }

    public int getAnswerIsRight() {
        return answerIsRight;
    }

    public Question getQuestion() {
        return question;
    }

    private Question getQuestion(int questionID, MyDBHandler db, boolean finnish) {
        return db.findQuestionBy(questionID, finnish);
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Answer getAnswer(int answerID, MyDBHandler db, boolean finnish) {
//        Log.i("CHOICE", "DB: " + db);
//        Log.i("CHOICE", "answer id:; " + answerID);
        Answer answer = db.findAnswerBy(answerID, finnish);
//        Log.i("CHOICE", "answer: " + answer);
        this.answerIsRight = answer.getIsRightAnswer();
        this.question.setChosenAnswer(answer);
        this.question.setAnswered(true);
        return answer;
    }
}

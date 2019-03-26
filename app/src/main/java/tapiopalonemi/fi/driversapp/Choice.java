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
//    private MyDBHandler db = new MyDBHandler(this);


    // --Commented out by Inspection (19/03/2019, 10.08):public Choice() {}

// --Commented out by Inspection START (19/03/2019, 10.08):
//    public Choice(int ID, int questionID, int answerID) {
////        db = new MyDBHandler(this);
//
//        this.ID = ID;
//        this.questionID = questionID;
//        this.answerID = answerID;
////        this.question = this.getQuestion(questionID);
////        this.answer = this.getAnswer(answerID);
////        this.answerIsRight = answer.getIsRightAnswer();
//    }
// --Commented out by Inspection STOP (19/03/2019, 10.08)

    public Choice(int ID, int questionID, int answerID, int answerIsRight) {
        this.ID = ID;
        this.questionID = questionID;
        this.answerID = answerID;
        this.answerIsRight = answerIsRight;
    }

    public Choice(int ID, int questionID, int answerID, int answerIsRight,
                  MyDBHandler db) {
        this.ID = ID;
        this.questionID = questionID;
        this.answerID = answerID;
        this.answerIsRight = answerIsRight;
        this.question = this.getQuestion(questionID, db);
        this.answer =  this.getAnswer(answerID, db);
    }

// --Commented out by Inspection START (19/03/2019, 10.09):
//    public Choice(int ID, Question question, Answer answer) {
//        this.ID = ID;
//        this.question = question;
//        this.answer = answer;
//        this.questionID = question.getQuestionID();
// --Commented out by Inspection START (19/03/2019, 10.09):
// --Commented out by Inspection START (19/03/2019, 10.09):
//////        this.answerID = answer.getAnswerID();
// --Commented out by Inspection START (19/03/2019, 10.09):
////// --Commented out by Inspection START (19/03/2019, 10.09):
//// --Commented out by Inspection STOP (19/03/2019, 10.09)
////////        this.answerIsRight = answer.getIsRightAnswer();
// --Commented out by Inspection STOP (19/03/2019, 10.09)
//// --Commented out by Inspection STOP (19/03/2019, 10.09)
// --Commented out by Inspection STOP (19/03/2019, 10.09)
//    }
// --Commented out by Inspection START (19/03/2019, 10.09):
//// --Commented out by Inspection STOP (19/03/2019, 10.09)
//
//    public int getID() {
//        return ID;
// --Commented out by Inspection START (19/03/2019, 10.09):
//// --Commented out by Inspection START (19/03/2019, 10.09):
////// --Commented out by Inspection STOP (19/03/2019, 10.09)
////    }
////
////    public void setID(int ID) {
// --Commented out by Inspection STOP (19/03/2019, 10.09)
//        this.ID = ID;
// --Commented out by Inspection STOP (19/03/2019, 10.09)
//    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public int getAnswerIsRight() {
        return answerIsRight;
    }

    public void setAnswerIsRight(int answerIsRight) {
        this.answerIsRight = answerIsRight;
    }

    public Question getQuestion() {
        return question;
    }

//    public void setQuestion(Question question) {
// --Commented out by Inspection START (19/03/2019, 10.09):
//        this.question = question;
//    }

    private Question getQuestion(int questionID, MyDBHandler db) {
//        Log.i("CHOICE", "DB: " + db);
//        Log.i("CHOICE", "question id: " + questionID);
        Question question = db.findQuestionBy(questionID);
//        Log.i("CHOICE", "question: " + question);
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Answer getAnswer(int answerID, MyDBHandler db) {
        Log.i("CHOICE", "DB: " + db);
        Log.i("CHOICE", "answer id:; " + answerID);
        Answer answer = db.findAnswerBy(answerID);
        Log.i("CHOICE", "answer: " + answer);
        this.answerIsRight = answer.getIsRightAnswer();
        this.question.setChosenAnswer(answer);
        this.question.setAnswered(true);
        return answer;
    }
}
package tapiopalonemi.fi.driversapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "driverdatabase8.db";
    public static final String TABLE_QUESTION = "question";
    public static final String TABLE_ANSWER = "answer";
    public static final String TABLE_USER_CHOICE = "userChoice";
    public static final String COLUMN_QUESTION_ID = "questionID";
    public static final String COLUMN_QUESTION_STRING = "questionString";
    public static final String COLUMN_RIGHT_ANSWER = "rightAnswer";
    public static final String COLUMN_ANSWER_ID = "answerID";
    public static final String COLUMN_ANSWER_STRING = "answerString";
    public static final String COLUMN_ANSWER_IS_RIGHT = "answerIsRight";
//    public static final String COLUMN_ANSWER_CHOSEN = "answerChosen";
    public static final String COLUMN_CHOICE_ID = "choiceID";

    //initialize the database
    public MyDBHandler(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("#######@@@@@@@@!!!!!!!!database", "on creating database....");
//        pushData(db);
    }

    private void pushData(SQLiteDatabase db) {
//        SQLiteDatabase driversDatabase = this.getWritableDatabase();
        String CREATE_QUESTION = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + "(" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_QUESTION_STRING + " VARCHAR, " +
                COLUMN_RIGHT_ANSWER + " INTEGER" +
                ")";
        String CREATE_ANSWER = "CREATE TABLE IF NOT EXISTS " + TABLE_ANSWER + "(" +
                COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ANSWER_STRING + " VARCHAR, " +
                COLUMN_ANSWER_IS_RIGHT + " INTEGER, " +
                COLUMN_QUESTION_ID + " INTEGER " +
                ")";
        String CREATE_CHOICE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_CHOICE + "(" +
                COLUMN_CHOICE_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_QUESTION_ID + " INTEGER, " +
                COLUMN_ANSWER_ID + " INTEGER, " +
                COLUMN_ANSWER_IS_RIGHT + " INTEGER " +
                ")";
        Log.i("MYDBHANDLER", "create tables");
        db.execSQL(CREATE_QUESTION);
        db.execSQL(CREATE_ANSWER);
        db.execSQL(CREATE_CHOICE);
        Log.i("MYDBHANDLER", "push Data");
        db.execSQL("INSERT INTO " + TABLE_QUESTION + "(" +
//                COLUMN_QUESTION_ID + ", " +
                COLUMN_QUESTION_STRING +  ", " +
                COLUMN_RIGHT_ANSWER +
                ") " +
                " VALUES('WHICH WAY IS LEFT?', -1)");
        db.execSQL("INSERT INTO " + TABLE_QUESTION +
                "(" +
//                COLUMN_QUESTION_ID + ", " +
                COLUMN_QUESTION_STRING + ", " +
                COLUMN_RIGHT_ANSWER +
                ") " +
                " VALUES('WHICH WAY IS RIGHT?', -1)");

        db.execSQL("INSERT INTO " + TABLE_ANSWER + " (" +
                COLUMN_ANSWER_STRING + ", " +
                COLUMN_ANSWER_IS_RIGHT + ", " +
                COLUMN_QUESTION_ID + ") " +
                " VALUES('UP', 0, 1)");
        db.execSQL("INSERT INTO " + TABLE_ANSWER +
                " (" +
                COLUMN_ANSWER_STRING + ", " +
                COLUMN_ANSWER_IS_RIGHT + ", " +
                COLUMN_QUESTION_ID + ") " +
                " VALUES('DOWN', 1, 1)");
        db.execSQL("INSERT INTO " + TABLE_ANSWER +
                " (" +
                COLUMN_ANSWER_STRING + ", " +
                COLUMN_ANSWER_IS_RIGHT + ", " +
                COLUMN_QUESTION_ID + ") " +
                " VALUES('NORTH', 1, 2)");
        db.execSQL("INSERT INTO " + TABLE_ANSWER +
                " (" +
                COLUMN_ANSWER_STRING + ", " +
                COLUMN_ANSWER_IS_RIGHT + ", " +
                COLUMN_QUESTION_ID + ") " +
                " VALUES('SOUTH', 0, 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public ArrayList<Question> loadQuestions() {
        ArrayList<Question> result = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String questionString = cursor.getString(1);
            int rightAnswer = cursor.getInt(2);
            result.add(new Question(id, questionString, rightAnswer));
//            result += String.valueOf(result_0) + " " + result_1 +
//                    System.getProperty("line.separator");
        }  while (cursor.moveToNext());
        cursor.close();
        db.close();
        return result;
    }

    public void addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_ID, question.getQuestionID());
        values.put(COLUMN_QUESTION_STRING, question.getQuestionString());
        values.put(COLUMN_RIGHT_ANSWER, question.getRightAnswerID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_QUESTION, null, values);
        db.close();
    }

    public Question findQuestionBy(String questionString) {
        String query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_STRING + " = " + "'" + questionString + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Question question = new Question();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            question.setQuestionID(Integer.parseInt(cursor.getString(0)));
            question.setQuestionString(cursor.getString(1));
            question.setRightAnswerID(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            question = null;
        }
        db.close();
        return question;
    }

    public Question findQuestionBy(int questionID) {
        String query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_ID + " = " + "'" + questionID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Question question = new Question();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            question.setQuestionID(Integer.parseInt(cursor.getString(0)));
            question.setQuestionString(cursor.getString(1));
            question.setRightAnswerID(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            question = null;
        }
        db.close();
        return question;
    }

    public boolean deleteQuestion(int ID) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Question question = new Question();
        if (cursor.moveToFirst()) {
            question.setQuestionID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_QUESTION, COLUMN_QUESTION_ID + "=?",
                    new String[] { String.valueOf(question.getQuestionID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteQuestion(Question question) {
        return deleteQuestion(question.getQuestionID());
    }

    public int updateQuestion(int ID, String name, int answerID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_QUESTION_ID, ID);
        args.put(COLUMN_QUESTION_STRING, name);
        args.put(COLUMN_RIGHT_ANSWER, answerID);
        return db.update(TABLE_QUESTION, args,COLUMN_QUESTION_ID + "=" + ID, null);
    }

    public int updateQuestion(Question question) {
        return updateQuestion(question.getQuestionID(), question.getQuestionString(), question.getRightAnswerID());
    }

    //ANSWERS
    public ArrayList<Answer> loadAllAnswers() {
        ArrayList<Answer> result = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ANSWER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String answerString = cursor.getString(1);
            int isRightAnswer = cursor.getInt(2);
            int questionID = cursor.getInt(3);
            result.add(new Answer(id, answerString, isRightAnswer, questionID));
        } while (cursor.moveToNext());
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<Answer> loadAnswersForQuestion(int questionID) {
        ArrayList<Answer> result = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_QUESTION_ID + " = " + questionID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String answerString = cursor.getString(1);
            int isRightAnswer = cursor.getInt(2);
            result.add(new Answer(id, answerString, isRightAnswer, questionID));
        } while (cursor.moveToNext());
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<Answer> loadAnswersForQuestion(Question question) {
        ArrayList<Answer> result = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_QUESTION_ID + " = " + question.getQuestionID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String answerString = cursor.getString(1);
            int isRightAnswer = cursor.getInt(2);
            result.add(new Answer(id, answerString, isRightAnswer, question));
        } while (cursor.moveToNext());
        cursor.close();
        db.close();
        return result;
    }

    public void addAnswer(Answer answer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANSWER_ID, answer.getAnswerID());
        values.put(COLUMN_ANSWER_STRING, answer.getAnswerString());
        values.put(COLUMN_ANSWER_IS_RIGHT, answer.isRightAnswer());
        values.put(COLUMN_QUESTION_ID, answer.getQuestionID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ANSWER, null, values);
        db.close();
    }

    public Answer findAnswerBy(String answerString) {
        String query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_ANSWER_STRING + " = " + "'" + answerString + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Answer answer = new Answer();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            answer.setQuestionID(Integer.parseInt(cursor.getString(0)));
            answer.setAnswerString(cursor.getString(1));
            answer.setRightAnswer(Integer.parseInt(cursor.getString(2)));
            answer.setQuestionID(Integer.parseInt(cursor.getString(3)));
            cursor.close();
        } else {
            answer = null;
        }
        db.close();
        return answer;
    }

    public Answer findAnswerBy(int answerID) {
        String query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_ANSWER_ID + " = " + "'" + answerID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Answer answer = new Answer();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            answer.setAnswerID(Integer.parseInt(cursor.getString(0)));
            answer.setAnswerString(cursor.getString(1));
            answer.setRightAnswer(Integer.parseInt(cursor.getString(2)));
            answer.setQuestionID(Integer.parseInt(cursor.getString(3)));
            cursor.close();
        } else {
            answer = null;
        }
        db.close();
        return answer;
    }

    public boolean deleteAnswer(int ID) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_ANSWER_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Answer answer = new Answer();
        if (cursor.moveToFirst()) {
            answer.setQuestionID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_ANSWER, COLUMN_ANSWER_ID + "=?",
                    new String[] { String.valueOf(answer.getAnswerID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteAnswer(Answer answer) {
        return deleteQuestion(answer.getAnswerID());
    }

    public int updateAnswer(int ID, String name, int answerID, int questionID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ANSWER_ID, ID);
        args.put(COLUMN_ANSWER_STRING, name);
        args.put(COLUMN_ANSWER_IS_RIGHT, answerID);
        return db.update(TABLE_ANSWER, args,COLUMN_ANSWER_ID + " = " + ID, null);
    }

    public int updateAnswer(Answer answer) {
        return updateAnswer(answer.getAnswerID(), answer.getAnswerString(), answer.isRightAnswer(), answer.getQuestionID());
    }

    //Answers for questions

    public Choice getChoiceByQuestionID(int questionID) {
        String query = "SELECT * FROM " + TABLE_USER_CHOICE + " WHERE " + COLUMN_QUESTION_ID + " = " + questionID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
//        Choice choice = new Choice();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
//            int questionID = cursor.getInt(1);
            int answerID = cursor.getInt(2);
            int isRightAnswer = cursor.getInt(3);
            return new Choice(id, questionID, answerID, isRightAnswer);
        }
        return null;
    }

    public void addChoice(Question question, Answer answer) {
        if (question != null && answer != null) {
            Choice oldChoice = getChoiceByQuestionID(question.getQuestionID());
            ContentValues values = new ContentValues();
            values.put(COLUMN_ANSWER_ID, answer.getAnswerID());
            values.put(COLUMN_QUESTION_ID, question.getQuestionID());
            values.put(COLUMN_ANSWER_IS_RIGHT, answer.isRightAnswer());
            SQLiteDatabase db = this.getWritableDatabase();
            if (oldChoice == null) {
                db.insert(TABLE_USER_CHOICE, null, values);
                Log.i("MBDATAHANDLER", "Choice added");

            } else {
                db.update(TABLE_USER_CHOICE, values, COLUMN_QUESTION_ID + "=" + question.getQuestionID(), null);
                Log.i("MBDATAHANDLER", "Choice updated");
            }
            db.close();
        } else {
            Log.i("MYDATAHANDLER", "no question or answer");
        }

    }

    public void deleteAllChoices() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER_CHOICE);
        Log.i("MBDATAHANDLER", "Choices deleted");
    }

    public int countRightChoices() {
        String query = "SELECT COUNT(*) FROM " + TABLE_USER_CHOICE + " WHERE " + COLUMN_ANSWER_IS_RIGHT + " = " + 1;
        Log.i("MYDBHANDLER", query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = -1;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
        } else {
            count = -1;
        }
        db.close();
        return count;
    }

    public ArrayList<Choice> loadAllChoices() {
        String query = "SELECT * FROM " + TABLE_USER_CHOICE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Choice> choices = new ArrayList<>();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                int questionID = cursor.getInt(1);
                int answerID = cursor.getInt(2);
                int isRightAnswer = cursor.getInt(3);
                choices.add(new Choice(id, questionID, answerID, isRightAnswer, this));
            } while (cursor.moveToNext());
        }
        return choices;
    }
}

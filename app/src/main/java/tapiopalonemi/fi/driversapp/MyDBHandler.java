package tapiopalonemi.fi.driversapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

class MyDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "driverdatabase8.db";
    public static final String TABLE_APPLICATION = "application";
    public static final String TABLE_QUESTION = "question";
    public static final String TABLE_ANSWER = "answer";
    public static final String TABLE_USER_CHOICE = "userChoice";
    public static final String COLUMN_IS_DATA_LOADED = "isDataLoaded";
    public static final String COLUMN_QUESTION_ID = "questionID";
    public static final String COLUMN_QUESTION_STRING = "questionString";
    public static final String COLUMN_QUESTION_PICTURE = "picture";
    public static final String COLUMN_RIGHT_ANSWER = "rightAnswer";
    public static final String COLUMN_ANSWER_ID = "answerID";
    public static final String COLUMN_ANSWER_STRING = "answerString";
    public static final String COLUMN_ANSWER_IS_RIGHT = "answerIsRight";
//    public static final String COLUMN_ANSWER_CHOSEN = "answerChosen";
    public static final String COLUMN_CHOICE_ID = "choiceID";

// --Commented out by Inspection START (19/03/2019, 10.09):
//    //initialize the database
//    public MyDBHandler(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
//    }
// --Commented out by Inspection STOP (19/03/2019, 10.09)
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("#######@@@@@@@@!!!!!!!!database", "on create");
//        pushData();
    }

    private void clearDB() {
        String dropApplication = "DROP TABLE IF EXISTS " + TABLE_APPLICATION;
        String dropQuestions = "DROP TABLE IF EXISTS " + TABLE_QUESTION;
        String dropAnswers = "DROP TABLE IF EXISTS " + TABLE_ANSWER;
        String dropChoices = "DROP TABLE IF EXISTS " + TABLE_USER_CHOICE;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(dropApplication);
        db.execSQL(dropQuestions);
        db.execSQL(dropAnswers);
        db.execSQL(dropChoices);
//        db.close();
    }

    private void pushData() {
        SQLiteDatabase db = this.getWritableDatabase();
//        createTables();
        Log.i("MYDBH ANDLER", "push Data");

        db.execSQL("INSERT INTO " + TABLE_QUESTION + "(" +
//                COLUMN_QUESTION_ID + ", " +
                COLUMN_QUESTION_STRING +  ", " +
                COLUMN_RIGHT_ANSWER + ", " +
                COLUMN_QUESTION_PICTURE +
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

    private void createTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_QUESTION = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + "(" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_QUESTION_STRING + " VARCHAR, " +
                COLUMN_RIGHT_ANSWER + " INTEGER, " +
                COLUMN_QUESTION_PICTURE + " VARCHAR " +
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
        String CREATE_APPLICATION = "CREATE TABLE IF NOT EXISTS " + TABLE_APPLICATION + "(" +
                COLUMN_IS_DATA_LOADED + " INTEGER)";
        Log.i("MYDBHANDLER", "create tables");
        db.execSQL(CREATE_APPLICATION);
        db.execSQL(CREATE_QUESTION);
        db.execSQL(CREATE_ANSWER);
        db.execSQL(CREATE_CHOICE);
        db.execSQL("INSERT INTO " + TABLE_APPLICATION + " (" +
                COLUMN_IS_DATA_LOADED + ") " +
                "VALUES (0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public void firstRun(Context context) {
        this.clearDB();
        this.createTables();
//        this.pushData();
        if (!this.isDataLoaded()) {
            try {
//                JSONObject jsonObject = new JSONObject(this.loadJSONFromAsset(context));
                JSONObject jsonQuestionsObject = new JSONObject(this.loadQuestionJSONFromResources(context));
//                Log.d("firstRun, jsonObject", jsonObject.toString());
                JSONArray jsonQuestions = jsonQuestionsObject.getJSONArray("questions");
                this.loadQuestionsFromJSON(jsonQuestions);

                JSONObject jsonAnswersObject = new JSONObject(this.loadAnswersJSONFromResources(context));
                JSONArray jsonAnswers = jsonAnswersObject.getJSONArray("answers");
                this.loadAnswersFromJson(jsonAnswers);
                this.setDataLoaded(true);
            } catch (JSONException e) {
                Log.d("JSONEXCEPTION", e.getLocalizedMessage());
            }
        }
    }

    public String loadQuestionJSONFromResources(Context context) {
        String json = null;
        try {
//            InputStream is = context.getAssets().open("questions.json");
            InputStream is = context.getResources().openRawResource(R.raw.questions);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            json = writer.toString();
//            String jsonString = writer.toString();
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadAnswersJSONFromResources(Context context) {
        String json = null;
        try {
//            InputStream is = context.getAssets().open("questions.json");
            InputStream is = context.getResources().openRawResource(R.raw.answers);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            json = writer.toString();
//            String jsonString = writer.toString();
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean isDataLoaded() {
        String query = "SELECT " + COLUMN_IS_DATA_LOADED + " FROM " + TABLE_APPLICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                int isDataLoaded = Integer.parseInt(cursor.getString(0));
                if (isDataLoaded != 0) {

                    cursor.close();
                    db.close();
                    return true;
                } else {
                    //this.setDataLoaded(true);
                    cursor.close();
                    db.close();
                    return false;
                }
            }
        } catch (SQLException e) {
            Log.e("DATABASE ERROR", e.getLocalizedMessage());
            return false;
        }
        db.close();
        return false;
    }

    private void setDataLoaded(boolean isLoaded) {
        int loaded = -1;
        if (isLoaded) {
            loaded = 1;
        } else {
            loaded = 0;
        }
        String query = "UPDATE " + TABLE_APPLICATION +
                " SET " + COLUMN_IS_DATA_LOADED + " = " + Integer.toString(loaded);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        db.close();
    }

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
            String picture = cursor.getString(3);
            result.add(new Question(id, questionString, rightAnswer, picture));
//            result += String.valueOf(result_0) + " " + result_1 +
//                    System.getProperty("line.separator");
        }  while (cursor.moveToNext());
        cursor.close();
        db.close();
        return result;
    }

    public int countNumberOfQuestions() {
        String query = "SELECT COUNT(*) FROM " + TABLE_QUESTION;
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

    public void addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_ID, question.getQuestionID());
        values.put(COLUMN_QUESTION_STRING, question.getQuestionString());
        values.put(COLUMN_RIGHT_ANSWER, question.getRightAnswerID());
        values.put(COLUMN_QUESTION_PICTURE, question.getPicture());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_QUESTION, null, values);
        db.close();
    }

    private void addQuestion(JSONObject object) {
        Question question = new Question();
        try {
            question.setQuestionID(object.getInt("questionID"));
            question.setQuestionString(object.getString("questionString"));
            question.setRightAnswerID(object.getInt("rightAnswer"));
            question.setPicture(object.getString("picture"));
            this.addQuestion(question);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadQuestionsFromJSON(JSONArray jsonQuestions) {
        for (int i=0; i < jsonQuestions.length(); i++) {
            try {
                this.addQuestion(jsonQuestions.getJSONObject(i));
            } catch (JSONException e)  {
                e.printStackTrace();
            }
        }
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
            question.setPicture(cursor.getString(3));
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
            question.setPicture(cursor.getString(3));
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

    public int updateQuestion(int ID, String name, int answerID, String picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_QUESTION_ID, ID);
        args.put(COLUMN_QUESTION_STRING, name);
        args.put(COLUMN_RIGHT_ANSWER, answerID);
        args.put(COLUMN_QUESTION_PICTURE, picture);
        return db.update(TABLE_QUESTION, args,COLUMN_QUESTION_ID + "=" + ID, null);
    }

    public int updateQuestion(Question question) {
        return updateQuestion(question.getQuestionID(),
                question.getQuestionString(),
                question.getRightAnswerID(),
                question.getPicture());
    }

    public int updateQuestionWithRightAnswer(Question question, Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_QUESTION_ID, question.getQuestionID());
        args.put(COLUMN_QUESTION_STRING, question.getQuestionString());
        args.put(COLUMN_RIGHT_ANSWER, answer.getAnswerID());
        args.put(COLUMN_QUESTION_PICTURE, question.getPicture());
        return db.update(TABLE_QUESTION, args,COLUMN_QUESTION_ID + "=" + question.getQuestionID(), null);
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
        int rightAnswerID = -1;
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String answerString = cursor.getString(1);
            int isRightAnswer = cursor.getInt(2);
            if (isRightAnswer != 0) {
                rightAnswerID = id;
            }
            result.add(new Answer(id, answerString, isRightAnswer, questionID));
        } while (cursor.moveToNext());
        Log.d("DBHANDLER", "right answer: " + rightAnswerID);
        cursor.close();
        query = "SELECT " + COLUMN_RIGHT_ANSWER +
                " FROM " + TABLE_QUESTION +
                " WHERE " + COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
        cursor = db.rawQuery(query, null);
        boolean updateQuestion = false;
        int existingRightAnswer = -1;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            existingRightAnswer = Integer.parseInt(cursor.getString(0));
            Log.d("DBHANDLER", "existing answer: " + existingRightAnswer);
            if (existingRightAnswer == -1) {
                updateQuestion = true;
            }
            cursor.close();
        }
        if (updateQuestion) {
            query = "UPDATE " + TABLE_QUESTION +
                    " SET " + COLUMN_RIGHT_ANSWER + " = " + Integer.toString(rightAnswerID) +
                    " WHERE " + COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
            Log.d("DBHANDLER", "change right answer id query: " + query);
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
            }
            cursor.close();

            //debug
            query = "SELECT " + COLUMN_RIGHT_ANSWER +
                    " FROM " + TABLE_QUESTION +
                    " WHERE " + COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
            cursor = db.rawQuery(query, null);
//            boolean updateQuestion = false;
//            int existingRightAnswer = -1;
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                existingRightAnswer = Integer.parseInt(cursor.getString(0));
                Log.d("!!!!!!!!DBHANDLER", "existing answer: " + existingRightAnswer);
//                if (existingRightAnswer == -1) {
//                    updateQuestion = true;
//                }
                cursor.close();
            }

        }
//        db.close();
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

    private void addAnswer(JSONObject object) {
        Answer answer = new Answer();
        try {
            answer.setAnswerID(object.getInt("answerID"));
            answer.setQuestionID(object.getInt("questionID"));
            answer.setAnswerString(object.getString("answerString"));
            answer.setRightAnswer(object.getInt("answerIsRight"));
            this.addAnswer(answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadAnswersFromJson(JSONArray jsonObjects) {
        for (int i=0; i < jsonObjects.length(); i++) {
            try {
                this.addAnswer(jsonObjects.getJSONObject(i));
            } catch (JSONException e)  {
                e.printStackTrace();
            }
        }
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
////
////    public boolean deleteAnswer(int ID) {
//// --Commented out by Inspection START (19/03/2019, 10.09):
// --Commented out by Inspection STOP (19/03/2019, 10.09)
////        boolean result = false;
////        String query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_ANSWER_ID + "= '" + String.valueOf(ID) + "'";
////        SQLiteDatabase db = this.getWritableDatabase();
// --Commented out by Inspection STOP (19/03/2019, 10.09)
//        Cursor cursor = db.rawQuery(query, null);
//        Answer answer = new Answer();
//        if (cursor.moveToFirst()) {
//            answer.setQuestionID(Integer.parseInt(cursor.getString(0)));
//            db.delete(TABLE_ANSWER, COLUMN_ANSWER_ID + "=?",
//// --Commented out by Inspection START (19/03/2019, 10.09):
// --Commented out by Inspection STOP (19/03/2019, 10.09)
//                    new String[] { String.valueOf(answer.getAnswerID()) });
//            cursor.close();
//            result = true;
//        }
//        db.close();
//        return result;
// --Commented out by Inspection STOP (19/03/2019, 10.09)
//    }

//    public boolean deleteAnswer(Answer answer) {
//        return deleteQuestion(answer.getAnswerID());
//    }

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



    //Choices
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
            db.close();
            cursor = null;
            return new Choice(id, questionID, answerID, isRightAnswer);
        }
        return null;
    }

    //Updates a choice if exists already, otherwise inserts new choice
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

    //Deletes all pre-existing choices. Called when starting a new exam.
    public void deleteAllChoices() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER_CHOICE);
        Log.i("MBDATAHANDLER", "Choices deleted");
    }

    public int countChoices() {
        String query = "SELECT COUNT(*) FROM " + TABLE_USER_CHOICE;
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
        db.close();
        cursor = null;
        return choices;
    }
}

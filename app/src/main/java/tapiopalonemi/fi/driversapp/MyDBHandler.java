package tapiopalonemi.fi.driversapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

class MyDBHandler extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "driverdatabase8.db";
    private static final String TABLE_APPLICATION = "application";
    private static final String TABLE_QUESTION = "question";
    private static final String TABLE_QUESTION_SE = "questionSE";
    private static final String TABLE_ANSWER = "answer";
    private static final String TABLE_ANSWER_SE = "answerSE";
    private static final String TABLE_USER_CHOICE = "userChoice";
    private static final String COLUMN_APPLICATION_ID = "applicationID";
    private static final String COLUMN_IS_DATA_LOADED = "isDataLoaded";
    private static final String COLUMN_LAST_ANSWERED_QUESTION = "lastAnsweredQuestion";
    private static final String COLUMN_IS_FINNISH_QUESTIONS = "isFinnishQuestions";
    private static final String COLUMN_QUESTION_ID = "questionID";
    private static final String COLUMN_QUESTION_STRING = "questionString";
    private static final String COLUMN_QUESTION_PICTURE = "picture";
    private static final String COLUMN_RIGHT_ANSWER = "rightAnswer";
    private static final String COLUMN_ANSWER_ID = "answerID";
    private static final String COLUMN_ANSWER_STRING = "answerString";
    private static final String COLUMN_ANSWER_IS_RIGHT = "answerIsRight";
//    public static final String COLUMN_ANSWER_CHOSEN = "answerChosen";
    private static final String COLUMN_CHOICE_ID = "choiceID";


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    private void clearDB() {
        Log.i("DB", "clear database");
        String dropApplication = "DROP TABLE IF EXISTS " + TABLE_APPLICATION;
        String dropQuestions = "DROP TABLE IF EXISTS " + TABLE_QUESTION;
        String dropSEQuestions = "DROP TABLE IF EXISTS " + TABLE_QUESTION_SE;
        String dropAnswers = "DROP TABLE IF EXISTS " + TABLE_ANSWER;
        String dropSEAnswers = "DROP TABLE IF EXISTS " + TABLE_ANSWER_SE;
        String dropChoices = "DROP TABLE IF EXISTS " + TABLE_USER_CHOICE;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(dropApplication);
        db.execSQL(dropQuestions);
        db.execSQL(dropSEQuestions);
        db.execSQL(dropAnswers);
        db.execSQL(dropSEAnswers);
        db.execSQL(dropChoices);
    }

    private void pushData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("MYDBHANDLER", "push Data");

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


    //Creates database tables and adds some default information
    private void createTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_QUESTION = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + "(" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_QUESTION_STRING + " VARCHAR, " +
                COLUMN_RIGHT_ANSWER + " INTEGER, " +
                COLUMN_QUESTION_PICTURE + " VARCHAR " +
                ")";
        String CREATE_QUESTION_SE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION_SE + "(" +
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
        String CREATE_ANSWER_SE = "CREATE TABLE IF NOT EXISTS " + TABLE_ANSWER_SE + "(" +
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
                COLUMN_APPLICATION_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_IS_DATA_LOADED + " INTEGER, " +
                COLUMN_LAST_ANSWERED_QUESTION+ " INTEGER, " +
                COLUMN_IS_FINNISH_QUESTIONS + " INTEGER" +
                ")";
        db.execSQL(CREATE_APPLICATION);
        db.execSQL(CREATE_QUESTION);
        db.execSQL(CREATE_QUESTION_SE);
        db.execSQL(CREATE_ANSWER);
        db.execSQL(CREATE_ANSWER_SE);
        db.execSQL(CREATE_CHOICE);
        db.execSQL("INSERT INTO " + TABLE_APPLICATION + " (" +
                COLUMN_APPLICATION_ID + ", " +
                COLUMN_IS_DATA_LOADED + ", " +
                COLUMN_LAST_ANSWERED_QUESTION + ", " +
                COLUMN_IS_FINNISH_QUESTIONS + ") " +
                "VALUES (1, 0, -1, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    //Some housekeeping tasks when the app is run for the first time
    //including creating database tables, loading data to database from json files.
    public void firstRun(Context context) {
        this.clearDB();
        this.createTables();
//        this.pushData();
        if (this.isNotDataLoaded()) {
            try {
                JSONObject jsonObject =
                        new JSONObject(this.loadJSONFromResources(context, R.raw.questions));
                JSONArray jsonArray = jsonObject.getJSONArray("questions");
                this.loadQuestionsFromJSON(jsonArray, true);

                jsonObject =
                        new JSONObject(this.loadJSONFromResources(context, R.raw.questions_se));
                jsonArray = jsonObject.getJSONArray("questions");
                this.loadQuestionsFromJSON(jsonArray, false);

                jsonObject = new JSONObject(this.loadJSONFromResources(context, R.raw.answers));
                jsonArray = jsonObject.getJSONArray("answers");
                this.loadAnswersFromJson(jsonArray, true);
                this.setDataIsLoaded();

                jsonObject = new JSONObject(this.loadJSONFromResources(context, R.raw.answers_se));
                jsonArray = jsonObject.getJSONArray("answers");
                this.loadAnswersFromJson(jsonArray, false);
                this.setDataIsLoaded();
            } catch (JSONException e) {
                Log.d("JSONEXCEPTION", e.getLocalizedMessage());
            }
        }
    }

    //Loads JSON file from resource a given resource.
    private String loadJSONFromResources(Context context, int resource) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(resource);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            json = writer.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //Checks if data loaded flag is set in database.
    public boolean isNotDataLoaded() {
        String query = "SELECT " + COLUMN_IS_DATA_LOADED + " FROM " + TABLE_APPLICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                int isDataLoaded = Integer.parseInt(cursor.getString(0));
                if (isDataLoaded != 0) {
                    cursor.close();
//                    db.close();
//                    Log.i("DB", "data is loaded");
                    return false;
                } else {
//                    Log.i("DB", "data is NOT loaded");
                    cursor.close();
//                    db.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            Log.e("DATABASE ERROR", e.getLocalizedMessage());
            return true;
        }
//        db.close();
        return true;
    }

    //Sets data loaded flag (set to 'true after data is loaded from JSON files) in database.
    private void setDataIsLoaded() {
        String query = "UPDATE " + TABLE_APPLICATION +
                " SET " + COLUMN_IS_DATA_LOADED + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //DEBUG
//        query = "SELECT " + COLUMN_IS_DATA_LOADED + " FROM " + TABLE_APPLICATION;
//        Cursor cursor1 = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            int isDataLoaded = Integer.parseInt(cursor.getString(0));
//            Log.d("DB", "data loaded is: " + isDataLoaded);
//        }
        cursor.close();
    }

    //Gets the id for the question that was last answered by user.
    public int getLastAnsweredQuestion() {
        SQLiteDatabase db = this.getReadableDatabase();
        int lastQuestionID = -1;
        String query = "SELECT " + COLUMN_LAST_ANSWERED_QUESTION +
                " FROM " + TABLE_APPLICATION +
                " WHERE " + COLUMN_APPLICATION_ID + " = 1";
//                " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            lastQuestionID = Integer.parseInt(cursor.getString(0));
        }
//        Log.i("DB", "get last answered question: " + lastQuestionID);
        cursor.close();
        return lastQuestionID;
    }

    //Sets the id for the question the user answered last.
    public void setLastAnsweredQuestion(int questionID) {
        Log.i("DB", "set last answered question: " + questionID);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_ANSWERED_QUESTION, questionID);
        String where = COLUMN_APPLICATION_ID + " = 1";
        db.update(TABLE_APPLICATION, values, where, null);
    }

    public boolean isFinnishQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        int isFinnishQuestions = -1;
        String query = "SELECT " + COLUMN_IS_FINNISH_QUESTIONS +
                " FROM " + TABLE_APPLICATION +
                " WHERE " + COLUMN_APPLICATION_ID + " = 1";
//                " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            isFinnishQuestions = Integer.parseInt(cursor.getString(0));
        }
//        Log.i("DB", "get last answered question: " + lastQuestionID);
        cursor.close();
        return isFinnishQuestions == 1;
    }

    //Sets the id for the question the user answered last.
    public void setIsFinnishQuestions(boolean isFinnish) {
        Log.i("DB", "set last answered question: " + isFinnish);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_FINNISH_QUESTIONS, isFinnish ? 1 : 0);
        String where = COLUMN_APPLICATION_ID + " = 1";
        db.update(TABLE_APPLICATION, values, where, null);
    }

    //Loads all the question for a Finnish or Swedish test.
    public ArrayList<Question> loadQuestions(boolean finnish) {
        String query;
        ArrayList<Question> result = new ArrayList<>();
        if (finnish) {
            query = "SELECT * FROM " + TABLE_QUESTION;
        } else {
            query = "SELECT * FROM " + TABLE_QUESTION_SE;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String questionString = cursor.getString(1);
            int rightAnswer = cursor.getInt(2);
            String picture = cursor.getString(3);
            result.add(new Question(id, questionString, rightAnswer, picture));
        }  while (cursor.moveToNext());
        cursor.close();
        return result;
    }

    //Returns the number of question for chosen language (to be shown in the UI.)
    public int countNumberOfQuestions(boolean finnish) {
        String query;
        if (finnish) {
            query = "SELECT COUNT(*) FROM " + TABLE_QUESTION;
        } else  {
            query = "SELECT COUNT(*) FROM " + TABLE_QUESTION_SE;
        }
        Log.i("MYDBHANDLER", "count number of questions: " + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = -1;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
        } else {
            count = -1;
        }
        cursor.close();
        return count;
    }

    //Adds a question to database, either in Finnish or Swedish table of questions.
    private void addQuestion(Question question, boolean finnish) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_ID, question.getQuestionID());
        values.put(COLUMN_QUESTION_STRING, question.getQuestionString());
        values.put(COLUMN_RIGHT_ANSWER, question.getRightAnswerID());
        values.put(COLUMN_QUESTION_PICTURE, question.getPicture());
        SQLiteDatabase db = this.getWritableDatabase();
        if (finnish) {
            db.insert(TABLE_QUESTION, null, values);
        } else {
            db.insert(TABLE_QUESTION_SE, null, values);
        }
    }

    //Parses question from JSON data and calls addQuestion to add it to database.
    private void addQuestion(JSONObject object, boolean finnish) {
        Question question = new Question();
        try {
            question.setQuestionID(object.getInt("questionID"));
            question.setQuestionString(object.getString("questionString"));
            question.setRightAnswerID(object.getInt("rightAnswer"));
            question.setPicture(object.getString("picture"));
            this.addQuestion(question, finnish);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Loops through JSON data and calls a method to parse and save each question.
    private void loadQuestionsFromJSON(JSONArray jsonQuestions, boolean finnish) {
        for (int i=0; i < jsonQuestions.length(); i++) {
            try {
                this.addQuestion(jsonQuestions.getJSONObject(i), finnish);
            } catch (JSONException e)  {
                e.printStackTrace();
            }
        }
    }

//    //Finds and returns a question (either from Finnish or Swedish exam question) by a question string.
//    public Question findQuestionBy(String questionString, boolean finnish) {
//        String query;
//        if (finnish) {
//            query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_STRING + " = " + "'" + questionString + "'";
//        } else {
//            query = "SELECT * FROM " + TABLE_QUESTION_SE + " WHERE " + COLUMN_QUESTION_STRING + " = " + "'" + questionString + "'";
//        }
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Question question = new Question();
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            question.setQuestionID(Integer.parseInt(cursor.getString(0)));
//            question.setQuestionString(cursor.getString(1));
//            question.setRightAnswerID(Integer.parseInt(cursor.getString(2)));
//            question.setPicture(cursor.getString(3));
//        } else {
//            question = null;
//        }
//        cursor.close();
//        return question;
//    }

    //Finds and returns a question (either from Finnish or Swedish exam question) by a question id.
    public Question findQuestionBy(int questionID, boolean finnish) {
        String query;
        if (finnish) {
            query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_ID + " = " + "'" + questionID + "'";
        } else {
            query = "SELECT * FROM " + TABLE_QUESTION_SE + " WHERE " + COLUMN_QUESTION_ID + " = " + "'" + questionID + "'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Question question = new Question();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            question.setQuestionID(Integer.parseInt(cursor.getString(0)));
            question.setQuestionString(cursor.getString(1));
            question.setRightAnswerID(Integer.parseInt(cursor.getString(2)));
            question.setPicture(cursor.getString(3));
        } else {
            question = null;
        }
        cursor.close();
        return question;
    }

//    //Deletes a question (either from the Finnish or Swedish questions tabel) by question ID.
//    private boolean deleteQuestion(int ID, boolean finnish) {
//        boolean result = false;
//        String query;
//        if (finnish) {
//            query = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_ID + "= '" + String.valueOf(ID) + "'";
//        } else {
//            query = "SELECT * FROM " + TABLE_QUESTION_SE + " WHERE " + COLUMN_QUESTION_ID + "= '" + String.valueOf(ID) + "'";
//        }
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Question question = new Question();
//        if (cursor.moveToFirst()) {
//            question.setQuestionID(Integer.parseInt(cursor.getString(0)));
//            db.delete(TABLE_QUESTION, COLUMN_QUESTION_ID + "=?",
//                    new String[] { String.valueOf(question.getQuestionID()) });
//            result = true;
//        }
//        cursor.close();
//        return result;
//    }

//    //Deletes a question from the database.
//    public boolean deleteQuestion(Question question, boolean finnish) {
//        return deleteQuestion(question.getQuestionID(), finnish);
//    }

    //Updates a question (either in Finnish or Swedish exam questions).
    private int updateQuestion(int ID, String name, int answerID, String picture, boolean finnish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_QUESTION_ID, ID);
        args.put(COLUMN_QUESTION_STRING, name);
        args.put(COLUMN_RIGHT_ANSWER, answerID);
        args.put(COLUMN_QUESTION_PICTURE, picture);
        if (finnish) {
            return db.update(TABLE_QUESTION, args, COLUMN_QUESTION_ID + "=" + ID, null);
        } else {
            return db.update(TABLE_QUESTION_SE , args, COLUMN_QUESTION_ID + "=" + ID, null);
        }
    }

    //Updates a question in database.
    public void updateQuestion(Question question, boolean finnish) {
        updateQuestion(question.getQuestionID(),
                question.getQuestionString(),
                question.getRightAnswerID(),
                question.getPicture(), finnish);
    }

//    //Updates a question's right answer field.
//    public int updateQuestionWithRightAnswer(Question question, Answer answer, boolean finnish) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues args = new ContentValues();
//        args.put(COLUMN_QUESTION_ID, question.getQuestionID());
//        args.put(COLUMN_QUESTION_STRING, question.getQuestionString());
//        args.put(COLUMN_RIGHT_ANSWER, answer.getAnswerID());
//        args.put(COLUMN_QUESTION_PICTURE, question.getPicture());
//        if (finnish) {
//            return db.update(TABLE_QUESTION, args, COLUMN_QUESTION_ID + "=" + question.getQuestionID(), null);
//        } else {
//            return db.update(TABLE_QUESTION, args, COLUMN_QUESTION_ID + "=" + question.getQuestionID(), null);
//        }
//    }

    //ANSWERS
//    //Loads all answers either from Finnish or Swedish exam answers table.
//    public ArrayList<Answer> loadAllAnswers(boolean finnish) {
//        ArrayList<Answer> result = new ArrayList<>();
//        String query;
//        if (finnish) {
//            query = "SELECT * FROM " + TABLE_ANSWER;
//        } else {
//            query = "SELECT * FROM " + TABLE_ANSWER_SE;
//        }
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//        do {
//            int id = cursor.getInt(0);
//            String answerString = cursor.getString(1);
//            int isRightAnswer = cursor.getInt(2);
//            int questionID = cursor.getInt(3);
//            result.add(new Answer(id, answerString, isRightAnswer, questionID));
//        } while (cursor.moveToNext());
//        cursor.close();
//        return result;
//    }

    //Loads all answers for a given question id from either Finnish or Swedish answer table.
    public ArrayList<Answer> loadAnswersForQuestion(int questionID, boolean finnish) {
        ArrayList<Answer> result = new ArrayList<>();
        //Define query
        String query;
        if (finnish) {
            query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_QUESTION_ID + " = " + questionID;
        } else {
            query = "SELECT * FROM " + TABLE_ANSWER_SE + " WHERE " + COLUMN_QUESTION_ID + " = " + questionID;
        }
        //Create cursor for query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rightAnswerID = -1;
        cursor.moveToFirst();
        //Loop result and add to array list.
        do {
            int id = cursor.getInt(0);
            String answerString = cursor.getString(1);
            int isRightAnswer = cursor.getInt(2);
            if (isRightAnswer != 0) {
                rightAnswerID = id;
            }
            result.add(new Answer(id, answerString, isRightAnswer, questionID));
        } while (cursor.moveToNext());
//        Log.d("DBHANDLER", "right answer: " + rightAnswerID);
        cursor.close();

        //Get right answer id for the question.
        if (finnish) {
            query = "SELECT " + COLUMN_RIGHT_ANSWER +
                    " FROM " + TABLE_QUESTION +
                    " WHERE " + COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
        } else {
            query = "SELECT " + COLUMN_RIGHT_ANSWER +
                    " FROM " + TABLE_QUESTION_SE +
                    " WHERE " + COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
        }
        cursor = db.rawQuery(query, null);
        //Check for existing right answer
        boolean updateQuestion = false;
        int existingRightAnswer = -1;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            existingRightAnswer = Integer.parseInt(cursor.getString(0));
//            Log.d("DBHANDLER", "existing answer: " + existingRightAnswer);
            if (existingRightAnswer == -1) {
                updateQuestion = true;
            }
            cursor.close();
        }
        //If there is not yet right answer defined, set the right answer
        if (updateQuestion) {
            if (finnish) {
                query = "UPDATE " + TABLE_QUESTION +
                        " SET " + COLUMN_RIGHT_ANSWER + " = " + Integer.toString(rightAnswerID) +
                        " WHERE " + COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
            } else {
                query = "UPDATE " + TABLE_QUESTION_SE +
                        " SET " + COLUMN_RIGHT_ANSWER + " = " + Integer.toString(rightAnswerID) +
                        " WHERE " + COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
            }
            Log.d("DBHANDLER", "change right answer id query: " + query);
            ContentValues values = new ContentValues();
            values.put(COLUMN_RIGHT_ANSWER, Integer.toString(rightAnswerID));
            String where = COLUMN_QUESTION_ID + " = " + Integer.toString(questionID);
            db.update(TABLE_QUESTION, values, where, null);
        }
        cursor.close();
        return result;
    }

    //Load answers for question
    public ArrayList<Answer> loadAnswersForQuestion(Question question, boolean finnish) {
        return loadAnswersForQuestion(question.getQuestionID(), finnish);
    }

    //Add answer to database for Finnish or Swedish exam answers table
    private void addAnswer(Answer answer, boolean finnish) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ANSWER_ID, answer.getAnswerID());
        values.put(COLUMN_ANSWER_STRING, answer.getAnswerString());
        values.put(COLUMN_ANSWER_IS_RIGHT, answer.isRightAnswer());
        values.put(COLUMN_QUESTION_ID, answer.getQuestionID());
        SQLiteDatabase db = this.getWritableDatabase();
        if (finnish) {
            db.insert(TABLE_ANSWER, null, values);
        } else {
            db.insert(TABLE_ANSWER_SE, null, values);
        }
    }

    //Parse answer from JSON object and save it to database.
    private void addAnswer(JSONObject object, boolean finnish) {
        Answer answer = new Answer();
        try {
            answer.setAnswerID(object.getInt("answerID"));
            answer.setQuestionID(object.getInt("questionID"));
            answer.setAnswerString(object.getString("answerString"));
            answer.setRightAnswer(object.getInt("answerIsRight"));
            this.addAnswer(answer, finnish);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Loop through JSON array and save answers to database.
    private void loadAnswersFromJson(JSONArray jsonObjects, boolean finnish) {
        for (int i=0; i < jsonObjects.length(); i++) {
            try {
                this.addAnswer(jsonObjects.getJSONObject(i), finnish);
            } catch (JSONException e)  {
                e.printStackTrace();
            }
        }
    }

    //Find answer by answer string.
//    public Answer findAnswerBy(String answerString, boolean finnish) {
//        String query;
//        if (finnish) {
//            query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_ANSWER_STRING + " = " + "'" + answerString + "'";
//        } else {
//            query = "SELECT * FROM " + TABLE_ANSWER_SE + " WHERE " + COLUMN_ANSWER_STRING + " = " + "'" + answerString + "'";
//        }
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Answer answer = new Answer();
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            answer.setQuestionID(Integer.parseInt(cursor.getString(0)));
//            answer.setAnswerString(cursor.getString(1));
//            answer.setRightAnswer(Integer.parseInt(cursor.getString(2)));
//            answer.setQuestionID(Integer.parseInt(cursor.getString(3)));
////            cursor.close();
//        } else {
//            answer = null;
//        }
//        cursor.close();
//        return answer;
//    }

    //Find answer by answer id from Finnish or Swedish exam answers.
    public Answer findAnswerBy(int answerID, boolean finnish) {
        String query;
        if (finnish) {
            query = "SELECT * FROM " + TABLE_ANSWER + " WHERE " + COLUMN_ANSWER_ID + " = " + "'" + answerID + "'";
        } else {
            query = "SELECT * FROM " + TABLE_ANSWER_SE + " WHERE " + COLUMN_ANSWER_ID + " = " + "'" + answerID + "'";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Answer answer = new Answer();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            answer.setAnswerID(Integer.parseInt(cursor.getString(0)));
            answer.setAnswerString(cursor.getString(1));
            answer.setRightAnswer(Integer.parseInt(cursor.getString(2)));
            answer.setQuestionID(Integer.parseInt(cursor.getString(3)));
        } else {
            answer = null;
        }
        cursor.close();
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
//
//    public boolean deleteAnswer(Answer answer) {
//        return deleteQuestion(answer.getAnswerID());
//    }

//    //Update answer in Finnish or Swedish answers table
//    private int updateAnswer(int ID, String name, int answerID, int questionID, boolean finnish) {
//        Log.i("DB", "update answer, answerID: " + ID + ", questionID: " + questionID +
//                ", answerString: " + name + ", ");
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues args = new ContentValues();
//        args.put(COLUMN_ANSWER_ID, ID);
//        args.put(COLUMN_QUESTION_ID, questionID);
//        args.put(COLUMN_ANSWER_STRING, name);
////        args.put(COLUMN_ANSWER_IS_RIGHT, answerID);
//        if (finnish) {
//            return db.update(TABLE_ANSWER, args, COLUMN_ANSWER_ID + " = " + ID, null);
//        } else {
//            return db.update(TABLE_ANSWER_SE, args, COLUMN_ANSWER_ID + " = " + ID, null);
//        }
//    }
//
//    //Update answer in Finnish or Swedish answers table
//    public int updateAnswer(Answer answer, boolean finnish) {
//        return updateAnswer(answer.getAnswerID(), answer.getAnswerString(), answer.isRightAnswer(), answer.getQuestionID(), finnish);
//    }



    //Choices
    //Get choice from choices table by question id.
    public Choice getChoiceByQuestionID(int questionID) {
        String query = "SELECT * FROM " + TABLE_USER_CHOICE + " WHERE " + COLUMN_QUESTION_ID + " = " + questionID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            int answerID = cursor.getInt(2);
            int isRightAnswer = cursor.getInt(3);
            cursor.close();
            return new Choice(id, questionID, answerID, isRightAnswer);
        }
        cursor.close();
        return null;
    }

    //Updates a choice if it exists already, otherwise inserts new choice
    public void addChoice(Question question, Answer answer) {
        if (question != null && answer != null) {
            Choice oldChoice = getChoiceByQuestionID(question.getQuestionID());
            ContentValues values = new ContentValues();
            values.put(COLUMN_ANSWER_ID, answer.getAnswerID());
            values.put(COLUMN_QUESTION_ID, question.getQuestionID());
            values.put(COLUMN_ANSWER_IS_RIGHT, answer.isRightAnswer());
            Log.i("DB", "add choice, questionID: " + question.getQuestionID() +
                            ", answerID: " + answer.getAnswerID() +
                    ", answer is right: " + answer.isRightAnswer()
                    );
            SQLiteDatabase db = this.getWritableDatabase();
            if (oldChoice == null) {
                db.insert(TABLE_USER_CHOICE, null, values);
                Log.i("MBDATAHANDLER", "Choice added");

            } else {
                db.update(TABLE_USER_CHOICE, values, COLUMN_QUESTION_ID + "=" + question.getQuestionID(), null);
                Log.i("MBDATAHANDLER", "Choice updated");
            }
//            if (getLastAnsweredQuestion() < question.getQuestionID()) {
                setLastAnsweredQuestion(question.getQuestionID());
//            }
            //DEBUG
//            Log.i("DB", "last answered question: " + getLastAnsweredQuestion());
        } else {
            Log.i("MYDATAHANDLER", "no question or answer");
        }

    }

    //Deletes all pre-existing choices. Called when starting a new exam.
    public void deleteAllChoices() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER_CHOICE);
//        Log.i("MBDATAHANDLER", "Choices deleted");
    }

    //Returns the number of choices in choices table.
//    public int countChoices() {
//        String query = "SELECT COUNT(*) FROM " + TABLE_USER_CHOICE;
////        Log.i("MYDBHANDLER", "count choices: " + query);
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        int count = -1;
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            count = Integer.parseInt(cursor.getString(0));
//            cursor.close();
//        } else {
//            count = -1;
//        }
//        cursor.close();
//        return count;
//    }

    //Count the number of choices that had a right answer.
    public int countRightChoices() {
        String query = "SELECT COUNT(*) FROM " + TABLE_USER_CHOICE + " WHERE " + COLUMN_ANSWER_IS_RIGHT + " = " + 1;
        Log.i("MYDBHANDLER", "count right choices: " + query);
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
        cursor.close();
        return count;
    }

    //Load all the choices in choices table in database.
    public ArrayList<Choice> loadAllChoices(boolean finnish) {
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
                choices.add(new Choice(id, questionID, answerID, isRightAnswer, this, finnish));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return choices;
    }
}

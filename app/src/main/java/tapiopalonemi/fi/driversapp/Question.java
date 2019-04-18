package tapiopalonemi.fi.driversapp;

//import android.database.Cursor;

//import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
//import org.json.JSONObject;

import java.util.ArrayList;

public class Question {
    //Fields
    private int questionID;
    private String questionString;
    private ArrayList<Answer> answers;
    private Answer rightAnswer;
    private int rightAnswerID;
    private Answer chosenAnswer;
    private String picture;

//    public Question(int questionID) {
//        this.questionID = questionID;
//    }

    private boolean isAnswered = false;

    //Constructors
    public Question() { answers = new ArrayList<>(); }

    public Question(int questionID, String questionString) {
        this.questionID = questionID;
        this.questionString = questionString;
    }

//    public Question(int questionID, String questionString, Answer rightAnswer, String picture) {
//        this.questionID = questionID;
//        this.questionString = questionString;
//        this.rightAnswer = rightAnswer;
//        this.picture = picture;
//    }

    public Question(int questionID, String questionString, int rightAnswerID, String picture) {
        this.questionID = questionID;
        this.questionString = questionString;
        this.rightAnswerID = rightAnswerID;
        this.picture = picture;
    }

    private static ArrayList<Answer> answersFromJson(JSONArray jsonObjects) {
        ArrayList<Answer> answers = new ArrayList<>();
        for (int i=0; i < jsonObjects.length(); i++) {
            try {
                answers.add(new Answer(jsonObjects.getJSONObject(i)));
            } catch (JSONException e)  {
                e.printStackTrace();
            }
        }
        return answers;
    }


    //Getters & Setters
    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public int getRightAnswerID() {
        return rightAnswerID;
    }

    public void setRightAnswerID(int rightAnswerID) {
        this.rightAnswerID = rightAnswerID;
    }

    public Answer getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(Answer chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
        //this.rightAnswerID = chosenAnswer.getAnswerID();
        this.isAnswered = true;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

//    @androidx.annotation.NonNull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ID: ").append(this.questionID).append(" ").append(this.questionString)
                .append(", answerID: ").append(this.rightAnswerID);
        if (null != this.chosenAnswer) {
            result.append(", chosen answer: " + this.chosenAnswer.getAnswerID());
        }
        return result.toString();
    }
}

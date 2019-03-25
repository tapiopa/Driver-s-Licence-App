package tapiopalonemi.fi.driversapp;

//import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public Question(int questionID) {
        this.questionID = questionID;
    }

    private boolean isAnswered = false;

    //Constructors
    public Question() { answers = new ArrayList<>(); }
    public Question(int questionID, String questionString) {
        this.questionID = questionID;
        this.questionString = questionString;
    }

    public Question(int questionID, String questionString, Answer rightAnswer) {
        this.questionID = questionID;
        this.questionString = questionString;
        this.rightAnswer = rightAnswer;
    }

    public Question(int questionID, String questionString, int rightAnswerID) {
        this.questionID = questionID;
        this.questionString = questionString;
        this.rightAnswerID = rightAnswerID;
    }

    private Question(JSONObject object) {
        try {
            this.questionID = object.getInt("questionID");
            this.questionString = object.getString("questionString");
            this.rightAnswerID = object.getInt("rightAnswer");
            this.picture = object.getString("picture");
            this.answers = answersFromJson(object.getJSONArray("answers"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Question> questionsFromJson(JSONArray jsonObjects) {
        ArrayList<Question> questionList = new ArrayList<>();
        for (int i=0; i < jsonObjects.length(); i++) {
            try {
                questionList.add(new Question(jsonObjects.getJSONObject(i)));
            } catch (JSONException e)  {
                e.printStackTrace();
            }
        }
        return questionList;
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

    public void addAnswer(Answer answer) {
        if (this.answers == null) {
            this.answers = new ArrayList<>();
        }
        this.answers.add(answer);
    }

    public void removeAnswer(Answer answer) {
        if (this.answers != null) {
            this.answers.remove(answer);
        }
    }

    public Answer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Answer rightAnswer) {
        this.rightAnswer = rightAnswer;
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
}

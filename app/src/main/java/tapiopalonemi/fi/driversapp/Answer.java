package tapiopalonemi.fi.driversapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Answer {
    //Fields
    private int answerID;
    private String answerString;
    private int isRightAnswer;
    private int questionID;
    private Question question;

    public Answer() {}

    public Answer(int answerID, String answerString, int isRightAnswer, int questionID) {
        this.answerID = answerID;
        this.answerString = answerString;
        this.isRightAnswer = isRightAnswer;
        this.questionID = questionID;
    }

    public Answer(int answerID, String answerString,
                  int isRightAnswer, Question question) {
        this.answerID = answerID;
        this.answerString = answerString;
        this.isRightAnswer = isRightAnswer;
        this.question = question;
        this.questionID = question.getQuestionID();
    }

    public Answer(JSONObject object) {
        try {
            this.answerID = object.getInt("ID");
            this.answerString = object.getString("answer");
            this.isRightAnswer = object.getInt("isRight");
            this.questionID = object.getInt("questionID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public int isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getIsRightAnswer() {
        return isRightAnswer;
    }

    public void setIsRightAnswer(int isRightAnswer) {
        this.isRightAnswer = isRightAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

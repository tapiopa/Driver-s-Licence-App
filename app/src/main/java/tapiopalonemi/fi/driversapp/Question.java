package tapiopalonemi.fi.driversapp;

import android.database.Cursor;

import java.util.ArrayList;

public class Question {
    //Fields
    private int questionID;
    private String questionString;
    private ArrayList<Answer> answers;
    private Answer rightAnswer;
    private int rightAnswerID;
    private Answer chosenAnswer;

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

    public void findAnswersForQuestion() {

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
}

package tapiopalonemi.fi.driversapp;

public class Answer {
    //Fields
    private int answerID;
    private String answerString;
    private int isRightAnswer;
    private int questionID;

    public Answer() {}

    public Answer(int answerID, String answerString, int isRightAnswer, int questionID) {
        this.answerID = answerID;
        this.answerString = answerString;
        this.isRightAnswer = isRightAnswer;
        this.questionID = questionID;
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

}

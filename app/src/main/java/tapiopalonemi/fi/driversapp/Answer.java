package tapiopalonemi.fi.driversapp;

public class Answer {
    //Fields
    private int answerID;
    private String answerText;
    private int isRightAnswer;
    private int questionID;
    private int chosenAnswerID;

    public Answer() {}

    public Answer(int answerID, String answerText, int isRightAnswer, int questionID, int chosenAnswerID) {
        this.answerID = answerID;
        this.answerText = answerText;
        this.isRightAnswer = isRightAnswer;
        this.questionID = questionID;
        this.chosenAnswerID = chosenAnswerID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
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

    public int getChosenAnswerID() {
        return chosenAnswerID;
    }

    public void setChosenAnswerID(int chosenAnswerID) {
        this.chosenAnswerID = chosenAnswerID;
    }
}

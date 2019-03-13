package tapiopalonemi.fi.driversapp;

public class Question {
    //Fields
    private int questionID;
    private String questionString;

    //Constructors
    public Question() {}
    public Question(int questionID, String questionString) {
        this.questionID = questionID;
        this.questionString = questionString;
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
}

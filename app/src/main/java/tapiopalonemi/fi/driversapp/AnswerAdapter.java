package tapiopalonemi.fi.driversapp;

import android.content.Context;
//import android.graphics.Color;
//import android.util.Log;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class AnswerAdapter extends ArrayAdapter<Answer> {
    private MyDBHandler db;
    private Question question;

    public AnswerAdapter(Context context, @NotNull ArrayList<Answer> answers, Question question) {
        super(context, 0, answers);

        Log.d("ANSWER ADAPTER", "constructor");
        db = new MyDBHandler(context);
        this.question = question;
//        Question question = db.findQuestionBy(answers.get(0).getQuestionID(), db.isFinnishQuestions());
//        Log.d("ANSWER ADAPTER", "constructor, question: " + question.getQuestionString());
        Choice choice = db.getChoiceByQuestionID(question.getQuestionID());

        for (Answer answer : answers) {
            if (answer.getQuestionID() >= 0) {
                answer.setQuestion(question);
                if (null != choice && null != choice.getQuestion() &&
                        choice.getQuestion().getQuestionID() == answer.getQuestion().getQuestionID()) {
                    answer.getQuestion().setAnswered(true);
                }
            }
        }
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

//        Log.i("ANSWER ADAPTER", "was called 0");
        Answer answer = getItem(position);

        assert answer != null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_answer, parent, false);
        }
//        Log.i("ANSWER ADAPTER", "was called 1");
        TextView chosenAnswer = convertView.findViewById(R.id.chosen_answer);
        TextView answerString = convertView.findViewById(R.id.answer_string);
        TextView indexString = convertView.findViewById(R.id.abc);
        TextView usersAnswerView = parent.getRootView().findViewById(R.id.user_answer);
//        Log.i("ANSWER ADAPTER", "was called 2");
        answerString.setPadding(0, 12, 0, 12);
        chosenAnswer.setText(null);

        Log.i("ANSWER ADAPTER", "answer id: " + answer.getAnswerID());
//
//        if (null != answer.getQuestion()) {
//            Log.i("ANSWER ADAPTER", "QUESTION: " + answer.getQuestion().toString());
//        }
//        if (null != answer.getQuestion() &&
//                answer.getQuestion().isAnswered() &&
//                null != answer.getQuestion().getChosenAnswer() &&
//                answer.getQuestion().getChosenAnswer().getAnswerID() >= 0 &&
//                answer.ge tQuestion().getChosenAnswer().getAnswerID() == answer.getAnswerID()) {
        if (question != null && question.isAnswered() && question.getChosenAnswer() != null &&
            question.getChosenAnswer().getAnswerID() == answer.getAnswerID()) {
            Log.i("$$$$$$$$$ANSWER ADAPTER", "answered, answer id: " + answer.getAnswerID());
            Log.i("$$$$$$$$$ANSWER ADAPTER", "answered answer.questionID: " + answer.getQuestion().getQuestionID());
            Log.i("$$$$$$$$$ANSWER ADAPTER", "answered, answer.question.chosenAnswer ID: " + answer.getQuestion().getChosenAnswer().getAnswerID());
//            chosenAnswer.setBackgroundColor(Color.CYAN);

            chosenAnswer.setTextColor(Color.GREEN);
            chosenAnswer.setText(R.string.sym_right);
//            userAnswer.setText(answer.getAnswerString());
            if (usersAnswerView != null) {
                usersAnswerView.setText(convertToAlphabet(position));
            }
//            Log.i("%%%%%%%%%ANSWER ADAPTER", "chosen answer ID: " + answer.getAnswerID());
//            Log.i("%%%%%%%%%ANSWER ADAPTER", "chosenAnswer text: " + chosenAnswer.getText());
        } else {
//            chosenAnswer.setTextColor(Color.WHITE);
//            chosenAnswer.setText(R.string.sym_right);
//            Log.i("============ANSWER ADAPTER", "not this answer: " + answer.getAnswerID());
        }
//        Log.i("ANSWER ADAPTER", "was called 5");
        indexString.setText(convertToAlphabet(position));
        String answerText = answer.getAnswerString() + "  ";
//        Log.i("ANSWER ADAPTER", "answerText: " + answerText);
        answerString.setText(answerText);
//        markAnswer(position, convertView, parent);
//        Log.i("ANSWER ADAPTER", "was called 6");
        return convertView;
    }

    private String convertToAlphabet(int position) {
        String result = "";
        if (position >= 0 && position < 10) {
            switch (position) {
                case 0:
                    result += "a";
                    break;
                case 1:
                    result += "b";
                    break;
                case 2:
                    result += "c";
                    break;
                case 3:
                    result += "d";
                    break;
                case 4:
                    result += "e";
                    break;
                case 5:
                    result += "f";
                    break;
                case 6:
                    result += "g";
                    break;
                case 7:
                    result += "h";
                    break;
                case 8:
                    result += "i";
                    break;
                case 9:
                    result += "j";
                    break;
                default:
                    return result;
            }
            result += ". ";
        }
        return result;
    }

}//class

package tapiopalonemi.fi.driversapp;

import android.content.Context;
import android.graphics.Color;
//import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class AnswerAdapter extends ArrayAdapter<Answer> {

    public AnswerAdapter(Context context, @NotNull ArrayList<Answer> answers) {
        super(context, 0, answers);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        Answer answer = getItem(position);
        assert answer != null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_answer, parent, false);
        }
        TextView chosenAnswer = convertView.findViewById(R.id.chosen_answer);
        TextView answerString = convertView.findViewById(R.id.answer_string);
        TextView indexString = convertView.findViewById(R.id.abc);
        TextView usersAnswerView = parent.getRootView().findViewById(R.id.user_answer);
//        parent.getRootView()

        answerString.setPadding(0, 12, 0, 12);
//        TextView userAnswer = parent.findViewById(R.id.user_answer);


//        chosenAnswer.setText(R.string.sym_right);

        if (answer.getQuestion().isAnswered() &&
                answer.getQuestion().getChosenAnswer().getAnswerID() == answer.getAnswerID()) {
//            Log.i("ANSWER ADAPTER", "answer: " + answer.getAnswerID());
//            Log.i("ANSWER ADAPTER", "answer.question.chosenAnswer: " + answer.getQuestion().getQuestionID());
//            Log.i("ANSWER ADAPTER", "answer.question.chosenAnswer: " + answer.getQuestion().getChosenAnswer().getAnswerID());
//            chosenAnswer.setBackgroundColor(Color.CYAN);
//            chosenAnswer.setTextColor(Color.GREEN);
            chosenAnswer.setText(R.string.sym_right);
//            userAnswer.setText(answer.getAnswerString());
            if (usersAnswerView != null) {
                usersAnswerView.setText(convertToAlphabet(position));
            }
            Log.i("ANSWER ADAPTER", "chosen answer: " + answer.getAnswerID());
        } else {
//            chosenAnswer.setTextColor(Color.WHITE);
//            chosenAnswer.setText(R.string.sym_right);
//            Log.i("ANSWER ADAPTER", "not this answer: " + answer.getAnswerID());
        }
        indexString.setText(convertToAlphabet(position));
        String answerText = answer.getAnswerString() + "  ";
        answerString.setText(answerText);
//        markAnswer(position, convertView, parent);

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
                    result += "a";
                    break;
                default:
                    return result;
            }
            result += ". ";
        }
        return result;
    }

//    public void markAnswer(int position, View convertView, ViewGroup parent) {
////        TextView chosenAnswer = convertView.findViewById(R.id.chosen_answer);
////        TextView answerString = convertView.findViewById(R.id.answer_string);
//
//        for (int i = 0; i < this.getCount(); i++) {
////            Answer answer = getItem(i);
//            View view = this.getView(i, convertView, parent);
//            view.invalidate();
//
////            Log.i("ANSWER ADAPTER", "answer: " + answer.getAnswerString());
////            if (answer.getQuestion().isAnswered() &&
////                    answer.getQuestion().getChosenAnswer().getAnswerID() == answer.getAnswerID()) {
//////            chosenAnswer.setBackgroundColor(Color.CYAN);
////                chosenAnswer.setTextColor(Color.GREEN);
////                chosenAnswer.setText("✔ ️");
////            } else {
////                chosenAnswer.setTextColor(Color.WHITE);
////                chosenAnswer.setText("✔ ");
////            }
////            answerString.setText(answer.getAnswerString());
//        }
//
//    }
}//class

package tapiopalonemi.fi.driversapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChoiceAdapter extends ArrayAdapter<Choice> {

    public ChoiceAdapter(Context context, ArrayList<Choice> choices) {
        super(context, 0, choices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Choice choice = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_choice, parent, false);
        }
        TextView choiceQuestion = convertView.findViewById(R.id.choice_question);
        TextView choiceAnswer = convertView.findViewById(R.id.choice_answer);
        TextView choiceIsRight = convertView.findViewById(R.id.choice_answer_is_right);

//        TextView userAnswer = parent.findViewById(R.id.user_answer);
        choiceQuestion.setText(choice.getQuestion().getQuestionString());
        if (choice.getQuestion().isAnswered() &&
                choice.getAnswerIsRight() != 0) {
//            chosenAnswer.setBackgroundColor(Color.CYAN);
            choiceIsRight.setTextColor(Color.GREEN);
            choiceIsRight.setText("✔ ️");
//            userAnswer.setText(answer.getAnswerString());
        } else {
            choiceIsRight.setTextColor(Color.WHITE);
            choiceIsRight.setText("✔  ");
        }
        choiceAnswer.setText(choice.getAnswer().getAnswerString());
//        markAnswer(position, convertView, parent);
        return convertView;
    }

    public void markAnswer(int position, View convertView, ViewGroup parent) {
//        TextView chosenAnswer = convertView.findViewById(R.id.chosen_answer);
//        TextView answerString = convertView.findViewById(R.id.answer_string);

        for (int i = 0; i < this.getCount(); i++) {
//            Answer answer = getItem(i);
            View view = this.getView(i, convertView, parent);
            view.invalidate();

//            Log.i("ANSWER ADAPTER", "answer: " + answer.getAnswerString());
//            if (answer.getQuestion().isAnswered() &&
//                    answer.getQuestion().getChosenAnswer().getAnswerID() == answer.getAnswerID()) {
////            chosenAnswer.setBackgroundColor(Color.CYAN);
//                chosenAnswer.setTextColor(Color.GREEN);
//                chosenAnswer.setText("✔ ️");
//            } else {
//                chosenAnswer.setTextColor(Color.WHITE);
//                chosenAnswer.setText("✔ ");
//            }
//            answerString.setText(answer.getAnswerString());
        }

        return;
    }
}


package tapiopalonemi.fi.driversapp;

import android.content.Context;
import android.graphics.Color;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class AdAdapter extends ArrayAdapter<AdItem> {

    public AdAdapter(Context context, ArrayList<AdItem> choices) {
        super(context, 0, choices);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        AdItem ad = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_choice, parent, false);
        }
        TextView choiceQuestion = convertView.findViewById(R.id.choice_question);
        TextView choiceAnswer = convertView.findViewById(R.id.choice_answer);
        TextView choiceIsRight = convertView.findViewById(R.id.choice_answer_is_right);

//        TextView userAnswer = parent.findViewById(R.id.user_answer);
        assert ad != null;
        choiceQuestion.setTextSize(12.0f);
        choiceQuestion.setPadding(12, 12, 0, 0);

        choiceAnswer.setTextSize(12.0f);
        choiceAnswer.setPadding(0, 0, 0, 12);
        choiceIsRight.setTextSize(12.0f);
        choiceIsRight.setPadding(12, 0, 0, 12);

        return convertView;
    }


}



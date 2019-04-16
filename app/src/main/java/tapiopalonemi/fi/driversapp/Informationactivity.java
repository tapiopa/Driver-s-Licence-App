package tapiopalonemi.fi.driversapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.Button;


public class Informationactivity extends AppCompatActivity {


    private ArrayList<String[]> listOfLists;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final String[] INFORMATIONS = new String[]{
                "Adjust your mirrors correctly", "If the car don't start turn high beams on", "Use hand brake regularly",
                "Don’t turn the wheels beforehand when making a left turn", "Maintain required distance always from other vehicles",
                "Park with precision", "follow the vehicle of neighbouring lane"
        };

        ListView myListView= findViewById(R.id.information_list_view);

        final String[] INFORMATIONS2 = new String[]{
                "Be careful with signs on roads as well as traffic lights", "Always follow the speed limits",
                "Give pedestrians the right-of-way in crosswalks", "Always check your seat belt","Park with precision",
                "Keep winter Survival kit in winter time","plan route in advanced for long trips"

        };
        listOfLists = new ArrayList<>();
        listOfLists.add(INFORMATIONS);
        listOfLists.add(INFORMATIONS2);
        nextInformation(null);
        previousInformation(null);

    }

    private void showInformation(String[] information) {

        ListView myListView = findViewById(R.id.information_list_view);
        final ArrayAdapter<String> kk;
        kk = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                information);


        myListView.setAdapter(kk);
    }

    public void nextInformation(View view) {
        index = index + 1;
        setPreviousNextButtons(index);
        showInformation(listOfLists.get(index));

    }

    public void previousInformation(View view) {
        index = index - 1;
        setPreviousNextButtons(index);
        showInformation(listOfLists.get(index));

    }

    private void setPreviousNextButtons(int idx) {
        Button previousButton = findViewById(R.id.button2);
        Button nextButton = findViewById(R.id.button3);

        if (idx <= 0) {
            index = 0;
            previousButton.setEnabled(false);
            nextButton.setEnabled(true);
        } else {
            previousButton.setEnabled(true);
        }
        if (index >= listOfLists.size()) {
            index = listOfLists.size() - 1;
            nextButton.setEnabled(false);

        } else {
            if (index == listOfLists.size() - 1) {
                nextButton.setEnabled(false);
            } else {
                nextButton.setEnabled(true);
            }
        }

    }

}
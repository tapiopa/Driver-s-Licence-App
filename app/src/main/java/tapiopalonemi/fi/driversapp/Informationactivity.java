package tapiopalonemi.fi.driversapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ViewFlipper;
import java.util.ArrayList;

//import android.widget.myListView;

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
                "Park with precision", "Be careful with signs on roads as well as traffic lights", "Always follow the speed limits",
                "Give pedestrians the right-of-way in crosswalks", "Always check your seat belt"

        };

        ListView myListView =  findViewById(R.id.information_list_view);

        final String[] INFORMATIONS2 = new String[]{
                "Adjust your mirrors correctly", "If the car don't start turn high beams on", "Use hand brake regularly",
                "Don’t turn the wheels beforehand when making a left turn", "Maintain required distance always from other vehicles",
                "Park with precision", "Be careful with signs on roads as well as traffic lights", "Always follow the speed limits",
                "Give pedestrians the right-of-way in crosswalks", "Always check your seat belt"

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

    private void nextInformation(View view) {
        index = index + 1;
        if (index > listOfLists.size()) {
            index = listOfLists.size() - 1;
        }
        Log.i("INFORMATIONS", "index: " + index);

        showInformation(listOfLists.get(index));

    }

    private void previousInformation(View view) {
        index = index - 1;
        if (index < 0) {
            index = 0;
        }
        Log.i("INFORMATIONS2", "index: " + index);
        showInformation(listOfLists.get(index));

    }

}


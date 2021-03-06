package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Locale;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Informationactivity extends AppCompatActivity {



    private MyDBHandler db;
    private int index = -1;
    private int minTipPage = -1;
    private int maxTipPage = -1;
    ArrayList<DrivingInfo> tips;



    @Override
//
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
            Toolbar myToolbar = findViewById(R.id.toolbar_Information);
            setSupportActionBar(myToolbar);

            // Get a support ActionBar corresponding to this toolbar

        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
            String abTitle = "Driver's App";
            ab.setTitle(abTitle);
            ab.setSubtitle("Driving Tips");

        }


        db = new MyDBHandler(this);

        tips = db.loadTips();
        minTipPage = db.minTipPage();
        maxTipPage = db.maxTipPage();
        index = minTipPage - 1;

//        final String[] INFORMATIONS = new String[]{
//                "Adjust your mirrors correctly", "If the car don't start turn high beams on", "Use hand brake regularly",
//                "Don’t turn the wheels beforehand when making a left turn", "Maintain required distance always from other vehicles",
//                "Park with precision", "follow the vehicle of neighbouring lane"
//        };
//
//        ListView myListView= findViewById(R.id.information_list_view);
//
//        final String[] INFORMATIONS2 = new String[]{
//                "Be careful with signs on roads as well as traffic lights", "Always follow the speed limits",
//                "Give pedestrians the right-of-way in crosswalks", "Always check your seat belt","Park with precision",
//                "Keep winter Survival kit in winter time","plan route in advanced for long trips"
//
//        };
//
//
//        final String[] INFORMATIONS3 = new String[]{
//                "Don't take alcohol and drugs while drive", "Don't assume that other cars know what you are doing",
//                "Don't play loud music in your vehicle","Don't Talk on your cellphone and drive",
//                "Don't treat your vehicle like a toy","Don't leave your valueables in your car",
//                "Give priority to emergency vehicles"
//
//        };
//
//        final String[] INFORMATIONS4 = new String[]{
//                "If you face an accident make sure nobody in car is injured","Check the conditions of other side vehicles",
//                "Call Police immediately","Stay at the scene","Stay Calm and contact your insurance provider",
//                "Give your liscence and other documents to Insurance and Police","Don't be Abusive"
//
//        };

//        listOfLists = new ArrayList<>();
//        listOfLists.add(INFORMATIONS);
//        listOfLists.add(INFORMATIONS2);
//        listOfLists.add(INFORMATIONS3);
//        listOfLists.add(INFORMATIONS4);
        nextInformation(null);
        previousInformation(null);

    }

    private void showInformation(ArrayList<String> information) {

        ListView myListView = findViewById(R.id.information_list_view);
        final ArrayAdapter<String> kk;
        kk = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                information);

        myListView.setAdapter(kk);
    }

    public void nextInformation(View view) {
        index = index + 1;
        setPreviousNextButtons(index);
        ArrayList<String> page = new ArrayList<>();
        for (DrivingInfo drivingInfo : tips) {
            if (drivingInfo.getTipPage() == index) {
                page.add(drivingInfo.getTipString());
            }
        }
        showInformation(page);

    }

    public void previousInformation(View view) {
        index = index - 1;
        setPreviousNextButtons(index);
        ArrayList<String> page = new ArrayList<>();
        for (DrivingInfo drivingInfo : tips) {
            if (drivingInfo.getTipPage() == index) {
                page.add(drivingInfo.getTipString());
            }
        }
        showInformation(page);
    }

    private void setPreviousNextButtons(int idx) {
        Button previousButton = findViewById(R.id.button2);
        Button nextButton = findViewById(R.id.button3);

        if (idx <= minTipPage) {
            index = minTipPage;
            previousButton.setEnabled(false);
            nextButton.setEnabled(true);
        } else {
            previousButton.setEnabled(true);
        }
        if (index >= maxTipPage) {
            index = maxTipPage;
            nextButton.setEnabled(false);
        } else {
            if (index == maxTipPage) {
                nextButton.setEnabled(false);
            } else {
                nextButton.setEnabled(true);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.language, menu);
        return true;
    }


    //Handle selecting a language in language menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.english:
                Log.i("LANGUAGE SELECTION", "english");
                setLocale("en");
                return true;
            case R.id.finnish:
                Log.i("LANGUAGE SELECTION", "finnish");
                setLocale("fi");
                return true;
            case R.id.swedish:
                Log.i("LANGUAGE SELECTION", "swedish");
                setLocale("sv");
                return true;
            case R.id.hungarian:
                Log.i("LANGUAGE SELECTION", "hungarian");
                setLocale("hu");
                return true;
            case R.id.nepali:
                Log.i("LANGUAGE SELECTION", "nepali");
                setLocale("ne");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Set locale according to language selection
    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Informationactivity.class);
        startActivity(refresh);
        finish();
    }
}

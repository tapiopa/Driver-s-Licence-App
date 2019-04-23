package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public class DrivingSymbols extends AppCompatActivity{




    // Array of strings for ListView Title
    String[] listviewTitle = new String[]{
            "Dangerous Curve", "Dangerous Curve Bend", "Direction Left or right", "Pass Either Side",
            "Passing place on Narrow Road", "Pedestrian Crossing", "Round about", "Track for cyclists and pedestrians",
            "Tunnel","Advanced Direction","Advisory Sign" ,"Bus and taxi lane","End Of Bus And Taxi Lane",
            "Direction Straight Or Right","Don't turn Left","Residental Area","Priority Symbol"
    };


    int[] listviewImage = new int[]{
            R.drawable.dangerouscurve, R.drawable.dangerous2, R.drawable.directionleftright, R.drawable.passeitherside,
            R.drawable.passingplaceonnarrowroad, R.drawable.pedestriancrossing, R.drawable.roundabout,
            R.drawable.trackforcyclistsandpedestrians, R.drawable.tunnel,R.drawable.advanceddirection,R.drawable.advisosrysign,
            R.drawable.bustaxilane,R.drawable.endofbusandtaxi,R.drawable.direction,R.drawable.noleft,
            R.drawable.residental,R.drawable.priority
    };

    String[] listviewShortDescription = new String[]{
            "It means Dangerous Curve and you should turn left similar Symbols to right means same as left", "It is also dangerous Curve and it Symbolizes it is a dangerous Curve and first we have to bend right",
            "It is mandatory sign and it Shows Direction to be followed Turn left or right ", "This is mandatory Sign and it tells us to pass in either side left or right",
            "It means passing place on narrow roads", "This Symbol comes when there is place where Pedestrian is crossing the road",
            "This symbol denotes for compulsory roundabout", "It shows the track for cyclists and pedestrian both",
            "This shows Tunnel which has length of 2 Km","It Shows direction as well as distance to different places from the junction",
            "it is advisory sign and it is with distance also to some places","This shows lane for bus and taxi",
            "This shows end of Bus and Taxi Lane","It shows mandatory sign either straight or towards right ","This symbol means turning to left is prohibited",
            "This symbol shows residental Area","This shows give priority to passing Vehicles"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbols);
        Toolbar myToolbar = findViewById(R.id.toolbar_DrivingSymbols);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 17; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_description", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_description"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
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
        Intent refresh = new Intent(this, DrivingSymbols.class);
        startActivity(refresh);
        finish();
    }
}


package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    MyDBHandler db;

    //Go to Finnish exam page
    public void toExamFi(View view) {
        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", true);
        db.setIsFinnishQuestions(true);
        startActivity(intent);
    }

    //Go to Swedish exam page
    public void toExamSe(View view) {
        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("finnish", false);
        db.setIsFinnishQuestions(false);
        startActivity(intent);
    }

    //Go to results page
    public void toResults(View view) {
        Log.i("BOTTOM NAVIGATION", "to results navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        startActivity(intent);
    }

    //Go to Finnish exam page (button)
    public void buttonToExam(View view) {
        Log.i("BOTTOM NAVIGATION", "to exam button clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }

    //Go to Swedish exam page (button)
    public void buttonToResults(View view) {
        Log.i("BOTTOM NAVIGATION", "to results button clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }
    public void buttonToInformations(View view) {
        Log.i("BOTTOM NAVIGATION", "to DRIVING TIPS button clicked");
        Intent intent = new Intent(getApplicationContext(), Informationactivity.class);
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }
    public void buttonToDrivingSymbols(View view) {
        Log.i("BOTTOM NAVIGATION", "to DRIVING SYMBOLS button clicked");
        Intent intent = new Intent(getApplicationContext(), DrivingSymbols.class);
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDBHandler(this);
        //Do some housekeeping if first time run of this app
        if (db.isNotDataLoaded()) {
            Log.d("HOME", "data is not loaded");
            db.firstRun(this);
        }

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.app_name);

        Fresco.initialize(this);
    }

    //Create options menu for language selection
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
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}

package tapiopalonemi.fi.driversapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
//import android.view.Menu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
//                    navigation.setSelectedItemId(R.id.navigation_home);
                    Log.i("HOME BOTTOM NAVIGATION", "HOME CLICKED");
                    return true;
                case R.id.navigation_exam:
                    mTextMessage.setText(R.string.title_exam);
                    Log.i("HOME BOTTOM NAVIGATION", "EXAM CLICKED");
                    return true;
                case R.id.navigation_results:
                    mTextMessage.setText(R.string.title_results);
                    Log.i("HOME BOTTOM NAVIGATION", "RESULTS CLICKED");
                    return true;
            }
            return false;
        }
    };

    public void toHome(MenuItem menuItem) {
        Log.i("BOTTOM NAVIGATION", "to home navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void toExam(MenuItem menuItem) {
        Log.i("BOTTOM NAVIGATION", "to exam navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
        intent.putExtra("extra", "extra");
        startActivity(intent);
    }

    public void toResults(MenuItem menuItem) {
        Log.i("BOTTOM NAVIGATION", "to results navigation item clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
        startActivity(intent);
    }

    public void buttonToExam(View view) {
        Log.i("BOTTOM NAVIGATION", "to exam button clicked");
        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }

    public void buttonToResults(View view) {
        Log.i("BOTTOM NAVIGATION", "to results button clicked");
        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
//        intent.putExtra("extra", "extra");
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }
}
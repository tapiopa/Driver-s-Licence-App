package tapiopalonemi.fi.driversapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.myListView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
public class Informationactivity extends AppCompatActivity{





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] INFORMATIONS = new String[] {

        };
        ListView myListView =  findViewById(R.id.information_list_view);
        final ArrayAdapter<String> kk;
        kk = new ArrayAdapter<String>(this ,android.R.layout.simple_list_item_1,
                INFORMATIONS);


        myListView.setAdapter(kk);
    }
}

package tapiopalonemi.fi.driversapp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import java.util.List;
public class DrivingschoolActivity extends AppCompatActivity {

    // Array of strings for ListView Title
    String[] listviewName = new String[]{
            "ListView Title 1"
    };
    String[] listviewMap = new String[]{
            "Map"

    };

    String[] listvieweditText = new String[]{
            "editText"
    };
    String[] listviewcall = new String[]{
            "call"
    };
    String[] listviewwebsite = new String[]{
            "go"
    };
    int[] listviewImage = new int[]{
            R.drawable.drive
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivingschool);
//        Button listview_item_call = (Button) findViewById(R.id.call);
//        Button listview_item_go = (Button) findViewById(R.id.go);
//        Button listview_item_map = (Button) findViewById(R.id.map);
//        EditText editText = (EditText) findViewById(R.id.editText);
//
//        listview_item_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("OPEN MAP", "onClick: OPEN MAP");
//                // Map point based on address
//                Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
//                // Or map point based on latitude/longitude
//                // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
//                startActivity(mapIntent);
//            }
//        });
//        listview_item_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("CALL", "onClick: MAKE A CALL");
//                Uri number = Uri.parse("tel:4567123");
//                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
//                startActivity(callIntent);
//            }
//        });
//        listview_item_go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String text = editText.getText().toString();
//                Log.d("text", "onClick: Go " + text);
//                Uri webpage = Uri.parse(text);
//                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
//                startActivity(webIntent);
//            }
//        });


//        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
//
//        for (int i = 0; i < 1; i++) {
//            HashMap<String, String> hm = new HashMap<String, String>();
//            hm.put("listview_name", listviewName[i]);
//            hm.put("listview_editText", listvieweditText[i]);
//            hm.put("listview_call", listviewcall[i]);
//            hm.put("listview_website", listviewwebsite[i]);
//            hm.put("listview_map", listviewMap[i]);
//            hm.put("listview_image", listviewImage[i]);
//
//
//            aList.add(hm);
//        }

//        String[] from = {"listview_image", "listview_name", "listview_phone","listview_Map","listview_website","listview_editText"};
//        int[] to = {R.id.listview_image, R.id.listview_name, R.id.listview_call, R.id.listview_go,R.id.listview_map,
//        R.id.listview_editText};

        AdItem adItem = new AdItem("ListView Title 1", "map", "editText", "call", R.drawable.drive);

        ArrayList<AdItem> adItems = new ArrayList<>();
        adItems.add(adItem);
//        adItems.add(new AdItem());

        ArrayAdapter<AdItem> adapter = new AdAdapter(this, adItems);

//        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.activity_drivingschools, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(adapter);
    }
}

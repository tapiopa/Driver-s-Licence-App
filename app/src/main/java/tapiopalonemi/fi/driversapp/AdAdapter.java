package tapiopalonemi.fi.driversapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
//import android.util.Log;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class AdAdapter extends ArrayAdapter<AdItem> {

    public AdAdapter(Context context, ArrayList<AdItem> ads) {
        super(context, 0, ads);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull final ViewGroup parent) {
        final AdItem ad = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_drivingschools, parent, false);
        }
        ImageView image = convertView.findViewById(R.id.image);

        TextView name = convertView.findViewById(R.id.name);
        final EditText editText = convertView.findViewById(R.id.editText);

        Button map = convertView.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OPEN MAP", "MAP address: " + ad.getMap() +  ", onClick: OPEN MAP");
                // Map point based on address
                Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
                // Or map point based on latitude/longitude
                // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                parent.getContext().startActivity(mapIntent);
            }
        });

        Button call = convertView.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CALL", "onClick: MAKE A CALL, phone: " + ad.getCall());
                Uri number = Uri.parse("tel:4567123");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                parent.getContext().startActivity(callIntent);
            }
        });

        Button go = convertView.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                Log.d("text", "GO, web page address: " + ad.getWebPage() + ", onClick: Go " + text);
                Uri webpage = Uri.parse(text);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                parent.getContext().startActivity(webIntent);
            }
        });

        return convertView;
    }


}



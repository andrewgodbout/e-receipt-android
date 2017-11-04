package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by David on 10/20/2017.
 */

public class ReceiptActivity extends AppCompatActivity {

    private static final String ITEM = "items";
    private static final String DATE = "date";
    private static final String STORE = "store";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        Intent intent = getIntent();

        String storeName = intent.getStringExtra(STORE);
        String date = intent.getStringExtra(DATE);

        populateTextViews(storeName,date);

    }

    public static Intent dataInstance(Context context, Receipt receipt){

        Intent intent = new Intent(context, ReceiptListActivity.class);
        String store = receipt.getStore();
        String date = receipt.getDate();

        intent.putExtra(STORE, store);
        intent.putExtra(DATE, date);
        //didn't add the items yet

        return intent;
    }

    public void populateTextViews(String name, String date){//, ArrayList<String> details) {

        TextView textViewStoreName = (TextView) findViewById(R.id.textview_store_name);
        TextView textViewDate = (TextView) findViewById(R.id.textview_date);
        //TextView textViewDetails = (TextView) findViewById (R.id.textview_details);

        textViewStoreName.setText(name);
        textViewDate.setText(date);

        //use an adapter to populate single textviews as needed
        /*ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.directions_item,details);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);*/
    }





}
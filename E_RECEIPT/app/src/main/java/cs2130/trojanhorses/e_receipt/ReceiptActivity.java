package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by David on 10/20/2017.
 */

public class ReceiptActivity extends AppCompatActivity {
/**
 private static final String EXTRA_RECEIPT_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID receiptId) {
        Intent intent = new Intent(packageContext, ReceiptActivity.class);
        intent.putExtra(EXTRA_RECEIPT_ID, receiptId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_RECEIPT_ID);
        return ReceiptFragment.newInstance(crimeId);
    }
} **/

   private static final String ITEM = "items";
    private static final String DATE = "date";
    private static final String STORE = "store";
    private static final String RECEIPT_ID = "receipt_id";
    private static Receipt mReceipt;
    private static final String ARG_RECEIPT_ID = "receipt_id";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        Intent intent = getIntent();

        String storeName = intent.getStringExtra(STORE);
        String date = intent.getStringExtra(DATE);

        ArrayList<Item> item = intent.getParcelableArrayListExtra(ITEM);

        Item[] items = new Item[item.size()];
        for ( int i = 0; i<item.size(); i++) {
            items[i] = item.get(i);
        }

        UUID receiptID = (UUID) intent.getSerializableExtra(RECEIPT_ID);


        mReceipt = ReceiptLab.get(ReceiptActivity.this, null, false).getReceipt(receiptID);
        populateTextViews(storeName,date,items);

    }

    public static Intent dataInstance(Context context, Receipt receipt){

        Intent intent = new Intent(context, ReceiptActivity.class);

        String store = receipt.getStore();
        String date = receipt.getDate();
        UUID id = receipt.getId();

        intent.putExtra(STORE, store);
        intent.putExtra(DATE, date);
        intent.putExtra(RECEIPT_ID, id);
        intent.putExtra(ITEM, new ArrayList<Item>(receipt.convertToArrayList()));

        return intent;
    }

    public void populateTextViews(String name, String date, Item [] data) {

        TextView textViewStoreName = (TextView) findViewById(R.id.textview_store_name);
        TextView textViewDate = (TextView) findViewById(R.id.textview_date);
        //TextView textViewDetails = (TextView) findViewById (R.id.textview_details);

        textViewStoreName.setText(name);
        textViewDate.setText(date);
        //textViewDetails.setText(convertArray(data));
    }

    public String convertArray (Item [] data){

        StringBuilder sb = new StringBuilder();

        String details = sb.toString();

        return details;
    }

}




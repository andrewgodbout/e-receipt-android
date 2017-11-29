package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by David on 10/20/2017.
 */

public class ReceiptActivity extends AppCompatActivity {

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

        /**This is not needed, I dont know why though*/
        //UUID receiptID = (UUID) intent.getSerializableExtra(RECEIPT_ID);
        //mReceipt = ReceiptLab.get(ReceiptActivity.this, null, false).getReceipt(receiptID);

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
        TextView textViewDetails = (TextView) findViewById (R.id.items_details);
        TextView textViewPrice = (TextView) findViewById(R.id.items_price);
        TextView textViewTotalPrice = (TextView) findViewById (R.id.receipt_price);

        textViewStoreName.setText(name);
        textViewDate.setText(date);
        textViewDetails.setText(convertArrayDetails(data));
        textViewPrice.setText(convertArrayPrice(data));
        textViewTotalPrice.setText(getTotal(data));
    }

    public String convertArrayDetails (Item [] data){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        for (int i=0 ; i<data.length; i++) {
            sb.append((i+1)+" "+data[i].getItemName().substring(0,1).toUpperCase()+ // 1st letter of ItemName to UpperCase
                    data[i].getItemName().trim().substring(1)+"\n");// Rest of ItemName
            //tabPrice(data[i].getItemName().trim().length(), String.valueOf(data[i].getItemPrice()))+ // Tab prices
            //data[i].getItemPrice()+"$\n"); // Print Price
        }
        String details = sb.toString();

        return details;
    }

    public String convertArrayPrice (Item [] data){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        for (int i=0 ; i<data.length; i++) {
            sb.append(data[i].getItemPrice()+"$\n"); // Print Price
        }
        String details = sb.toString();

        return details;
    }

    public String getTotal(Item[] data) {
        DecimalFormat df = new DecimalFormat("#.00");
        double total=0;
        for (Item i: data) {
            total += i.getItemPrice();
        }
        return df.format(total)+"$";
    }

}




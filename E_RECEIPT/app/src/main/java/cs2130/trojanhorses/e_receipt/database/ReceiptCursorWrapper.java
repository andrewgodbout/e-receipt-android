package cs2130.trojanhorses.e_receipt.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cs2130.trojanhorses.e_receipt.Item;
import cs2130.trojanhorses.e_receipt.Receipt;

import static java.lang.Double.parseDouble;

/**
 * Created by davidadams on 2017-11-12.
 */

public class ReceiptCursorWrapper extends CursorWrapper {

    public ReceiptCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Receipt getReceipt()  {
        String uuidString = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.UUID));
        String store = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.STORE));
        String date = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.DATE));
        String json = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.ITEMS));


        Receipt receipt = new Receipt(UUID.fromString(uuidString));
        receipt.setStore(store);
        receipt.setDate(date);
        try {
            receipt.setItems(parseItems(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return receipt;
    }

    public Item[] parseItems(String json) throws JSONException {

        JSONArray items_list = new JSONArray(json);


        String newJson = items_list.getString(0);

        //String[] item= new String[items_list.length()];
        //String[] price= new String[items_list.length()];




        Item[] items = new Item[items_list.length()];

        for (int i = 0; i < items_list.length(); i++) {
            String[] item = items_list.get(i).toString().split(":");
            //Log.d("DEBUG", item[1].substring(2));
            items[i] = new Item(item[0],
                    parseDouble(item[1].substring(2)));
        }
        //Log.d("TAG", items.toString());
        return items;
    }


}

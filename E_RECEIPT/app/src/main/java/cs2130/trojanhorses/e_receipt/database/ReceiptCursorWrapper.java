package cs2130.trojanhorses.e_receipt.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cs2130.trojanhorses.e_receipt.Item;
import cs2130.trojanhorses.e_receipt.Receipt;

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

        JSONObject jsonObject = new JSONObject(json);
        JSONArray items_list = jsonObject.getJSONArray("items");

        Item[] items = new Item[items_list.length()];

        for (int i = 0; i < items_list.length(); i++) {
            JSONArray item = items_list.getJSONArray(i);
            items[i] = new Item((String) item.get(0), (double) item.get(1));
        }

        return items;
    }
}

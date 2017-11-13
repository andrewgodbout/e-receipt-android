package cs2130.trojanhorses.e_receipt.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

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
        //String items = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.ITEMS));
        String json = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.ITEMS));

        try {
            JSONObject jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Receipt receipt = new Receipt(UUID.fromString(uuidString));
        receipt.setStore(store);
        receipt.setDate(date);
        receipt.setItems(json);

        return receipt;
    }
}

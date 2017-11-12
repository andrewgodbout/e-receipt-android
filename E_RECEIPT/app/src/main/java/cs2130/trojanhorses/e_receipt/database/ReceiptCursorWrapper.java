package cs2130.trojanhorses.e_receipt.database;

import android.database.Cursor;
import android.database.CursorWindow;
import android.database.CursorWrapper;

import java.lang.reflect.Array;

import cs2130.trojanhorses.e_receipt.Receipt;

/**
 * Created by davidadams on 2017-11-12.
 */

public class ReceiptCursorWrapper extends CursorWrapper {
    public ReceiptCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public Receipt getReceipt(){
        String uuidString = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.UUID));
        String store = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.STORE));
        String date = getString(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.DATE));
        //Array[] item = getA(getColumnIndex(ReceiptDbSchema.ReceiptTable.Cols.ITEM));

        return null;
    }
}

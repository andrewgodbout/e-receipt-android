package cs2130.trojanhorses.e_receipt.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by davidadams on 2017-11-12.
 */

public class ReceiptHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "receiptList.db";

    public ReceiptHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + ReceiptDbSchema.ReceiptTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
            ReceiptDbSchema.ReceiptTable.Cols.UUID + ", " +
            ReceiptDbSchema.ReceiptTable.Cols.STORE + ", " +
            ReceiptDbSchema.ReceiptTable.Cols.DATE + ", " +
            ReceiptDbSchema.ReceiptTable.Cols.ITEMS +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

package cs2130.trojanhorses.e_receipt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import cs2130.trojanhorses.e_receipt.database.ReceiptCursorWrapper;
import cs2130.trojanhorses.e_receipt.database.ReceiptDbSchema;
import cs2130.trojanhorses.e_receipt.database.ReceiptHelper;

/**Parses and loads the JSONArray into receipt objects and stores them into the database*/

public class ReceiptLab {

    private static ReceiptLab sReceiptLab;
    private List<Receipt> mReceipts;
    private static Callbackable mCb;
    private Receipt mReceipt;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private final String AUTHORITY = "137.149.157.18";
    private final String PATH = "CS2130/e-receipt/";
    private final String SCHEME = "http";
    private final String QUERY = "date";
    private String mQueryParam = "201710";
    private String mQueryYear = "2017";
    private int mItems;

    private ReceiptLab(Context context, boolean load){
        mContext = context;
        mDatabase = new ReceiptHelper(mContext)
                .getWritableDatabase();
        /**if load = true, load data from server */
        if (load) {
            mReceipts = new ArrayList<>();
            runMultiple();
        }
    }

    public static ReceiptLab get(Context context, Callbackable cb, boolean load) {
        if (cb != null){
            mCb = cb;
            sReceiptLab = null;
        }
        if (sReceiptLab == null) {
            sReceiptLab = new ReceiptLab(context, load);
        }
        return sReceiptLab;
    }

    public List<Receipt> getReceipts(){
        List<Receipt> receipts = new ArrayList<>();
        ReceiptCursorWrapper cursor = (ReceiptCursorWrapper) queryReceipts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                receipts.add(cursor.getReceipt());
                cursor.moveToNext();
            }
        } finally {
                cursor.close();
            }
        return receipts;
    }

    @NonNull
    private ReceiptCursorWrapper queryReceipts(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ReceiptDbSchema.ReceiptTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ReceiptCursorWrapper(cursor);
    }

    /**
     * Add a receipt into the database
     * */
    public void addReceipt(Receipt receipt){
        ContentValues values = getContentValues(receipt);
        mDatabase.insert(ReceiptDbSchema.ReceiptTable.NAME, null, values);
    }

    public void add(Receipt receipt) {
        mReceipts.add(receipt);
    }

    private URL buildURL( ) {
        URL url = null;
        Uri.Builder uri = new Uri.Builder();
        try {
            url = new URL (uri.scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendEncodedPath(PATH)
                    .appendQueryParameter(QUERY, mQueryParam)
                    .build().toString());
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Run multiple calls to load the database with the last 5 months of random receipts
     * */
    private void runMultiple() {
        Calendar c = Calendar.getInstance();

        for (int month = 0; month < 5; month++) {
            for (int day = 30; day > 0; day--) {
                if (((c.get(Calendar.MONTH)-month)+1)%12 < 10 && ((c.get(Calendar.MONTH)-month)+1)%12 > 0) { // From January to September
                    if (day % 5 == 0) {
                        if (day < 10) {
                            mQueryParam = mQueryYear + "0" + (((c.get(Calendar.MONTH) - month)+1)%12) + "0" + Integer.toString(day);
                            new eReceiptQuery(mCb).execute(buildURL());
                        } else {
                            mQueryParam = mQueryYear + "0" + (((c.get(Calendar.MONTH) - month)+1)%12) + Integer.toString(day);
                            new eReceiptQuery(mCb).execute(buildURL());
                        }
                    }
                }else if (((c.get(Calendar.MONTH)-month)+1)%12 == 0) {                              // Special case for December
                    if (day % 5 == 0) {
                        if (day<10) {
                            mQueryParam = mQueryYear + "120" + Integer.toString(day);
                            new eReceiptQuery(mCb).execute(buildURL());
                        } else {
                            mQueryParam = mQueryYear + "12" + Integer.toString(day);
                            new eReceiptQuery(mCb).execute(buildURL());
                        }
                    }
                }else {                                                                             // October and November
                    if (day % 5 == 0) {
                        if (day<10) {
                            mQueryParam = mQueryYear + (((c.get(Calendar.MONTH) - month) + 1)%12) + "0" + Integer.toString(day);
                            new eReceiptQuery(mCb).execute(buildURL());
                        } else {
                            mQueryParam = mQueryYear + (((c.get(Calendar.MONTH) - month) + 1)%12) + Integer.toString(day);
                            new eReceiptQuery(mCb).execute(buildURL());
                        }
                    }
                }
                mItems++;
            }
        }
    }

    public int size(){
        return mItems;
    }

    private static ContentValues getContentValues(Receipt receipt) {
        ContentValues values = new ContentValues();
        values.put(ReceiptDbSchema.ReceiptTable.Cols.UUID, receipt.getId().toString());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.DATE, receipt.getDate());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.STORE, receipt.getStore());

        JSONArray jsonArray = new JSONArray();
        Item [] items = receipt.getItems();

        for(int i = 0; i < items.length; i++)
            jsonArray.put(items[i]);

        values.put(ReceiptDbSchema.ReceiptTable.Cols.ITEMS, jsonArray.toString());

        return values;
    }

    /**
     * Parse to Receipts from the Database JSONObject
     * */
    private Receipt parseReceipt(JSONObject jsonObject) throws JSONException {
        String date_purchased = jsonObject.getString("date");

        String date = date_purchased.substring(0,4) + "." + date_purchased.substring(4,6)+ "."
                + date_purchased.substring(6);

        JSONArray items_list = jsonObject.getJSONArray("items");
        String store_name = jsonObject.getString("store");

        Item[] items = new Item[items_list.length()];

        for (int i = 0; i < items_list.length(); i++) {
            JSONArray item = items_list.getJSONArray(i);
            items[i] = new Item (item.getString(0), item.getDouble(1));
        }

        Receipt receipt = new Receipt(date, store_name, items);
        return receipt;
    }

    /**
     * AsyncTask to call the URL server to receive a JSONObject
     * */
    private class eReceiptQuery extends AsyncTask<URL, Void, String> {

        private Callbackable listener;

        private eReceiptQuery(Callbackable listener){this.listener = listener;}

        @Override
        protected String doInBackground(URL... params) {

            String resultString = "";

            HttpURLConnection httpURLConnection = null;

            try {
                httpURLConnection = (HttpURLConnection) params[0].openConnection();

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                Scanner scan = new Scanner(in);
                StringBuilder sb = new StringBuilder();

                while(scan.hasNext()) {
                    sb.append(scan.nextLine());
                }
                resultString = sb.toString();
            }catch(IOException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
            }
            return resultString;
        }

        /**Adds the receipt to the database*/
        @Override
        protected void onPostExecute(String resultString) {
            JSONObject jsonObject;

            try {
                JSONArray jsonArray = new JSONArray(resultString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    mReceipt = parseReceipt(jsonObject);

                    addReceipt(mReceipt);
                }
                listener.update();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

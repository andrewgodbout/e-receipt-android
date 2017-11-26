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
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import cs2130.trojanhorses.e_receipt.database.ReceiptCursorWrapper;
import cs2130.trojanhorses.e_receipt.database.ReceiptDbSchema;
import cs2130.trojanhorses.e_receipt.database.ReceiptHelper;

/**
 * Created by Aleix on 10/21/2017.
 */

public class ReceiptLab {

    private static ReceiptLab sReceiptLab;
    private List<Receipt> mReceipts;
    private static Callbackable mCb;
    private Receipt mReceipt;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private final String AUTHORITY = "137.149.157.18";
    private final String PATH = "CS2130/e-receipt/";//?date=20171004";
    private final String SCHEME = "http";
    private final String QUERY = "date";
    //private final String QUERY_PARAM = "20171021";
    private String queryParam = "201710";

    private ReceiptLab(Context context, boolean load){
        mContext = context;
        mDatabase = new ReceiptHelper(mContext)
                .getWritableDatabase();

        if (load) {
            mReceipts = new ArrayList<>();
            run();
        }
    }


    /**Commented out in case database fails */
    /*private ReceiptLab(Context context) {
        //Log.d("TAG", "receipt lab executed");
        mReceipts = new ArrayList<>();
        run();
    }*/

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

    /**Commented out in case database fails */
    /*public List<Receipt> getReceipts() {
        return mReceipts;
    } //this should be highlighted out once database works*/

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

    public Receipt getReceipt(UUID id){
        ReceiptCursorWrapper cursor = (ReceiptCursorWrapper) queryReceipts(
                ReceiptDbSchema.ReceiptTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getReceipt();
        } finally{
            cursor.close();
        }
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

        //return cursor;
        return new ReceiptCursorWrapper(cursor);
    }

    public void addReceipt(Receipt receipt){
        ContentValues values = getContentValues(receipt);
        //Log.d("DEBUG", receipt.getStore());
        mDatabase.insert(ReceiptDbSchema.ReceiptTable.NAME, null, values);
    }

    public void updateReceipt(Receipt receipt){

        ContentValues values = getContentValues(receipt);

        String uuidString = receipt.getId().toString();
        mDatabase.update(ReceiptDbSchema.ReceiptTable.NAME, values,
                ReceiptDbSchema.ReceiptTable.Cols.UUID + " = ?",
                new String [] { receipt.getId().toString()});
    }

    /**Commented out in case database fails  */
    /*public Receipt getReceipt(UUID id) {
        for (Receipt receipt: mReceipts) {
            if (receipt.getId().equals(id)) {
                return receipt;
            }
        }
        return null;
    }*/

    /**Commented out in case database fails  */
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
                    .appendQueryParameter(QUERY, queryParam)
                    .build().toString());
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /*private void run() {
        for ( int i = 30; i>1; i--) {
            queryParam = "201710";
            if (i < 10) {
                queryParam = "2017100";
                queryParam += i;
            } else {
                queryParam += i;
            }
            new eReceiptQuery(mCb).execute(buildURL());
        }
    }*/

    private void run() {
        for (int month = 5; month > 0; month--) {
            for (int day = 30; day > 0; day--) {
                if (month < 10) {
                    queryParam = "20170"+ Integer.toString(month) + Integer.toString(day);
                } else {
                    queryParam = "2017" + Integer.toString(month) + Integer.toString(day);
                }
                new eReceiptQuery(mCb).execute(buildURL());
            }
        }
    }

    private static ContentValues getContentValues(Receipt receipt) {
        ContentValues values = new ContentValues();
        values.put(ReceiptDbSchema.ReceiptTable.Cols.UUID, receipt.getId().toString());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.DATE, receipt.getDate().toString());
        values.put(ReceiptDbSchema.ReceiptTable.Cols.STORE, receipt.getStore());

        JSONArray jsonArray = new JSONArray();
        Item [] items = receipt.getItems();

        for(int i = 0; i < items.length; i++)
            jsonArray.put(items[i]);

        //String myString = "{\"items\":[\"Keyboard, 0.99\"]}";
        //Log.d("DEBUG", myString);
        values.put(ReceiptDbSchema.ReceiptTable.Cols.ITEMS, jsonArray.toString());

        return values;
    }

    private Receipt parseReceipt(JSONObject jsonObject) throws JSONException {
        String date_purchased = jsonObject.getString("date");
        /**Call a parse date method over here*/
        JSONArray items_list = jsonObject.getJSONArray("items");
        String store_name = jsonObject.getString("store");

        Item[] items = new Item[items_list.length()];

        for (int i = 0; i < items_list.length(); i++) {
            JSONArray item = items_list.getJSONArray(i);
            items[i] = new Item (item.getString(0), item.getDouble(1));
        }
        //Log.d("TAG", items[0].getItemName());
        Receipt receipt = new Receipt(date_purchased, store_name, items);

        /*JSONArray jsArr = jsonObject.getJSONArray("details");
        for (int i = 0; i < jsArr.length(); i++) {
            receipt.addTurn(jsArr.getString(i));
        }*/

        return receipt;
    }

    private class eReceiptQuery extends AsyncTask<URL, Void, String> {

        private Callbackable listener;

        private eReceiptQuery(Callbackable listener){this.listener = listener;}

       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mLoading = true;
            //mReceipts.clear();
        }*/

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
                /*Toast.makeText(this, "Error connecting to the Website ",
                        Toast.LENGTH_SHORT).show();*/
            }finally {
                httpURLConnection.disconnect();
            }
            //Log.d("DEBUG", resultString);
            return resultString;
        }

        /**Code will not work for now because .add doesn't work */
        @Override
        protected void onPostExecute(String resultString) {
            //Log.d("DEBUG", resultString);
            JSONObject jsonObject;

            try {
                JSONArray jsonArray = new JSONArray(resultString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    //Log.d("TAG", "Before");
                    mReceipt = parseReceipt(jsonObject);
                    //Log.d("TAG", "After");

                    addReceipt(mReceipt);
                }
                listener.update();

            } catch (JSONException e) {
                e.printStackTrace();
                //mReceipts.clear();
                //Toast.makeText(ReceiptListActivity.this, "Error loading receipts", Toast.LENGTH_SHORT).show();
            } //finally {
                //mLoading = false;
            //}
            //mReceiptAdapter.notifyDataSetChanged();
            //mDataLoaded = true;
        }
    }

}

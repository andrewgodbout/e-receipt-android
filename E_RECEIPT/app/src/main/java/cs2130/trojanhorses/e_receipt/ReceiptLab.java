package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Aleix on 10/21/2017.
 */

public class ReceiptLab {

    private static ReceiptLab sReceiptLab;
    private List<Receipt> mReceipts;
    private static Callbackable mCb;
    private Receipt mReceipt;

    private final String AUTHORITY = "137.149.157.18";
    private final String PATH = "CS2130/e-receipt/";//?date=20171004";
    private final String SCHEME = "http";
    private final String QUERY = "date";
    //private final String QUERY_PARAM = "20171021";
    private String queryParam = "201710";

    private ReceiptLab(Context context) {
        Log.d("TAG", "receipt lab executed");
        mReceipts = new ArrayList<>();
        run();
    }

    public static ReceiptLab get(Context context, Callbackable cb) {
        if (cb != null){
            mCb = cb;
            sReceiptLab = null;
        }
        if (sReceiptLab == null) {
            sReceiptLab = new ReceiptLab(context);
        }
        return sReceiptLab;
    }

    public List<Receipt> getReceipts() {
        return mReceipts;
    }

    public Receipt getReceipt(UUID id) {
        for (Receipt receipt: mReceipts) {
            if (receipt.getId().equals(id)) {
                return receipt;
            }
        }
        return null;
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
                    .appendQueryParameter(QUERY, queryParam)
                    .build().toString());
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private void run() {
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
    }

    private Receipt parseReceipt(JSONObject jsonObject) throws JSONException {

        String date_purchased = jsonObject.getString("date");
        JSONArray items_list = jsonObject.getJSONArray("items");
        String store_name = jsonObject.getString("store");

        Item[] items = new Item[items_list.length()];

        for (int i = 0; i < items_list.length(); i++) {
            JSONArray item = items_list.getJSONArray(i);
            items[i] = new Item ((String) item.get(0), (double)item.get(1));
        }


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
            return resultString;
        }

        @Override
        protected void onPostExecute(String resultString) {
            JSONObject jsonObject;

            try {
                JSONArray jsonArray = new JSONArray(resultString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    mReceipt = parseReceipt(jsonObject);
                    //parseReceipt(jsonObject);
                    add(mReceipt);
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

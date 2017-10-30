package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Aleix on 10/20/2017.
 */

public class ReceiptListActivity extends AppCompatActivity {


    //private ArrayList<Receipt> mReceipts;
    private Receipt mReceipt;
    private ReceiptLab mReceiptLab;
    private RecyclerView mRecyclerView;
    private ReceiptAdapter mReceiptAdapter;
    private boolean mLoading;


    private final String AUTHORITY = "137.149.157.18";
    private final String PATH = "CS2130/e-receipt/";
    private final String SCHEME = "http";
    private final String QUERY = "date";
    private final String QUERY_PARAM = "20171004";

    private final String URL = "http://137.149.157.18/CS2130/e-receipt/?date=20171001";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*mReceiptAdapter = new ReceiptAdapter(mReceipts);
        mRecyclerView.setAdapter(mReceiptAdapter);*/
        updateUI();
        updateRecyclerView();

    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent (packageContext,ReceiptListActivity.class);
        return intent;
    }

    private void updateRecyclerView() {
        ReceiptLab receiptLab = ReceiptLab.get(this);
        List<Receipt> receipts = receiptLab.getReceipts();

        if (mReceiptAdapter == null) {
            mReceiptAdapter = new ReceiptAdapter(receipts);
            mRecyclerView.setAdapter(mReceiptAdapter);
        } else {
            mReceiptAdapter.notifyDataSetChanged();
        }

    }

    private URL buildURL( ) {
        URL url = null;
        Uri.Builder uri = new Uri.Builder();
        try {
            url = new URL (uri.scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendEncodedPath(PATH)
                    .appendQueryParameter(QUERY, QUERY_PARAM)
                    .build().toString());
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private void updateUI(){
        URL url = buildURL();
        new eReceiptQuery().execute(url);
    }

    private static Receipt parseReceipt(JSONObject jsonObject) throws JSONException {

        String date_purchased = jsonObject.getString("date");
        String items_list = jsonObject.getString("items");
        String store_name = jsonObject.getString("store");

        Receipt receipt = new Receipt(date_purchased, store_name);

        /*JSONArray jsArr = jsonObject.getJSONArray("details");
        for (int i = 0; i < jsArr.length(); i++) {
            receipt.addTurn(jsArr.getString(i));
        }*/

        return receipt;
    }

    /**
     *              AsyncTask to receive from a URL
     */
    private class eReceiptQuery extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mLoading = true;
            //mReceipts.clear();
        }

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
                Toast.makeText(ReceiptListActivity.this, "Error connecting to the Website ",
                        Toast.LENGTH_SHORT).show();
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
                    mReceiptLab.add(mReceipt);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                //mReceipts.clear();
                Toast.makeText(ReceiptListActivity.this, "Error loading receipts", Toast.LENGTH_SHORT).show();
            } finally {
                mLoading = false;
            }
            mReceiptAdapter.notifyDataSetChanged();
        }
    }

    private class ReceiptHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //private TextView mName;
        private TextView mDate;
        private Receipt mReceipt;

        public ReceiptHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_receipt, parent, false));
            itemView.setOnClickListener(this);

            mDate = (TextView) itemView.findViewById(R.id.receipt_date);
        }

        public void bind (Receipt receipt){
            mReceipt = receipt;
            mDate.setText("Date of receipt: "+mReceipt.getDate());
        }

        @Override
        public void onClick(View view) {
            //Intent intent = ReceiptActivity.receiptsInstance(ReceiptListActivity.this, mReceipt);
            //startActivity(intent);
            int clicked = R.string.clicked_toast;
            Toast.makeText(ReceiptListActivity.this, clicked, Toast.LENGTH_SHORT).show();
        }
    }


    private class ReceiptAdapter extends RecyclerView.Adapter<ReceiptHolder>{

        private List<Receipt> mReceipts;

        public ReceiptAdapter(List<Receipt> receipts) {
            mReceipts = receipts;
        }

        @Override
        public ReceiptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ReceiptListActivity.this);
            return new ReceiptHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ReceiptHolder holder, int position) {
            Receipt receipt = mReceipts.get(position);
            holder.bind(receipt);
        }

        @Override
        public int getItemCount() {
            return mReceipts.size();
        }
    }


}

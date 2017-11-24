package cs2130.trojanhorses.e_receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    //private final URL mURL = new URL("http://137.149.157.18/CS2130/e-receipt/?date=20171001");

    private boolean mLoading;
    private Receipt mReceipt;
    private ReceiptLab mReceiptLab;

    //public MainActivity() throws MalformedURLException {
    //}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowReceiptsButton(View view){
        mLoading = false;
        //Intent intent = ReceiptListActivity.newIntent(MainActivity.this);
        Intent intent = new Intent (this, ReceiptListActivity.class);
        intent.putExtra("load", mLoading);
        startActivity(intent);
    }

    public void onClickCheckBudgetButton(View view){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);

    }

    public void onClickLoadReceiptsButton(View view){
        mLoading=true;
        Intent intent = new Intent (this, ReceiptListActivity.class);
        intent.putExtra("load", mLoading);
        startActivity(intent);
    }

    public void onClickAddReceiptsButton(View view){

    }

    /*private static Receipt parseReceipt(JSONObject jsonObject) throws JSONException {
        String datePurchased = jsonObject.getString("date");
        JSONArray itemsList = jsonObject.getJSONArray("items");
        String storeName = jsonObject.getString("store");
        Item[] items = new Item[itemsList.length()];
        for (int i = 0; i < itemsList.length(); i++) {
            JSONArray item = itemsList.getJSONArray(i);
            items[i] = new Item ((String) item.get(0), (double)item.get(1));
        }

        Receipt receipt = new Receipt(datePurchased, storeName, items);

        return receipt;
    }*/
/*
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
                //System.out.println(resultString);
            }catch(IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error connecting to the Website ",
                        Toast.LENGTH_SHORT).show();
            }finally {
                httpURLConnection.disconnect();
            }
            return resultString;
        }

        @Override
        protected void onPostExecute(String resultString) {
            try {
                JSONObject jsonObject;

                JSONArray jsonArray = new JSONArray(resultString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    mReceipt = parseReceipt(jsonObject);
                    mReceiptLab.add(mReceipt);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                //mReceipts.clear();
                Toast.makeText(MainActivity.this, "Error loading receipts", Toast.LENGTH_SHORT).show();
            } finally {
                mLoading = false;
            }
            //mReceiptAdapter.notifyDataSetChanged();
        }
    }*/
}


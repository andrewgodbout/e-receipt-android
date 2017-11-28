package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by David on 10/20/2017.
 */

public class ReceiptListActivity extends AppCompatActivity implements Callbackable {

    private ReceiptLab mReceiptLab;
    private RecyclerView mRecyclerView;
    private ReceiptAdapter mReceiptAdapter;
    private boolean mLoading;
    private boolean mSubtitleVisible;

    public void update() {updateRecyclerView();}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);

        Intent intent = getIntent();
        mLoading = intent.getBooleanExtra("load", true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_receipt_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                ReceiptListActivity.this.invalidateOptionsMenu();
                updateSubtitle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSubtitle(){
        int ReceiptCount = mReceiptLab.size();
        String subtitle = getString(R.string.subtitle_format, ReceiptCount);
        if(!mSubtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) ReceiptListActivity.this;
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent (packageContext,ReceiptListActivity.class);
        return intent;
    }

    private void updateRecyclerView() {
        List<Receipt> receipts = mReceiptLab.getReceipts();

        if (mReceiptAdapter == null) {
            mReceiptAdapter = new ReceiptAdapter(receipts);
            mRecyclerView.setAdapter(mReceiptAdapter);
        } else {
            mReceiptAdapter.setReceipts(receipts);
            mReceiptAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private void loadData() {
        mReceiptLab = ReceiptLab.get(ReceiptListActivity.this, this, mLoading); //start asynctask
        updateRecyclerView();
    }

    private class ReceiptHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mPrice;
        private TextView mDate;
        private TextView mStore;

        private Receipt mReceipt;

        public ReceiptHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_receipt, parent, false));
            itemView.setOnClickListener(this);

            mDate = (TextView) itemView.findViewById(R.id.receipt_date);
            mPrice = (TextView) itemView.findViewById(R.id.price_total);
            mStore = (TextView) itemView.findViewById(R.id.store_name);
        }

        public void bind (Receipt receipt){
            mReceipt = receipt;
            mDate.setText("Date Purchased: "+receipt.getDate());
            mPrice.setText(mReceipt.getTotal());
            mStore.setText("Store: "+receipt.getStore());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ReceiptActivity.dataInstance(ReceiptListActivity.this, mReceipt);
            startActivity(intent);
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

        public void setReceipts(List<Receipt> receipts){
            mReceipts = receipts;
        }
    }
}
package cs2130.trojanhorses.e_receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Aleix on 10/20/2017.
 */

public class ReceiptListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ReceiptAdapter mReceiptAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);
    }

    private class ReceiptHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mName;
        private TextView mDate;
        private Receipt mReceipt;

        public ReceiptHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_receipt, parent, false));
            itemView.setOnClickListener(this);

            mDate = (TextView) itemView.findViewById(R.id.receipt_date);
        }

        public void bind (Receipt receipt){
            mDate.setText(mReceipt.getDate());
        }

        @Override
        public void onClick(View view) {
            //Intent intent = ReceiptActivity.receiptsInstance(ReceiptListActivity.this, mReceipt);
            //startActivity(intent);
        }
    }

    private class ReceiptAdapter extends RecyclerView.Adapter<ReceiptHolder>{
        @Override
        public ReceiptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ReceiptHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


}

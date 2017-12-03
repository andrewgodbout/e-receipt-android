package cs2130.trojanhorses.e_receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**Primary screen that the user interacts with, providing options to load, show, and display total spending */

public class MainActivity extends AppCompatActivity {

    private boolean mLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Only displays the receipts, data is NOT loaded from the server
     * */
    public void onClickShowReceiptsButton(View view){
        mLoading = false;
        Intent intent = new Intent (this, ReceiptListActivity.class);
        intent.putExtra("load", mLoading);
        startActivity(intent);
    }

    public void onClickCheckBudgetButton(View view){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);

    }
    /**Loads the data from the server*/
    public void onClickLoadReceiptsButton(View view){
        mLoading=true;
        Intent intent = new Intent (this, ReceiptListActivity.class);
        intent.putExtra("load", mLoading);
        startActivity(intent);
    }

    public void onClickAddReceiptsButton(View view){
        Intent intent = new Intent(this, AddReceiptActivity.class);
        startActivity(intent);
    }

}


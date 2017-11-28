package cs2130.trojanhorses.e_receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowReceiptsButton(View view){
        //mLoading = false;
        Intent intent = new Intent (this, ReceiptListActivity.class);
        intent.putExtra("load", false);
        startActivity(intent);
    }

    public void onClickCheckBudgetButton(View view){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);

    }

    public void onClickLoadReceiptsButton(View view){
        //mLoading=true;
        Intent intent = new Intent (this, ReceiptListActivity.class);
        intent.putExtra("load", true);
        startActivity(intent);
    }

    public void onClickAddReceiptsButton(View view){

    }

}


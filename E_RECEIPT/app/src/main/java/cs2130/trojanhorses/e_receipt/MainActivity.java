package cs2130.trojanhorses.e_receipt;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowReceiptsButton(View view){
        Intent intent = ReceiptListActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }

    public void onClickCheckBudgetButton(View view){

    }

    public void onClickLoadReceiptsButton(View view){

    }

    public void onClickAddReceiptsButton(View view){

    }

}


package cs2130.trojanhorses.e_receipt;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by davidadams on 2017-10-21.
 */

public class ReceiptPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Receipt> mReceipts;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_pager);

        mViewPager = (ViewPager) findViewById(R.id.receipt_view_pager);

        //mReceipts = ReceiptList.get(this).getReceipts();

    }


}

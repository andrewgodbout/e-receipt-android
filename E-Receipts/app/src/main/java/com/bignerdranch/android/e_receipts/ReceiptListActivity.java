package com.bignerdranch.android.e_receipts;

import android.support.v4.app.Fragment;


public class ReceiptListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {return new ReceiptListFragment(); }
}

package com.bignerdranch.android.e_receipts;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class ReceiptList_Activity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {return new ReceiptList_Fragment(); }
}

package cs2130.trojanhorses.e_receipt;

import android.support.v4.app.Fragment;

/**
 * Created by Aleix on 10/20/2017.
 */

public class ReceiptListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ReceiptListFragment();
    }
}

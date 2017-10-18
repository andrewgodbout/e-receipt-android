package com.bignerdranch.android.e_receipts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by davidadams on 2017-10-14.
 */

public abstract class SingleFragmentActivity{

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}

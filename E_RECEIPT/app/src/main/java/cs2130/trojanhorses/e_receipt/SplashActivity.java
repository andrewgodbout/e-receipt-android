package cs2130.trojanhorses.e_receipt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by davidadams on 2017-11-21.
 */
/**Splash activity that loads a splash screen at the launch of the app*/
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 975;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        /**Delays the launch of the app*/
        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(SplashActivity.this,
                        MainActivity.class);

                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();

            }
        }, SPLASH_DISPLAY_TIME);
    }
}

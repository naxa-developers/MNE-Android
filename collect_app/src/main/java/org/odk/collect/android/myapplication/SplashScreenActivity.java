package org.odk.collect.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupListActivity;
import org.odk.collect.android.myapplication.onboarding.LoginActivity;
import org.odk.collect.android.myapplication.onboarding.UserLocalSource;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_splash);

        int SPLASH_TIME = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                endSplashScreen();
            }
        }, SPLASH_TIME);

    }

    private void endSplashScreen() {
        Intent intent;

        if (UserLocalSource.getINSTANCE().isLoggedIn(this)) {
            intent = new Intent(SplashScreenActivity.this, ActivityGroupListActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}

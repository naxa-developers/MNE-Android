package org.odk.collect.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.SplashScreenActivity;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.listeners.PermissionListener;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupListActivity;
import org.odk.collect.android.myapplication.cluster.ClusterListActivity;
import org.odk.collect.android.myapplication.onboarding.LoginActivity;
import org.odk.collect.android.myapplication.onboarding.UserLocalSource;
import org.odk.collect.android.myapplication.utils.PermissionUtil;
import org.odk.collect.android.utilities.DialogUtils;
import org.odk.collect.android.utilities.PermissionUtils;
import org.odk.collect.android.utilities.ThemeUtils;

public class PracticalActionSplashScreenActivity extends AppCompatActivity {

    private static final boolean EXIT = true;
    private ThemeUtils themeUtils;

    public static final int REQUEST_CODE_SETTINGS = 234;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this splash screen should be a blank slate
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        themeUtils = new ThemeUtils(this);
        setTheme(themeUtils.getPraticalActionTheme());
        setContentView(R.layout.activity_screen_splash);
        showDialogOK();
    }


    private void showDialogOK() {
        new PermissionUtils(this).requestStoragePermissions(new PermissionListener() {
            @Override
            public void granted() {
                try {
                    Collect.createODKDirs();
                } catch (RuntimeException e) {
                    DialogUtils.showDialog(DialogUtils.createErrorDialog(PracticalActionSplashScreenActivity.this,
                            e.getMessage(), EXIT), PracticalActionSplashScreenActivity.this);
                    return;
                }
                startSplash();
            }

            @Override
            public void denied() {
                finishAffinity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTINGS:
                if (!PermissionUtil.areStoragePermissionsGranted(this)) {
                    finishAffinity();
                } else {
                    startSplash();
                }
                break;
        }
    }

    private void startSplash() {
        int SPLASH_TIME = 2000;

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
            intent = new Intent(PracticalActionSplashScreenActivity.this, ClusterListActivity.class);
        } else {
            intent = new Intent(PracticalActionSplashScreenActivity.this, LoginActivity.class);
        }

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}

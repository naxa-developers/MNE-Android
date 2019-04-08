package org.odk.collect.android.myapplication.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;

public class PreferencesActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupToolbar("Settings");
    }
}

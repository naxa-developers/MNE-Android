package org.odk.collect.android.mne.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.odk.collect.android.R;
import org.odk.collect.android.mne.BaseActivity;

public class PreferencesActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupToolbar("Settings");
    }
}

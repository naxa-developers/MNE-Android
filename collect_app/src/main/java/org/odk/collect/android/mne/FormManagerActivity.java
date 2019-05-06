package org.odk.collect.android.mne;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.CollectAbstractActivity;
import org.odk.collect.android.fragments.DataManagerList;

public class FormManagerActivity extends CollectAbstractActivity {

    private final DataManagerList dataManagerList = DataManagerList.newInstance();


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(getString(R.string.manage_files));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_manager_activity);
        initToolbar();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, dataManagerList, "datamanager")
                .commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.notes);
    }
}


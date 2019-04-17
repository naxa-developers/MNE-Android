package org.odk.collect.android.myapplication.activitygroup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.FormDownloadList;
import org.odk.collect.android.activities.GoogleDriveActivity;
import org.odk.collect.android.activities.InstanceUploaderList;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.Constant;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;
import org.odk.collect.android.myapplication.sync.DataSyncService;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.utilities.PlayServicesUtil;
import org.odk.collect.android.utilities.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class ActivityGroupListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerViewEmptySupport recyclerView;
    private BaseRecyclerViewAdapter<ActivityGroup, ActivityGroupVH> adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        setupToolbar("Activities Group(s)");


        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        String clusterId = hashMap.get("cluster_id");

        ActivityGroupLocalSouce.getINSTANCE()
                .getById(clusterId)
                .observe(this, activityGroups -> {
                    Timber.i("Activities: %d", activityGroups != null ? activityGroups.size() : 0);
                    setupListAdapter(activityGroups);
                });

    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        recyclerView = findViewById(R.id.rv_activity_group);
        findViewById(R.id.download_forms).setOnClickListener(this);
        findViewById(R.id.upload_forms).setOnClickListener(this);

    }

    private void setupListAdapter(List<ActivityGroup> activityGroups) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<ActivityGroup, ActivityGroupVH>(activityGroups, R.layout.list_item_activity_group) {
            @Override
            public void viewBinded(ActivityGroupVH activityGroupVH, ActivityGroup activityGroup) {
                activityGroupVH.bindView(activityGroup);
            }

            @Override
            public ActivityGroupVH attachViewHolder(View view) {
                return new ActivityGroupVH(view);
            }

        };
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_forms:
                if (Collect.allowClick(getClass().getName())) {
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(ActivityGroupListActivity.this);
                    String protocol = sharedPreferences.getString(
                            GeneralKeys.KEY_PROTOCOL, getString(R.string.protocol_odk_default));
                    Intent i = null;
                    if (protocol.equalsIgnoreCase(getString(R.string.protocol_google_sheets))) {
                        if (PlayServicesUtil.isGooglePlayServicesAvailable(ActivityGroupListActivity.this)) {
                            i = new Intent(getApplicationContext(),
                                    GoogleDriveActivity.class);
                        } else {
                            PlayServicesUtil.showGooglePlayServicesAvailabilityErrorDialog(ActivityGroupListActivity.this);
                            return;
                        }
                    } else {
                        i = new Intent(getApplicationContext(),
                                FormDownloadList.class);
                    }
                    startActivity(i);
                }
                break;
            case R.id.upload_forms:
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(),
                            InstanceUploaderList.class);
                    startActivity(i);
                }
                break;
            default:
                break;
        }
    }


}

package org.odk.collect.android.myapplication.activitygroup;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.FormDownloadList;
import org.odk.collect.android.activities.GoogleDriveActivity;
import org.odk.collect.android.activities.InstanceUploaderList;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.beneficary.BeneficiariesActivity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.TitleDesc;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.TitleDescVH;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;
import org.odk.collect.android.myapplication.utils.ActivityUtil;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.utilities.PlayServicesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ActivityGroupListActivity extends BaseActivity implements View.OnClickListener, TitleDescAdapter.OnCardClickListener {

    DisposableObserver<Object> dis;
    private Toolbar toolbar;
    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;
    private ArrayList<ActivityGroup> activityGroups = new ArrayList<>(0);
    private BaseRecyclerViewAdapter<ActivityGroup, ActivityGroupVH> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();


        dis = ActivityGroupRemoteSource.getInstance()
                .getActivityGroups()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        ActivityGroupLocalSouce.getINSTANCE()
                .getById("")
                .observe(this, activityGroups -> {
                    Timber.i("Activities: %d", activityGroups != null ? activityGroups.size() : 0);
                    setupListAdapter(activityGroups);
                });

        findViewById(R.id.download_forms).setOnClickListener(this);
        findViewById(R.id.upload_forms).setOnClickListener(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        recyclerView = findViewById(R.id.rv_activity_group);

    }

    private void setupListAdapter(List<ActivityGroup> activityGroups) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setEmptyView(findViewById(R.id.root_layout_empty_layout), "No data"
                , () -> {

                });

        adapter = new BaseRecyclerViewAdapter<ActivityGroup, ActivityGroupVH>(activityGroups, R.layout.list_item_title_desc) {
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dis != null) {
            dis.dispose();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retry:
                // TODO 19/03/17
                break;
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

    @Override
    public void onCardClicked(TitleDesc surveyForm) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cluster_id", surveyForm.getSecondaryId());

        ActivityUtil.openActivity(BeneficiariesActivity.class, this, hashMap, false);
    }
}

package org.odk.collect.android.myapplication.activitygroup;

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

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class ActivityGroupListActivity extends BaseActivity implements View.OnClickListener, TitleDescAdapter.OnCardClickListener {

    DisposableObserver<ArrayList<TitleDesc>> dis;
    private Toolbar toolbar;
    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;
    private ArrayList<TitleDesc> titleDescs = new ArrayList<>(0);
    private BaseRecyclerViewAdapter<TitleDesc, TitleDescVH> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        setupListAdapter(titleDescs);

        dis = ActivityGroupRemoteSource.getInstance()
                .getAllActivitiesGroup()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showProgress();
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        hideProgress();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<TitleDesc>>() {
                    @Override
                    public void onNext(ArrayList<TitleDesc> titleDescs) {
                        setupListAdapter(titleDescs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
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

    private void setupListAdapter(ArrayList<TitleDesc> titleDescs) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setEmptyView(findViewById(R.id.root_layout_empty_layout), "No data"
                , new RecyclerViewEmptySupport.OnEmptyLayoutClickListener() {
                    @Override
                    public void onRetryButtonClick() {

                    }
                });

        adapter = new BaseRecyclerViewAdapter<TitleDesc, TitleDescVH>(titleDescs, R.layout.list_item_title_desc) {
            @Override
            public void viewBinded(TitleDescVH titleDescVH, TitleDesc titleDesc) {
                titleDescVH.bindView(titleDesc);
            }

            @Override
            public TitleDescVH attachViewHolder(View view) {
                return new TitleDescVH(view);
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

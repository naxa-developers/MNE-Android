package org.odk.collect.android.myapplication.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.TitleDesc;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class ActivityListActivity extends BaseActivity {

    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;
    private DisposableObserver<ArrayList<TitleDesc>> dis;
    private Toolbar toolbar;
    private BaseRecyclerViewAdapter<Activity, ActivityVH> adapter;
    private String activityGroupId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_acitivity);
        initView();


        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        activityGroupId = hashMap.get("activity_group_id");
        ActivityLocalSource.getInstance().getById(activityGroupId)
                .observe(this, activityList -> {
                    Timber.i("activity: %d", activityList != null ? activityList.size() : 0);
                    setupListAdapter(activityList);
                });
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.toolbar_title_activities));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.rv_activity_group);

    }

    private void setupListAdapter(List<Activity> activities) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setEmptyView(findViewById(R.id.root_layout_empty_layout), "No data"
                , () -> {

                });
        adapter = new BaseRecyclerViewAdapter<Activity, ActivityVH>(activities, R.layout.list_item_title_desc) {
            @Override
            public void viewBinded(ActivityVH activityVH, Activity activity) {
                activityVH.bindView(activity);
            }

            @Override
            public ActivityVH attachViewHolder(View view) {
                return new ActivityVH(view);
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

}

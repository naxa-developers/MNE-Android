package org.odk.collect.android.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.beneficary.BeneficiariesActivity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.TitleDesc;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;
import org.odk.collect.android.myapplication.utils.ActivityUtil;
import org.odk.collect.android.myapplication.utils.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class ActivityListActivity extends BaseActivity {

    private RecyclerViewEmptySupport recyclerView;
    private String clusterId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_acitivity);
        initView();


        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        String activityGroupId = hashMap.get("activity_group_id");
        clusterId = hashMap.get("cluster_id");

        ActivityLocalSource.getInstance().getById(activityGroupId)
                .observe(this, activityList -> {
                    Timber.i("activity: %d", activityList != null ? activityList.size() : 0);
                    setupListAdapter(activityList);
                });
    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        BaseRecyclerViewAdapter<Activity, ActivityVH> adapter = new BaseRecyclerViewAdapter<Activity, ActivityVH>(activities, R.layout.list_item_activity) {
            @Override
            public void viewBinded(ActivityVH activityVH, Activity activity) {
                activityVH.bindView(activity);
            }

            @Override
            public ActivityVH attachViewHolder(View view) {
                return new ActivityVH(view) {
                    @Override
                    void viewItemClicked(Activity activity) {
                        boolean hasBeneficiaries = activity.getBeneficiaryLevel();
                        String activityId = activity.getId();
                        String formId = activity.getForm();

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("activity_id", activityId);
                        hashMap.put("form_id", formId);
                        hashMap.put("cluster_id", clusterId);


                        if (hasBeneficiaries) {
                            ActivityUtil.openActivity(BeneficiariesActivity.class, ActivityListActivity.this, hashMap, false);
                        } else {
                            ActivityUtil.openFormEntryActivity(ActivityListActivity.this, activity.getForm(), activity.getId(), "");
                        }
                    }
                };
            }
        };
        recyclerView.setAdapter(adapter);
    }

}

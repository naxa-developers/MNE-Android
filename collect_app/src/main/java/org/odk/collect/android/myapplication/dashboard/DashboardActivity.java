package org.odk.collect.android.myapplication.dashboard;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shuhart.stickyheader.StickyHeaderItemDecorator;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activity.ActivityLocalSource;
import org.odk.collect.android.myapplication.activity.ActivityVH;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.beneficary.BeneficaryVH;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.BaseSectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DashboardActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private BaseRecyclerViewAdapter<Activity, ActivityVH> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
        ActivityLocalSource.getInstance().getById("1")
                .observe(this, new Observer<List<Activity>>() {
                    @Override
                    public void onChanged(@Nullable List<Activity> activityList) {

                    }
                });

        ActivityGroup activityGroup = new ActivityGroup();
        activityGroup.setId("1");

        Activity activity = new Activity();
        activity.setId("1.1");
        activityGroup.setActivity(Collections.singletonList(activity));

        List<ActivityGroup> list = Collections.singletonList(activityGroup);
        setupListAdapter(list);
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.toolbar_title_activities));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycler_view);
    }


    private void setupListAdapter(List<ActivityGroup> activities) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SectionAdapter adapter = new SectionAdapter();
        recyclerView.setAdapter(adapter);
        StickyHeaderItemDecorator decorator = new StickyHeaderItemDecorator(adapter);
        decorator.attachToRecyclerView(recyclerView);


        adapter.items = new ArrayList<Section>() {{
            for (ActivityGroup activityGroup : activities) {
                add(new SectionHeader(0, activityGroup.getName()));
                for (Activity activity : activityGroup.getActivity()) {
                    add(new SectionItem(activity.getName()));
                }
            }
        }};

        adapter.notifyDataSetChanged();
    }


}

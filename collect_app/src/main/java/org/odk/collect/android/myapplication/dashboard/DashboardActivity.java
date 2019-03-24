package org.odk.collect.android.myapplication.dashboard;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shuhart.stickyheader.StickyHeaderItemDecorator;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activity.ActivityLocalSource;
import org.odk.collect.android.myapplication.activity.ActivityVH;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupLocalSouce;
import org.odk.collect.android.myapplication.activitygroup.model.Activity;
import org.odk.collect.android.myapplication.activitygroup.model.ActivityGroup;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.database.dao.ActivityGroupAndActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

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
        ActivityGroupLocalSouce.getINSTANCE().getActGroupAndActById("")
                .observe(this, new Observer<List<ActivityGroupAndActivity>>() {
                    @Override
                    public void onChanged(@Nullable List<ActivityGroupAndActivity> activityGroupAndActivities) {
                        if (activityGroupAndActivities != null) {
                            setupListAdapter(activityGroupAndActivities);
                        }
                    }
                });

        ActivityLocalSource.getInstance().getById("1")
                .observe(this, new Observer<List<Activity>>() {
                    @Override
                    public void onChanged(@Nullable List<Activity> activityList) {

                    }
                });



    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.toolbar_title_activities));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycler_view);
    }


    private void setupListAdapter(List<ActivityGroupAndActivity> activities) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SectionAdapter adapter = new SectionAdapter();
        recyclerView.setAdapter(adapter);
        StickyHeaderItemDecorator decorator = new StickyHeaderItemDecorator(adapter);
        decorator.attachToRecyclerView(recyclerView);


        adapter.items = new ArrayList<Section>() {{
            for (int i = 0; i < activities.size(); i++) {
                ActivityGroupAndActivity activityGroup = activities.get(i);
                add(activityGroup.getGroup());
                this.addAll(activityGroup.getActivities());
            }
        }};

        adapter.notifyDataSetChanged();
    }


}

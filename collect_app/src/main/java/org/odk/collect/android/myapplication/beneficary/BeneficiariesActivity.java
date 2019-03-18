package org.odk.collect.android.myapplication.beneficary;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activity.ActivityListActivity;
import org.odk.collect.android.myapplication.common.TitleDesc;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;

public class BeneficiariesActivity extends BaseActivity implements TitleDescAdapter.OnCardClickListener {

    DisposableObserver<ArrayList<TitleDesc>> dis;
    private Toolbar toolbar;
    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiaries_acitivity);
        initView();
        setupListAdapter();
        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        String clusterId = hashMap.get("cluster_id");
        toast(clusterId);



    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title_beneficiaries);
        recyclerView = findViewById(R.id.rv_activity_group);

    }

    private void setupListAdapter() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setEmptyView(findViewById(R.id.root_layout_empty_layout), "No data"
                , new RecyclerViewEmptySupport.OnEmptyLayoutClickListener() {
                    @Override
                    public void onRetryButtonClick() {

                    }
                });
        listAdapter = new TitleDescAdapter();
        listAdapter.setOnCardClickListener(this);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dis != null) {
            dis.dispose();
        }
    }


    @Override
    public void onCardClicked(TitleDesc surveyForm) {
        ActivityUtil.openActivity(ActivityListActivity.class, this, null, false);
    }

}

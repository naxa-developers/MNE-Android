package org.odk.collect.android.myapplication.beneficary;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activity.ActivityListActivity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.TitleDesc;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.TitleDescVH;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BeneficiariesActivity extends BaseActivity {

    DisposableObserver<List<BeneficaryResponse>> dis;
    private Toolbar toolbar;
    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;
    private BaseRecyclerViewAdapter<BeneficaryResponse, BeneficaryVH> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiaries_acitivity);
        initView();

        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        String clusterId = hashMap.get("cluster_id");

        dis = BeneficaryRemoteSource.getInstance().getBeneficaryByClusterId(clusterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<BeneficaryResponse>>() {
                    @Override
                    public void onNext(List<BeneficaryResponse> beneficaryResponses) {
                        setupListAdapter(beneficaryResponses);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title_beneficiaries);
        recyclerView = findViewById(R.id.rv_activity_group);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (dis != null) {
            dis.dispose();
        }
    }


    private void setupListAdapter(List<BeneficaryResponse> beneficaryResponses) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setEmptyView(findViewById(R.id.root_layout_empty_layout), "No data"
                , new RecyclerViewEmptySupport.OnEmptyLayoutClickListener() {
                    @Override
                    public void onRetryButtonClick() {

                    }
                });

        adapter = new BaseRecyclerViewAdapter<BeneficaryResponse, BeneficaryVH>(beneficaryResponses, R.layout.list_item_beneficary) {
            @Override
            public void viewBinded(BeneficaryVH titleDescVH, BeneficaryResponse BeneficaryResponse) {
                titleDescVH.bindView(BeneficaryResponse);
            }

            @Override
            public BeneficaryVH attachViewHolder(View view) {
                return new BeneficaryVH(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }


}

package org.odk.collect.android.myapplication.beneficary;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BeneficiariesActivity extends BaseActivity {

    DisposableObserver<Object> dis;
    private Toolbar toolbar;
    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;
    private BaseRecyclerViewAdapter<BeneficaryResponse, BeneficaryVH> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiaries_acitivity);
        initView();

//        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
//        String clusterId = hashMap.get("cluster_id");

        BeneficaryLocalSource.getInstance().getById("")
                .observe(this, beneficiaries -> {
                    Timber.i("Beneficiaries: %d", beneficiaries != null ? beneficiaries.size() : 0);
                    setupListAdapter(beneficiaries);
                });

        dis = BeneficiaryRemoteSource.getInstance().getBeneficiaryByClusterId("2")
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

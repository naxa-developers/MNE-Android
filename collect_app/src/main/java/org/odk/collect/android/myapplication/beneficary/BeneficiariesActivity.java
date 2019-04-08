package org.odk.collect.android.myapplication.beneficary;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;

import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class BeneficiariesActivity extends BaseActivity {

    DisposableObserver<Object> dis;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TitleDescAdapter listAdapter;
    private BaseRecyclerViewAdapter<BeneficaryResponse, BeneficaryVH> adapter;
    private HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiaries_acitivity);
        initView();

        hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");


        BeneficaryLocalSource.getInstance().getById("")
                .observe(this, beneficiaries -> {
                    Timber.i("Beneficiaries: %d", beneficiaries != null ? beneficiaries.size() : 0);
                    setupListAdapter(beneficiaries);
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

        adapter = new BaseRecyclerViewAdapter<BeneficaryResponse, BeneficaryVH>(beneficaryResponses, R.layout.list_item_beneficary) {
            @Override
            public void viewBinded(BeneficaryVH titleDescVH, BeneficaryResponse BeneficaryResponse) {
                titleDescVH.setActivityAndBeneficiaryIds(hashMap);
                titleDescVH.bindView(BeneficaryResponse,hashMap.get("form_id"));
            }

            @Override
            public BeneficaryVH attachViewHolder(View view) {
                return new BeneficaryVH(view);
            }
        };


        recyclerView.setAdapter(adapter);
    }


}

package org.odk.collect.android.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupRemoteSource;
import org.odk.collect.android.myapplication.common.TitleDesc;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class ActivityListActivity extends BaseActivity implements TitleDescAdapter.OnCardClickListener {

    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;
    private DisposableObserver<ArrayList<TitleDesc>> dis;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_acitivity);
        initView();
        setupListAdapter();

        dis = ActivityGroupRemoteSource.getInstance().getAllActivity("")
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
                        listAdapter.addAll(titleDescs);
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
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.toolbar_title_activities));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void onCardClicked(TitleDesc surveyForm) {

    }
}

package org.odk.collect.android.myapplication.activitygroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activity.ActivityListActivity;
import org.odk.collect.android.myapplication.common.TitleDesc;
import org.odk.collect.android.myapplication.common.TitleDescAdapter;
import org.odk.collect.android.myapplication.common.view.RecyclerViewEmptySupport;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class ActivityGroupListActivity extends BaseActivity implements View.OnClickListener, TitleDescAdapter.OnCardClickListener {

    DisposableObserver<ArrayList<TitleDesc>> dis;
    private Toolbar toolbar;
    private RecyclerViewEmptySupport recyclerView;
    private TitleDescAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        setupListAdapter();

        dis = ActivityGroupRemoteSource.getInstance()
                .getAllActivitiesGroup()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

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
                        listAdapter.clear();
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
        getSupportActionBar().setTitle("Dashboard");

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
        showProgress();


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
            default:
                break;
        }
    }

    @Override
    public void onCardClicked(TitleDesc surveyForm) {
        ActivityUtil.openActivity(ActivityListActivity.class, this, null, false);
    }
}

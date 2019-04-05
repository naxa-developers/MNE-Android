package org.odk.collect.android.myapplication.cluster;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClusterListActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_cluster)
    RecyclerView rvCluster;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nv)
    NavigationView navigationView;
    @BindView(R.id.activity_main)
    DrawerLayout drawerlayout;
    private ActionBarDrawerToggle actionBarToggle;
    private BaseRecyclerViewAdapter<Cluster, ClusterVH> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_list_activty);
        ButterKnife.bind(this);
        setupNavigationDrawer();

        ClusterLocalSource.getInstance().getAll().observe(this, this::setupRecycler);

    }

    private void setupRecycler(List<Cluster> clusters) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvCluster.setLayoutManager(manager);
        rvCluster.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<Cluster, ClusterVH>(clusters, R.layout.list_item_title_desc) {
            @Override
            public void viewBinded(ClusterVH clusterVH, Cluster cluster) {
                clusterVH.bindView(cluster);
            }

            @Override
            public ClusterVH attachViewHolder(View view) {
                return new ClusterVH(view);
            }

        };
        rvCluster.setAdapter(adapter);
    }

    private void setupNavigationDrawer() {
        actionBarToggle = new ActionBarDrawerToggle(this, drawerlayout, R.string.action_open, R.string.action_close);
        drawerlayout.addDrawerListener(actionBarToggle);
        actionBarToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}

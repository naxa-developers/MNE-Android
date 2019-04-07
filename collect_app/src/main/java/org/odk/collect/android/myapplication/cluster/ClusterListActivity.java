package org.odk.collect.android.myapplication.cluster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import org.odk.collect.android.activities.FormDownloadList;
import org.odk.collect.android.activities.GoogleDriveActivity;
import org.odk.collect.android.activities.InstanceUploaderList;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.activitygroup.ActivityGroupListActivity;
import org.odk.collect.android.myapplication.common.BaseRecyclerViewAdapter;
import org.odk.collect.android.myapplication.common.Constant;
import org.odk.collect.android.myapplication.sync.DataSyncService;
import org.odk.collect.android.preferences.GeneralKeys;
import org.odk.collect.android.utilities.PlayServicesUtil;

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
        adapter = new BaseRecyclerViewAdapter<Cluster, ClusterVH>(clusters, R.layout.list_item_cluster) {
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        getSupportActionBar().setTitle("Cluster(s)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent startIntent = new Intent(this, DataSyncService.class);
        startIntent.setAction(Constant.SERVICE.STARTFOREGROUND_SYNC);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(startIntent);
        } else {
            startService(startIntent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        toggleNavDrawer();
        final int selectedItemId = menuItem.getItemId();

        new Handler().postDelayed(() -> handleNavDrawerClicks(selectedItemId), 250);

        return false;
    }

    private void handleNavDrawerClicks(int selectedItemId) {

        switch (selectedItemId) {
            case R.id.nav_download_form:

                break;
            case R.id.nav_edit_form:
                break;
            case R.id.nav_upload_form:
                if (Collect.allowClick(getClass().getName())) {
                    Intent i = new Intent(getApplicationContext(),
                            InstanceUploaderList.class);
                    startActivity(i);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void toggleNavDrawer() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            drawerlayout.openDrawer(GravityCompat.START);
        }

    }
}

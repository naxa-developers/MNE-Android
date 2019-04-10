package org.odk.collect.android.myapplication.beneficary;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;

import org.odk.collect.android.R;
import org.odk.collect.android.myapplication.BaseActivity;
import org.odk.collect.android.myapplication.common.BaseFilterableRecyclerViewAdapter;
import org.odk.collect.android.myapplication.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;


public class BeneficiariesActivity extends BaseActivity {


    DisposableObserver<Object> dis;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private BaseFilterableRecyclerViewAdapter<BeneficaryStats, BeneficaryVH> adapter;

    private HashMap<String, String> hashMap;
    private List<BeneficaryStats> beneficiaryFiltered;
    String formId;
    String activityId;
    String clusterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiaries_acitivity);
        initView();
        setupToolbar("Beneficiaries");


        hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        formId = hashMap.get("form_id");
        activityId = hashMap.get("activity_id");
        clusterId = hashMap.get("cluster_id");

        BeneficaryLocalSource.getInstance().getById(clusterId, activityId)
                .observe(this, beneficiaries -> {
                    Timber.i("Beneficiaries: %d", beneficiaries != null ? beneficiaries.size() : 0);
                    setupListAdapter(beneficiaries);
                });
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title_beneficiaries);
        recyclerView = findViewById(R.id.rv_activity_group);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        setupSearchView(menu);
        return true;
    }

    private void setupSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void setupListAdapter(List<BeneficaryStats> beneficaryResponses) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        beneficiaryFiltered = beneficaryResponses;


        adapter = new BaseFilterableRecyclerViewAdapter<BeneficaryStats, BeneficaryVH>(beneficaryResponses, R.layout.list_item_beneficary) {
            @Override
            public void viewBinded(BeneficaryVH titleDescVH, BeneficaryStats beneficaryStats) {
                titleDescVH.setActivityAndBeneficiaryIds(hashMap);
                titleDescVH.bindView(beneficaryStats, hashMap.get("form_id"));
            }

            @Override
            public BeneficaryVH attachViewHolder(View view) {
                return new BeneficaryVH(view) {
                    @Override
                    public void viewItemClicked(BeneficaryStats beneficaryResponse) {

                        String beneficiaryId = String.valueOf(beneficaryResponse.getId());
                        ActivityUtil.openFormEntryActivity(BeneficiariesActivity.this, formId, activityId, beneficiaryId);
                    }
                };

            }


            @Override
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence charSequence) {
                        String charString = charSequence.toString();
                        Timber.i(charString);
                        if (charString.isEmpty()) {
                            beneficiaryFiltered = beneficaryResponses;
                        } else {
                            List<BeneficaryStats> filteredList = new ArrayList<>();

                            for (BeneficaryStats row : getData()) {

                                if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                                    filteredList.add(row);
                                }
                            }
                            beneficiaryFiltered = filteredList;
                        }

                        FilterResults filterResults = new FilterResults();
                        filterResults.values = beneficiaryFiltered;
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        beneficiaryFiltered = (ArrayList<BeneficaryStats>) results.values;
                        adapter.notifyDataSetChanged();
                    }
                };
            }

            @Override
            public int getItemCount() {
                return beneficiaryFiltered.size();
            }


        };


        recyclerView.setAdapter(adapter);
    }


}

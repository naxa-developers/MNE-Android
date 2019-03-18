package org.odk.collect.android.myapplication.beneficary;

import org.odk.collect.android.myapplication.activitygroup.model.ClusterResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BenficaryAPI {

    @GET("/core/beneficiary/")
    Observable<List<ClusterResponse>> getBenficaryForCluster(@Query("cluster") String cluster);
}

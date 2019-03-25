package org.odk.collect.android.myapplication.beneficary;

import org.odk.collect.android.myapplication.activitygroup.model.ClusterResponse;
import org.odk.collect.android.myapplication.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BenficaryAPI {

    @GET("/core/beneficiary/")
    Observable<List<BeneficaryResponse>> getBenficaryForCluster(@Query("cluster") String cluster);

    @Deprecated
    @GET(Constant.URLs.GET_CLUSTER)
    Observable<List<ClusterResponse>> getCluster();
}

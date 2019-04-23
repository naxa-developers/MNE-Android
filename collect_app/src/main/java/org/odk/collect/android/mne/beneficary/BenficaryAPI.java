package org.odk.collect.android.mne.beneficary;

import org.odk.collect.android.mne.activitygroup.model.ClusterResponse;
import org.odk.collect.android.mne.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BenficaryAPI {

    @GET("/core/beneficiary/")
    Observable<List<BeneficaryResponse>> getBenficaryForCluster(@Query("cluster") Integer cluster);

    @Deprecated
    @GET(Constant.URLs.GET_CLUSTER)
    Observable<List<ClusterResponse>> getCluster();
}

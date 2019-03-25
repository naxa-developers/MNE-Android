package org.odk.collect.android.myapplication.cluster;

import org.odk.collect.android.myapplication.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

interface ClusterAPI {

    @GET(Constant.URLs.GET_CLUSTER)
    Observable<List<Cluster>> getCluster();
}

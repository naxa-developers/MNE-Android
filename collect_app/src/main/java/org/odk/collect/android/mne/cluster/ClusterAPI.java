package org.odk.collect.android.mne.cluster;

import org.odk.collect.android.mne.common.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ClusterAPI {

    @GET(Constant.URLs.GET_CLUSTER)
    Observable<List<Cluster>> getCluster();
}

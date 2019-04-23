package org.odk.collect.android.mne.beneficary;

import org.odk.collect.android.mne.api.ServiceGenerator;
import org.odk.collect.android.mne.cluster.Cluster;
import org.odk.collect.android.mne.cluster.ClusterAPI;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BeneficiaryRemoteSource {
    private static BeneficiaryRemoteSource INSTANCE = null;


    public static BeneficiaryRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeneficiaryRemoteSource();
        }
        return INSTANCE;
    }


    public Observable<List<BeneficaryResponse>> getAll() {
        return ServiceGenerator.createService(ClusterAPI.class)
                .getCluster()
                .subscribeOn(Schedulers.io())
                .flatMapIterable((Function<List<Cluster>, Iterable<Cluster>>) clusters -> clusters)
                .flatMap(cluster -> getBeneficiaryByClusterId(cluster.getId()));
    }

    public Observable<List<BeneficaryResponse>> getBeneficiaryByClusterId(Integer clusterId) {
        return ServiceGenerator.createService(BenficaryAPI.class).getBenficaryForCluster(clusterId)
                .map(new Function<List<BeneficaryResponse>, List<BeneficaryResponse>>() {
                    @Override
                    public List<BeneficaryResponse> apply(List<BeneficaryResponse> beneficaryResponses) throws Exception {
                        BeneficaryLocalSource.getInstance().deleteByClusterId(clusterId);
                        return beneficaryResponses;
                    }
                })
                .map(new Function<List<BeneficaryResponse>, List<BeneficaryResponse>>() {
                    @Override
                    public List<BeneficaryResponse> apply(List<BeneficaryResponse> beneficaryResponses) throws Exception {
                        BeneficaryLocalSource.getInstance().save(beneficaryResponses);
                        return beneficaryResponses;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

}

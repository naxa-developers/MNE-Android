package org.odk.collect.android.myapplication.beneficary;

import org.odk.collect.android.myapplication.activitygroup.model.ClusterResponse;
import org.odk.collect.android.myapplication.api.ServiceGenerator;
import org.odk.collect.android.myapplication.common.TitleDesc;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class BeneficaryRemoteSource {
    private static BeneficaryRemoteSource INSTANCE = null;


    public static BeneficaryRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeneficaryRemoteSource();
        }
        return INSTANCE;
    }


    public Observable<List<BeneficaryResponse>> getBeneficaryByClusterId(String clusterId){
        return ServiceGenerator.createService(BenficaryAPI.class).getBenficaryForCluster(clusterId);
    }

}

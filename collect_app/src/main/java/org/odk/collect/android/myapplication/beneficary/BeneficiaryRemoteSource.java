package org.odk.collect.android.myapplication.beneficary;

import org.odk.collect.android.myapplication.api.ServiceGenerator;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class BeneficiaryRemoteSource {
    private static BeneficiaryRemoteSource INSTANCE = null;


    public static BeneficiaryRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeneficiaryRemoteSource();
        }
        return INSTANCE;
    }


    Observable<Object> getBeneficiaryByClusterId(String clusterId) {
        return ServiceGenerator.createService(BenficaryAPI.class).getBenficaryForCluster(clusterId)
                .map(new Function<List<BeneficaryResponse>, Object>() {
                    @Override
                    public Object apply(List<BeneficaryResponse> beneficaryResponses) throws Exception {
                        BeneficaryLocalSource.getInstance().save(beneficaryResponses);
                        return beneficaryResponses;
                    }
                });
    }

}

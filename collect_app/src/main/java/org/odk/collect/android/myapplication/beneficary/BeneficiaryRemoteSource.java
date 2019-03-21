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
                .flatMap(new Function<List<BeneficaryResponse>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<BeneficaryResponse> beneficaryResponses) throws Exception {
                        return BeneficaryLocalSource.getInstance().save(beneficaryResponses).toObservable();

                    }
                });
    }

}

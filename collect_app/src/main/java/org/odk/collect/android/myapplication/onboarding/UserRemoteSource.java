package org.odk.collect.android.myapplication.onboarding;

import org.odk.collect.android.myapplication.api.LoginAPI;
import org.odk.collect.android.myapplication.api.ServiceGenerator;
import org.odk.collect.android.myapplication.api.model.AuthResponse;

import io.reactivex.Observable;

public class UserRemoteSource {


    private static  UserRemoteSource INSTANCE = null;

    public static UserRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRemoteSource();
        }
        return INSTANCE;
    }

    public Observable<AuthResponse> authUser(String username, String password) {
        return ServiceGenerator.createService(LoginAPI.class)
                .getAuthToken(username,password);
    }
}

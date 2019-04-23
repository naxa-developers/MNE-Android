package org.odk.collect.android.mne.onboarding;

import org.odk.collect.android.mne.api.LoginAPI;
import org.odk.collect.android.mne.api.ServiceGenerator;
import org.odk.collect.android.mne.api.model.AuthResponse;

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

package org.odk.collect.android.mne.api;

import org.odk.collect.android.mne.api.model.AuthResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginAPI {

    @FormUrlEncoded
    @POST("/core/api-token-auth/")
    Observable<AuthResponse> getAuthToken(
            @Field("username") String username,
            @Field("password") String password
    );
}

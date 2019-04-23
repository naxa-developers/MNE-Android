package org.odk.collect.android.mne.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.mne.common.Constant;
import org.odk.collect.android.mne.onboarding.UserLocalSource;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder().create();

    private static Interceptor createAuthInterceptor(final String token) {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization",
                                token)
                        .build();
                return chain.proceed(request);
            }
        };
    }

    public static void clear() {
        retrofit = null;
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        String token = UserLocalSource.getINSTANCE().getUserToken(Collect.getInstance().getApplicationContext());

        boolean isTokenEmpty = token == null || token.trim().length() == 0;

        if (!isTokenEmpty) {
            okHttpClientBuilder.addInterceptor(createAuthInterceptor(token));
        }

        okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor()).addInterceptor(new ChuckInterceptor(Collect.getInstance().getApplicationContext()));
        return okHttpClientBuilder
                .build();
    }

    public static <T> T createService(Class<T> serviceClass) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(createOkHttpClient())
                    .baseUrl(Constant.BASE_URL)
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        return retrofit.create(serviceClass);
    }

}

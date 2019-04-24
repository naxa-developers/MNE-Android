package org.odk.collect.android.mne.onboarding;


import android.content.Context;
import android.text.TextUtils;

import org.odk.collect.android.mne.utils.SharedPreferenceUtils;

public class UserLocalSource {

    private static UserLocalSource INSTANCE = null;


    public static UserLocalSource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalSource();
        }
        return INSTANCE;
    }

    public void saveUserToken(Context context, String token) {
        SharedPreferenceUtils.saveToPrefs(context, SharedPreferenceUtils.PREF_VALUE_KEY.KEY_TOKEN, "TOKEN ".concat(token));
    }

    public String getUserToken(Context context) {
        return SharedPreferenceUtils.getFromPrefs(context, SharedPreferenceUtils.PREF_VALUE_KEY.KEY_TOKEN, "");
    }

    public boolean isLoggedIn(Context context) {
        String token = getUserToken(context);
        return !TextUtils.isEmpty(token);
    }

    public void clearAll() {

    }
}

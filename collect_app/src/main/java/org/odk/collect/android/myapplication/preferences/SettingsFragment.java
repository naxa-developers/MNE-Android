package org.odk.collect.android.myapplication.preferences;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import org.odk.collect.android.R;
import org.odk.collect.android.listeners.DeleteFormsListener;
import org.odk.collect.android.myapplication.onboarding.LoginActivity;
import org.odk.collect.android.myapplication.utils.ActivityUtil;
import org.odk.collect.android.utilities.ToastUtils;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    private void init() {
        addPreferencesFromResource(R.xml.pratical_action_preferences);

    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case SettingsKeys.KEY_APP_UPDATE:

                break;
            case SettingsKeys.KEY_LOGOUT:
                ToastUtils.showLongToast(getString(R.string.please_wait));
                PraticalActionUserSession.getInstance().logout(deletedForms -> {
                    ActivityUtil.openActivity(LoginActivity.class, getActivity());
                    getActivity().finishAffinity();
                });
                break;

        }
        return false;
    }

}
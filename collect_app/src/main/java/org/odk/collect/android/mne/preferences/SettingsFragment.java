package org.odk.collect.android.mne.preferences;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import org.odk.collect.android.BuildConfig;
import org.odk.collect.android.R;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.mne.common.DialogFactory;
import org.odk.collect.android.mne.onboarding.LoginActivity;
import org.odk.collect.android.mne.utils.ActivityUtil;
import org.odk.collect.android.utilities.ToastUtils;

import static org.odk.collect.android.preferences.GeneralKeys.KEY_NAVIGATION;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initNavigationPrefs();


    }

    private void initNavigationPrefs() {
        final ListPreference pref = (ListPreference) findPreference(KEY_NAVIGATION);

        if (pref != null) {
            pref.setSummary(pref.getEntry());
            pref.setOnPreferenceChangeListener((preference, newValue) -> {
                int index = ((ListPreference) preference).findIndexOfValue(newValue.toString());
                String entry = (String) ((ListPreference) preference).getEntries()[index];
                preference.setSummary(entry);
                return true;
            });
        }
    }

    private void init() {
        addPreferencesFromResource(R.xml.pratical_action_preferences);
        String formattedAppName = getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME;
        findPreference(SettingsKeys.KEY_APP_UPDATE).setTitle(formattedAppName);

        findPreference(SettingsKeys.KEY_APP_UPDATE).setOnPreferenceClickListener(this);
        findPreference(SettingsKeys.KEY_LOGOUT).setOnPreferenceClickListener(this);
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case SettingsKeys.KEY_APP_UPDATE:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id" + BuildConfig.APPLICATION_ID));
                startActivity(intent);
                break;
            case SettingsKeys.KEY_LOGOUT:
                logoutConfirmDialog();
                break;

        }
        return false;
    }

    private void logoutConfirmDialog() {

        int unsentFormCount = new InstancesDao().getUnsentInstancesCursor().getCount();

        boolean isSafeToLogout = unsentFormCount == 0;
        if (isSafeToLogout) {
            ToastUtils.showLongToast(getString(R.string.please_wait));
            PraticalActionUserSession.getInstance().logout(deletedForms -> {
                ActivityUtil.openActivity(LoginActivity.class, getActivity());
                getActivity().finishAffinity();
            });
        } else {
            String msg;
            msg = getActivity().getString(R.string.logout_message_only_filled_forms, unsentFormCount);
            DialogFactory.createMessageDialog(getActivity(),
                    getActivity().getString(R.string.msg_stop_logout),
                    msg)
                    .show();
        }
    }
}
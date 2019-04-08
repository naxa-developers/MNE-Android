package org.odk.collect.android.myapplication.preferences;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import org.odk.collect.android.R;


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

                break;

        }
        return false;
    }

}
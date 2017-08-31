package com.example.jaewon.clipboardmanagertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

public class BlankFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    //service_tap_to_search
    public static final String PREF_TAP_TO_SEARCH = "service_tap_to_search";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_service);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("settings", "preference changed: " + key);

        if(key.equals(PREF_TAP_TO_SEARCH)) {
            Preference pref = (Preference) findPreference(PREF_TAP_TO_SEARCH);

            boolean bTapToSearch = sharedPreferences.getBoolean(PREF_TAP_TO_SEARCH, true);

            if(bTapToSearch)
                getActivity().startService(new Intent(getActivity(), CheckClipboardManagerService.class));
            else
                getActivity().stopService(new Intent(getActivity(), CheckClipboardManagerService.class));
        }
    }
}

package com.amielskywalker.notebook;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppPreferences extends AppCompatActivity {

    // Inner fragment class
    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Add the preferences fragment
            addPreferencesFromResource(R.xml.app_preferences);
        }
    } // END OF SettingFragment Inner class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use activity_show_note as the layout because it's empty anyway
        setContentView(R.layout.activity_show_note);

        // Create and start fragment transaction
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Create an instance of the SettingsFragment class
        SettingsFragment settingsFragment = new SettingsFragment();

        /* Add the fragment to the empty container at activity_show_fragment's layout named
        * note_container (android.R.id.content is the same as R.id.note_container)*/
        fragmentTransaction.add(android.R.id.content, settingsFragment, "SETTINGS_FRAGMENT");
        fragmentTransaction.commit();
    }
}

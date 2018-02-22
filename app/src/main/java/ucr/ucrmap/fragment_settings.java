package ucr.ucrmap;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class fragment_settings extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String FRAGMENT_TAG = "my_preference_fragment";

    public static fragment_settings newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_settings settingsFragment = new fragment_settings();
        settingsFragment.setArguments(args);
        return settingsFragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.fragment_settings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext()); //Use this to check currently stored settings

        //Testing if settings were indeed saved
        String myString = sharedPreferences.getString("user_key", null);
        boolean gps = sharedPreferences.getBoolean("gps_key", true);
        boolean friend = sharedPreferences.getBoolean("friend_key", true);
        boolean box = sharedPreferences.getBoolean("checkbox_key", true);

        /*
        Toast.makeText(getActivity(), myString, Toast.LENGTH_LONG).show();
        if (gps == false)
        {
            Toast.makeText(getActivity(), "GPS OFF", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getActivity(), "GPS ON", Toast.LENGTH_LONG).show();
        }
        if (friend == false)
        {
            Toast.makeText(getActivity(), "Friend Locator off", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getActivity(), "Friend Locator on", Toast.LENGTH_LONG).show();
        }
        if (box == false)
        {
            Toast.makeText(getActivity(), "Checkbox off", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getActivity(), "Checkbox on", Toast.LENGTH_LONG).show();
        }
        ///////////////////////////////////////////////////////////////////////*/

    }


    @Override
    public void onResume() {
        super.onResume();
        //register the preferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        //unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {

    }

}
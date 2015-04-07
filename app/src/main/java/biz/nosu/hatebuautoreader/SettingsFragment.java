package biz.nosu.hatebuautoreader;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by inoue on 2015/04/06.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }
}

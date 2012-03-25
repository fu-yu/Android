package jp.yucha.sample.preference;

import jp.yucha.sample.preference.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class Settings extends PreferenceActivity {

	protected static final String TAG = "Settings";
	private String[] weekNames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);

		weekNames = getResources().getStringArray(R.array.list_week);

		Preference prefWd2 = findPreference(getString(R.string.pref_key_wdCheckBox2));
		prefWd2.setSummary(getSavedValue());
		prefWd2.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {

				String str = getCheckedStr((String) newValue);
				preference.setSummary(str);
				// String oldValue = getSavedValue();

				return true;
			}
		});

		Preference prefWd = findPreference(getString(R.string.pref_key_wdCheckBox));
		prefWd.setSummary(getWdString());
		prefWd.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				String str = (String) newValue;
				preference.setSummary(getWdString(str));
				return true;
			}
		});
	}

	// initial Display
	private String getWdString() {
		String str = "";
		Resources res = getResources();
		String settingStr = PreferenceManager
				.getDefaultSharedPreferences(this)
				.getString(
						getString(R.string.pref_key_wdCheckBox),
						res.getString(R.string.pref_wdCheckBox_summary_nosetting));

		String[] valuesString = res.getStringArray(R.array.list_entries);
		if (!settingStr.equals(res
				.getString(R.string.pref_wdCheckBox_summary_nosetting))) {
			String[] lists = settingStr.split(",");
			for (int i = 0; i < lists.length; i++) {
				if (lists[i].equals("" + WeekDayPickerPreference.CHECKED)) {
					if (!str.equals(""))
						str += ",";
					str += valuesString[i];
				}
			}
		} else {
			return settingStr;
		}
		return str;
	}

	private String getWdString(String settingStr) {
		String str = "";

		if (settingStr != null && !settingStr.equals("")) {
			String[] lists = settingStr.split(",");
			for (int i = 0; i < lists.length; i++) {
				if (lists[i].equals("" + WeekDayPickerPreference.CHECKED)) {
					if (!str.equals(""))
						str += ",";
					str += WeekDayPickerPreference.valuesString[i];
				}
			}
		}
		return str;
	}

	private String getSavedValue() {
		String str = PreferenceManager.getDefaultSharedPreferences(this)
				.getString(getString(R.string.pref_key_wdCheckBox2), "");
		return getCheckedStr(str);
	}

	private String getCheckedStr(String str) {
		String result = "";
		Resources res = getResources();
		String[] lists = str.split(",");
		if (lists[0].equals("")) {
			result = res.getString(R.string.pref_wdCheckBox_summary_nosetting);
		} else {
			for (int i = 0; i < lists.length; i++) {
				if (i != 0)
					result += "、";
				result += weekNames[Integer.parseInt(lists[i])];
			}
			if (lists.length == 1) {
				result += "曜日";
			}
		}
		return result;
	}
}

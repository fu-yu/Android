package jp.yucha.sample.preference;

import android.content.Context;
import android.content.res.Resources;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class WeekDayPickerPreference2 extends DialogPreference {

	private static String[] valuesString;

	private int[] work;
	public static final int CHECKED = 1;
	public static final int UNCHECKED = 0;
	private static final String TAG = "WeekDayPickerPreference2";

	public WeekDayPickerPreference2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected View onCreateDialogView() {
		Context context = getContext();

		Resources res = context.getResources();
		valuesString = res.getStringArray(R.array.list_entries);
		// initialize value
		work = res.getIntArray(R.array.list_checkd);

		String str = getPersistedString("");
		String splitStr[] = str.split(",");

		//初期化
		if (splitStr.length>0) {

			for (int i = 0; i < splitStr.length; i++) {
				Log.d(TAG, "aaa"+i+" "+splitStr[i]);
				int no = 0 ;
				try {
					no = Integer.parseInt(splitStr[i]);
					work[no] = CHECKED;
				} catch (NumberFormatException e) {
					// TODO: handle exception
				}
			}
		}

		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);

		for (int i = 0; i < valuesString.length; i++) {
			CheckBox cb = new CheckBox(context);
			cb.setText(valuesString[i]);
			cb.setChecked((str.indexOf(String.valueOf((i)))>=0));
			cb.setTag(i);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					int index = (Integer) buttonView.getTag();

					if (isChecked) {
						work[index]  = CHECKED;
					} else {
						work[index]  = UNCHECKED;
					}
				}
			});

			ll.addView(cb);
		}

		return ll;

		// return super.onCreateDialogView();
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		String str = "";
		for (int i = 0; i < work.length; i++) {
			if (work[i] == CHECKED){
				if (!str.equals(""))
					str += ",";
				str += i;
			}
		}
		if (positiveResult) {
			persistString(str);
			// Log.d("persistString:", str);
			callChangeListener(str);
		}
		super.onDialogClosed(positiveResult);
	}

}

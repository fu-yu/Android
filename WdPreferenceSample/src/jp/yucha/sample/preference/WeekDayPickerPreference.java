package jp.yucha.sample.preference;

import android.content.Context;
import android.content.res.Resources;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class WeekDayPickerPreference extends DialogPreference {

	public static String[] valuesString;
	public int[] wdCheckd;
	public static final int CHECKED =1;
	public static final int UNCHECKED=0;


	public WeekDayPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected View onCreateDialogView() {
		Context context = getContext();

		Resources res = context.getResources();
		valuesString = res.getStringArray(R.array.list_entries);
		//initialize value
		wdCheckd = res.getIntArray(R.array.list_checkd);

		String str = this.getPersistedString("");
		if (!str.equals("")){
			String splitStr[] = str.split(",");
			for (int i = 0;i<splitStr.length;i++){
				wdCheckd[i] = Integer.parseInt(splitStr[i]);
			}
//		} else {
//			wdCheckd = res.getIntArray(R.array.list_checkd);
		}

		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);

		for (int i = 0; i < valuesString.length; i++) {
			CheckBox cb = new CheckBox(context);
			cb.setText(valuesString[i]);
			cb.setChecked((wdCheckd[i] == CHECKED));
			cb.setTag(i);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					int index = (Integer) buttonView.getTag();
					if (isChecked) {
						WeekDayPickerPreference.this.wdCheckd[index] = CHECKED;
//						Log.d("wdCheckd[index]:", "1");

					} else {
						WeekDayPickerPreference.this.wdCheckd[index] = UNCHECKED;
//						Log.d("wdCheckd[index]:", "0");
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		String str = "";
		for (int i = 0; i < wdCheckd.length; i++) {
			if (!str.equals(""))
				str += ",";
			str += wdCheckd[i];
		}
		if (positiveResult){
			persistString(str);
//			Log.d("persistString:", str);
			callChangeListener(str);
		}
		super.onDialogClosed(positiveResult);
	}

}

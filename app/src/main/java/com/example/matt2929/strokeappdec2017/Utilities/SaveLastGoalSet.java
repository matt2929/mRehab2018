package com.example.matt2929.strokeappdec2017.Utilities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

/**
 * Created by matt2929 on 1/26/18.
 */

public class SaveLastGoalSet {

	public SaveLastGoalSet() {

	}

	/**
	 * @param activity
	 * @return time you last set a goal, if you've never sent one return -1
	 */
	private Long loadGoalDate(Activity activity) {
		SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(activity);
		long highScore = sf.getLong(WorkoutData.Key_Last_Goal_Set, -1);
		Log.e("last Time", highScore + "");
		return highScore;
	}

	/**
	 * Save date you set a goal
	 *
	 * @param activity
	 */
	public void saveGoalDateNow(Activity activity) {
		SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor editor = sf.edit();
		editor.putLong(WorkoutData.Key_Last_Goal_Set, System.currentTimeMillis());
		editor.commit();
	}

	/**
	 * @param activity
	 * @return is the last time you set a goal within two weeks, if no last time also return true
	 */
	public boolean isOneWeek(Activity activity) {
		Long lastDate = loadGoalDate(activity);
		if (lastDate.equals(-1L)) {
			return true;
		}
		Long oneDay = 1000L * 60L * 60L * 24L;
		return (((System.currentTimeMillis() - lastDate) / oneDay) >= 7);
	}
}

package com.example.matt2929.strokeappdec2017.Utilities;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by matt2929 on 1/24/18.
 */

public class ZeroCrossCalculation {
	float[] lastF = null;
	float zeroCross = Long.valueOf(0);
	ArrayList<Float> zeroCrosses = new ArrayList<>();

	public ZeroCrossCalculation() {

	}

	public void dataIn(float[] f) {
		if (lastF != null) {
			for (int i = 0; i < f.length; i++) {
				f[i] = f[i] - lastF[i];
			}
			for (int i = 0; i < f.length; i++) {
				if ((f[i] < 0 && lastF[i] > 0) || (f[i] > 0 && lastF[i] < 0)) {
					zeroCross += 1;
				}
			}
		} else {
			lastF = new float[f.length];
		}
		for (int i = 0; i < f.length; i++) {
			lastF[i] = f[i];
		}
	}

	public void endRep() {
		zeroCrosses.add(zeroCross);
		Log.e("Zero Cross", zeroCross + "");
		zeroCross = 0L;
		lastF = null;
	}

	public Float calculateZeroCross() {
		return averageTime(zeroCrosses);
	}

	public Float averageTime(ArrayList<Float> floats) {
		Float sum = 0f;
		for (int i = 0; i < floats.size(); i++) {
			sum += floats.get(i);
		}
		return ((sum / floats.size()));
	}
}

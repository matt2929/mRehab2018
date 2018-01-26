package com.example.matt2929.strokeappdec2017.Utilities;

import java.util.ArrayList;

/**
 * Created by matt2929 on 1/24/18.
 */

public class ZeroCrossCalculation {
	float[] lastF = null;
	Long zeroCross = 0L;
	ArrayList<Long> zeroCrosses = new ArrayList<>();

	public ZeroCrossCalculation() {

	}

	public void dataIn(float[] f) {
		if (lastF != null) {
			for (int i = 0; i < f.length; i++) {
				if ((f[i] < 0 && lastF[i] > 0) || (f[i] > 0 && lastF[i] < 0)) {
					zeroCross += 1;
				}
			}
		}
		lastF = f;
	}

	public void endRep() {
		zeroCrosses.add(zeroCross);
		zeroCross = 0L;
		lastF = null;
	}

	public Long calculateZeroCross() {

		return averageTime(zeroCrosses);
	}

	public Long averageTime(ArrayList<Long> longs) {
		Long sum = 0L;
		for (int i = 0; i < longs.size(); i++) {
			sum += longs.get(i);
		}
		Long value = ((sum / (Long.valueOf(longs.size()))));
		return value;
	}
}

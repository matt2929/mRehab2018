package com.example.matt2929.strokeappdec2017.Utilities;

import java.util.ArrayList;

/**
 * Created by matt2929 on 12/20/17.
 */

public class AverageValue {
    private ArrayList<Float> data = new ArrayList<>();
    private int SizeAverage = 0;
    private float CurrentAverage = 0;

    public AverageValue(int sizeAverage) {
        SizeAverage = sizeAverage;
    }

    public float addData(float dataPoint) {

        if (SizeAverage == -1) {
            SizeAverage++;
            if (SizeAverage > 0) {
                CurrentAverage = (CurrentAverage * ((SizeAverage - 1f) / SizeAverage) + (dataPoint * (1f / SizeAverage)));

                return CurrentAverage;
            } else {
                return 0;

            }

        } else {
            data.add(dataPoint);
            if (data.size() > SizeAverage) {
                data.remove(0);
            }
            float sum = 0;
            for (float f : data) {
                sum += f;
            }
            return sum / data.size();
        }
    }
}

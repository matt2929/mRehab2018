package com.example.matt2929.strokeappdec2017.WorkoutsView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by matt2929 on 12/22/17.
 */

public abstract class WorkoutViewAbstract extends RelativeLayout {
    float[] data = null;
    String[] strings = null;

    public WorkoutViewAbstract(Context context) {
        super(context);
    }

    public WorkoutViewAbstract(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkoutViewAbstract(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkoutViewAbstract(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void dataIn(float[] f) {
        data = f;
    }

    public void stringIn(String[] s) {
        strings = s;
    }

}

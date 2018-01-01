package com.example.matt2929.strokeappdec2017.WorkoutsView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WV_Pour extends WorkoutViewAbstract {
    Paint paintWater, paintBubble;
    Long spawnRate = 250L;
    Random generator = new Random();
    ArrayList<Bubble> bubbles = new ArrayList<>();
    Long clock = System.currentTimeMillis();

    public WV_Pour(Context context) {
        super(context);
        setUp();
    }

    public WV_Pour(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public WV_Pour(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    public WV_Pour(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    public void setUp() {
        paintWater = new Paint();
        paintWater.setColor(Color.argb(255, 120, 171, 246));
        paintBubble = new Paint();
        paintBubble.setColor(Color.argb(255, 198, 218, 232));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float angle = 0;
        float fill = 100;
        if (data != null) {
            angle = data[0];
            fill = data[1];
        }
        if (System.currentTimeMillis() - clock > spawnRate) {
            bubbles.add(new Bubble(generator.nextInt(getHeight()), getHeight(), -1 * generator.nextInt(10)));
        }

        canvas.rotate(angle, getWidth(), getHeight() / 2);
        float trueAngle = 1 - (Math.abs(angle + 90) / 90);
        float trueHeight = getHeight() - ((getHeight() - getWidth()) * trueAngle);
        Log.e("true height", "" + trueAngle);
        Rect rect = new Rect(-2000, (int) (trueHeight - ((fill) * trueHeight)), getWidth() + 2000, getHeight() + 2000);
        canvas.drawRect(rect, paintWater);
        ArrayList<Bubble> bubbleTemp = new ArrayList<>();
        for (Bubble bubble : bubbles) {
            if (bubble.move(rect.top)) {
                bubbleTemp.add(bubble);
            }
            canvas.drawCircle(bubble.getPosX(), bubble.getPosY(), (generator.nextInt(10)) + 4, paintBubble);
        }
        bubbles = new ArrayList<>(bubbleTemp);
    }

    @Override
    public void dataIn(float[] f) {
        super.dataIn(f);
    }

    private class Bubble {
        private int posX = 0, posY = 0, moveRate = 1;

        public Bubble(int posX, int posY, int moveRate) {
            this.posX = posX;
            this.posY = posY;
            this.moveRate = moveRate;
        }

        public boolean move(int top) {
            posY += moveRate;
            return posY >= top;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

    }
}

package com.example.sortingvisualizer.animation;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class AnimateViews {
    private float height;
    private float width;
    public final Context context;

    public AnimateViews(float height, float width, Context context) {
        this.height = height;
        this.width = width;
        this.context = context;
    }

    public void updateHeight(float height) {
        this.height = height;
    }

    public void updateWidth(float width) {
        this.width = width;
    }

    private void animateLeft(View view, int times){
        int by = (int) (times * width);
        view.animate().translationXBy(-by);
    }

    private void animateRight(View view, int times){
        int by = (int) (times * width);
        view.animate().translationXBy(by);
    }

    public void animateDirection(final View view, final int times, final AnimationDirection animationDirection) {
        width = view.getWidth();
        view.animate().setDuration(0);
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (animationDirection){
                    case RIGHT:
                        animateRight(view, times);
                        break;
                    case LEFT:
                        animateLeft(view, times);
                        break;
                    case NULL:
                    default:
                        break;
                }
            }
        });
    }

}

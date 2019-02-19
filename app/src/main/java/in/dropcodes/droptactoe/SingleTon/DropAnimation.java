package in.dropcodes.droptactoe.SingleTon;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import in.dropcodes.droptactoe.R;

public class DropAnimation {

    private int INFINITE = 1;

    public void FadeIn(Context context, View view) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        view.startAnimation(animation);

    }

    public void FadeOut(Context context, View view) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        view.startAnimation(animation);

    }

    public void RotateClock(Context context, View view) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_clock_wise);
        view.startAnimation(animation);

    }

    public void RotateAntiClock(Context context, View view) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_anticlock_wise);
        view.startAnimation(animation);

    }

    public void SlideFromLeft(Context context, View view) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_left);
        view.startAnimation(animation);

    }

    public void SlideFromRight(Context context, View view) {
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
        view.startAnimation(animation);

    }
}

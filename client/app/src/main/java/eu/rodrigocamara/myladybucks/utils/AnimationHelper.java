package eu.rodrigocamara.myladybucks.utils;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Rodrigo Câmara on 15/01/2018.
 */

public class AnimationHelper {

    public static void startAnimation(ImageView imageView) {
        if (imageView.getVisibility() != View.VISIBLE) {
            imageView.setVisibility(View.VISIBLE);
        }
        ((AnimationDrawable) imageView.getBackground()).start();
    }

    public static void stopAnimation(ImageView imageView) {
        if (imageView.getVisibility() == View.VISIBLE) {
            imageView.setVisibility(View.GONE);
        }
        ((AnimationDrawable) imageView.getBackground()).stop();
    }
}

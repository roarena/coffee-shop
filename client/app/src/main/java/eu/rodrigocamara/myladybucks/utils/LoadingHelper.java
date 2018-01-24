package eu.rodrigocamara.myladybucks.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.Timer;

/**
 * Created by Rodrigo Câmara on 15/01/2018.
 */

public class LoadingHelper {
    private ImageView mIvLoading;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDbReference;
    private Context mContext;
    private Timer timer = new Timer();

    public LoadingHelper(ImageView ivLoading, ChildEventListener childEventListener, DatabaseReference dbReference, Context context) {
        this.mIvLoading = ivLoading;
        this.mChildEventListener = childEventListener;
        this.mDbReference = dbReference;
        this.mContext = context;
    }

    private void startAnimation() {
        if (mIvLoading.getVisibility() != View.VISIBLE) {
            mIvLoading.setVisibility(View.VISIBLE);
        }
        ((AnimationDrawable) mIvLoading.getBackground()).start();
    }

    public void stopLoading() {
        if (mIvLoading.getVisibility() == View.VISIBLE) {
            mIvLoading.setVisibility(View.GONE);
        }
        ((AnimationDrawable) mIvLoading.getBackground()).stop();
        timer.cancel();
    }

    public void startLoading() {
        startAnimation();
    }
}

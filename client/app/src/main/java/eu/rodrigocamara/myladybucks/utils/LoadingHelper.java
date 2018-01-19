package eu.rodrigocamara.myladybucks.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.Timer;
import java.util.TimerTask;

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

       /* TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                if (OrderHelper.getInstance().getOrderList().size() == 0) {
                    mDbReference.removeEventListener(mChildEventListener);
                    Toast.makeText(mContext, "Error on retrieving orders. Try again later.", Toast.LENGTH_SHORT).show();
                    stopLoading();
                }
            }
        };
        timer.schedule(timerTask, 15000L);*/
    }
}

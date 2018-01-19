package eu.rodrigocamara.myladybucks.screens.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.rodrigocamara.myladybucks.R;

/**
 * Created by Rodrigo Câmara on 19/01/2018.
 */

public class LoadingDialog extends Dialog {
    @BindView(R.id.iv_dialog_animation)
    ImageView ivDialogAnimation;
    Activity context;

    public LoadingDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placing_order_dialog);
        setCancelable(false);

        ButterKnife.bind(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ((AnimationDrawable) ivDialogAnimation.getBackground()).start();
    }
}

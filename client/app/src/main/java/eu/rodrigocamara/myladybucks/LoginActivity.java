package eu.rodrigocamara.myladybucks;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.cl_login_constraint)
    ConstraintLayout mConstraintLayout;

    @BindView(R.id.btn_signin)
    Button mBtnLogin;

    @BindView(R.id.btn_signup)
    Button mBtnSignup;

    private ConstraintSet layoutOpen;
    private ConstraintSet layoutClosed;
    private boolean isBarOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_closed);
        ButterKnife.bind(this);

        layoutOpen = new ConstraintSet();
        layoutClosed = new ConstraintSet();

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBar(R.layout.activity_login_open_login);
            }
        });
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBar(R.layout.activity_login_open_signup);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isBarOpen) {
            closeBar();
        } else {
            super.onBackPressed();
        }
    }

    private void openBar(int layoutToOpen) {
        layoutOpen.clone(getBaseContext(), layoutToOpen);
        layoutClosed.clone(mConstraintLayout);

        TransitionManager.beginDelayedTransition(mConstraintLayout);
        layoutOpen.applyTo(mConstraintLayout);
        isBarOpen = true;
    }

    private void closeBar() {
        layoutClosed.clone(getBaseContext(), R.layout.activity_login_closed);
        layoutOpen.clone(mConstraintLayout);

        TransitionManager.beginDelayedTransition(mConstraintLayout);
        layoutClosed.applyTo(mConstraintLayout);
        isBarOpen = false;
    }
}

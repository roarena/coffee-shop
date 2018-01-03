package eu.rodrigocamara.myladybucks.screens.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.screens.MainActivity;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.SharedPreferenceHelper;
import eu.rodrigocamara.myladybucks.utils.User;

/**
 * Created by Rodrigo Câmara on 31/12/2017.
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.tv_profile_name)
    TextView mTvProfileName;
    @BindView(R.id.tv_profile_email)
    TextView mTvProfileEmail;
    @BindView(R.id.txt_profile_phone_number)
    EditText mTxtProfilePhone;
    @BindView(R.id.btn_profile_signout)
    Button mBtnProfileSignout;
    @BindView(R.id.btn_profile_clear_favorites)
    Button mBtnProfileClear;
    @BindView(R.id.btn_profile_save)
    Button mBtnProfileSave;
    @BindView(R.id.iv_profile_screen_picture)
    CircleImageView mIvProfilePicture;

    private SharedPreferenceHelper mSharedPreferenceHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        ButterKnife.bind(this, view);

        mSharedPreferenceHelper = new SharedPreferenceHelper(this.getContext());
        mTxtProfilePhone.addTextChangedListener(phoneNumberWatcher());
        mBtnProfileSignout.setOnClickListener(signoutClickListener());
        mBtnProfileSave.setOnClickListener(saveClickListener());

        setUserInfo(view.getContext());

        return view;
    }

    private void setUserInfo(Context context) {
        if (User.getUser() != null) {
            mTvProfileName.setText(User.getUser().getDisplayName());
            mTvProfileEmail.setText(User.getUser().getEmail());
            if (mSharedPreferenceHelper.getString(C.PHONE_NUMBER_PREF) != "") {
                mTxtProfilePhone.setText(mSharedPreferenceHelper.getString(C.PHONE_NUMBER_PREF));
            } else if (User.getUser().getPhoneNumber() != "") {
                mTxtProfilePhone.setText(User.getUser().getPhoneNumber());
            }
            Picasso.with(context).load(User.getUser().getPhotoUrl()).placeholder(R.drawable.profile).into(mIvProfilePicture);
        }
    }

    private View.OnClickListener saveClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedPreferenceHelper.putString(C.PHONE_NUMBER_PREF, mTxtProfilePhone.getText().toString());
            }
        };
    }

    private View.OnClickListener signoutClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedPreferenceHelper.clearPrefs();
                FirebaseAuth.getInstance().signOut();
            }
        };
    }

    private TextWatcher phoneNumberWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    mBtnProfileSave.setEnabled(true);
            }
        };
    }
}

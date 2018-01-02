package eu.rodrigocamara.myladybucks.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.utils.User;

/**
 * Created by rodri on 31/12/2017.
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.tv_profile_name)
    TextView mTvProfileName;
    @BindView(R.id.tv_profile_email)
    TextView mTvProfileEmail;
    @BindView(R.id.txt_phone_number)
    EditText mTxtProfilePhone;
    @BindView(R.id.btn_signout)
    TextView mBtnProfileSignout;
    @BindView(R.id.btn_clear_favorites)
    TextView mBtnProfileClear;
    @BindView(R.id.iv_profile_screen_picture)
    CircleImageView mIvProfilePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        ButterKnife.bind(this, view);

        setUserInfo(view.getContext());

        return view;
    }

    private void setUserInfo(Context context) {
        if (User.getUser() != null) {
            mTvProfileName.setText(User.getUser().getDisplayName());
            mTvProfileEmail.setText(User.getUser().getEmail());
            if (User.getUser().getPhoneNumber() != "") {
                mTxtProfilePhone.setText(User.getUser().getPhoneNumber());
            }
            Picasso.with(context).load(User.getUser().getPhotoUrl()).placeholder(R.drawable.profile).into(mIvProfilePicture);
        }
    }

    private View.OnClickListener phoneClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Open Dialog? Maybe change this for a editText?
            }
        };
    }
}

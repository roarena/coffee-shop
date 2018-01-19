package eu.rodrigocamara.myladybucks.screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.Scopes;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.adapters.UserOrdersAdapter;
import eu.rodrigocamara.myladybucks.screens.dialogs.LoadingDialog;
import eu.rodrigocamara.myladybucks.screens.fragments.CoffeeMenuFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.HomeFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.ProfileFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.UserOrdersFragment;
import eu.rodrigocamara.myladybucks.utils.C;
import eu.rodrigocamara.myladybucks.utils.FragmentHelper;
import eu.rodrigocamara.myladybucks.utils.Log;
import eu.rodrigocamara.myladybucks.utils.User;

public class MainActivity extends AppCompatActivity {
    // Firebase declaration
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // UI components declaration
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.nvView)
    NavigationView mNvDrawer;

    CircleImageView mIvProfilePicture;
    TextView mTvProfileEmail;
    TextView mTvProfileName;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.printLog(Calendar.getInstance().getTime().toString());
        if (savedInstanceState == null) {
            HomeFragment f1 = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.flContent, f1);
            fragmentTransaction.commit();
        }

        // Load Navigation Drawer.
        setupDrawerHeader();
        setupDrawerContent(mNvDrawer);

        // Calls for Firebase to initialize user and login if it's a first time user.
        initializeFirebase();

    }

    private void setupDrawerHeader() {
        /* Workaround due to navigation drawer issue. =(
        * https://github.com/JakeWharton/butterknife/issues/406
        * https://code.google.com/p/android/issues/detail?id=190226
        * */
        View headerLayout = mNvDrawer.inflateHeaderView(R.layout.drawer_header);
        mTvProfileName = ButterKnife.findById(headerLayout, R.id.tv_profile_name);
        mTvProfileEmail = ButterKnife.findById(headerLayout, R.id.tv_profile_phone);
        mIvProfilePicture = ButterKnife.findById(headerLayout, R.id.iv_profile_screen_picture);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        switch (menuItem.getItemId()) {
            case R.id.action_home:
                goToFragment(HomeFragment.class);
                break;
            case R.id.action_profile:
                goToFragment(ProfileFragment.class);
                break;
            case R.id.action_menu:
                goToFragment(CoffeeMenuFragment.class);
                break;
            case R.id.action_purchases:
                goToFragment(UserOrdersFragment.class);
                break;
            case R.id.action_git:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/roarena/mylady-bucks"));
                startActivity(browserIntent);
                break;
            default:
                goToFragment(HomeFragment.class);
        }


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void goToFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Do Fragment Transaction with BackStack
        FragmentHelper.doFragmentTransaction(fragment, this);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        mDrawerToggle = setupDrawerToggle();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goHome() {
        //TODO THIS NEEDS REFACTORING AFTER FRAGMENTS RE-WORK
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        mNvDrawer.getMenu().getItem(0).setChecked(true);
    }

    private void initializeFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                User.setUser(firebaseAuth.getCurrentUser());
                if (User.getUser() != null) {
                    // Already exists and it's logged.
                    updateUserInformationUI();
                } else {
                    // First time user.
                    Log.printLog("New user, sending to Login Screen");

                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).setPermissions(Arrays.asList(Scopes.PROFILE)).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setTheme(R.style.LoginTheme)
                                    .build(),
                            C.LOGIN_ID);
                    goHome();
                }
            }
        };
    }

    private void updateUserInformationUI() {
        Log.printLog("User already logged. uID: " + User.getUser().getUid());
        Picasso.with(getApplicationContext()).load(User.getUser().getPhotoUrl()).placeholder(R.drawable.profile).into(mIvProfilePicture);
        mTvProfileName.setText(User.getUser().getDisplayName());
        mTvProfileEmail.setText(User.getUser().getEmail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == C.LOGIN_ID) {
            if (resultCode == RESULT_OK) {
                initializeFirebase();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}

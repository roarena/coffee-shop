package eu.rodrigocamara.myladybucks.utils;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by rodri on 31/12/2017.
 */

public class User {
    private static FirebaseUser mUser;

    public static FirebaseUser getUser() {
        return mUser;
    }

    public static void setUser(FirebaseUser firebaseUser) {
        mUser = firebaseUser;
    }
}

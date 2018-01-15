package eu.rodrigocamara.myladybucks.utils;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rodrigo Câmara on 15/01/2018.
 */

public class FirebaseHelper {

    public static FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }
}

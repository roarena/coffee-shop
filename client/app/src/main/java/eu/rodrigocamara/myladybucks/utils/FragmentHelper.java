package eu.rodrigocamara.myladybucks.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import eu.rodrigocamara.myladybucks.R;

/**
 * Created by Rodrigo Câmara on 03/01/2018.
 */

public class FragmentHelper {
    public static void doFragmentTransaction(Fragment fragment, AppCompatActivity activity) {

        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        String backFragmentName = fragment.getClass().getName();

        boolean isFragmentOutOfStack = fragmentManager.popBackStackImmediate(backFragmentName, 0);

        if (!isFragmentOutOfStack) { //fragment not in back stack, create it.
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, fragment);
            fragmentTransaction.addToBackStack(backFragmentName);
            fragmentTransaction.commit();
        }
    }
}

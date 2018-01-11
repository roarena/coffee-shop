package eu.rodrigocamara.myladybucks.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import eu.rodrigocamara.myladybucks.R;
import eu.rodrigocamara.myladybucks.screens.fragments.OrderDetailFragment;
import eu.rodrigocamara.myladybucks.screens.fragments.HomeFragment;

/**
 * Created by Rodrigo Câmara on 03/01/2018.
 */

public class FragmentHelper {
    public static void doFragmentTransaction(Fragment fragment, AppCompatActivity activity) {

        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        String backFragmentName = fragment.getClass().getName();
        String homeFragment = HomeFragment.class.getName();
        boolean isFragmentOutOfStack = fragmentManager.popBackStackImmediate(backFragmentName, 0);

        if (!isFragmentOutOfStack) { //fragment not in back stack, create it.
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, fragment);
            if (backFragmentName != HomeFragment.class.getName()) {
                if (backFragmentName == OrderDetailFragment.class.getName()) {
                    // If we are in order detail, the only way to go is Home.
                    fragmentManager.popBackStack();
                }
                fragmentTransaction.addToBackStack(backFragmentName);
            } else {
                // If we are HOME the only way to go is OUT.
                fragmentManager.popBackStack();
            }
            fragmentTransaction.commit();
        }
    }

    public static void doFragmentTransaction(Fragment fragment, AppCompatActivity activity, Bundle bundle) {

        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        String backFragmentName = fragment.getClass().getName();
        fragment.setArguments(bundle);
        String homeFragment = HomeFragment.class.getName();
        boolean isFragmentOutOfStack = fragmentManager.popBackStackImmediate(backFragmentName, 0);

        if (!isFragmentOutOfStack) { //fragment not in back stack, create it.
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, fragment);
            if (backFragmentName != HomeFragment.class.getName()) {
                if (backFragmentName == OrderDetailFragment.class.getName()) {
                    // If we are in order detail, the only way to go is Home.
                    fragmentManager.popBackStack();
                }
                fragmentTransaction.addToBackStack(backFragmentName);
            } else {
                // If we are HOME the only way to go is OUT.
                fragmentManager.popBackStack();
            }
            fragmentTransaction.commit();
        }
    }

    public static void updateDrawerMenu(Activity activity, int menuItemToCheck) {
        NavigationView navigation = activity.findViewById(R.id.nvView);
        Menu drawer_menu = navigation.getMenu();
        MenuItem menuItem;
        menuItem = drawer_menu.findItem(menuItemToCheck);
        if (!menuItem.isChecked()) {
            menuItem.setChecked(true);
        }
    }
}

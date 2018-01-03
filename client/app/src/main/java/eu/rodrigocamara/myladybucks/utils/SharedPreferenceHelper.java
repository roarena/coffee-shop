package eu.rodrigocamara.myladybucks.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by rodrigo.camara on 03/01/2018.
 */

public class SharedPreferenceHelper {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public SharedPreferenceHelper(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(C.SHARED_PREF, Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return mSharedPreferencesEditor;
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public void putString(String key, String value) {
        mSharedPreferencesEditor.putString(key, value);
        mSharedPreferencesEditor.commit();
    }

    public void putInt(String key, int value) {
        mSharedPreferencesEditor.putInt(key, value);
        mSharedPreferencesEditor.commit();
    }

    public void removePref(String key) {
        mSharedPreferencesEditor.remove(key);
        mSharedPreferencesEditor.commit();
    }

    public void clearPrefs() {
        mSharedPreferencesEditor.clear();
        mSharedPreferencesEditor.commit();
    }
}

package eu.rodrigocamara.myladybucks.utils;

/**
 * Created by rodrigo.camara on 28/12/2017.
 */

public class Log {
    public static void printLog(String message) {
        android.util.Log.i(C.LOG_TAG, message);
    }
}

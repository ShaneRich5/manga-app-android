package com.shadow.manga.logger;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Shane on 5/14/2015.
 */
public class Logger {

    public static void m(String message) {
        Log.d("Shadow", "" + message);
    }

    public static  void toastShort(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}

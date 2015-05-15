package com.shadow.manga.extras;

import android.os.Build;

/**
 * Created by Shane on 5/14/2015.
 */
public class Util {

    public static boolean isLollipopOrGreater() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean isJellyBeanOrGreater() {
        return Build.VERSION.SDK_INT >= 16;
    }
}

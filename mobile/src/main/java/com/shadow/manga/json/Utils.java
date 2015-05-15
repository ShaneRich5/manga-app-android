package com.shadow.manga.json;

import org.json.JSONObject;

/**
 * Created by Shane on 5/14/2015.
 */
public class Utils {

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key);
    }
}

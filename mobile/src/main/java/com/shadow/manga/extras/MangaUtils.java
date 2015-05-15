package com.shadow.manga.extras;

import com.android.volley.RequestQueue;
import com.shadow.manga.json.Endpoints;
import com.shadow.manga.json.Parser;
import com.shadow.manga.json.Requestor;
import com.shadow.manga.models.Manga;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shane on 5/14/2015.
 */
public class MangaUtils {

    public static ArrayList<Manga> loadMangaList(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMangaJSON(requestQueue, Endpoints.getRequestMangaList(0));
        ArrayList<Manga> listManga = Parser.parseMangasJSON(response);
        return listManga;
    }
}

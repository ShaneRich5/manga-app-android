package com.shadow.manga.json;

import com.shadow.manga.extras.Constants;
import com.shadow.manga.extras.Util;
import com.shadow.manga.logger.Logger;
import com.shadow.manga.models.Manga;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_ALIAS;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_CATEGORY;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_HITS;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_ID;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_LAST_CHAPTER_DATE;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_MANGA;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_STATUS;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_THUMBNAIL;
import static com.shadow.manga.extras.Keys.EnpointMangaEden.KEY_TITLE;

/**
 * Created by Shane on 5/14/2015.
 */
public class Parser {
    public static ArrayList<Manga> parseMangasJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Manga> mangaList = new ArrayList<>();

        if ((response != null) && (response.length() > 0)) {
            try {
                JSONArray mangaArray = response.getJSONArray(KEY_MANGA);

                for (int i = 0; i < mangaArray.length(); i++) {
                    // defaults
                    int followers = -1;
                    int status = -1;
                    String id = Constants.NA;
                    String title = Constants.NA;
                    String latestUpdate = Constants.NA;
                    String urlThumbnail = Constants.NA;
                    String alias = Constants.NA;
                    ArrayList<String> categories = new ArrayList<>();

                    JSONObject currentManga = mangaArray.getJSONObject(i);

                    if (Utils.contains(currentManga, KEY_ID)) {
                        id = currentManga.getString(KEY_ID);
                    }

                    if (Utils.contains(currentManga, KEY_ALIAS)) {
                        alias = currentManga.getString(KEY_ALIAS);
                    }

                    if (Utils.contains(currentManga, KEY_THUMBNAIL)) {
                        urlThumbnail = currentManga.getString(KEY_THUMBNAIL);
                        if (urlThumbnail == null)
                            urlThumbnail = Constants.NA;
                        Logger.m(urlThumbnail);
                    }

                    if (Utils.contains(currentManga, KEY_HITS)) {
                        followers = currentManga.getInt(KEY_HITS);
                    }

                    if (Utils.contains(currentManga, KEY_STATUS)) {
                        status = currentManga.getInt(KEY_STATUS);
                    }

                    if (Utils.contains(currentManga, KEY_TITLE)) {
                        title = currentManga.getString(KEY_TITLE);
                    }

                    if (Utils.contains(currentManga, KEY_LAST_CHAPTER_DATE)) {
                        latestUpdate = currentManga.getString(KEY_LAST_CHAPTER_DATE);
                    }

                    if (Utils.contains(currentManga, KEY_CATEGORY)) {
                        JSONArray categoriesJSON = currentManga.getJSONArray(KEY_CATEGORY);

                        for (int j = 0; j < categoriesJSON.length(); j++) {
                            categories.add(categoriesJSON.get(j).toString());
                        }
                    }

                    Date date = null;

                    try {
                        date = dateFormat.parse(latestUpdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Manga manga = new Manga();

                    manga.setId(id);
                    manga.setFollowers(followers);
                    manga.setUrlThumbnail(urlThumbnail);
                    manga.setCategories(categories);
                    manga.setLatestUpdate(date);
                    manga.setStatus(status);
                    manga.setAlias(alias);
                    manga.setTitle(title);

                    if ((!id.equals(Constants.NA)) && (!title.equals(Constants.NA))) {
                        mangaList.add(manga);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mangaList;
    }
}

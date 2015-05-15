package com.shadow.manga.json;

import com.shadow.manga.extras.UrlEndpoints;

import static com.shadow.manga.extras.UrlEndpoints.Char.AMPERSAND;
import static com.shadow.manga.extras.UrlEndpoints.Char.QUESTION;
import static com.shadow.manga.extras.UrlEndpoints.Language.ENGLISH;
import static com.shadow.manga.extras.UrlEndpoints.Params.LIST;
import static com.shadow.manga.extras.UrlEndpoints.QUERY.LIMIT;
import static com.shadow.manga.extras.UrlEndpoints.QUERY.PAGE;
import static com.shadow.manga.extras.UrlEndpoints.URL_BASE;

/**
 * Created by Shane on 5/14/2015.
 */
public class Endpoints {

    public static String getRequestMangaList(int page, int limit){
        return URL_BASE + LIST + ENGLISH
                + QUESTION
                + PAGE + page
                + AMPERSAND
                + LIMIT + limit;
    }

    public static String getRequestMangaList(int page){
        return URL_BASE + LIST + ENGLISH
                + QUESTION
                + PAGE + page
                + AMPERSAND
                + LIMIT + 30;
    }
}

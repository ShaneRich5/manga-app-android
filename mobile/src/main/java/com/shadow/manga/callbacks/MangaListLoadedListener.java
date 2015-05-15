package com.shadow.manga.callbacks;

import com.shadow.manga.models.Manga;

import java.util.ArrayList;

/**
 * Created by Shane on 5/14/2015.
 */
public interface MangaListLoadedListener {
    void onMangaListLoaded(ArrayList<Manga> listManga);
}

package com.shadow.manga.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.shadow.manga.callbacks.MangaDetailsLoadedListener;
import com.shadow.manga.models.Manga;
import com.shadow.manga.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Shane on 5/18/2015.
 */
public class TaskLoadMangaDetail extends AsyncTask<Void, Void, Manga> {

    private MangaDetailsLoadedListener mComponent;
    private VolleySingleton mVolleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMangaDetail(MangaDetailsLoadedListener mComponent) {
        this.mComponent = mComponent;
        mVolleySingleton = VolleySingleton.getsInstance();
        requestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    protected Manga doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Manga manga) {
        if (mComponent != null)
            mComponent.onMangaDetailsLoaded(manga);
    }
}

package com.shadow.manga.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.shadow.manga.callbacks.MangaListLoadedListener;
import com.shadow.manga.extras.MangaUtils;
import com.shadow.manga.models.Manga;
import com.shadow.manga.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Shane on 5/14/2015.
 */
public class TaskLoadMangaList extends AsyncTask<Void, Void, ArrayList<Manga>> {

    private MangaListLoadedListener mContext;
    private VolleySingleton mVolleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMangaList(MangaListLoadedListener mContext) {
        this.mContext = mContext;
        mVolleySingleton = VolleySingleton.getsInstance();
        requestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Manga> doInBackground(Void... params) {
        ArrayList<Manga> listManga = MangaUtils.loadMangaList(requestQueue);
        return listManga;
    }

    @Override
    protected void onPostExecute(ArrayList<Manga> mangas) {
        if (mContext != null)
            mContext.onMangaListLoaded(mangas);
    }
}

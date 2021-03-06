package com.shadow.manga.tasks;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.shadow.manga.application.MyApplication;
import com.shadow.manga.callbacks.MangaListLoadedListener;
import com.shadow.manga.extras.MangaUtils;
import com.shadow.manga.logger.Logger;
import com.shadow.manga.models.Manga;
import com.shadow.manga.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Shane on 5/14/2015.
 */
public class TaskLoadMangaList extends AsyncTask<Void, Void, ArrayList<Manga>> {

    private MangaListLoadedListener mComponent;
    private VolleySingleton mVolleySingleton;
    private RequestQueue requestQueue;
    private int page;

    public TaskLoadMangaList(MangaListLoadedListener mComponent) {
        this.mComponent = mComponent;
        mVolleySingleton = VolleySingleton.getsInstance();
        requestQueue = mVolleySingleton.getRequestQueue();
        page = 0;
    }

    public TaskLoadMangaList(MangaListLoadedListener mComponent, int page) {
        this.mComponent = mComponent;
        mVolleySingleton = VolleySingleton.getsInstance();
        requestQueue = mVolleySingleton.getRequestQueue();
        this.page = page;
    }

    @Override
    protected ArrayList<Manga> doInBackground(Void... params) {
        return MangaUtils.loadMangaList(requestQueue, page);
    }

    @Override
    protected void onPostExecute(ArrayList<Manga> mangas) {
        if (mComponent != null)
            mComponent.onMangaListLoaded(mangas);


        Logger.toastShort(MyApplication.getAppContext(), "onPostExecute");
    }
}

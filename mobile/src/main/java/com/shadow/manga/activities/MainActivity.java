package com.shadow.manga.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.shadow.manga.R;
import com.shadow.manga.adapters.MangaAdapter;
import com.shadow.manga.callbacks.MangaListLoadedListener;
import com.shadow.manga.listeners.RecyclerEndlessOnScollListener;
import com.shadow.manga.listeners.RecyclerItemClickListener;
import com.shadow.manga.logger.Logger;
import com.shadow.manga.models.Manga;
import com.shadow.manga.tasks.TaskLoadMangaList;
import com.shadow.manga.views.DividerItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MangaListLoadedListener {

    private static final String STATE_MANGA = "state_manga";

    private ArrayList<Manga> mListManga = new ArrayList<>();
    private RecyclerView mRecyclerManga;
    private MangaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new MangaAdapter(this);
        if (savedInstanceState != null) {
            mListManga = savedInstanceState.getParcelableArrayList(STATE_MANGA);
        } else {
            new TaskLoadMangaList(this).execute();
        }

        mAdapter.setManga(mListManga);

        setUpRecyclerView();
   }

    private void setUpRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerManga = (RecyclerView) findViewById(R.id.list_manga);
        mRecyclerManga.setLayoutManager(linearLayoutManager);
        mRecyclerManga.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerManga.setAdapter(mAdapter);
        mRecyclerManga.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerManga,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Manga manga = mListManga.get(position);
                        Logger.toastShort(MainActivity.this, manga.getAlias() + " " + manga.getId());
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }));

        mRecyclerManga.setOnScrollListener(new RecyclerEndlessOnScollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Logger.toastShort(MainActivity.this, "at the end");
                new TaskLoadMangaList(MainActivity.this, currentPage);
                Logger.toastShort(MainActivity.this, "" + currentPage);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MANGA, mListManga);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMangaListLoaded(ArrayList<Manga> listManga) {
        if (mListManga.isEmpty()) {
            mListManga = listManga;
            mAdapter.setManga(listManga);
        }
        else {
            mListManga.addAll(listManga);
            mAdapter.notifyDataSetChanged();
        }
    }
}

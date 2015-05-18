package com.shadow.manga.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.shadow.manga.R;
import com.shadow.manga.adapters.MangaAdapter;
import com.shadow.manga.callbacks.MangaListLoadedListener;
import com.shadow.manga.extras.Util;
import com.shadow.manga.listeners.RecyclerEndlessOnScollListener;
import com.shadow.manga.listeners.RecyclerItemClickListener;
import com.shadow.manga.models.Manga;
import com.shadow.manga.tasks.TaskLoadMangaList;
import com.shadow.manga.views.DividerItemDecoration;

import java.util.ArrayList;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class MainActivity extends AppCompatActivity implements MangaListLoadedListener {

    private static final String STATE_MANGA = "state_manga";

    private ArrayList<Manga> mListManga = new ArrayList<>();
    private RecyclerView mRecyclerManga;
    private MangaAdapter mAdapter;
    private Toolbar mToolbar;
    private SmoothProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupProgressBar();

        mAdapter = new MangaAdapter(this);

        if (savedInstanceState != null) {
            mListManga = savedInstanceState.getParcelableArrayList(STATE_MANGA);
        } else {
            new TaskLoadMangaList(this).execute();
        }

        mAdapter.setManga(mListManga);

        setUpRecyclerView();
   }

    private void setupProgressBar() {
        mProgressBar = (SmoothProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
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
                        openDetailsActivity(manga, view);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }));

        mRecyclerManga.setOnScrollListener(new RecyclerEndlessOnScollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadFromApi(currentPage);
            }
        });
    }

    private void loadFromApi(int currentPage) {
        mProgressBar.setVisibility(View.VISIBLE);
        new TaskLoadMangaList(MainActivity.this, currentPage).execute();
    }

    @SuppressLint("NewApi")
    private void openDetailsActivity(Manga manga, View view) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

        ImageView thumbnail = (ImageView) view.findViewById(R.id.mangaThumbnail);
        Bitmap bitmap = thumbnail.getDrawingCache();

        intent.putExtra(DetailsActivity.EXTRA_MANGA, manga);
        intent.putExtra(DetailsActivity.EXTRA_BITMAP, bitmap);

        if (Util.isLollipopOrGreater()){
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, thumbnail, "thumbnail");
            startActivity(intent, options.toBundle());
        } else
            startActivity(intent);
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
        mProgressBar.setVisibility(View.GONE);
    }
}

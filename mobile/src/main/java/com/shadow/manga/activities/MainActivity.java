package com.shadow.manga.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shadow.manga.R;
import com.shadow.manga.adapters.MangaAdapter;
import com.shadow.manga.callbacks.MangaListLoadedListener;
import com.shadow.manga.extras.UrlEndpoints;
import com.shadow.manga.logger.Logger;
import com.shadow.manga.models.Manga;
import com.shadow.manga.network.VolleySingleton;
import com.shadow.manga.tasks.TaskLoadMangaList;

import org.json.JSONObject;

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

        mRecyclerManga = (RecyclerView) findViewById(R.id.list_manga);
        mRecyclerManga.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerManga.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            mListManga = savedInstanceState.getParcelableArrayList(STATE_MANGA);
        } else {
            new TaskLoadMangaList(this).execute();
        }
        mAdapter.setManga(mListManga);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        mAdapter.setManga(listManga);
    }
}

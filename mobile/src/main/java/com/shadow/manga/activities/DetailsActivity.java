package com.shadow.manga.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.shadow.manga.R;
import com.shadow.manga.models.Manga;
import com.shadow.manga.tasks.TaskLoadMangaDetail;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MANGA = "extra_manga";
    public static final String EXTRA_BITMAP = "bitmap_manga";

    private ImageView ivThumbnail;
    private Toolbar mToolbar;
    private Manga manga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivThumbnail = (ImageView) findViewById(R.id.manga_image);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            manga = extras.getParcelable(EXTRA_MANGA);
            manga.setThumbnail(extras.getParcelable(EXTRA_BITMAP));

            ivThumbnail.setImageBitmap(manga.getThumbnail());
        }

        // check if restoring from rotation, etc
        if (savedInstanceState != null) {
            manga = savedInstanceState.getParcelable(EXTRA_MANGA);
            manga.setThumbnail(savedInstanceState.getParcelable(EXTRA_BITMAP));
        } else {
            Bundle extras = getIntent().getExtras();

            if (extas != null) {
                manga = extras.getParcelable(EXTRA_MANGA);
                manga.setThumbnail(extras.getParcelable(EXTRA_BITMAP));
            } else {
                new TaskLoadMangaDetail(this).execute();
            }
        }

        setUpToolbar();
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
}

package com.shadow.manga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.shadow.manga.R;
import com.shadow.manga.extras.Constants;
import com.shadow.manga.json.Endpoints;
import com.shadow.manga.models.Manga;
import com.shadow.manga.network.VolleySingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shane on 5/14/2015.
 */
public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.ViewHolderManga> {

    private ArrayList<Manga> mListManga = new ArrayList<>();
    private LayoutInflater mInflater;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private int mPreviousPosition = 0;

    public MangaAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getsInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
    }

    public void setManga(ArrayList<Manga> listManga) {
        this.mListManga = listManga;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderManga onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_manga, parent, false);
        ViewHolderManga viewHolder = new ViewHolderManga(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderManga holder, int position) {
        Manga currentManga = mListManga.get(position);

        holder.title.setText(currentManga.getTitle());

        Date latestUpdate = currentManga.getLatestUpdate();

        if (latestUpdate != null) {
            String formattedDate = mFormatter.format(latestUpdate);
            holder.latestUpdate.setText(formattedDate);
        } else {
            holder.latestUpdate.setText(Constants.NA);
        }

        int mangaScore = currentManga.getScore();

        if (mangaScore == -1) {
            holder.score.setRating(0.0F);
            holder.score.setAlpha(0.5F);
        } else {
            holder.score.setRating(mangaScore / 20.0F);
            holder.score.setAlpha(1.0F);
        }

        mPreviousPosition = position;

        String urlThumbnail = currentManga.getUrlThumbnail();
        loadImages(urlThumbnail, holder);
    }

    private void loadImages(String urlThumbnail, final ViewHolderManga holder) {
        if (!urlThumbnail.equals(Constants.NA)){
            mImageLoader.get(Endpoints.getRequestMangaThumbnail(urlThumbnail), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.thumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListManga.size();
    }

    public static class ViewHolderManga extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title;
        TextView latestUpdate;
        RatingBar score;

        public ViewHolderManga(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.mangaThumbnail);
            title = (TextView) itemView.findViewById(R.id.mangaTitle);
            latestUpdate = (TextView) itemView.findViewById(R.id.mangaLastUpdate);
            score = (RatingBar) itemView.findViewById(R.id.mangaScore);
        }
    }
}

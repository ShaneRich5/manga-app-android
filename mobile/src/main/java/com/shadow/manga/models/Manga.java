package com.shadow.manga.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.shadow.manga.logger.Logger;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shane on 5/14/2015.
 */
public class Manga implements Parcelable{

    private String id;
    private int score;
    private int status;
    private String title;
    private int followers;
    private Date latestUpdate;
    private String urlThumbnail;
    private ArrayList<String> categories;

    public static final Parcelable.Creator<Manga> CREATOR = new Parcelable.Creator<Manga>() {

        @Override
        public Manga createFromParcel(Parcel in) {
            Logger.m("create from parcel :Manga");
            return new Manga(in);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };
    private String alias;

    public Manga() {
    }

    public Manga(String id, String title, Date latestUpdate, int score, String urlThumbnail, int followers) {
        this.id = id;
        this.title = title;
        this.latestUpdate = latestUpdate;
        this.score = score;
        this.urlThumbnail = urlThumbnail;
        this.followers = followers;
    }

    public Manga(Parcel input) {
        id = input.readString();
        title = input.readString();
        long dateMillis = input.readLong();
        latestUpdate = (dateMillis == -1 ? null : new Date(dateMillis));
        score = input.readInt();
        urlThumbnail = input.readString();
        followers = input.readInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(Date latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus () {
        return status;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public int describeContents() {
        Logger.m("describe Contents Manga");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeLong(latestUpdate == null ? -1 : latestUpdate.getTime());
        dest.writeInt(score);
        dest.writeString(urlThumbnail);
    }
}

package com.example.Goodreads_at_Goodreads.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinitnayak on 4/22/15.
 */
public class Book {

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("description")
    private String description;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCoverUrl() { return coverUrl; }

    public String getDescription() { return description; }
}

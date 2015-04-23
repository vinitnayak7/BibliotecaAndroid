package com.example.Goodreads_at_Goodreads.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinitnayak on 4/22/15.
 */
public class BookDetails {

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

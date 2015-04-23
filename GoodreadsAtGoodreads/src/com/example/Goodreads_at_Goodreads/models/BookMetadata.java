package com.example.Goodreads_at_Goodreads.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookMetadata {

    @SerializedName("list")
    private List<BookDetails> metatdataList;

    public BookDetails getBook() {
        return metatdataList.size() > 0 ?
                metatdataList.get(0) : null;
    }

}

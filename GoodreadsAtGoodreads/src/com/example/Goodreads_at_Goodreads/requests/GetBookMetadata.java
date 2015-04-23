package com.example.Goodreads_at_Goodreads.requests;

import com.example.Goodreads_at_Goodreads.models.BookMetadata;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GetBookMetadata {

    @GET("/isbn/{isbn}?method=getMetadata&format=json&fl=*")
    public void getMetaData(@Path("isbn") String isbn, Callback<BookMetadata> callback);
}

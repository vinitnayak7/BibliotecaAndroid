package com.example.Goodreads_at_Goodreads.requests;

import com.example.Goodreads_at_Goodreads.models.Book;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

public interface GetBookMetadata {

    @GET("/book/metadata/by_isbn/{isbn}")
    public void getMetaData(@Path("isbn") String isbn, Callback<Book> callback);

    @GET("/book")
    public void getBookList(Callback<List<Book>> callback);
}

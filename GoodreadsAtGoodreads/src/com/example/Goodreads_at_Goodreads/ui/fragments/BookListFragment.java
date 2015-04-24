package com.example.Goodreads_at_Goodreads.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.Goodreads_at_Goodreads.R;
import com.example.Goodreads_at_Goodreads.models.Book;
import com.example.Goodreads_at_Goodreads.requests.GetBookMetadata;
import com.example.Goodreads_at_Goodreads.ui.adapters.BookAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

import static com.example.Goodreads_at_Goodreads.utils.Constants.BASE_URL;

public class BookListFragment extends Fragment {

    public static final String UPLOAD_BOOKS_KEY = "upload_books";

    private ListView bookList;

    public static BookListFragment newInstance(boolean booksToUpload) {
        BookListFragment myFragment = new BookListFragment();

        Bundle args = new Bundle();
        args.putBoolean(UPLOAD_BOOKS_KEY, booksToUpload);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_list_fragment, container, false);
        bookList = (ListView) v.findViewById(R.id.book_list);
        getBookList();
        return v;
    }

    private void getBookList() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        GetBookMetadata request = restAdapter.create(GetBookMetadata.class);
        request.getBookList(new Callback<List<Book>>() {
            @Override
            public void success(List<Book> books, Response response) {
                Log.d("metatdata response", books.get(0).getAuthor() + " " + books.get(0).getTitle());
                BookAdapter adapter = new BookAdapter(getActivity(), R.layout.single_book_fragment, books);
                bookList.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("metatdata response", "FAILURE: " + retrofitError.getMessage());
            }
        });
    }
}

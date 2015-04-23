package com.example.Goodreads_at_Goodreads.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.Goodreads_at_Goodreads.R;
import com.example.Goodreads_at_Goodreads.models.Book;
import com.example.Goodreads_at_Goodreads.requests.GetBookMetadata;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.Goodreads_at_Goodreads.utils.Constants.BASE_URL;

public class SingleBookFragment extends Fragment {

    public static final String KEY_SINGLE_BOOK_ISBN = "singleBookIsbn";

    private String bookIsbn;

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookDescription;


    public static SingleBookFragment newInstance(String isbn) {
        SingleBookFragment myFragment = new SingleBookFragment();

        Bundle args = new Bundle();
        args.putString(KEY_SINGLE_BOOK_ISBN, isbn);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arguments = getArguments();
        if (arguments != null &&
                arguments.containsKey(KEY_SINGLE_BOOK_ISBN)) {
            bookIsbn = arguments.getString(KEY_SINGLE_BOOK_ISBN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.single_book_fragment, container, false);
        bookTitle = (TextView) v.findViewById(R.id.book_title);
        bookAuthor = (TextView) v.findViewById(R.id.book_author);
        bookDescription = (TextView) v.findViewById(R.id.book_description);

        populateSingleBook();
        return v;
    }

    private void populateSingleBook() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)  //call your base url
                .build();

        GetBookMetadata request = restAdapter.create(GetBookMetadata.class);
        request.getMetaData(bookIsbn, new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                Log.d("metatdata response", book.getAuthor() + " " + book.getTitle());
                displaySingleBook(book);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("metatdata response", "FAILURE: " + retrofitError.getMessage());
            }
        });
    }


    private void displaySingleBook(Book book) {
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookDescription.setText(book.getDescription());
    }
}

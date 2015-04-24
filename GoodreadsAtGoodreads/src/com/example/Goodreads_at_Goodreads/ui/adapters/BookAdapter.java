package com.example.Goodreads_at_Goodreads.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.Goodreads_at_Goodreads.R;
import com.example.Goodreads_at_Goodreads.models.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vinitnayak on 4/23/15.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    private final Context context;

    public BookAdapter(Context context, int viewLayout,
                       List<Book> bookList) {
        super(context, viewLayout, bookList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_book_fragment, parent, false);
        Book b = getItem(position);
        ((TextView)convertView.findViewById(R.id.book_title)).setText(b.getTitle());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.book_image);
        Picasso.with(getContext()).load(b.getCoverUrl()).into(imageView);
        return convertView;
//        bookTitle.setText(book.getTitle());
//        bookAuthor.setText(book.getAuthor());
//        bookDescription.setText(book.getDescription());
//        Picasso.with(getActivity()).load(book.getCoverUrl()).into(bookImage);
//        return super.getView(position, convertView, parent);
    }
}

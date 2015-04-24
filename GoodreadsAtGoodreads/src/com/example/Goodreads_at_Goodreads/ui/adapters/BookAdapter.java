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

public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Context context, int viewLayout,
                       List<Book> bookList) {
        super(context, viewLayout, bookList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookListViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_book_fragment, parent, false);
            viewHolder = new BookListViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BookListViewHolder) convertView.getTag();
        }
        Book b = getItem(position);
        viewHolder.titleView.setText(b.getTitle());
        viewHolder.authorView.setText(b.getAuthor());
        Picasso.with(getContext()).load(b.getCoverUrl()).into(viewHolder.imageView);
        return convertView;
    }

    private class BookListViewHolder {

        private final TextView authorView;
        private final ImageView imageView;
        private final TextView titleView;

        private BookListViewHolder(View view) {
            this.imageView = (ImageView) view.findViewById(R.id.book_image);
            this.titleView = (TextView) view.findViewById(R.id.book_title);
            this.authorView = (TextView) view.findViewById(R.id.book_author);
        }
    }
}

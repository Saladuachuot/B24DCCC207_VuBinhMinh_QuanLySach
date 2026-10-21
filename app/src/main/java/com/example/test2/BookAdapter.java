package com.example.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvAuthor);
        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);
        if (book != null) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText("Tác giả: " + book.getAuthor());
            tvCategory.setText(book.getCategory());
            tvStatus.setText(book.getStatus());
            ratingBar.setRating(book.getRating());
        }
        return convertView;
    }
}
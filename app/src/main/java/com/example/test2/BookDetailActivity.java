package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tvDetailTitle, tvDetailSub, tvDetailStatus, tvDetailNote;
    private RatingBar detailRatingBar;
    private Button btnMarkAsRead, btnBack, btnDeleteBook;

    private Book currentBook;
    private int bookIndex;
    private boolean isUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailSub = findViewById(R.id.tvDetailSub);
        tvDetailStatus = findViewById(R.id.tvDetailStatus);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        detailRatingBar = findViewById(R.id.detailRatingBar);
        btnMarkAsRead = findViewById(R.id.btnMarkAsRead);
        btnBack = findViewById(R.id.btnBack);
        btnDeleteBook = findViewById(R.id.btnDeleteBook);

        currentBook = (Book) getIntent().getSerializableExtra("BOOK_DATA");
        bookIndex = getIntent().getIntExtra("BOOK_INDEX", -1);

        if (currentBook != null) {
            displayBookDetails();
        }

        detailRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                currentBook.setRating(rating);
                isUpdated = true;
            }
        });

        btnMarkAsRead.setOnClickListener(v -> {
            currentBook.setStatus("Đã đọc");
            isUpdated = true;
            displayBookDetails();
        });

        btnBack.setOnClickListener(v -> finishWithResult("UPDATE"));

        btnDeleteBook.setOnClickListener(v -> finishWithResult("DELETE"));
    }

    private void displayBookDetails() {
        tvDetailTitle.setText(currentBook.getTitle());
        tvDetailSub.setText(currentBook.getAuthor() + " • " + currentBook.getCategory());
        tvDetailStatus.setText(currentBook.getStatus());
        tvDetailNote.setText(currentBook.getNote().isEmpty() ? "(Không có ghi chú)" : currentBook.getNote());
        detailRatingBar.setRating(currentBook.getRating());

        if ("Đã đọc".equals(currentBook.getStatus())) {
            btnMarkAsRead.setEnabled(false);
            btnMarkAsRead.setAlpha(0.5f);
        } else {
            btnMarkAsRead.setEnabled(true);
            btnMarkAsRead.setAlpha(1.0f);
        }
    }

    private void finishWithResult(String action) {
        Intent intent = new Intent();
        intent.putExtra("ACTION", action);
        intent.putExtra("BOOK_INDEX", bookIndex);
        if ("UPDATE".equals(action) && isUpdated) {
            intent.putExtra("UPDATED_BOOK", currentBook);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        finishWithResult("UPDATE");
        super.onBackPressed();
    }
}
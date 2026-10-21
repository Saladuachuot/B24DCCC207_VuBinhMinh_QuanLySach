package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;

public class AddBookActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etNote;
    private Spinner spCategory;
    private RadioGroup rgStatus;
    private RatingBar ratingBar;
    private TextView tvRatingText;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        rgStatus = findViewById(R.id.rgStatus);
        ratingBar = findViewById(R.id.ratingBar);
        tvRatingText = findViewById(R.id.tvRatingText);
        btnSave = findViewById(R.id.btnSave);

        String[] categories = {"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Khác"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) ->
                tvRatingText.setText((int) rating + " / 5 sao")
        );

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Tên sách không được để trống");
                return;
            }
            if (author.isEmpty()) {
                etAuthor.setError("Tác giả không được để trống");
                return;
            }

            String category = spCategory.getSelectedItem().toString();

            int selectedStatusId = rgStatus.getCheckedRadioButtonId();
            RadioButton selectedRb = findViewById(selectedStatusId);
            String status = selectedRb != null ? selectedRb.getText().toString() : "Chưa đọc";

            float rating = ratingBar.getRating();
            String note = etNote.getText().toString().trim();

            Book newBook = new Book(UUID.randomUUID().toString(), title, author, category, status, rating, note);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_BOOK", newBook);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
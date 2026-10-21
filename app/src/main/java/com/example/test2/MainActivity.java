package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_BOOK = 1;
    public static final int REQUEST_DETAIL_BOOK = 2;

    private TextView tvTotalBooks, tvReadBooks, tvUnreadBooks;
    private ListView lvBooks;
    private Button btnAddBook;

    private ArrayList<Book> bookList;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalBooks = findViewById(R.id.tvTotalBooks);
        tvReadBooks = findViewById(R.id.tvReadBooks);
        tvUnreadBooks = findViewById(R.id.tvUnreadBooks);
        lvBooks = findViewById(R.id.lvBooks);
        btnAddBook = findViewById(R.id.btnAddBook);

        bookList = new ArrayList<>();
        initSampleData();

        adapter = new BookAdapter(this, bookList);
        lvBooks.setAdapter(adapter);

        updateStatistics();

        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivityForResult(intent, REQUEST_ADD_BOOK);
        });

        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            Book selectedBook = bookList.get(position);
            Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
            intent.putExtra("BOOK_DATA", selectedBook);
            intent.putExtra("BOOK_INDEX", position);
            startActivityForResult(intent, REQUEST_DETAIL_BOOK);
        });
    }

    private void initSampleData() {
        bookList.add(new Book("1", "Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", "Đang đọc", 4.0f, "Bài học nhập môn hữu ích"));
        bookList.add(new Book("2", "Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", "Đã đọc", 5.0f, "Sách rất hay"));
        bookList.add(new Book("3", "Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", "Chưa đọc", 0.0f, "Sách thiếu nhi"));
    }

    private void updateStatistics() {
        int total = bookList.size();
        int read = 0;
        int unread = 0;

        for (Book b : bookList) {
            if ("Đã đọc".equals(b.getStatus())) {
                read++;
            } else {
                unread++;
            }
        }

        tvTotalBooks.setText(String.valueOf(total));
        tvReadBooks.setText(String.valueOf(read));
        tvUnreadBooks.setText(String.valueOf(unread));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_ADD_BOOK) {
                Book newBook = (Book) data.getSerializableExtra("NEW_BOOK");
                if (newBook != null) {
                    bookList.add(newBook);
                    adapter.notifyDataSetChanged();
                    updateStatistics();
                }
            } else if (requestCode == REQUEST_DETAIL_BOOK) {
                String action = data.getStringExtra("ACTION");
                int index = data.getIntExtra("BOOK_INDEX", -1);

                if (index != -1) {
                    if ("UPDATE".equals(action)) {
                        Book updatedBook = (Book) data.getSerializableExtra("UPDATED_BOOK");
                        if (updatedBook != null) {
                            bookList.set(index, updatedBook);
                        }
                    } else if ("DELETE".equals(action)) {
                        bookList.remove(index);
                    }
                    adapter.notifyDataSetChanged();
                    updateStatistics();
                }
            }
        }
    }
}
package com.example.kalendarr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalendarr.data.DatabaseHandler;
import com.example.kalendarr.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewEventActivity extends AppCompatActivity {

    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private TextView dateValue;
    private EditText titleInput;
    private EditText descriptionInput;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        final DatabaseHandler db = new DatabaseHandler(AddNewEventActivity.this);

        dateValue = findViewById(R.id.date_value);
        titleInput = findViewById(R.id.title_input);
        descriptionInput = findViewById(R.id.description_input);
        addBtn = findViewById(R.id.add_btn);

        Intent intent = getIntent();
        final String date = intent.getStringExtra("dateString");

        dateValue.setText(date);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();

                if(title.matches("")) {
                    Toast.makeText(getApplicationContext(), "Empty event title", Toast.LENGTH_LONG).show();
                } else if(description.matches("")) {
                    Toast.makeText(getApplicationContext(), "Empty event description", Toast.LENGTH_LONG).show();
                } else {
                    db.addEvent(new Event(1, title, description, date));

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }
        });
    }
}

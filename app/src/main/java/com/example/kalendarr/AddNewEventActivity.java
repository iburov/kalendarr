package com.example.kalendarr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalendarr.data.DatabaseHandler;
import com.example.kalendarr.model.Event;
import com.example.kalendarr.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class AddNewEventActivity extends AppCompatActivity {

    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private final int maxLengthOfDescription = 200;
    private TextView dateValue;
    private EditText titleInput;
    private TextView charCounter;
    private EditText descriptionInput;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        final DatabaseHandler db = new DatabaseHandler(AddNewEventActivity.this);

        dateValue = findViewById(R.id.date_value);
        titleInput = findViewById(R.id.title_input);
        charCounter = findViewById(R.id.char_counter);
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
                    db.addEvent(new Event(title, description, date));

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }
        });


        descriptionInput.addTextChangedListener(descriptionEditTextTW);
    }

    private final TextWatcher descriptionEditTextTW = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            charCounter.setText(String.valueOf(s.length()) + "/" + maxLengthOfDescription);
        }

        public void afterTextChanged(Editable s) {
        }
    };
}

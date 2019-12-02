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

public class EditEventActivity extends AppCompatActivity {

    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private final int maxLengthOfDescription = 200;
    private TextView dateValue;
    private EditText titleInput;
    private TextView charCounter;
    private EditText descriptionInput;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        final DatabaseHandler db = new DatabaseHandler(EditEventActivity.this);

        dateValue = findViewById(R.id.date_value);
        titleInput = findViewById(R.id.title_input);
        charCounter = findViewById(R.id.char_counter);
        descriptionInput = findViewById(R.id.description_input);
        editBtn = findViewById(R.id.edit_btn);

        Intent intent = getIntent();
        final int eventId = intent.getIntExtra("eventIdToEdit", 0);

        Event e = db.getEvent(eventId);

        final String date = e.getDate();
        dateValue.setText(date);

        titleInput.setText(e.getTitle());
        descriptionInput.setText(e.getDescription());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();

                if(title.matches("")) {
                    Toast.makeText(getApplicationContext(), "Empty event title", Toast.LENGTH_LONG).show();
                } else if(description.matches("")) {
                    Toast.makeText(getApplicationContext(), "Empty event description", Toast.LENGTH_LONG).show();
                } else {
                    db.updateEvent(new Event(eventId, title, description, date));

                    Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                    intent.putExtra("eventId", Integer.toString(eventId));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        final int eventId = intent.getIntExtra("eventIdToEdit", 0);
        Intent backIntent = new Intent(getApplicationContext(), ListItemActivity.class);
        backIntent.putExtra("eventId", Integer.toString(eventId));
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(backIntent);
    }
}

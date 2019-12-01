package com.example.kalendarr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kalendarr.data.DatabaseHandler;
import com.example.kalendarr.model.Event;
import com.example.kalendarr.util.Util;

public class ListItemActivity extends AppCompatActivity {

    TextView eventDateTextView;
    TextView eventNameTextView;
    TextView eventDescriptionTextView;

    public static FragmentManager fragmentManager;

    final Intent intent = getIntent();
    final String eventId = intent.getStringExtra("eventId");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        fragmentManager = getSupportFragmentManager();

        eventDateTextView = findViewById(R.id.eventDateTextView);
        eventNameTextView = findViewById(R.id.eventNameTextView);
        eventDescriptionTextView = findViewById(R.id.eventDescriptionTextView);

        final DatabaseHandler db = new DatabaseHandler(this);
        Event e = db.getEvent(Integer.parseInt(eventId));

        String date = e.getDate();
        String fullDateName = Util.getSplitMonthString(date)[0] + " " + Util.getMonth(date) + ", " + Util.getSplitMonthString(date)[2];
        eventDateTextView.setText(fullDateName);
        eventNameTextView.setText(e.getTitle());
        eventDescriptionTextView.setText(e.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_menu_btn:
                //go to edit activity
            case R.id.delete_menu_btn:
                Bundle bundle = new Bundle();
                bundle.putInt("eventIdToDelete", Integer.parseInt(eventId));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DeleteEventFragment deleteEventFragment = new DeleteEventFragment();
                deleteEventFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.delete_fragment, deleteEventFragment, null);
                fragmentTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }
}

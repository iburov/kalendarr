package com.example.kalendarr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        Intent intent = getIntent();
        String eventId = intent.getStringExtra("eventId");

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
        Intent intent = getIntent();
        String eventId = intent.getStringExtra("eventId");

        switch (item.getItemId()) {
            case R.id.edit_menu_btn:
                //go to edit activity
                Intent editIntent = new Intent(getApplicationContext(), EditEventActivity.class);
                editIntent.putExtra("eventIdToEdit", Integer.parseInt(eventId));
                editIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(editIntent);
            case R.id.delete_menu_btn:
                Bundle bundle = new Bundle();
                bundle.putInt("eventIdToDelete", Integer.parseInt(eventId));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DeleteEventFragment deleteEventFragment = new DeleteEventFragment();
                fragmentTransaction.add(R.id.delete_fragment, deleteEventFragment,"delete_frag");
                deleteEventFragment.setArguments(bundle);
                fragmentTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }
}

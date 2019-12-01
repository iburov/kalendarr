package com.example.kalendarr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.kalendarr.data.DatabaseHandler;
import com.example.kalendarr.model.Event;
import com.example.kalendarr.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //array for testing
    private ArrayList<Event> eventsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        final List<Event> events = db.getEvents();

        CalendarView cv = findViewById(R.id.calendarView);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                eventsArrayList = new ArrayList<Event>();
                String date = d + "/" + (m+1) + "/" + y;
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();

                for(Event event: events) {
                    if(event.getDate().equals(date)) {
                        eventsArrayList.add(event);
                    }
                }

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(eventsArrayList, getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });

    }
}

package com.example.kalendarr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.kalendarr.data.DatabaseHandler;
import com.example.kalendarr.model.Event;
import com.example.kalendarr.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.kalendarr.util.Util.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {

    //array for testing
    private ArrayList<Event> eventsArrayList;
    String chosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseHandler db = new DatabaseHandler(MainActivity.this);
//        db.deleteAll();
//        getApplicationContext().deleteDatabase(DATABASE_NAME);
//        db.addEvent(new Event(null, "TILE", "DESCRIPTION!!!!", "02/12/2019"));

        final List<Event> events = db.getEvents();

        final CalendarView cv = findViewById(R.id.calendarView);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button addBtn = findViewById(R.id.add_btn);

        chosenDate = getDateString(cv.getDate(), "dd/MM/yyyy");

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                eventsArrayList = new ArrayList<Event>();

                String dString;
                if(d<10) {
                    dString = "0" + d;
                } else {
                    dString = "" + d;
                }

                m++;
                String mString;
                if(m<10) {
                    mString = "0" + m;
                } else {
                    mString = "" + m;
                }

                String date = dString + "/" + mString + "/" + y;
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
                chosenDate = date;
                System.out.println(chosenDate);

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

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewEventActivity.class);
                intent.putExtra("dateString", getChosenDate());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    public static String getDateString(long milliSeconds, String dateFormat){
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public String getChosenDate() {
        return chosenDate;
    }
}

package com.example.kalendarr;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kalendarr.data.DatabaseHandler;

import static com.example.kalendarr.ListItemActivity.fragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteEventFragment extends Fragment{

    public DeleteEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_event, container, false);
        Button deleteBtn = view.findViewById(R.id.delete_btn);
        Button cancelBtn = view.findViewById(R.id.cancel_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int eventId = getArguments().getInt("eventIdToDelete");
                DatabaseHandler db = new DatabaseHandler(getContext());
                db.deleteEvent(eventId);

                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
                Toast.makeText(getContext(), "deleted", Toast.LENGTH_LONG).show();

                System.out.println("delete button clicked!!!");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment frm = fragmentManager.findFragmentByTag("delete_frag");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(frm);
                fragmentTransaction.commit();

                System.out.println("cancel button clicked!!!");
            }
        });

        return view;
    }
}

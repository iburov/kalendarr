package com.example.kalendarr;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kalendarr.data.DatabaseHandler;

import static com.example.kalendarr.ListItemActivity.fragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteEventFragment extends Fragment {

    public DeleteEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button deleteBtn = getActivity().findViewById(R.id.delete_btn);
        Button cancelBtn = getActivity().findViewById(R.id.cancel_btn);

        final DatabaseHandler db = new DatabaseHandler(getContext());
        final int eventId = getArguments().getInt("eventIdToDelete");

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteEvent(eventId);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DeleteEventFragment deleteEventFragment = new DeleteEventFragment();
                fragmentTransaction.hide(deleteEventFragment);
                fragmentTransaction.commit();
            }
        });

        return inflater.inflate(R.layout.fragment_delete_event, container, false);
    }

}

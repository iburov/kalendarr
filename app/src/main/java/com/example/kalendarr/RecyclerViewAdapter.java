package com.example.kalendarr;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalendarr.model.Event;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Event> events;
    private Context context;


    public RecyclerViewAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.listItemName.setText(events.get(position).getTitle());

        holder.listItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListItemActivity(events.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout listItemLayout;
        TextView listItemName;

        public ViewHolder(View itemView) {
            super(itemView);

            listItemLayout = itemView.findViewById(R.id.list_item_layout);
            listItemName = itemView.findViewById(R.id.list_item_name);
        }
    }

    public void openListItemActivity(int id) {
        Intent intent = new Intent(context, ListItemActivity.class);
        String eventId = id + "";
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("eventId", eventId);
        context.startActivity(intent);
    }
}

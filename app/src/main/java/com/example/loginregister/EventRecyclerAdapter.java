package com.example.loginregister;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRecyclerAdapter  extends RecyclerView.Adapter<EventRecyclerAdapter.myViewHolder> {
Context context;
ArrayList<Event>arrayList;

    public EventRecyclerAdapter(Context context, ArrayList<Event> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_event_rowlayout,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Event event= arrayList.get(position);
        holder.Event.setText(event.getEVENT());
        holder.DateTxt.setText(event.getDATE());
        holder.Time.setText(event.getTIME());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class myViewHolder extends RecyclerView.ViewHolder{
    TextView DateTxt,Event,Time;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            DateTxt=itemView.findViewById(R.id.eventdate);
           Event= itemView.findViewById(R.id.eventname);
           Time=itemView.findViewById(R.id.eventtime);

        }
    }
}

package com.example.loginregister;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView   extends LinearLayout {
    ImageButton Nextbutton, Previousbutton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CAL_DAYS=42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);


    MygrindAdaptor mygrindAdaptor;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Event> eventList = new ArrayList<>();
    DBhelper dBhelper;
    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        Layout();
        Setupcalendar();
        Previousbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               calendar.add(Calendar.MONTH,-1);
               Setupcalendar();
            }
        });
        Nextbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                Setupcalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_event_layout,null);
                final EditText EventName=addView.findViewById(R.id.event_name);
                final TextView EventTime = addView.findViewById(R.id.eventtime);
                ImageButton SetTime= addView.findViewById(R.id.seteventtime);
                Button AddEvent= addView.findViewById(R.id.addevent);
                SetTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformate = new SimpleDateFormat("K:mm a ", Locale.ENGLISH);
                                String event_time = hformate.format(c.getTime());
                                EventTime.setText(event_time);

                            }

                        }, hours, minutes, false);
                        timePickerDialog.show();
                    }
                });
                final String date = eventDateFormat.format(dates.get(position));
                final String month= monthFormat.format((dates.get(position)));
                final String year =yearFormat.format((dates.get(position)));


                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveEvent(EventName.getText().toString(),EventTime.getText().toString(),date,month,year);
                        Setupcalendar();
                        alertDialog.dismiss();
                    }
                });
                builder.setView(addView);
                alertDialog=builder.create();
                alertDialog.show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               String date=eventDateFormat.format(dates.get(position));

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView =LayoutInflater.from(parent.getContext()).inflate(R.layout.show_event_layout,null);
                RecyclerView recyclerView=showView.findViewById(R.id.EventsRV);
                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                EventRecyclerAdapter eventRecyclerAdapter= new EventRecyclerAdapter(showView.getContext(),CollectEventByDate(date));
                recyclerView.setAdapter(eventRecyclerAdapter);
                eventRecyclerAdapter.notifyDataSetChanged();

                builder.setView(showView);
                alertDialog=builder.create();
                alertDialog.show();

                return true;
            }
        });


    }
private  ArrayList<Event> CollectEventByDate(String date){
        ArrayList<Event> arrayList= new ArrayList<>();
        dBhelper= new DBhelper(context);
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        Cursor cursor= dBhelper.ReadEvents(date,database);
    while(cursor.moveToNext()) {
        String event = cursor.getString(cursor.getColumnIndex(DBstructure.EVENT));
        String time = cursor.getString(cursor.getColumnIndex(DBstructure.TIME));
        String Date = cursor.getString(cursor.getColumnIndex(DBstructure.DATE));
        String month = cursor.getString(cursor.getColumnIndex(DBstructure.MONTH));
        String Year = cursor.getString(cursor.getColumnIndex(DBstructure.YEAR));
        Event events = new Event(event, time, Date, month, Year);
        arrayList.add(events);
    }
    cursor.close();
    dBhelper.close();
    return arrayList;

}

    public CustomCalendarView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private  void SaveEvent(String event,String time,String date,String month,String year){
        dBhelper= new DBhelper (context);
        SQLiteDatabase database =dBhelper.getWritableDatabase();
        dBhelper.SaveEvent(event,time,date,month,year,database);
        dBhelper.close();
        Toast.makeText(context,"Event have been saved",Toast.LENGTH_SHORT).show();

    }

    private void Layout(){
    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view= inflater.inflate(R.layout.calendar_layout,this);
    Nextbutton=view.findViewById(R.id.nextBTN);
    Previousbutton=view.findViewById(R.id.previousBTN);
    CurrentDate=view.findViewById(R.id.curentdate);
    gridView=view.findViewById(R.id.gridview);
    }
private  void Setupcalendar(){
    String currwntDate= dateFormat.format(calendar.getTime());
    CurrentDate.setText(currwntDate);
    dates.clear();
    Calendar monthCalendar=(Calendar) calendar.clone();
    monthCalendar.set(Calendar.DAY_OF_MONTH,1);
    int FirstdayofMonth=monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
    monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstdayofMonth);

// CollectEventPerMonth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

    while (dates.size()<MAX_CAL_DAYS){
        dates.add(monthCalendar.getTime());
        monthCalendar.add(Calendar.DAY_OF_MONTH,1);

    }
    mygrindAdaptor= new MygrindAdaptor(context,dates,calendar,eventList);
    gridView.setAdapter(mygrindAdaptor);
   }

    private  void CollectEventPerMonth(String Month,String year){
       eventList.clear();
        dBhelper = new DBhelper(context);
        SQLiteDatabase database=dBhelper.getReadableDatabase();
       Cursor cursor = dBhelper.ReadEventsPerMonth(Month,year,database);
        while(cursor.moveToNext()){
           String event =cursor.getString(cursor.getColumnIndex(DBstructure.EVENT));
            String time =cursor.getString(cursor.getColumnIndex(DBstructure.TIME));
            String date =cursor.getString(cursor.getColumnIndex(DBstructure.DATE));
            String month =cursor.getString(cursor.getColumnIndex(DBstructure.MONTH));
            String Year =cursor.getString(cursor.getColumnIndex(DBstructure.YEAR));
            Event events =new Event(event,time,date,month,Year);
            eventList.add(events);
        }
        cursor.close();
        dBhelper.close();

    }

}

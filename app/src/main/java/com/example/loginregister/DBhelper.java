package com.example.loginregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final String CREATE_EVENTS_TABLE = "create table " + DBstructure.EVENTS_TABLE+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DBstructure.EVENT+" TEXT, "+DBstructure.TIME+" TEXT, "+DBstructure.DATE+" TEXT, "+DBstructure.MONTH+" TEXT, "
            +DBstructure.YEAR+" TEXT)";

    private static final String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS "+DBstructure.EVENTS_TABLE;


    public DBhelper(Context context) {
        super(context, DBstructure.DB_NAME, null, DBstructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }


    public void SaveEvent(String event, String time, String date, String month, String year, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBstructure.EVENT,event);
        contentValues.put(DBstructure.TIME,time);
        contentValues.put(DBstructure.DATE,date);
        contentValues.put(DBstructure.MONTH,month);
        contentValues.put(DBstructure.YEAR,year);
        database.insert(DBstructure.EVENTS_TABLE, null, contentValues);
    }

    public Cursor ReadEvents(String date, SQLiteDatabase database){
        String [] Projections = {DBstructure.EVENT, DBstructure.TIME, DBstructure.DATE, DBstructure.MONTH, DBstructure.YEAR};
        String Selection = DBstructure.DATE+"=?";
        String [] SelectionArgs ={date};

        return database.query(DBstructure.EVENTS_TABLE, Projections, Selection, SelectionArgs, null, null, null);
    }

    public Cursor ReadEventsPerMonth(String month, String year, SQLiteDatabase database){
        String [] Projections = {DBstructure.EVENT, DBstructure.TIME, DBstructure.DATE, DBstructure.MONTH, DBstructure.YEAR};
        String Selection = DBstructure.MONTH +"=? and"+DBstructure.YEAR+"=?";
        String [] SelectionArgs ={month,year};
      return database.query(DBstructure.EVENTS_TABLE,Projections,Selection,SelectionArgs,null,null,null);
    }

}

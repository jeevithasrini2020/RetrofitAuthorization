package com.example.proj3.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Employee.db";
    public static final String TABLE_NAME = "employee_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";
    public static final String COL_5 = "IDEMP";
    public static final String COL_6 = "YOUTUBE";
    public static final String COL_7 = "INSTA";
    public static final String COL_8 = "IMAGE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER,IDEMP TEXT,YOUTUBE TEXT,INSTA TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String surname, String marks,String idemp,String youtube,String insta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        contentValues.put(COL_5,idemp);
        contentValues.put(COL_6,youtube);
        contentValues.put(COL_7,insta);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
public Cursor getParticularData(String idemp){
    SQLiteDatabase db = this.getWritableDatabase();
   // Cursor cursor = db.rawQuery("select * from " + TABLE_NAME+ ""+ "WHERE"+"" +COL_5+ "=?" +empid,null);
   // Cursor cursor = db.rawQuery( TABLE_NAME,"IDEMP= ?", new String[]{idemp});
    String selectQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE "
            + COL_5 + " = " + idemp;
    Cursor res = db.rawQuery(selectQuery, null);
    res.getString(res.getColumnIndex(COL_6));
    //if (cursor.moveToFirst()) {

      //  String[] columnNames = cursor.getColumnNames();

//        //yData = new String[columnNames.length];
//
//        for (int i = 0; i < columnNames.length; i++) {
//
//            // Assume every column is int
//
//            yData[i] = cursor.getString(cursor.getColumnIndex(columnNames[i]));
//        }
//
//    }
//    cursor.close();
//    db.close();
        return res;
}
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

//    public boolean updateData(String id, String name, String surname, String marks,String idemp,String youtube,String insta) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1, id);
//        contentValues.put(COL_2, name);
//        contentValues.put(COL_3, surname);
//        contentValues.put(COL_4, marks);
//        contentValues.put(COL_5,idemp);
//        contentValues.put(COL_6,youtube);
//        contentValues.put(COL_7,insta);
//        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
//        return true;
//    }

    public boolean updateData( String idemp,String youtube,String insta) {//String id,
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_1, id);

        contentValues.put(COL_5,idemp);
        contentValues.put(COL_6,youtube);
        contentValues.put(COL_7,insta);
       // db.update(TABLE_NAME, contentValues, "IDEMP= ?", new String[]{idemp,youtube,insta});
      //  NAME + " = ? AND " + LASTNAME + " = ?",
        db.update(TABLE_NAME, contentValues, "IDTEMP= ?" +COL_6 +"=?"+COL_7+"=?", new String[]{idemp,youtube,insta});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}

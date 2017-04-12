package com.example.expenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shara on 10/12/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DbHelper";
    private static DbHelper mDbHelper;
    // Database Info
    public static final String DATABASE_NAME = "Expense.db";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    public static final String TABLE_USERDETAIL = "expenses";


    //userdetail Table Columns
    private static final String _ID = "SNo";
    public static final String CLIENTNAME = "ClientName";
    private static final String INVOICENUMBER = "InvoiceNumber";
    private static final String DATE = "Date";
    private static final String AMOUNT = "Amount";
    private static final String DESCRIPTION = "InvoiceDescription";
    private static final String BUSINESSEXPENSE="BusinessExpense";
    public String databasePath = "";
String clientName;
    private String cnn;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERDETAIL_TABLE = "CREATE TABLE " + TABLE_USERDETAIL +
                "(" +
                _ID + " INTEGER PRIMARY KEY ," +
                CLIENTNAME + " TEXT," +
                INVOICENUMBER + " TEXT," +
                DATE + " TEXT," +
                AMOUNT + " TEXT," +
                DESCRIPTION + " TEXT," +
                BUSINESSEXPENSE +" TEXT"+
                ")";
        db.execSQL(CREATE_USERDETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDETAIL);

            onCreate(db);
        }
    }

    public static synchronized DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new DbHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }
 /*
   Insert a  user detail into database
   */

 /*   public void insertUserDetail(String cname, String iname, String date, String amount, String description, String cv) {

        SQLiteDatabase db = getWritableDatabase();
Log.i("expense came as:",cv);
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(CLIENTNAME, cname);
            values.put(INVOICENUMBER, iname);
            values.put(DATE, date);
            values.put(AMOUNT, amount);
            values.put(DESCRIPTION, description);
           values.put(BUSINESSEXPENSE,cv);

            db.insertOrThrow(TABLE_USERDETAIL, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        } finally {

            db.endTransaction();
        }
    }


    /*
   Delete single row from UserTable
     */
    /*void deleteRow(UserData name) {
        SQLiteDatabase db = getWritableDatabase();


        try {
            db.beginTransaction();
            db.execSQL("delete from " + TABLE_USERDETAIL + " where cname ='" + name + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
            db.endTransaction();
        }


    }*/



    public ArrayList<ArrayList<String>> getAllUserData() {
        ArrayList<ArrayList<String>> UserDatas = new ArrayList<ArrayList<String>>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USERDETAIL ;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list

        //UserData userdata = null;
        try {
            if (cursor.moveToFirst()) {
                do {

                    ArrayList<String> dataList = new ArrayList<String>();
                    // dataList.add("Id:"+cursor.getString(0)+"\n");
                    dataList.add("ClientName:" + cursor.getString(1) + "\n");
                    dataList.add("InvoiceNumber:" + cursor.getString(2) + "\n");
                    dataList.add("Date:" + cursor.getString(3) + "\n");
                    dataList.add("Amount:" + cursor.getString(4) + "\n");
                    dataList.add("Description:" + cursor.getString(5) + "\n");
                    dataList.add("BusinessExpense:" + cursor.getString(6) + "\n");
                    UserDatas.add(dataList);
                } while (cursor.moveToNext());

            }



        }
        catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
          //  db.endTransaction();
            db.execSQL("VACUUM expenses");
        }
        return UserDatas;
    }

       public ArrayList<ArrayList<String>> getClientUserData(String clientName)  {
//String cn=clientName;

        ArrayList<ArrayList<String>> UserDatas = new ArrayList<ArrayList<String>>();

         //  String[] columns ={"CLIENTNAME","INVOICENUMBER","DATE","AMOUNT"};
        // 1. build the query
       String query = "SELECT  * FROM expenses where CLIENTNAME  = '"+clientName+"' ";
Log.i("ClientName is coming as",clientName);
        // 2. get reference to writable DB
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery(query,null);
          // Cursor cursor = db1.query("expenses", columns, "CLIENTNAME=?", new String[] { cn }, null, null, null);
        // 3. go over each row, build book and add it to list
       // UserData userdata = null;
        if (cursor.moveToFirst()) {
            do {

                ArrayList<String> dataList = new ArrayList<String>();
                // dataList.add("Id:"+cursor.getString(0)+"\n");
                dataList.add("ClientName:" + cursor.getString(1) + "\n");
                dataList.add("InvoiceNumber:" + cursor.getString(2) + "\n");
                dataList.add("Date:" + cursor.getString(3) + "\n");
                dataList.add("Amount:" + cursor.getString(4) + "\n");
                dataList.add("Description:" + cursor.getString(5) + "\n");
             dataList.add("BusinessExpense:" + cursor.getString(6) + "\n");
                UserDatas.add(dataList);
            } while (cursor.moveToNext());
        }


        return UserDatas;
    }

    public ArrayList<ArrayList<String>> getDateUserData(String date) {
        ArrayList<ArrayList<String>> UserDatas = new ArrayList<ArrayList<String>>();

        //  String[] columns ={"CLIENTNAME","INVOICENUMBER","DATE","AMOUNT"};
        // 1. build the query
        String query = "SELECT  * FROM expenses where DATE  = '"+date+"' ";
        Log.i("Date is coming as",date);
        // 2. get reference to writable DB
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery(query,null);
        // Cursor cursor = db1.query("expenses", columns, "CLIENTNAME=?", new String[] { cn }, null, null, null);
        // 3. go over each row, build book and add it to list
        // UserData userdata = null;
        if (cursor.moveToFirst()) {
            do {

                ArrayList<String> dataList = new ArrayList<String>();
                // dataList.add("Id:"+cursor.getString(0)+"\n");
                dataList.add("ClientName:" + cursor.getString(1) + "\n");
                dataList.add("InvoiceNumber:" + cursor.getString(2) + "\n");
                dataList.add("Date:" + cursor.getString(3) + "\n");
                dataList.add("Amount:" + cursor.getString(4) + "\n");
                dataList.add("Description:" + cursor.getString(5) + "\n");
                dataList.add("BusinessExpense:" + cursor.getString(6) + "\n");
                UserDatas.add(dataList);
            } while (cursor.moveToNext());

        }


        return UserDatas;
    }

 /*   public void DeletePosition(int position) {
        SQLiteDatabase db = getWritableDatabase();


        try {
            db.beginTransaction();
            Log.i("deleting row",String.valueOf(position));
        // db.execSQL("DELETE   FROM " + TABLE_USERDETAIL);
        db.execSQL("delete from " + TABLE_USERDETAIL + " where rowid = '" + position + "'");
          //  db.execSQL("alter table expenses Sno = 0");

            db.setTransactionSuccessful();
           // db.execSQL("VACUUM expenses");

        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
           // mDbHelper.getAllUserData();
            db.endTransaction();
            db.execSQL("VACUUM expenses");

        }


    }

    public void execSQL() {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("VACUUM expenses");
    }*/
   public void insertUserDetail(UserData userData) {

       SQLiteDatabase db = getWritableDatabase();

       db.beginTransaction();

       try {
           ContentValues values = new ContentValues();
           values.put(CLIENTNAME, userData.ClientName);
           values.put(INVOICENUMBER, userData.InvoiceId);
           values.put(DATE, userData.Date);
           values.put(AMOUNT, userData.Amount);
           values.put(DESCRIPTION, userData.Description);
            values.put(BUSINESSEXPENSE,userData.be);
           db.insertOrThrow(TABLE_USERDETAIL, null, values);
           db.setTransactionSuccessful();
       } catch (SQLException e) {
           e.printStackTrace();
           Log.d(TAG, "Error while trying to add post to database");
       } finally {

           db.endTransaction();
       }


   }

   /*
   fetch all data from UserTable
    */

    public List<UserData> getAllUser() {

        List<UserData> usersdetail = new ArrayList<>();

        String USER_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_USERDETAIL;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    UserData userData = new UserData();
                    userData.ClientName = cursor.getString(cursor.getColumnIndex(CLIENTNAME));
                    userData.InvoiceId = cursor.getString(cursor.getColumnIndex(INVOICENUMBER));
                    userData.Date = cursor.getString(cursor.getColumnIndex(DATE));
                    userData.Amount = cursor.getString(cursor.getColumnIndex(AMOUNT));
                    userData.Description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));


                    usersdetail.add(userData);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return usersdetail;

    }

    /*
   Delete single row from UserTable
     */
    void deleteRow(String name) {
        SQLiteDatabase db = getWritableDatabase();


        try {
            db.beginTransaction();
            db.execSQL("delete from " + TABLE_USERDETAIL + " where InvoiceNumber ='" + name + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete  users detail");
        } finally {
            db.endTransaction();
        }


    }
}


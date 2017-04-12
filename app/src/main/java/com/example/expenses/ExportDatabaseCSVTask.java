package com.example.expenses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Locale;

import au.com.bytecode.opencsv.CSVWriter;

import static com.example.expenses.DbHelper.DATABASE_NAME;

public class ExportDatabaseCSVTask {
DbHelper dbhelper;
    Context context;
    public ExportDatabaseCSVTask() {
        dbhelper=new DbHelper(context);
    }
    public void exportDataBaseIntoCSV(){

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        String db_path="/data/data/com.example.expenses/databases/";
        String db_name=DATABASE_NAME;
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return ;
        }
        else {
            //We use the Download directory for saving our .csv file.
            File sharath = new File(Environment.getExternalStorageDirectory(), "BusinessExpensesCSV");
            if (!sharath.exists()) {
                sharath.mkdirs();
            }

            File file;
            PrintWriter printWriter = null;
            Cursor curCSV = null;
            SQLiteDatabase db = null;
            try {
                file = new File(sharath, "Businessexpenses.csv");
                file.createNewFile();
                printWriter = new PrintWriter(new FileWriter(file));

                /**This is our database connector class that reads the data from the database.
                 * The code of this class is omitted for brevity.
                 */
                db = SQLiteDatabase.openDatabase(db_path + db_name, null, SQLiteDatabase.OPEN_READONLY);
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                /**Let's read the first table of the database.
                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
                 * containing all records of the table (all fields).
                 * The code of this class is omitted for brevity.
                 */
            Log.i("creating file with ","filename");
                curCSV = db.rawQuery("select * from expenses", null);
                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
                printWriter.println("FIRST TABLE OF THE DATABASE");
                printWriter.println("ClientName,InvoiceNumber,Date,Amount,Description");
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4),curCSV.getString(5),curCSV.getString(6)};
                    csvWrite.writeNext(arrStr);
                }

                csvWrite.close();
                curCSV.close();
            } catch (Exception sqlEx) {
                Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
            }
            curCSV.close();
            db.close();
        }

    }
}
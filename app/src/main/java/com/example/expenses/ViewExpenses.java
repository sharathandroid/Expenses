package com.example.expenses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import static com.example.expenses.R.id.cname;


public class ViewExpenses extends AppCompatActivity {
Button cg,gd,viewall;
    EditText clientname;
    EditText idate;
  Context context;
    SQLiteDatabase db=null;
    DbHelper db1;
    String date;
    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);
       db1 = DbHelper.getInstance(getApplicationContext());
        cg=(Button)findViewById(R.id.cg);
        idate=(EditText) findViewById(R.id.date);
       gd=(Button)findViewById(R.id.dg);
       viewall=(Button)findViewById(R.id.viewall) ;

      // inumber=(EditText)findViewById(R.id.inumber) ;
       final Calendar c = Calendar.getInstance();
       year = c.get(Calendar.YEAR);
       month = c.get(Calendar.MONTH);
       day = c.get(Calendar.DAY_OF_MONTH);

       // Show current date

       idate.setText(new StringBuilder()
               // Month is 0 based, just add 1
               .append(month + 1).append("-").append(day).append("-")
               .append(year).append(" "));

       // Button listener to show date picker dialog

       idate.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               // On button click show datepicker dialog
               showDialog(DATE_PICKER_ID);

           }

       });



        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent updateCustomerIntent = new Intent(ViewExpenses.this, Result.class);
              //  updateCustomerIntent.putExtra("intVariableName", 0);
                //startActivity(updateCustomerIntent);
                Intent intent=new Intent(ViewExpenses.this,UserDetailList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
       cg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               clientname=(EditText)findViewById(cname) ;

               Intent updateCustomerIntent1 = new Intent(ViewExpenses.this, ClientName.class);

               //updateCustomerIntent.putExtra("intVariableName", 1);
             updateCustomerIntent1.putExtra("ClientName",clientname.getText().toString());
               updateCustomerIntent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
               updateCustomerIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
               Log.i("ClientName is :",clientname.getText().toString());
               startActivity(updateCustomerIntent1);
              // finish();



           }
       });
       gd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               Intent updateCustomerIntent = new Intent(ViewExpenses.this, DateSearch.class);

               //updateCustomerIntent.putExtra("intVariableName", 1);
               updateCustomerIntent.putExtra("Date",date);
               updateCustomerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
               updateCustomerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
               Log.i("Date searched  is :",date);
               startActivity(updateCustomerIntent);

           }
       });
    }
    protected Dialog onCreateDialog (int id){
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day);
        }
        return null;
    }


    DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            idate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
            date=idate.getText().toString();        }
    };
}

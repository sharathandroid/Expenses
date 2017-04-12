package com.example.expenses;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Result extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter adapter;
    int intValue;
    String clientName;
    // private DatabaseHelper databaseHelper;
    private ArrayList<ArrayList<String>> dataList;
    private TextView title;
    public DbHelper myDb = new DbHelper(this);
    private String[] lv_arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView) findViewById(R.id.total);
        // Intent updateCustomerIntent = getIntent();
        //  clientName = updateCustomerIntent.getStringExtra("ClientName");
        //intValue = updateCustomerIntent.getExtras().getInt("intVariableName");
        myDb = new DbHelper(this);
        dataList = new ArrayList<>();
        reloadingDatabase(); //loading table of DB to ListView
    }

    public void reloadingDatabase() {
        dataList = myDb.getAllUserData();

        if (dataList.size() == 0) {
            Toast.makeText(this, "No record found in database!", Toast.LENGTH_LONG).show();
            title.setVisibility(View.GONE);
        }
        lv_arr = new String[dataList.size()];
        //StringBuilder builder=new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            //  lv_arr[i] = String.valueOf(dataList.get(i));
            lv_arr[i] = Arrays.toString(dataList.get(i).toArray()).replace("[", "").replace("]", "").replace(',', ' ');
            //  builder.append(lv_arr);
            // title.setText(lv_arr[i]);
        }
        //  adapter = new ListViewAdapter(this, R.layout.item_listview, dataList, myDb);
        adapter = new ArrayAdapter(this, R.layout.activity_result, R.id.total, lv_arr);
        listView.setAdapter(adapter);
        // title.setVisibility(View.VISIBLE);
        //title.setText("Total records: " + myDb.getContactsCount());

        // setting onItemLongClickListener and passing the position to the function

        // method to remove list item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(Result.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //lv_arr.(positionToRemove);
                        adapter.remove(positionToRemove);

                        adapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });

    }
}

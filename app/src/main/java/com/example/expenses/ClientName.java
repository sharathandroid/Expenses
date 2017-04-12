package com.example.expenses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientName extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter adapter;
    int intValue;

    // private DatabaseHelper databaseHelper;
    private ArrayList<ArrayList<String>> dataList;
    private TextView title;
    DbHelper myDb = new DbHelper(this);
    private String[] lv_arr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_name);
        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView)findViewById(R.id.total);
        Bundle bu;
        bu=getIntent().getExtras();

         String clientName = bu.getString("ClientName");
     //   intValue = updateCustomerIntent.getExtras().getInt("intVariableName");
        Log.i("getting the client",clientName);
        myDb = new DbHelper(this);
        dataList = new ArrayList<>();
         //loading table of DB to ListView
        if(clientName!=null) {
            dataList = myDb.getClientUserData(clientName);
        }
        else
        Toast.makeText(getBaseContext(),"please enter Client Name",Toast.LENGTH_LONG).show();

        if (dataList.size() == 0) {
            Toast.makeText(this, "No record found in database!", Toast.LENGTH_LONG).show();
            title.setVisibility(View.GONE);
        }
        lv_arr = new String[dataList.size()];

        for (int i = 0; i < dataList.size(); i++) {

            lv_arr[i]= Arrays.toString(dataList.get(i).toArray()).replace("[", "").replace("]", "").replace(',',' ');

        }

        adapter=  new ArrayAdapter(this,R.layout.activity_client_name,R.id.total,lv_arr);
        listView.setAdapter(adapter);

    }


}

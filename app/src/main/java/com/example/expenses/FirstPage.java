package com.example.expenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstPage extends AppCompatActivity {
    Button addexpenses, viewexpense, sendemail, export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  ActionBar actionbar=getActionBar();
       // getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setTitle("Expenses");
        getSupportActionBar().setLogo(R.drawable.expenses);
        getSupportActionBar().setIcon(R.mipmap.expenses);
        setContentView(R.layout.activity_first_page);
        sendemail = (Button) findViewById(R.id.sendmail);
        addexpenses = (Button) findViewById(R.id.addexpenses);
        viewexpense = (Button) findViewById(R.id.viewexpenses);
        export = (Button) findViewById(R.id.export);

        addexpenses.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent mainactivity = new Intent(FirstPage.this, MainActivity.class);
                mainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mainactivity.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(mainactivity);
                // finish();


            }
        });
        viewexpense.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent viewexpenses = new Intent(FirstPage.this, ViewExpenses.class);
                viewexpenses.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                viewexpenses.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(viewexpenses);
                //     finish();

            }
        });
        sendemail.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent sendemail = new Intent(FirstPage.this, SendingEmail.class);
                sendemail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                sendemail.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(sendemail);
                // finish();
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportDatabaseCSVTask ep = new ExportDatabaseCSVTask();
                Toast.makeText(getBaseContext(), "CSV file is Created", Toast.LENGTH_LONG).show();
                ep.exportDataBaseIntoCSV();
            }
        });
    }
public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater menuinflate= getMenuInflater();
    menuinflate.inflate(R.menu.menu,menu);
    return true;
}

}
package com.example.expenses;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

//import android.icu.util.Calendar;

//import static com.example.expenses.ExportDatabaseCSVTask.verifyStoragePermissions;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private String provider;
    EditText et_cname, et_inumber, et_amount, et_desc, dates;
    Button btn_submit, btn_photo;
    public EditText loc;
    CheckBox cb1, cb2;
    CalendarView et_date;
    DbHelper dbHelper;
    String curDate;
    String clientname;
    String invoicenumber;
    String str;
    String amount;
    String description;
    boolean checkedvalue;
    ExportDatabaseCSVTask ep = new ExportDatabaseCSVTask();
    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private String latitude, longitude;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = DbHelper.getInstance(getApplicationContext());
        dates = (EditText) findViewById(R.id.date);
        et_cname = (EditText) findViewById(R.id.et_clientname);
        et_inumber = (EditText) findViewById(R.id.et_invoiceid);
        //et_date = (CalendarView) findViewById(R.id.calendarView2);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_desc = (EditText) findViewById(R.id.et_description);
        loc = (EditText) findViewById(R.id.et_location);
        cb1 = (CheckBox) findViewById(R.id.cb);
        cb2 = (CheckBox) findViewById(R.id.cb1);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        imageView = (ImageView) this.findViewById(R.id.ImageView1);
        //viewbt = (Button) findViewById(R.id.btn_convert);
        btn_photo = (Button) findViewById(R.id.btn_photo);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

        dates.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // Button listener to show date picker dialog

        dates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);

            }

        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = new UserData();

                if (!et_cname.getText().toString().isEmpty()) {
                    userData.ClientName = et_cname.getText().toString();
                } else {
                    userData.ClientName = "";
                }
                if (!et_inumber.getText().toString().isEmpty()) {
                    userData.InvoiceId = et_inumber.getText().toString();
                } else {
                    userData.InvoiceId = "";
                }
                if (!dates.getText().toString().isEmpty()) {
                    userData.Date = dates.getText().toString();
                } else {
                    userData.Date = "";
                }
                if (!et_amount.getText().toString().isEmpty()) {
                    userData.Amount = et_amount.getText().toString();
                } else {
                    userData.Amount = "";
                }

                if (!et_desc.getText().toString().isEmpty()) {
                    userData.Description = et_desc.getText().toString();
                } else {
                    userData.Description = "";
                }
                if (cb1.isChecked()) {
                    userData.be = "yes";
                }
                if (cb2.isChecked()) {
                    userData.be = "No";
                }

                dbHelper.insertUserDetail(userData);

                Intent intent = new Intent(MainActivity.this, UserDetailList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
       /* locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null)

        {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else

        {
            // latituteField.setText("Location not available");
            // longitudeField.setText("Location not available");
            Toast.makeText(this, "No location is found", Toast.LENGTH_SHORT).show();
        }
    }
    


    /* Request updates at startup */

  /*  protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
        /*   protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
    //    latituteField.setText(String.valueOf(lat));
      //  longitudeField.setText(String.valueOf(lng));
        latitude=Integer.toString(lat);
        longitude=Integer.toString(lng);
        //Toast.makeText(this,"latitude and longitude are"+latitude+","+longitude,Toast.LENGTH_LONG).show();
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(lat, lng,
                getApplicationContext(),new GeocoderHandler());
       // loc.setText((CharSequence) locationAddress);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

*/
    }
        protected void onActivityResult( int requestCode, int resultCode, Intent data){
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                //  imageView.setImageBitmap(photo);
                Toast.makeText(getBaseContext(), "Image is saved in Gallery", Toast.LENGTH_LONG).show();
            }
        }

    protected Dialog onCreateDialog(int id) {
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
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            dates.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
            curDate = dates.getText().toString();
        }
    };


}






/*finally {
        if (myDB != null) { myDB.close(); }
 }   }*/














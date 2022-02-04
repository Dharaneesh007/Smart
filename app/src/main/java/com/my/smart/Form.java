package com.my.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Form extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener{

    EditText date_edt, time_edt, etime_edt, hr_edt, pu_edt;
    Button  btnEntry;
    private  int mYear, mMonth, mDay, mHour, mMinute;
    private Spinner sname_spinner, clsname_spinner;
    DatabaseReference databaseReference,databaseReference1,databaseReference2;
    ValueEventListener listener,listener1;
    ArrayList<String> list,list1;
    ArrayAdapter<String> adapter,adapter1;
    FirebaseAuth mAuth;
    private static Locale id = new Locale("en", "IN");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", id);
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int hr1,hr2,minute1,minute2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);




        mAuth = FirebaseAuth.getInstance();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Menu menu = navigationView.getMenu();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            menu.findItem(R.id.nav_login).setVisible(false);
        }
        if (user == null){
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
        }

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Form.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);


        btnEntry = (Button) findViewById(R.id.btnEtry);

        date_edt = (EditText) findViewById(R.id.date_edt_text);
        time_edt = (EditText) findViewById(R.id.time_edt_text);
        etime_edt = (EditText) findViewById(R.id.etime_edt_text);
        hr_edt = (EditText) findViewById(R.id.hr_edt_text);
        pu_edt = (EditText) findViewById(R.id.pu_edt_text);

        sname_spinner = (Spinner) findViewById(R.id.sname_spr);
        clsname_spinner = (Spinner) findViewById(R.id.clsname_spr);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("faculty");

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("class");

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("entry");



        date_edt.setOnClickListener(this);
        time_edt.setOnClickListener(this);
        etime_edt.setOnClickListener(this);
        btnEntry.setOnClickListener(this);

        fecthdata();
        fecthdata1();
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.spinner,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        sname_spinner.setAdapter(adapter);
        list1 = new ArrayList<String>();
        adapter1 = new ArrayAdapter<String>(this, R.layout.spinner,list1);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown);
        clsname_spinner.setAdapter(adapter1);

    }

    private void fecthdata1() {

        listener1 = databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren())
                    list1.add(mydata.child("name").getValue().toString());
                adapter1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fecthdata() {
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren())
                    list.add(mydata.child("name").getValue().toString());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onClick(View v) {

        if (v==date_edt){
            final Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date_edt.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            },mYear,mMonth,mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.setTitle("Date");
            datePickerDialog.show();
        }
        if (v==time_edt){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time_edt.setText(hourOfDay+":"+minute);
                    hr1 = hourOfDay;
                    minute1 = minute;
                }
            },mHour,mMinute, false);
            timePickerDialog.show();
            timePickerDialog.setTitle("Starting Time");
        }
        if (v==etime_edt){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etime_edt.setText(hourOfDay+":"+minute);
                    hr2 = hourOfDay;
                    minute2 = minute;

                    int hr = hr2-hr1;
                    int min = minute1-minute2;

                    hr_edt.setText(hr+":"+min);
                    hr_edt.setEnabled(false);
                }
            },mHour,mMinute, false);
            timePickerDialog.show();
            timePickerDialog.setTitle("End Time");
        }
        if(v==btnEntry){
            String id = databaseReference2.push().getKey();
            String fname = sname_spinner.getSelectedItem().toString().trim();
            String cname = clsname_spinner.getSelectedItem().toString().trim();
            String tgl_daftar_date = date_edt.getText().toString().trim();
            String stime = time_edt.getText().toString().trim();
            String etime = etime_edt.getText().toString().trim();
            String hrs = hr_edt.getText().toString().trim();
            String pur = pu_edt.getText().toString().trim();

            Entry entry = new Entry(id, fname, cname, tgl_daftar_date, stime, etime,hrs, pur);
            databaseReference2.child(id).setValue(entry)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Form.this, "Entry added", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Form.this, WelcomeActivity.class));
                        }
                    });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(Form.this, LoginActivity.class));
            Toast.makeText(Form.this, "Login is mandatory to make an Entry!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(Form.this, UserProfileActivity.class));
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(Form.this, WelcomeActivity.class));
                finish();
                break;
            case R.id.nav_about:
                startActivity(new Intent(Form.this, AboutActivity.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(Form.this, LoginActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(Form.this, SettingsActivity.class));
                break;
            case R.id.nav_contact:
                startActivity(new Intent(Form.this, AboutActivity.class));
                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(Form.this, WelcomeActivity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(Form.this, WelcomeActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
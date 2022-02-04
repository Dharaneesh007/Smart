package com.my.smart;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Final extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText input_minimal,
            input_maximal;
    Button cari,
            export;
    String csvFile;
    WritableWorkbook workbook;
    ArrayList<dataUser> list = new ArrayList<>();
    AdapterItem adapterItem;
    RecyclerView recyclerView;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    Context context;
    Calendar calendar = Calendar.getInstance();
    Locale id = new Locale("en", "IN");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", id);
    Date date_minimal;
    Date date_maximal;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Final.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);

        context = this;
        cari = findViewById(R.id.cari);
        input_minimal = findViewById(R.id.input_minimal);
        input_maximal = findViewById(R.id.input_maximal);
        recyclerView = findViewById(R.id.recyclerView_data);
        export = findViewById(R.id.excel);

        export.setEnabled(false);



        input_minimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        input_minimal.setText(simpleDateFormat.format(calendar.getTime()));
                        date_minimal = calendar.getTime();


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        input_maximal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        input_maximal.setText(simpleDateFormat.format(calendar.getTime()));
                        date_maximal = calendar.getTime();

                        String input1 = input_maximal.getText().toString();
                        String input2 = input_minimal.getText().toString();
                        cari.setEnabled(!input1.isEmpty() && !input2.isEmpty());
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = database.child("entry");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        showLisenerRange(snapshot,date_minimal,date_maximal);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        addListenerOnButton();

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                else
                {
                    //your code
                    try {
                        createExcelSheet();
                        Log.e(TAG, "Writing file");
                    } catch (Exception e) {
                        Log.e(TAG, "Error writing Exception: ", e);
                    }
                }

            }
        });


        showData();
    }

    void createExcelSheet() {

        if (csvFile != null) {
            File file = new File(Environment.getExternalStorageDirectory(), "Smart");

            if (!file.exists()) {

                file.mkdir();

            }

            File futureStudioIconFile = new File(Environment
                    .getExternalStorageDirectory() + "/" + "Smart"
                    + "/" + csvFile + ".xls");
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            try {
                workbook = Workbook.createWorkbook(futureStudioIconFile, wbSettings);
                createFirstSheet();
//            createSecondSheet();
                //closing cursor
                workbook.write();
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(context, "Enter file name to Save", Toast.LENGTH_LONG).show();
        }
    }


    void createFirstSheet() {
        try {


            database.child("entry").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    showLisenerRange(snapshot,date_minimal,date_maximal);
                    Toast.makeText(context, "Data Exported, Check File Maganer/Smart/"+csvFile, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Final.this, Final.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "Faculty Name"));
            sheet.addCell(new Label(1, 0, "Class"));
            sheet.addCell(new Label(2, 0, "Date"));
            sheet.addCell(new Label(3, 0, "Starting Time"));
            sheet.addCell(new Label(4, 0, "Ending Time"));
            sheet.addCell(new Label(5, 0, "Total Hours"));
            sheet.addCell(new Label(6, 0, "Purpose"));


            for (int i = 0; i < list.size(); i++) {
                sheet.addCell(new Label(0, i + 1, list.get(i).getFname()));
                sheet.addCell(new Label(1, i + 1, list.get(i).getCname()));
                sheet.addCell(new Label(2, i + 1, list.get(i).getTime()));
                sheet.addCell(new Label(3, i + 1, list.get(i).getStime()));
                sheet.addCell(new Label(4, i + 1, list.get(i).getEtime()));
                sheet.addCell(new Label(5, i + 1, list.get(i).getHrs()));
                sheet.addCell(new Label(6, i + 1, list.get(i).getPur()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showData() {
        database.child("entry").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showLisener(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showLisener(DataSnapshot snapshot) {
        list.clear();
        for (DataSnapshot item : snapshot.getChildren()) {
            dataUser user = item.getValue(dataUser.class);
            list.add(user);
        }
        adapterItem = new AdapterItem(context, list);
        recyclerView.setAdapter(adapterItem);
    }
    private void showLisenerRange(DataSnapshot snapshot, Date date_minimal, Date date_maximal) {
        list.clear();
        for (DataSnapshot item : snapshot.getChildren()) {
            dataUser user = item.getValue(dataUser.class);
            if (user != null){
                Date current = strtotime.strtotime(user.getTime());
                if (current.getTime()>= date_minimal.getTime() && current.getTime()<=date_maximal.getTime()){
                    list.add(user);

                }
            }

            }
        adapterItem = new AdapterItem(context, list);
        recyclerView.setAdapter(adapterItem);
    }

    public void addListenerOnButton() {
        TextView button =  findViewById(R.id.file);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // load the dialog_promt_user.xml layout and inflate to view
                LayoutInflater layoutinflater = LayoutInflater.from(context);
                View promptUserView = layoutinflater.inflate(R.layout.item_file, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptUserView);

                final EditText userAnswer = (EditText) promptUserView.findViewById(R.id.username);

                alertDialogBuilder.setTitle("File Name");

                // prompt for username
                alertDialogBuilder.setPositiveButton("Ok", (dialog, id) -> {
                    // and display the username on main activity layout
                    csvFile = userAnswer.getText().toString();
                    userAnswer.setText(csvFile);
                    export.setEnabled(true);
                });

                // all set and time to build and show up!
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(Final.this, LoginActivity.class));
            Toast.makeText(Final.this, "Login is mandatory to make an Data Export!", Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(Final.this, UserProfileActivity.class));
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(Final.this, Final.class));
                finish();
                break;
            case R.id.nav_about:
                startActivity(new Intent(Final.this, AboutActivity.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(Final.this, LoginActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(Final.this, SettingsActivity.class));
                break;
            case R.id.nav_contact:
                startActivity(new Intent(Final.this, AboutActivity.class));
                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(Final.this, WelcomeActivity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(Final.this, Final.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
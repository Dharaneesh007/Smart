package com.my.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText editTextPwdCurr, editTextPwdNew, getEditTextPwdConfirmNew;
    private TextView textViewAuthenticated;
    private Button buttonChangePwd, buttonreAuthenticate;
    private ProgressBar progressBar;
    private String userPwdCurr, userPwdNew, userPwdConfirmNew;

    private FirebaseAuth authProfile;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Menu menu = navigationView.getMenu();

            menu.findItem(R.id.menu_change_password).setVisible(false);
            menu.findItem(R.id.menu_delete_profile).setVisible(false);
            menu.findItem(R.id.menu_update_email).setVisible(false);
            menu.findItem(R.id.menu_update_profile).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ChangePasswordActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);


        editTextPwdNew = findViewById(R.id.editText_change_password);
        getEditTextPwdConfirmNew = findViewById(R.id.editText_change_confirm_password);
        editTextPwdCurr = findViewById(R.id.editText_change_pass_current);
        textViewAuthenticated = findViewById(R.id.textView_change_pass_authenticated);
        progressBar = findViewById(R.id.progressBar7);
        buttonChangePwd = findViewById(R.id.button_change_password);
        buttonreAuthenticate = findViewById(R.id.button_change_pass_authenticate);

        editTextPwdNew.setEnabled(false);
        getEditTextPwdConfirmNew.setEnabled(false);
        buttonChangePwd.setEnabled(false);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser.equals("")){
            Toast.makeText(ChangePasswordActivity.this, "Something went wrong! User's details not availabe", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordActivity.this,UserProfileActivity.class);
            startActivity(intent);
            finish();
        }else{
            reAuthenticate(firebaseUser);
        }

    }

    private void reAuthenticate(FirebaseUser firebaseUser) {
        buttonreAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPwdCurr = editTextPwdCurr.getText().toString();
                if(TextUtils.isEmpty(userPwdCurr)){
                    Toast.makeText(ChangePasswordActivity.this, "password is needed to continue", Toast.LENGTH_SHORT).show();
                    editTextPwdCurr.setError("Please enter your password for authentication");
                    editTextPwdCurr.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), userPwdCurr);

                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ChangePasswordActivity.this, "Successfully Authenticated", Toast.LENGTH_SHORT).show();

                                textViewAuthenticated.setText("You are authenticated. You can update your email now.");
                                editTextPwdNew.setEnabled(true);
                                getEditTextPwdConfirmNew.setEnabled(true);
                                buttonChangePwd.setEnabled(true);
                                editTextPwdCurr.setEnabled(false);
                                buttonreAuthenticate.setEnabled(false);

                                buttonChangePwd.setBackgroundTintList(ContextCompat.getColorStateList(ChangePasswordActivity.this,R.color.purple_200));

                                buttonChangePwd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        userPwdNew = editTextPwdNew.getText().toString();
                                        userPwdConfirmNew = getEditTextPwdConfirmNew.getText().toString();

                                        if(TextUtils.isEmpty(userPwdNew)){
                                            Toast.makeText(ChangePasswordActivity.this, "Please Enter your password", Toast.LENGTH_SHORT).show();
                                            editTextPwdNew.setError("Password is Required");
                                            editTextPwdNew.requestFocus();
                                        }else if(userPwdCurr.matches(userPwdNew)){
                                            Toast.makeText(ChangePasswordActivity.this, "Seems to be same as Old Current Password", Toast.LENGTH_SHORT).show();
                                            editTextPwdNew.setError("Please enter New Password");
                                            editTextPwdNew.requestFocus();
                                        }else if(userPwdNew.length()<6){
                                            Toast.makeText(ChangePasswordActivity.this, "Please Enter your password", Toast.LENGTH_SHORT).show();
                                            editTextPwdNew.setError("Password is weak");
                                            editTextPwdNew.requestFocus();
                                        }else if(TextUtils.isEmpty(userPwdConfirmNew)){
                                            Toast.makeText(ChangePasswordActivity.this, "Please Enter your full name", Toast.LENGTH_SHORT).show();
                                            getEditTextPwdConfirmNew.setError("Password Confirmation is Required");
                                            getEditTextPwdConfirmNew.requestFocus();
                                        }else if(!userPwdNew.equals(userPwdConfirmNew)){
                                            Toast.makeText(ChangePasswordActivity.this, "Please re-enter your full name", Toast.LENGTH_SHORT).show();
                                            getEditTextPwdConfirmNew.setError("Password is mismatched");
                                            getEditTextPwdConfirmNew.requestFocus();

                                            editTextPwdNew.clearComposingText();
                                            getEditTextPwdConfirmNew.clearComposingText();
                                        }else{
                                            updatePassword(firebaseUser);
                                        }
                                    }
                                });
                            }else{
                                try {
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void updatePassword(FirebaseUser firebaseUser) {

        firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ChangePasswordActivity.this, "Passowrd has been changed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this,UserProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
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
            case R.id.menu_refresh:
                startActivity(getIntent());
                finish();
                overridePendingTransition(0,0);
                break;
            case R.id.menu_profile:
                startActivity(new Intent(ChangePasswordActivity.this, UserProfileActivity.class));
                break;
            case R.id.menu_update_profile:
                startActivity(new Intent(ChangePasswordActivity.this, UpdateProfileActivity.class));
                break;
            case R.id.menu_update_email:
                startActivity(new Intent(ChangePasswordActivity.this, UpdateEmailActivity.class));
                break;
            case R.id.menu_change_password:
                startActivity(new Intent(ChangePasswordActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.menu_delete_profile:
                startActivity(new Intent(ChangePasswordActivity.this, DeleteProfileActivity.class));
                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(ChangePasswordActivity.this, WelcomeActivity.class));
                break;
            case R.id.menu_logout:
                authProfile.signOut();
                Toast.makeText(ChangePasswordActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangePasswordActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
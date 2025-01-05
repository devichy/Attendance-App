package com.example.attendanceapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;
    FirebaseAuth mAuth;
    DatePickerDialog.OnDateSetListener setListener;

    private static final Pattern name_pattern = Pattern.compile("^[a-zA-Z .]+$");
    //    private static final Pattern email_pattern = Pattern.compile("^[a-z0-9]+@[a-z]+.[a-z]+$\"");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%&*()]).{6,20}");
    private static final Pattern mobile_pattern = Pattern.compile("(\\+88)?-?01[3-9]\\d{8}");

    String getFullname,getEmailTxt,getPassword,getMobileTxt,getDob;
    FirebaseDatabase db;
    DatabaseReference reference;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        ProgressBar progressBar = findViewById(R.id.progressBar);
        EditText email = findViewById(R.id.emailET);
        EditText mobile = findViewById(R.id.mobileET);
        EditText dob = findViewById(R.id.dobET);
        EditText fullname = findViewById(R.id.FullNameET);
        EditText password = findViewById(R.id.passwordET);
        EditText conPassword = findViewById(R.id.conPasswordET);
        ImageView passwordIcon = findViewById(R.id.passwordIcon);
        ImageView conPasswordIcon = findViewById(R.id.conPasswordIcon);
        AppCompatButton singUpBtn = findViewById(R.id.signUnBtn);
        TextView signInBtn = findViewById(R.id.signInBtn);


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (passwordShowing) {
                    passwordShowing = false;

                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.pass_show);
                } else {
                    passwordShowing = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.pass_hide);
                }
                // Move the cursor at last of the text
                password.setSelection(password.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (conPasswordShowing) {
                    conPasswordShowing = false;

                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.pass_show);
                } else {
                    conPasswordShowing = true;

                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.pass_hide);
                }
                // Move the cursor at last of the text
                conPassword.setSelection(conPassword.length());
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                dob.setText(date);
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;

                        String date = dayOfMonth + "/" + month + "/" + year;
                        dob.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(view.GONE);
                final String getMobileTxt = mobile.getText().toString();
                final String getEmailTxt = email.getText().toString();
                final String getPassword = password.getText().toString();
                final String getConPassword = conPassword.getText().toString();
                final String getDob = dob.getText().toString();
                final String getFullname = fullname.getText().toString();

                /*if(!getFullname.isEmpty() && !getEmailTxt.isEmpty() && !getPassword.isEmpty() && !getMobileTxt.isEmpty() && !getDob.isEmpty()){

                    User user = new User(getFullname,getEmailTxt,getPassword,getMobileTxt,getDob);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("UserInfo");
                    reference.child(getFullname.trim()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            fullname.setText("");
                            email.setText("");
                            password.setText("");
                            mobile.setText("");
                            dob.setText("");

                        }
                    });
                }*/


                if (TextUtils.isEmpty(getFullname)) {
                    fullname.setError("Full Name is Required");
                    fullname.requestFocus();
                    // Toast.makeText(Register.this, "Enter Fullname ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!name_pattern.matcher(getFullname).matches()) {
                    Toast.makeText(Register.this, "Enter a proper name !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getEmailTxt)) {
                    email.setError("Email is Required");
                    email.requestFocus();
                    // Toast.makeText(Register.this, "Enter Email ", Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if (!email_pattern.matcher(getEmailTxt).matches()){
//                    Toast.makeText(Register.this, "Enter Valid email !!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (TextUtils.isEmpty(getMobileTxt)) {
                    mobile.setError("Mobile is Required");
                    mobile.requestFocus();
                    // Toast.makeText(Register.this, "Enter Mobile Number ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!mobile_pattern.matcher(getMobileTxt).matches()) {
                    Toast.makeText(Register.this, "Enter Valid mobile number !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(getPassword)) {
                    password.setError("Password is Required");
                    password.requestFocus();
                    // Toast.makeText(Register.this, "Enter Password ", Toast.LENGTH_SHORT).show();

                    return;
                } else if (!PASSWORD_PATTERN.matcher(getPassword).matches()) {

                    Toast.makeText(Register.this, "At least 1 digit, 1 lower, 1 upper, 1 special char and length 6-20 required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getConPassword)) {
                    conPassword.setError("Confirm Password is Required");
                    conPassword.requestFocus();
                    //Toast.makeText(Register.this, "Enter Confirm Password ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getDob)) {
                    dob.setError("Date of birth is Required");
                    dob.requestFocus();
                    //Toast.makeText(Register.this, "Enter Date of Birth ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!getPassword.equals(getConPassword)) {
                    Toast.makeText(Register.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(getEmailTxt, getPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                Toast.makeText(Register.this, "This email is already used", Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);


                                Toast.makeText(Register.this, "Please check your email for verification", Toast.LENGTH_SHORT).show();
                                firebaseUser.sendEmailVerification();
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account Created .", Toast.LENGTH_SHORT).show();

                                    mAuth.signOut();

                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);

            }
        });
    }
}

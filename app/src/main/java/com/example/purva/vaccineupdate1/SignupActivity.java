package com.example.purva.vaccineupdate1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener  {

    // [START define_functions_instance]
    private FirebaseFunctions mFunctions;
    // [END define_functions_instance

    private EditText _nameText;
    private EditText _childnameText;
    private EditText _childdobText;
    private EditText _addressText;
    private EditText _emailText;
    private EditText _mobileText;
    private EditText _passwordText;
    private EditText _reEnterPasswordText;
    private Button _signupButton;
    private TextView _loginLink;
    private int mYear, mMonth, mDay;
    private Spinner spinner;
    private static final String[] paths = {"Guardian", "Father", "Mother"};
    String childAssoc="";
    long finalDate;
    private String global_token;
    //private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private  DatabaseReference mDatabaseRef;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();

        setContentView(R.layout.activity_signup);

        _loginLink=findViewById(R.id.link_login);
        _signupButton=findViewById(R.id.btn_signup);
        _reEnterPasswordText=findViewById(R.id.input_reEnterPassword);
        _nameText=(EditText)findViewById(R.id.input_name);
        _childnameText=(EditText)findViewById(R.id.input_childname);
        _childdobText=(EditText)findViewById(R.id.input_dob);
        _nameText=(EditText)findViewById(R.id.input_name);
        _passwordText=findViewById(R.id.input_password);
        _addressText=(EditText)findViewById(R.id.input_address);
        _emailText=findViewById(R.id.input_email);
        _mobileText = findViewById(R.id.input_mobile);
        //for drop down
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();

            }
        });
        _childdobText.setOnClickListener(this);
        _childdobText.setInputType(InputType.TYPE_NULL);

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //to check if user is already signed in

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null) {
            Toast.makeText(this, "Already signed in! Logging you out.",Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
        }
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String name = _nameText.getText().toString();
        String childName = _childnameText.getText().toString();
        String childDOB = _childdobText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }
        if (childName.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }
        if (childDOB.isEmpty() || name.length() < 3) {
            _nameText.setError("select Date of Birth of Child");
            valid = false;
        } else {
            _nameText.setError(null);
        }
        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }
        if (childAssoc=="") {
            childAssoc="Guardian";
            valid = false;
        }


        return valid;
    }
    public void signup() {

        final String FCM_TOKEN = "FCM_TOKEN";

        // [START initialize_functions_instance]
        mFunctions = FirebaseFunctions.getInstance();
        // [END initialize_functions_instance]


        //fcm token part
        //generate fcm token ... and send an http request to invoke cloud functions

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SignupActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String fcmToken = instanceIdResult.getToken();
                setToken(fcmToken);
                Log.d(FCM_TOKEN,fcmToken);
            }
        });

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String childName = _childnameText.getText().toString();
        final String childDOB = _childdobText.getText().toString();
        final long childDOBInMilliseconds = finalDate;
        final String address = _addressText.getText().toString();
        final String email = _emailText.getText().toString();
        final String mobile = _mobileText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String reEnterPassword = _reEnterPasswordText.getText().toString();
        final String childAssociation = childAssoc;

        // TODO: Implement your own signup logic here.

       mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(String.valueOf(0), "createUserWithEmail:success");

                            //Toast.makeText(this, "User Logged in",Toast.LENGTH_SHORT).show();

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                            if (currentUser == null) {
                                //Toast.makeText(this, "No User",Toast.LENGTH_LONG).show();
                                Log.d("1", "This object is empty");
                            }

                            if (currentUser != null) {
                                //Toast.makeText(this, currentUser.getEmail(),Toast.LENGTH_LONG).show();
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("dob").setValue(childDOB);
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("parentName").setValue(name);
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("childName").setValue(childName);
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("address").setValue(address);
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("mobile").setValue(mobile);
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("assoc_with_child").setValue(childAssociation);
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("childDOBInMilliseconds").setValue(childDOBInMilliseconds);
                                mDatabaseRef.child("users").child(currentUser.getUid()).child("token").setValue(global_token);
                                //mDatabase.setValue(); - update db here.
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(String.valueOf(1), "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        //



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String day,month;
                        if(dayOfMonth < 10){
                            day = "0" + dayOfMonth;
                        }
                        else {
                            day = "" + dayOfMonth;
                        }
                        if((monthOfYear + 1) < 10){
                            month = "0" + (monthOfYear + 1);
                        }
                        else{
                            month = "" + (monthOfYear + 1);
                        }

                        String tempDate = month + "." +  day + "." + year;
                        Date date = null;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yyyy");
                        try {
                            date = simpleDateFormat.parse(tempDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long longDate = date.getTime();//mili seconds from 1970
                        finalDate = longDate;
                        _childdobText.setText(day + "/" +  month + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                childAssoc = "Guardian";
                break;
            case 1:
                childAssoc = "Mother";
                break;
            case 2:
                childAssoc = "Father";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            childAssoc ="Guardian";
    }

    public void setToken(String token1)
    {
        global_token = token1;
    }
}

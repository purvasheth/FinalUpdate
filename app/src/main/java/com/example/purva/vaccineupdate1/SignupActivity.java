package com.example.purva.vaccineupdate1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener  {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

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

        String name = _nameText.getText().toString();
        String childName = _childnameText.getText().toString();
        String childDOB = _childdobText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String childAssociation = childAssoc;
        // TODO: Implement your own signup logic here.

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

                        _childdobText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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
}

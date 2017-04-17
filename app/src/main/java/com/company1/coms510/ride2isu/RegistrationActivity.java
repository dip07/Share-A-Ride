package com.company1.coms510.ride2isu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Stroh L on 10/14/2016.
 */
public class RegistrationActivity extends Activity {

    CheckBox pp;
    CheckBox ss;
    CheckBox dl;
    EditText fullName;
    EditText userName;
    EditText dob;
    EditText email;
    EditText id;
    EditText password;
    EditText confirmPassword;
    TextView imagePath;
    Bitmap uploadImage;
    String selectedCheckBox;

    int imageIntentRequestCode;


 /*   private EditText fName;
    private EditText lName;*/
    //private EditText etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        init();


        // registerViews();
    }


    public void init()
    {
        pp=(CheckBox)findViewById(R.id.passportBox);
        ss=(CheckBox)findViewById(R.id.ssBox);
        dl=(CheckBox)findViewById(R.id.dlBox);
        fullName =(EditText)findViewById(R.id.fullNameEditText);
        userName =(EditText)findViewById(R.id.userNameEditText);
        dob=(EditText)findViewById(R.id.dobEditText);
        id = (EditText)findViewById(R.id.idEditText);
        email=(EditText)findViewById(R.id.emailEditText);
        password=(EditText)findViewById(R.id.passwordEditText);
        confirmPassword=(EditText)findViewById(R.id.confirmPasswordEditText);
        imageIntentRequestCode = 7;
        imagePath=(TextView)findViewById(R.id.uploadView);
    }



    public void uploadClicked(View v){

        Intent photopicker = new Intent(Intent.ACTION_PICK);
        File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String directoryPath = pictures.getPath();
        Uri data=Uri.parse(directoryPath);

        photopicker.setDataAndType(data, "image/*");
        startActivityForResult(photopicker, imageIntentRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == imageIntentRequestCode )
        {
            Uri imageData = data.getData();
            InputStream stream;
            try {
                stream = getContentResolver().openInputStream(imageData);
                imagePath.setText(imageData.toString());
                Bitmap image = BitmapFactory.decodeStream(stream);
                uploadImage=image;

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Could not get image", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void selectCheckBox(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        if(checked && view.getId() == R.id.ssBox) {
            selectedCheckBox = "ssbox";
            dl.setChecked(false);
            pp.setChecked(false);
        }
        else if(checked && view.getId() == R.id.passportBox) {
            selectedCheckBox = "ppbox";
            dl.setChecked(false);
            ss.setChecked(false);

        }
        else if(checked && view.getId() == R.id.dlBox) {
            selectedCheckBox = "dlbox";
            ss.setChecked(false);
            pp.setChecked(false);
        }
        else
        {
            selectedCheckBox = null;
        }
    }

    public void submitOnClick(View v)
    {
        String input = validation();
        if(!input.isEmpty())
        {
            Toast.makeText(RegistrationActivity.this, input, Toast.LENGTH_LONG).show();
        }
        //send data to database, if database returns error make toast telling whats wrong
        else{
            Toast.makeText(RegistrationActivity.this,"registration successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);
        }

    }


    private String validation()
    {
        StringBuilder result = new StringBuilder();
        String name = fullName.getText().toString();
        String email = this.email.getText().toString();
        String dob = this.dob.getText().toString();
        String pass = password.getText().toString();
        String confirmpass = confirmPassword.getText().toString();
        String id= this.id.getText().toString();
        String username = userName.getText().toString();

        if(!validName(name))
        {
            result.append("Must type first and last name. Only allows alphaNumericCharacters,- and a maximum of 1 space\n");
        }
        if(!validEmail(email))
        {
            result.append("email must have an @ sign and a . extension\n");
        }
        if(!validDOB(dob))
        {
            result.append("Not in form MM/DD/YYYY, or invalid date\n");
        }
        if(!validId(id))
        {
            result.append("check the box and enter id\n");
        }
//        if((!validPicture(uploadImage)))
//        {
//            result.append("must select picture using upload button\n");
//        }
        if(!validUsername(username))
        {
            result.append("enter a username");
        }
        if((!validPasswords(pass,confirmpass)))
        {
            result.append("passwords do not match or empty");
        }
        return result.toString();
    }

    private boolean validPasswords(String password,String confirmPassword)
    {
        if(password == null||password.isEmpty()||confirmPassword == null||password.isEmpty())
        {
            return false;
        }
        return password.equals(confirmPassword);
    }

    private boolean validUsername(String input)
    {
        if(input == null||input.isEmpty())
        {
            return false;
        }
        return true;
    }
    private boolean validPicture(Bitmap image)
    {
        if(image == null)
        {
            return false;
        }
        return true;
    }

    private boolean validId(String input)
    {
        if(input== null || input.isEmpty())
        {
            return false;
        }
        if(selectedCheckBox == null)
        {
            return false;
        }
        return true;
    }

    private boolean validDOB(String input){
        if(input == null){return false;}
        String[]info = input.split("/");
        if(info.length != 3)
        {
            return false;
        }
        try {
            int month = Integer.parseInt(info[0]);
            int day = Integer.parseInt(info[1]);
            int year = Integer.parseInt(info[2]);

            if(month>12||month < 0)
            {
                return false;
            }
            if(day>31||day < 1)
            {
                return false;
            }
            if(year >9999|| year < 1000)
            {
                return false;
            }
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }


    private boolean validEmail(String input){
        if(input==null){return false;}
        int atLocation;
        if((atLocation = input.indexOf('@'))<0)
        {
            return false;
        }
        String sequence = input.substring(atLocation,input.length());
        if(sequence.indexOf('.')<0)
        {
            return false;
        }
        return true;
    }


    private boolean validName(String input){
        if(input == null) {return false;}
        String[] names = input.split(" ");
        if(names.length != 2)
        {
            return false;
        }
        for(String n: names)
        {
            if(!hasAllValidNameCharacters(n))
            {
                return false;
            }
        }
        return true;
    }

    private boolean  hasAllValidNameCharacters(String input){
        if(input == null){return false;}
        String validCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-";
        for(int i =0;i<input.length();i++)
        {
            if(validCharacters.indexOf(input.charAt(i)) < 0)
            {
                return false;
            }
        }
        return true;
    }

    /*private void registerViews() {
        fName = (EditText) findViewById(R.id.et_normal_text);
        // TextWatcher would let us check validation error on the fly
        etNormalText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etNormalText);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etEmailAddrss = (EditText) findViewById(R.id.et_email_address);
        etEmailAddrss.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(etEmailAddrss, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isPhoneNumber(etPhoneNumber, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 *//*
                if ( checkValidation () )
                    submitForm();
                else
                    Toast.makeText(MyActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(etNormalText)) ret = false;
        if (!Validation.isEmailAddress(etEmailAddrss, true)) ret = false;
        if (!Validation.isPhoneNumber(etPhoneNumber, false)) ret = false;

        return ret;
    }*/
}


package com.example.card_quiz_game_tilloop1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Iterator;

import static android.text.TextUtils.isEmpty;
import static android.util.Patterns.EMAIL_ADDRESS;

/************************************************************************************
 *
 *  The Register activity captures User details like First Name, Family name, Email
 *  address, password and their Date of birth.
 * @author Pallavi Tilloo
 *
 *************************************************************************************/

public class Register extends AppCompatActivity {
    ArrayList<TextInputLayout> registerLayout;
    TextInputEditText tbFirstName, tbFamilyName, tbEmail, tbPassword, tbDOB;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        //Capture details from the Input fields
        tbFirstName = (TextInputEditText)findViewById(R.id.tbFirstName);
        tbFamilyName = (TextInputEditText)findViewById(R.id.tbFamilyName);
        tbEmail = (TextInputEditText)findViewById(R.id.tbEmail);
        tbPassword = (TextInputEditText)findViewById(R.id.tbPassword);
        tbDOB = (TextInputEditText)findViewById(R.id.tbDOB);

        //Create an array list with all layouts for easy iteration
        registerLayout = new ArrayList<>();
        registerLayout.add((TextInputLayout)findViewById(R.id.layout1));
        registerLayout.add((TextInputLayout)findViewById(R.id.layout2));
        registerLayout.add((TextInputLayout)findViewById(R.id.layout3));
        registerLayout.add((TextInputLayout)findViewById(R.id.layout4));
        registerLayout.add((TextInputLayout)findViewById(R.id.layout5));

        //To iterate through the fields
        Iterator<TextInputLayout> iterator = registerLayout.iterator();
        while(iterator.hasNext()){

            //Add text change listeners for all the text input layouts
            TextInputEditText tbField = (TextInputEditText)iterator.next().getEditText();

            tbField.addTextChangedListener(new TextWatcher(){
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                //This is to remove the error message if the user starts typing again
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    TextInputLayout layout = (TextInputLayout)tbField.getParent().getParent();
                    layout.setErrorEnabled(false);
                }

                public void afterTextChanged(Editable editable) {
                }
            });
        }
    }

    /************************************************************************************
     *
     *  IfEmptyOrInvalid method takes an array list of Text Input Layouts as argument
     *  and checks the fields for empty values, invalid email address for the email field,
     *  and invalid date format (other than MM/DD/YYYY) for the Date of Birth (DOB) field
     *
     *************************************************************************************/

    private boolean IfEmptyOrInvalid(ArrayList<TextInputLayout> layouts){
        Iterator<TextInputLayout> iterator = layouts.iterator();
        String strText;
        boolean isInvalid = false;
        while(iterator.hasNext()){
            TextInputLayout layout = iterator.next();
            strText = layout.getEditText().getText().toString();

            //Check if any field is empty
            if(isEmpty(strText)){
                layout.setErrorEnabled(true);
                layout.setError("Input required!");
                isInvalid = true;
            }
            else{

                //Check specific inputs - email, DOB and text fields
                switch(layout.getId()) {

                    case R.id.layout1:

                        //First name needs to be between 3-30 characters
                        if (strText.length() < 3 || strText.length() > 30) {
                            layout.setErrorEnabled(true);
                            layout.setError("First name should be 3-30 characters long!");
                            isInvalid = true;
                        } else {
                            layout.setErrorEnabled(false);
                        }
                        break;

                    case R.id.layout3:

                        //EMAIL_ADDRESS.matcher checks the input string for a valid email format
                        if (!EMAIL_ADDRESS.matcher(strText).matches()) {
                            layout.setErrorEnabled(true);
                            layout.setError("Invalid email format!");
                            isInvalid = true;
                        } else {
                            layout.setErrorEnabled(false);
                        }
                        break;

                    case R.id.layout5:

                        //If the field is date of birth, check the format for MM/DD/YYYY
                        if (!dateValidator(strText)) {
                            layout.setErrorEnabled(true);
                            layout.setError("Invalid date format! (Rqd: MM/DD/YYYY)");
                            isInvalid = true;
                        }
                        break;

                    default:
                        break;
                }
            }
        }
        return isInvalid;
    }

    /************************************************************************************
     *
     *  dateValidator method takes a string for date and checks if the date format is
     *  valid (MM/DD/YYYY) for the Date of Birth (DOB) field. Returns a boolean variable.
     *  The hardcoded values for checking validity can be changed later. This is just to
     *  make a simple quick date validator for registration
     *
     *************************************************************************************/

    private boolean dateValidator(String strDate){
        boolean dateIsValid = false;
        int mon, date, year;

        //Split the date into month, date and year
        String[] partsOfDate = strDate.split("/");
        if(partsOfDate.length == 3)
        {
            //Get each element of the array
            mon = Integer.parseInt(partsOfDate[0]);
            date = Integer.parseInt(partsOfDate[1]);
            year = Integer.parseInt(partsOfDate[2]);

            //Check specifically validity of month, date and year values
            if ((mon > 0 && mon <13) &&
                    (date > 0 && date < 32) &&
                    (year > 1850 && year < 2020))
            {
                dateIsValid = true;
            }
        }
        return dateIsValid;
    }

    /************************************************************************************
     *
     *  The method register_user is called when Register button is clicked. It checks the
     *  values provided if they are valid, it gets the user details and bundles them to
     *  pass them to the MainActivity class. If the user logs in, the details will be checked
     *  against the values provided here. This will be only until the app is not closed.
     *
     *************************************************************************************/

    public void register_user(View view){

        String firstname, familyname, email, password, dob;

        if(!IfEmptyOrInvalid(registerLayout)) {

            //Register user if data is valid
            firstname = tbFirstName.getText().toString();
            familyname = tbFamilyName.getText().toString();
            email = tbEmail.getText().toString();
            password = tbPassword.getText().toString();
            dob = tbDOB.getText().toString();

            //Get the Shared Preferences
            pref = getApplicationContext().getSharedPreferences("CARD_QUIZ", 0); // 0 - for private mode
            editor = pref.edit();

            //Store data in Shared Preferences
            editor.putString("First_Name", firstname);
            editor.putString("Family_Name", familyname);
            editor.putString("Email", email);
            editor.putString("Password", password);
            editor.putString("DOB", dob);
            editor.commit();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}

/************************************ End of Register Class *************************************/
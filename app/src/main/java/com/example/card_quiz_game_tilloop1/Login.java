package com.example.card_quiz_game_tilloop1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Iterator;

import static android.text.TextUtils.isEmpty;

public class Login extends AppCompatActivity {

    ArrayList<TextInputLayout> loginLayout;
    TextInputEditText tbEmail, tbPassword;
    Button btnLogin;
    String reg_name, reg_fname, reg_email, reg_password;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginLayout = new ArrayList<>();
        loginLayout.add((TextInputLayout)findViewById(R.id.layout1));
        loginLayout.add((TextInputLayout)findViewById(R.id.layout2));
        tbEmail = (TextInputEditText) findViewById(R.id.tbEmail);
        tbPassword = (TextInputEditText)findViewById(R.id.tbPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        pref = getApplicationContext().getSharedPreferences("CARD_QUIZ", 0);
    }


    /************************************************************************************
     *
     *  IFEmpty method takes an array list of Text Input Layouts as argument and checks
     *  the fields for empty values.
     *
     *************************************************************************************/
    private boolean IfEmpty(ArrayList<TextInputLayout> layouts){
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
        }
        return isInvalid;
    }

    /************************************************************************************
     *
     *  The method login is called when Login button is clicked. It checks the
     *  values provided if they are not empty, it gets the user details and checks
     *  against the values received from the MainActivity. The values persist only until
     *  the app instance is running.
     *
     *************************************************************************************/
    public void login(View view){

        String email, password;
        Toast toast;

        if(!IfEmpty(loginLayout)) {

            //Get the details entered by user
            email = tbEmail.getText().toString();
            password = tbPassword.getText().toString();

            reg_email = pref.getString("Email", null);
            reg_password = pref.getString("Password",null);

            //Check if the entered values match the ones with which the user registered
            if(email.equals(reg_email) && password.equals(reg_password)) {

                //Login user if data is valid
                toast = Toast.makeText(getApplicationContext(), R.string.login_success, Toast.LENGTH_LONG);
            }
            else{
                //The details don't match
                toast = Toast.makeText( getApplicationContext(),R.string.login_fail, Toast.LENGTH_LONG);
            }
            toast.show();
        }
    }
}
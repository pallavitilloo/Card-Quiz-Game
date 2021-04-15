package com.example.card_quiz_game_tilloop1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnRegister, btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons shown on the activity screen will be listened for clicks
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //To identify who called this method, we need view.getid
        switch(v.getId()){

            case R.id.btnRegister:

                //If Register is clicked
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btnLogin:

                //If the user wants to login
                startActivity(new Intent(this, Login.class));
                break;

            default:
                break;
        }
    }
}
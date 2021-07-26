package com.quantum.mystreamingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreLogin extends AppCompatActivity {
    Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pre_login );

        signin=findViewById( R.id. appCompatButton);
        signup=findViewById( R.id. appCompatButton2);


        signup.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( PreLogin.this,SignUp.class ) );

            }
        } );

        signin.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( PreLogin.this,Login.class ) );
            }
        } );
    }
}
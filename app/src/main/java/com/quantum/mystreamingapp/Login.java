package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button signup,login,forgot_pass;
    EditText malil_et,pass_et;
    public FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );


        signup=findViewById( R.id.signup );
        malil_et=findViewById( R.id.mail_edit_txt );
        pass_et=findViewById( R.id.pass_edit_txt );

        login=findViewById( R.id.login );
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        forgot_pass=findViewById( R.id.forgotPassword );

        forgot_pass.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
              //  startActivity( new Intent(Login.this,ForgotPassword.class) );
            }
        } );



        login.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if(validatemail()&&validatpass()){
                    validate(malil_et.getText().toString(),pass_et.getText().toString());
                }
            }
        } );




        signup.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent i=new Intent (getApplicationContext(),SignUp.class);
                startActivity( i );
            }
        } );

    }

    private boolean validatemail(){

        String MAIL=malil_et.getText().toString();
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(MAIL.isEmpty()){

            return false;
        }else if(MAIL.matches( regex )){
            return  true;

        }else{
            Toast.makeText( this, "Incorrect mail", Toast.LENGTH_SHORT ).show( );

            return false;

        }

    }

    private boolean validatpass(){
        String PASSWORD=pass_et.getText().toString();

        if(PASSWORD.isEmpty()){
            Toast.makeText( this, "Password Can't be empty ", Toast.LENGTH_SHORT ).show( );

            return false;
        }else if(PASSWORD.length()>=6){

            return  true;

        }else{
            Toast.makeText( this, "Minimum 6 digit password", Toast.LENGTH_SHORT ).show( );

            return false;

        }

    }


    public void validate(String username,String userPassword){

        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(username,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailverification();

                }else {
                    progressDialog.cancel();
                    Toast.makeText(Login.this,"Login Failed,Incorrect Email or password" ,Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void checkEmailverification(){
        FirebaseUser firebaseUser =firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            startActivity( new Intent(Login.this,OfflineHome.class));
            DBquaries.load_userDetails();
        }else{
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }
}
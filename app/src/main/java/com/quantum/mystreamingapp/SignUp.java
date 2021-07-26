package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    TextInputLayout namelayout,password_layout,maillayout,re_password_layout;


    Button signUp;
    TextInputEditText phone_et;
    CountryCodePicker ccp;
    public FirebaseAuth firebase;

    private Dialog loadingDialog;
    public FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );


        namelayout=findViewById( R.id.namelayout );
        phone_et=findViewById( R.id.phone_et );
        maillayout=findViewById( R.id.mail_layout );
        password_layout=findViewById( R.id.password_layout );
        signUp= findViewById( R.id.sign_up);
        ccp=findViewById( R.id.ccp );
        re_password_layout=findViewById( R.id.re_password_layout );

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebase=FirebaseAuth.getInstance();
        ccp.setAutoDetectedCountry( true );

        loadingDialog= new Dialog( this);
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );


        signUp.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if(validatNmae()&&validatemail()&&validatpass()&&validatPhone()){
                    // String code=ccp.getSelectedCountryCode();
                    String name=namelayout.getEditText().getText().toString();
                    String mail=maillayout.getEditText().getText().toString();
                    String password=password_layout.getEditText().getText().toString();
                    String re_password=re_password_layout.getEditText().getText().toString();
                    String phoneNo=phone_et.getText().toString();


                    FirebaseAuth.getInstance().createUserWithEmailAndPassword( mail,password ).addOnCompleteListener( new OnCompleteListener<AuthResult>( ) {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingDialog.show();
                                Map<String,Object> userData =new HashMap<>(  );
                                userData.put( "fullname",name );
                                userData.put( "image","" );
                                userData.put( "mail",mail );
                                userData.put( "password",password );
                                userData.put( "phone",phoneNo );
                                userData.put( "previous_position",0 );
                                userData.put( "address_details","" );
                                userData.put( "address_type","" );



                                FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).set( userData ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Map<String,Object> listSize =new HashMap<>(  );
                                        listSize.put( "list_size",0 );
                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_ADDRESS").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );
                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_GROCERY_CARTITEMCOUNT").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );
                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_GROCERY_CARTLIST").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );
                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_FOLLOWER").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );
                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_FOLLOWING").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );
                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_GROCERY_ORDERS").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );

                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_STORE_ORDERS").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );
                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_LIKES").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );

                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_NOTIFICATION").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );

                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_SELECTED_COLOR").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                } );

                                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document("MY_GROCERY_WISHLIST").set( listSize )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        loadingDialog.cancel();
                                                        sendemailVerification();
                                                    }
                                                } );



                                    }
                                } );

                            }
                            else {
                                String error = task.getException( ).getMessage( );
                                Toast.makeText( SignUp.this, error, Toast.LENGTH_SHORT ).show( );
                                loadingDialog.cancel();


                            }
                        }
                    } );
               }




            }
        } );
    }

    private boolean validatemail(){

        String MAIL=maillayout.getEditText().getText().toString();
        maillayout.setErrorEnabled( true );
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(MAIL.isEmpty()){
            maillayout.setError( "Can't be empty " );

            return false;
        }else if(MAIL.matches( regex )){
            maillayout.setErrorEnabled( false );
            return  true;

        }else{
            maillayout.setError( "Incorrect mail" );
            return false;

        }

    }

    private boolean validatpass(){
        String PASSWORD=password_layout.getEditText().getText().toString();
        password_layout.setErrorEnabled( true );

        if(PASSWORD.isEmpty()){
            password_layout.setError( "Can't be empty " );

            return false;
        }else if(PASSWORD.length()>=6){
            password_layout.setErrorEnabled( false );

            return  validatrepass(PASSWORD);

        }else{
            password_layout.setError( "Minimum 6 digit password" );
            return false;

        }

    }

    private boolean validatrepass(String pass){
        String REPASSWORD=re_password_layout.getEditText().getText().toString();
        re_password_layout.setErrorEnabled( true );

        if(!REPASSWORD.equals(pass)){
            re_password_layout.setError( "Do not match " );

            return false;
        }else {
            re_password_layout.setErrorEnabled( false );
            return true;
        }


    }


    private boolean validatNmae(){
        String NAME=namelayout.getEditText().getText().toString();
        namelayout.setErrorEnabled( true );

        if(NAME.isEmpty()){
            namelayout.setError( "Can't be empty " );

            return false;
        }else
            namelayout.setErrorEnabled( false );
        return  true;



    }

    private boolean validatPhone(){
        String Phone=phone_et.getText().toString();


        if(Phone.isEmpty()){
            Toast.makeText( this, "Phone can't be empty", Toast.LENGTH_SHORT ).show( );

            return false;
        }else

            return  true;



    }

    private  void sendemailVerification(){

        loadingDialog.show();


        FirebaseUser firebaseUser=firebase.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(SignUp.this,"Verification mail sent!",Toast.LENGTH_SHORT).show();
                        firebase.signOut();
                        loadingDialog.cancel();
                        finish();
                        startActivity(new Intent(SignUp.this,Login.class));
                    }else {
                        Toast.makeText(SignUp.this,"Verification mail has not been sent!",Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }
    }
}
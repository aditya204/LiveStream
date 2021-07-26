package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Address extends AppCompatActivity {

    private EditText name, phone, alternatePhone, pin, details, city, state, landmark;
    private CheckBox home, office;
    private Button saveAddress;
    private static String type = "";
    private Dialog loadingDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_address );

        name = findViewById( R.id.add_address_name_et );
        phone = findViewById( R.id.add_address_number_et );
        alternatePhone = findViewById( R.id.add_address_alternate_no_et );
        pin = findViewById( R.id.add_address_pin_et );
        details = findViewById( R.id.add_address_details_et );
        city = findViewById( R.id.add_address_city_et );

        state = findViewById( R.id.add_address_state_et );
        landmark = findViewById( R.id.add_address_LandMark_et );
        home = findViewById( R.id.address_type_Home );
        office = findViewById( R.id.address_type_office );
        saveAddress = findViewById( R.id.save_address_btn );

        toolbar=findViewById( R.id.toolbar_editaddress );

        loadingDialog= new Dialog( Address.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();


        toolbar.setTitle( "Edit Address" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );

        final int position = getIntent( ).getIntExtra( "position", -1 );
        final int layout_code = getIntent( ).getIntExtra( "layout_code", -2 );


        if (layout_code == 1) {

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_ADDRESS" ).collection( "address_list" ).document( "address_" + position )
                    .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful( )) {

                        name.setText( task.getResult( ).get( "name" ).toString( ) );
                        phone.setText( task.getResult( ).get( "phone" ).toString( ) );
                        alternatePhone.setText( task.getResult( ).get( "altPhone" ).toString( ) );
                        pin.setText( task.getResult( ).get( "pin" ).toString( ) );
                        details.setText( task.getResult( ).get( "details" ).toString( ) );
                        city.setText( task.getResult( ).get( "city" ).toString( ) );
                        state.setText( task.getResult( ).get( "state" ).toString( ) );
                        landmark.setText( task.getResult( ).get( "landmark" ).toString( ) );

                        if (task.getResult( ).get( "type" ).toString( ).equals( "HOME" )) {
                            home.setChecked( true );
                            type = "HOME";

                        } else {
                            office.setChecked( true );
                            type = "OFFICE";
                        }
                        loadingDialog.dismiss();

                    }

                }
            } );
        }

        home.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (home.isChecked( )) {
                    type = "HOME";
                    office.setChecked( false );
                    home.setChecked( true );
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        home.setCompoundDrawableTintList( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ) );
                    }
                }
            }
        } );

        office.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (office.isChecked( )) {
                    type = "OFFICE";
                    home.setChecked( false );
                    office.setChecked( true );
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        office.setCompoundDrawableTintList( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ) );
                    }
                }
            }
        } );

        saveAddress.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                UploadAddress( layout_code,name.getText( ).toString( ), phone.getText( ).toString( ), alternatePhone.getText( ).toString( ),
                        pin.getText( ).toString( ), details.getText( ).toString( ), city.getText( ).toString( ), state.getText( ).toString( ),
                        landmark.getText( ).toString( ), type, position );

            }
        } );

        loadingDialog.dismiss();
    }

    private void UploadAddress(final int Layout_code, final String Name, final String Phone, final String AltPhone, final String Pin, final String Details, final String City, final String State, final String Landmak, final String Type, final int Position) {

        if (Name.isEmpty( ) || Phone.isEmpty( ) || Pin.isEmpty( ) || Details.isEmpty( ) || City.isEmpty( ) || State.isEmpty( ) || Type.isEmpty( )) {

            Toast.makeText( Address.this, "Enter all * details", Toast.LENGTH_SHORT ).show( );
        } else {
            loadingDialog.show();


            final Map<String, Object> Data = new HashMap<>( );
            Data.put( "name", Name );
            Data.put( "phone", Phone );
            Data.put( "pin", Pin );
            Data.put( "details", Details );
            Data.put( "city", City );
            Data.put( "state", State );
            Data.put( "type", Type );
            Data.put( "index", Position );
            Data.put( "address_details", Details + "," + Landmak + "," + City + "," + State + "," + Pin );
            Data.put( "is_visible" ,true );


            if (Landmak.isEmpty( )) {
                Data.put( "landmark", " " );
            } else {
                Data.put( "landmark", Landmak );
            }
            if (AltPhone.isEmpty( )) {
                Data.put( "altPhone", " " );
            } else {
                Data.put( "altPhone", AltPhone );
            }


            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_ADDRESS" ).collection( "address_list" ).document( "address_" + Position )
                    .set( Data ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful( )) {
                        Toast.makeText( Address.this, "Your Address is Added", Toast.LENGTH_SHORT ).show( );


                        if (Layout_code == 1) {
                            Intent intent = new Intent( Address.this, MyAddress.class );
                            startActivity( intent );
                            loadingDialog.dismiss();

                        }else {
                            Map<String, Object> UserData = new HashMap<>( );
                            UserData.put( "fullname", Name );
                            UserData.put( "phone", Phone );
                            UserData.put( "address_type", Type );
                            UserData.put( "previous_position", 1 );
                            UserData.put( "address_details", Details + ", " + Landmak + ", " + City + ", " + State + ", " + Pin );



                            if (AltPhone.isEmpty( )) {
                                UserData.put( "altPhone", " " );
                            } else {
                                UserData.put( "altPhone", AltPhone );
                            }

                            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                    .update( UserData ).addOnCompleteListener(
                                    new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful( )) {
                                                Intent intent = new Intent( Address.this, MyAddress.class );
                                                startActivity( intent );
                                                finish();

                                                Map<String, Object> UserData = new HashMap<>( );
                                                UserData.put( "list_size", Position + 1 );
                                                FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                                        .collection( "USER_DATA" ).document( "MY_ADDRESS" ).set( UserData )
                                                        .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                MyAddress.selectedAdd.setVisibility( View.VISIBLE );
                                                                loadingDialog.dismiss();
                                                            }
                                                        } );


                                            }
                                        }
                                    }
                            );

                        }
                    }

                }
            } );
        }

    }
}
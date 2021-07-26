package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.AddressAdapter;
import com.quantum.mystreamingapp.Models.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class MyAddress extends AppCompatActivity {
    private LinearLayout addnewAdd;
    private RecyclerView addRecycler;
    private Toolbar toolbar;
    private AddressAdapter addressAdapter;
    private List<AddressModel> addressModelList;
    public static int size=0;
    public static TextView selectedAdd;
    public static int selected_position=0;
    public static Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_address );

        addnewAdd=findViewById( R.id. add_new_address_LL);
        addRecycler=findViewById( R.id.my_address_recycler );
        toolbar=findViewById( R.id.toolbar_address );
        selectedAdd=findViewById( R.id.selected_address );

        loadingDialog= new Dialog( MyAddress.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();

        toolbar.setTitle( "My Address" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );

        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getUid() )
                .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if((long)task.getResult().get("previous_position")==0){
                        MyAddress.selectedAdd.setVisibility( View.GONE );
                    }
                    MyAddress.selectedAdd.setText( "selected Address no:"+ String.valueOf((long)task.getResult().get("previous_position")) );

                    selected_position=Integer.parseInt(String.valueOf( (long)task.getResult().get("previous_position")) );

                }
            }
        } );


        size=0;



        addressModelList=new ArrayList<>(  );
        addressAdapter=new AddressAdapter( addressModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        addRecycler.setLayoutManager( layoutManager );
        addRecycler.setAdapter( addressAdapter );



        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_ADDRESS" ).collection( "address_list" )
                .orderBy( "index", Query.Direction.DESCENDING ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){

                        loadingDialog.show();
                        size=size+1;
                        if((boolean)documentSnapshot.get( "is_visible" )) {
                            addressModelList.add( new AddressModel( documentSnapshot.get( "name" ).toString( ),
                                    documentSnapshot.get( "phone" ).toString( ),
                                    documentSnapshot.get( "type" ).toString( ),
                                    documentSnapshot.get( "address_details" ).toString( ),
                                    documentSnapshot.get( "altPhone" ).toString( ),
                                    size
                            ) );
                        }
                        loadingDialog.dismiss();
                    }

                    addressAdapter.notifyDataSetChanged();


                }
            }
        } );

        addnewAdd.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( MyAddress.this,Address.class );
                intent.putExtra( "position",size );
                startActivity( intent );
            }
        } );


        loadingDialog.dismiss();


    }


    @Override
    public void onRestart()
    { super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId( ) == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );



    }
}
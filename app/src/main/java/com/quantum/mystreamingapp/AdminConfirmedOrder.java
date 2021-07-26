package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.AdminOrderAdaptrer;
import com.quantum.mystreamingapp.Models.AdminOrderModel;

import java.util.ArrayList;
import java.util.List;

public class AdminConfirmedOrder extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView confirmOrderRecycler;
    private AdminOrderAdaptrer adminOrderAdaptrer;
    private List<AdminOrderModel> adminOrderModelList;
    TextView noorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_confirmed_order );
        toolbar=findViewById( R.id.confirm_toolbar );
        confirmOrderRecycler=findViewById( R.id.confirmOrderRecycler );
        noorder=findViewById( R.id.no_orders );


        toolbar.setTitle( "Confirm Order" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        adminOrderModelList = new ArrayList<>( );

        adminOrderAdaptrer = new AdminOrderAdaptrer( adminOrderModelList );
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager( this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        confirmOrderRecycler.setLayoutManager( linearLayoutManager );
        confirmOrderRecycler.setAdapter( adminOrderAdaptrer );


        int size=DBquaries.admin_OrderList.size();

        for(int i=0;i<size;i++){

            String id=DBquaries.admin_OrderList.get( i );
            FirebaseFirestore.getInstance().collection( "ORDERS").document( id ).get()
                    .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){

                                boolean is_paid=(boolean) task.getResult().get( "is_paid" );
                                if(is_paid){
                                    adminOrderModelList.add( new AdminOrderModel(  task.getResult().get( "id" ).toString(),
                                            task.getResult().get( "time" ).toString(),
                                            task.getResult().get( "otp" ).toString(),
                                            task.getResult().get( "grand_total" ).toString(),
                                            (boolean) task.getResult().get( "is_paid" ),
                                            (boolean) task.getResult().get( "is_pickUp" )
                                    ) );



                                    adminOrderAdaptrer.notifyDataSetChanged();

                                }

                            }
                            if(adminOrderModelList.size()==0){
                                noorder.setVisibility( View.VISIBLE );
                                confirmOrderRecycler.setVisibility( View.GONE );

                            }else {
                                confirmOrderRecycler.setVisibility( View.VISIBLE );

                                noorder.setVisibility( View.GONE );
                            }
                            adminOrderAdaptrer.notifyDataSetChanged();


                        }
                    } );



        }
        if(adminOrderModelList.size()==0){
            noorder.setVisibility( View.VISIBLE );
            confirmOrderRecycler.setVisibility( View.GONE );

        }else {
            confirmOrderRecycler.setVisibility( View.VISIBLE );

            noorder.setVisibility( View.GONE );
        }

    }
}
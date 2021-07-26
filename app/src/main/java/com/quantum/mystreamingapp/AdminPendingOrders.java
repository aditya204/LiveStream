package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
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

public class AdminPendingOrders extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView pendingOrderRecycler;
    private AdminOrderAdaptrer adminOrderAdaptrer;
    private List<AdminOrderModel> adminOrderModelList;
    TextView noOrders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_pending_orders );

        toolbar=findViewById( R.id.pending_toolbar );
        pendingOrderRecycler=findViewById( R.id.pendingOrderRecycler );
        noOrders=findViewById( R.id.no_orders );



        adminOrderModelList = new ArrayList<>( );

        adminOrderAdaptrer = new AdminOrderAdaptrer( adminOrderModelList );
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager( this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        pendingOrderRecycler.setLayoutManager( linearLayoutManager );
        pendingOrderRecycler.setAdapter( adminOrderAdaptrer );

        toolbar.setTitle( "Pending Order" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );



        int size=DBquaries.admin_OrderList.size();

        if(size==0){
            noOrders.setVisibility( View.VISIBLE );

        }else {
            noOrders.setVisibility( View.GONE );
        }


        for(int i=0;i<size;i++){

            String id=DBquaries.admin_OrderList.get( i );
            FirebaseFirestore.getInstance().collection( "ORDERS").document( id ).get()
                    .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){

                                boolean is_paid=(boolean) task.getResult().get( "is_paid" );
                                if(!is_paid){
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
                            adminOrderAdaptrer.notifyDataSetChanged();


                        }
                    } );



        }





    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){

        }
        return super.onOptionsItemSelected( item );
    }
}
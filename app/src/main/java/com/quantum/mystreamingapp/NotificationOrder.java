package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.AddressAdapter;
import com.quantum.mystreamingapp.Adapters.NotificationOrderAdapter;
import com.quantum.mystreamingapp.Models.NotificationModelClass;

import java.util.ArrayList;
import java.util.List;

public class NotificationOrder extends AppCompatActivity {

    RecyclerView notification_order;
    NotificationOrderAdapter notificationOrderAdapter;
    List<NotificationModelClass> notificationModelClass;
    private Toolbar toolbar;
    LinearLayout order,alert,deals;
    ImageButton home,search,profile,live;
    TextView no_txt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notification_order );

        home=findViewById( R.id.profile_goto_Home );
        search=findViewById( R.id.profile_goto_search );
        profile=findViewById( R.id.profile_goto_profile );
        live=findViewById( R.id.profile_goto_live );
        no_txt=findViewById( R.id.no_notification_txt);

        notification_order=findViewById( R.id.notificcation_order_recycler );

        toolbar=findViewById( R.id.toolbar_notification );



        toolbar.setTitle( "Notification" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );


        order=findViewById( R.id.notification_order_order );
        deals=findViewById( R.id.notification_order_deals );
        alert=findViewById( R.id.notification_order_alert );


        order.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationOrder.this,NotificationOrder.class ) );
                finish();
            }
        } );

        deals.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationOrder.this,NotificationDeal.class ) );
                finish();
            }
        } );

        alert.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationOrder.this,NotificationAlert.class ) );
                finish();
            }
        } );

        home.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationOrder.this,OfflineHome.class ) );
                finish();
            }
        } );

        profile.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationOrder.this,Profile.class ) );
                finish();
            }
        } );

        live.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationOrder.this,Live.class ) );
                finish();
            }
        } );

        search.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationOrder.this,Search.class ) );
                finish();
            }
        } );



        notificationModelClass =new ArrayList<>(  );


        notificationOrderAdapter=new NotificationOrderAdapter( notificationModelClass );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        notification_order.setLayoutManager( layoutManager );
        notification_order.setAdapter( notificationOrderAdapter );

        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).orderBy( "order_id" )
                .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc:task.getResult()){
                        notificationModelClass.add( new NotificationModelClass(
                                doc.get( "product_image" ).toString( ),
                                doc.get( "header" ).toString( ),
                                doc.get( "time" ).toString( ),
                                doc.get( "order_id" ).toString( ),
                                doc.get( "product_name" ).toString( ),
                                doc.get( "content" ).toString( ),
                                doc.get( "product_id" ).toString( ),
                                doc.getId(),
                                (boolean)doc.get( "is_readed" ),
                                (boolean)doc.get( "is_for_Seller" )
                        ) );

                        if(notificationModelClass.size()==0){
                            no_txt.setVisibility( View.VISIBLE );
                        }else {
                            no_txt.setVisibility( View.GONE );
                        }
                        notificationOrderAdapter.notifyDataSetChanged();

                    }
                }

            }
        } );



//        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).orderBy( "order_id" ).addSnapshotListener( new EventListener<QuerySnapshot>( ) {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
//                if(error!=null){
//                    Toast.makeText( NotificationOrder.this, error.getMessage(), Toast.LENGTH_SHORT ).show( );
//
//                }else {
//                    notificationModelClass.clear();
//                    for (QueryDocumentSnapshot doc : documentSnapshots) {
//                        if (doc.exists( )) {
//
//
//
//
//                        }
//                    }
//                }
//
//            }
//        } );




    }
}
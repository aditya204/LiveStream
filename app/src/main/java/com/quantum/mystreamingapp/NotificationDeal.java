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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.NotificationDealAdapter;
import com.quantum.mystreamingapp.Models.NotificationAlertModel;
import com.quantum.mystreamingapp.Models.NotificationDealsModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationDeal extends AppCompatActivity {


    RecyclerView notification_order;
    NotificationDealAdapter notificationDealAdapter;
    List<NotificationDealsModel> notificationAlertModelList;
    private Toolbar toolbar;
    LinearLayout order,alert,deals;
    ImageButton home,search,profile,live;
    TextView noTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notification_deal );

        notification_order=findViewById( R.id.notificcation_deal_recycler );
        noTxt=findViewById( R.id.no_notification_txt );
        toolbar=findViewById( R.id.toolbar_notification );

        order=findViewById( R.id.notification_deals_order );
        deals=findViewById( R.id.notification_deals_deals );
        alert=findViewById( R.id.notification_deals_alert );

        home=findViewById( R.id.profile_goto_Home );
        search=findViewById( R.id.profile_goto_search );
        profile=findViewById( R.id.profile_goto_profile );
        live=findViewById( R.id.profile_goto_live );


        toolbar.setTitle( "Notification" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );


        home.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationDeal.this,OfflineHome.class ) );
                finish();
            }
        } );

        profile.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationDeal.this,Profile.class ) );
                finish();
            }
        } );

        live.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationDeal.this,Live.class ) );
                finish();
            }
        } );

        search.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( NotificationDeal.this,Search.class ) );
                finish();
            }
        } );



        order.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationDeal.this,NotificationOrder.class ) );
                finish();
            }
        } );
        deals.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationDeal.this,NotificationDeal.class ) );
                finish();
            }
        } );

        alert.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationDeal.this,NotificationAlert.class ) );
                finish();
            }
        } );

        notificationAlertModelList =new ArrayList<>(  );


        notificationDealAdapter =new NotificationDealAdapter( notificationAlertModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        notification_order.setLayoutManager( layoutManager );
        notification_order.setAdapter( notificationDealAdapter );

        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "DEALS" ).orderBy( "order_time" )
                .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc:task.getResult()){
                        notificationAlertModelList.add( new NotificationDealsModel(
                                doc.get( "image" ).toString( ),
                                doc.get( "header" ).toString( ),
                                doc.get( "time" ).toString( ),
                                doc.get( "content" ).toString( ),
                                doc.get( "seller_id" ).toString( ),
                                doc.getId(),
                                (boolean)doc.get( "is_readed" ),
                                (boolean)doc.get( "is_timmer" )
                                ,doc.get( "product_id" ).toString( )

                        ) );
                        notificationDealAdapter.notifyDataSetChanged();

                    }
                    if(notificationAlertModelList.size()==0){
                        noTxt.setVisibility( View.VISIBLE );
                    }else {
                        noTxt.setVisibility( View.GONE );
                    }

                    notificationDealAdapter.notifyDataSetChanged();

                }
            }
        } );

    }
}
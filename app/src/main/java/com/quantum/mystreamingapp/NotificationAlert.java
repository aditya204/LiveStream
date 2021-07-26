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
import com.quantum.mystreamingapp.Adapters.NotificationAlertAdapter;
import com.quantum.mystreamingapp.Adapters.NotificationOrderAdapter;
import com.quantum.mystreamingapp.Models.NotificationAlertModel;
import com.quantum.mystreamingapp.Models.NotificationModelClass;

import java.util.ArrayList;
import java.util.List;

public class NotificationAlert extends AppCompatActivity {


    RecyclerView notification_order;
    NotificationAlertAdapter notificationAlertAdapter;
    List<NotificationAlertModel> notificationAlertModelList;
    private Toolbar toolbar;
    LinearLayout order,alert,deals;
    ImageButton home,search,profile,live;
    TextView no_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notification_alert );

        notification_order=findViewById( R.id.notificcation_alert_recycler );
        toolbar=findViewById( R.id.toolbar_notification );
        no_txt = findViewById( R.id.no_notification_txt );

        order=findViewById( R.id.notification_alert_order );
        deals=findViewById( R.id.notification_alert_deals );
        alert=findViewById( R.id.notification_alert_alert );


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
                startActivity( new Intent( NotificationAlert.this,OfflineHome.class ) );
                finish();
            }
        } );

        profile.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationAlert.this,Profile.class ) );
                finish();
            }
        } );

        live.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationAlert.this,Live.class ) );
                finish();
            }
        } );

        search.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( NotificationAlert.this,Search.class ) );
                finish();
            }
        } );


        order.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationAlert.this,NotificationOrder.class ) );
                finish();
            }
        } );
        deals.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationAlert.this,NotificationDeal.class ) );
                finish();
            }
        } );

        alert.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( NotificationAlert.this,NotificationAlert.class ) );
                finish();
            }
        } );




        notificationAlertModelList =new ArrayList<>(  );
        notificationAlertAdapter=new NotificationAlertAdapter( notificationAlertModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        notification_order.setLayoutManager( layoutManager );
        notification_order.setAdapter( notificationAlertAdapter );

        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" ).orderBy( "order_time" )
                .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc:task.getResult()){
                        notificationAlertModelList.add( new NotificationAlertModel(
                                doc.get( "follower_image" ).toString( ),
                                doc.get( "header" ).toString( ),
                                doc.get( "time" ).toString( ),
                                doc.get( "content" ).toString( ),
                                doc.get( "follower_id" ).toString( ),
                                doc.getId(),
                                (boolean)doc.get( "is_readed" )

                        ) );
                        notificationAlertAdapter.notifyDataSetChanged();

                    }
                    notificationAlertAdapter.notifyDataSetChanged();
                    if(notificationAlertModelList.size()==0){
                        no_txt.setVisibility( View.VISIBLE );
                    }else {
                        no_txt.setVisibility( View.GONE );
                    }

                }

            }
        } );





    }
}
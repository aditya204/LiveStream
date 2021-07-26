package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.LiveCategoryAdapter;
import com.quantum.mystreamingapp.Models.LiveCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class Live extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<LiveCategoryModel> liveCategoryModelList;
    LiveCategoryAdapter liveCategoryAdapter;
    private Toolbar toolbar;

    ImageButton home,search,notification,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_live );

        recyclerView=findViewById( R.id.live_category_recycler );
        toolbar=findViewById( R.id.live_categories_toolbar );
        home=findViewById( R.id.live_goto_home_main );
        notification=findViewById( R.id.live_goto_notification );
        profile=findViewById( R.id.live_goto_profile );
        search=findViewById( R.id.live_goto_Search );

        home.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Live.this,OfflineHome.class ) );
                finish();
            }
        } );

        notification.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new  Intent (Live.this,NotificationOrder.class));

            }
        } );

        profile.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Live.this,Profile.class ) );
                finish();
            }
        } );

        search.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Live.this,Search.class ) );
                finish();
            }
        } );



        toolbar.setTitle( "Live" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

        liveCategoryModelList=new ArrayList<>(  );
        liveCategoryAdapter=new LiveCategoryAdapter( liveCategoryModelList );
        GridLayoutManager layoutManager1 = new GridLayoutManager( this,2 );
        recyclerView.setLayoutManager( layoutManager1 );
        recyclerView.setAdapter( liveCategoryAdapter );


        FirebaseFirestore.getInstance().collection( "LIVE_IDS" ).orderBy( "index" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        liveCategoryModelList.add( new LiveCategoryModel(
                                documentSnapshot.get( "name" ).toString(),
                                documentSnapshot.get( "image" ).toString()
                        ) );
                    }
                    liveCategoryAdapter.notifyDataSetChanged();


                }
            }
        } );







    }
}
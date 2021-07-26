package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.PlayerAdapter;
import com.quantum.mystreamingapp.Models.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class player_recycler extends AppCompatActivity {
    RecyclerView recycler;
    PlayerAdapter playerAdapter;
    List<PlayerModel> playerModelList=new ArrayList<>(  );
    TextView statusTXT;
    String category="";
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_player_recycler );

        statusTXT=findViewById( R.id.statusTXT );
        recycler=findViewById( R.id.recyclerView );
        toolbar=findViewById( R.id.player_recycler_toolbar );
        category=getIntent().getStringExtra( "category" );



        toolbar.setTitle( "Live Sessions" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );

        playerAdapter=new PlayerAdapter( playerModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        recycler.setLayoutManager( layoutManager );
        recycler.setAdapter( playerAdapter );

        FirebaseFirestore.getInstance().collection( "LIVE_IDS" ).document( category ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String nmae=task.getResult().get( "name" ).toString();
                    toolbar.setTitle( nmae );

                }
            }
        } );

        FirebaseFirestore.getInstance().collection( "LIVE_IDS" ).document( category ).collection( "LISTS" ).orderBy( "time", Query.Direction.DESCENDING ).get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){

                                playerModelList.add( new PlayerModel( documentSnapshot.get( "stream_id" ).toString(),
                                        documentSnapshot.get( "display_picture" ).toString(),
                                        documentSnapshot.get( "user_name" ).toString(),
                                        documentSnapshot.get( "date_time" ).toString(),
                                        documentSnapshot.get( "user_image" ).toString(),
                                        documentSnapshot.get( "product_id" ).toString()
                                ) );

                                if(playerModelList.size()==0){
                                    recycler.setVisibility( View.GONE );
                                    statusTXT.setVisibility( View.VISIBLE );
                                }else {
                                    recycler.setVisibility( View.VISIBLE );
                                    statusTXT.setVisibility( View.GONE );
                                }






                            }
                            if(playerModelList.size()==0){
                                recycler.setVisibility( View.GONE );
                                statusTXT.setVisibility( View.VISIBLE );
                            }
                            else {
                                recycler.setVisibility( View.VISIBLE );
                                statusTXT.setVisibility( View.GONE );
                            }
                            playerAdapter.notifyDataSetChanged();


                        }
                    }
                } );









    }


}
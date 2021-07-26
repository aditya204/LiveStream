package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.HomeCategoryAdapter;
import com.quantum.mystreamingapp.Adapters.HomeProductAdapter;
import com.quantum.mystreamingapp.Models.HomeCategoryModel;
import com.quantum.mystreamingapp.Models.HomeProductModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeMain extends AppCompatActivity {

    RecyclerView recyclerView,timer_recycler;
    private HomeCategoryAdapter homeCategoryAdapter;
    private List<HomeCategoryModel> homeCategoryModelList;
    private Toolbar toolbar;

    HomeProductAdapter homeProductAdapter;
    List<HomeProductModel> homeProductModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_main );
        toolbar=findViewById( R.id.toolbar_home_main );

        toolbar.setTitle( "Countdown" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );


        recyclerView=findViewById( R.id.home_category_recycler );
        timer_recycler=findViewById( R.id.timer_recycler );


        homeCategoryModelList=new ArrayList<>(  );
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!","https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(2).jpg?alt=media&token=376e63e2-f547-4231-ba90-6c671c73ddb7" ) );
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!","https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(1).jpg?alt=media&token=38706c6a-5acf-42d4-820a-01328cc0c978" ) );
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!","https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(2).jpg?alt=media&token=376e63e2-f547-4231-ba90-6c671c73ddb7") );
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!", "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(3).jpg?alt=media&token=7f36ff75-3c7b-47d8-a2ba-053c0c1571c1" ));
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!","https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(1).jpg?alt=media&token=38706c6a-5acf-42d4-820a-01328cc0c978" ) );
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!", "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(2).jpg?alt=media&token=376e63e2-f547-4231-ba90-6c671c73ddb7") );
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!", "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(2).jpg?alt=media&token=376e63e2-f547-4231-ba90-6c671c73ddb7") );
        homeCategoryModelList.add( new HomeCategoryModel( "NAME_!", "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/download%20(1).jpg?alt=media&token=38706c6a-5acf-42d4-820a-01328cc0c978") );




        homeCategoryAdapter=new HomeCategoryAdapter( homeCategoryModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.HORIZONTAL );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( homeCategoryAdapter );
        homeCategoryAdapter.notifyDataSetChanged();



        homeProductModelList= new ArrayList<>(  );
        homeProductAdapter=new HomeProductAdapter( homeProductModelList );
        GridLayoutManager layoutManager1 = new GridLayoutManager( this,2 );
        layoutManager1.setOrientation( RecyclerView.VERTICAL );
        timer_recycler.setLayoutManager( layoutManager1 );
        timer_recycler.setAdapter( homeProductAdapter );


        FirebaseFirestore.getInstance().collection( "PRODUCTS" ).orderBy( "name" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        boolean is_timmer=(boolean)documentSnapshot.get( "is_timmer" );

                        if(is_timmer)
                        {

                            homeProductModelList.add( new HomeProductModel(  documentSnapshot.get("name").toString(),
                                    documentSnapshot.get("price").toString(),
                                    documentSnapshot.getTimestamp("time").toDate(),
                                    documentSnapshot.get("image_00").toString(),
                                    documentSnapshot.getId()
                                    ) );

                        }




                    }
                    homeProductAdapter.notifyDataSetChanged();


                }
            }
        } );

    }
}
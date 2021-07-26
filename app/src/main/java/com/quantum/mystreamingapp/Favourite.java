package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.GroceryProductAdapter;
import com.quantum.mystreamingapp.Adapters.HomeProductAdapter;
import com.quantum.mystreamingapp.Models.GroceryProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Favourite extends AppCompatActivity {
    private Dialog loadingDialog;
    private Toolbar toolbar;
    GroceryProductAdapter groceryProductAdapter;
    List<GroceryProductModel> groceryProductModelList;
    RecyclerView wish_recycler;
    TextView no_item_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_favourite );


        wish_recycler = findViewById( R.id.wish_recycler );
        toolbar = findViewById( R.id.wish_toolbar );
        no_item_txt = findViewById( R.id.no_txt );

        loadingDialog = new Dialog( Favourite.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow( ).setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );


        int size = DBquaries.grocery_wishList.size( );

        groceryProductModelList = new ArrayList<>( );
        groceryProductAdapter = new GroceryProductAdapter( groceryProductModelList );
        GridLayoutManager layoutManager1 = new GridLayoutManager( this, 2 );
        layoutManager1.setOrientation( RecyclerView.VERTICAL );
        wish_recycler.setLayoutManager( layoutManager1 );
        wish_recycler.setAdapter( groceryProductAdapter );

        int layout_code = getIntent( ).getIntExtra( "layout_code", -1 );

        if (layout_code == 1) {
            String my_uid = getIntent( ).getStringExtra( "my_uid" );
            toolbar.setTitle( "All Products" );
            setSupportActionBar( toolbar );
            toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

            FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).orderBy( "name" ).get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful( )) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {
                            if (my_uid.equals( documentSnapshot.get( "owner_id" ).toString( ) )) {

                                groceryProductModelList.add( new GroceryProductModel( Objects.requireNonNull( documentSnapshot.get( "image_00" ) ).toString( )
                                        , Objects.requireNonNull( documentSnapshot.get( "name" ) ).toString( )
                                        , documentSnapshot.get( "cut_price" ).toString( )
                                        , documentSnapshot.get( "offer" ).toString( )
                                        , documentSnapshot.get( "price" ).toString( )
                                        , documentSnapshot.get( "rating" ).toString( )
                                        , documentSnapshot.get( "review_count" ).toString( )
                                        , (long) documentSnapshot.get( "in_stock" )
                                        , documentSnapshot.getId( )
                                        , documentSnapshot.get( "relevant_tag" ).toString( ),
                                        documentSnapshot.getTimestamp( "time" ).toDate( ),
                                        (boolean) documentSnapshot.get( "is_timmer" ) ) );


                            }


                        }
                        groceryProductAdapter.notifyDataSetChanged( );


                    }
                }
            } );


        } else {

            if (size == 0) {
                wish_recycler.setVisibility( View.GONE );
                no_item_txt.setVisibility( View.VISIBLE );

            } else {
                wish_recycler.setVisibility( View.VISIBLE );
                no_item_txt.setVisibility( View.GONE );

            }
            toolbar.setTitle( "Wish List" );
            setSupportActionBar( toolbar );
            toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
            for (int i = 0; i < size; i++) {
                String id = DBquaries.grocery_wishList.get( i );

                FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( id ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful( )) {
                            groceryProductModelList.add( new GroceryProductModel( Objects.requireNonNull( task.getResult( ).get( "image_00" ) ).toString( )
                                    , Objects.requireNonNull( task.getResult( ).get( "name" ) ).toString( )
                                    , task.getResult( ).get( "cut_price" ).toString( )
                                    , task.getResult( ).get( "offer" ).toString( )
                                    , task.getResult( ).get( "price" ).toString( )
                                    , task.getResult( ).get( "rating" ).toString( )
                                    , task.getResult( ).get( "review_count" ).toString( )
                                    , (long) task.getResult( ).get( "in_stock" )
                                    , task.getResult( ).getId( )
                                    , task.getResult( ).get( "relevant_tag" ).toString( ),
                                    task.getResult( ).getTimestamp( "time" ).toDate( ),
                                    (boolean) task.getResult( ).get( "is_timmer" ) ) );





                        }
                        groceryProductAdapter.notifyDataSetChanged( );


                    }
                } );

                groceryProductAdapter.notifyDataSetChanged( );


            }
            groceryProductAdapter.notifyDataSetChanged( );
        }


    }
}
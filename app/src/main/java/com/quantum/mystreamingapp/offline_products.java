package com.quantum.mystreamingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.quantum.mystreamingapp.Adapters.GroceryProductAdapter;
import com.quantum.mystreamingapp.Models.GroceryProductModel;

import java.util.ArrayList;
import java.util.List;

public class offline_products extends AppCompatActivity {

    RecyclerView product_recycler;
    public static GroceryProductAdapter groceryProductAdapter;
    private List<GroceryProductModel> groceryProductModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_offline_products );

        product_recycler=findViewById( R.id.grocery_product_recycler );


        toolbar=findViewById( R.id.product_toolbar );
        toolbar.setTitle( "Products");
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#000000" ) );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );


        groceryProductModel = new ArrayList<>( );


        groceryProductAdapter = new GroceryProductAdapter( groceryProductModel );
        GridLayoutManager gridLayoutManager = new GridLayoutManager( getApplicationContext( ), 2 );
        product_recycler.setLayoutManager( gridLayoutManager );
        product_recycler.setAdapter( groceryProductAdapter );
        groceryProductAdapter.notifyDataSetChanged();

    }
}
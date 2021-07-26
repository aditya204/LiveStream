package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.SearchProductNameAdapter;
import com.quantum.mystreamingapp.Adapters.SearchTagsAdapter;
import com.quantum.mystreamingapp.Models.SearchTagsModels;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {


    private SearchView searchView;
    private RecyclerView product_recycler;
    private RecyclerView tag_recycler;
    private SearchProductNameAdapter searchProductNameAdapter;
    private SearchTagsAdapter searchtagAdapter;
    private List<SearchTagsModels> searchTagsModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );

        searchView= findViewById( R.id.search_view );
        product_recycler=findViewById( R.id.search_product_name_recycler );
        tag_recycler=findViewById( R.id.search_product_tag_recycler );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchView.setElevation( (float) 5.0 );
        }


        final List<String> previousSearch = new ArrayList<>( DBquaries.product_list );

        searchProductNameAdapter = new SearchProductNameAdapter( previousSearch);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( Search.this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        product_recycler.setLayoutManager( linearLayoutManager );
        product_recycler.setAdapter( searchProductNameAdapter );
        searchProductNameAdapter.notifyDataSetChanged();


        searchTagsModels=new ArrayList<>(  );
        searchtagAdapter = new SearchTagsAdapter( searchTagsModels);
        LinearLayoutManager LayoutManager=new LinearLayoutManager( Search.this );
        LayoutManager.setOrientation( RecyclerView.HORIZONTAL );
        tag_recycler.setLayoutManager( LayoutManager );
        tag_recycler.setAdapter( searchtagAdapter );


        FirebaseFirestore.getInstance().collection( "TAGS" ).orderBy( "tag" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        searchTagsModels.add( new SearchTagsModels( documentSnapshot.get( "tag" ).toString().toLowerCase().trim() ) );
                    }
                    searchtagAdapter.notifyDataSetChanged();


                }
            }
        } );

        searchtagAdapter.notifyDataSetChanged();





        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener( ) {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Intent intent=new Intent( Search.this, SearchedProduct.class );
                intent.putExtra( "tag_string", s);
                startActivity( intent );

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                searchProductNameAdapter.getFilter().filter( s );
                return false;
            }
        } );


    }
}
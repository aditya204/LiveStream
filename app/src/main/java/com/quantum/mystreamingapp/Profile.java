package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quantum.mystreamingapp.Adapters.GroceryProductAdapter;
import com.quantum.mystreamingapp.Adapters.HomeProductAdapter;
import com.quantum.mystreamingapp.Models.GroceryProductModel;
import com.quantum.mystreamingapp.Models.HomeProductModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    GroceryProductAdapter groceryProductAdapter;
    List<GroceryProductModel> groceryProductModelList;
    RecyclerView timer_recycler;
    private ScrollView scrollView;
    private Toolbar toolbar;
    ImageView edit_profile, profile_Image;
    private static final int PICK_IMAGE_REQUEST = 1;

    ImageButton home, search, notification, live, to_deliver, to_review, to_recieve, to_ship;
    String my_uid = "";
    Uri mImageUri = null;
    private StorageReference mStorageRef;

    private Dialog loadingDialog;

    LinearLayout orders_image_ll, orders_name_ll, products_ll;
    TextView followers_count, following_count, likes_count, seeAll,no_products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );


        toolbar = findViewById( R.id.toolbar_profile );

        toolbar.setTitle( "Profile" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );

        home = findViewById( R.id.profile_goto_Home );
        search = findViewById( R.id.profile_goto_search );
        notification = findViewById( R.id.profile_goto_notification );
        live = findViewById( R.id.profile_goto_live );
        to_deliver = findViewById( R.id.to_deliver );
        to_ship = findViewById( R.id.to_ship );
        to_recieve = findViewById( R.id.to_recieve );
        to_review = findViewById( R.id.to_review );
        edit_profile = findViewById( R.id.edit_profile_image );
        profile_Image = findViewById( R.id.roundedImageView );
        no_products=findViewById( R.id.no_item_txt );
        mStorageRef = FirebaseStorage.getInstance( ).getReference( "USER_PROFILE_IMAGE" );

        orders_image_ll = findViewById( R.id.orders_image_ll );
        orders_name_ll = findViewById( R.id.orders_name_ll );
        followers_count = findViewById( R.id.followers_count );
        following_count = findViewById( R.id.following_count );
        likes_count = findViewById( R.id.likes_count );
        products_ll = findViewById( R.id.products_LL );
        seeAll = findViewById( R.id.see_all_txt );


        callOnClick( );


        int layout_code = getIntent( ).getIntExtra( "layout_code", -1 );

        if (layout_code == 1) {
            my_uid = getIntent( ).getStringExtra( "owner_id" );
            orders_image_ll.setVisibility( View.GONE );
            orders_name_ll.setVisibility( View.GONE );
            edit_profile.setVisibility( View.GONE );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( my_uid ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful( )) {
                        String image = task.getResult( ).get( "image" ).toString( );
                        Glide.with( Profile.this ).load( image ).into( profile_Image );
                    }
                }
            } );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_FOLLOWING" )
                    .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful( )) {
                        following_count.setText( String.valueOf( (long) task.getResult( ).get( "list_size" ) ) );

                    }
                }
            } );
            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_FOLLOWER" )
                    .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful( )) {
                        followers_count.setText( String.valueOf( (long) task.getResult( ).get( "list_size" ) ) );

                    }
                }
            } );
            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_LIKES" )
                    .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful( )) {
                        likes_count.setText( String.valueOf( (long) task.getResult( ).get( "list_size" ) ) );

                    }
                }
            } );

        } else {


            likes_count.setText( String.valueOf( DBquaries.MY_LIKES ) );
            following_count.setText( String.valueOf( DBquaries.following_list.size( ) ) );
            followers_count.setText( String.valueOf( DBquaries.follower_list.size( ) ) );
            my_uid = FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( );
            Glide.with( Profile.this ).load( DBquaries.USER_IMAGE ).into( profile_Image );

        }


        loadingDialog = new Dialog( Profile.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow( ).setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );


        timer_recycler = findViewById( R.id.recycler_view111 );
        scrollView = findViewById( R.id.scroll_view_profile );
        scrollView.setSmoothScrollingEnabled( true );
        scrollView.smoothScrollTo( 0, 0 );

        SimpleDateFormat formatter = new SimpleDateFormat( "HH:mm:ss" );
        Date date = new Date( );
        Calendar c1 = Calendar.getInstance( );
        c1.setTime( date );


        groceryProductModelList = new ArrayList<>( );
        groceryProductAdapter = new GroceryProductAdapter( groceryProductModelList );
        GridLayoutManager layoutManager1 = new GridLayoutManager( this, 2 );
        layoutManager1.setOrientation( RecyclerView.VERTICAL );
        timer_recycler.setLayoutManager( layoutManager1 );
        timer_recycler.setAdapter( groceryProductAdapter );
        timer_recycler.setNestedScrollingEnabled( false );

        seeAll.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( Profile.this,Favourite.class);
                intent.putExtra( "layout_code",1 );
                intent.putExtra( "my_uid",my_uid );
                startActivity( intent );

            }
        } );


        final int[] count = {0};

        FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).orderBy( "name" ).get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful( )) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {
                        if (my_uid.equals( documentSnapshot.get( "owner_id" ).toString( ) )) {
                            count[0] = count[0] +1;
                            if(count[0]<5) {

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


                    }
                    groceryProductAdapter.notifyDataSetChanged( );

                    if (count[0] == 0) {
                        products_ll.setVisibility( View.GONE );
                        timer_recycler.setVisibility( View.GONE );
                        no_products.setVisibility( View.VISIBLE );
                    } else if (count[0] > 4) {
                        seeAll.setVisibility( View.VISIBLE );
                    }

                }
            }
        } );


    }

    private void callOnClick() {


        edit_profile.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openFileChooser( );

            }
        } );


        to_deliver.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Profile.this, AdminConfirmedOrder.class ) );
            }
        } );

        to_review.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Profile.this, Orders.class ) );
            }
        } );

        to_ship.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Profile.this, AdminPendingOrders.class ) );
            }
        } );

        to_recieve.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Profile.this, Orders.class ) );
            }
        } );


        home.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Profile.this, OfflineHome.class ) );
                finish( );
            }
        } );

        notification.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Profile.this, NotificationOrder.class ) );
                finish( );
            }
        } );

        live.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Profile.this, Live.class ) );
                finish( );
            }
        } );

        search.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( Profile.this, Search.class ) );
                finish( );
            }
        } );

    }

    private void openFileChooser() {

        Intent intent = new Intent( );
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( intent, PICK_IMAGE_REQUEST );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData( ) != null) {


            mImageUri = data.getData( );
            profile_Image.setImageURI( mImageUri );

            uploadFile( );

        }
    }

    private String getFileExtention(Uri uri) {
        ContentResolver cR = getContentResolver( );
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton( );
        return mimeTypeMap.getExtensionFromMimeType( cR.getType( uri ) );
    }

    public void uploadFile() {

        if (mImageUri != null) {
            loadingDialog.show( );


            final StorageReference fileref = mStorageRef.child( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) ).child( System.currentTimeMillis( ) + "." + getFileExtention( mImageUri ) );

            fileref.putFile( mImageUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    taskSnapshot.getMetadata( ).getReference( ).getDownloadUrl( ).addOnSuccessListener( new OnSuccessListener<Uri>( ) {
                        @Override
                        public void onSuccess(final Uri uri) {
                            String image_url = uri.toString( );

                            Map<String, Object> pic = new HashMap<>( );
                            pic.put( "image", image_url );

                            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( my_uid ).update( pic )
                                    .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful( )) {
                                                loadingDialog.dismiss( );

                                                Toast.makeText( Profile.this, "Image Uploaded", Toast.LENGTH_SHORT ).show( );
                                            } else {
                                                loadingDialog.dismiss( );

                                            }
                                        }
                                    } );


                        }
                    } );


                }
            } ).addOnFailureListener( new OnFailureListener( ) {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismiss( );

                    Toast.makeText( Profile.this, e.getMessage( ), Toast.LENGTH_SHORT ).show( );
                }
            } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {


                }
            } );
        } else {
            Toast.makeText( this, "no item selected", Toast.LENGTH_SHORT ).show( );

        }


    }
}
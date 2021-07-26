package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bambuser.broadcaster.BroadcastPlayer;
import com.bambuser.broadcaster.PlayerState;
import com.bambuser.broadcaster.SurfaceViewWithAutoAR;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import okhttp3.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Request;
import okhttp3.Response;

import android.graphics.Point;
import android.view.Display;
import android.widget.MediaController;
import android.view.MotionEvent;
import android.widget.Toast;

public class player extends AppCompatActivity {
    final OkHttpClient mOkHttpClient = new OkHttpClient( );
    SurfaceViewWithAutoAR mVideoSurface;
    BroadcastPlayer mBroadcastPlayer;
    TextView mPlayerStatusTextView;
    //View mPlayerContentView;
    private static final String APPLICATION_ID = "Nm1XfDGpuTrVhUi9WcQTZQ";
    private static final String API_KEY = "76r34ngAohp91fvAb41THE";
    BroadcastPlayer.Observer mBroadcastPlayerObserver;
    Display mDefaultDisplay;
    MediaController mMediaController = null;
    View mPlayerContentView;

    long OWNER_LIKES = 0;
    long OWNER_FOLLOWERS = 0;
    public static List<String> owner_followers_list = new ArrayList<>( );
    String uid = FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( );


    RoundedImageView imageView;
    String productImage = "", productNmae = "", prodecctPricce = "", owner_id = "";


    String url = null;
    String product_id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_player );


        imageView = findViewById( R.id.open_bottom_sheet_layout );
        mVideoSurface = findViewById( R.id.VideoSurfaceView );
        mPlayerStatusTextView = findViewById( R.id.PlayerStatusTextView );
        mPlayerContentView = findViewById( R.id.PlayerContentView );
        mDefaultDisplay = getWindowManager( ).getDefaultDisplay( );


        mBroadcastPlayerObserver = new BroadcastPlayer.Observer( ) {
            @Override
            public void onStateChange(PlayerState playerState) {
                if (mPlayerStatusTextView != null)
                    mPlayerStatusTextView.setText( "Status: " + playerState );

                if (playerState == PlayerState.PLAYING || playerState == PlayerState.PAUSED || playerState == PlayerState.COMPLETED) {
                    if (mMediaController == null && mBroadcastPlayer != null && !mBroadcastPlayer.isTypeLive( )) {
                        mMediaController = new MediaController( player.this );
                        // mMediaController.setAnchorView(mPlayerContentView);
                        mMediaController.setMediaPlayer( mBroadcastPlayer );
                    }
                    if (mMediaController != null) {
                        mMediaController.setEnabled( true );
                        mMediaController.show( );
                    }
                } else if (playerState == PlayerState.ERROR || playerState == PlayerState.CLOSED) {
                    if (mMediaController != null) {
                        mMediaController.setEnabled( false );
                        mMediaController.hide( );
                    }
                    mMediaController = null;
                }
            }


            @Override
            public void onBroadcastLoaded(boolean live, int width, int height) {
                Point size = getScreenSize( );
                float screenAR = size.x / (float) size.y;
                float videoAR = width / (float) height;
                float arDiff = screenAR - videoAR;
                mVideoSurface.setCropToParent( Math.abs( arDiff ) < 0.2 );
            }

        };


        url = getIntent( ).getStringExtra( "url" );
        product_id = getIntent( ).getStringExtra( "product_id" );

        FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    imageView.setVisibility( View.VISIBLE );
                    prodecctPricce = task.getResult( ).get( "price" ).toString( );
                    productImage = task.getResult( ).get( "image_00" ).toString( );
                    productNmae = task.getResult( ).get( "name" ).toString( );
                    owner_id = task.getResult( ).get( "owner_id" ).toString( );

                    loadOwnerLikes( owner_id );
                    loadOwnerfollowerList( );
                } else {
                    String er = task.getException( ).getMessage( );
                    Toast.makeText( player.this, er, Toast.LENGTH_SHORT ).show( );

                }
            }
        } );


        imageView.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Button addtoCart, follow, unfollow;
                ImageView Image, addtoFav, removeWish;
                TextView name, gotoseller, price;
                LinearLayout seeDetails;

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( player.this, R.style.BottomsheetdialogTheme );
                View bottomSheetview = LayoutInflater.from( getApplicationContext( ) ).inflate( R.layout.layout_bottom_sheet, (LinearLayout) findViewById( R.id.bottomsheetContainer ) );


                seeDetails = bottomSheetview.findViewById( R.id.see_details_ll );
                Image = bottomSheetview.findViewById( R.id.bottom_product_image );
                name = bottomSheetview.findViewById( R.id.bottom_product_name );
                price = bottomSheetview.findViewById( R.id.bottom_product_price );
                follow = bottomSheetview.findViewById( R.id.bottom_follow );
                unfollow = bottomSheetview.findViewById( R.id.bottom_unfollow );
                gotoseller = bottomSheetview.findViewById( R.id.bottom_goto_seller );
                addtoFav = bottomSheetview.findViewById( R.id.bottom_addto_wish );
                removeWish = bottomSheetview.findViewById( R.id.bottom_remove_wish );


                gotoseller.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent( player.this,Profile.class );
                        intent.putExtra( "layout_code",1 );
                        intent.putExtra( "owner_id",owner_id );
                        startActivity( intent );
                    }
                } );

                removeWish.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        removeWish.setVisibility( View.GONE );
                        addtoFav.setVisibility( View.VISIBLE );
                        addtoFav.setClickable( false );
                        final Map<String, Object> updateLikes = new HashMap<>( );
                        updateLikes.put( "list_size", OWNER_LIKES - 1 );

                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( owner_id ).collection( "USER_DATA" ).document( "MY_LIKES" ).update( updateLikes ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {
                                    addtoFav.setClickable( true );
                                }
                            }
                        } );

                        DBquaries.removeFromGroceryWishList( product_id, player.this );

                    }
                } );

                if (DBquaries.following_list.contains( owner_id )) {
                    follow.setVisibility( View.GONE );
                    unfollow.setVisibility( View.VISIBLE );
                } else {
                    follow.setVisibility( View.VISIBLE );
                    unfollow.setVisibility( View.GONE );
                }


                unfollow.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        DBquaries.removeFromFollowing( owner_id, uid, player.this );
                        DBquaries.removeFromSeller( owner_id, uid, player.this );
                        unfollow.setVisibility( View.GONE );
                        follow.setVisibility( View.VISIBLE );

                    }
                } );
                follow.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        follow.setVisibility( View.GONE );
                        unfollow.setVisibility( View.VISIBLE );
                        unfollow.setClickable( false );
                        final Map<String, Object> updateFollow = new HashMap<>( );
                        updateFollow.put( "list_size", (long) DBquaries.following_list.size( ) + 1 );
                        updateFollow.put( "id_" + (long) DBquaries.following_list.size( ), owner_id );
                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_FOLLOWING" ).
                                update( updateFollow ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {
                                    final Map<String, Object> updateOwner = new HashMap<>( );
                                    updateOwner.put( "list_size", OWNER_FOLLOWERS + 1 );
                                    updateOwner.put( "id_" + OWNER_FOLLOWERS, uid );

                                    Date dNow = new Date( );
                                    SimpleDateFormat ft = new SimpleDateFormat( "yy/MM/dd hh:mm" );
                                    String time = ft.format( dNow );

                                    SimpleDateFormat ft1 = new SimpleDateFormat( "yyMMddhhmmss" );
                                    String order_time = ft1.format( dNow );

                                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( owner_id ).collection( "USER_DATA" ).document( "MY_FOLLOWER" ).update( updateOwner ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful( )) {
                                                unfollow.setClickable( true );
                                                DBquaries.following_list.add( owner_id );
                                                owner_followers_list.add( uid );
                                                DBquaries.followerLikeNotification( uid,DBquaries.USER_NAME,DBquaries.USER_IMAGE,owner_id,false,time,order_time );
                                                Toast.makeText( player.this, "Followed", Toast.LENGTH_SHORT ).show( );
                                            }
                                        }
                                    } );

                                }

                            }
                        } );

                    }
                } );


                if (DBquaries.grocery_wishList.contains( product_id )) {
                    removeWish.setVisibility( View.VISIBLE );
                    addtoFav.setVisibility( View.GONE );

                } else {
                    removeWish.setVisibility( View.GONE );
                    addtoFav.setVisibility( View.VISIBLE );
                }

                seeDetails.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( player.this, offlineProductDetails.class );
                        intent.putExtra( "product_id", product_id );
                        startActivity( intent );
                    }
                } );

                addtoFav.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view) {
                        addtoFav.setVisibility( View.GONE );
                        removeWish.setVisibility( View.VISIBLE );
                        removeWish.setClickable( false );
                        final Map<String, Object> updateListSize = new HashMap<>( );
                        updateListSize.put( "list_size", (long) DBquaries.grocery_wishList.size( ) + 1 );
                        Map<String, Object> product_Id = new HashMap<>( );
                        product_Id.put( "id_" + (long) DBquaries.grocery_wishList.size( ), product_id );
                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" ).
                                update( product_Id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {
                                    final Map<String, Object> updateLikes = new HashMap<>( );
                                    updateLikes.put( "list_size", OWNER_LIKES + 1 );

                                    Date dNow = new Date( );
                                    SimpleDateFormat ft = new SimpleDateFormat( "yy/MM/dd hh:mm" );
                                    String time = ft.format( dNow );

                                    SimpleDateFormat ft1 = new SimpleDateFormat( "yyMMddhhmmss" );
                                    String order_time = ft1.format( dNow );

                                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( owner_id ).collection( "USER_DATA" ).document( "MY_LIKES" ).update( updateLikes ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful( )) {
                                                DBquaries.followerLikeNotification( uid,DBquaries.USER_NAME,DBquaries.USER_IMAGE,owner_id,true,time,order_time );

                                            }
                                        }
                                    } );


                                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                            .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" ).
                                            update( updateListSize ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful( )) {
                                                Toast.makeText( player.this, "Added to Wishlist", Toast.LENGTH_SHORT ).show( );
                                                removeWish.setClickable( true );

                                                DBquaries.grocery_wishList.add( product_id );

                                            }

                                        }
                                    } );


                                }

                            }
                        } );


                    }
                } );


                Glide.with( bottomSheetview.getContext( ) ).load( productImage ).into( Image );
                name.setText( productNmae );
                price.setText( "RM " + prodecctPricce );
                addtoCart = bottomSheetview.findViewById( R.id.add_to_cart_btn );

                addtoCart.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText( player.this, "Added to Cart", Toast.LENGTH_SHORT ).show( );
                    }
                } );


                bottomSheetDialog.setContentView( bottomSheetview );
                bottomSheetDialog.show( );
            }
        } );


    }

    @Override
    protected void onPause() {
        super.onPause( );
        mOkHttpClient.dispatcher( ).cancelAll( );
        mVideoSurface = null;

        if (mBroadcastPlayer != null)
            mBroadcastPlayer.close( );
        mBroadcastPlayer = null;

        if (mMediaController != null)
            mMediaController.hide( );
        mMediaController = null;

    }


    @Override
    protected void onResume() {
        super.onResume( );
        mVideoSurface = findViewById( R.id.VideoSurfaceView );
        mPlayerStatusTextView.setText( "Loading latest broadcast" );
        // getLatestResourceUri();

        initPlayer( url );


    }

    void getLatestResourceUri() {
        Request request = new Request.Builder( )
                .url( "https://api.bambuser.com/broadcasts" )
                .addHeader( "Accept", "application/vnd.bambuser.v1+json" )
                .addHeader( "Content-Type", "application/json" )
                .addHeader( "Authorization", "Bearer " + API_KEY )
                .get( )
                .build( );
        mOkHttpClient.newCall( request ).enqueue( new Callback( ) {
            @Override
            public void onFailure(final Call call, final IOException e) {
                runOnUiThread( new Runnable( ) {
                    @Override
                    public void run() {
                        if (mPlayerStatusTextView != null)
                            mPlayerStatusTextView.setText( "Http exception: " + e );
                    }
                } );
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String body = response.body( ).string( );
                String resourceUri = null;
                int size = 0;
                try {
                    JSONObject json = new JSONObject( body );
                    JSONArray results = json.getJSONArray( "results" );
                    size = results.length( );
                    JSONObject latestBroadcast = results.optJSONObject( 0 );
                    resourceUri = latestBroadcast.optString( "resourceUri" );
                } catch (Exception ignored) {
                }
                final String uri = resourceUri;
                int finalSize = size;
                runOnUiThread( new Runnable( ) {
                    @Override
                    public void run() {
                        initPlayer( uri );
                    }
                } );
            }
        } );
    }

    void initPlayer(String resourceUri) {
        if (resourceUri == null) {
            if (mPlayerStatusTextView != null)
                mPlayerStatusTextView.setText( "Could not get info about latest broadcast" );
            return;
        }
        if (mVideoSurface == null) {
            // UI no longer active
            return;
        }

        if (mBroadcastPlayer != null)
            mBroadcastPlayer.close( );
        mBroadcastPlayer = new BroadcastPlayer( this, resourceUri, APPLICATION_ID, mBroadcastPlayerObserver );
        mBroadcastPlayer.setSurfaceView( mVideoSurface );
        mBroadcastPlayer.load( );

    }


    private Point getScreenSize() {
        if (mDefaultDisplay == null)
            mDefaultDisplay = getWindowManager( ).getDefaultDisplay( );
        Point size = new Point( );
        try {
            // this is officially supported since SDK 17 and said to work down to SDK 14 through reflection,
            // so it might be everything we need.
            mDefaultDisplay.getClass( ).getMethod( "getRealSize", Point.class ).invoke( mDefaultDisplay, size );
        } catch (Exception e) {
            // fallback to approximate size.
            mDefaultDisplay.getSize( size );
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked( ) == MotionEvent.ACTION_UP && mBroadcastPlayer != null && mMediaController != null) {
            PlayerState state = mBroadcastPlayer.getState( );
            if (state == PlayerState.PLAYING ||
                    state == PlayerState.BUFFERING ||
                    state == PlayerState.PAUSED ||
                    state == PlayerState.COMPLETED) {
                if (mMediaController.isShowing( ))
                    mMediaController.hide( );
                else
                    mMediaController.show( );
            } else {
                mMediaController.hide( );
            }
        }
        return false;
    }

    void loadOwnerLikes(String id) {
        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( id ).collection( "USER_DATA" ).document( "MY_LIKES" ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    OWNER_LIKES = (long) task.getResult( ).get( "list_size" );
                }
            }
        } );
    }


    void loadOwnerfollowerList() {
        owner_followers_list.clear( );
        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( owner_id ).collection( "USER_DATA" ).document( "MY_FOLLOWER" ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    OWNER_FOLLOWERS = (long) task.getResult( ).get( "list_size" );
                    for (long x = 0; x < OWNER_FOLLOWERS; x++) {
                        String id = task.getResult( ).get( "id_" + x ).toString( );
                        owner_followers_list.add( id );
                    }
                }
            }
        } );

    }


}
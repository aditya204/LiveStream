package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceView;

import com.bambuser.broadcaster.BroadcastStatus;
import com.bambuser.broadcaster.Broadcaster;
import com.bambuser.broadcaster.CameraError;
import com.bambuser.broadcaster.ConnectionError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.quantum.mystreamingapp.DBquaries.USER_IMAGE;


public class MainActivity extends AppCompatActivity {
    private static final String APPLICATION_ID = "Nm1XfDGpuTrVhUi9WcQTZQ";

    SurfaceView mPreviewSurface;
    Broadcaster mBroadcaster;
    Button mBroadcastButton,player;
    private ImageButton toggleCamera;
    String product_id="";
    String category="";
    String sub_category="";
    String display_pic="";
    String list_id="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );



        product_id=getIntent().getStringExtra( "product_id" );
        display_pic=getIntent().getStringExtra( "display_picture" );
        category=getIntent().getStringExtra( "category" );
        sub_category=getIntent().getStringExtra( "sub_category" );


        DocumentReference ref = FirebaseFirestore.getInstance().collection("LIVE_IDS").document(category).collection( "LISTS" ).document();
        list_id = ref.getId();




        mPreviewSurface = findViewById(R.id.PreviewSurfaceView);
        player=findViewById( R.id.PlayerButton );
        toggleCamera=findViewById( R.id.flip_camera );





        toggleCamera.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                mBroadcaster.switchCamera();
            }
        } );





        mBroadcastButton = findViewById(R.id.BroadcastButton);

        mBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat( "yy/MM/dd hh:mm" );
                String datetime = ft.format( dNow );

                Map<String,Object> AddProductDetails=new HashMap<>(  );
                AddProductDetails.put( "product_id",product_id );
                AddProductDetails.put( "display_picture",display_pic );
                AddProductDetails.put( "sub_category",sub_category );
                AddProductDetails.put( "stream_id","stream_id" );
                AddProductDetails.put( "time",System.currentTimeMillis() );
                AddProductDetails.put( "date_time",datetime );
                AddProductDetails.put( "user_name",DBquaries.USER_NAME );
                AddProductDetails.put( "user_image",USER_IMAGE );
                AddProductDetails.put( "user_id", FirebaseAuth.getInstance().getCurrentUser().getUid() );


                FirebaseFirestore.getInstance().collection("LIVE_IDS").document(category).collection( "LISTS" ).document( list_id )
                        .set(AddProductDetails ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText( MainActivity.this, "You will be live now but its not working yet", Toast.LENGTH_LONG ).show( );
                        startActivity( new Intent( MainActivity.this,OfflineHome.class ) );
                        finish();
                    }
                } );




//                if (mBroadcaster.canStartBroadcasting())
//                    mBroadcaster.startBroadcast();
//                else
//                    mBroadcaster.stopBroadcast();
            }
        });


        Broadcaster.Observer mBroadcasterObserver = new Broadcaster.Observer() {
            @Override
            public void onConnectionStatusChange(BroadcastStatus broadcastStatus) {
                if (broadcastStatus == BroadcastStatus.STARTING)
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                if (broadcastStatus == BroadcastStatus.IDLE)
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                mBroadcastButton.setText(broadcastStatus == BroadcastStatus.IDLE ? "Broadcast" : "Disconnect");
            }
            @Override
            public void onStreamHealthUpdate(int i) {
            }
            @Override
            public void onConnectionError(ConnectionError connectionError, String s) {
            }
            @Override
            public void onCameraError(CameraError cameraError) {
            }
            @Override
            public void onChatMessage(String s) {
            }
            @Override
            public void onResolutionsScanned() {
            }
            @Override
            public void onCameraPreviewStateChanged() {
            }
            @Override
            public void onBroadcastInfoAvailable(String s, String s1) {

            }
            @Override
            public void onBroadcastIdAvailable(String s) {
//                Map<String,Object> AddProductDetails=new HashMap<>(  );
//
//                AddProductDetails.put( "live_id",s );
//                FirebaseFirestore.getInstance().collection( "PRODUCTS" ).document( product_id ).update(AddProductDetails).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText( MainActivity.this, "You are Live! now", Toast.LENGTH_SHORT ).show( );
//                    }
//                } );

            }
         };

         mBroadcaster = new Broadcaster(this, APPLICATION_ID, mBroadcasterObserver);
         mBroadcaster.setRotation(getWindowManager().getDefaultDisplay().getRotation());


    }







    @Override
    public void onResume() {
        super.onResume();
        if (!hasPermission(Manifest.permission.CAMERA)
                && !hasPermission(Manifest.permission.RECORD_AUDIO))
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO}, 1);
        else if (!hasPermission(Manifest.permission.RECORD_AUDIO))
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, 1);
        else if (!hasPermission(Manifest.permission.CAMERA))
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);

        mBroadcaster.setCameraSurface(mPreviewSurface);
        mBroadcaster.onActivityResume();
        mBroadcaster.setRotation(getWindowManager().getDefaultDisplay().getRotation());
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        mBroadcaster.onActivityDestroy();
    }
    @Override
    public void onPause() {
        super.onPause();
        mBroadcaster.onActivityPause();
    }

    private boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }


}
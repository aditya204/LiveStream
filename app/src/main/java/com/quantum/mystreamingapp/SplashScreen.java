package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.SplashSliderAdapter;
import com.quantum.mystreamingapp.Models.SplashSliderModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScreen extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout dot_layout,nextLayout,getstartedLL;
    private TabLayout dot_indicator;
    private SplashSliderAdapter splashSliderAdapter;
    private List<SplashSliderModel> splashSliderModel;
    private TextView nextTXT,getStartedTXT;
    private static final String API_KEY = "76r34ngAohp91fvAb41THE";
    final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static List<String> playerModelList=new ArrayList<>(  );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );

        mViewPager=findViewById( R.id.splash_view_pager );
        dot_indicator=findViewById( R.id.dot_indicator );
        dot_layout=findViewById( R.id.dot_layout );
        nextLayout=findViewById( R.id.next_layout );
        getstartedLL=findViewById( R.id.getstartedLL );
        nextTXT=findViewById( R.id.nextTXT );
        getStartedTXT=findViewById( R.id.getStartedTXT );


       // getLatestResourceUri();


        splashSliderModel=new ArrayList<>(  );
        splashSliderModel.add( new SplashSliderModel( "", "Header", "description of thr anything you want to mention here description of thr anything you want to mention here ", R.drawable.splash_screen_1 ) );
        splashSliderModel.add( new SplashSliderModel( "", "Header", "description of thr anything you want to mention here description of thr anything you want to mention here ", R.drawable.splash_screen_2 ) );
        splashSliderModel.add( new SplashSliderModel( "", "Header", "description of thr anything you want to mention here description of thr anything you want to mention here ", R.drawable.splash_screen_3 ) );
        splashSliderAdapter=new SplashSliderAdapter( splashSliderModel );
        mViewPager.setAdapter( splashSliderAdapter );
        splashSliderAdapter.notifyDataSetChanged();


//        FirebaseFirestore.getInstance().collection( "SPLASH" ).orderBy( "index" ).get()
//                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
//
//                            }
//
//
//                        }
//                    }
//                } );


        dot_indicator.setupWithViewPager( mViewPager,true );
        mViewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener( ) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==splashSliderModel.size()-1){
                    getstartedLL.setVisibility( View.VISIBLE );
                    nextLayout.setVisibility( View.GONE );
                    dot_layout.setVisibility( View.GONE );
                }else {

                    getstartedLL.setVisibility( View.GONE );
                    nextLayout.setVisibility( View.VISIBLE );
                    dot_layout.setVisibility( View.VISIBLE );

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
           Intent intent=new Intent( SplashScreen.this,OfflineHome.class );
           startActivity( intent );
           DBquaries.loadProductList();
           finish();
        }else {
            nextTXT.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    int position=mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem( position+1,true );
                }
            } );
            getStartedTXT.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent( SplashScreen.this,PreLogin.class ) );
                    finish();
                }
            } );
        }



    }

    void getLatestResourceUri() {
        Request request = new Request.Builder()
                .url("https://api.bambuser.com/broadcasts")
                .addHeader("Accept", "application/vnd.bambuser.v1+json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                runOnUiThread(new Runnable() { @Override public void run() {
                    Toast.makeText( SplashScreen.this, e.getMessage(), Toast.LENGTH_SHORT ).show( );
                }});
            }
            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                String body = response.body().string();
                String resourceUri = null;
                int size=0;
                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray results = json.getJSONArray("results");
                    size=results.length();
                    for(int i =0;i<size;i++){
                        JSONObject latestBroadcast = results.optJSONObject(i);
                        resourceUri = latestBroadcast.optString("resourceUri");
                        playerModelList.add( resourceUri );
                    }

                } catch (Exception ignored) {}
            }
        });
    }



}
package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quantum.mystreamingapp.Adapters.grocery_product_details_descrioption_Adapter;
import com.quantum.mystreamingapp.Adapters.productDetailsViewPagerAdapter;
import com.quantum.mystreamingapp.Models.grocery_product_details_descrioption_Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.quantum.mystreamingapp.R.color.gray_e3;
import static com.quantum.mystreamingapp.R.color.green_200;

public class ProductsDetaials extends AppCompatActivity {

    private ViewPager imageViewPager;
    private TabLayout viewPagerIndicator;
    private RecyclerView descriptionRecycler;
    private List<String> productImages=new ArrayList<>(  );
    private int count[] ={0};

    private TextView out_of_stockText;

    private ImageButton bid_price_add,bid_price_subtract;
    private Dialog loadingDialog;

    private TextView name, base_price, winner_name, bid_price,timmer_txt,user_bid;
    String product_id="";
    String winner_id="";
    String in_stock="";
    String base_price_str="";

    Button bid_now;
    int user_bid_price=0;


    boolean is_s_available,is_m_available,is_l_available,is_xl_available,is_xxl_available;
    long color_1,color_2,color_3;
    LinearLayout options_LL,color_options_ll,size_options_ll;
    TextView s_size,m_size,l_size,xl_size,xxl_size,color_1_txt,color_2_txt,color_3_txt,selected_color_txt;
    String is_size_selected="";
    String is_color_selected="";
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_products_detaials );



        imageViewPager = findViewById( R.id.grocery_product_image_VP );
        viewPagerIndicator = findViewById( R.id.grocery_product_image_VP_indicator );
        descriptionRecycler=findViewById( R.id. grocery_product_details_descrioption_Recycler);
        out_of_stockText=findViewById( R.id.grocery_product_details_OutstockText );
        name = findViewById( R.id.grocery_product_details_Name );
        base_price=findViewById( R.id.grocery_product_details_base_price );
        winner_name=findViewById( R.id.grocery_product_details_winner_name );
        bid_price=findViewById( R.id.grocery_product_details_bid_price );
        timmer_txt=findViewById( R.id.timer_txt );
        user_bid=findViewById( R.id.user_bid );
        bid_price_add=findViewById( R.id.user_bid_add );
        bid_price_subtract=findViewById( R.id.user_bid_sub );
        bid_now=findViewById( R.id.bid_now );



        ////size/color options
        options_LL=findViewById( R.id.offline_product_details_options );
        color_options_ll=findViewById( R.id.offline_product_details_color_options );
        size_options_ll=findViewById( R.id. offline_product_details_size_options);
        s_size=findViewById( R.id.s_size );
        m_size=findViewById( R.id.m_size );
        l_size=findViewById( R.id.l_size );
        xxl_size=findViewById( R.id.xxl_size );
        xl_size=findViewById( R.id.xl_size );
        color_1_txt=findViewById( R.id.color_1 );
        color_2_txt=findViewById( R.id.color_2 );
        color_3_txt=findViewById( R.id.color_3 );
        selected_color_txt=findViewById( R.id.show_selected_color );
        ////size/color options

        getSize();
        getColor();



        loadingDialog= new Dialog( this);
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );

        loadingDialog.show();


        product_id=getIntent().getStringExtra( "product_id" );
        FirebaseFirestore.getInstance().collection( "PRODUCTS" ).document( product_id ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    base_price_str=task.getResult( ).get( "price" ).toString( );
                    in_stock= String.valueOf((long) task.getResult( ).get( "in_stock" ));
                    long no_of_images = (long) task.getResult( ).get( "no_of_image" );

                    for (int x = 0; x < no_of_images; x++) {
                        productImages.add( task.getResult( ).get( "image_0"+x).toString( ) );
                    }
                    productDetailsViewPagerAdapter productDetailsViewPagerAdapter = new productDetailsViewPagerAdapter( productImages );
                    imageViewPager.setAdapter( productDetailsViewPagerAdapter );

                    name.setText( task.getResult( ).get( "name" ).toString( ) );
                    base_price.setText( "RM " +base_price_str  + "/-" );
                    user_bid.setText(base_price_str);
                    user_bid_price=Integer.parseInt(base_price_str );

                    if(in_stock.equals( "0" )){
                        out_of_stockText.setVisibility( View.VISIBLE );
                    }else{
                        out_of_stockText.setVisibility( View.GONE );
                    }


                    winner_id=task.getResult( ).get( "winner_id" ).toString( );
                    winner_name.setText(task.getResult( ).get( "winner_name" ).toString( )  );
                    bid_price.setText(task.getResult( ).get( "bid_price" ).toString( ) );
                    date=task.getResult().getTimestamp( "time" ).toDate();

                    long timmer_date=date.getTime();
                    long now=new Date( ) .getTime();
                    long distance=timmer_date-now;
                    new CountDownTimer(distance, 1000) {


                        public void onTick(long millisUntilFinished) {
                            long to_finish = millisUntilFinished ;


                            double days = Math.floor(to_finish / (1000 * 60 * 60 * 24));
                            double hours = Math.floor((to_finish % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                            double minutes = Math.floor((to_finish % (1000 * 60 * 60)) / (1000 * 60));
                            double seconds = Math.floor((to_finish % (1000 * 60)) / 1000);

                            String  hours_s=String.format("%02d", (int)hours);
                            String  minutes_s=String.format("%02d", (int)minutes);
                            String  seconds_s=String.format("%02d", (int)seconds);

                            timmer_txt.setText(hours_s +":"+minutes_s +":"+ seconds_s );

                            FirebaseFirestore.getInstance().collection( "PRODUCTS" ).document( product_id ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        winner_name.setText(  task.getResult().get( "winner_name" ).toString());
                                        bid_price.setText(  task.getResult().get( "bid_price" ).toString());
                                        winner_id= task.getResult().get( "winner_id" ).toString();
                                        loadingDialog.dismiss();
                                    }
                                }
                            } );

                        }

                        public void onFinish() {
                            Toast.makeText( ProductsDetaials.this, winner_name.getText().toString()+"is the winner", Toast.LENGTH_SHORT ).show( );
                            bid_price_add.setVisibility( View.GONE );
                            bid_price_subtract.setVisibility( View.GONE );
                            bid_now.setVisibility( View.GONE );
                            timmer_txt.setVisibility( View.GONE );
                            user_bid.setVisibility( View.GONE );
                            loadingDialog.dismiss();
                            ///add to cart

                            if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(winner_id  ) && !DBquaries.grocery_CartList_product_id.contains( product_id )) {


                                final Map<String, Object> updateListSize = new HashMap<>( );
                                updateListSize.put( "list_size", (long) DBquaries.grocery_CartList_product_id.size( ) + 1 );

                                Map<String, Object> product_Id = new HashMap<>( );
                                product_Id.put( "id_" + (long) DBquaries.grocery_CartList_product_id.size( ), product_id );

                                FirebaseFirestore.getInstance( ).collection( "USERS" ).document( winner_id )
                                        .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).
                                        update( product_Id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful( )) {
                                            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( winner_id )
                                                    .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).
                                                    update( updateListSize ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful( )) {
                                                        DBquaries.grocery_CartList_product_id.add( product_id );

                                                         loadingDialog.dismiss();

                                                        Map<String, Object> Count = new HashMap<>( );
                                                        Count.put( product_id, 1 );

                                                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( winner_id )
                                                                .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).
                                                                update( Count ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                            }
                                                        } );


                                                        Map<String, Object> Color = new HashMap<>( );
                                                        Color.put( product_id + "color", is_color_selected );
                                                        Color.put( product_id + "size", is_size_selected );

                                                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( winner_id )
                                                                .collection( "USER_DATA" ).document( "MY_SELECTED_COLOR" ).
                                                                update( Color ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                            }
                                                        } );


                                                    }

                                                }
                                            } );

                                        }
                                    }
                                } );

                            }


                        }
                    }.start();



                    ////options

                    is_s_available=(boolean)task.getResult( ).get( "is_s" );
                    is_m_available=(boolean)task.getResult( ).get( "is_m" );
                    is_l_available=(boolean)task.getResult( ).get( "is_l" );
                    is_xl_available=(boolean)task.getResult( ).get( "is_xl" );
                    is_xxl_available=(boolean)task.getResult( ).get( "is_xxl" );
                    color_1=(long)task.getResult( ).get( "color_1" );
                    color_3=(long)task.getResult( ).get( "color_3" );
                    color_2=(long)task.getResult( ).get( "color_2" );



                    if(!(is_s_available || is_m_available || is_l_available || is_xl_available || is_xxl_available )){

                        size_options_ll.setVisibility( View.GONE );
                    }else {

                        if(!is_s_available){
                            s_size.setVisibility( View.GONE );
                        }
                        if(!is_m_available){
                            m_size.setVisibility( View.GONE );
                        }
                        if(!is_l_available){
                            l_size.setVisibility( View.GONE );
                        }
                        if(!is_xl_available){
                            xl_size.setVisibility( View.GONE );
                        }
                        if(!is_xxl_available){
                            xxl_size.setVisibility( View.GONE );
                        }
                    }

                    if(!( color_1==0 && color_2==0 && color_3==0 )){
                        if(color_1==0){
                            color_1_txt.setVisibility( View.GONE );
                        }else {
                            color_1_txt.setBackgroundTintList( ColorStateList.valueOf( Integer.parseInt( String.valueOf( color_1 ) ) ) );

                        }
                        if(color_2==0){
                            color_2_txt.setVisibility( View.GONE );
                        }
                        else {
                            color_2_txt.setBackgroundTintList( ColorStateList.valueOf( Integer.parseInt( String.valueOf( color_2 ) ) ) );
                        }
                        if(color_3==0){
                            color_3_txt.setVisibility( View.GONE );
                        }
                        else {
                            color_3_txt.setBackgroundTintList( ColorStateList.valueOf( Integer.parseInt( String.valueOf( color_3 ) ) ) );
                        }

                    }else {
                        color_options_ll.setVisibility( View.GONE );
                    }

                    if(color_options_ll.getVisibility()==View.GONE && size_options_ll.getVisibility()==View.GONE){
                        options_LL.setVisibility( View.GONE );
                    }




                    /////options


                }

            }
        } );
        viewPagerIndicator.setupWithViewPager( imageViewPager, true );

        bid_now.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                if(user_bid_price >Integer.parseInt( bid_price.getText().toString() )){
                    String price =String.valueOf( user_bid_price );
                    String name=DBquaries.USER_NAME;
                    String user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();


                    Map<String,Object> upload=new HashMap<>(  );
                    upload.put( "bid_price",price );
                    upload.put( "winner_name",name );
                    upload.put( "winner_id",user_id );

                    FirebaseFirestore.getInstance().collection( "PRODUCTS" ).document( product_id ).update( upload )
                            .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText( ProductsDetaials.this, "Bidding Done", Toast.LENGTH_SHORT ).show( );
                                    }
                                }
                            } );
                }




            }
        } );

        bid_price_add.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                user_bid_price=user_bid_price+1;
                user_bid.setText( String.valueOf( user_bid_price ) );

            }
        } );

        bid_price_subtract.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(user_bid_price>Integer.parseInt( base_price_str )){
                    user_bid_price=user_bid_price-1;
                    user_bid.setText( String.valueOf( user_bid_price ) );

                }else
                    Toast.makeText( ProductsDetaials.this, "Can't Bid lower than base price", Toast.LENGTH_SHORT ).show( );

            }
        } );






    }

    public void getSize(){
        s_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );


                is_size_selected="s";

                if(DBquaries.product_id_list.contains( product_id )){
                    DBquaries.cart_size_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_size_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ),is_size_selected  );

                }

            }
        } );

        m_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(),gray_e3 ) );
                m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                is_size_selected="m";
                if(DBquaries.product_id_list.contains( product_id )) {
                    DBquaries.cart_size_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_size_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ), is_size_selected );
                }

            }
        } );

        l_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );

                is_size_selected="l";

                if(DBquaries.product_id_list.contains( product_id )) {
                    DBquaries.cart_size_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_size_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ), is_size_selected );
                }

            }
        } );

        xl_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );

                is_size_selected="xl";
                if(DBquaries.product_id_list.contains( product_id )) {
                    DBquaries.cart_size_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_size_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ), is_size_selected );
                }

            }
        } );

        xxl_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );

                is_size_selected="xxl";
                if(DBquaries.product_id_list.contains( product_id )) {
                    DBquaries.cart_size_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_size_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ), is_size_selected );
                }

            }
        } );
    }
    public void getColor(){

        color_1_txt.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                is_color_selected=String.valueOf( color_1 );
                selected_color_txt.setBackgroundTintList( ColorStateList.valueOf( Integer.parseInt( String.valueOf( color_1 ) ) ) );

                if(DBquaries.product_id_list.contains( product_id )) {
                    DBquaries.cart_color_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_color_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ), is_color_selected );
                }

            }
        } );

        color_2_txt.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                is_color_selected=String.valueOf( color_2 );
                selected_color_txt.setBackgroundTintList( ColorStateList.valueOf( Integer.parseInt( String.valueOf( color_2 ) ) ) );
                if(DBquaries.product_id_list.contains( product_id )) {
                    DBquaries.cart_color_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_color_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ), is_color_selected );
                }
            }
        } );

        color_3_txt.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                is_color_selected=String.valueOf( color_3 );
                selected_color_txt.setBackgroundTintList( ColorStateList.valueOf( Integer.parseInt( String.valueOf( color_3 ) ) ) );

                if(DBquaries.product_id_list.contains( product_id )) {
                    DBquaries.cart_color_list.remove( DBquaries.grocery_CartList_product_id.indexOf( product_id ) );
                    DBquaries.cart_color_list.add( DBquaries.grocery_CartList_product_id.indexOf( product_id ), is_color_selected );
                }

            }
        } );

    }
    private void setTv(Date date){







    }

}
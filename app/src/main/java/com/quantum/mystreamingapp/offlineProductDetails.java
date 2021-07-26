package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quantum.mystreamingapp.Adapters.GroceryProductAdapter;
import com.quantum.mystreamingapp.Adapters.ReviewAdapter;
import com.quantum.mystreamingapp.Adapters.grocery_product_details_descrioption_Adapter;
import com.quantum.mystreamingapp.Adapters.productDetailsViewPagerAdapter;
import com.quantum.mystreamingapp.Models.GroceryProductModel;
import com.quantum.mystreamingapp.Models.ReviewModel;
import com.quantum.mystreamingapp.Models.grocery_product_details_descrioption_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.quantum.mystreamingapp.R.color.baby_pink;
import static com.quantum.mystreamingapp.R.color.gray_e3;
import static com.quantum.mystreamingapp.R.color.green_200;

public class offlineProductDetails extends AppCompatActivity {


    private ViewPager imageViewPager;
    private TabLayout viewPagerIndicator;
    private FloatingActionButton addtoWishlist,cartFAB;
    public static boolean ADDED_towishList = false;
    private RecyclerView descriptionRecycler;
    private List<String> productImages=new ArrayList<>(  );
    private int count[] ={0};
    private List<grocery_product_details_descrioption_Model> grocery_product_details_descrioption_modelList;

    grocery_product_details_descrioption_Adapter grocery_product_details_descrioption_adapter;
    Toolbar toolbar;
    private TextView out_of_stockText;
    private Button buyBtn;
    public static LinearLayout addtoCart,allReviews;
    public static LinearLayout gotoCart;

    public static LinearLayout viewAllDescription;
    public static ConstraintLayout descLayout,review_layout;
    public static String Pname="";
    private LinearLayout rating_LL;






    /////productImage/nmae/price

    private TextView name, price, cutprice, offer, rating, reviewCount;



    /////productImage/nmae/price

    /////productRating
    private RecyclerView reviewRecycler;
    private List<ReviewModel> reviewModelList;
    private ReviewAdapter reviewAdapter;

    private TextView star_1_count;
    private TextView star_2_count;
    private TextView star_3_count;
    private TextView star_4_count;
    private TextView star_5_count;
    private TextView avg_rating;
    private TextView totalstarCount;
    private TextView totalreviewCount;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ProgressBar progressBar5;
    final int[] one_star_count = {0};
    final int[] two_star_count = {0};
    final int[] five_star_count = {0};
    final int[] four_star_count = {0};
    final int[] three_star_count = {0};
    final int[] total_rating_count = {0};
    final int[] total_review_count = {0};
    final double[] avg_star = {0.00000};
    Dialog loadingDialog;

    String in_stock="";
    String product_id="";



    ////size/color options
    boolean is_s_available,is_m_available,is_l_available,is_xl_available,is_xxl_available;
    long color_1,color_2,color_3;
    LinearLayout options_LL,color_options_ll,size_options_ll;
    TextView s_size,m_size,l_size,xl_size,xxl_size,color_1_txt,color_2_txt,color_3_txt,selected_color_txt;
    String is_size_selected="";
    String is_color_selected="";


    ////size/color options


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_offline_product_details );




        

        buyBtn=findViewById( R.id.buy_now_groceryBtn );
        addtoWishlist = findViewById( R.id.addtoWishlist );
        imageViewPager = findViewById( R.id.grocery_product_image_VP );
        viewPagerIndicator = findViewById( R.id.grocery_product_image_VP_indicator );
        descriptionRecycler = findViewById( R.id.grocery_product_details_descrioption_Recycler );
        addtoCart=findViewById( R.id.addtoGroceryCary );
        gotoCart=findViewById( R.id.gotoGroceryCary );


        rating_LL=findViewById( R.id.linearLayout17 );

        review_layout=findViewById( R.id.review_layout );
        //    viewAllDescription=findViewById( R.id.description_ViewAll_LL );
        descLayout=findViewById( R.id.descriptionConstraintLayout );

        addtoWishlist.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#FF609E" ) ) );

        /////productImage/nmae/price

        out_of_stockText=findViewById( R.id.grocery_product_details_OutstockText );
        name = findViewById( R.id.grocery_product_details_Name );
        price = findViewById( R.id.grocery_product_details_Price );
        cutprice = findViewById( R.id.grocery_product_details_CutPrice );
        rating = findViewById( R.id.grocery_product_details_Rating );
        reviewCount = findViewById( R.id.grocery_product_details_ReviewCount );
        offer = findViewById( R.id.grocery_product_details_Offer );
        cartFAB=findViewById( R.id.cartList );


        /////productImage/nmae/price


        /////productRating

        reviewRecycler=findViewById( R.id.review_recycler );
        star_1_count = findViewById( R.id.review_1_star_count );
        star_2_count = findViewById( R.id.review_2_star_count );
        star_3_count = findViewById( R.id.review_3_star_count );
        star_4_count = findViewById( R.id.review_4_star_count );
        star_5_count = findViewById( R.id.review_5_star_count );
        progressBar1 = findViewById( R.id.progressBar1star );
        progressBar2 = findViewById( R.id.progressBar2star );
        progressBar3 = findViewById( R.id.progressBar3star );
        progressBar4 = findViewById( R.id.progressBar4star );
        progressBar5 = findViewById( R.id.progressBar5star );
        avg_rating = findViewById( R.id.review_avg_star );
        totalstarCount = findViewById( R.id.review_total_rating );
        totalreviewCount = findViewById( R.id.review_total_review_count );
        allReviews=findViewById( R.id.view_all_review );

        /////productRating


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




        product_id=getIntent().getStringExtra( "product_id" );

        getSize();
        getColor();



//        loadingDialog= new Dialog( offlineProductDetails.this);
//        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
//        loadingDialog.setCancelable( false );
//        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
//        loadingDialog.show();


        FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).get( )
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful( )) {
                            in_stock= String.valueOf((long) task.getResult( ).get( "in_stock" ));
                            long no_of_images = (long) task.getResult( ).get( "no_of_image" );

                            for (int x = 0; x < no_of_images; x++) {
                                productImages.add( task.getResult( ).get( "image_00").toString( ) );
                            }
                            productDetailsViewPagerAdapter productDetailsViewPagerAdapter = new productDetailsViewPagerAdapter( productImages );
                            imageViewPager.setAdapter( productDetailsViewPagerAdapter );

                            name.setText( task.getResult( ).get( "name" ).toString( ) );
                            price.setText( "RM" + task.getResult( ).get( "price" ).toString( ) + "/-" );

                            if(in_stock.equals( "0" )){
                                out_of_stockText.setVisibility( View.VISIBLE );
                            }else{
                                out_of_stockText.setVisibility( View.GONE );
                            }
                            if(task.getResult( ).get( "offer" ).toString( ).equals( "0" )){

                                cutprice.setVisibility( View.GONE );
                                offer.setVisibility( View.GONE );
                            }
                            if(task.getResult( ).get( "rating" ).toString( ).length()==1){
                                rating_LL.setVisibility( View.GONE );
                                review_layout.setVisibility( View.GONE );
                                reviewCount.setVisibility( View.GONE );
                            }
                            cutprice.setText( "RM" + task.getResult( ).get( "cut_price" ).toString( ) + "/-" );
                            offer.setText( task.getResult( ).get( "offer" ).toString( ) + " off " );
                            reviewCount.setText( "(" + task.getResult( ).get( "review_count" ).toString( ) + ")" );
                            rating.setText( task.getResult( ).get( "rating" ).toString( ) );
                            grocery_product_details_descrioption_adapter.notifyDataSetChanged( );

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

        if(DBquaries.grocery_CartList_product_id.contains( product_id )){
            addtoCart.setVisibility( View.GONE );
            gotoCart.setVisibility( View.VISIBLE );
            cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#00CAA8" ) ) );
            int index=DBquaries.grocery_CartList_product_id.indexOf(  product_id );
            String size=DBquaries.cart_size_list.get( index );
            String color=DBquaries.cart_color_list.get( index );
            if(!size.isEmpty()){
                if(size.equals( "s" )){
                    s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );

                }
                if(size.equals( "m" )){
                    s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );

                }
                if(size.equals( "l" )){
                    s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );

                }
                if(size.equals( "xl" )){
                    s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );

                }
                if(size.equals( "xxl" )){
                    s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                }


            }

            if(!color.isEmpty()){
                selected_color_txt.setBackgroundTintList( ColorStateList.valueOf( Integer.parseInt(color ) ) );
            }

        }else {
            cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#696969" ) ) );
        }

        gotoCart.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( offlineProductDetails.this,Cart.class ) );
            }
        } );

        cartFAB.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( offlineProductDetails.this,Cart.class ) );
            }
        } );

        buyBtn.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( offlineProductDetails.this,Cart.class ) );
            }
        } );








        grocery_product_details_descrioption_modelList=new ArrayList<>(  );
        grocery_product_details_descrioption_modelList.add( new grocery_product_details_descrioption_Model( "description of the product" ) );
        grocery_product_details_descrioption_modelList.add( new grocery_product_details_descrioption_Model( "description of the product" ) );
        grocery_product_details_descrioption_modelList.add( new grocery_product_details_descrioption_Model( "description of the product" ) );
        grocery_product_details_descrioption_modelList.add( new grocery_product_details_descrioption_Model( "description of the product" ) );

        grocery_product_details_descrioption_adapter = new grocery_product_details_descrioption_Adapter( grocery_product_details_descrioption_modelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        descriptionRecycler.setAdapter( grocery_product_details_descrioption_adapter );
        descriptionRecycler.setLayoutManager( layoutManager );










//        reviewAdapter = new ReviewAdapter( reviewModelList );
//        LinearLayoutManager reviewlayoutManager = new LinearLayoutManager( this );
//        reviewlayoutManager.setOrientation( RecyclerView.VERTICAL );
//        reviewRecycler.setAdapter( reviewAdapter );
//        reviewRecycler.setLayoutManager( reviewlayoutManager );
//        reviewRecycler.setHasFixedSize( true);
//        reviewRecycler.setNestedScrollingEnabled( false );

        addtoCart.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                   // loadingDialog.show();
                    addtoCart.setClickable( false );
                    final Map<String, Object> updateListSize = new HashMap<>( );
                    updateListSize.put( "list_size", (long) DBquaries.grocery_CartList_product_id.size( ) + 1 );

                    Map<String, Object> product_Id = new HashMap<>( );
                    product_Id.put( "id_" + (long) DBquaries.grocery_CartList_product_id.size( ), product_id );

                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                            .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).
                            update( product_Id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                        .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).
                                        update( updateListSize ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            DBquaries.grocery_CartList_product_id.add( product_id );
                                            addtoCart.setVisibility( View.GONE );
                                            DBquaries.cart_color_list.add(is_color_selected );
                                            DBquaries.cart_size_list.add(is_size_selected );
                                            addtoCart.setClickable( true );
                                            cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#00CAA8" ) ) );

                                            gotoCart.setVisibility( View.VISIBLE );
                                            Toast.makeText( offlineProductDetails.this, "Added to cart", Toast.LENGTH_SHORT ).show( );

                                           // loadingDialog.dismiss();

                                            Map<String, Object> Count = new HashMap<>( );
                                            Count.put( product_id,1 );

                                            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                                    .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).
                                                    update( Count ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            } );


                                            Map<String, Object> Color = new HashMap<>( );
                                            Color.put( product_id+"color",is_color_selected );
                                            Color.put( product_id+"size",is_size_selected );


                                            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
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
                }else {
                    Intent intent = new Intent(offlineProductDetails.this, Login.class );
                    startActivity( intent );
                }
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



}
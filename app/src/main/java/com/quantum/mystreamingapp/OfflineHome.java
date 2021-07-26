package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.mystreamingapp.Adapters.CircularHorizontqalAdapter;
import com.quantum.mystreamingapp.Adapters.HomeAdapter;
import com.quantum.mystreamingapp.Adapters.HomeCategoryAdapter;
import com.quantum.mystreamingapp.Models.HomeCategoryModel;
import com.quantum.mystreamingapp.Models.HomeModel;
import com.quantum.mystreamingapp.Models.dealsofthedayModel;
import com.quantum.mystreamingapp.Models.sliderModel;

import java.util.ArrayList;
import java.util.List;

public class OfflineHome extends AppCompatActivity {


    RecyclerView homeRecycler;

    private List<HomeModel> homeModelList;
    public static List<dealsofthedayModel> dealsofthedayModelList=new ArrayList<>(  );
    public static List<dealsofthedayModel> gridList=new ArrayList<>(  );
    private List<sliderModel> sliderModelList=new ArrayList<>(  );
    private List<HomeCategoryModel> circularHorizontallist=new ArrayList<>(  );
    private ImageButton gotoLive,gotoCart,gotoProfile,gotoSearch,goto_notification,profileImageFavLL;
    LinearLayout searchInapp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_offline_home );

        gotoLive=findViewById( R.id.gotoLiveCategories );
        gotoCart=findViewById( R.id.home_cart_img_btn);
        gotoProfile=findViewById( R.id.goto_profile );
        gotoSearch=findViewById( R.id.goto_search );
        goto_notification=findViewById( R.id.goto_notification );
        searchInapp=findViewById( R.id.search_in_app );
        profileImageFavLL=findViewById( R.id.home_fav_img_btn );





        profileImageFavLL.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( OfflineHome.this,Favourite.class ) );

            }
        } );

        searchInapp.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( OfflineHome.this,Search.class ) );
            }
        } );
        goto_notification.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                 startActivity(new  Intent (OfflineHome.this,NotificationOrder.class));
            }
        } );
        gotoProfile.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity(new  Intent (OfflineHome.this,Profile.class));

            }
        } );
        gotoCart.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(  OfflineHome.this,Cart.class ));
            }
        } );

        DBquaries.loadGroceryCartList( OfflineHome.this );
        DBquaries.loadSizeCOLOR();
        DBquaries.load_userDetails();
        DBquaries.loadGroceryOrders();
        DBquaries.loadAdminOrders();
        DBquaries.loadGroceryWishList( OfflineHome.this );
        DBquaries.loadFollowingList( OfflineHome.this );
        DBquaries.loadFollowersList( OfflineHome.this );
        DBquaries.loadLikes();

        gotoLive.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( OfflineHome.this,Live.class ) );
            }
        } );

        gotoSearch.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( OfflineHome.this,Search.class ) );
            }
        } );



        homeRecycler=findViewById( R.id.offline_home_recycler );

        homeModelList=new ArrayList<>(  );

//        dealsofthedayModelList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/category%2Fcategory%20webinar%20white.png?alt=media&token=b4bc0765-61c9-4d88-a9e6-3d8f99c1e620","Product_name","Description of the product","200","","" ) );
//        dealsofthedayModelList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/category%2Fcategory%20webinar%20white.png?alt=media&token=b4bc0765-61c9-4d88-a9e6-3d8f99c1e620","Product_name","Description of the product","200","","" ) );
//        dealsofthedayModelList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/category%2Fcategory%20webinar%20white.png?alt=media&token=b4bc0765-61c9-4d88-a9e6-3d8f99c1e620","Product_name","Description of the product","200","","" ) );
//        dealsofthedayModelList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/category%2Fcategory%20webinar%20white.png?alt=media&token=b4bc0765-61c9-4d88-a9e6-3d8f99c1e620","Product_name","Description of the product","200","","" ) );
//        dealsofthedayModelList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/category%2Fcategory%20webinar%20white.png?alt=media&token=b4bc0765-61c9-4d88-a9e6-3d8f99c1e620","Product_name","Description of the product","200","","" ) );
//        dealsofthedayModelList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/category%2Fcategory%20webinar%20white.png?alt=media&token=b4bc0765-61c9-4d88-a9e6-3d8f99c1e620","Product_name","Description of the product","200","","" ) );
//        dealsofthedayModelList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/iqualive.appspot.com/o/category%2Fcategory%20webinar%20white.png?alt=media&token=b4bc0765-61c9-4d88-a9e6-3d8f99c1e620","Product_name","Description of the product","200","","" ) );
//
//        gridList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633","Product_name","Description of the product","200","","" ) );
//        gridList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633","Product_name","Description of the product","200","","" ) );
//        gridList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633","Product_name","Description of the product","200","","" ) );
//        gridList.add( new dealsofthedayModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633","Product_name","Description of the product","200","","" ) );
//
//
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fbeauty_banner.jpg?alt=media&token=a888c114-aef6-4ebf-8b75-fabfc3e97d75","","#FFFFFF" ) );
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fstay_home.jpg?alt=media&token=d02ed496-8793-4c3e-a59d-57a461633e17","","#FFFFFF" ) );
//
//
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633","","#FFFFFF" ) );
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fchocolate_banner.jpg?alt=media&token=5cd3bee5-321f-4dbc-a8e1-b00dcd2b03c0","","#FFFFFF" ) );
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fcovid_banner.jpg?alt=media&token=a9cfefa8-f3cc-4454-9a57-167f9b497ec6","","#FFFFFF" ) );
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fbeauty_banner.jpg?alt=media&token=a888c114-aef6-4ebf-8b75-fabfc3e97d75","","#FFFFFF" ) );
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fstay_home.jpg?alt=media&token=d02ed496-8793-4c3e-a59d-57a461633e17","","#FFFFFF" ) );
//
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633","","#FFFFFF" ) );
//        sliderModelList.add( new sliderModel( "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fchocolate_banner.jpg?alt=media&token=5cd3bee5-321f-4dbc-a8e1-b00dcd2b03c0","","#FFFFFF" ) );
//
//
//        ArrayList<String> ids=new ArrayList<>(  );
//
//        homeModelList=new ArrayList<>(  );
//        homeModelList.add( new HomeModel( 1,sliderModelList ) );
//        homeModelList.add( new HomeModel( 2,"Deals of the Day",dealsofthedayModelList,ids,"#FFFFFF" ) );
//
//        homeModelList.add( new HomeModel( 5,"d6b5797f-f159-4a84-8988-5fc627cec562","d6b5797f-f159-4a84-8988-5fc627cec562","NAME_3","NAME_4","https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633"
//                ,"https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633"
//                ,"https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633",
//                "https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/banners%2Fadding_product_daily.jpg?alt=media&token=ce55440c-aee7-4919-a0ba-d8a112f60633"
//                ,"FIND YOUR BEST","35e51nTjdjwNZg1joKxn","LQxo5k5tOMkBz7k2Wqk2","","") );
//        homeModelList.add( new HomeModel( 3,"TRENDING",gridList,ids,"#8DF0C9" ) );
//

        LinearLayoutManager grocerymain=new LinearLayoutManager( this );
        grocerymain.setOrientation( RecyclerView.VERTICAL );
        homeRecycler.setLayoutManager( grocerymain );
        final HomeAdapter homeAdapter=new HomeAdapter(homeModelList  );
        homeRecycler.setAdapter( homeAdapter );
        homeAdapter.notifyDataSetChanged();

        FirebaseFirestore.getInstance().collection( "HOME" ).orderBy( "index" ).get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot :task.getResult()){

                                if ((long) documentSnapshot.get( "view_type" ) == 1) {
                                    sliderModelList = new ArrayList<>( );

                                    long no_of_banners = (long) documentSnapshot.get( "no_of_banners" );

                                    for (long x = no_of_banners; x > no_of_banners - 2; x--) {
                                        sliderModelList.add( new sliderModel( documentSnapshot.get( "banner_"+x+"_image"  ).toString( ),documentSnapshot.get( "banner_"+x+"_tag"  ).toString( ),"" ) );
                                    }
                                    for (long x = 1; x < no_of_banners + 1; x++) {
                                        sliderModelList.add( new sliderModel( documentSnapshot.get(  "banner_"+x+"_image" ).toString( ),documentSnapshot.get( "banner_"+x+"_tag"  ).toString( ),"" ) );
                                    }

                                    for (long x = 1; x < 3; x++) {
                                        sliderModelList.add( new sliderModel( documentSnapshot.get(  "banner_"+x+"_image" ).toString( ),documentSnapshot.get( "banner_"+x+"_tag"  ).toString( ),"" ) );
                                    }

                                    homeModelList.add( new HomeModel( 1,sliderModelList ) );

                                }
                                if((long) documentSnapshot.get( "view_type" ) == 2){
                                    ArrayList<String> ids=new ArrayList<>(  );
                                    dealsofthedayModelList =new ArrayList<>(  );
                                    long no_of_products=(long) documentSnapshot.get( "no_of_products" );

                                    for (long x = 1; x < no_of_products + 1; x++) {

                                        dealsofthedayModelList.add( new dealsofthedayModel( documentSnapshot.get( "image_" + x ).toString( )
                                                , documentSnapshot.get( "category_id_"+x ).toString( )
                                                , "",
                                               "",
                                                "",
                                               "") );

                                        ids.add( "" );


                                    }


                                    homeModelList.add( new HomeModel( 2,"",dealsofthedayModelList,ids,"#FFFFFF" ) );


                                }

                                if((long) documentSnapshot.get( "view_type" ) == 3){
                                    ArrayList<String> ids=new ArrayList<>(  );
                                    gridList =new ArrayList<>(  );

                                    for (long x = 1; x <5; x++) {
                                        gridList.add( new dealsofthedayModel( documentSnapshot.get( "image_" + x ).toString( )
                                                ,""
                                                ,""
                                                , "",
                                                documentSnapshot.get( "product_id_" + x ).toString( ),
                                                documentSnapshot.get( "stream_id_" + x ).toString( )) );


                                        ids.add( "" );

                                    }
                                    homeModelList.add( new HomeModel( 3,"Grid Layout",gridList,ids,"#EF9A9A" ) );
                                }

                                if((long)documentSnapshot.get( "view_type" ) == 5){

                                    homeModelList.add( new HomeModel( 5,
                                            documentSnapshot.get("stream_id_1").toString(),
                                            documentSnapshot.get("stream_id_2").toString(),
                                            documentSnapshot.get("stream_id_3").toString(),
                                            documentSnapshot.get("stream_id_4").toString(),
                                            documentSnapshot.get("image_1").toString(),
                                            documentSnapshot.get("image_2").toString(),
                                            documentSnapshot.get("image_3").toString(),
                                            documentSnapshot.get("image_4").toString(),
                                            documentSnapshot.get("title").toString(),
                                            documentSnapshot.get("product_id_1").toString(),
                                            documentSnapshot.get("product_id_2").toString(),
                                            documentSnapshot.get("product_id_3").toString(),
                                            documentSnapshot.get("product_id_4").toString()
                                    ) );


                                }

                            }
                            homeAdapter.notifyDataSetChanged();

                        }else {
                            String error = task.getException( ).getMessage( );
                            Toast.makeText( OfflineHome.this, error, Toast.LENGTH_SHORT ).show( );


                        }
                    }
                } );


















    }
}
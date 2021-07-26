package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyOrderGroceryAdapter myOrderGroceryAdapter;
    private List<MyOrderGroceryModel> myOrderGroceryModelList;
    private LinearLayout noItemLL;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_orders );

        toolbar=findViewById( R.id.toolbar_grocery_Orders );


        recyclerView = findViewById( R.id.mr_ordrer_grocery_recycler );



        myOrderGroceryModelList = new ArrayList<>( );

        loadingDialog = new Dialog( Orders.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow( ).setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show( );




        toolbar.setTitle( "My Orders" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );

        myOrderGroceryModelList = new ArrayList<>( );

        myOrderGroceryAdapter = new MyOrderGroceryAdapter( myOrderGroceryModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( myOrderGroceryAdapter );

        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_GROCERY_ORDERS" ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {

                    long size = (long) task.getResult( ).get( "list_size" );

                    if (size == 0) {
                        loadingDialog.dismiss( );
                        noItemLL.setVisibility( View.VISIBLE );

                    }
                    for (long x =0; x<size; x++) {
                        final String order_id = task.getResult( ).get( "order_id_"+x ).toString( );

                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).orderBy( "product_id" )
                                .get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful( )) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {

                                        myOrderGroceryModelList.add( new MyOrderGroceryModel(
                                                documentSnapshot.get( "name" ).toString( ),
                                                documentSnapshot.get( "delivery_time" ).toString( ),
                                                documentSnapshot.get( "image" ).toString( ),
                                                documentSnapshot.get( "description" ).toString( ),
                                                order_id,
                                                documentSnapshot.get( "product_id" ).toString( ),
                                                documentSnapshot.get( "rating" ).toString( ),
                                                documentSnapshot.get( "review" ).toString( ),
                                                (boolean) documentSnapshot.get( "is_cancled" ),
                                                (boolean) documentSnapshot.get( "delivery_status" ),
                                                documentSnapshot.get( "otp" ).toString( )

                                        ) );



                                    }
                                    myOrderGroceryAdapter.notifyDataSetChanged( );
                                }
                            }
                        } );


                    }
                    loadingDialog.dismiss( );


                }

            }
        } );



    }


    public static class MyOrderGroceryModel {

        private String name, deliveryStat, description, product_id, order_id, rating, review, otp;
        private String image;
        private boolean is_cancled, is_deliverd;

        public MyOrderGroceryModel(String name, String deliveryStat, String image, String description, String order_id, String product_id, String rating, String review, boolean is_cancled, boolean is_deliverd, String otp) {
            this.name = name;
            this.deliveryStat = deliveryStat;
            this.image = image;
            this.description = description;
            this.order_id = order_id;
            this.product_id = product_id;
            this.rating = rating;
            this.review = review;
            this.is_cancled = is_cancled;
            this.is_deliverd = is_deliverd;
            this.otp = otp;

        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public boolean isIs_cancled() {
            return is_cancled;
        }

        public void setIs_cancled(boolean is_cancled) {
            this.is_cancled = is_cancled;
        }

        public boolean isIs_deliverd() {
            return is_deliverd;
        }

        public void setIs_deliverd(boolean is_deliverd) {
            this.is_deliverd = is_deliverd;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeliveryStat() {
            return deliveryStat;
        }

        public void setDeliveryStat(String deliveryStat) {
            this.deliveryStat = deliveryStat;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public static class MyOrderGroceryAdapter extends RecyclerView.Adapter<MyOrderGroceryAdapter.ViewHolder> {


        private List<MyOrderGroceryModel> myOrderGroceryModelList;

        public MyOrderGroceryAdapter(List<MyOrderGroceryModel> myOrderGroceryModelList) {
            this.myOrderGroceryModelList = myOrderGroceryModelList;
        }

        @NonNull
        @Override
        public MyOrderGroceryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.my_order_grocery_item, parent, false );
            return new ViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull MyOrderGroceryAdapter.ViewHolder holder, int position) {
            String name = myOrderGroceryModelList.get( position ).getName( );
            String DeliceryStatus = myOrderGroceryModelList.get( position ).getDeliveryStat( );
            String image = myOrderGroceryModelList.get( position ).getImage( );
            String description = myOrderGroceryModelList.get( position ).getDescription( );
            String order_id = myOrderGroceryModelList.get( position ).getOrder_id( );
            String productId = myOrderGroceryModelList.get( position ).getProduct_id( );
            String rating = myOrderGroceryModelList.get( position ).getRating( );
            String review = myOrderGroceryModelList.get( position ).getReview( );
            boolean is_Deliverd = myOrderGroceryModelList.get( position ).isIs_deliverd( );
            boolean is_Cancled = myOrderGroceryModelList.get( position ).isIs_cancled( );
            String otp = myOrderGroceryModelList.get( position ).getOtp( );


            holder.setDAta( name, image, description, order_id, productId, rating, review, otp );
            holder.setdeliveryStat( is_Cancled, is_Deliverd, DeliceryStatus );

            // holder.setStar( order_id, productId );
        }

        @Override
        public int getItemCount() {
            return myOrderGroceryModelList.size( );
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView deliveryStatus, name, write_rewiew;
            private ImageView image;
            private ConstraintLayout constraintLayout;
            private TextView blueDot, greenDot, RedDot, description, otp_txt;
            private ImageView star1, star2, star3, star4, star5;


            public ViewHolder(@NonNull View itemView) {
                super( itemView );

                image = itemView.findViewById( R.id.myOrder_groceryItemImage );
                deliveryStatus = itemView.findViewById( R.id.myOrder_groceryItemDeliveryStatus );
                name = itemView.findViewById( R.id.myOrder_groceryItemName );
                constraintLayout = itemView.findViewById( R.id.myOrder_groceryLayout );
                description = itemView.findViewById( R.id.myOrder_groceryItemDescription );
                blueDot = itemView.findViewById( R.id.myOrder_groceryItem_BlueDot );
                greenDot = itemView.findViewById( R.id.myOrder_groceryItem_GreenDot );
                RedDot = itemView.findViewById( R.id.myOrder_groceryItem_RedDot );

                star1 = itemView.findViewById( R.id.myOrder_star1 );
                star2 = itemView.findViewById( R.id.myOrder_star2 );
                star3 = itemView.findViewById( R.id.myOrder_star3 );
                star4 = itemView.findViewById( R.id.myOrder_star4 );
                star5 = itemView.findViewById( R.id.myOrder_star5 );
                write_rewiew = itemView.findViewById( R.id.myOrder_WriteReviewBlank );
                otp_txt = itemView.findViewById( R.id.otp_txt );


            }


            public void setDAta(String Name, String res, String desc, final String Oid, final String Pid, String Rating, final String Review, String Otp) {

                 Glide.with( itemView.getContext( ) ).load( res ).into( image );


                otp_txt.setText( Otp );
                name.setText( Name );
                description.setText( desc );

                constraintLayout.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent( itemView.getContext( ), OrderDetails.class );
                        intent.putExtra( "order_id", Oid );
                        intent.putExtra( "product_id", Pid );
                        itemView.getContext( ).startActivity( intent );


                    }
                } );
                if (Review.length( ) == 0) {
                    if(Rating.equals( "0" )){
                        write_rewiew.setVisibility( View.GONE );
                    }else {
                        write_rewiew.setVisibility( View.VISIBLE );
                    }
                } else {
                    write_rewiew.setClickable( false );
                    write_rewiew.setVisibility( View.VISIBLE );
                    write_rewiew.setText( "Reviewed !" );
                    write_rewiew.setTextColor( Color.parseColor( "#DF4444" ) );
                }

                if (Rating.equals( "1" )) {


                    star1.setImageResource( R.drawable.ic_star_green_24dp );
                    star2.setImageResource( R.drawable.ic_star_border_black_24dp );
                    star4.setImageResource( R.drawable.ic_star_border_black_24dp );
                    star3.setImageResource( R.drawable.ic_star_border_black_24dp );
                    star5.setImageResource( R.drawable.ic_star_border_black_24dp );
                }
                if (Rating.equals( "2" )) {
                    star1.setImageResource( R.drawable.ic_star_green_24dp );
                    star2.setImageResource( R.drawable.ic_star_green_24dp );
                    star4.setImageResource( R.drawable.ic_star_border_black_24dp );
                    star3.setImageResource( R.drawable.ic_star_border_black_24dp );
                    star5.setImageResource( R.drawable.ic_star_border_black_24dp );
                }
                if (Rating.equals( "3" )) {
                    star1.setImageResource( R.drawable.ic_star_green_24dp );
                    star2.setImageResource( R.drawable.ic_star_green_24dp );
                    star4.setImageResource( R.drawable.ic_star_border_black_24dp );
                    star3.setImageResource( R.drawable.ic_star_green_24dp );
                    star5.setImageResource( R.drawable.ic_star_border_black_24dp );
                }
                if (Rating.equals( "4" )) {


                    star1.setImageResource( R.drawable.ic_star_green_24dp );
                    star2.setImageResource( R.drawable.ic_star_green_24dp );
                    star4.setImageResource( R.drawable.ic_star_green_24dp );
                    star3.setImageResource( R.drawable.ic_star_green_24dp );
                    star5.setImageResource( R.drawable.ic_star_border_black_24dp );
                }
                if (Rating.equals( "5" )) {

                    star1.setImageResource( R.drawable.ic_star_green_24dp );
                    star2.setImageResource( R.drawable.ic_star_green_24dp );
                    star4.setImageResource( R.drawable.ic_star_green_24dp );
                    star3.setImageResource( R.drawable.ic_star_green_24dp );
                    star5.setImageResource( R.drawable.ic_star_green_24dp );
                }


//                write_rewiew.setOnClickListener( new View.OnClickListener( ) {
//                    @Override
//                    public void onClick(View view) {
//                        if (Review.length( ) == 0) {
//                            Intent intent = new Intent( itemView.getContext( ), WriteReview.class );
//                            intent.putExtra( "layout_code", 1 );
//                            intent.putExtra( "order_id", Oid );
//                            intent.putExtra( "product_id", Pid );
//                            itemView.getContext( ).startActivity( intent );
//
//                        }
//
//                    }
//                } );


            }

//            private void setStar(final String order_id, final String product_id) {
//
//                final Map<String, Object> RATING = new HashMap<>( );
//
//
//                star1.setOnClickListener( new View.OnClickListener( ) {
//                    @Override
//                    public void onClick(View view) {
//                        write_rewiew.setVisibility( View.VISIBLE );
//                        star1.setImageResource( R.drawable.ic_star_green_24dp );
//                        star2.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        star4.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        star3.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        star5.setImageResource( R.drawable.ic_star_border_black_24dp );
//
//                        RATING.put( "rating", "1" );
//
//
//                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
//                                .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful( )) {
//
//                                    Toast.makeText( itemView.getContext( ), "Thank You", Toast.LENGTH_SHORT ).show( );
//
//                                    FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).collection( "REVIEWS" ).document( order_id )
//                                            .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                        }
//                                    } );
//
//
//                                }
//
//                            }
//                        } );
//
//
//                    }
//
//
//                } );
//
//                star2.setOnClickListener( new View.OnClickListener( ) {
//                    @Override
//                    public void onClick(View view) {
//                        write_rewiew.setVisibility( View.VISIBLE );
//                        star1.setImageResource( R.drawable.ic_star_green_24dp );
//                        star2.setImageResource( R.drawable.ic_star_green_24dp );
//                        star4.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        star3.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        star5.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        RATING.put( "rating", "2" );
//
//
//                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
//                                .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful( )) {
//                                    Toast.makeText( itemView.getContext( ), "Thank You", Toast.LENGTH_SHORT ).show( );
//
//
//                                    FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).collection( "REVIEWS" ).document( order_id )
//                                            .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                        }
//                                    } );
//
//
//                                }
//
//                            }
//                        } );
//                    }
//
//
//                } );
//
//                star3.setOnClickListener( new View.OnClickListener( ) {
//                    @Override
//                    public void onClick(View view) {
//                        write_rewiew.setVisibility( View.VISIBLE );
//                        star1.setImageResource( R.drawable.ic_star_green_24dp );
//                        star2.setImageResource( R.drawable.ic_star_green_24dp );
//                        star4.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        star3.setImageResource( R.drawable.ic_star_green_24dp );
//                        star5.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        RATING.put( "rating", "3" );
//
//
//                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
//                                .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful( )) {
//
//
//                                    FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).collection( "REVIEWS" ).document( order_id )
//                                            .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                        }
//                                    } );
//                                    Toast.makeText( itemView.getContext( ), "Thank You", Toast.LENGTH_SHORT ).show( );
//
//
//                                }
//
//                            }
//                        } );
//                    }
//
//
//                } );
//
//
//                star4.setOnClickListener( new View.OnClickListener( ) {
//                    @Override
//                    public void onClick(View view) {
//                        write_rewiew.setVisibility( View.VISIBLE );
//                        star1.setImageResource( R.drawable.ic_star_green_24dp );
//                        star2.setImageResource( R.drawable.ic_star_green_24dp );
//                        star4.setImageResource( R.drawable.ic_star_green_24dp );
//                        star3.setImageResource( R.drawable.ic_star_green_24dp );
//                        star5.setImageResource( R.drawable.ic_star_border_black_24dp );
//                        RATING.put( "rating", "4" );
//
//
//                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
//                                .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful( )) {
//
//                                    Toast.makeText( itemView.getContext( ), "Thank You", Toast.LENGTH_SHORT ).show( );
//
//                                    FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).collection( "REVIEWS" ).document( order_id )
//                                            .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                        }
//                                    } );
//
//
//                                }
//
//                            }
//                        } );
//                    }
//
//
//                } );
//
//
//                star5.setOnClickListener( new View.OnClickListener( ) {
//                    @Override
//                    public void onClick(View view) {
//                        write_rewiew.setVisibility( View.VISIBLE );
//                        star1.setImageResource( R.drawable.ic_star_green_24dp );
//                        star2.setImageResource( R.drawable.ic_star_green_24dp );
//                        star4.setImageResource( R.drawable.ic_star_green_24dp );
//                        star3.setImageResource( R.drawable.ic_star_green_24dp );
//                        star5.setImageResource( R.drawable.ic_star_green_24dp );
//                        RATING.put( "rating", "5" );
//
//
//                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
//                                .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful( )) {
//                                    Toast.makeText( itemView.getContext( ), "Thank You", Toast.LENGTH_SHORT ).show( );
//                                    FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).collection( "REVIEWS" ).document( order_id )
//                                            .update( RATING ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                        }
//                                    } );
//
//
//                                }
//
//                            }
//                        } );
//                    }
//
//
//                } );
//
//
//            }

            public void setdeliveryStat(boolean is_cancled, boolean is_deliverd, String deliStat) {


                if (is_cancled) {
                    RedDot.setVisibility( View.VISIBLE );
                    deliveryStatus.setText( "Cancelled" );
                    deliveryStatus.setTextColor( Color.parseColor( "#DF4444" ) );
                } else {
                    if (is_deliverd) {
                        greenDot.setVisibility( View.VISIBLE );
                        deliveryStatus.setText( "Delivered on :" + deliStat );

                    } else {
                        blueDot.setVisibility( View.VISIBLE );
                        deliveryStatus.setText( "Estimate delivery time :" + deliStat + "Day/s" );
                    }

                }


            }
        }
    }

}
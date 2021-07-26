package com.quantum.mystreamingapp;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBquaries {

    public static String USER_NAME = "";
    public static String USER_IMAGE = "";


    public static List<String> product_images = new ArrayList( );
    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance( );
    ////grocery
    public static int TOTAL_DISCOUNT_GROCERY = 0;
    public static List<Integer> itemCount_grocery = new ArrayList<>( );
    public static List<String> itemName_grocery = new ArrayList<>( );
    public static List<String> itemCategory_grocery = new ArrayList<>( );
    public static int ITEMS_IN_CART_GROCERY = 0;


    public static List<String> grocery_CartList_product_id = new ArrayList<>( );
    public static List<String> grocery_CartList_product_count = new ArrayList<>( );
    public static List<String> grocery_CartList_product_OutOfStock = new ArrayList<>( );
    public static List<String> grocery_OrderList = new ArrayList<>( );

    public static List<Integer> tax_price = new ArrayList<>( );

    public static int PRICE_IN_CART_GROCERY = 0;
    public static int TOTAL_SAVE = 0;
    public static int DELIVERY_CHARGES = 0;
    public static int DELIVERY_FREE_ABOVE = 0;
    public static boolean IS_ADMIN = false;
    public static int MIN_ORDER_AMOUNT = 0;
    public static String ADMIN_NO;
    public static String ADMIN_MAIL;


    public static List<String> product_list = new ArrayList<>( );
    public static List<String> product_id_list = new ArrayList<>( );
    public static List<String> product_stream_id_list = new ArrayList<>( );
    public static List<Boolean> product_is_live_list = new ArrayList<>( );

    public static void loadProductList() {
        product_list.clear( );
        product_id_list.clear( );
        product_stream_id_list.clear( );
        product_is_live_list.clear( );
        FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).orderBy( "name" ).get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful( )) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {

                        product_list.add( documentSnapshot.get( "name" ).toString( ) );
                        product_id_list.add( documentSnapshot.getId( ).toString( ) );
                        product_stream_id_list.add( documentSnapshot.get( "live_id" ).toString( ) );
                        product_is_live_list.add( (boolean) documentSnapshot.get( "is_timmer" ) );

                    }

                }
            }
        } );

    }



    public static List<String> admin_OrderList = new ArrayList<>( );

    public static void loadAdminOrders() {
        admin_OrderList.clear( );
        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_STORE_ORDERS" ).get( )
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful( )) {
                            long size = (long) task.getResult( ).get( "list_size" );
                            for (long x = 0; x < size; x++) {
                                admin_OrderList.add( task.getResult( ).get( "order_id_" + x ).toString( ) );
                            }


                        }

                    }
                } );

    }

    public static void loadGroceryOrders() {
        grocery_OrderList.clear( );
        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_GROCERY_ORDERS" ).get( )
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful( )) {
                            long size = (long) task.getResult( ).get( "list_size" );
                            for (long x = 0; x < size; x++) {
                                grocery_OrderList.add( task.getResult( ).get( "order_id_" + x ).toString( ) );
                            }


                        }

                    }
                } );

    }


    public static List<String> cart_size_list = new ArrayList<>( );
    public static List<String> cart_color_list = new ArrayList<>( );

    public static void loadGroceryCartList(final Context context) {
        grocery_CartList_product_id.clear( );
        cart_size_list.clear( );
        cart_color_list.clear( );
        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" )
                .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    for (long x = 0; x < (long) task.getResult( ).get( "list_size" ); x++) {
                        String id = task.getResult( ).get( "id_" + x ).toString( );
                        grocery_CartList_product_id.add( id );

                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_SELECTED_COLOR" ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful( )) {
                                    cart_color_list.add( task.getResult( ).get( id + "color" ).toString( ) );
                                    cart_size_list.add( task.getResult( ).get( id + "size" ).toString( ) );
                                }

                            }
                        } );

                    }
                } else {
                    String error = task.getException( ).getMessage( );
                    Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );
                }
            }
        } );

    }

    public static void loadSizeCOLOR() {


    }

    public static void calcualtePriceGrocery(String todo, String amount) {
        if (todo.equals( "+" )) {
            DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY + Integer.parseInt( amount );
            Cart.priceIncart.setText( String.valueOf( PRICE_IN_CART_GROCERY ) );

        } else {
            DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY - Integer.parseInt( amount );
            Cart.priceIncart.setText( String.valueOf( PRICE_IN_CART_GROCERY ) );

        }


    }

    public static void calculateTotalSave(String todo, String price, String cutPrice) {

        if (todo.equals( "+" )) {
            TOTAL_SAVE = TOTAL_SAVE + Integer.parseInt( cutPrice ) - Integer.parseInt( price );
            Cart.totalSave.setText( "₹" + TOTAL_SAVE );
        } else {
            TOTAL_SAVE = TOTAL_SAVE - Integer.parseInt( cutPrice ) + Integer.parseInt( price );
            Cart.totalSave.setText( "₹" + String.valueOf( TOTAL_SAVE ) );

        }
    }


    public static void load_userDetails() {
        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) ).get( )
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful( )) {

                            USER_IMAGE = task.getResult( ).get( "image" ).toString( );
                            USER_NAME = task.getResult( ).get( "fullname" ).toString( );

//
                        }
                    }
                } );
    }


    public static void setDeliveryCharges() {
        if (Cart.pickUpCheck.isChecked( )) {
            Cart.tax.setText( "0" );

            Cart.grandTotal.setText( String.valueOf( PRICE_IN_CART_GROCERY ) );
            Cart.payAmount.setText( "₹" + PRICE_IN_CART_GROCERY );
        } else {

            if (PRICE_IN_CART_GROCERY < DELIVERY_FREE_ABOVE) {

                Cart.tax.setText( String.valueOf( DELIVERY_CHARGES ) );
                Cart.grandTotal.setText( String.valueOf( PRICE_IN_CART_GROCERY + DELIVERY_CHARGES ) );
                Cart.payAmount.setText( "₹" + (PRICE_IN_CART_GROCERY + DELIVERY_CHARGES) );

            } else {
                Cart.tax.setText( "0" );

                Cart.grandTotal.setText( String.valueOf( PRICE_IN_CART_GROCERY ) );
                Cart.payAmount.setText( "₹" + PRICE_IN_CART_GROCERY );

            }

        }


    }

    public static List<String> grocery_wishList = new ArrayList<>( );
    public static List<String> following_list = new ArrayList<>( );
    public static List<String> follower_list = new ArrayList<>( );
    public static long MY_LIKES = 0;


    public static void loadFollowingList(final Context context) {
        following_list.clear( );
        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_FOLLOWING" )
                .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    for (long x = 0; x < (long) task.getResult( ).get( "list_size" ); x++) {

                        String id = task.getResult( ).get( "id_" + x ).toString( );
                        following_list.add( id );
                    }
                } else {
                    String error = task.getException( ).getMessage( );
                    Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );
                }
            }
        } );
    }

    public static void loadFollowersList(final Context context) {
        follower_list.clear( );
        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_FOLLOWER" )
                .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    for (long x = 0; x < (long) task.getResult( ).get( "list_size" ); x++) {

                        String id = task.getResult( ).get( "id_" + x ).toString( );
                        follower_list.add( id );
                    }
                } else {
                    String error = task.getException( ).getMessage( );
                    Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );
                }
            }
        } );


    }

    public static void loadLikes() {
        MY_LIKES = 0;
        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) ).collection( "USER_DATA" ).document( "MY_LIKES" ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    MY_LIKES = (long) task.getResult( ).get( "list_size" );
                }
            }
        } );


    }

    public static void loadGroceryWishList(final Context context) {
        grocery_wishList.clear( );
        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" )
                .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {
                    for (long x = 0; x < (long) task.getResult( ).get( "list_size" ); x++) {

                        String id = task.getResult( ).get( "id_" + x ).toString( );
                        grocery_wishList.add( id );
                    }
                } else {
                    String error = task.getException( ).getMessage( );
                    Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );
                }
            }
        } );


    }

    public static void removeFromGroceryWishList(String id, final Context context) {


        grocery_wishList.remove( id );

        final int size = grocery_wishList.size( );
        Map<String, Object> updateWishList = new HashMap<>( );
        if (size == 0) {

            Map<String, Object> Size = new HashMap<>( );
            Size.put( "list_size", 0 );

            firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" )
                    .set( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {

                    }

                }
            } );


        }

        for (int x = 0; x < size; x++) {

            updateWishList.put( "id_" + x, grocery_wishList.get( x ) );

            final int finalX = x;
            firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" )
                    .set( updateWishList ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {
                        Map<String, Object> Size = new HashMap<>( );
                        Size.put( "list_size", size );

                        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" )
                                .update( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText( context, "removed", Toast.LENGTH_SHORT ).show( );
                            }
                        } );

                    } else {
                        String error = task.getException( ).getMessage( );
                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );


                    }
                }
            } );

        }

    }

    public static void removeFromFollowing(String owner_id, String uid, final Context context) {


        following_list.remove( owner_id );


        final int size = following_list.size( );
        Map<String, Object> updateWishList = new HashMap<>( );
        if (size == 0) {

            Map<String, Object> Size = new HashMap<>( );
            Size.put( "list_size", 0 );

            firebaseFirestore.collection( "USERS" ).document( uid )
                    .collection( "USER_DATA" ).document( "MY_FOLLOWING" )
                    .set( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {

                    }

                }
            } );


        }

        for (int x = 0; x < size; x++) {

            updateWishList.put( "id_" + x, following_list.get( x ) );

            final int finalX = x;
            firebaseFirestore.collection( "USERS" ).document( uid )
                    .collection( "USER_DATA" ).document( "MY_FOLLOWING" )
                    .set( updateWishList ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {
                        Map<String, Object> Size = new HashMap<>( );
                        Size.put( "list_size", size );

                        firebaseFirestore.collection( "USERS" ).document( uid )
                                .collection( "USER_DATA" ).document( "MY_FOLLOWING" )
                                .update( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        } );

                    } else {
                        String error = task.getException( ).getMessage( );
                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );


                    }
                }
            } );

        }

    }

    public static void removeFromSeller(String owner_id, String uid, final Context context) {


        player.owner_followers_list.remove( uid );


        final int size = player.owner_followers_list.size( );
        Map<String, Object> updateWishList = new HashMap<>( );
        if (size == 0) {

            Map<String, Object> Size = new HashMap<>( );
            Size.put( "list_size", 0 );

            firebaseFirestore.collection( "USERS" ).document( owner_id )
                    .collection( "USER_DATA" ).document( "MY_FOLLOWER" )
                    .set( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {
                        Toast.makeText( context, "Unfollowed", Toast.LENGTH_SHORT ).show( );
                    }

                }
            } );


        }

        for (int x = 0; x < size; x++) {

            updateWishList.put( "id_" + x, player.owner_followers_list.get( x ) );

            final int finalX = x;
            firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_FOLLOWER" )
                    .set( updateWishList ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {
                        Map<String, Object> Size = new HashMap<>( );
                        Size.put( "list_size", size );

                        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_FOLLOWER" )
                                .update( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText( context, "Unfollowed", Toast.LENGTH_SHORT ).show( );
                            }
                        } );

                    } else {
                        String error = task.getException( ).getMessage( );
                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );


                    }
                }
            } );

        }

    }

    public static void removeFromGroceryCartList(final String id, final Context context) {
        int index = grocery_CartList_product_id.indexOf( id );
        cart_color_list.remove( index );
        cart_size_list.remove( index );

        grocery_CartList_product_id.remove( id );

        final int size = grocery_CartList_product_id.size( );

        if (size == 0) {

            Map<String, Object> Size = new HashMap<>( );
            Size.put( "list_size", 0 );


            firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" )
                    .set( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {
                        grocery_CartList_product_id.clear( );
                        grocery_CartList_product_count.clear( );
                        cart_color_list.clear( );
                        cart_size_list.clear( );
                        Toast.makeText( context, "removed from cart", Toast.LENGTH_SHORT ).show( );
                        grocery_CartList_product_OutOfStock.remove( id );

                        // ProductDetails.gotoCart.setVisibility( View.GONE );
                        //ProductDetails.addtoCart.setVisibility( View.VISIBLE );


                    }


                }
            } );

        }
        for (int x = 0; x < size; x++) {
            Map<String, Object> updateWishList = new HashMap<>( );
            updateWishList.put( "id_" + x, grocery_CartList_product_id.get( x ) );

            firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" )
                    .set( updateWishList ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful( )) {
                        Map<String, Object> Size = new HashMap<>( );
                        Size.put( "list_size", size );

                        firebaseFirestore.collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" )
                                .update( Size ).addOnCompleteListener( new OnCompleteListener<Void>( ) {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (task.isSuccessful( )) {


                                    loadGroceryCartList( context );
                                    Toast.makeText( context, "removed from cart", Toast.LENGTH_SHORT ).show( );
                                    grocery_CartList_product_OutOfStock.remove( id );
                                    // ProductDetails.gotoCart.setVisibility( View.GONE );
                                    // ProductDetails.addtoCart.setVisibility( View.VISIBLE );


                                }


                            }
                        } );

                    } else {
                        String error = task.getException( ).getMessage( );
                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show( );


                    }
                }
            } );


        }


    }

    public static void placeOrderNotification(String user_id, String image,String seller_id, String order_id, String product_id, String time, boolean is_for_Seller,String product_name) {

        if (is_for_Seller) {
            DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(user_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document(  );
            String notification_id=ref.getId();

            Map<String,Object> placeOrder=new HashMap<>(  );
            placeOrder.put( "user_id",user_id );
            placeOrder.put( "seller_id", seller_id);
            placeOrder.put( "order_id", order_id);
            placeOrder.put( "product_id",product_id );
            placeOrder.put( "header", "Your order is placed");
            placeOrder.put( "time",time );
            placeOrder.put( "product_image",image );
            placeOrder.put( "product_name",product_name );
            placeOrder.put( "content","Your order for Product "+ product_name+" is placed . Tap for more info" );
            placeOrder.put( "is_for_Seller",is_for_Seller );
            placeOrder.put( "is_readed",false );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document(user_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document( notification_id ).
                    set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            } );
        }else {

            DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(user_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document(  );
            String notification_id=ref.getId();

            Map<String,Object> placeOrder=new HashMap<>(  );
            placeOrder.put( "user_id",user_id );
            placeOrder.put( "seller_id", seller_id);
            placeOrder.put( "order_id", order_id);
            placeOrder.put( "product_id",product_id );
            placeOrder.put( "header", "You have got an order");
            placeOrder.put( "product_image",image );
            placeOrder.put( "time",time );
            placeOrder.put( "product_name",product_name );

            placeOrder.put( "content","You have got an order for "+ product_name +" tap for more info" );
            placeOrder.put( "is_for_Seller",is_for_Seller );
            placeOrder.put( "is_readed",false );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document(seller_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document( notification_id ).
                    set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            } );

        }
    }
    public static void confirmedOrderNotification(String user_id, String image,String seller_id, String order_id, String product_id, String time, boolean is_for_Seller,String product_name) {
        if (is_for_Seller) {
            DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(user_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document(  );
            String notification_id=ref.getId();

            Map<String,Object> placeOrder=new HashMap<>(  );
            placeOrder.put( "user_id",user_id );
            placeOrder.put( "seller_id", seller_id);
            placeOrder.put( "order_id", order_id);
            placeOrder.put( "product_id",product_id );
            placeOrder.put( "header", "Your order is placed");
            placeOrder.put( "time",time );
            placeOrder.put( "product_image",image );
            placeOrder.put( "content","Your order for Product "+ product_name+" is placed . Tap for more info" );
            placeOrder.put( "is_for_Seller",is_for_Seller );
            placeOrder.put( "is_readed",false );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document(user_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document( notification_id ).
                    set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            } );
        }else {

            DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(user_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document(  );
            String notification_id=ref.getId();

            Map<String,Object> placeOrder=new HashMap<>(  );
            placeOrder.put( "user_id",user_id );
            placeOrder.put( "seller_id", seller_id);
            placeOrder.put( "order_id", order_id);
            placeOrder.put( "product_id",product_id );
            placeOrder.put( "header", "You have got an order");
            placeOrder.put( "product_image",image );
            placeOrder.put( "time",time );
            placeOrder.put( "content","You have got an order for "+ product_name +" tap for more info" );
            placeOrder.put( "is_for_Seller",is_for_Seller );
            placeOrder.put( "is_readed",false );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document(seller_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" ).document( notification_id ).
                    set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            } );

        }
    }
    public  static  void followerLikeNotification(String follower_id,String follower_name,String follower_image,String following_id,boolean is_like,String time,String order_time){
        if(is_like){
            DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(following_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" ).document(  );
            String notification_id=ref.getId();

            Map<String,Object> placeOrder=new HashMap<>(  );
            placeOrder.put( "follower_id",follower_id );
            placeOrder.put( "follower_name", follower_name);
            placeOrder.put( "follower_image", follower_image);
            placeOrder.put( "following_id",following_id );
            placeOrder.put( "header", "You got a like ");
            placeOrder.put( "time",time );
            placeOrder.put( "order_time",order_time );
            placeOrder.put( "is_like",is_like );
            placeOrder.put( "content", follower_name+" liked your profile. Tap to See his profile " );
            placeOrder.put( "is_readed",false );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document(following_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" ).document( notification_id ).
                    set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            } );
        }else {

            DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(following_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" ).document(  );
            String notification_id=ref.getId();

            Map<String,Object> placeOrder=new HashMap<>(  );
            placeOrder.put( "follower_id",follower_id );
            placeOrder.put( "follower_name", follower_name);
            placeOrder.put( "follower_image", follower_image);
            placeOrder.put( "following_id",following_id );
            placeOrder.put( "header", "You got a follower ");
            placeOrder.put( "time",time );
            placeOrder.put( "order_time",order_time );
            placeOrder.put( "is_like",is_like );
            placeOrder.put( "content", follower_name+" started following you." );
            placeOrder.put( "is_readed",false );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document(following_id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" ).document( notification_id ).
                    set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            } );

        }
    }
    public static void dealsUploadNotification(String image,String product_id,boolean is_timmer,String sub_category,String validity){

        int size=follower_list.size();
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat( "yy/MM/dd hh:mm" );
        String time = ft.format( dNow );

        SimpleDateFormat ft1 = new SimpleDateFormat( "yyMMddhhmmss" );
        String order_time = ft1.format( dNow );

        if(is_timmer){
            for(int i=0;i<size;i++){

                String id=follower_list.get( i );

                DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(id).collection("USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "DEALS" ).document(  );
                String notification_id=ref.getId();

                Map<String,Object> placeOrder=new HashMap<>(  );
                placeOrder.put( "seller_id",FirebaseAuth.getInstance().getCurrentUser().getUid() );
                placeOrder.put( "image", image);
                placeOrder.put( "product_id",product_id );
                placeOrder.put( "header",  DBquaries.USER_NAME +" have just uploaded a product");
                placeOrder.put( "time",time );
                placeOrder.put( "order_time",order_time );
                placeOrder.put( "is_timmer",is_timmer );
                placeOrder.put( "content","Bin now! Get the latest "+sub_category+" at crazy price. Only via Ulalive. Bidding Ends At " +validity );
                placeOrder.put( "is_readed",false );

                FirebaseFirestore.getInstance( ).collection( "USERS" ).document(id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "DEALS" ).document( notification_id ).
                        set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                } );


            }


        }else {
            for(int i=0;i<size;i++){

                String id=follower_list.get( i );

                DocumentReference ref =  FirebaseFirestore.getInstance( ).collection( "USERS" ).document(id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" ).document(  );
                String notification_id=ref.getId();

                Map<String,Object> placeOrder=new HashMap<>(  );
                placeOrder.put( "seller_id",FirebaseAuth.getInstance().getCurrentUser().getUid() );
                placeOrder.put( "image", image);
                placeOrder.put( "product_id",product_id );
                placeOrder.put( "header",  DBquaries.USER_NAME +" have just uploaded a product");
                placeOrder.put( "time",time );
                placeOrder.put( "order_time",order_time );
                placeOrder.put( "is_timmer",is_timmer );
                placeOrder.put( "content","Check out now! Get the latest "+sub_category+" at crazy price. Only" +
                        "via Ulalive " );
                placeOrder.put( "is_readed",false );

                FirebaseFirestore.getInstance( ).collection( "USERS" ).document(id).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" ).document( notification_id ).
                        set( placeOrder ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                } );


            }
        }





    }


}

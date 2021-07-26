package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {
    private TextView id;
    private Toolbar toolbar;
    private RecyclerView productRecycler;
    private TextView name, address_details, phn,type;
    private TextView priceinCart, tax, discunt, grandTotal;
    private TextView deliveryStatus, paymentStatus, paymentMode;
    private Orders.MyOrderGroceryAdapter myOrderGroceryAdapter;
    private List<Orders.MyOrderGroceryModel> myOrderGroceryModelList;
    private Button cancel_btn;
    private static String total="",tax_price="";
    private LinearLayout updateEDT_LL;
    private EditText updateEDT_editText;
    private Button updateEDT_button;
    private ConstraintLayout order_details_activity;
    private TextView product_name, product_description, product_price, product_cutPrice, product_offer, product_quantity, product_delivery_stat;
    private LinearLayout no_countLayout;
    private ImageView product_image;
    private ConstraintLayout Bill_LL,payent_stat_Cl;
    private Dialog loadingDialog;
    private  Button removeBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_details );
        //  order_details_activity=findViewById( R.id. order_details_activity);
//


        removeBtn=findViewById( R.id.remove_product_btn );
        removeBtn.setVisibility( View.GONE );


        order_details_activity=findViewById( R.id.order_details_activi );
        order_details_activity.setVisibility( View.INVISIBLE );
        toolbar=findViewById( R.id.my_order_grocery_toolbar );
        id = findViewById( R.id.grocery_order_details_order_id );
        name = findViewById( R.id.addressDetailsName );
        address_details = findViewById( R.id.addressDetailsAddress );
        priceinCart = findViewById( R.id.itemTotalPrice );
        tax = findViewById( R.id.taxChargesPrice );
        phn = findViewById( R.id.addressDetailsPhone );
        type=findViewById( R.id.addressDetailsType);
        cancel_btn=findViewById( R.id.grocery_cancelProduct_btn );
        discunt = findViewById( R.id.discount_price );
        grandTotal = findViewById( R.id.grandTotalPrice );
        deliveryStatus = findViewById( R.id.orderDetailsDeliveryStatus );
        paymentStatus = findViewById( R.id.orderDetailsPaymentStatus );
        paymentMode = findViewById( R.id.orderDetailsPaymentMode );
        productRecycler = findViewById( R.id.grocery_cart_product_recycler );
        Bill_LL=findViewById( R.id.bill_ll );
        payent_stat_Cl=findViewById( R.id.order_details_payment_details_CL );

        updateEDT_LL=findViewById( R.id.updateEDT_LL );
        updateEDT_button=findViewById( R.id.updateEDT_button );
        updateEDT_editText=findViewById( R.id.updateEDT_editText );


        no_countLayout = findViewById( R.id.grocery_cart_noCountLayout );
        product_quantity = findViewById( R.id.grocery_cart_product_Quantity );
        product_image = findViewById( R.id.grocery_cart_productImage );
        product_name = findViewById( R.id.grocery_cart_product_title );
        product_cutPrice=findViewById( R.id.grocery_cart_productcutprice );
        product_price = findViewById( R.id.grocery_cart_productPrice );
        product_description = findViewById( R.id.grocery_cart_productDescription );
        product_offer = findViewById( R.id.grocery_cart_product_offer );
        product_delivery_stat = findViewById( R.id.grocery_cart_productDeliveryStatus );


        loadingDialog= new Dialog( OrderDetails.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();



        product_quantity.setVisibility( View.VISIBLE );
        no_countLayout.setVisibility( View.GONE );

        toolbar.setTitle( "Order Details" );
        toolbar.setTitleTextColor( Color.parseColor( "#000000" ) );

        setSupportActionBar( toolbar );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );





        product_quantity.setVisibility( View.VISIBLE );
        no_countLayout.setVisibility( View.GONE );



        final String product_id = getIntent( ).getStringExtra( "product_id" );
        final String order_id = getIntent( ).getStringExtra( "order_id" );




        if(DBquaries.IS_ADMIN){
            updateEDT_LL.setVisibility( View.VISIBLE );
            updateEDT_button.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    if(updateEDT_editText.getText().length()!=0){
                        final String time=updateEDT_editText.getText().toString();
                        Map<String,Object> EDT=new HashMap<>(  );
                        EDT.put( "delivery_time",time );


                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
                                .update( EDT ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    product_delivery_stat.setText( "Estimate delivery time | " +time+ " Day/s" );
                                    Toast.makeText(OrderDetails.this, "EDT updated", Toast.LENGTH_SHORT ).show( );
                                }

                            }
                        } );


                    }else {
                        Toast.makeText( OrderDetails.this, "enter text", Toast.LENGTH_SHORT ).show( );

                    }



                }
            } );



        }

        toolbar.setTitle( "Order Details" );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

        setSupportActionBar( toolbar );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );





        id.setText( order_id );


        myOrderGroceryModelList = new ArrayList<>( );

        myOrderGroceryAdapter = new Orders.MyOrderGroceryAdapter( myOrderGroceryModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        productRecycler.setLayoutManager( layoutManager );
        productRecycler.setAdapter( myOrderGroceryAdapter );

        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    tax_price=task.getResult().get( "tax" ).toString();
                    total=task.getResult().get( "grand_total" ).toString();
                    tax.setText( tax_price );
                    priceinCart.setText( total );
                    grandTotal.setText( String.valueOf( Integer.parseInt( total )+Integer.parseInt( tax_price ) ) );
                }

            }
        } );

        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).orderBy( "product_id" ).get( )
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful( )) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {

                                if(documentSnapshot.get( "product_id" ).toString().equals( product_id )){

                                    product_name.setText( documentSnapshot.get("name").toString() );
                                    product_description.setText( documentSnapshot.get("description").toString() );
                                    product_price.setText( "₹"+documentSnapshot.get("price").toString()+"/-" );
                                    product_cutPrice.setText("₹"+ documentSnapshot.get("cut_price").toString()+"/-" );
                                    product_offer.setText( documentSnapshot.get("offer").toString() +"% off");
                                    product_quantity .setText( "Qty:- "+documentSnapshot.get("item_count").toString() );

                                    Glide.with(OrderDetails.this ).load(  documentSnapshot.get("image").toString()).into( product_image );


                                    phn.setText( documentSnapshot.get("user_phn").toString() );
                                    name.setText( documentSnapshot.get("user_name").toString() );
                                    address_details.setText( documentSnapshot.get("user_address_details").toString() );
                                    type.setText( documentSnapshot.get("user_address_type").toString() );
                                    //priceinCart.setText( String.valueOf(  Integer.parseInt( documentSnapshot.get("price").toString() )*Integer.parseInt( documentSnapshot.get("item_count").toString() )) );
                                    // grandTotal.setText(   String.valueOf(  Integer.parseInt( documentSnapshot.get("price").toString() )*Integer.parseInt( documentSnapshot.get("item_count").toString() )));
                                    paymentMode.setText( documentSnapshot.get("payment_mode").toString()  );

                                    if((boolean)documentSnapshot.get( "is_cancled" )){
                                        deliveryStatus.setText( "Cancelled" );
                                        payent_stat_Cl.setVisibility( View.GONE );
                                        cancel_btn.setClickable( false );
                                        cancel_btn.setText( "Cancelled" );
                                        product_delivery_stat.setText( "Cancelled" );
                                        deliveryStatus.setTextColor( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ));

                                        product_delivery_stat.setTextColor( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ));
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            cancel_btn.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ) );
                                        }
                                        Bill_LL.setVisibility( View.GONE );

                                    }else {

                                        if ((boolean) documentSnapshot.get( "delivery_status" )) {
                                            cancel_btn.setVisibility( View.GONE );
                                            deliveryStatus.setText( "Delivered" );
                                            product_delivery_stat.setText( "Delivered On | " + documentSnapshot.get( "delivery_time" ).toString( ) );
                                        } else {
                                            deliveryStatus.setText( "Not Delivered" );
                                            product_delivery_stat.setText( "Estimate delivery time | " + documentSnapshot.get( "delivery_time" ).toString( )+" Day/s" );

                                        }
                                    }
                                    if((boolean)documentSnapshot.get( "payment_status" )){
                                        paymentStatus.setText( "Paid" );
                                    }else {
                                        paymentStatus.setText( "Not Paid" );
                                    }
                                    order_details_activity.setVisibility( View.VISIBLE );
                                    loadingDialog.dismiss();

                                }else {


                                    myOrderGroceryModelList.add( new Orders.MyOrderGroceryModel(
                                            documentSnapshot.get( "name" ).toString( ),
                                            documentSnapshot.get( "delivery_time" ).toString( ),
                                            documentSnapshot.get( "image" ).toString( ),
                                            documentSnapshot.get( "description" ).toString( ),
                                            order_id,
                                            documentSnapshot.get( "product_id" ).toString( ),
                                            documentSnapshot.get( "rating" ).toString( ),
                                            documentSnapshot.get( "review" ).toString( ),
                                            (boolean)documentSnapshot.get( "is_cancled" ),
                                            (boolean)documentSnapshot.get( "delivery_status" ),
                                            documentSnapshot.get( "otp" ).toString( )
                                    ) );
                                    order_details_activity.setVisibility( View.VISIBLE );
                                    loadingDialog.dismiss();

                                }


                            }
                            myOrderGroceryAdapter.notifyDataSetChanged();


                        }

                    }
                } );

        cancel_btn.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                loadingDialog.show();
                Map<String,Object> Update =new HashMap<>(  );
                Update.put( "is_cancled" ,true );
                FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
                        .update( Update ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).document( product_id )
                                    .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        int price=Integer.parseInt(  task.getResult().get( "price" ).toString());
                                        int count=Integer.parseInt(  task.getResult().get( "item_count" ).toString());

                                        final int new_Total=Integer.parseInt( total )-price*count;

                                        // final int Inttax=setTax(new_Total );

                                        Map<String,Object> Update =new HashMap<>(  );
                                        Update.put( "grand_total" ,String.valueOf( new_Total ) );
                                        //   Update.put( "tax" ,String.valueOf( Inttax ) );

                                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).update( Update )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            loadingDialog.dismiss();
                                                            deliveryStatus.setText( "Cancelled" );
                                                            cancel_btn.setClickable( false );
                                                            cancel_btn.setText( "Cancelled" );
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                                cancel_btn.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ) );
                                                            }
                                                            Bill_LL.setVisibility( View.GONE );
                                                        }
                                                    }
                                                } );
                                    }
                                }
                            } );



                        }
                    }
                } );
            }
        } );


    }
}
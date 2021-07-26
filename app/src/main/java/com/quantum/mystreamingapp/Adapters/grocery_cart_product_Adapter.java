package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quantum.mystreamingapp.Cart;
import com.quantum.mystreamingapp.DBquaries;
import com.quantum.mystreamingapp.Models.grocery_cart_product_Model;
import com.quantum.mystreamingapp.ProductsDetaials;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.offlineProductDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class grocery_cart_product_Adapter extends RecyclerView.Adapter<grocery_cart_product_Adapter.ViewHolder> {

    private List<grocery_cart_product_Model> grocery_cart_product_modelList;


    public grocery_cart_product_Adapter(List<grocery_cart_product_Model> grocery_cart_product_modelList) {
        this.grocery_cart_product_modelList = grocery_cart_product_modelList;
    }

    @NonNull
    @Override
    public grocery_cart_product_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.grocery_cart_product_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull grocery_cart_product_Adapter.ViewHolder holder, int position) {

        String name = grocery_cart_product_modelList.get( position ).getName( );
        String description = grocery_cart_product_modelList.get( position ).getDescription( );
        String price = grocery_cart_product_modelList.get( position ).getPrice( );
        String cutPrice = grocery_cart_product_modelList.get( position ).getCutprice( );
        String id = grocery_cart_product_modelList.get( position ).getProduct_id( );
        String Image = grocery_cart_product_modelList.get( position ).getImage( );
        String offer = grocery_cart_product_modelList.get( position ).getOffer( );
        String count = grocery_cart_product_modelList.get( position ).getItemcount( );
        long stock=grocery_cart_product_modelList.get( position ).getIn_stock();
        boolean is_timmer=grocery_cart_product_modelList.get( position ).isIs_timmer();


        holder.setData( Image, name, description, price, cutPrice, offer, count,stock,id,is_timmer );

        holder.setAdd(name, id, position, count,price ,stock,cutPrice,is_timmer);
    }

    @Override
    public int getItemCount() {
        return grocery_cart_product_modelList.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, description, price, offer, cutPrice,outOfStockTxt;
        private ImageView image;
        private LinearLayout no_countLL;
        private TextView addproduct, subproduct, product_count;
        private ConstraintLayout constraintLayout;
        private Button remove;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            name = itemView.findViewById( R.id.grocery_cart_product_title );
            description = itemView.findViewById( R.id.grocery_cart_productDescription );
            price = itemView.findViewById( R.id.grocery_cart_productPrice );
            offer = itemView.findViewById( R.id.grocery_cart_product_offer );
            cutPrice = itemView.findViewById( R.id.grocery_cart_productcutprice );
            image = itemView.findViewById( R.id.grocery_cart_productImage );
            addproduct = itemView.findViewById( R.id.grocery_cart_noCountAdd );
            subproduct = itemView.findViewById( R.id.grocery_cart_noCountSubtract );
            product_count = itemView.findViewById( R.id.grocery_cart_noCount );
            outOfStockTxt=itemView.findViewById( R.id.grocery_cart_out_of_stockTxt );
            no_countLL=itemView.findViewById( R.id.grocery_cart_noCountLayout );
            constraintLayout=itemView.findViewById( R.id.grocery_cart_product_item_Layout );
            remove=itemView.findViewById( R.id.remove_product_btn );


        }

        private void setData(String res, String Name, String Desc, String Price, String CutPrice, String Offer, String count, long Stock,String product_id,boolean is_timmer) {


            if(is_timmer){
                remove.setVisibility( View.GONE );
                no_countLL.setVisibility( View.GONE );
                cutPrice.setVisibility( View.GONE );
                offer.setVisibility( View.GONE );
            }
            Glide.with( itemView.getContext( ) ).load( res ).into( image );

            name.setText( Name );

            description.setText( Desc );

            price.setText( "RM " + Price + "/-" );

            cutPrice.setText( "RM " + CutPrice + "/-" );

            offer.setText( Offer + " off" );

            product_count.setText( count );

            if(Offer.equals( "0" )){
                cutPrice.setVisibility( View.GONE );
                offer.setVisibility( View.GONE );
            }


            if(Stock==0){
                no_countLL.setVisibility( View.GONE );
                outOfStockTxt.setVisibility( View.VISIBLE );
            }

            remove.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    DBquaries.removeFromGroceryCartList( product_id,itemView.getContext() );

                    itemView.getContext().startActivity( new Intent(itemView.getContext(), Cart.class ) );
                }
            } );



        }



        private void setAdd(final String Name, final String id, final int index, String itemcount, final String price, final long stock, final String CutPrice, boolean is_timmer) {

            final int count[] = {Integer.parseInt( itemcount )};
            constraintLayout.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    if(is_timmer){
                        Intent intent=new Intent( itemView.getContext(), ProductsDetaials.class );
                        intent.putExtra( "product_id",id);
                        itemView.getContext().startActivity( intent );
                    }else {
                        Intent intent=new Intent( itemView.getContext(), offlineProductDetails.class );
                        intent.putExtra( "product_id",id );
                        intent.putExtra( "tag_string",Name );
                        itemView.getContext().startActivity( intent );
                    }

                }
            } );


            addproduct.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    addproduct.setClickable( false );
                    if(count[0]<stock) {
                        count[0] = count[0] + 1;
                        subproduct.setVisibility( View.VISIBLE );
                        subproduct.setClickable( true );

                        Map<String, Object> itemCount = new HashMap<>( );
                        itemCount.put( id, String.valueOf( count[0] ) );

                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).
                                update( itemCount ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {
                                    addproduct.setClickable( true );
                                    product_count.setText( String.valueOf( count[0] ) );
                                    DBquaries.grocery_CartList_product_count.remove( index );
                                    DBquaries.grocery_CartList_product_count.add( index, String.valueOf( count[0] ) );
                                    DBquaries.calcualtePriceGrocery( "+", price );
                                    DBquaries.calculateTotalSave( "+",price,CutPrice );
                                    DBquaries.setDeliveryCharges();


                                }
                            }
                        } );
                    }else {
                        addproduct.setClickable( true );

                        Toast.makeText( itemView.getContext(), "Running Out of Stock", Toast.LENGTH_SHORT ).show( );
                    }

                }
            } );

            subproduct.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    subproduct.setClickable( false );
                    if (count[0] > 1) {
                        count[0] = count[0] - 1;
                        Map<String, Object> itemCount = new HashMap<>( );
                        itemCount.put( id, String.valueOf( count[0] ) );

                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).
                                update( itemCount ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {
                                    subproduct.setClickable( true );
                                    DBquaries.calcualtePriceGrocery("-",price);
                                    product_count.setText( String.valueOf( count[0] ) );
                                    DBquaries.grocery_CartList_product_count.remove( index );
                                    DBquaries.grocery_CartList_product_count.add( index, String.valueOf( count[0] ) );
                                    DBquaries.calculateTotalSave( "-",price,CutPrice );
                                    DBquaries.setDeliveryCharges();

                                }
                            }
                        } );
                    }else {
                        subproduct.setVisibility( View.GONE );
                        product_count.setTextColor( Color.parseColor( "#696969" ) );
                    }

                }
            } );


        }
    }
}

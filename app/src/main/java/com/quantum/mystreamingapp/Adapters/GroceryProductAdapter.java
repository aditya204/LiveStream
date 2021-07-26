package com.quantum.mystreamingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quantum.mystreamingapp.Models.GroceryProductModel;
import com.quantum.mystreamingapp.ProductsDetaials;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.offlineProductDetails;


import java.util.Date;
import java.util.List;

public class GroceryProductAdapter extends RecyclerView.Adapter<GroceryProductAdapter.ViewHolder> {

    private List<GroceryProductModel> groceryProductModelList;



    public GroceryProductAdapter(List<GroceryProductModel> groceryProductModelList) {
        this.groceryProductModelList = groceryProductModelList;
    }

    public List<GroceryProductModel> getGroceryProductModelList() {
        return groceryProductModelList;
    }

    public void setGroceryProductModelList(List<GroceryProductModel> groceryProductModelList) {
        this.groceryProductModelList = groceryProductModelList;
    }

    @NonNull
    @Override
    public GroceryProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.offline_product_item, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryProductAdapter.ViewHolder holder, int position) {
        String name = groceryProductModelList.get( position ).getName( );

        String offerType = groceryProductModelList.get( position ).getOffertype( );
        String offerAmount = groceryProductModelList.get( position ).getOfferAmount( );
        String price = groceryProductModelList.get( position ).getPrice( );
        String rating = groceryProductModelList.get( position ).getRating( );
        String reviewCount = groceryProductModelList.get( position ).getReviewCount( );
        String id = groceryProductModelList.get( position ).getId( );
        String tag=groceryProductModelList.get( position ).getTag_list();
        boolean is_timmer=groceryProductModelList.get( position ).isIs_timmer();
        Date date=groceryProductModelList.get( position ).getDate();
        long inStock = groceryProductModelList.get( position ).getStock( );
        String image = groceryProductModelList.get( position ).getImage( );


        if(is_timmer){
            holder.setTv( date );
        }

        holder.setData( name, offerType, price, offerAmount, inStock, rating, reviewCount, image, id,tag,is_timmer );




    }

    @Override
    public int getItemCount() {
        return groceryProductModelList.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, offer, cutprice, price, rating, reviewCount,no_of_stock,tv;
        private ImageView image;
        private LinearLayout rating_ll;
        private ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull final View itemView) {
            super( itemView );

            name = itemView.findViewById( R.id.grocery_home_product_Name );
            tv=itemView.findViewById( R.id.text_view );
            offer = itemView.findViewById( R.id.grocery_home_product_Offer );
            cutprice = itemView.findViewById( R.id.grocery_home_product_CutPrice );
            price = itemView.findViewById( R.id.grocery_home_product_Price );
            image = itemView.findViewById( R.id.grocery_home_productImage );
            no_of_stock=itemView.findViewById( R.id.no_of_stock );
            constraintLayout = itemView.findViewById( R.id.grocery_home_product_layout );
            rating = itemView.findViewById( R.id.grocery_home_product_Rating );
            reviewCount = itemView.findViewById( R.id.grocery_home_product_ReviewCount );
            rating_ll=itemView.findViewById( R.id.grocery_home_product_Rating_LL );


        }

        private void setTv(Date date){

            offer.setVisibility( View.GONE );
            cutprice.setVisibility( View.GONE );
            rating_ll.setVisibility( View.GONE );
            reviewCount.setVisibility( View.GONE );


            long timmer_date=date.getTime();
            long now=new Date( ) .getTime();
            long distance=timmer_date-now;

            if(distance<=0){
                tv.setText("Winner final");

            }else {
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

                        tv.setText(hours_s +":"+minutes_s +":"+ seconds_s );





                    }

                    public void onFinish() {

                    }
                }.start();

            }





        }








        @SuppressLint("RestrictedApi")
        private void setData(final String Name, String cutPrice, String Price, String OfferAmount, long instock, String Rating, String ReviewCount, String resource, final String Id, final String Tag,boolean isTimmer) {

            if (isTimmer) {

                image.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent( itemView.getContext(), ProductsDetaials.class );
                        intent.putExtra( "product_id",Id);
                        itemView.getContext().startActivity( intent );
                    }
                } );

            }else {
                tv.setVisibility( View.GONE );

                image.setOnClickListener( new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent( itemView.getContext(), offlineProductDetails.class );
                        intent.putExtra( "product_id",Id);
                        itemView.getContext().startActivity( intent );
                    }
                } );
            }



            price.setText( "RM"+Price+"/-" );
            cutprice.setText( "RM"+cutPrice+"/-" );


            name.setText( Name );
            offer.setText( OfferAmount + " off" );




            if (OfferAmount.equals( "0" )) {
                offer.setVisibility( View.GONE );
                cutprice.setVisibility( View.GONE );
            }


            if(Rating.length()==1){

                rating_ll.setVisibility( View.GONE );
                reviewCount.setVisibility( View.GONE );

            }
            rating.setText( Rating );
            reviewCount.setText( "(" + ReviewCount + ")" );

            Glide.with( itemView.getContext( ) ).load( resource ).into( image );




        }




    }
}

package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.quantum.mystreamingapp.Models.HomeProductModel;
import com.quantum.mystreamingapp.ProductsDetaials;
import com.quantum.mystreamingapp.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ViewHolder> {

    List<HomeProductModel> homeProductModelList;


    public HomeProductAdapter(List<HomeProductModel> homeProductModelList) {
        this.homeProductModelList = homeProductModelList;
    }

    @NonNull
    @Override
    public HomeProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.home_product_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductAdapter.ViewHolder holder, int position) {

        String nmae=homeProductModelList.get( position ).getProduct_nmae();
        String Bidp=homeProductModelList.get( position ).getBid_price();
        String image=homeProductModelList.get( position ).getImage();
        String id=homeProductModelList.get( position ).getProduct_id();
        Date date=homeProductModelList.get( position ).getDate();

       holder.setTv(date);
        holder.setDats( nmae,Bidp,image,id  );

    }

    @Override
    public int getItemCount() {
        return homeProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv,nmae,bid;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            tv=itemView.findViewById( R.id.text_view );
            nmae=itemView.findViewById( R.id.product_name );
            bid=itemView.findViewById( R.id.product_bid );
            imageView=itemView.findViewById( R.id. product_image);

        }

        private void setTv(Date date){
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

        private void setDats(String Name,String Bid,String Image,String id){


           nmae.setText(Name );
           bid.setText(" RM " +Bid );
            Glide.with( itemView.getContext() ).load( Image ).into( imageView );
            imageView.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent( itemView.getContext(), ProductsDetaials.class );
                    intent.putExtra( "product_id",id );
                    itemView.getContext().startActivity(intent );
                }
            } );
            nmae.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent( itemView.getContext(), ProductsDetaials.class );
                    intent.putExtra( "product_id",id );
                    itemView.getContext().startActivity(intent );                }
            } );




            bid.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent( itemView.getContext(), ProductsDetaials.class );
                    intent.putExtra( "product_id",id );
                    itemView.getContext().startActivity(intent );                }
            } );



        }
    }
}

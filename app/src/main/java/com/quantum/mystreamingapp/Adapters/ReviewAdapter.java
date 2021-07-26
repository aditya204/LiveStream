package com.quantum.mystreamingapp.Adapters;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.quantum.mystreamingapp.Models.ReviewModel;
import com.quantum.mystreamingapp.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter< ReviewAdapter.ViewHolder> {

    private List<ReviewModel> reviewModelList;

    public ReviewAdapter(List<ReviewModel> reviewModelList) {
        this.reviewModelList = reviewModelList;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.review_recycler_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {

        String user_name=reviewModelList.get( position ).getUsre_name();
        String rating=reviewModelList.get( position ).getRating();
        String review_content=reviewModelList.get( position ).getReview_content();
        String date=reviewModelList.get(position).getDate();
        String image=reviewModelList.get(position ).getImage();


        holder.setStar( rating );
        holder.setData( user_name,date,review_content,image );

    }

    @Override
    public int getItemCount()
    {
        if(reviewModelList.size()>4) {
            return 4;
        }else {
            return reviewModelList.size() ;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView pic;
        private TextView user_name,product_name,header,review,date;
        private ImageView star1,star2,star3,star4,star5;



        public ViewHolder(@NonNull View itemView) {
            super( itemView );


            pic=itemView.findViewById( R.id.review_profile_image );
            user_name=itemView.findViewById( R.id.review_UserName );
            date=itemView.findViewById( R.id.review_date );
            product_name=itemView.findViewById( R.id.review_productName);
            header=itemView.findViewById( R.id.review_header );
            review=itemView.findViewById( R.id.review_content );
            star1=itemView.findViewById( R.id.review_star1 );
            star2=itemView.findViewById( R.id.review_star2 );
            star3=itemView.findViewById( R.id.review_star3 );
            star4=itemView.findViewById( R.id.review_star4 );
            star5=itemView.findViewById( R.id.review_star5 );


        }




        private void setData(String User_name, String Daate, String Review, String IMage){

            product_name.setVisibility( View.GONE );
            header.setVisibility( View.GONE );
            user_name.setText( User_name );
            date.setText( Daate  );
            review.setText( Review );

//            if(IMage!=null){
//                Glide.with( itemView.getContext() ).load( IMage ).into( pic );
//            }else {
//                pic.setImageResource( R.drawable.ic_account_circle_black_24dp );
//
//            }


        }

      private void setStar(String Rating){

            if(Rating.equals( "1" )){
                header.setText( "Poor" );
                header.setTextColor( Color.parseColor( "#FFFF0000" ) );
                star1.setImageResource( R.drawable.ic_star_green_24dp );
                star2.setImageResource( R.drawable.ic_star_border_black_24dp );
                star4.setImageResource( R.drawable.ic_star_border_black_24dp );
                star3.setImageResource( R.drawable.ic_star_border_black_24dp );
                star5.setImageResource( R.drawable.ic_star_border_black_24dp );


            }
            if(Rating.equals( "2" )){
                header.setText( "Good" );
                header.setTextColor( Color.parseColor( "#FFFF8400" ) );

                star1.setImageResource( R.drawable.ic_star_green_24dp );
                star2.setImageResource( R.drawable.ic_star_green_24dp );
                star3.setImageResource( R.drawable.ic_star_border_black_24dp );
                star4.setImageResource( R.drawable.ic_star_border_black_24dp );
                star5.setImageResource( R.drawable.ic_star_border_black_24dp );

            }
            if(Rating.equals( "3" )){
                header.setText( "Very Good" );
                header.setTextColor( Color.parseColor( "#FFFF8400" ) );

                star1.setImageResource( R.drawable.ic_star_green_24dp );
                star2.setImageResource( R.drawable.ic_star_green_24dp );
                star3.setImageResource( R.drawable.ic_star_green_24dp );
                star4.setImageResource( R.drawable.ic_star_border_black_24dp );
                star5.setImageResource( R.drawable.ic_star_border_black_24dp );

            }
            if(Rating.equals( "4" )){
                header.setText( "Excellent" );
                header.setTextColor( Color.parseColor( "#FF11FF00" ) );
                star1.setImageResource( R.drawable.ic_star_green_24dp );
                star2.setImageResource( R.drawable.ic_star_green_24dp );
                star4.setImageResource( R.drawable.ic_star_green_24dp );
                star3.setImageResource( R.drawable.ic_star_green_24dp );
                star5.setImageResource( R.drawable.ic_star_border_black_24dp );


            }
            if(Rating.equals( "5" )){
                header.setText( "Great" );
                header.setTextColor( Color.parseColor( "#FF11FF00" ) );
                star1.setImageResource( R.drawable.ic_star_green_24dp );
                star2.setImageResource( R.drawable.ic_star_green_24dp );
                star4.setImageResource( R.drawable.ic_star_green_24dp );
                star3.setImageResource( R.drawable.ic_star_green_24dp );
                star5.setImageResource( R.drawable.ic_star_green_24dp );
            }




        }










    }


}

package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quantum.mystreamingapp.Models.NotificationAlertModel;
import com.quantum.mystreamingapp.Models.NotificationDealsModel;
import com.quantum.mystreamingapp.OrderDetails;
import com.quantum.mystreamingapp.ProductsDetaials;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.offlineProductDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationDealAdapter extends RecyclerView.Adapter<NotificationDealAdapter.ViewHolder> {

    List<NotificationDealsModel> notificationAlertModelList;

    public NotificationDealAdapter(List<NotificationDealsModel> notificationAlertModelList) {
        this.notificationAlertModelList = notificationAlertModelList;
    }

    @NonNull
    @Override
    public NotificationDealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.notification_deals_item, parent, false );
        return new ViewHolder( view );    }

    @Override
    public void onBindViewHolder(@NonNull NotificationDealAdapter.ViewHolder holder, int position) {
        String image=notificationAlertModelList.get( position ).getImage();
        String headerr=notificationAlertModelList.get( position ).getHeader();
        String time=notificationAlertModelList.get( position ).getTime();
        String desc=notificationAlertModelList.get( position ).getDescription();
        String id=notificationAlertModelList.get( position ).getId();
        String pid=notificationAlertModelList.get( position ).getProduct_id();
        String seller_id=notificationAlertModelList.get( position ).getDescription();
        boolean is_timmer=notificationAlertModelList.get( position ).isIs_timmer();
        boolean is_reeded=notificationAlertModelList.get( position ).isIs_readed();



        holder.setData( image,time,desc,headerr,id,seller_id,pid,is_timmer,is_reeded );
    }

    @Override
    public int getItemCount() {
        return notificationAlertModelList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView header,time,descriptin;
        ImageView imageView;
        LinearLayout content_LL;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            header=itemView.findViewById( R.id.notification_deals_item_header );
            time=itemView.findViewById( R.id.notification_deals_item_time_date );

            descriptin=itemView.findViewById( R.id.notification_deals_item_description );
            imageView=itemView.findViewById( R.id.notification_deals_item_image);
            content_LL=itemView.findViewById( R.id.content_LL );

        }

        private void setData(String  Image,String Time,String Desc,String Header,String id,String seller_id,String product_id,boolean isreaded,boolean isTimmer){

            header.setText( Header );
            time.setText( Time );
            Glide.with( itemView.getContext()).load( Image ).into( imageView );
            descriptin.setText( Desc );
            content_LL.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    if(isreaded){
                        if(isTimmer){
                            Intent intent=new Intent( itemView.getContext(), ProductsDetaials.class );

                            intent.putExtra( "product_id",product_id );
                            itemView.getContext().startActivity( intent );
                        }else {
                            Intent intent=new Intent( itemView.getContext(), offlineProductDetails.class );
                            intent.putExtra( "product_id",product_id );
                            itemView.getContext().startActivity( intent );
                        }

                    }else {
                        content_LL.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );

                        Map<String,Object> map= new HashMap<>(  );
                        map.put( "is_readed",true );

                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "DEALS" )
                                .document( id ).update( map ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                }
                            }
                        } );
                        if(isTimmer){
                            Intent intent=new Intent( itemView.getContext(), ProductsDetaials.class );

                            intent.putExtra( "product_id",product_id );
                            itemView.getContext().startActivity( intent );
                        }else {
                            Intent intent=new Intent( itemView.getContext(), offlineProductDetails.class );
                            intent.putExtra( "product_id",product_id );
                            itemView.getContext().startActivity( intent );
                        }




                    }
                }
            } );


        }




    }

}

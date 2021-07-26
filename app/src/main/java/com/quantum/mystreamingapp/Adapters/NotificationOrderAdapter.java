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
import com.quantum.mystreamingapp.Models.NotificationModelClass;
import com.quantum.mystreamingapp.OrderDetails;
import com.quantum.mystreamingapp.Orders;
import com.quantum.mystreamingapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationOrderAdapter extends RecyclerView.Adapter<NotificationOrderAdapter.ViewHolder> {

    List<NotificationModelClass> notificationModelClassList;

    public NotificationOrderAdapter(List<NotificationModelClass> notificationModelClassList) {
        this.notificationModelClassList = notificationModelClassList;
    }

    @NonNull
    @Override
    public NotificationOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.notification_order_item, parent, false );
        return new ViewHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationOrderAdapter.ViewHolder holder, int position) {

        String image=notificationModelClassList.get( position ).getImage();
        String name=notificationModelClassList.get( position ).getProduct_name();
        String oid=notificationModelClassList.get( position ).getOrder_id();
        String headerr=notificationModelClassList.get( position ).getHeader();
        String time=notificationModelClassList.get( position ).getTime();
        String desc=notificationModelClassList.get( position ).getDescription();
        boolean is_readed=notificationModelClassList.get( position ).isIs_readed();
        boolean isSeller=notificationModelClassList.get( position ).isSeller();
        String id=notificationModelClassList.get( position ).getId();
        String product_id=notificationModelClassList.get( position ).getId();


        holder.setData( name,image,time,oid,desc,headerr,is_readed,isSeller,product_id,id);

    }

    @Override
    public int getItemCount() {
        return notificationModelClassList.size();
    }

    TextView header,time,order_id,name,descriptin;
    ImageView imageView;
    LinearLayout content_LL;

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            header=itemView.findViewById( R.id.notification_order_item_header );
            time=itemView.findViewById( R.id.notification_order_item_time_date );
            order_id=itemView.findViewById( R.id.notification_order_item_order_id );
            name=itemView.findViewById( R.id.notification_order_item_name );
            descriptin=itemView.findViewById( R.id.notification_order_item_description );
            imageView=itemView.findViewById( R.id.notification_order_item_image);
            content_LL=itemView.findViewById( R.id.notificatin_order_content_ll);

        }

        private void setData(String Nmae,String  Image,String Time,String Order_id,String Desc,String Header, boolean is_readed,boolean is_seller,String Product_id,String id){

            header.setText( Header );
            time.setText( Time );
            name.setText( Nmae );
            Glide.with( itemView.getContext()).load( Image ).into( imageView );
            order_id.setText( "Order id :- "+Order_id );
            descriptin.setText( Desc );

            if(is_readed){
                content_LL.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );

            }else {
                content_LL.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#E3E3E3" ) ) );
            }

            content_LL.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    if(is_readed){



                    }else {
                        content_LL.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );

                        Map<String,Object> map= new HashMap<>(  );
                        map.put( "is_readed",true );

                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ORDER" )
                                .document( id ).update( map ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                }
                            }
                        } );
                        if(is_seller){
                            Intent intent=new Intent( itemView.getContext(), OrderDetails.class );
                            intent.putExtra( "order_id",Order_id );
                            intent.putExtra( "product_id",Product_id );
                            itemView.getContext().startActivity( intent );
                        }else {
                            Intent intent=new Intent( itemView.getContext(), OrderDetails.class );
                            intent.putExtra( "order_id",Order_id );
                            intent.putExtra( "product_id",Product_id );
                            itemView.getContext().startActivity( intent );
                        }




                    }
                }
            } );


        }




    }
}

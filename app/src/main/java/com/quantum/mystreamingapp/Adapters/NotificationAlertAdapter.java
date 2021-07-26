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
import com.quantum.mystreamingapp.OrderDetails;
import com.quantum.mystreamingapp.Profile;
import com.quantum.mystreamingapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationAlertAdapter extends RecyclerView.Adapter<NotificationAlertAdapter.ViewHolder> {

    List<NotificationAlertModel> notificationAlertModelList;

    public NotificationAlertAdapter(List<NotificationAlertModel> notificationAlertModelList) {
        this.notificationAlertModelList = notificationAlertModelList;
    }

    @NonNull
    @Override
    public NotificationAlertAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.notification_alert_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAlertAdapter.ViewHolder holder, int position) {

        String image = notificationAlertModelList.get( position ).getImage( );
        String headerr = notificationAlertModelList.get( position ).getHeader( );
        String time = notificationAlertModelList.get( position ).getTime( );
        String desc = notificationAlertModelList.get( position ).getDescription( );
        String follower_id = notificationAlertModelList.get( position ).getFollower_id( );
        String id = notificationAlertModelList.get( position ).getId( );
        boolean is_readed= notificationAlertModelList.get( position ).isIs_readed();

        holder.setData( image, time, desc, headerr, follower_id, id ,is_readed);
    }

    @Override
    public int getItemCount() {
        return notificationAlertModelList.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView header, time, descriptin;
        ImageView imageView;
        LinearLayout content_ll;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            header = itemView.findViewById( R.id.notification_alert_item_header );
            time = itemView.findViewById( R.id.notification_alert_item_time_date );

            descriptin = itemView.findViewById( R.id.notification_alert_item_description );
            imageView = itemView.findViewById( R.id.notification_alert_item_image );
            content_ll = itemView.findViewById( R.id.content_ll );

        }

        private void setData(String Image, String Time, String Desc, String Header, String Follower_id, String Id, boolean is_readed) {

            header.setText( Header );
            time.setText( Time );

            Glide.with( itemView.getContext( ) ).load( Image ).into( imageView );
            descriptin.setText( Desc );

            if (is_readed) {
                content_ll.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );

            } else {
                content_ll.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#E3E3E3" ) ) );
            }

            content_ll.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    if (is_readed) {

                        Intent intent = new Intent( itemView.getContext( ), Profile.class );
                        intent.putExtra( "owner_id", Follower_id );
                        intent.putExtra( "layout_code", 1 );
                        itemView.getContext( ).startActivity( intent );

                    } else {
                        content_ll.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( "#FFFFFF" ) ) );

                        Map<String, Object> map = new HashMap<>( );
                        map.put( "is_readed", true );

                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) ).collection( "USER_DATA" ).document( "MY_NOTIFICATION" ).collection( "ALERT" )
                                .document( Id ).update( map ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {

                                }
                            }
                        } );

                        Intent intent = new Intent( itemView.getContext( ), Profile.class );
                        intent.putExtra( "owner_id", Follower_id );
                        intent.putExtra( "layout_code", 1 );
                        itemView.getContext( ).startActivity( intent );


                    }
                }
            } );


        }


    }

}

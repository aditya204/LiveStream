package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quantum.mystreamingapp.Models.PlayerModel;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.offlineProductDetails;
import com.quantum.mystreamingapp.player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    List<PlayerModel> playerModelList;

    public PlayerAdapter(List<PlayerModel> playerModelList) {
        this.playerModelList = playerModelList;
    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.player_recycler_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        String URL = playerModelList.get( position ).getUrl();
        String image=playerModelList.get( position ).getImage();
        String profile_pic=playerModelList.get( position ).getProfile_pic();
        String user_name=playerModelList.get( position ).getUser_name();
        String time=playerModelList.get( position ).getTime();
        String Id=playerModelList.get( position ).getProduct_id();
        holder.setUrl( URL,profile_pic,user_name,time,image,Id);
    }

    @Override
    public int getItemCount() {
        return playerModelList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView user_name,time;
        ImageView thumbnail,account_img;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            user_name=itemView.findViewById( R.id.user_name );
            thumbnail=itemView.findViewById( R.id.player_recycler_image  );
            account_img=itemView.findViewById( R.id.imageView2 );
            time=itemView.findViewById( R.id.textView2 );




        }

        private void setUrl(String URi,String Profile_image,String User_name,String Time,String Display_image,String P_id){


            if(Profile_image.isEmpty()){
                account_img.setImageResource( R.drawable.ic_baseline_account_circle_24 );
            }else {
                Glide.with( itemView.getContext() ).load( Profile_image ).into( account_img );
            }
            Glide.with( itemView.getContext() ).load( Display_image ).into( thumbnail );
            time.setText( Time );
            user_name.setText( User_name );

            thumbnail.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent( itemView.getContext(), player.class );
                    intent.putExtra( "product_id",P_id);
                    itemView.getContext().startActivity( intent );
                }
            } );

            user_name.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent( itemView.getContext(), player.class );
                    intent.putExtra( "product_id",P_id);
                    itemView.getContext().startActivity( intent );
                }
            } );
        }
    }
}

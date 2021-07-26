package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quantum.mystreamingapp.HomeMain;
import com.quantum.mystreamingapp.Models.dealsofthedayModel;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.offline_products;
import com.quantum.mystreamingapp.player_recycler;


import java.util.List;

public class dealsofthedayAdapter extends RecyclerView.Adapter<dealsofthedayAdapter.ViewHolder> {


    private List<dealsofthedayModel> dealsofthedayModelList;

    public dealsofthedayAdapter(List<dealsofthedayModel> dealsofthedayModelList) {
        this.dealsofthedayModelList = dealsofthedayModelList;
    }

    @NonNull
    @Override
    public dealsofthedayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.dealsoftheday_recycler_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull dealsofthedayAdapter.ViewHolder holder, int position) {
        String title = dealsofthedayModelList.get( position ).getTitle( );
        String descri = dealsofthedayModelList.get( position ).getDescription( );
        String price = dealsofthedayModelList.get( position ).getPrice( );
        String image = dealsofthedayModelList.get( position ).getImage( );
        String id = dealsofthedayModelList.get( position ).getId( );
        String tag= dealsofthedayModelList.get( position ).getTag();

        holder.setData( image, descri, title, price, id,tag );

    }

    @Override
    public int getItemCount() {
        return dealsofthedayModelList.size( );
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            image = itemView.findViewById( R.id.dealsofthedayitemimage );
            title = itemView.findViewById( R.id.dealsofthedayitemname );

        }


        private void setData(String resource, String Description, final String Title, String Price, final String id, final String tag) {

            Glide.with( itemView.getContext( ) ).load( resource ).into( image );
            title.setText( Title );


            image.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    if(Title.contains( "countdown" )){
                        Intent intent = new Intent( view.getContext( ), HomeMain.class );
                        view.getContext( ).startActivity( intent );
                    }else {
                        String Ttle=Title.toLowerCase().replaceAll("\\s","_" );
                        Intent intent = new Intent( view.getContext( ), player_recycler.class );
                        intent.putExtra( "category",Ttle );
                        view.getContext( ).startActivity( intent );

                    }



                }
            } );
        }


    }
}

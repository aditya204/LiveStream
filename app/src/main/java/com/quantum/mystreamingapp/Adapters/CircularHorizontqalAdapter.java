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
import com.quantum.mystreamingapp.Models.HomeCategoryModel;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.offline_products;


import java.util.List;

public class CircularHorizontqalAdapter extends RecyclerView.Adapter<CircularHorizontqalAdapter.ViewHolder> {

    private List<HomeCategoryModel> circularHorizontalList;

    public CircularHorizontqalAdapter(List<HomeCategoryModel> circularHorizontalList) {
        this.circularHorizontalList = circularHorizontalList;
    }

    @NonNull
    @Override
    public CircularHorizontqalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.circular_horizontal_itam, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CircularHorizontqalAdapter.ViewHolder holder, int position) {

        String name= circularHorizontalList.get( position ).getName();
        String image=circularHorizontalList.get( position ).getImage();
       // String tag=circularHorizontalList.get( position ).getTag();

        holder.setData( name,image,"" );

    }

    @Override
    public int getItemCount() {
        return circularHorizontalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            productImage=itemView.findViewById( R.id.circular_horizontal_item_image );
            name=itemView.findViewById( R.id.circular_horizontal_item_name );
        }

        private void setData(final String Nmae, String url, final String Tag){
            name.setText( Nmae );
            Glide.with( itemView.getContext()).load( url ).into( productImage );
            productImage.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( view.getContext( ), offline_products.class );
                    view.getContext( ).startActivity( intent );
//                    Intent intent=new Intent( itemView.getContext(), SearchedProduct.class );
//                    intent.putExtra( "tag_string",Tag );
//                   itemView.getContext().startActivity( intent );
                }
            } );
        }




    }
}

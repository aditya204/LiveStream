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
import com.quantum.mystreamingapp.AddProductCategory;
import com.quantum.mystreamingapp.Models.LiveCategoryModel;
import com.quantum.mystreamingapp.R;

import java.util.List;

public class LiveCategoryAdapter extends RecyclerView.Adapter<LiveCategoryAdapter.ViewHolder> {
    List<LiveCategoryModel> liveCategoryModels;

    public LiveCategoryAdapter(List<LiveCategoryModel> liveCategoryModels) {
        this.liveCategoryModels = liveCategoryModels;
    }

    @NonNull
    @Override
    public LiveCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.live_categoy_recycler_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull LiveCategoryAdapter.ViewHolder holder, int position) {

        String nmae = liveCategoryModels.get( position ).getName( );
        String Image=liveCategoryModels.get( position ).getImage();


        holder.setNmae( nmae,Image );

    }

    @Override
    public int getItemCount() {
        return liveCategoryModels.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            name = itemView.findViewById( R.id.live_category_recycler_item_name );
            image = itemView.findViewById( R.id.live_category_recycler_item_image );



        }
        public void setNmae(String Name,String Image ){
            Glide.with( itemView.getContext() ).load( Image ).into( image );

            image.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( itemView.getContext( ), AddProductCategory.class );
                    intent.putExtra( "category_name", Name );
                    itemView.getContext().startActivity( intent );
                }
            } );

        }

    }

}

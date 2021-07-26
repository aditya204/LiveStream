package com.quantum.mystreamingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quantum.mystreamingapp.R;

import java.util.ArrayList;
import java.util.List;

public class AddProductDetailsAdapter extends RecyclerView.Adapter<AddProductDetailsAdapter.ViewHolder> {

    List<String> imageList;

    public AddProductDetailsAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public AddProductDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.add_product_details_recycler_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductDetailsAdapter.ViewHolder holder, int position) {
        String url=imageList.get( position );

        holder.setImageView( url );
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            imageView=itemView.findViewById( R.id.add_product_category_recycler_item_image );

        }

        public void setImageView(String URL){
            Glide.with( itemView.getContext() ).load( URL ).into( imageView );

        }


    }

}

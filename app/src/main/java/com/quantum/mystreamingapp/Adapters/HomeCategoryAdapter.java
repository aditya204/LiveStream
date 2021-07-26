package com.quantum.mystreamingapp.Adapters;

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

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    List<HomeCategoryModel> homeCategoryModelList;

    public HomeCategoryAdapter(List<HomeCategoryModel> homeCategoryModelList) {
        this.homeCategoryModelList = homeCategoryModelList;
    }



    @NonNull
    @Override
    public HomeCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.home_category_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.ViewHolder holder, int position) {

        String name=homeCategoryModelList.get( position ).getName();
        String Image=homeCategoryModelList.get( position ).getImage();

        holder.setName( name,Image );





    }

    @Override
    public int getItemCount() {
        return homeCategoryModelList.size();
    }



    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name;
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            name=itemView.findViewById( R.id.grocery_home_category_title);
            image=itemView.findViewById( R.id.grocery_home_category_icon );
        }

        private void setName(String nmae,String Image){

            name.setText( nmae );
            Glide.with( itemView.getContext() ).load( Image ).into( image );


        }
    }
}

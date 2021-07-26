package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantum.mystreamingapp.AddProductCategory;
import com.quantum.mystreamingapp.AddProductDetails;
import com.quantum.mystreamingapp.Models.AddProductCategoryModel;
import com.quantum.mystreamingapp.R;

import java.util.List;

public class AddProductCategoryAdapter extends RecyclerView.Adapter<AddProductCategoryAdapter.ViewHolder> {
    List<AddProductCategoryModel> addProductCategoryModelList;

    public AddProductCategoryAdapter(List<AddProductCategoryModel> addProductCategoryModelList) {
        this.addProductCategoryModelList = addProductCategoryModelList;
    }





    @NonNull
    @Override
    public AddProductCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.add_product_category_item, parent, false );
        return new ViewHolder( view );    }

    @Override
    public void onBindViewHolder(@NonNull AddProductCategoryAdapter.ViewHolder holder, int position) {
        String cate=addProductCategoryModelList.get( position ).getCategory();
        holder.categoryNmae.setText( cate );

        holder.setCategoryNmae( cate );


    }

    @Override
    public int getItemCount() {
        return addProductCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryNmae;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            categoryNmae=itemView.findViewById( R.id.category_name );



        }

        public void setCategoryNmae(String name){
            categoryNmae.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent( itemView.getContext(), AddProductDetails.class );
                    intent.putExtra( "live_display_image", AddProductCategory.imageUrl );
                    intent.putExtra( "category_name", AddProductCategory.live_category );
                    intent.putExtra( "sub_category_name",name);
                    itemView.getContext().startActivity( intent );
                }
            } );

        }
    }
}

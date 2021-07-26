package com.quantum.mystreamingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.quantum.mystreamingapp.Models.grocery_product_details_descrioption_Model;
import com.quantum.mystreamingapp.R;

import java.util.List;

public class grocery_product_details_descrioption_Adapter extends RecyclerView.Adapter<grocery_product_details_descrioption_Adapter.ViewHolder> {

    private List<grocery_product_details_descrioption_Model> grocery_product_details_descrioption_modelList;

    public grocery_product_details_descrioption_Adapter(List<grocery_product_details_descrioption_Model> grocery_product_details_descrioption_modelList) {
        this.grocery_product_details_descrioption_modelList = grocery_product_details_descrioption_modelList;
    }

    @NonNull
    @Override
    public grocery_product_details_descrioption_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.grocery_product_details_description_item,parent,false);
        return new ViewHolder( view );    }

    @Override
    public void onBindViewHolder(@NonNull grocery_product_details_descrioption_Adapter.ViewHolder holder, int position) {

        String txt=grocery_product_details_descrioption_modelList.get( position ).getDescription();

        holder.setDescription( txt );
    }

    @Override
    public int getItemCount() {
        return grocery_product_details_descrioption_modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView descti;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
           descti =itemView.findViewById( R.id.description_txt );

        }

        private void setDescription(String Description ){

           descti.setText("-"+Description );


        }
    }
}

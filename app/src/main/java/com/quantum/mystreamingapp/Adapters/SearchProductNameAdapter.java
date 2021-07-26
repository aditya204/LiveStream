package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.quantum.mystreamingapp.DBquaries;
import com.quantum.mystreamingapp.ProductsDetaials;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchProductNameAdapter extends RecyclerView.Adapter<SearchProductNameAdapter.ViewHolder> implements Filterable {

    private List<String> searchProductNameModelList;
    private List<String> allProducts;

    public SearchProductNameAdapter(List<String> searchProductNameModelList) {
        this.searchProductNameModelList = searchProductNameModelList;
        this.allProducts = new ArrayList<>( searchProductNameModelList );
    }

    @NonNull
    @Override
    public SearchProductNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.search_product_name_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductNameAdapter.ViewHolder holder, int position) {


        holder.textView.setText( searchProductNameModelList.get( position ) );
        holder.setData( searchProductNameModelList.get( position ), position );
    }

    @Override
    public int getItemCount() {


        return searchProductNameModelList.size( );
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    Filter filter = new Filter( ) {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> filteredList = new ArrayList<>( );

            if (charSequence.toString( ).isEmpty( )) {
                filteredList.addAll( allProducts );
            } else {
                for (String list : allProducts) {
                    if (list.toLowerCase( ).contains( charSequence.toString( ).toLowerCase( ) )) {

                        filteredList.add( list );
                    }
                }
            }

            FilterResults filterResults = new FilterResults( );
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            searchProductNameModelList.clear( );
            searchProductNameModelList.addAll( (Collection<? extends String>) filterResults.values );
            notifyDataSetChanged( );

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull final View itemView) {
            super( itemView );
            textView = itemView.findViewById( R.id.search_product_item_name );

        }

        private void setData(final String name, final int index) {


            textView.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                    if(DBquaries.product_is_live_list.get( DBquaries.product_list.indexOf( name ) )){
                        Intent intent = new Intent( itemView.getContext( ), ProductsDetaials.class );
                        intent.putExtra( "product_id", DBquaries.product_id_list.get( DBquaries.product_list.indexOf( name ) ) );
                        itemView.getContext( ).startActivity( intent );
                    }else {
                        Intent intent = new Intent( itemView.getContext( ), player.class );
                        intent.putExtra( "url", DBquaries.product_stream_id_list.get(  DBquaries.product_list.indexOf( name ) ) );
                        intent.putExtra( "product_id", DBquaries.product_id_list.get( DBquaries.product_list.indexOf( name ) ) );
                        itemView.getContext( ).startActivity( intent );
                    }


                }
            } );
        }

    }


}

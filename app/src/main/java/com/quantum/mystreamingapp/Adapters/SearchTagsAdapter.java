package com.quantum.mystreamingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.quantum.mystreamingapp.Models.SearchTagsModels;
import com.quantum.mystreamingapp.R;

import java.util.List;

public class SearchTagsAdapter extends RecyclerView.Adapter<SearchTagsAdapter.ViewHolder> {

    private List<SearchTagsModels> searchTagsModelsList;

    public SearchTagsAdapter(List<SearchTagsModels> searchTagsModelsList) {
        this.searchTagsModelsList = searchTagsModelsList;
    }

    @NonNull
    @Override
    public SearchTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.seach_tag_items, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTagsAdapter.ViewHolder holder, int position) {

        String tag=searchTagsModelsList.get( position ).getTags();

        holder.setData(tag);
    }

    @Override
    public int getItemCount() {
        return searchTagsModelsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            textView=itemView.findViewById( R.id. search_tag_item_text);
        }

        public void setData(final String tag) {

            textView.setText( tag );
            textView.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
//                    Intent intent=new Intent( itemView.getContext(), SearchedProduct.class );
//                    intent.putExtra( "tag_string", tag);
//                    itemView.getContext().startActivity( intent );
                }
            } );
        }
    }
}

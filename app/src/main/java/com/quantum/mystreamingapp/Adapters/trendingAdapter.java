package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.quantum.mystreamingapp.Models.dealsofthedayModel;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.SplashScreen;
import com.quantum.mystreamingapp.offline_products;
import com.quantum.mystreamingapp.player;


import java.util.List;

public class trendingAdapter extends BaseAdapter {

    List<dealsofthedayModel> gridmodelList;

    public trendingAdapter(List<dealsofthedayModel> gridmodelList) {
        this.gridmodelList = gridmodelList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from( viewGroup.getContext( ) ).inflate( R.layout.trending_item, null );
            view.setElevation( 2 );
            view.setBackgroundColor( Color.parseColor( "#ffffff" ) );
            ImageView imageView = view.findViewById( R.id.trending_image );
            final String id = gridmodelList.get( i ).getId( );
            String tag = gridmodelList.get( i ).getTag( );
            Glide.with( view.getContext( ) ).load( gridmodelList.get( i ).getImage( ) ).into( imageView );

            imageView.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( view.getContext( ), player.class );
                    intent.putExtra( "product_id",id );
                    view.getContext( ).startActivity( intent );

//                    for (int i = 0; i < SplashScreen.playerModelList.size( ); i++) {
//                        if (SplashScreen.playerModelList.get( i ).contains( tag )) {
//                            Intent intent = new Intent( view.getContext( ), player.class );
//                            intent.putExtra( "url", SplashScreen.playerModelList.get( i ) );
//                            intent.putExtra( "product_id", id );
//                            view.getContext( ).startActivity( intent );
//                        }
//                    }
                }
            } );


        } else {
            view = convertView;
        }
        return view;
    }
}

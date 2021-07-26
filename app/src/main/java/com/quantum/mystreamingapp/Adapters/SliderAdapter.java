package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.quantum.mystreamingapp.Models.sliderModel;
import com.quantum.mystreamingapp.R;
import com.quantum.mystreamingapp.offline_products;


import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private List<sliderModel> sliderModelList;

    public SliderAdapter(List<sliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        View view = LayoutInflater.from( container.getContext( ) ).inflate( R.layout.banner_slide_item, container, false );
        ImageView banner = view.findViewById( R.id.banner_slide );
        ConstraintLayout sliderCL=view.findViewById( R.id.sliderCL );
        final String tags=sliderModelList.get( position ).getTags();
        String backColor=sliderModelList.get( position ).getBackground();



        Glide.with( container.getContext() ).load(sliderModelList.get( position ).getBanner()).into( banner );
        container.addView( view, 0 );

        banner.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Toast.makeText( container.getContext(), "AFTER search part", Toast.LENGTH_SHORT ).show( );

//                Intent intent = new Intent( container.getContext( ), offline_products.class );
//                container.getContext( ).startActivity( intent );
//                if(!tags.isEmpty()) {
//
////                    Intent intent = new Intent( container.getContext( ), SearchedProduct.class );
////                    intent.putExtra( "tag_string", tags );
////                    container.getContext( ).startActivity( intent );
////
//                    }

            }
        } );
        return view;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (View)object );

    }


    @Override
    public int getCount() {
        return sliderModelList.size();
    }
}

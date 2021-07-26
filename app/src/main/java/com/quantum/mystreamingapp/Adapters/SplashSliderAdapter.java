package com.quantum.mystreamingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.quantum.mystreamingapp.Models.SplashSliderModel;
import com.quantum.mystreamingapp.R;

import java.util.List;

public class SplashSliderAdapter extends PagerAdapter {

    private List<SplashSliderModel> sliderModelList;

    public SplashSliderAdapter(List<SplashSliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from( container.getContext( ) ).inflate( R.layout.splash_slide_item, container, false );
        ImageView imageView = view.findViewById( R.id.splash_slider_image );
        TextView heading = view.findViewById( R.id.splash_slider_heading );
        TextView description = view.findViewById( R.id.splash_slider_descriptin );

        imageView.setImageResource( sliderModelList.get( position ).getImg() );
        //Glide.with( container.getContext() ).load(sliderModelList.get( position ).getImage()).into( imageView );
        heading.setText(  sliderModelList.get( position ).getHeading());
        description.setText(  sliderModelList.get( position ).getDescription());


        container.addView( view, 0 );
        return view;

    }


    @Override
    public int getCount() {
        return sliderModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (View)object );

    }
}

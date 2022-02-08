package com.example.patronus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.patronus.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public  SliderAdapter(Context context){
        this.context=context;
    }

    public int[] slide_images={
            R.drawable.girl,
//            R.drawable.police,
            R.drawable.first_aid_kit_big,
            R.drawable.girl,
            R.drawable.contacts
    };
    public String[] slide_headings={
            "",
//                "POLICE",
            "HOSPITAL",
            "HELPLINE",
            "CONTACTS"
    };

    public String[] slide_des={
            "Welcome To Patronus!!",
            "Get Information Of All The Nearby Hospitals On One Click.",
            "Get All The Necessary Helpline Numbers.",
            "Save Contacts Of Your Closeones Who Will Recieve Your Location When You Are In Danger Situation.",

    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView=(ImageView)view.findViewById(R.id.slide_image);

        if(position==0){
            slideImageView.setVisibility(View.GONE);
        }
        TextView slideHeading=(TextView)view.findViewById(R.id.slide_heading);
        TextView slideDescription=(TextView)view.findViewById(R.id.slide_description);


        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_des[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

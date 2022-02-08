package com.example.patronus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.patronus.MainActivity;
import com.example.patronus.R;
import com.example.patronus.SliderAdapter;

public class IntroSlides extends AppCompatActivity {
    private SliderAdapter sliderAdapter;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private int mcurrentPage;
    private Button mNextButton;
    private Button mPrevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slides);
        mSlideViewPager=(ViewPager)findViewById(R.id.slideViewPager);
        mDotLayout=(LinearLayout)findViewById(R.id.dots_layout);
//        mPrevButton=(Button)findViewById(R.id.prev);
        mNextButton=(Button)findViewById(R.id.next);
        mNextButton.setVisibility(View.INVISIBLE);
        mNextButton.setEnabled(false);
        sliderAdapter=new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mSlideViewPager.addOnPageChangeListener(viewListener);
        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor= prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();



    }
    public void addDotsIndicator(int position){
        mDots=new TextView[4];
        mDotLayout.removeAllViews();
        for(int i=0;i< mDots.length;i++){

            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.black));
            mDotLayout.addView(mDots[i]);

        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
        }



    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);


            if(position==3){
                mNextButton.setText("FINISH");
                mNextButton.setVisibility(View.VISIBLE);
                mNextButton.setEnabled(true);
            }
            else{
                mNextButton.setVisibility(View.INVISIBLE);
                mNextButton.setEnabled(false);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
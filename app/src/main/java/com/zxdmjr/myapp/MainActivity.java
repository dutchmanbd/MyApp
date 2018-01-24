package com.zxdmjr.myapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zxdmjr.myapp.adapters.SlideAdapter;
import com.zxdmjr.myapp.models.WelcomeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.ll_dots)
    LinearLayout llDots;

    @BindView(R.id.ib_prev)
    ImageButton ibPrev;

    @BindView(R.id.ib_next)
    ImageButton ibNext;

    private List<WelcomeMessage> welcomeMessages;

    private SlideAdapter adapter;

    private int currentPage=0;

    private TextView[] dots;

    private Timer timer;
    private int delaySecond = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setWelcomeMessage();

        adapter = new SlideAdapter(this, welcomeMessages);

        viewPager.setAdapter(adapter);

        addDotIndicator(0);


        viewPager.addOnPageChangeListener(viewListener);

        autoChangeListener();
        //viewPager.setCurrentItem(0);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null)
            timer.cancel();
    }

    private void autoChangeListener() {
        if(timer == null)
            timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, delaySecond * 1000); // delay
    }


    private void setWelcomeMessage() {

        welcomeMessages = new ArrayList<>();
        welcomeMessages.clear();

        String[] messages = getResources().getStringArray(R.array.messages);
        int[] icons = {R.drawable.ic_phone, R.drawable.ic_chatting, R.drawable.ic_stick_man, R.drawable.ic_mess,R.drawable.ic_report};

        for(int i =0; i<messages.length; i++){
            WelcomeMessage welcomeMessage = new WelcomeMessage(icons[i], messages[i]);
            welcomeMessages.add(welcomeMessage);
        }
    }

    private void addDotIndicator(int position){

        dots = new TextView[welcomeMessages.size()];
        llDots.removeAllViews();

        for(int i=0; i<dots.length; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentBlack));

            llDots.addView(dots[i]);

        }

        if(dots.length > 0){

            dots[position].setTextColor(getResources().getColor(R.color.colorSelectedDot));

        }

    }


    @OnClick(R.id.ib_next)
    void doNext(){


        if(viewPager.getCurrentItem() == dots.length-1){
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            finish();
        } else{
            //currentPage = currentPage+1;
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            delaySecond = 5;
        }
        //Log.d("MainActivity", "doNext: "+currentPage+" "+dots.length);
    }

    @OnClick(R.id.ib_prev)
    void doPrev(){
        //Log.d("MainActivity", "doPrev: "+currentPage);
        if(viewPager.getCurrentItem() > 0) {
            //currentPage = currentPage-1;
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            delaySecond = 5;
        }
    }



    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotIndicator(position);

            currentPage = position;

            //Log.d("MainActivity", "onPageSelected: "+currentPage);
            if(position == 0){

                ibPrev.setEnabled(false);
                ibPrev.setVisibility(View.GONE);
                ibNext.setVisibility(View.VISIBLE);

            } else if(position == dots.length-1){

                ibNext.setVisibility(View.GONE);

            } else{
                ibNext.setVisibility(View.VISIBLE);
                ibPrev.setEnabled(true);
                ibPrev.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    if (viewPager.getCurrentItem() == welcomeMessages.size()-1) { // In my case the number of pages are 5
                        //timer.cancel();
                        currentPage = 0;
                        viewPager.setCurrentItem(currentPage);
                        // Showing a toast for just testing purpose
//                        Toast.makeText(getApplicationContext(), "Timer stoped",
//                                Toast.LENGTH_LONG).show();
                    } else {
                        viewPager.setCurrentItem(currentPage++);
                    }
                }
            });

        }
    }


}

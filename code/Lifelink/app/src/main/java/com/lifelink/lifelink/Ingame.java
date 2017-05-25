package com.lifelink.lifelink;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Ingame extends AppCompatActivity {
    private int lifeTotal = 0;
    private int intialTime = 0;
    private int time = 0;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        final EditText display = (EditText) findViewById(R.id.lifeTotalDisplay);
        display.setBackgroundColor(Color.parseColor(playerProfile.getString("color", "#ffffff")));
        display.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(! display.getText().toString().equals("")) {
                        setLifeTotal(display);
                    } else {
                        Toast.makeText(Ingame.this,"Invalid input", Toast.LENGTH_SHORT).show();
                        updateLifeTotalDisplay(display);
                    }

                    //Hide the keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(display.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        final TextView textDisplay = (TextView) findViewById(R.id.time);
        //if player is host...
        lifeTotal = Integer.parseInt(playerProfile.getString("preferredLife","20"));
        display.setText(String.valueOf(this.getLifeTotal()));
        /**
        //Set time
        textDisplay.setText(playerProfile.getString("preferredTime","120"));
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                setTime(time-1, textDisplay);
            }
        }, 1000);
*/

        //Buttons
        Button plus1 = (Button) findViewById(R.id.plus1);
        plus1.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            MediaPlayer mp = MediaPlayer.create(Ingame.this, R.raw.supahotfire);

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    increment(1, display);
                    if (mHandler == null) return true;
                    mHandler.removeCallbacks(mAction);
                    mHandler.removeCallbacks(mPlay);
                    mHandler = null;
                    return true;
                } else {
                    // held down
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (mHandler != null) return true;
                            mHandler = new Handler();
                            mHandler.postDelayed(mAction, 500);
                            mHandler.postDelayed(mPlay, 1200);
                            break;
                        case MotionEvent.ACTION_UP:
                            if (mHandler == null) return true;
                            mHandler.removeCallbacks(mAction);
                            mHandler.removeCallbacks(mPlay);
                            mHandler = null;
                            break;
                    }
                    return false;
                }
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    increment(10, display);
                    mHandler.postDelayed(this, 500);
                }
            };

            Runnable mPlay = new Runnable() {
                @Override
                public void run() {
                    SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);
                    boolean sound = Boolean.parseBoolean(playerProfile.getString("sound", "true"));
                    if (sound) {
                        mp.start();
                        mHandler.postDelayed(this, 2000);
                    }
                }
            };
        });

        Button minus1 = (Button) findViewById(R.id.minus1);
        minus1.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    increment(-1, display);
                    if (mHandler == null) return true;
                    mHandler.removeCallbacks(mAction);
                    mHandler = null;
                    return true;
                } else {
                    // held down
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (mHandler != null) return true;
                            mHandler = new Handler();
                            mHandler.postDelayed(mAction, 500);
                            break;
                        case MotionEvent.ACTION_UP:
                            if (mHandler == null) return true;
                            mHandler.removeCallbacks(mAction);
                            mHandler = null;
                            break;
                    }
                    return false;
                }
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    increment(-10, display);
                    mHandler.postDelayed(this, 500);
                }
            };
        });

        //TextViews
        TextView opp1 = (TextView) findViewById(R.id.Opponent1);
        TextView opp2 = (TextView) findViewById(R.id.Opponent2);
        TextView opp3 = (TextView) findViewById(R.id.Opponent3);
        TextView oppLife1 = (TextView) findViewById(R.id.OpponentLife1);
        TextView oppLife2 = (TextView) findViewById(R.id.OpponentLife2);
        TextView oppLife3 = (TextView) findViewById(R.id.OpponentLife3);

        //TODO set opponents colors
        //opponent1.setBackgroundResource("#ffffff") Take variables over Network connection
    }

    //Life counter implementation
    public void increment(int amount, TextView display) {
        lifeTotal += amount;
        updateLifeTotalDisplay(display);
    }

    public void setLifeTotal(EditText display) {
        int amount = Integer.parseInt(display.getText().toString());
        lifeTotal = amount;
    }

    public int getLifeTotal() {
        return lifeTotal;
    }

    public void updateLifeTotalDisplay(TextView display) {
        display.setText(String.valueOf(this.getLifeTotal()));
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

/**
    public void setTime(int amount, TextView textDisplay) {
        time = amount;
        int minutes = time%60;
        int seconds = time - minutes*60;
        textDisplay.setText("" + minutes + ":" + seconds);
    }
 */
}

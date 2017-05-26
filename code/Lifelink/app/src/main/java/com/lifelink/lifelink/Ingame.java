package com.lifelink.lifelink;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
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

    private TextView opp1, opp2, opp3;
    private TextView oppLife1, oppLife2, oppLife3;

    private TextView timeDisplay;

    private boolean timeTicking;
    private int currentTime;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        Bundle bundle = getIntent().getExtras();

        int numberOfPlayers = 3;

        if (bundle != null) {
            numberOfPlayers = (int) bundle.get("NUMBER_OF_PLAYERS");
        }

        // TextViews
        opp1 = (TextView) findViewById(R.id.Opponent1);
        opp2 = (TextView) findViewById(R.id.Opponent2);
        opp3 = (TextView) findViewById(R.id.Opponent3);
        oppLife1 = (TextView) findViewById(R.id.OpponentLife1);
        oppLife2 = (TextView) findViewById(R.id.OpponentLife2);
        oppLife3 = (TextView) findViewById(R.id.OpponentLife3);

        // Set appropiate amount of opponents
        switch (numberOfPlayers) {
            case 1:
                setOpponenOneInvisible();
                setOpponenTwoInvisible();
                setOpponenThreeInvisible();
                break;
            case 2:
                setOpponenTwoInvisible();
                setOpponenThreeInvisible();
                break;
            case 3:
                setOpponenThreeInvisible();
                break;
            case 4:
                // do nothing
                break;
            default:
                // do nohing
                break;
        }

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
        //if player is host...
        lifeTotal = Integer.parseInt(playerProfile.getString("preferredLife","20"));
        display.setText(String.valueOf(this.getLifeTotal()));

        // Set opponents life
        oppLife1.setText(String.valueOf(lifeTotal));
        oppLife2.setText(String.valueOf(lifeTotal));
        oppLife3.setText(String.valueOf(lifeTotal));

        // Set starting time
        timeDisplay = (TextView) findViewById(R.id.time);
        int startingTime = Integer.parseInt(playerProfile.getString("preferredTime","120"));
        setTime(startingTime);
        currentTime = startingTime;

        timeTicking = true;
        newCountDownTimer();

        // Time buttons
        final Button timeButton = (Button) findViewById(R.id.pass);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeTicking) {
                    timeButton.setText("Resume turn");
                    timeTicking = false;
                    stopCountDownTimer();
                } else {
                    timeButton.setText("Pass turn");
                    timeTicking = true;
                    newCountDownTimer();
                }
            }
        });

        // Plus buttons
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

        //TODO set opponents colors
        //opponent1.setBackgroundResource("#ffffff") Take variables over Network connection
    }

    private void stopCountDownTimer() {
        if (countDownTimer == null) {
            return;
        }
        countDownTimer.cancel();
    }

    private void newCountDownTimer() {
        countDownTimer = new CountDownTimer(currentTime*1000 + 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tickTime();
            }

            @Override
            public void onFinish() {
                timeOut();
            }
        }.start();
    }

    private void timeOut() {
        Toast.makeText(this, "Time out!", Toast.LENGTH_SHORT).show();
    }

    private void tickTime() {
        currentTime--;
        setTime(currentTime);
    }

    private void setTime(int amount) {
        time = amount;
        int minutes = (int)Math.floor(time/60);
        int seconds = time - 60*minutes;
        timeDisplay.setText("" + minutes + ":" + seconds);
    }

    private void setOpponenOneInvisible() {
        opp1.setVisibility(View.INVISIBLE);
        oppLife1.setVisibility(View.INVISIBLE);
    }

    private void setOpponenTwoInvisible() {
        opp2.setVisibility(View.INVISIBLE);
        oppLife2.setVisibility(View.INVISIBLE);
    }

    private void setOpponenThreeInvisible() {
        opp3.setVisibility(View.INVISIBLE);
        oppLife3.setVisibility(View.INVISIBLE);
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

}

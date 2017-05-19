package com.lifelink.lifelink;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Ingame extends AppCompatActivity {
    private int lifeTotal = 0;
    private int intialTime = 0;
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);

        final EditText display = (EditText) findViewById(R.id.lifeTotalDisplay);
        display.setBackgroundColor(Color.parseColor(playerProfile.getString("color", "#ffffff")));
        display.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setLifeTotal(display);

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
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(1, display);
            }
        });

        Button minus1 = (Button) findViewById(R.id.minus1);
        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(-1, display);
            }
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
/**
    public void setTime(int amount, TextView textDisplay) {
        time = amount;
        int minutes = time%60;
        int seconds = time - minutes*60;
        textDisplay.setText("" + minutes + ":" + seconds);
    }
 */
}

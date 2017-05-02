package com.lifelink.lifelink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

public class Ingame extends AppCompatActivity {
    private int lifeTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);
        final TextView display = (TextView) findViewById(R.id.lifeTotalDisplay);
        display.setText(String.valueOf(this.getLifeTotal()));

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

/**
        //Display editor
        TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView display, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    setLifeTotal();
                    updateLifeTotalDisplay(display);
                    //example_confirm();//match this behavior to your 'Send' (or Confirm) button
                }
                return true;
            }
        }
 */
    }

    //Life counter implementation
    public void increment(int amount, TextView display) {
        lifeTotal += amount;
        updateLifeTotalDisplay(display);
    }

    public void setLifeTotal(int amount) {
        lifeTotal = amount;
    }

    public int getLifeTotal() {
        return lifeTotal;
    }

    public void updateLifeTotalDisplay(TextView display) {
        display.setText(String.valueOf(this.getLifeTotal()));
    }
}

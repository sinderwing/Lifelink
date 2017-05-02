package com.lifelink.lifelink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ingame extends AppCompatActivity {
    private int lifeTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);
        TextView textView = (TextView) findViewById(R.id.lifeTotalDisplay);
        textView.setText(lifeTotal);

        //Buttons
        Button plus1 = (Button) findViewById(R.id.plus1);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(1);
            }
        });

        Button minus1 = (Button) findViewById(R.id.plus1);
        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(-1);
            }
        });

    }

    //Life counter implementation
    public void increment(int amount) {
        lifeTotal += amount;
    }

    public void setLifeTotal(int amount) {
        lifeTotal = amount;
    }
}

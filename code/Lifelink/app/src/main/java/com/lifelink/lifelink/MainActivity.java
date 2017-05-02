package com.lifelink.lifelink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        Button gotoCreate = (Button) findViewById(R.id.gotoCreate);
        gotoCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LobbyCreation.class);
                startActivity(intent);
            }
        });

        Button gotoJoin = (Button) findViewById(R.id.gotoJoin);
        gotoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LobbyJoining.class);
                startActivity(intent);
            }
        });

        Button gotoChangeName = (Button) findViewById(R.id.gotoChangeName);
        gotoChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NameChange.class);
                startActivity(intent);
            }
        });

        Button gotoChangeColor = (Button) findViewById(R.id.gotoChangeColor);
        gotoChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ColorChange.class);
                startActivity(intent);
            }
        });
    }
}
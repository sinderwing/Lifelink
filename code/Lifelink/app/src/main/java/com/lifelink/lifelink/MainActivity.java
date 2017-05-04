package com.lifelink.lifelink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    EditText colorInput;

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


        Button gotoIngame = (Button) findViewById(R.id.gotoIngame);
        gotoIngame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Ingame.class);
                startActivity(intent);
            }
        });

        //Saved preferences
        nameInput = (EditText) findViewById(R.id.nameInput);
        //colorInput = (EditText) findViewById(R.id.colorInput);
    }

    /**
     * Update and save user profile information
     */
    public void updateProfile (View view){
        SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = playerProfile.edit();
        editor.putString("name", nameInput.getText().toString());
        editor.putString("color", colorInput.getText().toString());
        editor.apply();

        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT);
    }
}
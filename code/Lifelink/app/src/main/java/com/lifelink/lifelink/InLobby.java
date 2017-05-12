package com.lifelink.lifelink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Lobby screen that is before the game is started.
 */
public class InLobby extends AppCompatActivity {

    /**
     * Method called when the Activity is first created.
     * @param savedInstanceState the previous instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inlobby);


        // Button to start the game.
        Button startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InLobby.this, Ingame.class);
                startActivity(intent);
            }
        });
    }
}
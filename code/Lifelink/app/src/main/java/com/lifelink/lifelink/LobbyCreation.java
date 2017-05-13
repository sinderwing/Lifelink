package com.lifelink.lifelink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.TextView;
import android.widget.Button;

/**
 * Class for the view of LobbyCreation.
 */
public class LobbyCreation extends AppCompatActivity {

    /**
     * Called when the instance is first created.
     * @param savedInstanceState the previous instant state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_creation);

        //SeekBar and TextView to set and show the starting life count.
        SeekBar setLifeCount = (SeekBar) findViewById(R.id.setLifeCount);
        final TextView currentLifeCount = (TextView) findViewById(R.id.currentLifeCount);
        setLifeCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            /**
             * Update the value displayed in currentLifeCount when the seekbar is changed.
             *
             * @param seekBar the seekBar that is referenced
             * @param progress the change in progress
             * @param fromUser if the input comes from the user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentLifeCount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing to do
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nothing to do
            }
        });

         // SeekBar and TextView to set and show the starting time value.
        SeekBar setTimeValue = (SeekBar) findViewById(R.id.setTimeValue);
        final TextView currentTimeValue = (TextView) findViewById(R.id.currentTimeValue);
        setTimeValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            /**
             * Update the value displayed in currentLifeCount when the setTimeValue SeekBar is
             * changed.
             *
             * @param seekBar th                                                e seekBar that is referenced
             * @param progress the change in progress
             * @param fromUser if the input comes from the user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTimeValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing to do
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nothing to do
            }
        });

         // Toggle time settings on/off.
         // TODO: Make this work.
        ToggleButton timeOnOff = (ToggleButton) findViewById(R.id.setTimeOnOff);


        // Button to go to the Lobby.
        Button gotoInLobby = (Button) findViewById(R.id.gotoInLobby);
        gotoInLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LobbyCreation.this, InLobby.class);
                startActivity(intent);
            }
        });


    }

}
package com.lifelink.lifelink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
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
        SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);
        SeekBar setLifeCount = (SeekBar) findViewById(R.id.setLifeCount);
        final int offset = 20; //lower boundary
        int preferredLife = Integer.parseInt(playerProfile.getString("preferredLife", "20"));
        setLifeCount.setProgress(preferredLife-offset);
        setLifeCount.setMax(40 - offset);
        final TextView currentLifeCount = (TextView) findViewById(R.id.currentLifeCount);
        currentLifeCount.setText("" + (setLifeCount.getProgress() + offset));
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
                progress += offset;
                progress = progress / 10;
                progress = progress * 10; //rounds off to lower int, e.g. 18 --> 10
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
        final SeekBar setTimeValue = (SeekBar) findViewById(R.id.setTimeValue);
        final int offsetTime = 20; //minimum time limit
        int preferredTime = Integer.parseInt(playerProfile.getString("preferredTime", "180"));
        setTimeValue.setProgress(preferredTime-offsetTime);
        setTimeValue.setMax(6*60 - offsetTime); //Six minutes
        final TextView currentTimeValue = (TextView) findViewById(R.id.currentTimeValue);
        currentTimeValue.setText("" + (setTimeValue.getProgress() + offsetTime));
        setTimeValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            /**
             * Update the value displayed in currentLifeCount when the setTimeValue SeekBar is
             * changed.
             *
             * @param seekBar the seekBar that is referenced
             * @param progress the change in progress
             * @param fromUser if the input comes from the user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress += offsetTime;
                progress = progress / 10;
                progress = progress * 10; //rounds off to lower int, e.g. 18 --> 10
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
        ToggleButton timeOnOff = (ToggleButton) findViewById(R.id.setTimeOnOff);
        final TextView timeText = (TextView) findViewById(R.id.timeText);
        timeOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    // Show all time settings
                    currentTimeValue.setVisibility(View.VISIBLE);
                    setTimeValue.setVisibility(View.VISIBLE);
                    timeText.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    // Hide all time settings
                    currentTimeValue.setVisibility(View.INVISIBLE);
                    setTimeValue.setVisibility(View.INVISIBLE);
                    timeText.setVisibility(View.INVISIBLE);
                }
            }
        });


        // Button to go to the Lobby.
        Button gotoInLobby = (Button) findViewById(R.id.gotoInLobby);
        gotoInLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LobbyCreation.this, InLobby.class);

                //Save lobby preferences
                SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = playerProfile.edit();
                editor.putString("preferredLife", currentLifeCount.getText().toString());
                editor.putString("preferredTime", currentTimeValue.getText().toString());

                editor.apply();

                startActivity(intent);
            }
        });
    }
}
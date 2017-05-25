package com.lifelink.lifelink;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;


public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean soundPref = true;
        final SharedPreferences playerProfile = getSharedPreferences("playerProfile", Context.MODE_PRIVATE);
        soundPref = Boolean.parseBoolean(playerProfile.getString("sound", "true"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Switch sound = (Switch) findViewById(R.id.sound);
        sound.setChecked(soundPref);
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = playerProfile.edit();

                if (isChecked) {
                    editor.putString("sound", "true");
                } else {
                    editor.putString("sound", "false");
                }

                editor.apply();
            }
        });
    }
}

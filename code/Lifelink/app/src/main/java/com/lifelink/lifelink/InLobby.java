package com.lifelink.lifelink;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * Lobby Activity that is before the game is started.
 * Players can join the lobby via Wifi P2P and the host can then when the lobby is adequately full
 * start the game.
 * (Also possibly the functionality for the host to interact with the lobby settings.)
 */
public class InLobby extends FragmentActivity implements
        WifiP2pManager.ChannelListener {

    private int numberOfPlayers;

    public static final String TAG = "InLobbyActivity";

    private boolean isWifiP2pEnabled = false;
    private boolean retryAgain = true;

    private String passCode;

    // IntentFilter for this activity.
    private final IntentFilter intentFilter = new IntentFilter();

    // WifiP2P fields
    private WifiP2pManager wifiP2pManager;
    private Channel channel;
    private InLobbyBroadcastReceiver receiver;

    /**
     * Method called when the Activity is first created.
     * @param savedInstanceState the previous instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "This should print");
        setContentView(R.layout.activity_inlobby);

        numberOfPlayers = 1;

        // Adds broadcast to the IntentFilter when the Wi-Fi P2P status is changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Adds broadcast to the IntentFilter when the list of available peers is registered.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Adds broadcast to the IntentFilter for when the state of Wi-Fi P2P connectivity is changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Adds broadcast to the IntentFilter for when this device's details has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        wifiP2pManager = (WifiP2pManager)getSystemService(WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(this, getMainLooper(), null);

        wifiP2pManager.createGroup(channel, new WifiP2pManager.ActionListener() {

            /**
             * Successfully created a group.
             */
            @Override
            public void onSuccess() {
                Log.d(TAG, "Successfully created P2P group.");
            }

            /**
             * Failed to create a group.
             * @param reason for the failure
             */
            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "Failed to create P2P group: " + getReason(reason));
            }
        });

        final TextView numberOfPlayersValueView = (TextView) findViewById(R.id.numberOfPlayersValue);



                // Button to start the game.
        Button startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            /**
             * Attempt to start the game.
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InLobby.this, Ingame.class);
                numberOfPlayers = Integer.parseInt(numberOfPlayersValueView.getText().toString());
                intent.putExtra("NUMBER_OF_PLAYERS", numberOfPlayers);
                startActivity(intent);
            }
        });
    }

    private String getReason(int reason) {
        switch (reason) {
            case WifiP2pManager.P2P_UNSUPPORTED: return "P2P Unsupported.";

            case WifiP2pManager.ERROR: return "Error.";

            case WifiP2pManager.BUSY: return "Busy.";

            default: return "Unknown error";
        }
    }

    /**
     * When the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        receiver = new InLobbyBroadcastReceiver(wifiP2pManager, channel, this);
        registerReceiver(receiver, intentFilter);
    }

    /**
     * When the activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /**
     * When the channel is disconnected attempt to reconnect.
     */
    @Override
    public void onChannelDisconnected() {
        if (wifiP2pManager != null && retryAgain) {
            Toast.makeText(this, "Channel lost. Attempting again", Toast.LENGTH_SHORT).show();
            resetData();
            retryAgain = false;
            channel = wifiP2pManager.initialize(this, getMainLooper(), this);
        } else {
            Toast.makeText(this,
                    "Channel is probably lost permanently. Try to Disable/Re-enable P2P.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Reset the peer connections.
     */
    public void resetData() {
        ConnectedPeersFragment connectedPeersFragment = (ConnectedPeersFragment)
                getFragmentManager().findFragmentById(R.id.in_lobby_player_list);
        if (connectedPeersFragment != null) {
            connectedPeersFragment.clearPeers();
        }
    }

    /**
     * Set the isWifiP2pEnabled field to the inputted value.
     * @param isWifiP2pEnabled to set the field to
     */
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }
}
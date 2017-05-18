package com.lifelink.lifelink;

import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LobbyJoining extends FragmentActivity implements
    WifiP2pManager.ChannelListener {

    public static final String TAG = "LobbyJoining";

    private boolean isWifiP2pEnabled = false;
    private boolean retryAgain = true;

    // IntentFilter for this activity.
    private final IntentFilter intentFilter = new IntentFilter();

    // WifiP2P fields
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private LobbyJoiningBroadcastReceiver receiver;

    /**
     * Method called when the Activity is first created.
     * @param savedInstanceState the previous instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "This should print");
        setContentView(R.layout.activity_lobby_joining);

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
        receiver = new LobbyJoiningBroadcastReceiver(wifiP2pManager, channel, this);
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
    public void onChannelDisconnected() {
        if (wifiP2pManager != null && retryAgain) {
            Toast.makeText(this, "Channel lost. Attempting again", Toast.LENGTH_SHORT).show();
            resetData();
            retryAgain = false;
            channel = wifiP2pManager.initialize(this, getMainLooper(), (WifiP2pManager.ChannelListener) this);
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
                getFragmentManager().findFragmentById(R.id.joining_lobby_hosting_list);
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
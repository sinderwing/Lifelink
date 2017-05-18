package com.lifelink.lifelink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * BroadcastReceiver class for the in lobby screen.
 *
 * Created by AngusLothian on 5/13/2017.
 */
public class InLobbyBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private InLobby activity;

    /**
     * Create a InLobbyBroadCastReceiver object.
     * @param wifiP2pManagermanager the WifiP2pManager that is used
     * @param channel the WifiP2pManager.Channel that is used
     * @param activity the activity that calls the BroadCastReceiver
     */
    public InLobbyBroadcastReceiver(WifiP2pManager wifiP2pManagermanager,
                                    WifiP2pManager.Channel channel, InLobby activity) {
        super();
        this.wifiP2pManager = wifiP2pManagermanager;
        this.channel = channel;
        this.activity = activity;
    }

    /**
     * When the InLobbyBroadCastReceiver receives an intent.
     * @param context the context of the intent
     * @param intent the intent to be processed
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if WifiP2p mode is enabled. Alerts the activity.

            // UI update to indicate wifi p2p status
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                activity.setIsWifiP2pEnabled(true);
            } else {
                activity.setIsWifiP2pEnabled(false);
                activity.resetData();
            }
            Log.d(InLobby.TAG, "P2P state changed: " + state);

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            if (wifiP2pManager != null) {
                wifiP2pManager.requestPeers(channel, (WifiP2pManager.PeerListListener)
                        activity.getFragmentManager().findFragmentById(R.id.in_lobby_player_list));
            }
            Log.d(InLobby.TAG, "P2P peers changed.");

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            if (wifiP2pManager == null) {
                return;
            }

            NetworkInfo netWorkInfo = (NetworkInfo)
                    intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (!netWorkInfo.isConnected()) {
                // Disconnected
                activity.resetData();
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            ConnectedPeersFragment fragment = (ConnectedPeersFragment) activity.getFragmentManager()
                    .findFragmentById(R.id.in_lobby_player_list);
        }

        // Initiate peer discovery
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            /**
             * The ActionListener succeeds to discovers peers.
             */
            @Override
            public void onSuccess() {
                // Not sure if anything is suppose to go here.
            }

            /**
             * The ActionListener fails to discover peers.
             * @param reason the reason for failure.
             */
            @Override
            public void onFailure(int reason) {
                // TODO: Alert the user that something went wrong.
                Log.d(InLobby.TAG, "failed to discover peers");
            }
        });

    }
}

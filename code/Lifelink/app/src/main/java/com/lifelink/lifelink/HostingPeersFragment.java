package com.lifelink.lifelink;

import android.app.ListFragment;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AngusLothian on 5/18/2017.
 */
public class HostingPeersFragment extends ListFragment implements WifiP2pManager.PeerListListener {

    private List<WifiP2pDevice> currentPeers = new ArrayList<WifiP2pDevice>();
    View contentView;
    private WifiP2pDevice device;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setListAdapter(new WiFiPeerListAdapter(getActivity(), R.layout.rows_connected_peers,
                currentPeers));
    }

    /**
     * Creates a View.
     * @param inflater that instantiates the View from the xml file
     * @param container the ViewGroup which the View is contained in
     * @param savedInstanceState
     * @return the View that is created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.list_fragment, container);
        return contentView;
    }

    /**
     * Update currentPeers when new peers become available.
     * @param peerList of new peers that have been found
     */
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList) {
        currentPeers.clear();
        currentPeers.addAll(peerList.getDeviceList());
        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
        if (currentPeers.size() == 0) {
            Log.d(InLobby.TAG, "No devices found");
            return;
        }
    }

    /**
     * Clear all the current peers.
     */
    public void clearPeers() {
        currentPeers.clear();
        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     * Array adapter for ListFragment that maintains WifiP2pDevice list.
     */
    private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {
        private List<WifiP2pDevice> items;

        /**
         * @param context
         * @param textViewResourceId
         * @param objects
         */
        public WiFiPeerListAdapter(Context context, int textViewResourceId,
                                   List<WifiP2pDevice> objects) {
            super(context, textViewResourceId, objects);
            items = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.rows_connected_peers, null);
            }
            WifiP2pDevice device = items.get(position);
            if (device != null) {
                TextView deviceName = (TextView) v.findViewById(R.id.device_name);
                TextView playerName = (TextView) v.findViewById(R.id.player_name);
                if (deviceName != null) {
                    deviceName.setText(device.deviceName);
                }
                if (playerName != null) {
                    // TODO: fix this: playerName.setText(sharedPreferences.getString("name", "empty"));
                    playerName.setText("Rip bulle");
                }
            }
            return v;
        }
    }

    private static String getDeviceStatus(int deviceStatus) {
        Log.d(InLobby.TAG, "Peer status :" + deviceStatus);
        switch (deviceStatus) {
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.INVITED:
                return "Invited";
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";
        }
    }


}

// -*- mode: java; c-basic-offset: 2; -*-
// Create by Victor Shang, All Rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.example.networklistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStatusReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkListener";
    private NetworkChangeListener networkChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        int result = NetworkStatusConverter.NO_NETWORK;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                result = NetworkStatusConverter.WIFI | NetworkStatusConverter.MOBILE;
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                result = NetworkStatusConverter.WIFI;
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                result = NetworkStatusConverter.MOBILE;
            } else {
                result = NetworkStatusConverter.NO_NETWORK;
            }
        } else {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network[] networks = connMgr.getAllNetworks();
            for (int i = 0; i < networks.length; i++) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if (networkInfo.isConnected()) {
                    result |= NetworkStatusConverter.convert(networkInfo.getType());
                }
            }
            networkChangeListener.networkChange(result);
        }
    }

    public void setNetworkChangeListener(NetworkChangeListener networkChangeListener) {
        this.networkChangeListener = networkChangeListener;
    }

}
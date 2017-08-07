// -*- mode: java; c-basic-offset: 2; -*-
// Create by Victor Shang, All Rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.example.networklistener;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(version = 1,
        description = "A component for listen and get the network status.\n version 1.00 \n Create by Victor Shang \n Email:vshang2006@163.com",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "images/extension.png")

@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.ACCESS_NETWORK_STATE")

public class NetworkListener extends AndroidNonvisibleComponent
        implements Component, OnResumeListener, OnPauseListener, NetworkChangeListener {
    private static final String TAG = "NetworkListener";

    private ComponentContainer container;
    private Context context;
    private NetworkStatusReceiver networkStatusReceiver;

    public NetworkListener(ComponentContainer container) {
        super(container.$form());
        this.container = container;
        this.context = container.$context();

        // Set up listeners
        form.registerForOnResume(this);
        form.registerForOnPause(this);
    }

    /**
     * Returns the current network status in a integer.
     *
     * @return current  network status in a integer.-1:Error;0:No Network ;1:WIFI;2:Mobile;>2:Other Connection
     */
    @SimpleFunction(description = "Detect network status.return -1:Error;0:No Network ;1:WIFI;2:Mobile;>2:Other Connection")
    public int NetworkStatus() {
        NetworkInfo mNetworkInfo;
        ConnectivityManager mConnectivityManager;
        if (context != null) {
            mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                if (mNetworkInfo.isConnected()) {
                    return NetworkStatusConverter.convert(mNetworkInfo.getType());
                } else {
                    return NetworkStatusConverter.NO_NETWORK;
                }
            } else {
                return NetworkStatusConverter.NO_NETWORK;
            }
        } else {
            return NetworkStatusConverter.ERROR;
        }
    }

    /**
     * Network Changed Event
     *
     * @return current  network status in a integer.-1:Error;0:No Network ;1:WIFI;2:Mobile;>2:Other Connection
     */

    @SimpleEvent(description = "Network status has changed .return status : 0:No Network ;1:WIFI;2:Mobile;>2:Other Connection")
    public void NetworkChanged(int status) {
        EventDispatcher.dispatchEvent(this, "NetworkChanged", status);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        if (networkStatusReceiver == null) {
            networkStatusReceiver = new NetworkStatusReceiver();
            networkStatusReceiver.setNetworkChangeListener(this);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkStatusReceiver, filter);
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause()");
        context.unregisterReceiver(networkStatusReceiver);
    }

    @Override
    public void networkChange(int status) {
        Log.i(TAG, "networkChange(" + status + ") ");
        NetworkChanged(status);
    }

}
// -*- mode: java; c-basic-offset: 2; -*-
// Create by Victor Shang, All Rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.example.networklistener;

public class NetworkStatusConverter{
    public static final int ERROR = -1;
    public static final int NO_NETWORK = 0x00;
    public static final int WIFI = 0x01;
    public static final int MOBILE = 0x02;
    public static final int ETHERNET = 0x04;
    public static final int VPN = 0x08;
    public static final int BLUETOOTH = 0x10;
    public static final int WIMAX = 0x20;
    public static final int OTHER = 0x40;

    public static int convert(int networkInfoType){
        int result=NO_NETWORK;
        /*
        networkInfoType has the following values:
          MOBILE:0;
          WIFI:1;
          WiMAX:6;
          BLUETOOTH:7;
          ETHERNET:9;
          VPN:17;
          ....
         */
        switch (networkInfoType) {
            case 0: //MOBILE
                result |= MOBILE;
                break;
            case 1://WIFI
                result |= WIFI;
                break;
            case 6://WiMAX
                result |= WIMAX;
                break;
            case 7://BLUETOOTH
                result |= BLUETOOTH;
                break;
            case 9://ETHERNET
                result |= ETHERNET;
                break;
            case 17://VPN
                result |= VPN;
                break;
            default:
                result |= OTHER;
        }
        return result;
    }
}
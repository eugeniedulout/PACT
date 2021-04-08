package com.beacons.demo;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.Date;

public class BeaconModel
{
    private static final int PROTOCOL_OFFSET = 3;
    private static final int AD_LENGTH_INDEX = 0 + PROTOCOL_OFFSET;
    private static final int AD_TYPE_INDEX = 1 + PROTOCOL_OFFSET;
    private static final int COMPANY_ID_START_INDEX = 2 + PROTOCOL_OFFSET;
    private static final int BEACON_CODE_INDEX = 4 + PROTOCOL_OFFSET;
    private static final int UUID_START_INDEX = 6 + PROTOCOL_OFFSET;
    private static final int UUID_STOP_INDEX = UUID_START_INDEX + 15;
    private static final int ARGS_START_INDEX = UUID_STOP_INDEX + 1;
    private static final int TXPOWER_INDEX = ARGS_START_INDEX + 4;

    private static final int AD_ALT_BEACON_LENGTH_VALUE = 0x1b;
    private static final int AD_IBEACON_LENGTH_VALUE = 0x1a;
    private static final int AD_TYPE_VALUE = 0xff;
    private static final int ALT_BEACON_CODE_VALUE = 0xbeac;
    private static final int IBEACON_CODE_VALUE = 0x0215;

    //UUID of beacon
    public String uuid;
    //string representing arguments inside the beacon
    public String arguments;
    //reference power
    public int txPower;
    //current RSSI
    public int rssi;
    //timestamp when this beacon was last time scanned
    public long timestamp;
    //ID of the beacon
    public String id;
    //The distance
    public double distance;

    //The coords
    public double x;
    public double y;

    public static boolean isAltBeacon(final byte[] data) {
        Log.d("TESTING DEVICE", "****************************************************\n"+data.toString());
        if((data[AD_LENGTH_INDEX] & 0xff) != AD_ALT_BEACON_LENGTH_VALUE) return false;
        if((data[AD_TYPE_INDEX] & 0xff) != AD_TYPE_VALUE) return false;

        final int code = ((data[BEACON_CODE_INDEX] << 8) & 0x0000ff00) |
                         ((data[BEACON_CODE_INDEX+1]) & 0x000000ff);
        if(code != ALT_BEACON_CODE_VALUE) return false;

        return true;
    }

    public static boolean isIBeacon(final byte[] data) {
        if((data[AD_LENGTH_INDEX] & 0xff) != AD_IBEACON_LENGTH_VALUE) return false;
        if((data[AD_TYPE_INDEX] & 0xff) != AD_TYPE_VALUE) return false;

        final int code = ((data[BEACON_CODE_INDEX] << 8) & 0x0000ff00) |
                         ((data[BEACON_CODE_INDEX+1]) & 0x000000ff);

        if(code != IBEACON_CODE_VALUE) return false;

        return true;
    }

    public void updateFrom(final BluetoothDevice device, final int rssi, final byte[] advertisement) {
        this.rssi = rssi;
        this.id = device.getAddress();
        this.timestamp = new Date().getTime();
        this.txPower = (int) advertisement[TXPOWER_INDEX];
        updateDistance();
        this.arguments = String.format("arg1: %02x %02x \targ2: %02x %02x\t company: %04x",
                advertisement[ARGS_START_INDEX],
                advertisement[ARGS_START_INDEX+1],
                advertisement[ARGS_START_INDEX+2],
                advertisement[ARGS_START_INDEX+3],
                advertisement[COMPANY_ID_START_INDEX]<<16|advertisement[COMPANY_ID_START_INDEX+1]);

        StringBuilder sb = new StringBuilder();
        for(int i = UUID_START_INDEX, offset=0; i <= UUID_STOP_INDEX; ++i, ++offset) {
            sb.insert(0,String.format("%02x", (int)(advertisement[i] & 0xff)));
            if (offset == 5 || offset == 7 || offset == 9 || offset == 11) {
                sb.insert(0,"-");
            }
        }
        this.uuid = sb.toString();
    }

    public void updateDistance() {
        double d = Math.exp(Math.log(10)*(this.txPower-this.rssi)/70);
        Log.d("distance", ""+d);
        this.distance = d;
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

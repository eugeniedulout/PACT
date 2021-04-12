package com.beacons.demo;

import android.bluetooth.BluetoothDevice;

import java.util.Date;

public class BeaconModel
{
    // UUID of beacon
    public String uuid;
    // string representing arguments inside AltBeacon
    public String arguments;
    // reference power
    public int txPower;
    // current RSSI
    public int rssi;
    // timestamp when this beacon was last time scanned
    public long timestamp;
    // ID of the beacon, in case of Android it will be Bluetooth MAC address
    public String id;

    private static final int PROTOCOL_OFFSET = 3;
    private static final int AD_LENGTH_INDEX = 0 + PROTOCOL_OFFSET;
    private static final int AD_TYPE_INDEX = 1 + PROTOCOL_OFFSET;
    private static final int BEACON_CODE_INDEX = 4 + PROTOCOL_OFFSET;
    private static final int UUID_START_INDEX = 6 + PROTOCOL_OFFSET;
    private static final int UUID_STOP_INDEX  = UUID_START_INDEX + 15;
    private static final int ARGS_START_INDEX = UUID_STOP_INDEX + 1;
    private static final int TXPOWER_INDEX = ARGS_START_INDEX + 4;

    private static final int AD_LENGTH_VALUE = 0x1b;
    private static final int AD_TYPE_VALUE = 0xff;
    private static final int BEACON_CODE_VALUE = 0xbeac;

    public static boolean isAltBeacon(final byte[] data) {
        if ((data[AD_LENGTH_INDEX] & 0xff) != AD_LENGTH_VALUE) return false;

        if ((data[AD_TYPE_INDEX] & 0xff) != AD_TYPE_VALUE) return false;

        final int code = ((data[BEACON_CODE_INDEX] << 8) & 0x0000ff00) |
                ((data[BEACON_CODE_INDEX + 1]) & 0x000000ff);
        if(code != BEACON_CODE_VALUE) return false;

        return true;
    }

    public void updateFrom(final BluetoothDevice device,
                           final int rssi,
                           final byte[] advertisement) {
        this.rssi = rssi;
        this.id = device.getAddress();
        this.timestamp = new Date().getTime();
        this.txPower = (int) advertisement[TXPOWER_INDEX];
        this.arguments = String.format("arg1: %02x %02x  arg2: %02x %02x",
                advertisement[ARGS_START_INDEX],
                advertisement[ARGS_START_INDEX + 1],
                advertisement[ARGS_START_INDEX + 2],
                advertisement[ARGS_START_INDEX + 3]);

        StringBuilder sb = new StringBuilder();
        for(int i = UUID_START_INDEX, offset = 0; i <= UUID_STOP_INDEX; ++i, ++offset) {
            sb.append(String.format("%02x", (int)(advertisement[i] & 0xff)));
            if (offset == 3 || offset == 5 || offset == 7 || offset == 9) {
                sb.append("-");
            }
        }
        this.uuid = sb.toString();
    }

}

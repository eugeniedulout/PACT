package com.beacons.demo;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity
{
    private final static int BT_REQUEST_ID = 1;
    private final static int REQUEST_LOCATION = 0;

    private static String[] PERMISSION_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean permissions_granted = false;
    private Toast toast;

    private final BeaconsAdapter mAdapter = new BeaconsAdapter();
    private BluetoothAdapter mBtAdapter = null;
    //Before Lollipop
    private BluetoothAdapter.LeScanCallback mLeOldCallback = null;
    //After Lollipop
    private ScanCallback mLeNewCallback = null;
    TextView x_coord, y_coord;

    Trilateration t = new Trilateration();
    // smoothing constant for low-pass filter 0 - 1 ; a smaller
    public static float ALPHA = 0.03f;
    public ArrayMap <int,byte[]> previous = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView tmpListView = (ListView) findViewById(R.id.list_view);
        tmpListView.setAdapter(mAdapter);

        x_coord = (TextView) findViewById(R.id.x_coord);
        y_coord = (TextView) findViewById(R.id.y_coord);

        t.init();
        initializeCallback();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(permissions_granted) {
            startScanning();
        }
    }

    @Override
    protected void onPause()
    {
        if(permissions_granted) {
            stopScanning();
        }
        super.onPause();
    }

    private void startScanning() {
        if(!permissions_granted) {
            return;
        }
        if(!isBluetoothAvailableAndEnabled()) {
            requestForBluetooth();
            return;
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mBtAdapter.startLeScan(mLeOldCallback);
        } else {
            BluetoothLeScanner scanner = mBtAdapter.getBluetoothLeScanner();
            if(scanner != null) {
                ScanSettings settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .setReportDelay(0)
                        .build();
                scanner.startScan(null, settings, mLeNewCallback);
            }
        }
    }

    private void stopScanning() {
        if(permissions_granted) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mBtAdapter.stopLeScan(mLeOldCallback);
            } else {
                BluetoothLeScanner scanner = mBtAdapter.getBluetoothLeScanner();
                if(scanner != null) {
                    scanner.stopScan(mLeNewCallback);
                }
            }
        }
    }

    private void initializeCallback() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Before android 4.4
            mLeOldCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    previous.put(device,scanRecord);
                    handleNewBeaconDiscovered(device, rssi, scanRecord);
                }
            };

        } else {
            //android 5.0 and later
            mLeNewCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        previous.put(result.getDevice(),result.getScanRecord().getBytes());
                        handleNewBeaconDiscovered(
                                result.getDevice(),
                                result.getRssi(),
                                result.getScanRecord().getBytes());
                    }
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    for(final ScanResult result : results) {
                        onScanResult(0, result);
                    }
                }
            };
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions_granted = false;
                requestLocationPermission();
            } else {
                permissions_granted = true;
            }
        }
    }

    private void handleNewBeaconDiscovered(final BluetoothDevice device, final int rssi, final byte[] advertisement) {
        //Here in a thread not blocking UI
        
        if (previous.containsKey(device){
            advertisement = filter(advertisement,previous.get(device),ALPHA);
        }
        
        if(BeaconModel.isAltBeacon(advertisement)) {
            Log.d("BEACON", "------------------------  ALT BEACON  -----------------------");
        } else if(BeaconModel.isIBeacon(advertisement)) {
            Log.d("BEACON", "------------------------  IBEACON  -----------------------");
        } else {return;}

        final BeaconModel beaconToAdd;
        BeaconModel beacon = mAdapter.findBeaconWithId(device.getAddress());
        if(beacon == null) {
            beacon = new BeaconModel();
            beacon.updateFrom(device, rssi, advertisement);
            beaconToAdd = beacon;
        } else {
            beaconToAdd = null;
            beacon.rssi = rssi;
            beacon.updateDistance();
            beacon.timestamp = new Date().getTime();
        }

        if(beacon!=null) {
            updateCoords(beacon);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Here for modifying UI
                if(beaconToAdd != null) {
                    mAdapter.addNewBeacon(beaconToAdd);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //*FONCTION A REMPLACER PAR LES RESULTATS DU SERVEUR *//
    private double[] getCoords(String uuid) {
        double [] coords = new double[2];
        switch (uuid) {
            case "1cad5144-5bda-11eb-ae93-0242ac130002":
                coords[0] = 0.6;
                coords[1] = 4;
                break;
            case "2d68cb07-d277-465e-8a44-bf509eccf6de":
                coords[0] = 0;
                coords[1] = 2.2;
                break;
            case "8ec76ea3-6668-48da-9866-75be8bc86fbb":
                coords[0] = 0;
                coords[1] = 0;
                break;
            default:
                coords[0] = -50;
                coords[1] = -100;
                break;
        }
        Log.d("[getCoords]",""+coords[0]+" "+coords[1]);
        return coords;
    }



    private void updateCoords(BeaconModel beacon) {
        //beacon.setCoords(Controller.getBeaconsCoords(beacon.uuid));
        double [] coords = getCoords(beacon.uuid);
        beacon.setCoords(coords[0], coords[1]);

        Log.d("[DEBUG]", "Entering trilateration");
        t.updateBeacon(beacon);
        Point G = t.getPosition();
        x_coord.setText("x: "+G.getX());
        y_coord.setText("y: "+G.getY());
    }



    public static float[] filter(float[] input, float[] prev, float alpha) {
		if (input == null || prev == null)
			throw new NullPointerException("input and prev float arrays must be non-NULL");
		if (input.length != prev.length)
			throw new IllegalArgumentException("input and prev must be the same length");

		for (int i = 0; i < input.length; i++) {
			prev[i] = prev[i] + alpha * (input[i] - prev[i]);
		}
		return prev;
	}

    private boolean isBluetoothAvailableAndEnabled() {
        BluetoothManager btManager = null;
        btManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        mBtAdapter = btManager.getAdapter();
        return mBtAdapter != null && mBtAdapter.isEnabled();
    }

    private void requestForBluetooth() {
        Intent request = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(request, BT_REQUEST_ID);
    }

    private void requestLocationPermission() {
        if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Required");
            builder.setMessage("Please grant Location access so this application can perform Bluetooth scanning");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                }
            });
            builder.show();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissions_granted = true;
            } else {}
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BT_REQUEST_ID) {
            if(isBluetoothAvailableAndEnabled()) {
                startScanning();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}






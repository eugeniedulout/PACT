package com.beacons.demo;

import android.Manifest;
import android.app.AlertDialog;
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

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    final private BeaconsAdapter mAdapter = new BeaconsAdapter();
    private BluetoothAdapter mBtAdapter = null;
    final private static int BT_REQUEST_ID = 1;
    // callback for Android before Lollipop
    private BluetoothAdapter.LeScanCallback mLeOldCallback = null;
    // callback used on Lollipop and later
    private ScanCallback mLeNewCallback = null;

    // location permissions
    private static final int REQUEST_LOCATION = 0;
    private static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean permissions_granted=false;
    private Toast toast;

    final private Handler mHandler = new Handler();
    final private static long VALIDATION_PERIOD = 7000; // 7 seconds

    private void startValidating() {
        mHandler.postDelayed(periodicValidationTask, VALIDATION_PERIOD);
    }

    private void stopValidating() {
        mHandler.removeCallbacks(periodicValidationTask);
    }

    private Runnable periodicValidationTask = new Runnable() {
        @Override
        public void run() {
            if (mAdapter.validateAllBeacons()) {
                mAdapter.notifyDataSetChanged();
            }
            // add it again to queue:
            startValidating(); // this function will be declared and defined in next step
        }
    };

    private void initializeCallback() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // android 4.4.4 and earlier
            mLeOldCallback = new BluetoothAdapter.LeScanCallback()
            {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    handleNewBeaconDiscovered(device, rssi, scanRecord);
                }
            };

            permissions_granted = true;

        } else {
// android 5.0 and later
            mLeNewCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        handleNewBeaconDiscovered(
                                result.getDevice(),
                                result.getRssi(),
                                result.getScanRecord().getBytes());
                    }
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {

                }
            };
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions_granted = false;
                requestLocationPermission();
            } else {
                permissions_granted = true;
            }
        }
    }

    private void requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Required");
            builder.setMessage("Please grant Location access so this application can perform Bluetooth scanning");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
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
        if (requestCode == REQUEST_LOCATION) {
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission has been granted
                permissions_granted = true;
            }else{
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void simpleToast(String message, int duration) {
        toast = Toast.makeText(this, message, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void requestForBluetooth() {
        Intent request = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(request, BT_REQUEST_ID);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView tmpListView = (ListView) findViewById(R.id.list_view);
        tmpListView.setAdapter(mAdapter);
        initializeCallback();
    }

    @Override
//this should replace our previous dummy onResume from Exercise 1
    protected void onResume()
    {
        super.onResume();
        if (permissions_granted) {
            startScanning();
            startValidating();
        }
    }

    @Override
    protected void onPause()
    {
        if (permissions_granted) {
            stopScanning();
            stopValidating();
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BT_REQUEST_ID) {
            if (isBluetoothAvailableAndEnabled()) {
                startScanning(); // we will declare that function shortly
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean isBluetoothAvailableAndEnabled() {
        BluetoothManager btManager = null;
        btManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        mBtAdapter = btManager.getAdapter();
        return mBtAdapter != null && mBtAdapter.isEnabled();
    }

    private void startScanning() {
        if (!permissions_granted) {
            return;
        }
        if (!isBluetoothAvailableAndEnabled()) {
            requestForBluetooth();
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mBtAdapter.startLeScan(mLeOldCallback);
        } else {
// code for Lollipop and later
            BluetoothLeScanner scanner = mBtAdapter.getBluetoothLeScanner();
            if (scanner != null) {
                ScanSettings settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .setReportDelay(0)
                        .build();

                scanner.startScan(null, settings, mLeNewCallback);
            }
        }
    }

    private void stopScanning() {
        if (permissions_granted) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mBtAdapter.stopLeScan(mLeOldCallback);
            } else {
                BluetoothLeScanner scanner = mBtAdapter.getBluetoothLeScanner();
                if (scanner != null) {
                    scanner.stopScan(mLeNewCallback);
                }
            }
        }
    }

    private void handleNewBeaconDiscovered(final BluetoothDevice device,
                                           final int rssi,
                                           final byte[] advertisement)
    {
        /*do as much as possible here (it is running in background thread, so is not blocking UI*/

        if (!BeaconModel.isAltBeacon(advertisement)) return;

        // rest of implementation as in previous step
        final BeaconModel beaconToAdd;
        BeaconModel beacon = mAdapter.findBeaconWithId(device.getAddress());
        if (beacon == null) {
            // new item
            beacon = new BeaconModel();
            beacon.updateFrom(device, rssi, advertisement);
            beaconToAdd = beacon;
        }
        else {
            // we have this in the list.. just update and notify adapter about changes
            beaconToAdd = null;
            beacon.rssi = rssi;
            beacon.timestamp = new Date().getTime();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (beaconToAdd != null) {
                    mAdapter.addNewBeacon(beaconToAdd);
                }
                else {
                    // just notify about changes in underlying data
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }


}






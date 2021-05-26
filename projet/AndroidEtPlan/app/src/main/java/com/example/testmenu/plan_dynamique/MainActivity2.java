package com.example.testmenu.plan_dynamique;

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
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.R;
import com.example.testmenu.adapters.RecycleViewConsigneRecetteAdapter;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.bluetooth.BeaconModel;
import com.example.testmenu.bluetooth.BeaconsAdapter;
import com.example.testmenu.bluetooth.Trilateration;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.example.testmenu.ListProduct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {


    public static ArrayList<Point> pointsProduits = new ArrayList<>();
    public static ListProduct listeProduit;
    public static Point current_position = new Point(3,1);
    private static com.example.testmenu.bluetooth.Point p = new com.example.testmenu.bluetooth.Point(100,100);

    private TextView textView;


    // Permissions du bluetooth
    private final static int BT_REQUEST_ID = 1;
    private final static int REQUEST_LOCATION = 0;

    private boolean permissions_granted = false;

    private static final BeaconsAdapter mAdapter = new BeaconsAdapter();
    private BluetoothAdapter mBtAdapter = null;

    //Before Lollipop
    private BluetoothAdapter.LeScanCallback mLeOldCallback = null;
    //After Lollipop
    private ScanCallback mLeNewCallback = null;

    static Trilateration t = new Trilateration();

    // smoothing constant for low-pass filter 0 - 1 ; a smaller
    public static float ALPHA = 0.03f;

    // Liste des packets bluetooth reçus
    public static ArrayMap<BluetoothDevice, byte[]> previous = new ArrayMap<BluetoothDevice, byte[]>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        getSupportActionBar().hide();

        t.init();
        initializeCallback();

        pointsProduits = (ArrayList<Point>) getIntent().getSerializableExtra("produits coordonnees");
        listeProduit = (ListProduct) getIntent().getSerializableExtra("produitsOject");

        ArrayList<String> productsName = new ArrayList<>();

        for(int i=0; i< listeProduit.getListOfProducts().size(); i++)
            productsName.add(listeProduit.getListOfProducts().get(i).getName());

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycleProduit);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewConsigneRecetteAdapter adapterConsigne = new RecycleViewConsigneRecetteAdapter(productsName, getApplicationContext());
        recyclerView.setAdapter(adapterConsigne);


        FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.containerGL,new MainFragment2());
        fr.commit();
        Log.e("message", "hehuuuuu");


        /*voirListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragment(new DisplayedProductsFromAListFragment(listeProduit.getListOfProducts(), listeProduit.getListName()), R.id.containerGL, getApplicationContext());

            }
        });*/
        Button btnScanCode = (Button)findViewById(R.id.scanBttn);
        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanButton(v);
            }
        });

        textView = findViewById(R.id.codeBarText);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);


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
                    handleNewBeaconDiscovered(device, rssi, scanRecord);
                }
            };

        } else {
            //android 5.0 and later
            mLeNewCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
            Log.d("CHECKING PERMS", "+++++++++++++++++++++++++++++++++");

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions_granted = false;
                requestLocationPermission();
            } else {
                permissions_granted = true;
            }
        }
    }


    private void handleNewBeaconDiscovered(final BluetoothDevice device, final int rssi, byte[] advertisement) {
        //Here in a thread not blocking UI

        if (previous.containsKey(device)){
            Log.d("Filter", "FILTER");
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

        if(beacon != null) {
            p = getCoords(beacon);
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


    private static Point getCoords(String uuid) {
        Point coords;
        switch (uuid) {
            case "1cad5144-5bda-11eb-ae93-0242ac130002":
                //coords = new Point(-3.38,3.61);
                coords = new Point(7.5*0.5, 5*0.5);

                break;
            case "2d68cb07-d277-465e-8a44-bf509eccf6de":
                //coords = new Point(8,3.8);
                coords = new Point(7.5*0.5, 6*0.5);
                break;
            case "8ec76ea3-6668-48da-9866-75be8bc86fbb":
                //coords = new Point(0,0);
                coords = new Point(4.5*0.5,3*0.5);
                break;
            default:
                coords = new Point(-100,-100); // Valeur par défaut aberrantes;
                break;
        }
        Log.d("[getCoords]",""+coords.getX()+" "+coords.getY());
        return coords;
    }


    private static com.example.testmenu.bluetooth.Point getCoords(BeaconModel beacon) {
        //beacon.setCoords(Controller.getBeaconsCoords(beacon.uuid));
        Point coords = getCoords(beacon.uuid);
        beacon.setCoords(coords.getX(), coords.getY());

        Log.d("[DEBUG]", "Entering trilateration");
        t.updateBeacon(beacon);
        p = t.getPosition(100, p);
        current_position = new Point(p.getX(), p.getY());
        return p;
    }




    public void ScanButton(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    public com.example.testmenu.bluetooth.Point getPosition(){
        return p;
    }


    public static byte[] filter(byte[] input, byte[] prev, float alpha) {
        if (input == null || prev == null)
            throw new NullPointerException("input and prev float arrays must be non-NULL");
        if (input.length != prev.length)
            throw new IllegalArgumentException("input and prev must be the same length");

        for (int i = 0; i < input.length; i++) {
            prev[i] = (byte) (prev[i] + alpha * (input[i] - prev[i]));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == BT_REQUEST_ID) {
            if(isBluetoothAvailableAndEnabled()) {
                startScanning();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null){
            if (intentResult.getContents() == null){
                textView.setText("Cancelled");
            }
            else {
                textView.setText(intentResult.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        //placeHolder(i,j,k,Integer.getInteger(textView.getText().toString()));
    }

   /* private boolean detectOpenGLES30() {
        ActivityManager am =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }*/

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/



}

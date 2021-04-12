package com.beacons.demo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.Random;

class BeaconsAdapter extends BaseAdapter
{

    private final ArrayList<BeaconModel> mBeacons = new ArrayList<>();


    @Override
    public int getCount() {
        return mBeacons.size();
    }

    @Override
    public BeaconModel getItem(int position) {
        return mBeacons.get(position);
    }

    public void addNewBeacon(final BeaconModel beacon) {
        mBeacons.add(beacon);
        notifyDataSetChanged();
    }

    public BeaconModel findBeaconWithId(final String id) {
        for(final BeaconModel beacon : mBeacons) {
            if(beacon.id.equals(id)) return beacon;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(parent.getContext(), android.R.layout.two_line_list_item, null);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(holder == null) {
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.updateAccordingToBeacon(getItem(position));

        return convertView;
    }


    private class ViewHolder {
        public TextView text1;
        public TextView text2;

        public ViewHolder(final View target) {
            text1 = (TextView) target.findViewById(android.R.id.text1);
            text2 = (TextView) target.findViewById(android.R.id.text2);
        }

        public void updateAccordingToBeacon(final BeaconModel beacon) {
            text1.setText(beacon.uuid);
            String secondLine = String.format("%s\nRSSI: %d dBm TxPower: %d distance: %fm, x: %f, y: %f",
                    beacon.arguments, beacon.rssi, beacon.txPower, beacon.distance, beacon.x, beacon.y);
            text2.setText(secondLine);



        }
    }
}

#!/bin/bash

# Advertising flags
AD_FLAGS="02 01 1a"

# Beacon protocol. All items prefilled here cannot be edited, those which can are taken from the profile
AD_LENGTH="1b"
AD_TYPE="ff"
MFG_ID="18 01"
BEACON_CODE="be ac"
BEACON_ID="{{company_id}} {{premises_id}} {{beacon_node_id}}"
REFERENCE_RSSI="{{reference_rssi}}"
MFG_RESERVED="01"

Ad_Flags=`echo "$AD_FLAGS"`
Advertisement=`echo "$AD_LENGTH $AD_TYPE $MFG_ID $BEACON_CODE $BEACON_ID $REFERENCE_RSSI $MFG_RESERVED"`

# Commands running on Raspberry Pi
BLE="hci0"

# Turn off BLE
sudo hciconfig $BLE down

# Turn on BLE
sudo hciconfig $BLE up

# Stop LE advertising
sudo hciconfig $BLUETOOTH_DEVICE noleadv

# Start LE advertising (non-connectable)
sudo hciconfig $BLE leadv 3

# Turn scanning off (can sometimes affect advertising)
sudo hciconfig $BLE noscan

# Set the Beacon
sudo hcitool -i $BLE cmd 0x08 0x0008 1f $Ad_Flags $Advertisement

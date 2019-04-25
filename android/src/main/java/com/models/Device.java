package com.reactlibrary.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.List;
import java.util.UUID;

public class Device  {

    private interface Metadata {
        String ID = "id";
        String NAME = "name";
        String RSSI = "rssi";
        String MTU = "mtu";

        String MANUFACTURER_DATA = "manufacturerData";
        String SERVICE_DATA = "serviceData";
        String SERVICE_UUIDS = "serviceUUIDs";
        String LOCAL_NAME = "localName";
        String TX_POWER_LEVEL = "txPowerLevel";
        String SOLICITED_SERVICE_UUIDS = "solicitedServiceUUIDs";
        String IS_CONNECTABLE = "isConnectable";
        String OVERFLOW_SERVICE_UUIDS = "overflowServiceUUIDs";
    }

    private BluetoothDevice device;
    private BluetoothAdapter adapter;
    
    public Device(@NonNull BluetoothDevice device, @Nullable BluetoothAdapter adapter) {
        this.device = device;
        this.adapter = adapter;
    }

    public BluetoothDevice getNativeDevice() {
        return device;
    }

    @Nullable
    public BluetoothAdapter getAdapter() {
        return adapter;
    }

    public WritableMap toJSObject(@Nullable Integer rssi) {
        WritableMap result = Arguments.createMap();
        result.putString(Metadata.ID, device.getAddress());
        result.putString(Metadata.NAME, device.getName());
        if (rssi != null) {
            result.putInt(Metadata.RSSI, rssi);
        } else {
            result.putNull(Metadata.RSSI);
        }

        // Advertisement data is not set
        result.putNull(Metadata.MANUFACTURER_DATA);
        result.putNull(Metadata.SERVICE_DATA);
        result.putNull(Metadata.SERVICE_UUIDS);
        result.putNull(Metadata.LOCAL_NAME);
        result.putNull(Metadata.TX_POWER_LEVEL);
        result.putNull(Metadata.SOLICITED_SERVICE_UUIDS);
        result.putNull(Metadata.IS_CONNECTABLE);
        result.putNull(Metadata.OVERFLOW_SERVICE_UUIDS);

        return result;
    }
}

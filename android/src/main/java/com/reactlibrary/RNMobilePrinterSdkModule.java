
package com.reactlibrary;
import com.reactlibrary.models.Device;
import com.reactlibrary.service.BluetoothService;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Callback;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;
import java.util.Set;
import android.os.Handler;

public class RNMobilePrinterSdkModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private BluetoothAdapter mBluetoothAdapter;
  private BluetoothService bluetoothService;
  public RNMobilePrinterSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.bluetoothService = new BluetoothService(getReactApplicationContext(), new Handler());
  }

  @ReactMethod 
  public void connectToDevice(String address,Promise promise){
    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
    this.bluetoothService.connect(device);
  }
  @ReactMethod
  public void startConnection(final Promise promise){
    this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (mBluetoothAdapter == null) {
			Toast.makeText(getReactApplicationContext(), "Bluetooth is not available",
          Toast.LENGTH_LONG).show();
      promise.resolve("OK");
		}else{
      Toast.makeText(getReactApplicationContext(), "Adapter granted",
          Toast.LENGTH_LONG).show();
      promise.resolve("NOK");
    }
    
  }
  @ReactMethod
  public void getPairedDevices(final Promise promise){
    
    Set<BluetoothDevice> pairedDevices = this.mBluetoothAdapter.getBondedDevices();
    WritableArray devicesArray = Arguments.createArray();
    for(BluetoothDevice device : pairedDevices){
      Device rnDevice = new Device(device,this.mBluetoothAdapter);
      devicesArray.pushMap(rnDevice.toJSObject(null));
    }
    promise.resolve(devicesArray);
  }
  @ReactMethod
  public void showMessage(final Promise promise) {
      promise.resolve("ASD");
  }
  @Override
  public String getName() {
    return "RNMobilePrinterSdk";
  }
}
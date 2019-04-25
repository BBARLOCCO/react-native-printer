
package com.reactlibrary;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

public class RNMobilePrinterSdkModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private BluetoothAdapter mBluetoothAdapter;
  public RNMobilePrinterSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    
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
    Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
    promise.resolve(pairedDevices);
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

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
import android.os.Message;

public class RNMobilePrinterSdkModule extends ReactContextBaseJavaModule {

  // Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int MESSAGE_CONNECTION_LOST = 6;
	public static final int MESSAGE_UNABLE_CONNECT = 7;
  
  // Key names received from the BluetoothService Handler
  public static final String DEVICE_NAME = "device_name";
  public static final String TOAST = "toast";

  private final ReactApplicationContext reactContext;
  private BluetoothAdapter mBluetoothAdapter;
  private BluetoothService bluetoothService;
  private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
          Toast.makeText(getReactApplicationContext(),
          "Connected" ,
          Toast.LENGTH_SHORT).show();
					break;
				case BluetoothService.STATE_CONNECTING:
          Toast.makeText(getReactApplicationContext(),
          "Connecting" ,
          Toast.LENGTH_SHORT).show();
					break;
				case BluetoothService.STATE_LISTEN:
				case BluetoothService.STATE_NONE:
          Toast.makeText(getReactApplicationContext(),
          "Not Connected" ,
          Toast.LENGTH_SHORT).show();
					break;
				}
				break;
			case MESSAGE_WRITE:
				
				break;
			case MESSAGE_READ:
				
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				String mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getReactApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getReactApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                Toast.makeText(getReactApplicationContext(), "Device connection was lost",
                               Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_UNABLE_CONNECT:     //无法连接设备
            	Toast.makeText(getReactApplicationContext(), "Unable to connect device",
                        Toast.LENGTH_SHORT).show();
            	break;
			}
		}
	};

  public RNMobilePrinterSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.bluetoothService = new BluetoothService(getReactApplicationContext(), mHandler);
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
			promise.resolve("OK");
		}else{
      promise.resolve("OK");
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
  @ReactMethod
  public void printText(String text,Promise promise){
		SendDataString(text);
  }
  private void SendDataString(String data) {
		
		if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
			Toast.makeText(getReactApplicationContext(), "Not connected", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (data.length() > 0) {				
			try {
				bluetoothService.write(data.getBytes("GBK"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
  @Override
  public String getName() {
    return "RNMobilePrinterSdk";
  }
}
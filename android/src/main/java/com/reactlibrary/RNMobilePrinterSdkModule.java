
package com.reactlibrary;
import com.reactlibrary.command.sdk.PrinterCommand;
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
import android.util.Log;

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
          
					break;
				case BluetoothService.STATE_CONNECTING:
          
					break;
				case BluetoothService.STATE_LISTEN:
				case BluetoothService.STATE_NONE:
          
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
				/*Toast.makeText(getReactApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();*/
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getReactApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                
                break;
            case MESSAGE_UNABLE_CONNECT:     //无法连接设备
            	
            	break;
			}
		}
	};

  public RNMobilePrinterSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    Log.i("BluetoothService","Construyo Module");
    this.bluetoothService = new BluetoothService(getReactApplicationContext(), mHandler);
    
  }
  
  @ReactMethod 
  public void connectToDevice(String address,Promise promise){
    try{
      Log.d("BluetoothService",address);
      BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
      Log.d("BluetoothService","Connect to device");
      Log.d("BluetoothService",device.getName());
      Log.d("BluetoothService",device.getAddress());
      this.bluetoothService.connect(device);
      promise.resolve(true);
    }catch(Exception e){
      Log.i("BluetoothService",e.getMessage());
      promise.resolve(false);
    }
  }
  @ReactMethod
  public void stopConnection(final Promise promise){
    try{
      this.bluetoothService.stop();
      promise.resolve(true);
    }catch(Exception e){
      Log.i("BluetoothService",e.getMessage());
      promise.resolve(false);
    }
  }
  @ReactMethod
  public void startConnection(final Promise promise){
    try{
      this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      if (mBluetoothAdapter == null) {
        promise.resolve(false);
      }else{
        if(this.mBluetoothAdapter.isEnabled()){
          this.bluetoothService.start();
          promise.resolve(true);
        }else{
          promise.resolve(false);
        }
      }
    }catch(Exception e){
      Log.i("BluetoothService",e.getMessage());
      promise.resolve(false);
    }
    
  }
  @ReactMethod
  public void getPairedDevices(final Promise promise){
    try{
      Set<BluetoothDevice> pairedDevices = this.mBluetoothAdapter.getBondedDevices();
      WritableArray devicesArray = Arguments.createArray();
      for(BluetoothDevice device : pairedDevices){
        Device rnDevice = new Device(device,this.mBluetoothAdapter);
        devicesArray.pushMap(rnDevice.toJSObject(null));
      }
      promise.resolve(devicesArray);
    }catch(Exception e){
      Log.i("BluetoothService",e.getMessage());
      promise.resolve(false);
    }
  }
  @ReactMethod
  public void printText(String text,Promise promise){
    try{
      if(bluetoothService.getState() != BluetoothService.STATE_CONNECTED){
        promise.resolve(false);
      }else{
        SendDataString(text+"\n\n\n");
        promise.resolve(true);
      }
    }catch(Exception e){
      Log.i("BluetoothService",e.getMessage());
      promise.resolve(false);
    }
  }
  private void SendDataString(String data) {
		
		if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
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
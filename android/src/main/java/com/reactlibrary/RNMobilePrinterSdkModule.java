
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNMobilePrinterSdkModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNMobilePrinterSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @ReactMethod
  public void showMessage() {
      Toast.makeText(reactContext.getApplicationContext(), "NATIVE CODE IS WORKING", Toast.LENGTH_LONG).show();
  }
  @Override
  public String getName() {
    return "RNMobilePrinterSdk";
  }
}
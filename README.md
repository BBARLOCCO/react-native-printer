
# react-native-mobile-printer-sdk
Package to connect and print using Bluetooth POS Printer.

## Getting started

`$ npm install react-native-mobile-printer-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-mobile-printer-sdk`
Add perimsions for BLUETOOTH on your manifest.

### Manual installation


#### iOS -- TODO -- Noothing done on iOS yet.

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-mobile-printer-sdk` and add `RNMobilePrinterSdk.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNMobilePrinterSdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNMobilePrinterSdkPackage;` to the imports at the top of the file
  - Add `new RNMobilePrinterSdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-mobile-printer-sdk'
  	project(':react-native-mobile-printer-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-mobile-printer-sdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-mobile-printer-sdk')
  	```


## Usage
```javascript
import RNMobilePrinterSdk from 'react-native-mobile-printer-sdk';

// TODO: What to do with the module?
RNMobilePrinterSdk;
```
  
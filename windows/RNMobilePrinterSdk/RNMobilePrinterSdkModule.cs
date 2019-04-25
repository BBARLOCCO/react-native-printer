using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Mobile.Printer.Sdk.RNMobilePrinterSdk
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNMobilePrinterSdkModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNMobilePrinterSdkModule"/>.
        /// </summary>
        internal RNMobilePrinterSdkModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNMobilePrinterSdk";
            }
        }
    }
}

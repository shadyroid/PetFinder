# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#connect-sdk rules
-keep public class com.google.gson.** {public private protected *;}

-keep public class com.fawry.pos.retailer.ipc.IPCConnectivity { public *;}
-keep public class com.fawry.pos.retailer.ipc.IPCConnectivity$Builder { public *;}

-keep public class com.fawry.pos.retailer.bluetooth.BluetoothConnectivity { public *;}
-keep public class com.fawry.pos.retailer.bluetooth.BluetoothConnectivity$Builder { public *;}

-keep class com.fawry.pos.retailer.bluetooth.** { public *;}
-keep class com.fawry.pos.retailer.modelBuilder.** { public *;}

-keep class com.fawry.pos.retailer.connect.FawryConnect { public *;}
-keep class com.fawry.pos.retailer.connect.FawryConnect$OnConnectionCallBack { public *;}
-keep class com.fawry.pos.retailer.connect.FawryConnect$OnTransactionCallBack { public *;}
-keep class com.fawry.pos.retailer.connect.FawryConnect$Companion { public *;}
-keep class com.fawry.pos.retailer.utilities.Connectivity { public *;}
-keep class com.fawry.pos.retailer.utilities.ConnectivityBuilder { public *;}
-keep class com.fawry.pos.retailer.utilities.SaleBuilder { public *;}
-keep class com.fawry.pos.retailer.utilities.Sale { public *;}
-keep class com.fawry.pos.retailer.utilities.RefundBuilder { public *;}
-keep class com.fawry.pos.retailer.utilities.Refund { public *;}
-keep class com.fawry.pos.retailer.utilities.VoidBuilder { public *;}
-keep class com.fawry.pos.retailer.utilities.VoidModel { public *;}
-keep class com.fawry.pos.retailer.utilities.InquiryBuilder { public *;}
-keep class com.fawry.pos.retailer.utilities.Inquiry { public *;}

-keep enum com.fawry.pos.retailer.connect.model.connection.ConnectionType { public *;}
-keep class com.fawry.pos.retailer.connect.model.connection.ConnectionData { public *;}

-keep enum com.fawry.pos.retailer.connect.model.connection.ConnectionStatus { public *;}
-keep enum com.fawry.pos.retailer.connect.model.payment.PaymentOptionType { public *;}
-keep enum com.fawry.pos.retailer.connect.model.payment.wallet.inquiry.IdType { public *;}

-keep interface com.fawry.pos.retailer.connect.model.ErrorCode { public *;}
-keep enum com.fawry.pos.retailer.connect.model.ErrorCode$Request { public *;}
-keep enum com.fawry.pos.retailer.connect.model.ErrorCode$Payment { public *;}
-keep enum com.fawry.pos.retailer.connect.model.ErrorCode$General { public *;}
-keep enum com.fawry.pos.retailer.connect.model.ErrorCode$Connection { public *;}
-keep enum com.fawry.pos.retailer.connect.model.ErrorCode$Configurations { public *;}
-keep class com.fawry.pos.retailer.connect.model.messages.user.UserData {*;}
-keep class com.fawry.pos.retailer.connect.model.messages.user.UserType {*;}
-keep class com.fawry.pos.retailer.connect.model.payment.extrakeys.ExtraKey {*;}

-keepclassmembers public class com.fawry.pos.retailer.connect.model.payment.** {*;}
-keepclassmembers public class com.fawry.pos.retailer.connect.model.messages.** {*;}

-keep public class android.serialport.** {public private protected *;}

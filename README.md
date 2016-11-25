## Currently under development

## Installation
* Run `npm install --save git+https://github.com/MrToph/react-native-app-launcher.git`
* Add the following to `android/settings.gradle`:
    ```
    include ':react-native-app-launcher'
    project(':react-native-app-launcher').projectDir = new File(settingsDir, '../node_modules/react-native-app-launcher/android')
    ```

* Add the following to `android/app/build.gradle`:
    ```xml
    ...

    dependencies {
        ...
        compile project(':react-native-app-launcher')  // react-native-app-launcher
    }
    ```
* Add the following to `android/app/src/main/AndroidManifest.xml`:
    ```xml
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.reactnativeproject">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
      android:allowBackup="true"
      android:label="@string/app_name"
      android:icon="@mipmap/ic_launcher"
      android:theme="@style/AppTheme">

    ...

        <activity android:name="com.facebook.react.devsupport.DevSettingsActivity" />
        <receiver android:name="io.cmichel.appLauncher.AlarmReceiver" />   <!-- react-native-app-launcher -->
        </application>

    </manifest>
    ```
* Add the following to `android/app/src/main/java/**/MainApplication.java`:
    ```java
    package com.motivation;

    import io.cmichel.appLauncher.LauncherPackage;  // add this for react-native-app-launcher

    public class MainApplication extends Application implements ReactApplication {

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new LauncherPackage()     // add this for react-native-app-launcher
            );
        }
    }
    ```
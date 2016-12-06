package io.cmichel.appLauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmID = intent.getAction();
        launchApplication(context, alarmID);
    }

    private void launchApplication(Context context, String alarmID) {
        String packageName = context.getApplicationContext().getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchIntent.putExtra("alarmID", alarmID);

        context.startActivity(launchIntent);
        Log.i("ReactNativeAppLauncher", "AlarmReceiver: Launching: " + packageName);
    }
}

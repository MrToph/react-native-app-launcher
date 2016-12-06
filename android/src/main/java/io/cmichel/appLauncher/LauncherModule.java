package io.cmichel.appLauncher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class LauncherModule extends ReactContextBaseJavaModule {

  public LauncherModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "AppLauncher";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    return constants;
  }

  /**
   * Creates or overwrites an alarm that launches the main application at the specified timestamp.
   * You can set multiple alarms by using different ids.
   * @param id The id identifying this alarm.
   * @param timestamp When to fire off the alarm.
   * @param inexact Determines if the alarm should be inexact to save on battery power.
     */
  @ReactMethod
  public final void setAlarm(String id, double timestamp, boolean inexact) {
    PendingIntent pendingIntent = createPendingIntent(id);
    long timestampLong = (long)timestamp; // React Bridge doesn't understand longs
    // get the alarm manager, and schedule an alarm that calls the receiver
    // We will use setAlarmClock because we want an indicator to show in the status bar.
    // If you want to modify it and are unsure what to method to use, check https://plus.google.com/+AndroidDevelopers/posts/GdNrQciPwqo
    if(!inexact) {
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        getAlarmManager().setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timestampLong, pendingIntent);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        getAlarmManager().setAlarmClock(new AlarmManager.AlarmClockInfo(timestampLong, pendingIntent), pendingIntent);
      else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        getAlarmManager().setExact(AlarmManager.RTC_WAKEUP, timestampLong, pendingIntent);
      else
        getAlarmManager().set(AlarmManager.RTC_WAKEUP, timestampLong, pendingIntent);
    } else {
      getAlarmManager().set(AlarmManager.RTC_WAKEUP, timestampLong, pendingIntent);
    }
    Context context = getReactApplicationContext();
  }

  @ReactMethod
  public final void clearAlarm(String id) {
    PendingIntent pendingIntent = createPendingIntent(id);
    getAlarmManager().cancel(pendingIntent);
  }

  private PendingIntent createPendingIntent(String id) {
    Context context = getReactApplicationContext();
    // create the pending intent
    Intent intent = new Intent(context, AlarmReceiver.class);
    // set unique alarm ID to identify it. Used for clearing and seeing which one fired
    // public boolean filterEquals(Intent other) compare the action, data, type, package, component, and categories, but do not compare the extra
    intent.setData(Uri.parse("id://" + id));
    intent.setAction(String.valueOf(id));
    return PendingIntent.getBroadcast(context, 0, intent, 0);
  }

  private AlarmManager getAlarmManager() {
    return (AlarmManager) getReactApplicationContext().getSystemService(Context.ALARM_SERVICE);
  }
}
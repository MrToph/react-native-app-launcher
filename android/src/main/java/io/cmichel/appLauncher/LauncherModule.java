package io.cmichel.appLauncher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class LauncherModule extends ReactContextBaseJavaModule {

  private static final String DURATION_SHORT_KEY = "SHORT";
  private static final String DURATION_LONG_KEY = "LONG";

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
    constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
    constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
    return constants;
  }

  /**
   * Creates or overwrites an alarm that launches the main application at the specified timestamp.
   * You can set multiple alarms by using different ids.
   * @param id The id identifying this alarm.
   * @param seconds When to fire off the alarm.
   * @param exact Determines if the alarm should be exact, or can be inexact to save on battery power.
     */
  @ReactMethod
  public final void setAlarm(String id, int seconds, boolean exact) {
    PendingIntent pendingIntent = createPendingIntent(id);
    // get the alarm manager, and schedule an alarm that calls the receiver
    getAlarmManager().set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds * 1000, pendingIntent);
    Context context = getReactApplicationContext();
    Toast.makeText(context, "Timer with id'" + id + "' set to " + seconds + " seconds.", Toast.LENGTH_LONG).show();
  }

  @ReactMethod
  public final void clearAlarm(String id) {
    PendingIntent pendingIntent = createPendingIntent(id);
    getAlarmManager().cancel(pendingIntent);
    Context context = getReactApplicationContext();
    Toast.makeText(context, "Timer with id " + id + " cleared.", Toast.LENGTH_LONG).show();
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
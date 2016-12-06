'use strict';

var fail = 'Cannot use react-native-app-launcher on IOS.';
class AppLauncher {

  static setAlarm() {
    console.warn(fail);
  }

  static clearAlarm() {
    console.warn(fail);
  }

}

module.exports = AppLauncher;
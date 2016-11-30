'use strict';

var fail = 'Cannot use react-native-app-launcher on IOS.';
class AppLauncher {

  static setAlarm() {
    warning(false, fail);
  }

  static clearAlarm() {
    warning(false, fail);
  }

}

module.exports = AppLauncher;
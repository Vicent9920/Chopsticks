// Ionic NurseryApp App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'NurseryApp' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'NurseryApp.services' is found in services.js
// 'NurseryApp.controllers' is found in controllers.js
angular.module('NurseryApp', ['ionic', 'NurseryApp.controllers', 'NurseryApp.services','NurseryApp.config'])
.run(function($ionicPlatform,Constants,$rootScope,$ionicPopup){  //全局执行
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);
````````````````````````````````````````````````
    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
  // 确认对话框
  $rootScope.confirmPopup = function (title, content, confirmFun, cancelFun) {
    $ionicPopup.confirm({
      title: title,
      template: content
    }).then(function (res) {
      if (res) {
        confirmFun();
      } else {
        cancelFun();
      }
    });
  };
  // 提示对话框
  $rootScope.alertPop = function (content) {
    $ionicPopup.alert({
      title: '温馨提示：',
      template: content
    });
  };

})

.config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider) {  //全局配置
  $ionicConfigProvider.platform.ios.tabs.style('standard'); //平台配置样式
  $ionicConfigProvider.platform.ios.tabs.position('bottom');
  $ionicConfigProvider.platform.android.tabs.style('standard');
  $ionicConfigProvider.platform.android.tabs.position('bottom');

  $ionicConfigProvider.platform.ios.navBar.alignTitle('center');
  $ionicConfigProvider.platform.android.navBar.alignTitle('center');

  $ionicConfigProvider.platform.ios.backButton.previousTitleText('').icon('ion-ios-arrow-thin-left');
  $ionicConfigProvider.platform.android.backButton.previousTitleText('').icon('ion-android-arrow-back');

  $ionicConfigProvider.platform.ios.views.transition('ios');
  $ionicConfigProvider.platform.android.views.transition('android');

  $ionicConfigProvider.platform.ios.scrolling.jsScrolling(true);
  $ionicConfigProvider.platform.android.scrolling.jsScrolling(true);
  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  // setup an abstract state for the tabs directive
    .state('tab', {
    url: '/tab',
    abstract: true,
    templateUrl: 'templates/tabs.html'
  })

  // Each tab has its own nav history stack:

  .state('tab.dash', {
    url: '/dash',
    views: {
      'tab-dash': {
        templateUrl: 'templates/tab-dash.html',
        controller: 'DashCtrl'
      }
    }
  })

  .state('tab.chats', {
      url: '/chats',
      cache:false,
      views: {
        'tab-chats': {
          templateUrl: 'templates/tab-chats.html',
          controller: 'ChatsCtrl'
        }
      }
    })
    .state('tab.chat-detail', {
      url: '/chats/:chatId',
      views: {
        'tab-chats': {
          templateUrl: 'templates/chat-detail.html',
          controller: 'ChatDetailCtrl'
        }
      }
    })

  .state('tab.account', {
    url: '/account',
    views: {
      'tab-account': {
        templateUrl: 'templates/tab-account.html',
        controller: 'AccountCtrl'
      }
    }
  });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/dash');

});

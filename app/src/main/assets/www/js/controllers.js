angular.module('NurseryApp.controllers', [])

.controller('DashCtrl', function($scope,$http) {
  connectWebViewJavascriptBridge(function(bridge) {
    //初始化
    bridge.init(function(message, responseCallback) {});
    //接收安卓发来的消息   并返回给安卓通知
//				bridge.registerHandler("InitData", function(data, responseCallback) {

    // });
    //接收安卓发来的消息   并返回给安卓通知
//				bridge.registerHandler("InitPicture", funct ion(data, responseCallback) {

    // });
  });
  $scope.user={
    username: "",
    password:  "",
  };
  // 简单的 GET 请求，可以改为 POST
  $scope.signIn=function () {  //登陆
    $http({
      method: 'POST',
      url: 'http://dev.l-try.com/user/login?',
      headers:{'Content-Type': 'application/x-www-form-urlencoded'},
      params:{
        name:$scope.user.username,
        passwd:$scope.user.password
      }
    }).then(function successCallback(response) {
      // 请求成功执行代码
      if(response.data.code==='000'){
          alert(response.data.msg);
      }
    }, function errorCallback(response) {
      // 请求失败执行代码
      alert('请稍后重试');
    });
  };
})

.controller('ChatsCtrl', function($scope,$state, Chats) {
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //
  //$scope.$on('$ionicView.enter', function(e) {
  //});
  $scope.toChatDetail = function (producerId) {
    $state.go('tab.chat-detail', {chatId: producerId});
  };
  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  };
})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats,$http) {
  $scope.chat = Chats.get($stateParams.chatId);
  $scope.change = function(id){
    Chats.change(id,$scope.src);
  };
  $scope.abc = function(){
    console.log(123);
    localStorage.setItem("abc",123);
  }
  $scope.src ="./img/a123.jpg";
  $scope.server = function(){
    window.WebViewJavascriptBridge.callHandler('openMedia', "null", function(data) {
      if(data){
        $scope.src = data;
        $scope.$apply();
      }
    });
  }
})

.controller('AccountCtrl', function($scope,$rootScope,$templateCache,$sce,$ionicModal,$ionicPopup) {
  $scope.settings = {
    enableFriends: true
  };

  $scope.checkeMap=function () {  //点击查看地图调起原生Android查看地图
    $rootScope.alertPop('查看地图');
    // $scope.tt = new Date().getMilliseconds();
    // $scope.chatStru = {
    //   paySrc: $sce.trustAsResourceUrl('https://www.baidu.com/?tt='+$scope.tt),
    //   token: "",
    //   hasToken: false
    // };
    //
    // $scope.modal = $ionicPopup.show({
    //   templateUrl: 'login.html',
    //   scope: $scope
    // });
    //
    // // $ionicModal.fromTemplateUrl('login.html', {
    // //   scope: $scope,
    // //   animation: 'slide-in-up'
    // // }).then(function (modal) {
    // //   $scope.modal = modal;
    // // });
    //
    // $scope.openModal = function () {
    //   $scope.modal.show();
    // };
    // $scope.closeModal = function () {
    //   $scope.modal.hide();
    // };
  };

});

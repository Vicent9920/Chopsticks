angular.module('NurseryApp.services', [])
  .service("apiService", ["$rootScope", "$state", "$http", "$timeout", "$q", "Constants", function ($rootScope, $state, $http, $timeout, $q, Constants) {
    /**
     * 获取接口数据
     * @param {string} method 请求方式
     * @param {string} urlPostfix 请求链接的后缀路径
     * @param {object} params 包含提交的 POST 数据的对象 eg：{a : 1, b : "haha"}
     * @returns {Promise} 返回一个带有请求结果的promise对象
     * @constructor
     */
    this.GetData = function (method, urlPostfix, params, SuccessFunc, ErrorFunc) {
      var deferred = $q.defer();
      console.log("%c" + urlPostfix + " Params:", "background-color:yellow", params);
      $http({
        headers:{'Content-Type': 'application/x-www-form-urlencoded'},  //设置请求头 解决跨域问题
        method: method,
        url: Constants.api + urlPostfix,
        //url:urlPostfix,
        params: params,
        timeout: Constants.timeout
      })
        .success(function (response) {
          console.log("%c" + urlPostfix + " res: ", "background-color:yellow", response);
          deferred.resolve(response);
        })
        .error(function (err) {
          console.log("%c请求失败", "color:white;background:red;");
          deferred.reject(err);
        });
      return deferred.promise;
    };

    //使用模板
    //ApiService.GetData("POST", "/getMember", {
    //    //参数...
    //}).then(function (res) {
    //    //成功...})

  }])

.factory('Chats', function() {
  // Might use a resource here that returns a JSON array

  // Some fake testing data
  var chats = [{
    id: 0,
    name: 'Ben Sparrow',
    lastText: 'You on your way?',
    face: 'img/ben.png'
  }, {
    id: 1,
    name: 'Max Lynx',
    lastText: 'Hey, it\'s me',
    face: 'img/max.png'
  }, {
    id: 2,
    name: 'Adam Bradleyson',
    lastText: 'I should buy a boat',
    face: 'img/adam.jpg'
  }, {
    id: 3,
    name: 'Perry Governor',
    lastText: 'Look at my mukluks!',
    face: 'img/perry.png'
  }, {
    id: 4,
    name: 'Mike Harrington',
    lastText: 'This is wicked good ice cream.',
    face: 'img/mike.png'
  }];

  return {
    all: function() {
      return chats;
    },
    remove: function(chat) {
      chats.splice(chats.indexOf(chat), 1);
    },
    get: function(chatId) {
      for (var i = 0; i < chats.length; i++) {
        if (chats[i].id === parseInt(chatId)) {
          return chats[i];
        }
      }
      return null;
    },
    change: function(chatId,src){
      for (var i = 0; i < chats.length; i++) {
        if (chats[i].id === parseInt(chatId)) {
          chats[i].face=src;
        }
      }
    }
  };
});

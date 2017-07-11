/**
 * Created by cj on 2017/6/20.
 */
//常量配置
var configMod = angular.module("NurseryApp.config", []);
configMod.constant("Constants", {
  "debug": false,
  "resultCode": {"SUCCESS": 0, "FAILED": 1},
  'version': '1.0.0',
  'timeout': 1000 * 30,//网络请求timeout
  'userAppId': 'HTML5',
  "api": "",
  "ResourceImageHost":""
});


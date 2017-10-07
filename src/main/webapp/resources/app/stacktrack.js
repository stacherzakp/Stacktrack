angular.module('stacktrack', ['ngResource', 'ngRoute', 'ngMaterial', 'ngFileUpload', 'ngAnimate', 'ui.bootstrap']);

angular.module('stacktrack').factory('httpRequestInterceptor', function () {
  return {
    request: function (config) {

      config.headers['Accept'] = 'application/json;';
      config.headers['X-Frame-Options'] = "SAMEORIGIN";
      config.headers['Cache-Control'] = "no-cache,no-store";
      config.headers['Pragma'] = "no-cache";
      config.headers['X-XSS-Protection'] = "1; mode=block";

      return config;
    }
  };
});

angular.module('stacktrack').config(function ($httpProvider) {
  $httpProvider.interceptors.push('httpRequestInterceptor');
});
angular.module('stacktrack').config(function($routeProvider) {
	   $routeProvider
	    .when("/index", {
	        templateUrl : "resources/app/paste.html"
	    })
	    .when("/activity", {
	        templateUrl : "resources/app/activity.html"
	    })
	    .when("/pastes/:uuid", {
	    	templateUrl : "resources/app/content.html",
	    	controller : "contentCtrl"
	    })
	    .when("/pastes/raw/:uuid", {
	    	templateUrl : "resources/app/rawContent.html",
	    	controller : "contentCtrl"
	    })
	    .otherwise({
	        templateUrl : "resources/app/paste.html"
	    });
});
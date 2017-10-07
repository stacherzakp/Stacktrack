angular.module('stacktrack').controller('navbarCtrl', function($scope, $http, $document, $location) 
{
	var that = this;
	
	that.switchToActivity = function()
	{	
		$location.path('/activity');
	};

	that.switchToPaste = function()
	{	
		$location.path('/paste');
	};
});
angular.module('stacktrack').controller('activityCtrl', function($scope, $http, $location) 
{
	var that = this;

	that.searchTitle = "";
	that.searchAuthor = "";
	that.searchedText = "Search: ";

	that.latest = [];
	that.searched = [];

	that.alert = {
		enabled: false,
		type: "danger",
		msg: "Internal error."
	};

	that.prepareSearchLabel = function() 
	{
		if (that.searchAuthor && that.searchTitle) {
			that.searchedText = 'Author: ' + that.searchAuthor + ", title: " + that.searchTitle;
		} else if (that.searchAuthor) {
			that.searchedText = "Author: " + that.searchAuthor;
		} else {
			that.searchedText = "Title: " + that.searchTitle;
		}
	};

	that.getLatest = function()
	{
		$http.get('pastes')
		.then(function(response) {
			that.latest = response.data;
		})
		.catch(function(response) {
			if (response.status == '400' || response.status == '500') {
				that.alert.enabled = true;
				that.alert.msg = "Problem with retrieving latest pastes.";
				that.alert.type = "warning";
			}
		})
	};

	that.search = function()
	{
		if (that.searchTitle || that.searchAuthor) {
			that.prepareSearchLabel();

			$http.get('pastes/search', {params:{"title": that.searchTitle, "author": that.searchAuthor}})
			.then(function(response) {
				that.searched = response.data;
			})
			.catch(function(response) {
				if (response.status == '400' || response.status == '500') {
					that.alert.enabled = true;
					that.alert.msg = "Cannot search by requested params.";
					that.alert.type = "danger";
				}
			});
		}
	};

	that.goToRaw = function(uuid) {
		$location.path('/pastes/raw/' + uuid);
	};

	that.goToContent = function(uuid) {
		$location.path('/pastes/' + uuid);
	};

	that.calculateTimeAgo = function(dateToCheck) 
	{
		var seconds = that.calculateSecondsAgo(dateToCheck);
		var minutes = Math.floor(seconds / 60);
		var hours = Math.floor(minutes / 60);
		var days = Math.floor(hours / 24);

		if (days > 0) {
			if (days == 1) {
				return days + ' day ago';
			} else {
				return days + ' days ago';
			}
		} else if (hours > 0) {
			if (hours == 1) {
				return hours + ' hour ago';
			} else {
				return hours + ' hours ago';
			}
		} else if (minutes > 0) {
			if (minutes == 1) {
				return minutes + ' minute ago';
			} else {
				return minutes + ' minutes ago';
			}
		} else {
			return 'less than minute ago'
		}
	};

	that.calculateSecondsAgo = function(dateToCheck) 
	{
		var date = dateToCheck.split(" ")[0];
		var time = dateToCheck.split(" ")[1];

		var dateParts = date.split("-");
		var timeParts = time.split(":");

		var dateObjectToCheck = new Date(dateParts[2], dateParts[1] - 1, dateParts[0], timeParts[0], timeParts[1], timeParts[2]);

		return Math.floor((new Date().getTime() - dateObjectToCheck.getTime()) / 1000); 
	};

	that.closeAlert = function() 
	{
		that.alert.enabled = false;
	};
})
.config(function($mdThemingProvider) 
{
  $mdThemingProvider.theme('lime').backgroundPalette('lime');
});
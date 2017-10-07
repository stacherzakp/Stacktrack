angular.module('stacktrack').controller('contentCtrl', function($scope, $http, $location, $mdDialog, $routeParams) 
{
	var that = this;
	var uuid = $routeParams.uuid;

	that.password = '';

	that.contentData = {
		title: '',
		author: '',
		createDate: '',
		content: ''
	};

	that.metadata = function() {
		if (that.contentData.author) {
			return that.contentData.author + ', ' + that.contentData.createDate;
		}
		return that.contentData.createDate;
	};

	that.goToRaw = function() {
		$location.path('/pastes/raw/' + uuid);
	};

	that.getContent = function()
	{
		$http.get('pastes/' + uuid)
		.then(function(response) {
			that.contentData.title = response.data.title;
			that.contentData.content = response.data.content;
			that.contentData.author = response.data.author;
			that.contentData.createDate = response.data.createDate;
		})
		.catch(function(response) {
			if (response.status == '401') {
				that.showPasswordPrompt();
			}
		})
	};

	that.sendWithPassword = function() {
		var data = {'password': that.password};

		$http.post('pastes/' + uuid, data)
		.then(function(response) {
			that.contentData.title = response.data.title;
			that.contentData.content = response.data.content;
			that.contentData.author = response.data.author;
			that.contentData.createDate = response.data.createDate;
		})
		.catch(function(response) {
			if (response.status == '401') {
				that.showPasswordPrompt();
			}
		});
	}

	that.showPasswordPrompt = function() 
	{    
	    $mdDialog.show({
	      controller: PasswordPromptController,
	      controllerAs: 'that',
	      templateUrl: 'resources/app/modals/passwordPrompt.html',
	      parent: angular.element(document.body),
	      clickOutsideToClose: false
	    })
	    .then(function(answer) {
	      that.password = answer;
	      that.sendWithPassword();
	    });
  	};

  	function PasswordPromptController($scope, $mdDialog) 
  	{
  		var that = this;
  		that.password = '';

	    that.applyPassword = function(answer) {
	      $mdDialog.hide(that.password);
	    };
  	}

});
angular.module('stacktrack').controller('pasteCtrl', function($scope, $http, $document, $location) 
{
	var that = this;

	that.pasteModel = {
		title: '',
		author: '',
		privacy: 'PUBLIC',
		password: '',
		content: ''
	};

	that.file = undefined;
	that.uploadProgress = 0;

	that.invalidFile = undefined;
	that.savingError = false;

	that.invalidFileMessage = function() {
		if (that.invalidFile && that.invalidFile.$errorMessages) {
			if (that.invalidFile.$errorMessages.pattern) {
				return "Only .txt and .xml files are allowed.";
			} else {
				return "File size exceeds 5MB.";
			}
		} else {
			return "";
		}
	};

	that.isPrivatePaste = function() {
		return that.pasteModel.privacy == 'PRIVATE';
	};

	that.create = function()
	{	
		$http.post('pastes', that.pasteModel)
		.then(function(response) {
			$location.path(response.headers('Location'));
		})
		.catch(function(response) {
			if (response.status == '400' || response.status == '500') {
				that.savingError = true;
			}
		});
	};

	that.submitFile = function() {
		that.onFileSelect();
		that.uploadFile(that.file, that.pasteModel);
	};

	that.onFileSelect = function() {
		that.uploadProgress = 0;
	};

	that.uploadFile = function(file, model) {

		var formData = new FormData();
        formData.append("file", file);
        formData.append("title", model.title);
        formData.append("author", model.author);
        formData.append("privacy", model.privacy);
        formData.append("password", model.password);

		$http({url: 'pastes',
        	method: 'POST',
        	headers: { 'Content-Type': undefined },
        	data: formData,
        	transformRequest: angular.identity,
            uploadEventHandlers: {
				progress: function(e) {
            		that.uploadProgress = parseInt(100.0 * e.loaded / e.total, 10);}
            }})
		.then(function(response) {
			$location.path(response.headers('Location'));
		})
		.catch(function(response) {
			if (response.status == '400' || response.status == '500') {
				that.savingError = true;
			}
		});
	};

	that.closeAlert = function() {
		that.savingError = false;
	};

});
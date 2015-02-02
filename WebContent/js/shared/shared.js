angular.module('modules.common.shared', [])
.factory('ModalFactory', function ($modal) {
	var factory = {};
	factory.sendMail = function(user,mailconfig,service){
		var modalInstance = $modal.open({
			  templateUrl: 'template/popup/sendemail.htm',
			  controller: 'EmailCtrl',
			  windowClass: 'app-modal-window',
			  size: "lg",
			  resolve: {
				emailSender: function(){
					return { user: user,mailconfig: mailconfig,service: service,type:'test' };
				}
			}
		});
	};
	factory.sendDocument = function(user,document,service){
		var modalInstance = $modal.open({
			  templateUrl: 'template/popup/sendemail.htm',
			  controller: 'EmailCtrl',
			  windowClass: 'app-modal-window',
			  size: "lg",
			  resolve: {
				emailSender: function(){
					return { user: user,service: service,type:'document',document: document };
				}
			}
		});
	};
	factory.confirm = function(){
		var modalInstance = $modal.open({
			  templateUrl: 'template/popup/confirm.htm',
			  controller: 'ErrorCtrl',
			  windowClass: 'app-modal-window',
			  size: "lg",
			  resolve: {
				emailSender: function(){
					return { };
				}
			  }
		});
	};
	factory.error = function(message){
		var modalInstance = $modal.open({
			  templateUrl: 'template/popup/error.htm',
			  controller: 'ErrorCtrl',
			  windowClass: 'app-modal-window',
			  size: "lg",
			  resolve: {
				emailSender: function(){
					return { message: message };
				}
			}
		});
	};
	return factory;
	
});
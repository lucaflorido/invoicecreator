/**
 * 
 */
angular.module('modules.common.shared', [])
.directive('onEnter', function () {
  return function (scope, element, attrs) {
    element.bind("keydown keypress", function (event) {
      if (event.which === 13) {
        scope.$apply(function () {
          scope.$eval(attrs.onEnter);
        });
        event.preventDefault();
      }
    });
  };
})
.factory("AlertsFactory",function(){
	var factory = {};
	factory.type = "";
	factory.message = "";
	factory.initialize = function(){
		factory.type = "";
		factory.message = "";
	};
	factory.infoMessage = function(message){
		factory.type = "info";
		factory.message = message;
	};
	factory.successMessage = function(message){
		factory.type = "success";
		factory.message = message;
	};
	factory.warningMessage = function(message){
		factory.type = "warning";
		factory.message = message;
	};
	factory.alertMessage = function(message){
		factory.type = "alert";
		factory.message = message;
	};
	return factory;
}).factory("PermissionFactory",function(){
	var factory = {};
	factory.permission = "";
	factory.setupPermission = function(permit){
		factory.permission = permit;
	}
	return factory;
}).factory("LoaderFactory",function(){
	var factory = {};
	factory.loader = false;
	
	return factory;
});
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
	factory.user = null;
	return factory;
}).factory("LoaderFactory",function(){
	var factory = {};
	factory.loader = false;
	
	return factory;
}).factory("MenuFactory",function(){
	var factory = {};
	
	return factory;
}).factory("FormatFactory",function(){
	var factory = {};
	factory.formatCurrency = function(value){
		value=value+"";
		var val = value.split(".");
		if (val.length == 0){
			return '0.00';
		}else if (val.length == 1){
			return val[0]+".00"
		}else if (val.length == 2){
			if (val[1] .length == 1){
				return val[0]+"."+val[1]+"0";
			}else{
				return val[0]+"."+val[1];
			}
		}else{
			return "0.00";
		}
	}
	return factory;
}).factory("DraftFactory",function(){
	var factory = {};
	factory.company = {};
	factory.payments = {};
	factory.user = {};
	factory.checkPayments = function(user){
		var ecp = [];
		if (user && user.iduser){
			ecp = angular.copy(factory.payments);
			if (user.ecpayment){
				ecp.push({ecpayment:user.ecpayment});
			}
		}else{
			ecp = $.grep(factory.payments,function(item){
				return item.ecpayment.nologgeduser == true;
				});
		}
		return ecp;
	}

	return factory;
}).factory('URLFactory', function ($http, $q, AppConfig)
{
    var factory = {};
     factory.getParameter = function (name)
    {

        sParam = name;
        var sPageURL = window.location.href;
        var sURLIntPoint = sPageURL.split('?');
        if (sURLIntPoint.length > 1){
        	var sURLVariables = sURLIntPoint[1].split('&');
            for (var i = 0; i < sURLVariables.length; i++)
            {
                var sParameterName = sURLVariables[i].split('=');
                if (sParameterName[0] == sParam)
                {
                    this.successMessage = sParameterName[1];
                    return this.successMessage;
                }
            }
        }
        


        return this.successMessage;

    };
    return factory;
});

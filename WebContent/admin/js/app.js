var gecoApp = angular.module("gecoApp",["ngRoute","gecoControllers","gecoBasicControllers","gecoRegistryControllers","gecoDocumentControllers","gecoStoreControllers","gecoAccountingControllers"])
.directive('onFinishRender', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    }
}).directive('createDialog', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            
                    element.dialog({autoOpen:false,modal:true});
            
        }
    }
}).directive('capitalize', function($parse) {
   return {
     require: 'ngModel',
     link: function(scope, element, attrs, modelCtrl) {
        var capitalize = function(inputValue) {
		//if (inputValue != null && inputValue != undefined){
			   var capitalized = inputValue.toUpperCase();
			   if(capitalized !== inputValue) {
				  modelCtrl.$setViewValue(capitalized);
				  modelCtrl.$render();
				}         
				return capitalized;
			 }
			 modelCtrl.$parsers.push(capitalize);
			 capitalize($parse(attrs.ngModel)(scope)); // capitalize initial value
		// }
     }
   };
}).directive('createDatepicker', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            
                    element.datepicker({ dateFormat: "dd/mm/yy" ,inline: true,  
            showOtherMonths: true,  
			firstDay:1,
			monthNames: [ "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" ],
            dayNamesMin: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab']   });
            
        }
    }
}).directive('createChosen', function ($timeout) {
    return {
        restrict: 'A',
        scope: {
            name: '@ngDataProvider'
        }
		
    }
}).directive('onFinishChosenUpdate', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
					
			scope.$watch(attrs.ngDataProvider, function() {
					element.chosen({allow_single_deselect: true,width: "25%"});
                    element.trigger("chosen:updated");
			});
            scope.$watch(attrs.ngModel, function() {
					element.chosen({allow_single_deselect: true,width: "25%"});
                    element.trigger("chosen:updated");
			});
                
            
        }
    }
});

gecoApp.config(['$routeProvider',
function($routeProvider) {
	$routeProvider.
		when('/userlist', {
			templateUrl: 'template/userlist.htm',
			controller: 'UserListCtrl'
		}).
		when('/welcome', {
			templateUrl: 'template/home.htm',
			controller: 'WelcomeCtrl'
		}).
		when('/userlist/:userId', {
			templateUrl: 'template/userdetail.htm',
			controller: 'UserDetailCtrl'
		}).
		when('/role', {
			templateUrl: 'template/rolelist.htm',
			controller: 'RoleCtrl'
		}).
		when('/taxrate', {
			templateUrl: 'template/basic/taxratelist.htm',
			controller: 'TaxrateCtrl'
		}).
		when('/counter', {
			templateUrl: 'template/basic/counterlist.htm',
			controller: 'CounterCtrl'
		}).
		when('/storemovement', {
			templateUrl: 'template/basic/storemovementlist.htm',
			controller: 'StoreMovementCtrl'
		}).
		when('/payment', {
			templateUrl: 'template/basic/paymentlist.htm',
			controller: 'PaymentCtrl'
		}).
		when('/document', {
			templateUrl: 'template/basic/documentlist.htm',
			controller: 'DocumentCtrl'
		}).
		when('/groupproduct', {
			templateUrl: 'template/basic/groupproductlist.htm',
			controller: 'GroupProductCtrl'
		}).
		when('/categoryproduct', {
			templateUrl: 'template/basic/categoryproductlist.htm',
			controller: 'CategoryProductCtrl'
		}).
		when('/unitmeasure', {
			templateUrl: 'template/basic/unitmeasurelist.htm',
			controller: 'UnitmeasureCtrl'
		}).
		when('/company/:idcompany', {
			templateUrl: 'template/registry/companydetail.htm',
			controller: 'CompanyCtrl'
		}).
		when('/companylist', {
			templateUrl: 'template/registry/companylist.htm',
			controller: 'CompanyListCtrl'
		}).
		when('/bank', {
			templateUrl: 'template/registry/banklist.htm',
			controller: 'BankListCtrl'
		}).
		when('/bank/:idbank', {
			templateUrl: 'template/registry/bankdetail.htm',
			controller: 'BankDetailCtrl'
		}).
		when('/product', {
			templateUrl: 'template/registry/productlist.htm',
			controller: 'ProductListCtrl'
		}).
		when('/product/:idproduct', {
			templateUrl: 'template/registry/productdetail.htm',
			controller: 'ProductDetailCtrl'
		}).
		when('/list', {
			templateUrl: 'template/registry/listlist.htm',
			controller: 'ListListCtrl'
		}).
		when('/list/:idlist', {
			templateUrl: 'template/registry/listdetail.htm',
			controller: 'ListDetailCtrl'
		}).
		when('/customer', {
			templateUrl: 'template/registry/customerlist.htm',
			controller: 'CustomerListCtrl'
		}).
		when('/customer/:idcustomer', {
			templateUrl: 'template/registry/customerdetail.htm',
			controller: 'CustomerDetailCtrl'
		}).
		when('/destination', {
			templateUrl: 'template/registry/destinationlist.htm',
			controller: 'DestinationListCtrl'
		}).
		when('/destination/:iddestination', {
			templateUrl: 'template/registry/destinationdetail.htm',
			controller: 'DestinationDetailCtrl'
		}).
		when('/supplier', {
			templateUrl: 'template/registry/supplierlist.htm',
			controller: 'SupplierListCtrl'
		}).
		when('/supplier/:idsupplier', {
			templateUrl: 'template/registry/supplierdetail.htm',
			controller: 'SupplierDetailCtrl'
		}).
		when('/transporter', {
			templateUrl: 'template/registry/transporterlist.htm',
			controller: 'TransporterListCtrl'
		}).
		when('/transporter/:idtransporter', {
			templateUrl: 'template/registry/transporterdetail.htm',
			controller: 'TransporterDetailCtrl'
		}).
		when('/headlist/:section/:type', {
			templateUrl: 'template/document/headlist.htm',
			controller: 'HeadListCtrl'
		}).
		when('/generatedocs', {
			templateUrl: 'template/document/generatedocs.htm',
			controller: 'GenerateDocsCtrl'
		}).
		when('/copyrows', {
			templateUrl: 'template/document/copyrows.htm',
			controller: 'CopyRowsCtrl'
		}).
		when('/reportorder', {
			templateUrl: 'template/store/reportorder.htm',
			controller: 'ReportOrderCtrl'
		}).
		when('/createorders', {
			templateUrl: 'template/document/openorders.htm',
			controller: 'CreateOrdersCtrl'
		}).
		when('/head/:idhead', {
			templateUrl: 'template/document/headdetail.htm',
			controller: 'HeadDetailCtrl'
		}).
		when('/head/:idhead/:rows', {
			templateUrl: 'template/document/headdetail.htm',
			controller: 'HeadDetailCtrl'
		}).
		when('/customercategory', {
			templateUrl: 'template/basic/customercategorylist.htm',
			controller: 'CustomerCategoryListCtrl'
		}).
		when('/customergroup', {
			templateUrl: 'template/basic/customergrouplist.htm',
			controller: 'CustomerGroupListCtrl'
		}).
		when('/suppliercategory', {
			templateUrl: 'template/basic/suppliercategorylist.htm',
			controller: 'SupplierCategoryListCtrl'
		}).
		when('/suppliergroup', {
			templateUrl: 'template/basic/suppliergrouplist.htm',
			controller: 'SupplierGroupListCtrl'
		}).
		when('/myprofile/:myuserId', {
			templateUrl: 'template/myprofile.htm',
			controller: 'MyProfileCtrl'
		}).
		when('/login', {
			templateUrl: 'template/welcome.htm',
			controller:'LoginCtrl'
		}).
		when('/storelist', {
			templateUrl: 'template/store/productstorage.htm',
			controller: 'StoreCtrl'
		}).
		when('/brand', {
			templateUrl: 'template/basic/brandlist.htm',
			controller: 'BrandCtrl'
		}).
		when('/storeneeded', {
			templateUrl: 'template/store/storeneeded.htm',
			controller: 'StoreNeededCtrl'
		}).
		when('/accounting/:type', {
			templateUrl: 'template/accounting/accountingcustomer.htm',
			controller: 'AccountingCustomerCtrl'
		}).
		when('/accountinglist/:type', {
			templateUrl: 'template/accounting/accountinglist.htm',
			controller: 'AccountingListCtrl'
		}).
		otherwise({
			redirectTo: '/login'
}		);
}]);
gecoApp.factory('ScopeFactory', function ($http, $q) {
	var scopefactory = {};
	return { 
	    "factory":scopefactory
	}
});
gecoApp.run(function($rootScope) {
	$rootScope.$on('$routeChangeStart', function(next, current) { 
		$(document).unbind("keydown");
		if ($rootScope.issaved == false){
			$.confirm({
			text: "ATTENZIONE ci sono dati non salvati...Salvare ora?",
			confirm: 
				$rootScope.saveFuntion
			,
			cancel: function(button) {
				$rootScope.issaved = true;
			},
			confirmButton: "Si",
			cancelButton: "No"
		});
		}
	});
	$rootScope.pagesize = 100;
	$rootScope.showIncrement = false;
	$rootScope.showFilter = false;
	$rootScope.deleteObj = {};
	$rootScope.issaved = true;
	$rootScope.openFilter = function(){
		if ($rootScope.showIncrement == true){
			$rootScope.showFilter = false;
		}
		$rootScope.showIncrement = false;
		if ($rootScope.showFilter == true){
			$rootScope.showFilter = false;
		}else{
			$rootScope.showFilter = true;
		}
		
	}
	$rootScope.deleteElement = function(obj){
	}
	$rootScope.confirmDelete = function(obj){
		$rootScope.deleteObj = obj;
		$.confirm({
			text: "Confermare l'eliminazione dell'elemento selezionato?",
			confirm: 
				$rootScope.deleteElement
			,
			cancel: function(button) {
				// do something
			},
			confirmButton: "Si",
			cancelButton: "No"
		});
	}
	$rootScope.confirmSaved = function(){

		$.confirm({
			text: "Elemento Salvato con successo",
			confirm: function(button) {
			
			}
				
			,
			cancel: function(button) {
				// do something
			},
			showCancelButton:false,
			confirmButton: "Ok",
			
		});
	}
	$rootScope.saveFuntion = function(){
	
	}
	$rootScope.setSaveControl = function(savefunc){
		$rootScope.issaved = true;
		$rootScope.saveFuntion = savefunc;
		
	}
	$rootScope.setModified = function(){
		$rootScope.issaved = false;
	}
	$rootScope.orderArray = [];
	$rootScope.sortFunction = function(field){
		if ($(".sortericon."+field).hasClass("up")){
			$(".sortericon."+field).removeClass("up");
			$(".sortericon."+field).addClass("down");
			$rootScope.orderArray = jQuery.grep($rootScope.orderArray, function( n ) {
				return ( n != field);
			});
			$rootScope.orderArray.push("-"+field);
		}else if ($(".sortericon."+field).hasClass("down")){
			$(".sortericon."+field).removeClass("down");
			$rootScope.orderArray = jQuery.grep($rootScope.orderArray, function( n ) {
				return ( n != "-"+field);
			});
		}else{
			$(".sortericon."+field).addClass("up");
			$rootScope.orderArray.push(field);
		}
	}
	$rootScope.sortDateFunction = function(field,func){
		if ($(".sortericon."+field).hasClass("up")){
			$(".sortericon."+field).removeClass("up");
			
			$rootScope.orderArray = jQuery.grep($rootScope.orderArray, function( n ) {
				return ( n != func);
			});
			
		}else{
			$(".sortericon."+field).addClass("up");
			$rootScope.orderArray = jQuery.grep($rootScope.orderArray, function( n ) {
				return ( n != func);
			});
			$rootScope.orderArray.push(func);
		}
	}
  }).run( function($rootScope, $location) {
   $rootScope.$watch(function() { 
      return $location.path(); 
    },
    function(a){  
      $rootScope.orderArray = [];
    });
});

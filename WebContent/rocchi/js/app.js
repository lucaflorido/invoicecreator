angular.module("rocchi.customer",[]);
angular.module("rocchi.product",[]);
angular.module("rocchi.list",[]);
angular.module("rocchi.transporter",[]);
angular.module("rocchi.documents",[]);
angular.module("rocchi.parameters",[]);
angular.module("rocchi.promoter",[]);
var gecoApp = angular.module("gecoApp",
["ngRoute",
"gecoControllers",
"mm.foundation",
"smart-table",
'lr.upload',
"rocchi.customer",
"rocchi.product",
"rocchi.transporter",
"rocchi.documents",
"rocchi.parameters",
"rocchi.promoter",
"rocchi.list",
'modules.common.shared',
"gecoBasicControllers","gecoRegistryControllers","gecoDocumentControllers","gecoStoreControllers","gecoAccountingControllers"])
.provider('AppConfig', function ()
		{
			var main_domain = "/InvoiceCreator"
		    this.$get = function ($window)
		    {
		    	return {
	            ServiceUrls: {
	            		AddRow:main_domain+ "/rest/head/addrow/",
		                ListOfCustomer:main_domain+ "/rest/registry/customer/",
		                DetailsOfCustomer:main_domain+ "/rest/registry/customer/",
		                SaveCustomer:main_domain+ "/rest/registry/customer/",
		                CustomerGroup:main_domain+ "/rest/basic/groupcustomer",
		                CustomerCategory:main_domain+ "/rest/basic/categorycustomer",
		                Product:main_domain+ "/rest/registry/product/",
		                ProductMainList:main_domain+ "/rest/registry/products/",
		                ProductBasicPrice:main_domain+ "/rest/util/prodbasicprice/",
		                ProductBasicPriceList:main_domain+ "/rest/util/prodbasicprice/list",
		                ProductCategory:main_domain+ "/rest/basic/categoryproduct/",
		                ProductSubCategory:main_domain+ "/rest/basic/subcategoryproduct/",
		                ProductGroup:main_domain+ "/rest/basic/groupproduct/",
		                ProductIncrement:main_domain+ "/rest/registry/product/increment/",
		                Brand:main_domain+ "/rest/basic/brand/",
		                
		                List:main_domain+ "/rest/registry/list/",
		                ListNoProduct:main_domain+ "/rest/registry/list/noproduct/",
		                SearchProduct:main_domain+ "/rest/registry/product/search/",
		                Transporter:main_domain+ "/rest/registry/transporter/",
	                    DocumentList:main_domain+"/rest/head/head/",
		                DocumentDetails:main_domain+"",
		                ProductUniteMeasure:main_domain+"/rest/registry/productum/",
		                ProductList:main_domain+ "/rest/registry/productlist/",
		                UniteMeasure:main_domain+"/rest/basic/unitmeasure/",
		                UtilPricePercentage:main_domain+"/rest/util/prodbasicprice/percentage/",
		                UtilPricePrice:main_domain+"/rest/util/prodbasicprice/sellprice/",
		                UtilPriceEndPrice:main_domain+"/rest/util/prodbasicprice/endprice/",
		                Promoter:main_domain+"/rest/registry/promoter/",
		                ProductPagination:main_domain+"/rest/registry/product/pages/",
		                Role:main_domain+"/rest/role/",
		                Region:main_domain+"/rest/basic/region/",
		                ExportHeads:main_domain+"/rest/export/heads/",
		                ImportProducts:main_domain+"/rest/import/products/",
		                Upload:main_domain+"/rest/upload/file",
		                TaxRate:main_domain+"/rest/basic/taxrate/"
		            }
		        };
		    };
		})
.directive('fDatepicker', function(){
      return {
         link: function(scope, element) {
            $(element).fdatepicker({format:"dd/mm/yyyy"})
         }
      }
	})
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
		if (inputValue != null && inputValue != undefined){
			   var capitalized = inputValue.toUpperCase();
			   if(capitalized !== inputValue) {
				  modelCtrl.$setViewValue(capitalized);
				  modelCtrl.$render();
				}         
				return capitalized;
			 }
			 modelCtrl.$parsers.push(capitalize);
			 capitalize($parse(attrs.ngModel)(scope)); // capitalize initial value
		 }
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
}).directive('createAccordion', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            
                    element.accordion();  
            
            
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
			controller: 'RocchiTaxrateCtrl'
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
			controller: 'RocchiPaymentCtrl'
		}).
		when('/document', {
			templateUrl: 'template/basic/documentlist.htm',
			controller: 'DocumentCtrl'
		}).
		when('/groupproduct', {
			templateUrl: 'template/basic/groupproductlist.htm',
			controller: 'RocchiGroupProductCtrl'
		}).
		when('/categoryproduct', {
			templateUrl: 'template/basic/categoryproductlist.htm',
			controller: 'RocchiCategoryProductCtrl'
		}).
		when('/region', {
			templateUrl: 'template/basic/regionlist.htm',
			controller: 'RocchiRegionCtrl'
		}).
		when('/unitmeasure', {
			templateUrl: 'template/basic/unitmeasurelist.htm',
			controller: 'RocchiUnitmeasureCtrl'
		}).
		when('/company', {
			templateUrl: 'template/registry/companydetail.htm',
			controller: 'CompanyCtrl'
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
			controller: 'RocchiProductListCtrl'
		}).
		
		when('/product/:idproduct', {
			templateUrl: 'template/registry/productdetail.htm',
			controller: 'RocchiProductDetailCtrl'
		}).
		when('/list', {
			templateUrl: 'template/registry/listlist.htm',
			controller: 'RocchiListListCtrl'
		}).
		when('/list/:idlist', {
			templateUrl: 'template/registry/listdetail.htm',
			controller: 'RocchiListDetailCtrl'
		}).
		when('/customer', {
			templateUrl: 'template/registry/customerlist.htm',
			controller: 'RocchiCustomerListCtrl'
		}).
		when('/customer/:idcustomer', {
			templateUrl: 'template/registry/customerdetail.htm',
			controller: 'RocchiCustomerDetailCtrl'
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
			controller: 'RocchiTransporterListCtrl'
		}).
		when('/transporter/:idtransporter', {
			templateUrl: 'template/registry/transporterdetail.htm',
			controller: 'RocchiTransporterDetailCtrl'
		}).
		when('/headlist/:section/:type', {
			templateUrl: 'template/document/headlist.htm',
			controller: 'RocchiHeadListCtrl'
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
			templateUrl: 'template/document/wizard.html',
			controller: 'RocchiWizardCtrl'
		}).
		when('/head/:idhead/:rows', {
			templateUrl: 'template/document/headdetail.htm',
			controller: 'RocchiHeadDetailCtrl'
		}).
		when('/customercategory', {
			templateUrl: 'template/basic/customercategorylist.htm',
			controller: 'RocchiCustomerCategoryListCtrl'
		}).
		when('/customergroup', {
			templateUrl: 'template/basic/customergrouplist.htm',
			controller: 'RocchiCustomerGroupListCtrl'
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
			controller: 'RocchiBrandCtrl'
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
		when('/parameters', {
			templateUrl: 'template/parameters/parameters.htm',
			controller: 'ParametersCtrl'
		}).
		when('/rates', {
			templateUrl: 'template/parameters/rates.htm',
			controller: 'TaxrateCtrl'	
		}).
		when('/storemovement', {
			templateUrl: 'template/parameters/storemovement.htm',
			controller: 'StoreMovementCtrl'	
		}).
		when('/docpayment', {
			templateUrl: 'template/parameters/docpayment.htm',
			controller: 'PaymentCtrl'
		}).
		when('/documents', {
			templateUrl: 'template/parameters/documents.htm',
			controller: 'ParametersCtrl'	
				
		}).
		when('/promoter', {
			templateUrl: 'template/registry/promoterlist.htm',
			controller: 'RocchiPromoterListCtrl'
		}).
		when('/promoter/:idpromoter', {
			templateUrl: 'template/registry/promoterdetail.htm',
			controller: 'RocchiPromoterDetailCtrl'
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
		/*if ($rootScope.issaved == false){
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
		}*/
	});
	$rootScope.user = {};
	$rootScope.path = "";
	$rootScope.pagesize = 100;
	$rootScope.showIncrement = false;
	$rootScope.showFilter = false;
	$rootScope.deleteObj = {};
	$rootScope.issaved = true;
	$rootScope.openTab = function(url) {
    // Create link in memory
		var a = window.document.createElement("a");
		a.target = '_blank';
		a.href = url;
	 
		// Dispatch fake click
		var e = window.document.createEvent("MouseEvents");
		e.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
		a.dispatchEvent(e);
	};
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

		//ModalFactory.confirm();
	}
	$rootScope.errorMessage = function(message){
		//ModalFactory.error(message);
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
	$rootScope.viewheader = false;
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

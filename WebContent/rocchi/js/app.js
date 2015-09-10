angular.module("rocchi.customer",[]);
angular.module("rocchi.product",[]);
angular.module("rocchi.list",[]);
angular.module("rocchi.transporter",[]);
angular.module("rocchi.documents",[]);
angular.module("rocchi.parameters",[]);
angular.module("rocchi.promoter",[]);
var gecoApp = angular.module("gecoApp",
[   "ui.router",
	"gecoControllers",
	"mm.foundation",
	"smart-table",
	'ngFileUpload',
	"rocchi.customer",
	"rocchi.product",
	"rocchi.transporter",
	"rocchi.documents",
	"rocchi.parameters",
	"rocchi.promoter",
	"rocchi.list",
	'modules.common.shared',
	'ngCookies',
	"gecoBasicControllers","gecoRegistryControllers","gecoDocumentControllers","gecoStoreControllers","gecoAccountingControllers"])
.provider('AppConfig', function ()
		{
			var main_domain = "/InvoiceCreator"
		    this.$get = function ($window)
		    {
		    	return {
	            ServiceUrls: {
	            		AddRow:main_domain+ "/rest/head/addrow/",
	            		CheckUser:main_domain+ "/rest/user/loggedinuser/",
	            		CustomerCategory:main_domain+ "/rest/basic/categorycustomer",
	            		CustomerGroup:main_domain+ "/rest/basic/groupcustomer",
	            		CustomerUser:main_domain+ "/rest/registry/customer/user/",
	            		Company:main_domain+ "/rest/registry/company/",
	            		DetailsOfCustomer:main_domain+ "/rest/registry/customer/",
	            		DocumentList:main_domain+ '/rest/basic/document',
	            		DraftInit:main_domain+ '/rest/draft/init/',
	            		DraftAdd:main_domain+ '/rest/draft/addtodraft/',
	            		DraftRemove:main_domain+ '/rest/draft/removefromdraft/',
	            		DraftUpdate:main_domain+ '/rest/draft/updatedraft/',
	            		DraftConfirm:main_domain+ '/rest/draft/confirmdraft/',
	            		HeadPaging:main_domain+ '/rest/head/head',
		                ListOfCustomer:main_domain+ "/rest/registry/customer/",
		                PrintHead:main_domain+ "/rest/print/head/",
		                Product:main_domain+ "/rest/registry/product/",
		                ProductPublic:main_domain+ "/rest/registry/public/product/",
		                SaveCustomer:main_domain+ "/rest/registry/customer/",
		                ProductMainList:main_domain+ "/rest/registry/products/",
		                ProductMainPublicList:main_domain+ "/rest/registry/public/products/",
		                ProductBasicPrice:main_domain+ "/rest/util/prodbasicprice/",
		                ProductBasicPriceList:main_domain+ "/rest/util/prodbasicprice/list",
		                ProductCategory:main_domain+ "/rest/basic/categoryproduct/",
		                ProductSubCategory:main_domain+ "/rest/basic/subcategoryproduct/",
		                ProductGroup:main_domain+ "/rest/basic/groupproduct/",
		                ProductIncrement:main_domain+ "/rest/registry/product/increment/",
		                Brand:main_domain+ "/rest/basic/brand/",
		                HeadTotal:main_domain+"/rest/documenthelp/headtotal",
		                RowTotal:main_domain+"/rest/documenthelp/rowtotal",
		                HeadAllTotal:main_domain+"/rest/documenthelp/headalltotal",
		                List:main_domain+ "/rest/registry/list/",
		                ListNoProduct:main_domain+ "/rest/registry/list/noproduct/",
		                Login:main_domain+ "/rest/user/",
		                Logout:main_domain+"/rest/user/logout/",
		                SearchProduct:main_domain+ "/rest/registry/product/search/",
		                Transporter:main_domain+ "/rest/registry/transporter/",
	                    HeadList:main_domain+"/rest/head/head/",
		                DocumentDetails:main_domain+"",
		                ProductUniteMeasure:main_domain+"/rest/registry/productum/",
		                ProductList:main_domain+ "/rest/registry/productlist/",
		                UniteMeasure:main_domain+"/rest/basic/unitmeasure/",
		                UtilPricePercentage:main_domain+"/rest/util/prodbasicprice/percentage/",
		                UtilPricePrice:main_domain+"/rest/util/prodbasicprice/sellprice/",
		                UtilPriceEndPrice:main_domain+"/rest/util/prodbasicprice/endprice/",
		                Promoter:main_domain+"/rest/registry/promoter/",
		                PromoterUser:main_domain+ "/rest/registry/promoter/user",
		                ProductPagination:main_domain+"/rest/registry/product/pages/",
		                ProductListPagination:main_domain+"/rest/registry/listproduct/pages/",
		                ProductPublicPagination:main_domain+"/rest/registry/public/product/pages/",
		                Role:main_domain+"/rest/role/",
		                Region:main_domain+"/rest/basic/region/",
		                ExportHeads:main_domain+"/rest/export/heads/",
		                ImportProducts:main_domain+"/rest/import/products/",
		                ImportCustomers:main_domain+"/rest/import/customers/",
		                Upload:main_domain+"/rest/upload/file",
		                TaxRate:main_domain+"/rest/basic/taxrate/",
		                DeleteRow:main_domain+"/rest/head/removerow/",
		                Role:main_domain+"/rest/role/",
		                ListIncrement:main_domain+"/rest/util/incrementlist",
		                PaymentsList:main_domain+"/rest/basic/payment",
		                SuppliersList:main_domain+"/rest/registry/supplier",
		                SearchProductCode:main_domain+"/rest/registry/product/code/",
		                CheckHead:main_domain+"/rest/head/checkhead",
		                PrintHead:main_domain+"/rest/print/head/"
		            },Permissions:{
		            	Promoter:"promoter",
		            	Transporter:"transporter",
		            	Admin:"",
		            	Customer:"customer"
		            },Const:{
		            	ServerProblem:"Problema con la connessione al server,contattare l'amministrazione del sistema",
		            	CompanyId:"94579938-e847-46b2-9063-4692f15aa8b6"
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

gecoApp.config(['$stateProvider', '$urlRouterProvider',
function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("login");
	
	$stateProvider.state('userlist', {
			url:'/userlist',
			templateUrl: 'template/userlist.htm',
			controller: 'UserListCtrl'
		}).state('welcome', {
			url:'/welcome',
			templateUrl: 'template/home.htm',
			controller: 'WelcomeCtrl'
		}).state('userlist_details', {
			url:'/userlist/:userId',
			templateUrl: 'template/userdetail.htm',
			controller: 'UserDetailCtrl'
		}).state('role', {
			url:'/role',
			templateUrl: 'template/rolelist.htm',
			controller: 'RoleCtrl'
		}).state('taxrate', {
			url:'/taxrate',
			templateUrl: 'template/basic/taxratelist.htm',
			controller: 'RocchiTaxrateCtrl'
		}).state('counter', {
			url:'/counter',
			templateUrl: 'template/basic/counterlist.htm',
			controller: 'CounterCtrl'
		}).state('storemovement_list', {
			url:'/storemovement',
			templateUrl: 'template/basic/storemovementlist.htm',
			controller: 'StoreMovementCtrl'
		}).state('payment', {
			url:'/payment',
			templateUrl: 'template/basic/paymentlist.htm',
			controller: 'RocchiPaymentCtrl'
		}).state('document', {
			url:'/document',
			templateUrl: 'template/basic/documentlist.htm',
			controller: 'DocumentCtrl'
		}).state('groupproduct', {
			url:'/groupproduct',
			templateUrl: 'template/basic/groupproductlist.htm',
			controller: 'RocchiGroupProductCtrl'
		}).state('categoryproduct', {
			url:'/categoryproduct',
			templateUrl: 'template/basic/categoryproductlist.htm',
			controller: 'RocchiCategoryProductCtrl'
		}).state('region', {
			url:'/region',
			templateUrl: 'template/basic/regionlist.htm',
			controller: 'RocchiRegionCtrl'
		}).state('unitmeasure', {
			url:'/unitmeasure',
			templateUrl: 'template/basic/unitmeasurelist.htm',
			controller: 'RocchiUnitmeasureCtrl'
		}).state('company', {
			url:'/company',
			templateUrl: 'template/registry/companydetail.htm',
			controller: 'CompanyCtrl'
		}).state('bank', {
			url:'/bank',
			templateUrl: 'template/registry/banklist.htm',
			controller: 'BankListCtrl'
		}).state('bank_details', {
			url:'/bank/:idbank',
			templateUrl: 'template/registry/bankdetail.htm',
			controller: 'BankDetailCtrl'
		}).state('product', {
			url:'/product',
			templateUrl: 'template/registry/productlist.htm',
			controller: 'RocchiProductListCtrl'
		}).state('product_details', {
			url:'/product/:idproduct',
			templateUrl: 'template/registry/productdetail.htm',
			controller: 'RocchiProductDetailCtrl'
		}).state('list', {
			url:'/list',
			templateUrl: 'template/registry/listlist.htm',
			controller: 'RocchiListListCtrl'
		}).state('list_details', {
			url:'/list/:idlist',
			templateUrl: 'template/registry/listdetail.htm',
			controller: 'RocchiListDetailCtrl'
		}).state('customer', {
			url:'/customer',
			templateUrl: 'template/registry/customerlist.htm',
			controller: 'RocchiCustomerListCtrl'
		}).state('customer_details', {
			url:'/customer/:idcustomer',
			templateUrl: 'template/registry/customerdetail.htm',
			controller: 'RocchiCustomerDetailCtrl'
		}).state('destination', {
			url:'/destination',
			templateUrl: 'template/registry/destinationlist.htm',
			controller: 'DestinationListCtrl'
		}).state('destination_details', {
			url:'/destination/:iddestination',
			templateUrl: 'template/registry/destinationdetail.htm',
			controller: 'DestinationDetailCtrl'
		}).state('supplier', {
			url:'/supplier',
			templateUrl: 'template/registry/supplierlist.htm',
			controller: 'SupplierListCtrl'
		}).state('supplier_details', {
			url:'/supplier/:idsupplier',
			templateUrl: 'template/registry/supplierdetail.htm',
			controller: 'SupplierDetailCtrl'
		}).state('transporter', {
			url:'/transporter',
			templateUrl: 'template/registry/transporterlist.htm',
			controller: 'RocchiTransporterListCtrl'
		}).state('transporter_details', {
			url:'/transporter/:idtransporter',
			templateUrl: 'template/registry/transporterdetail.htm',
			controller: 'RocchiTransporterDetailCtrl'
		}).state('headlist_details_type', {
			url:'/headlist/:section/:type',
			templateUrl: 'template/document/headlist.htm',
			controller: 'RocchiHeadListCtrl'
		}).state('generatedocs', {
			url:'/generatedocs',
			templateUrl: 'template/document/generatedocs.htm',
			controller: 'GenerateDocsCtrl'
		}).state('copyrows', {
			url:'/copyrows',
			templateUrl: 'template/document/copyrows.htm',
			controller: 'CopyRowsCtrl'
		}).state('reportorder', {
			url:'/reportorder',
			templateUrl: 'template/store/reportorder.htm',
			controller: 'ReportOrderCtrl'
		}).state('createorders', {
			url:'/createorders',
			templateUrl: 'template/document/openorders.htm',
			controller: 'CreateOrdersCtrl'
		}).state('head_details', {
			url:'/head/:idhead',
			templateUrl: 'template/document/headdetail.html',
			controller: 'RocchiHeadDetailCtrl'
		}).state('head_details_wizard', {
			url:'/head_wizard/:idhead',
			templateUrl: 'template/document/wizard.html',
			controller: 'RocchiWizardCtrl'
		}).state('head_details_rows', {
			url:'/head/:idhead/:rows',
			templateUrl: 'template/document/headdetail.htm',
			controller: 'RocchiHeadDetailCtrl'
		}).state('customercategory', {
			url:'/customercategory',
			templateUrl: 'template/basic/customercategorylist.htm',
			controller: 'RocchiCustomerCategoryListCtrl'
		}).state('customergroup', {
			url:'/customergroup',
			templateUrl: 'template/basic/customergrouplist.htm',
			controller: 'RocchiCustomerGroupListCtrl'
		}).state('suppliercategory', {
			url:'/suppliercategory',
			templateUrl: 'template/basic/suppliercategorylist.htm',
			controller: 'SupplierCategoryListCtrl'
		}).state('suppliergroup', {
			url:'/suppliergroup',
			templateUrl: 'template/basic/suppliergrouplist.htm',
			controller: 'SupplierGroupListCtrl'
		}).state('myprofile_details', {
			url:'/myprofile/:myuserId',
			templateUrl: 'template/myprofile.htm',
			controller: 'MyProfileCtrl'
		}).state('login', {
			url:'/login',
			templateUrl: 'template/welcome.htm',
			controller:'LoginCtrl'
		}).state('ec', {
			url:'/ec',
			templateUrl: 'template/ecommerce.htm',
			controller:'ECommerceCtrl'
		}).state('storelist', {
			url:'/storelist',
			templateUrl: 'template/store/productstorage.htm',
			controller: 'StoreCtrl'
		}).state('brand', {
			url:'/brand',
			templateUrl: 'template/basic/brandlist.htm',
			controller: 'RocchiBrandCtrl'
		}).state('storeneeded', {
			url:'/storeneeded',
			templateUrl: 'template/store/storeneeded.htm',
			controller: 'StoreNeededCtrl'
		}).state('accounting_details', {
			url:'/accounting/:type',
			templateUrl: 'template/accounting/accountingcustomer.htm',
			controller: 'AccountingCustomerCtrl'
		}).state('accountinglist_details', {
			url:'/accountinglist/:type',
			templateUrl: 'template/accounting/accountinglist.htm',
			controller: 'AccountingListCtrl'
		}).state('parameters', {
			url:'/parameters',
			templateUrl: 'template/parameters/parameters.htm',
			controller: 'ParametersCtrl'
		}).state('rates', {
			url:'/rates',
			templateUrl: 'template/parameters/rates.htm',
			controller: 'TaxrateCtrl'	
		}).state('storemovement', {
			url:'/storemovement',
			templateUrl: 'template/parameters/storemovement.htm',
			controller: 'StoreMovementCtrl'	
		}).state('docpayment', {
			url:'/docpayment',
			templateUrl: 'template/parameters/docpayment.htm',
			controller: 'PaymentCtrl'
		}).state('documents', {
			url:'documents',
			templateUrl: 'template/parameters/documents.htm',
			controller: 'ParametersCtrl'	
				
		}).state('promoter', {
			url:'/promoter',
			templateUrl: 'template/registry/promoterlist.htm',
			controller: 'RocchiPromoterListCtrl'
		}).state('promoter_details', {
			url:'/promoter/:idpromoter',
			templateUrl: 'template/registry/promoterdetail.htm',
			controller: 'RocchiPromoterDetailCtrl'
		});
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

/**
 * 
 */
angular.module("rocchi.customer")
.controller('RocchiCustomerListCtrl',["$scope","$http","$rootScope","$location","AppConfig","AlertsFactory","LoaderFactory",function($scope, $http, $rootScope, $location,AppConfig,AlertsFactory,LoaderFactory) {
	// $scope.loginuser =
	// GECO_LOGGEDUSER.checkloginuser();
	$scope.location = $location;
	if ($scope.pagesize == null) {
		$scope.pagesize = 99;
		
	} else {
		// $scope.pagesize =
		// ScopeFactory.factory.productList.pagesize
	}
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	$scope.pageArray = [];
	$scope.showuser = false;
	$scope.hasuser = false;
	$scope.importobj = {};
	$scope.url = AppConfig.ServiceUrls;
	$scope.importobj = {colCode:"B",colName:"C",colAlternativecode1:"A",colAlternativecode2:"B",colTaxcode:"G",colSerialnumber:"H",colList:"Q",colPromoter:"T",colCommission:"W",colEmail:"BG",colPhone:"M",colMobile:"AC",colStreet:"D",colZipcode:"AD",colCity:"E",colZone:"F",startIndex:"2",endIndex:"5"};
	$scope.filterMenu = function(value){
		if ($scope.menuselected == "" || $scope.menuselected != value){
			$scope.menuselected = value;
		}else{
			$scope.menuselected = "";
		}
	}
	if ($scope.showFilter == null) {
		$scope.showFilter = false;
	}
	if ($scope.filterCustomer == null) {
		$scope.filterCustomer = {
			"pagefilter" : {}
		};
		$scope.currentCustomerGroup = {};
		$scope.currentBrand = {};
		$scope.currentCategory = {};
		$scope.currentSubCategory = {};
		$scope.currentSupplier = {};
	} else {
		$scope.currentCustomerGroup = $scope.filterCustomer.group;
		$scope.currentBrand = $scope.filterCustomer.brand;
		$scope.currentCategory = $scope.filterCustomer.category;
		$scope.currentSubCategory = $scope.filterCustomer.subcategory;
		$scope.currentSupplier = $scope.filterCustomer.supplier;
	}
	$http.get(AppConfig.ServiceUrls.CustomerGroup).success(
					function(data) {
						$scope.groups = data;
						if ($scope.currentGroup != null) {
							for (var i = 0; i < $scope.groups.length; i++) {
								if ($scope.currentCustomerGroup.idGroupCustomer == $scope.groups[i].idGroupCustomer) {
									$scope.currentCustomerGroup = $scope.groups[i];
								}
							}
						}
					});
	$http.get('rest/basic/categorycustomer')
			.success(
					function(data) {
						$scope.categorys = data;
						if ($scope.currentCategory != null) {
							for (var i = 0; i < $scope.categorys.length; i++) {
								if ($scope.currentCategory.idCategoryCustomer == $scope.categorys[i].idCategoryCustomer) {
									$scope.currentCategory = $scope.categorys[i];
								}
							}
						}
					});
	$scope.getCustomers = function() {
		$scope.filterCustomer.pagefilter.startelement = (1 - 1)	* $scope.pagesize;
		$scope.filterCustomer.pagefilter.pageSize = $scope.pagesize;
		$rootScope.filterCustomer = $scope.filterCustomer
		$.ajax({
					url : AppConfig.ServiceUrls.ListOfCustomer,
					type : "POST",
					data : "filter="
							+ JSON
									.stringify($scope.filterCustomer),
					success : function(data) {

						$scope.customers = JSON
								.parse(data);

						$scope.$apply();
					}
				})
	}
	$scope.getCustomers();

	$scope.deleteElement = function(id) {
		for (var i = 0; i < $scope.customers.length; i++) {
			if (id == $scope.customers[i].idCustomer) {
				$scope.deletecustomer = $scope.customers[i];
				$.ajax({
							url : "rest/registry/customer/",
							type : "DELETE",
							data : "customerobj="
									+ JSON
											.stringify($scope.deletecustomer),
							success : function(data) {
								alert("Utente eliminato con successo");
								$http
										.get(
												'rest/registry/customer')
										.success(
												function(
														data) {
													$scope.customers = data;
												});
							}
						})
			}
		}
	}
	$scope.printElements = function() {
		$http.get('rest/print').success(function(data) {
			window.open(data, '_blank');
		});
	}
	$scope.onComplete = function (response) {
		if (response.data.type == "success"){
			$scope.importCustomer(response.data.success)
		}else{
			$scope.msg.alertMessage(response.data.errorMessage);
		}
	};
	$scope.importCustomer = function(filename){
		$scope.importobj.lists = [];
		$scope.importobj.filename = filename;
		$scope.infos = [];
		LoaderFactory.loader = true;
		$http.post(AppConfig.ServiceUrls.ImportCustomers,$scope.importobj).then(function(result){
			if (result.data.type == "success"){
				$scope.msg.infoMessage("IMPORTAZIONE TERMINATA CON SUCCESSO");
				$scope.infos = result.data.success;
				$scope.importview = false;
				$scope.novalid = true;
				$scope.getCustomers();
				LoaderFactory.loader = false;
			}else{
				LoaderFactory.loader = false;
				$scope.msg.alertMessage(result.data.errorMessage);
			}
			
		},function(result){
			LoaderFactory.loader = false;
			});
	};
	
} ])
.controller('RocchiCustomerDetailCtrl',["$scope","$http","$stateParams","AppConfig","AlertsFactory","LoaderFactory",function($scope, $http, $stateParams, AppConfig,AlertsFactory,LoaderFactory) {
							// GECO_LOGGEDUSER.checkloginuser();

$scope.msg = AlertsFactory;
$scope.msg.initialize();
GECO_validator.startupvalidator();
$scope.idcustomer = $stateParams.idcustomer;

$scope.showuser = false;
/*
 * $http.get('rest/registry/list').success(function(data){
 * $scope.lists= data; });
 * $http.get('rest/basic/payment').success(function(data){
 * $scope.payments= data; if ($scope.customer !=
 * null && $scope.customer.payment != null){ for(var
 * i=0;i<$scope.payments.length;i++ ){ if
 * ($scope.customer.payment.idPayment ==
 * $scope.payments[i].idPayment){
 * $scope.currentPayment = $scope.payments[i]; } } }
 * });
 */ $http.get(AppConfig.ServiceUrls.CustomerCategory).success(function(data){
  $scope.categorys= data; 
  });
 
$http.get(AppConfig.ServiceUrls.CustomerGroup).success(function(data) {
			$scope.groups = data;
		});
$http.get(AppConfig.ServiceUrls.Promoter).success(function(data) {
	$scope.promoters = data;
});
$http.get(AppConfig.ServiceUrls.List).success(function(data) {
	$scope.lists = data;
	fillList();
});
var fillList = function(){
	if ($scope.lists && $scope.customer ) {
		for (var i = 0; i < $scope.lists.length; i++) {
			if ($scope.customer.lists) {
				for(var y =0;y<$scope.customer.lists.length;y++){
					if ($scope.customer.lists[y].list.idList == $scope.lists[i].idList){
						$scope.currentList = $scope.lists[i];
					}
				}
			} else {
				$scope.customer.lists = [{idListCustomer:0,list:{}}];
			}
		}
	}
}
$http.get(AppConfig.ServiceUrls.DetailsOfCustomer+ $scope.idcustomer).success(function(data) {
			$scope.customer = data;
			if ($scope.customer.contact){
				if ($scope.customer.contact.user){

					$scope.hasuser = true;
				}
			}
			if ($scope.groups !== null
					&& $scope.groups !== undefined) {
				for (var i = 0; i < $scope.groups.length; i++) {
					if ($scope.customer.group != null) {
						if ($scope.customer.group.idGroupCustomer == $scope.groups[i].idGroupCustomer) {
							$scope.currentGroup = $scope.groups[i];
						}
					} else {
						$scope.customer.group = {}
					}
				}
			}
			if ($scope.categorys !== null
					&& $scope.categorys !== undefined) {
				for (var ig = 0; ig < $scope.categorys.length; ig++) {
					if ($scope.customer.category != null) {
						if ($scope.customer.category.idCategoryCustomer == $scope.categorys[ig].idCategoryCustomer) {
							$scope.currentCategory = $scope.categorys[ig];
						}
					} else {
						$scope.customer.category = {};
					}
				}
			}
			if ($scope.promoters !== null
					&& $scope.promoters !== undefined) {
				for (var ip = 0; ip < $scope.promoters.length; ip++) {
					if ($scope.customer.promoter != null) {
						if ($scope.customer.promoter.idPromoter == $scope.promoters[ip].idPromoter) {
							$scope.currentPromoter = $scope.promoters[ip];
						}
					} else {
						$scope.customer.promoter = {};
					}
				}
			}
			fillList();
		});
			$scope.getListName = function(list) {
				return list.code + ' ' + list.description + ' '
				+ list.startdate;
	}
	$scope.addListElement = function(customer) {
		customer.lists.push({
			idListCustomer : 0
		});
	}
	$scope.changeListElement = function(list) {

		if (list.list != null) {
			for (var i = 0; i < $scope.lists.length; i++) {
				if (list.list.idList == $scope.lists[i].idList) {
					$scope.currentList = $scope.lists[i];
				}
			}
		}
		$scope.listid = list.idListCustomer
	}
	$scope.saveCustomer = function() {
		$scope.customer.group = $scope.currentGroup;
		$scope.customer.category = $scope.currentCategory;
		$scope.customer.taxrate = $scope.currentTaxRate;
		$scope.customer.promoter = $scope.currentPromoter;
		if ($scope.customer.lists && $scope.customer.lists.length > 0 ){
			$scope.customer.lists[0].list = $scope.currentList;
		}else{
			$scope.customer.lists = [{idListCustomer:0,list:$scope.currentList}];
		}
		$scope.msg.initialize();
		$.ajax({
					url : AppConfig.ServiceUrls.SaveCustomer,
					type : "PUT",
					data : "customers="
							+ JSON
									.stringify($scope.customer),
					success : function(data) {
						result = JSON.parse(data);
						if (result.type == "success") {
							$scope.customer.idCustomer = result.success;
							$scope.idcustomer = result.success;
							$scope.msg.successMessage("CLIENTE SALVATO CON SUCCESSO");
							$scope.$apply();
						} else {
							$scope.msg
									.alertMessage(result.errorMessage);
						}

					},
					error : function(data) {
						$scope.msg.alertMessage(data);
					}
				})
		// }
	};
	$scope.userCustomer = function() {
		var obj = {};
		obj.customer = $scope.customer;
		obj.role = $scope.currentRole;
		LoaderFactory.loader = true;
		$http.put(AppConfig.ServiceUrls.CustomerUser,obj).then(function(results){
			LoaderFactory.loader = false;
			if (results.data.type == "success"){
				$scope.showuser = false;
				$scope.customer.hasuser = true;
				LoaderFactory.loader = false;
				$scope.msg.successMessage("UTENTE CREATO CON SUCCESSO");
			}else{
				$scope.msg.alertMessage(results.data.errorMessage);
			}
			
		},function(error){
			$scope.msg.alertMessage(error);
		});
		/*$.ajax({
					url : "rest/registry/customer/user",
					type : "PUT",
					data : "customers="
							+ JSON
									.stringify($scope.customer)
							+ "&role="
							+ JSON
									.stringify($scope.currentRole),
					success : function(data) {
						result = JSON.parse(data);
						if (result.type == "success") {
							$scope.customer.idCustomer = result.success;
							$scope.idcustomer = result.success;
							$scope.confirmSaved();
							$scope.$apply();
						} else {
							alert("Errore: "
									+ result.errorName
									+ " Messaggio:"
									+ result.errorMessage);
						}
					}
				})*/
		// }
	};
	$http.get(AppConfig.ServiceUrls.Role).success(function(data) {
		$scope.roles = data;
	});
} ]);

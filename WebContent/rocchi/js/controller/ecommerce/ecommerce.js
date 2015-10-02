angular.module("geco.ecommerce",[])
.controller('ECommerceCtrl',function($scope,$http,$rootScope,$location,PermissionFactory,AlertsFactory,AppConfig,$cookies,FormatFactory,DraftFactory,LoaderFactory){
	$rootScope.viewheader = false;
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	$scope.step = 0;
	$scope.formatCurrency = FormatFactory.formatCurrency,
	$scope.perm = PermissionFactory;
	$scope.draftFactory = DraftFactory;
	$scope.paymentValue = {};
	$scope.paymentValue.code = "";
	$scope.prodlist = [];
	$scope.draft={};
	$scope.selectedDestination ={};
	$scope.Range = function(start, end) {
	    var result = [];
	    for (var i = start; i <= end; i++) {
	        result.push(i);
	    }
	    return result;
	};
	var controlAddress = function(){
		if (!$scope.draftaddress){
			$scope.draftaddress = {};
		}
		if (!$scope.draftaddress.countryObj){
			$scope.draftaddress.countryObj = null;
		}else{
			if ( $scope.draftaddress.countryObj.idCountry == null ||  $scope.draftaddress.countryObj.idCountry  == undefined ){
				var cn = angular.copy($scope.draftaddress.countryObj);
				$scope.draftaddress.countryObj = {};
				$scope.draftaddress.countryObj.name = cn;
				$scope.draftaddress.country = cn;
				$scope.draftaddress.countryObj.idCountry = 0;
			}
		}
		
		if (!$scope.draftaddress.zoneObj){
			$scope.draftaddress.zoneObj = null;
		}else{
			if ( $scope.draftaddress.zoneObj.idZone == null ||  $scope.draftaddress.zoneObj.idZone  == undefined  ){
				var cnz = angular.copy($scope.draftaddress.zoneObj);
				$scope.draftaddress.zoneObj = {};
				$scope.draftaddress.zoneObj.name = cnz;
				$scope.draftaddress.zone = cnz;
				$scope.draftaddress.zoneObj.idZone = 0;
			}
		}
		
		if (!$scope.draftaddress.cityObj){
			$scope.draftaddress.cityObj = null;
		}else{
			if ( $scope.draftaddress.cityObj.idCity == null ||  $scope.draftaddress.cityObj.idCity  == undefined ){
				var cnc = angular.copy($scope.draftaddress.cityObj);
				$scope.draftaddress.cityObj = {};
				$scope.draftaddress.cityObj.name = cnc;
				$scope.draftaddress.city = cnc;
				$scope.draftaddress.cityObj.idCity = 0;
			}
		}
		
	}
	var intitialize = function(){
		
		$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
			$scope.categories= data;
		});
		$http.get(AppConfig.ServiceUrls.ProductGroup).success(function(data){
			$scope.groups= data;
		});
		$http.get(AppConfig.ServiceUrls.Brand).success(function(data){
			$scope.brands= data;
		});
		$http.get(AppConfig.ServiceUrls.Region).success(function(data){
			$scope.regions= data;
		});
		
		$http.get(AppConfig.ServiceUrls.Country).success(function(result){
			$scope.countries = result;
			
		});
		
		
	};
	var calculateDraftCosts = function(){
		controlAddress();
		LoaderFactory.loader = true;
		$http.post(AppConfig.ServiceUrls.DeliveryCost+AppConfig.Const.CompanyId,$scope.draftaddress).success(function(result){
			LoaderFactory.loader = false;
			if (result.type == "success"){
				$scope.draft.deliverycost  = result.success;
				$scope.refreshDraft();
			}else{
				LoaderFactory.loader = false;
			}
		}).error(function(error){
			LoaderFactory.loader = false;
		});
	}
	$scope.refreshDraft = function(){
		$http.post(AppConfig.ServiceUrls.DraftRefresh,$scope.draft).success(function(result){
			if (result.type == "success"){
				$scope.draft  = result.success;
				LoaderFactory.loader = false;
			}else{
				LoaderFactory.loader = false;
			}
		}).error(function(error){
			LoaderFactory.loader = false;
		});
	}
	$scope.subcategories = [];
	$scope.changeCategory = function(){
		$scope.subcategories = [];
		$scope.subcategories = $scope.filter.category.subcategories;
	}
	intitialize();
	$scope.onSelect = function ($item, $model, $label) {
	    $scope.draftaddress.countryObj = $model;
	    $scope.draftaddress.country = $label;
	    $http.post(AppConfig.ServiceUrls.DeliveryCost+AppConfig.Const.CompanyId,$scope.draftaddress).success(function(result){
			if (result.type == "success"){
				$scope.draft.deliverycost  = result.success;
				$scope.refreshDraft();
			}else{
				LoaderFactory.loader = false;
			}
			$http.get(AppConfig.ServiceUrls.Zone+$scope.draftaddress.countryObj.idCountry).success(function(result){
				$scope.zones = result;
			});
			$http.get(AppConfig.ServiceUrls.CityCountry+$scope.draftaddress.countryObj.idCountry).success(function(result){
				$scope.cities = result;
			});
		}).error(function(error){
			LoaderFactory.loader = false;
		});
	};
	$scope.onSelectZone = function ($item, $model, $label) {
		$scope.draftaddress.zoneObj = $model;
		$scope.draftaddress.zone = $label;
		controlAddress();
		$http.post(AppConfig.ServiceUrls.DeliveryCost+AppConfig.Const.CompanyId,$scope.draftaddress).success(function(result){
			if (result.type == "success"){
				$scope.draft.deliverycost  = result.success;
				$scope.refreshDraft();
			}
			
			$http.get(AppConfig.ServiceUrls.CityZone+$scope.draftaddress.zoneObj.idZone).success(function(result){
				$scope.cities = result;
			});
		}).error(function(error){
			LoaderFactory.loader = false;
		});
	};
	//TODO eliminare gli oggetti
	
	$scope.calculateTransportPrice = function(){
		
		controlAddress();
		$scope.zones = [];
		$scope.cities = [];
		$scope.draftaddress.zoneObj = null;
		$scope.draftaddress.cityObj = null;
		$http.post(AppConfig.ServiceUrls.DeliveryCost+AppConfig.Const.CompanyId,$scope.draftaddress).success(function(result){
			if (result.type == "success"){
				$scope.draft.deliverycost  = result.success;
				$scope.refreshDraft();
			}
		
		}).error(function(error){
			LoaderFactory.loader = false;
		});
	};
	$scope.onSelectCity = function ($item, $model, $label) {
		$scope.draftaddress.cityObj = $model;
		$scope.draftaddress.city = $label;
		controlAddress();
		$http.post(AppConfig.ServiceUrls.DeliveryCost+AppConfig.Const.CompanyId,$scope.draftaddress).success(function(result){
			if (result.type == "success"){
				$scope.draft.deliverycost  = result.success;
				$scope.refreshDraft();
			}
		}).error(function(error){
			LoaderFactory.loader = false;
		});
	};
	
	$scope.zoneChange = function(){
		controlAddress();
		$scope.draftaddress.cityObj = null;
		if ($scope.draftaddress.countryObj.idCountry){
			$http.post(AppConfig.ServiceUrls.DeliveryCost+AppConfig.Const.CompanyId,$scope.draftaddress).success(function(result){
				if (result.type == "success"){
					$scope.draft.deliverycost  = result.success;
					$scope.refreshDraft();
				}
				$http.get(AppConfig.ServiceUrls.Zone+$scope.draftaddress.countryObj.idCountry).success(function(result){
					$scope.zones = result;
				});
				$http.get(AppConfig.ServiceUrls.CityCountry+$scope.draftaddress.countryObj.idCountry).success(function(result){
					$scope.cities = result;
				});
			}).error(function(error){
				LoaderFactory.loader = false;
			});
		}else{
			$scope.cities = [];
		}
	}
	$scope.cityChange = function(){
		controlAddress();
		if ($scope.draftaddress.countryObj.idCountry){
			$http.post(AppConfig.ServiceUrls.DeliveryCost+AppConfig.Const.CompanyId,$scope.draftaddress).success(function(result){
				if (result.type == "success"){
					$scope.draft.deliverycost  = result.success;
					$scope.refreshDraft();
				}
				
			}).error(function(error){
				LoaderFactory.loader = false;
			});
		}
	}
	$http.get(AppConfig.ServiceUrls.Company+AppConfig.Const.CompanyId).success(function(result){
		DraftFactory.company = result;
		DraftFactory.payments = DraftFactory.company.ecpayments;
		$scope.ecpayments = DraftFactory.checkPayments(PermissionFactory.user);
		LoaderFactory.loader = false;
	}).error(function(error){
		LoaderFactory.loader = false;
	});
	if (!navigator.cookieEnabled){
		 alert("Attenzione i cookie non sono abilitati...alcune informazioni potrebbero essere perse");
		 $http.post(AppConfig.ServiceUrls.DraftInit+AppConfig.Const.CompanyId,"").success(function(result){
			if(result.type == "success"){
				$scope.draft = result.success
				calculateDraftCosts();
				$scope.getProductsNumber();
			}
		 }).error(function(error){
				LoaderFactory.loader = false;
			});
	}else{
		var cookie = $cookies.get("PRODraft");
		$http.post(AppConfig.ServiceUrls.DraftInit+AppConfig.Const.CompanyId,cookie).success(function(result){
			if(result.type == "success"){
				$scope.draft = result.success
				$cookies.put("PRODraft",$scope.draft.id);
				calculateDraftCosts();
				$scope.getProductsNumber();
			}
		});
	}
	$scope.setDestination = function(dest){
		$scope.draftaddress = dest.address;
		
		calculateDraftCosts();
	};
	$scope.newDeliveryAddress = function(){
		$scope.draftaddress = {};
		$scope.selectedDestination = null;
		calculateDraftCosts();
	}
	$scope.loginfunction = function(){
		$http.post(AppConfig.ServiceUrls.LoginEc,$scope.login).success(function(result){
			
			if (result.type == 'success'){
				result = result.success;
				$(".myprofilelabel").html(result.username);
					PermissionFactory.setupPermission(result.path);
					PermissionFactory.user = angular.copy(result);
					DraftFactory.user = angular.copy(PermissionFactory.user);
					$scope.msg.initialize();
					$scope.ecpayments = DraftFactory.checkPayments(PermissionFactory.user);
					$http.get(AppConfig.ServiceUrls.ListOfCustomerDestinations+DraftFactory.user.entity.idCustomer).success(function(result){
						DraftFactory.user.destinations = result;
						if (DraftFactory.user.destinations && DraftFactory.user.destinations.length > 0){
							for(var i = 0;i<DraftFactory.user.destinations.length;i++){
								if(i==0){
									$scope.selectedDestination = DraftFactory.user.destinations[i];
									$scope.setDestination($scope.selectedDestination);
								}
							}
						}else{
							$scope.selectedDestination = {};
						}
					}).error(function(error){
						$scope.msg.alertMessage(error);
					})
						
					
			}else{
				$scope.msg.alertMessage(result.errorMessage);
			}
		}).error(function(error){
			$scope.msg.alertMessage(AppConfig.Const.ServerProblem)
		});
		
	};
	$scope.logout = function(){
		$http.get(AppConfig.ServiceUrls.Logout).then(
				function(result){
					$rootScope.viewheader = false;
					PermissionFactory.user = null;
					DraftFactory.user = null;
				}
		);
		
	}
	$scope.addToChart = function(product){
		$http.post(AppConfig.ServiceUrls.DraftAdd+$scope.draft.id+"/"+AppConfig.Const.CompanyId,product).success(function(result){
			if(result.type == "success"){
				$scope.draft = result.success;
				product.added= true;
			}
		});
	}
	$scope.updateChart = function(product,force){
		if (product.added == true || force){
			$http.post(AppConfig.ServiceUrls.DraftUpdate+$scope.draft.id,product).success(function(result){
				if(result.type == "success"){
					$scope.draft = result.success;
					
				}
			});
		}
		
	}
	$scope.confirmChart = function(user){
		DraftFactory.user.entity = null;
		LoaderFactory.loader = true;
		$http.post(AppConfig.ServiceUrls.DraftConfirm+$scope.draft.id+"/"+$scope.paymentValue.code,DraftFactory.user).success(function(result){
			if(result.type == "success"){
				switch(result.success){
					case "confirmed":
						$scope.step = 3;
						break;
					case "paypal":
						//$scope.userNew = PermissionFactory.user;
						$("#paypalform").submit();
						break;
				}
				
				
			}
			LoaderFactory.loader = false;
		}).error(function(error){
			$scope.msg.alertMessage(AppConfig.Const.ServerProblem)
		});
		
		
	}
	$scope.removeChart = function(product){
		
			$http.post(AppConfig.ServiceUrls.DraftRemove+$scope.draft.id+"/"+AppConfig.Const.CompanyId,product).success(function(result){
				if(result.type == "success"){
					$scope.draft = result.success;
					resetRemovedElement(product)
				}
			});
		
		
	}
	var resetRemovedElement = function(element){
		angular.forEach($scope.prodlist,function(value){
			if (value.product.idProduct == element.product.idProduct){
				value.added = false;
			}
		});
	}
	
	
	if ($scope.filter == null){
		$scope.filter = {"pagefilter":{}};
		$scope.currentGroup = {};
		$scope.currentBrand = {};
		$scope.currentCategory = {};
		$scope.currentSubCategory = {};
		$scope.currentSupplier = {};
	}else{
		$scope.currentGroup = $scope.filter.group;
		$scope.currentBrand = $scope.filter.brand;
		$scope.currentCategory = $scope.filter.category;
		$scope.currentSubCategory = $scope.filter.subcategory;
		$scope.currentSupplier = $scope.filter.supplier;
	}
	$scope.getProducts = function(page){
		$scope.filter.pagefilter.startelement = (page - 1 ) * $scope.pagesize_confirmed;
		$scope.filter.pagefilter.pageSize = $scope.pagesize_confirmed;
		$scope.filter = $scope.filter
		$http.post(AppConfig.ServiceUrls.ProductMainPublicList+AppConfig.Const.CompanyId,$scope.filter).then(function(result){
			$scope.prodlist = result.data;
			angular.forEach($scope.draft.products,function(item){
				$scope.tempDraft = item;
				angular.forEach($scope.prodlist,function(elem){
					if (elem.product.idProduct == $scope.tempDraft.product.idProduct){
						elem.quantity = $scope.tempDraft.quantity;
						elem.added = true;
					}
				});
			});
		})
		
}
	$scope.pagesize = 6;
$scope.getProductsNumber = function(){
	
	if ($scope.prodlist.length != $scope.pagesize){
	$scope.pages = [];
	$scope.totalitems = 0;
	$scope.pageArray = [];
		$http.get(AppConfig.ServiceUrls.ProductPublicPagination+$scope.pagesize+"/"+AppConfig.Const.CompanyId).then(function(result){
			$scope.pages = result.data.pages;
			$scope.totalitems = result.data.totalitems;
			$scope.pagesize_confirmed = $scope.pagesize;
			$scope.getProducts(1);
			LoaderFactory.loader = false;
		});
		
	}else{
		$scope.getProducts(1);
		LoaderFactory.loader = false;
	}
}
	$scope.gotoPay = function(){
		$scope.step = 2;
		DraftFactory.user.deliveryaddress =$scope.draftaddress;
	}
	$scope.commissionCalculation = function(ecp){
		$scope.draft.commission = ecp.ecpayment.commission;
		$scope.refreshDraft();
	}
	$scope.signup = function(){
		$location.path("/newecuser");
	}
	$scope.recoverUsername = function(){
		LoaderFactory.loader = true;
		$http.post(AppConfig.ServiceUrls.EcRecoverUID+AppConfig.Const.CompanyId,$scope.recoverUE).success(function(result){
			if(result.type == "success"){
				$scope.msg.successMessage("Una mail è stata inviata al suo indirizzo con lo username recuperato");
			}else{
				$scope.msg.alertMessage(result.errorMessage);
			}
			LoaderFactory.loader = false;
		}).error(function(error){
			LoaderFactory.loader = false;
			$scope.msg.alertMessage(error);
		});
	}
	$scope.recoverPassword = function(){
		LoaderFactory.loader = true;
		$http.post(AppConfig.ServiceUrls.EcAskRecoverPWD+AppConfig.Const.CompanyId,$scope.recoverPW).success(function(result){
			if(result.type == "success"){
				$scope.msg.successMessage("Una mail è stata inviata al suo indirizzo con le istruzioni per il recupero password");
			}else{
				$scope.msg.alertMessage(result.errorMessage);
			}
			LoaderFactory.loader = false;
		}).error(function(error){
			LoaderFactory.loader = false;
			$scope.msg.alertMessage(error);
		});
	}
}).controller('ECommerceUserCtrl',function($scope,$http,$rootScope,$location,AlertsFactory,AppConfig){
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	var intitialize = function(){
		$http.get(AppConfig.ServiceUrls.Country).success(function(result){
				$scope.countries = result;
		});
	};
	intitialize();
	var controlAddress = function(){
		if (!$scope.draftaddress){
			$scope.draftaddress = {};
		}
		if (!$scope.draftaddress.countryObj){
			$scope.draftaddress.countryObj = null;
		}else{
			if ( $scope.draftaddress.countryObj.idCountry == null ||  $scope.draftaddress.countryObj.idCountry  == undefined ){
				var cn = angular.copy($scope.draftaddress.countryObj);
				$scope.draftaddress.countryObj = {};
				$scope.draftaddress.countryObj.name = cn;
				$scope.draftaddress.country = cn;
				$scope.draftaddress.countryObj.idCountry = 0;
			}
		}
		if (!$scope.draftaddress.zoneObj){
			$scope.draftaddress.zoneObj = null;
		}else{
			if ( $scope.draftaddress.zoneObj.idZone == null ||  $scope.draftaddress.zoneObj.idZone  == undefined  ){
				var cnz = angular.copy($scope.draftaddress.zoneObj);
				$scope.draftaddress.zoneObj = {};
				$scope.draftaddress.zoneObj.name = cnz;
				$scope.draftaddress.zone = cnz;
				$scope.draftaddress.zoneObj.idZone = 0;
			}
		}
		
		if (!$scope.draftaddress.cityObj){
			$scope.draftaddress.cityObj = null;
		}else{
			if ( $scope.draftaddress.cityObj.idCity == null ||  $scope.draftaddress.cityObj.idCity  == undefined ){
				var cnc = angular.copy($scope.draftaddress.cityObj);
				$scope.draftaddress.cityObj = {};
				$scope.draftaddress.cityObj.name = cnc;
				$scope.draftaddress.city = cnc;
				$scope.draftaddress.cityObj.idCity = 0;
			}
		}
		
	}
	$scope.onSelect = function ($item, $model, $label) {
	    $scope.newUser.deliveryaddress.countryObj = $model;
	    $scope.newUser.deliveryaddress.country = $label;

			$http.get(AppConfig.ServiceUrls.Zone+$scope.newUser.deliveryaddress.countryObj.idCountry).success(function(result){
				$scope.zones = result;
			});
			$http.get(AppConfig.ServiceUrls.CityCountry+$scope.newUser.deliveryaddress.countryObj.idCountry).success(function(result){
				$scope.cities = result;
			});
		
	};
	$scope.onSelectZone = function ($item, $model, $label) {
		$scope.newUser.deliveryaddress.zoneObj = $model;
		$scope.newUser.deliveryaddress.zone = $label;
		//controlAddress();
	
			
			$http.get(AppConfig.ServiceUrls.CityZone+$scope.newUser.deliveryaddress.zoneObj.idZone).success(function(result){
				$scope.cities = result;
			});
		
	};
	$scope.onSelectCity = function ($item, $model, $label) {
		$scope.newUser.deliveryaddress.cityObj = $model;
		$scope.newUser.deliveryaddress.city = $label;
		//controlAddress();
		
	};
	
	$scope.zoneChange = function(){
		//controlAddress();
		$scope.newUser.deliveryaddress.zoneObj = null;
		$scope.newUser.deliveryaddress.cityObj = null;
		if ($scope.newUser.deliveryaddress.countryObj.idCountry){
			
				$http.get(AppConfig.ServiceUrls.Zone+$scope.newUser.deliveryaddress.countryObj.idCountry).success(function(result){
					$scope.zones = result;
				});
				$http.get(AppConfig.ServiceUrls.CityCountry+$scope.newUser.deliveryaddress.countryObj.idCountry).success(function(result){
					$scope.cities = result;
				});
			
		}else{
			$scope.cities = [];
		}
	}
	$scope.cityChange = function(){
		//controlAddress();
		$scope.newUser.deliveryaddress.cityObj = null;
		
	}
	
	$scope.registry = function(){
			LoaderFactory.loader = true;
		
		$http.post(AppConfig.ServiceUrls.CreateEcUser+AppConfig.Const.CompanyId,$scope.newUser).success(function(result){
			if(result.type == "success"){
				$scope.msg.successMessage("Registrazione riuscita. E' stata inviata una mail con le istruzioni riguardanti l'attivazione dell'account");
			}else{
				$scope.msg.alertMessage(result.errorMessage);
			}
			LoaderFactory.loader = false;
		}).error(function(error){
			LoaderFactory.loader = false;
			$scope.msg.alertMessage(error);
		});
	}
}).controller("EcPasswordRecover",function($scope,$http,AppConfig,$location,AlertsFactory,URLFactory){
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	var code = URLFactory.getParameter("uid");
	$scope.user = {};
	$scope.user.code = code;
	$scope.registry = function(){
		LoaderFactory.loader = true;
		$http.post(AppConfig.ServiceUrls.EcRecoverPWD,$scope.user).success(function(result){
			if(result.type == "success"){
				$scope.msg.successMessage("Password aggiornata con successo");
			}else{
				$scope.msg.alertMessage(result.errorMessage);
			}
			LoaderFactory.loader = false;
		}).error(function(error){
			LoaderFactory.loader = false;
			$scope.msg.alertMessage(error);
		});
	}
});

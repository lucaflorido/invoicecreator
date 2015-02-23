angular.module("rocchi.list")
.controller('RocchiListListCtrl',["$scope","$http","AppConfig",function($scope,$http,AppConfig){
    
	
	
	$http.get(AppConfig.ServiceUrls.List).success(function(data){
		$scope.lists= data;
	});
	$scope.deleteElement = function(id){
		for(var i=0;i<$scope.lists.length;i++){
			if (id == $scope.lists[i].idList){
				$scope.deletelist = $scope.lists[i];
				$.ajax({
						url:AppConfig.ServiceUrls.List,
						type:"DELETE",
						data:"listobj="+JSON.stringify($scope.deletelist),
						success:function(data){
							alert("Utente eliminato con successo");
							$http.get(AppConfig.ServiceUrls.List).success(function(data){
								$scope.lists= data;
							});
						}	
					})
			}	
		}
	}
	$scope.printElement = function(id){
		$.ajax({
						url:"rest/print/list/"+id,
						type:"POST",
						success:function(data){
							//alert("Utente eliminato con successo");
							window.open(JSON.parse(data), '_blank');
						}	
					})
	}
	$scope.dateSortFunction = function(list) {
		var date = list.startdate.split("/");
		return date[2]+date[1]+date[0];
	};
}])
.controller('RocchiListDetailCtrl',["$scope","$http","$routeParams","ScopeFactory","AppConfig","AlertsFactory",function($scope,$http,$routeParams,ScopeFactory,AppConfig,AlertsFactory){
    
	GECO_validator.startupvalidator();
	$scope.newList = {isPercentage:true};
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	$scope.idlist= $routeParams.idlist;
	$http.get(AppConfig.ServiceUrls.Product).success(function(data){
		$scope.products= data;
		$http.get(AppConfig.ServiceUrls.List+$scope.idlist).success(function(data){
			$scope.list= data;
		});
	});
	$scope.addProdElement = function(list){
		$scope.prodid = 0
		list.listproduct.push({idListProduct:0});
	}
	$scope.changeProdElement = function(ump){
	    
		if (ump.product != null){
			for(var i=0;i<$scope.products.length;i++){
				if (ump.product.idProduct == $scope.products[i].idProduct){
					$scope.currentProd = $scope.products[i];
				}
			}
		}
		$scope.prodid = ump.idListProduct
	}
	$scope.calculatePercentage = function(listprod){
		var obj = {purchaseprice:listprod.product.purchaseprice,sellprice:listprod.price,percentage:listprod.percentage,endprice:listprod.endprice,taxrate:listprod.product.taxrate.value};
		$.ajax({
			url:AppConfig.ServiceUrls.UtilPricePercentage,
			type:"POST",
			data:"prices="+JSON.stringify(obj),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					var priceCalc = result.success;
					listprod.price = priceCalc.sellprice;
					listprod.percentage = priceCalc.percentage;
					listprod.endprice = priceCalc.endprice;
					$scope.msg.infoMessage("SALVARE PER REGISTRARE LE MODIFICHE");
					$scope.$apply();
				}else{
					$scope.msg.alertMessage(result.errorMessage);
					$scope.$apply();
				}	
			},error:function(data){
				$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DEL LISTINO");
				$scope.$apply();
			}	
		})
	};
	$scope.calculateSellPrice = function(listprod){
		var obj = {purchaseprice:listprod.product.purchaseprice,sellprice:listprod.price,percentage:listprod.percentage,endprice:listprod.endprice,taxrate:listprod.product.taxrate.value};
		$.ajax({
			url:AppConfig.ServiceUrls.UtilPricePrice,
			type:"POST",
			data:"prices="+JSON.stringify(obj),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					var priceCalc = result.success;
					listprod.price = priceCalc.sellprice;
					listprod.percentage = priceCalc.percentage;
					listprod.endprice = priceCalc.endprice;
					$scope.msg.infoMessage("SALVARE PER REGISTRARE LE MODIFICHE");
					$scope.$apply();
				}else{
					$scope.msg.alertMessage(result.errorMessage);
					$scope.$apply();
				}	
			},error:function(data){
				$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DEL LISTINO");
				$scope.$apply();
			}	
		})
	};
	$scope.calculateEndPrice = function(listprod){
		var obj = {purchaseprice:listprod.product.purchaseprice,sellprice:listprod.price,percentage:listprod.percentage,endprice:listprod.endprice,taxrate:listprod.product.taxrate.value};
		$.ajax({
			url:AppConfig.ServiceUrls.UtilPriceEndPrice,
			type:"POST",
			data:"prices="+JSON.stringify(obj),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					var priceCalc = result.success;
					listprod.price = priceCalc.sellprice;
					listprod.percentage = priceCalc.percentage;
					listprod.endprice = priceCalc.endprice;
					$scope.msg.infoMessage("SALVARE PER REGISTRARE LE MODIFICHE");
					$scope.$apply();
				}else{
					$scope.msg.alertMessage(result.errorMessage);
					$scope.$apply();
				}	
			},error:function(data){
				$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DEL LISTINO");
				$scope.$apply();
			}	
		})
	};
	$scope.saveProduct = function(){
	//if (GECO_validator.requiredFields()== true && GECO_validator.emailFields()==true){
			
			
			$scope.newList.list = $scope.list;
			$scope.value = 0;
			$.ajax({
				url:AppConfig.ServiceUrls.List,
				type:"PUT",
				data:"lists="+JSON.stringify($scope.newList),
				success:function(data){
					result = JSON.parse(data);
					if (result.type == "success"){	
						$scope.list.idList = result.success;
						$scope.idlist = result.success;
						$scope.msg.successMessage("LISTINO SALVATO CON SUCCESSO");
						$http.get(AppConfig.ServiceUrls.List+$scope.idlist).success(function(data){
							$scope.list= data;
						});
					}else{
						$scope.msg.alertMessage(result.errorMessage);
					}	
				},error:function(data){
					$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DEL LISTINO");
				}	
			})
		//}
	} ;
	
}]);

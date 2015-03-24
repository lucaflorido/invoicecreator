angular.module("rocchi.product")
.controller('RocchiProductListCtrl',["$scope","$rootScope","$http","ScopeFactory","$location","AppConfig","AlertsFactory",function($scope,$rootScope,$http,ScopeFactory,$location,AppConfig,AlertsFactory){
    $scope.importview = false;	
	$scope.location = $location;
	$scope.url = AppConfig.ServiceUrls;
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	$scope.importobj = {};
	$(document).unbind("keydown");
	$(document).keydown(function(e){
		if(e.altKey && e.keyCode == 78 ){
			$location.path("/product/0");
			$scope.$apply();
		}
		if(e.altKey && e.keyCode == 80 ){
		    $scope.printElements();
			$scope.$apply();
	   }
	});
	
	if ($scope.pagesize == null ){
		$scope.pagesize = 99;
		
	}else{
		//$scope.pagesize = ScopeFactory.factory.productList.pagesize
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
	};
	intitialize();
	$scope.pageArray = [];
	if ($scope.showFilter == null){
		$scope.showFilter = false;
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
	$scope.products = [];
	$scope.updateProd = {};
	
	
	$scope.getProducts = function(page){
			$scope.filter.pagefilter.startelement = (page - 1 ) * $scope.pagesize_confirmed;
			$scope.filter.pagefilter.pageSize = $scope.pagesize_confirmed;
			$scope.filter = $scope.filter
			$http.post(AppConfig.ServiceUrls.ProductMainList,$scope.filter).then(function(result){
				$scope.products = result.data.success;
			})
			
	}
	
	$scope.getProductsNumber = function(){
		
		if ($scope.products.length != $scope.pagesize){
		$scope.pages = [];
		$scope.totalitems = 0;
		$scope.pageArray = [];
			$http.get(AppConfig.ServiceUrls.ProductPagination+$scope.pagesize).then(function(result){
				$scope.pages = result.data.pages;
				$scope.totalitems = result.data.totalitems;
				$scope.pagesize_confirmed = $scope.pagesize;
				$scope.getProducts(1);
			});
			
		}
	}
	$scope.getProductsNumber();
	
	$scope.deleteElement = function(id){
		for(var i=0;i<$scope.products.length;i++){
			if (id == $scope.products[i].idProduct){
				$scope.deleteproduct = $scope.products[i];
				$.ajax({
						url:AppConfig.ServiceUrls.Product,
						type:"DELETE",
						data:"productobj="+JSON.stringify($scope.deleteproduct),
						success:function(data){
							alert("Utente eliminato con successo");
							$http.get('rest/registry/product').success(function(data){
								$scope.products= data;
							});
						}	
					})
			}	
		}
	}
	$scope.printElements = function(){
		$.ajax({
						url:"rest/print/products/",
						type:"POST",
						data:"filter="+JSON.stringify($scope.filter),
						success:function(data){
							//alert("Utente eliminato con successo");
							window.open(JSON.parse(data), '_blank');
						}	
					})
	}
	$scope.openIncrement = function(){
		if ($scope.showIncrement == true){
			$scope.showIncrement = false;
			$scope.showFilter = false;
		}else{
			$scope.showIncrement = true;
			$scope.showFilter = true;
		}
		$rootScope.showFilter = $scope.showFilter;
		$rootScope.showIncrement = $scope.showIncrement;
	}
	$scope.incrementPrices = function(){
		$http.post(AppConfig.ServiceUrls.ProductIncrement,$scope.filter).then(
			function(results){
				var result = results.data;
				if (result.type == "success"){	
					$scope.getProductsNumber();
				}else{
					alert("Errore: "+results.errorName+" Messaggio:"+result.errorMessage);
				}	
		});
		/*$.ajax({
				url:"rest/registry/product/increment/",
				type:"POST",
				data:"filter="+JSON.stringify($scope.filter),
				success:function(data){
					result = JSON.parse(data);
					if (result.type == "success"){	
						
						$scope.getProductsNumber();
					}else{
						$scope.msg.alertMessage(data.data.errorMessage);
					}	
				}	
			});*/
	}
	$scope.changeCategory = function(){
		$scope.filter.category = $scope.currentCategory;
		$scope.subcategories = $scope.currentCategory.subcategories		
		$scope.currentSubCategory = null;
	}
	$scope.novalid = false;
	$scope.importProduct = function(filename){
		
		$scope.importobj.filename = filename;
		$http.post(AppConfig.ServiceUrls.ImportProducts,$scope.importobj).then(function(result){
			if (result.data.type == "success"){
				$scope.msg.infoMessage("IMPORTAZIONE TERMINATA CON SUCCESSO");
				$scope.importview = false;
				$scope.novalid = true;
				$scope.getProducts();
			}else{
				$scope.msg.alertMessage(result.data.errorMessage);
			}
			
		});
	}
	 $scope.onComplete = function (response) {
			if (response.data.type == "success"){
				$scope.importProduct(response.data.success)
			}else{
				$scope.msg.alertMessage(response.data.errorMessage);
			}
		}
	 $scope.addNew = function(){
		 $location.path("/product/0");
	 };
}])
.controller('RocchiProductDetailCtrl',["$scope","$http","$routeParams","$location","$rootScope","AppConfig","AlertsFactory",function($scope,$http,$routeParams,$location,$rootScope,AppConfig,AlertsFactory){
    
	GECO_validator.startupvalidator();
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	$scope.currentBrand = {};
	$scope.currentSupplier = {};
	$scope.currentGroup = {};
	$scope.currentTaxRate = {};
	
	$(document).unbind("keydown");
	$(document).keydown(function(e){
		if(e.altKey && e.keyCode == 81 ){
		    $scope.saveProduct();
			$scope.$apply();
		}
		if(e.altKey && e.keyCode == 78 ){
			$location.path("/product/0");
			$scope.$apply();
		}
		if(e.altKey && e.keyCode == 69 ){
			$location.path("/product");
			$scope.$apply();
		}
		if(e.altKey && e.keyCode == 85 ){
			$scope.product.ums.push({idUnitMeasureProduct:0});
			$scope.umpid = $scope.product.ums.length-1;
			$scope.$apply();
		}
	});
	$scope.idproduct= $routeParams.idproduct;
	$scope.isNew = false
	if ($scope.idproduct == 0){
		$scope.isNew = true;
		$scope.umpid = 0;
	}else{
		$scope.isNew = false;
	}
	var getProduct = function(){
		$http.get(AppConfig.ServiceUrls.UniteMeasure).success(function(data){
			$scope.ums= data;
						$http.get(AppConfig.ServiceUrls.Product+$scope.idproduct).success(function(data){
							$scope.product= data;
							if ($rootScope.newProductToAdd != null)
								$scope.product.code = $rootScope.newProductToAdd;
							if ($scope.product.idProduct == 0){
								$scope.product.ums = [];
								$scope.product.ums.push({idUnitMeasureProduct:0,preference:true,um:$scope.ums[0],conversion:1});
							}
							$http.get(AppConfig.ServiceUrls.ProductGroup).success(function(data){
								$scope.groups= data;
									if($scope.product.group){
										for (var i=0;i<$scope.groups.length;i++){
											if ($scope.product.group.idGroupProduct == $scope.groups[i].idGroupProduct){
												$scope.currentGroup = $scope.groups[i]; 
											}
										}
									}
							});
							$http.get(AppConfig.ServiceUrls.Region).success(function(data){
								$scope.regions= data;
									if($scope.product.region){
										for (var i=0;i<$scope.regions.length;i++){
											if ($scope.product.region.idRegion == $scope.regions[i].idRegion){
												$scope.currentRegion = $scope.regions[i]; 
											}
										}
									}
							});
							
							$http.get(AppConfig.ServiceUrls.TaxRate).success(function(data){
					$scope.taxrates= data;
							for (var itx=0;itx<$scope.taxrates.length;itx++){
								if($scope.product.taxrate){
									if ($scope.product.taxrate.idtaxrate == $scope.taxrates[itx].idtaxrate){
										$scope.currentTaxRate = $scope.taxrates[itx]; 
									}
								}
							}
							});
							$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
								$scope.categorys= data;
								if($scope.product.category){
									for (var ig=0;ig<$scope.categorys.length;ig++){
										if ($scope.product.category.idCategoryProduct == $scope.categorys[ig].idCategoryProduct){
											$scope.currentCategory = $scope.categorys[ig];
											$scope.subcategories = $scope.currentCategory.subcategories		
											$scope.currentSubCategory = null;
										}
									}
									for (var igs=0;igs<$scope.subcategories.length;igs++){
										if ($scope.product.subcategory == null){
											$scope.product.subcategory = {};
										}
										if ($scope.product.subcategory.idSubCategoryProduct == $scope.subcategories[igs].idSubCategoryProduct){
											$scope.currentSubCategory = $scope.subcategories[igs]; 
										}
									}
								}
							});
				});
		});
	}
	getProduct();
	$scope.changeCategory = function(){
		$scope.subcategories = $scope.currentCategory.subcategories		
		$scope.currentSubCategory = null;
	}
	$scope.addUMElement = function(product){
		product.ums.push({idUnitMeasureProduct:0});
		$scope.umpid = product.ums.length-1 ;
	}
	
	$scope.deleteListElement = function(prod){
		$.ajax({
				url:AppConfig.ServiceUrls.ProductList,
				type:"DELETE",
				data:"productobj="+JSON.stringify(prod),
				success:function(data){
					$scope.msg.successMessage("UNITA' DI MISURA ELIMINATA CON SUCCESSO");
					getProduct();
					
				}	
			});
	}
	$scope.deleteUMElement = function(um){
		$.ajax({
				url:AppConfig.ServiceUrls.ProductUniteMeasure,
				type:"DELETE",
				data:"productobj="+JSON.stringify(um),
				success:function(data){
					$scope.msg.successMessage("UNITA' DI MISURA ELIMINATA CON SUCCESSO");
					getProduct();
					
				}	
			});
	}
	$scope.addListElement = function(){
		$.ajax({
			url:AppConfig.ServiceUrls.ListNoProduct,
			type:"POST",
			data:"product="+JSON.stringify($scope.product),
			success:function(data){
				var result = JSON.parse(data);
				$scope.availablelists = result;
				$scope.product.listproduct.push({idListProduct:0});
				$scope.listid = 0 ;
				$scope.$apply();
				
			}	
		});
		
	}
	$scope.selectList = function(prodlist){
		prodlist.percentage = prodlist.list.increment;
		$scope.calculateSellPrice(prodlist);
	}
	$scope.changeUMElement = function(ump){
	    $scope.umpid = ump; //ump.idUnitMeasureProduct
	}
	
	$scope.saveProduct = function(){
		$scope.product.taxrate = $scope.currentTaxRate;
		$scope.product.group = $scope.currentGroup;
		$scope.product.region = $scope.currentRegion;
		$scope.product.category = $scope.currentCategory;
		$scope.product.isProduct = true;
		$.ajax({
				url:AppConfig.ServiceUrls.Product,
				type:"PUT",
				data:"products="+JSON.stringify($scope.product),
				success:function(data){
					
					result = JSON.parse(data);
					if (result.type == "success"){	
						$scope.product.idProduct = result.success;
						$scope.idproduct = result.success;
						
						$scope.$apply();
						if ($rootScope.headScope != null){
							$rootScope.newProductToAdd = $scope.product.code;
							$scope.isNew = false;
						}else{
							$scope.isNew = false;
							//$location.path("/product/"+$scope.product.idProduct);
						}
						$http.get(AppConfig.ServiceUrls.Product+$scope.idproduct).success(function(data){
							$scope.product= data;
							$scope.umpid = -1;
							$scope.listid = -1;
						});
						$scope.msg.successMessage("PRODOTTO SALVATO CON SUCCESSO");
					}else{
						$scope.msg.alertMessage(result.errorMessage);
					}	
				},error:function(data){
					$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DEL LISTINO");
				}	
			})
		//}
	} ;
	$rootScope.setSaveControl($scope.saveProduct);
	$scope.newProduct = function(){
		$location.path("/product/0");
	} 
	$scope.mainPage = function(){
		$location.path("/product");
	} 
	$scope.calculatePrices = function(type){
		$scope.basicPrices = {};
		$scope.basicPrices.sellprice = $scope.product.sellprice;
		$scope.basicPrices.percentage = $scope.product.percentage;
		$scope.basicPrices.purchaseprice = $scope.product.purchaseprice;
		$.ajax({
				url:AppConfig.ServiceUrls.ProductBasicPrice+type,
				type:"POST",
				data:"prices="+JSON.stringify($scope.basicPrices),
				success:function(data){
					
					result = JSON.parse(data);
					if (result.type == "success"){	
						$scope.product.sellprice = result.success.sellprice;
						$scope.product.percentage = result.success.percentage;
						$scope.product.purchaseprice = result.success.purchaseprice;
						$scope.$apply();
					}else{
						$scope.msg.alertMessage(result.errorMessage);
					}	
				}	
			})
	}
	$scope.calculateListPrices = function(){
		$scope.product.taxrate = $scope.currentTaxRate;
		$.ajax({
				url:AppConfig.ServiceUrls.ProductBasicPriceList,
				type:"POST",
				data:"product="+JSON.stringify($scope.product),
				success:function(data){
					
					result = JSON.parse(data);
					if (result.type == "success"){	
						$scope.product = result.success;
						$scope.$apply();
					}else{
						$scope.msg.alertMessage(result.errorMessage);
					}	
				}	
			})
	}
	
	$scope.calculatePercentage = function(listprod){
		var obj = createProductCalcObj(listprod);
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
		var obj = createProductCalcObj(listprod);
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
	var createProductCalcObj = function(listprod){
		$scope.product.taxrate = $scope.currentTaxRate;
		return {purchaseprice:$scope.product.purchaseprice,sellprice:listprod.price,percentage:listprod.percentage,endprice:listprod.endprice,taxrate:$scope.product.taxrate.value};
	}
	$scope.calculateEndPrice = function(listprod){
		var obj = createProductCalcObj(listprod);
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
				$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DEL PRODOTTO");
				$scope.$apply();
			}	
		})
	};
	$scope.setListId = function(listProd){
		if (listProd.idListProduct == $scope.listid ){
			$scope.listid = -1;
		}else{
			$scope.listid = listProd.idListProduct ;
		}
			
	}
}]);
/**
 * 
 */
angular.module("rocchi.product")
.controller('RocchiCategoryProductCtrl',["$scope","$http","AppConfig","AlertsFactory",function($scope,$http,AppConfig,AlertsFactory){
	$scope.msg = AlertsFactory;
    $scope.msg.initialize();
	$scope.categoryproductsaved = true;
	$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
		$scope.categoryproducts= data;
	});
	$scope.modifyid = 0;
	$scope.modifycategoryproductElement = function(cat){
		if ($scope.modifyid != cat.idCategoryProduct){
			$scope.modifyid = cat.idCategoryProduct;
			$scope.selectedcategory = cat;
		}else{
			$scope.modifyid = 0;
			$scope.selectedcategory = null;
		}
	}
	$scope.modifysubcategoryproductElement = function(cat){
		if ($scope.modifyscid != cat.idSubCategoryProduct){
			$scope.modifyscid = cat.idSubCategoryProduct;
			
		}else{
			$scope.modifyscid = 0;
			
		}
	}
	$scope.addcategoryproductElement = function(id){
		$scope.categoryproductsaved = false;
		$scope.categoryproducts.push({idcategoryproduct:0});
	}
	$scope.addSubCategoryElement = function(){
		$scope.categoryproductsaved = false;
		$scope.selectedcategory.subcategories.push({idSubCategoryProduct:0});
			
		
	}
	$scope.deletecategoryproductElement = function(cat){
		$http.delete(AppConfig.ServiceUrls.ProductCategory,{data:cat}).then(function(data){
			$scope.msg.successMessage("ELEIMINAZIONE EFFETTUATA CON SUCCESSO");
			$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
					$scope.categoryproducts= data;
					$scope.selectedcategory = null;
			});
			
			});
		/*for(var i=0;i<$scope.categoryproducts.length;i++){
			if (id == $scope.categoryproducts[i].idcategoryproduct){
				$scope.deletecategoryproduct = $scope.categoryproducts[i];
				$.ajax({
						url:AppConfig.ServiceUrls.ProductCategory,
						type:"DELETE",
						data:"categoryproductobj="+JSON.stringify($scope.deletecategoryproduct),
						success:function(data){
							$scope.msg.successMessage("ELEIMINAZIONE EFFETTUATA CON SUCCESSO");
								$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
										$scope.categoryproducts= data;
								});
								
						}	
					})
			}	
		}*/
	}
	$scope.deletesubcategorycustomerElement = function(cat){
		$http.delete(AppConfig.ServiceUrls.ProductSubCategory,{data:cat}).then(function(data){
			$scope.msg.successMessage("ELEIMINAZIONE EFFETTUATA CON SUCCESSO");
			$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
					$scope.categoryproducts= data;
					$scope.selectedcategory = null;
			});
			
		});
		
	}
	$scope.savecategoryproducts = function(){
		$.ajax({
			url:AppConfig.ServiceUrls.ProductCategory,
			type:"PUT",
			data:"categoryproducts="+JSON.stringify($scope.categoryproducts),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					$scope.categoryproductsaved = true;
					$scope.modifyid = 0;
					$scope.$apply();
					$scope.msg.successMessage("ELEMENTO SALVATO CON SUCCESSO");
					$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
						$scope.categoryproducts= data;
					});
				}else{
					$scope.msg.alertMessage(result.errorMessage);
				}	
					
					
			}	
		})
		
	}
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
    	$(".detailview").css("display","none")
	});
	$scope.detailView = function(id){
		
		if ($("#detail"+id).hasClass("open")){
			$scope.detailViewOpen(id);
		}else{
			$scope.detailViewClose(id);
		}
	}
	$scope.detailViewOpen = function(id){
		$("#detail"+id).removeClass("open");
		$("#detail"+id).addClass("close");
		$("#detailview"+id).css("display","");
	}
	$scope.detailViewClose = function(id){
		$("#detail"+id).removeClass("close");
			$("#detail"+id).addClass("open");
			$("#detailview"+id).css("display","none");
	}
}]);
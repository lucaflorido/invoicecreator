/**
 * 
 */
angular.module("rocchi.product")
.controller('RocchiCategoryProductCtrl',["$scope","$http","AppConfig",function($scope,$http,AppConfig){
    
	$scope.categoryproductsaved = true;
	$http.get(AppConfig.ServiceUrls.ProductCategory).success(function(data){
		$scope.categoryproducts= data;
	});
	$scope.modifyid = 0;
	$scope.modifycategoryproductElement = function(id){
		if ($scope.modifyid != id){
			$scope.modifyid = id;
			$scope.detailViewOpen(id);
		}else{
			$scope.modifyid = 0;
			$scope.detailViewClose(id);
		}
	}
	
	$scope.addcategoryproductElement = function(id){
		$scope.categoryproductsaved = false;
		$scope.categoryproducts.push({idcategoryproduct:0});
	}
	$scope.addSubCategoryElement = function(categoryproduct){
		if ($scope.categoryproductsaved == true){
			$scope.categoryproductsaved = false;
			categoryproduct.subcategories.push({idSubCategoryProduct:0});
			$scope.detailViewOpen(categoryproduct.idCategoryProduct);
		}
	}
	$scope.deletecategorycustomerElement = function(id){
		for(var i=0;i<$scope.categoryproducts.length;i++){
			if (id == $scope.categoryproducts[i].idcategoryproduct){
				$scope.deletecategoryproduct = $scope.categoryproducts[i];
				$.ajax({
						url:AppConfig.ServiceUrls.ProductCategory,
						type:"DELETE",
						data:"categoryproductobj="+JSON.stringify($scope.deletecategoryproduct),
						success:function(data){
								$http.get('rest/basic/categoryproduct').success(function(data){
										$scope.categoryproducts= data;
								});
								
						}	
					})
			}	
		}
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
					$http.get('rest/basic/categoryproduct').success(function(data){
						$scope.categoryproducts= data;
					});
				}else{
					alert("Errore: "+result.errorName+" Messaggio:"+result.errorMessage);
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
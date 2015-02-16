/**
 * 
 */
angular.module("rocchi.product")
.controller('RocchiGroupProductCtrl',["$scope","$http","AppConfig",function($scope,$http,AppConfig){
    
	$scope.groupproductsaved = true;
	$http.get(AppConfig.ServiceUrls.ProductGroup).success(function(data){
		$scope.groupproducts= data;
	});
	$scope.modifyid = 0;
	$scope.modifygroupproductElement = function(id){
		if ($scope.modifyid != id){
			$scope.modifyid = id;
		}else{
			$scope.modifyid = 0
		}
	}
	$scope.addgroupproductElement = function(id){
		$scope.groupproductsaved = false;
		$scope.groupproducts.push({idGroupProduct:0});
	}
	$scope.deletegroupproductElement = function(id){
		for(var i=0;i<$scope.groupproducts.length;i++){
			if (id == $scope.groupproducts[i].idGroupProduct){
				$scope.deletegroupproduct = $scope.groupproducts[i];
				$.ajax({
						url:AppConfig.ServiceUrls.ProductGroup,
						type:"DELETE",
						data:"groupproductobj="+JSON.stringify($scope.deletegroupproduct),
						success:function(data){
								$http.get('rest/basic/groupproduct').success(function(data){
										$scope.groupproducts= data;
								});
								
						}	
					})
			}	
		}
	}
	$scope.savegroupproducts = function(){
		$.ajax({
			url:AppConfig.ServiceUrls.ProductGroup,
			type:"PUT",
			data:"groupproducts="+JSON.stringify($scope.groupproducts),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					$scope.groupproductsaved = true;
					$scope.modifyid = 0;
					$scope.$apply();
					$http.get(AppConfig.ServiceUrls.ProductGroup).success(function(data){
						$scope.groupproducts= data;
					});
				}else{
					alert("Errore: "+result.errorName+" Messaggio:"+result.errorMessage);
				}	
					
					
			}	
		})
		
	}
	
	
}]);
/**
 * 
 */
angular.module("rocchi.customer")
.controller('RocchiCustomerCategoryListCtrl',["$scope","$http","AlertsFactory","AppConfig",function($scope,$http,AlertsFactory,AppConfig){
    $scope.msg = AlertsFactory;
    $scope.msg.initialize();
	$scope.customercategorysaved = true;
	$http.get(AppConfig.ServiceUrls.CustomerCategory).success(function(data){
		$scope.customercategorys= data;
	});
	$scope.modifyid = 0;
	$scope.modifycustomercategoryElement = function(id){
		if ($scope.modifyid != id){
			$scope.modifyid = id;
		}else{
			$scope.modifyid = 0
		}
	}
	$scope.addcustomercategoryElement = function(id){
		$scope.customercategorysaved = false;
		$scope.customercategorys.push({idCategoryCustomer:0});
	}
	$scope.deletecustomercategoryElement = function(id){
		for(var i=0;i<$scope.customercategorys.length;i++){
			if (id == $scope.customercategorys[i].idCategoryCustomer){
				$scope.deletecustomercategory = $scope.customercategorys[i];
				$.ajax({
						url:AppConfig.ServiceUrls.CustomerCategory,
						type:"DELETE",
						data:"categorycustomerobj="+JSON.stringify($scope.deletecustomercategory),
						success:function(data){
							$scope.msg.successMessage("CATEGORIA ELIMINATA CON SUCCESSO");
								$http.get(AppConfig.ServiceUrls.CustomerCategory).success(function(data){
										$scope.customercategorys= data;
								});
								
						},error:function(error){
							
							$scope.msg.alertMessage("ERRORE NELLA CANCELLAZIONE DELLA CATEGORIA");
						}	
					})
			}	
		}
	}
	$scope.savecustomercategorys = function(){
		$.ajax({
			url:AppConfig.ServiceUrls.CustomerCategory,
			type:"PUT",
			data:"categorycustomers="+JSON.stringify($scope.customercategorys),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					$scope.customercategorysaved = true;
					$scope.modifyid = 0;
					$scope.$apply();
					$scope.msg.successMessage("CATEGORIA SALVATA CON SUCCESSO");
					$http.get(AppConfig.ServiceUrls.CustomerCategory).success(function(data){
						$scope.customercategorys= data;
					});
				}else{
					$scope.msg.alertMessage(result.errorMessage);
				}
										
			},error:function(error){
				
				$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DELLA CATEGORIA");
			}		
		})
		
	}
	
	
}]);

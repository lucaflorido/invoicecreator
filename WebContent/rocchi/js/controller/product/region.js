/**
 * 
 */
angular.module("rocchi.product")
.controller('RocchiRegionCtrl',["$scope","$http","AppConfig","AlertsFactory",function($scope,$http,AppConfig,AlertsFactory){
    $scope.msg = AlertsFactory;
    $scope.msg.initialize();
	$scope.regionsaved = true;
	$http.get(AppConfig.ServiceUrls.Region).success(function(data){
		$scope.regions= data;
	});
	$scope.modifyid = 0;
	$scope.modifyregionElement = function(id){
		if ($scope.modifyid != id){
			$scope.modifyid = id;
		}else{
			$scope.modifyid = 0
		}
	}
	$scope.addregionElement = function(id){
		$scope.regionsaved = false;
		$scope.regions.push({idRegion:0});
	}
	$scope.deleteregionElement = function(id){
		for(var i=0;i<$scope.regions.length;i++){
			if (id == $scope.regions[i].idRegion){
				$scope.deleteregion = $scope.regions[i];
				$.ajax({
						url:AppConfig.ServiceUrls.Region,
						type:"DELETE",
						data:"regionobj="+JSON.stringify($scope.deleteregion),
						success:function(data){
							$scope.msg.successMessage("ELIMINAZIONE RIUSCITA CON SUCCESSO");
								$http.get(AppConfig.ServiceUrls.Region).success(function(data){
										$scope.regions= data;
								});
								
						}	
					})
			}	
		}
	}
	$scope.saveregions = function(){
		$.ajax({
			url:AppConfig.ServiceUrls.Region,
			type:"PUT",
			data:"regions="+JSON.stringify($scope.regions),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					$scope.regionsaved = true;
					$scope.modifyid = 0;
					$scope.$apply();
					$scope.msg.successMessage("SALVATAGGIO RIUSCITO CON SUCCESSO");
					$http.get(AppConfig.ServiceUrls.Region).success(function(data){
						$scope.regions= data;
					});
				}else{
					$scope.msg.alertMessage(result.errorMessage);
				}	
					
					
			}	
		})
		
	}
	
	
}]);
/**
 * 
 */
angular.module("rocchi.product")
.controller('RocchiUnitmeasureCtrl',["$scope","$http","AppConfig","AlertsFactory",function($scope,$http,AppConfig,AlertsFactory){
    $scope.msg = AlertsFactory;
    $scope.msg.initialize();
	$scope.umsaved = true;
	$http.get(AppConfig.ServiceUrls.UniteMeasure).success(function(data){
		$scope.ums= data;
	});
	$scope.modifyid = 0;
	$scope.modifyUmElement = function(id){
		$scope.modifyid = id;
	}
	$scope.addUmElement = function(id){
		$scope.umsaved = false;
		$scope.ums.push({idUnitMeasure:0});
	}
	$scope.deleteUmElement = function(id){
		for(var i=0;i<$scope.ums.length;i++){
			if (id == $scope.ums[i].idUnitMeasure){
				$scope.deleteUm = $scope.ums[i];
				$.ajax({
						url:AppConfig.ServiceUrls.UniteMeasure,
						type:"DELETE",
						data:"umobj="+JSON.stringify($scope.deleteUm),
						success:function(data){
							$scope.msg.successMessage("ELIMINAZIONE RIUSCITA CON SUCCESSO");
								$http.get(AppConfig.ServiceUrls.UniteMeasure).success(function(data){
										$scope.ums= data;
								});
								
						}	
					})
			}	
		}
	}
	$scope.saveUms = function(){
		$.ajax({
			url:AppConfig.ServiceUrls.UniteMeasure,
			type:"PUT",
			data:"ums="+JSON.stringify($scope.ums),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					$scope.umsaved = true;
					$scope.modifyid = 0;
					$scope.msg.successMessage("SALVATAGGIO RIUSCITO CON SUCCESSO");
					$scope.$apply();
					$http.get(AppConfig.ServiceUrls.UniteMeasure).success(function(data){
							$scope.ums= data;
					});
				}else{
					$scope.msg.alertMessage(result.errorMessage);
				}		
			}	
		})
		
	}
	
	
}]);
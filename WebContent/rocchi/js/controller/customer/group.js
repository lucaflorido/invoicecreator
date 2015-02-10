/**
 * 
 */
angular.module("rocchi.customer")
.controller('RocchiCustomerGroupListCtrl',["$scope","$http","AppConfig","AlertsFactory",function($scope,$http,AppConfig,AlertsFactory){
    
	$scope.customergroupsaved = true;
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	$http.get(AppConfig.ServiceUrls.CustomerGroup).success(function(data){
		$scope.customergroups= data;
	}).error(function(message){
		$scope.msg.alertMessage(message);
	});
	$scope.modifyid = 0;
	$scope.modifycustomergroupElement = function(id){
		if ($scope.modifyid != id){
			$scope.modifyid = id;
		}else{
			$scope.modifyid = 0
		}
	}
	$scope.addcustomergroupElement = function(id){
		$scope.customergroupsaved = false;
		$scope.customergroups.push({idGroupCustomer:0});
	}
	$scope.deletecustomergroupElement = function(id){
		for(var i=0;i<$scope.customergroups.length;i++){
			if (id == $scope.customergroups[i].idGroupCustomer){
				$scope.deletecustomergroup = $scope.customergroups[i];
				$.ajax({
						url:AppConfig.ServiceUrls.CustomerGroup,
						type:"DELETE",
						data:"groupcustomerobj="+JSON.stringify($scope.deletecustomergroup),
						success:function(data){
							$scope.msg.successMessage("GRUPPO ELIMINATO CON SUCCESSO");
								$http.get(AppConfig.ServiceUrls.CustomerGroup).success(function(data){
										$scope.customergroups= data;
								});
								
						},error:function(error){
							
							$scope.msg.alertMessage("ERRORE NELL'ELIMINAZIONE DEL GRUPPO");
						}	
					})
			}	
		}
	}
	$scope.savecustomergroups = function(){
		$.ajax({
			url:AppConfig.ServiceUrls.CustomerGroup,
			type:"PUT",
			data:"groupcustomers="+JSON.stringify($scope.customergroups),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					$scope.customergroupsaved = true;
					$scope.modifyid = 0;
					$scope.$apply();
					$http.get(AppConfig.ServiceUrls.CustomerGroup).success(function(data){
						$scope.customergroups= data;
					});
					$scope.msg.successMessage("GRUPPO SALVATO CON SUCCESSO");
				}else{
					$scope.msg.alertMessage(result.errorMessage);
				}
				
					
			},error:function(error){
				
				$scope.msg.alertMessage("ERRORE NEL SALVATAGGIO DEL GRUPPO");
			}		
		})
		
	}
	
	
}]);


var gecoBasicControllers = angular.module("gecoBasicControllers",[]);

/*****
TAXRATE
***/
gecoBasicControllers.controller('TaxrateCtrl',["$scope","$http","$rootScope",function($scope,$http,$rootScope){
    $scope.loginuser = GECO_LOGGEDUSER.checkloginuser();
	$scope.taxratesaved = true;
	$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/basic/taxrate').success(function(data){
		$scope.taxrates= data;
	});
	$scope.modifyid = 0;
	$scope.modifyTaxrateElement = function(id){
		if ($scope.modifyid != id){
			$scope.modifyid = id;
		}else{
			$scope.modifyid = 0
		}
	}
	$scope.addTaxrateElement = function(id){
		$scope.taxratesaved = false;
		$scope.taxrates.push({idtaxrate:0});
	}
	$scope.deleteTaxrateElement = function(id){
		for(var i=0;i<$scope.taxrates.length;i++){
			if (id == $scope.taxrates[i].idtaxrate){
				$scope.deleteTaxrate = $scope.taxrates[i];
				$.ajax({
						url:GECO_LOGGEDUSER.getSecondDomain()+"rest/basic/taxrate/",
						type:"DELETE",
						data:"taxrateobj="+JSON.stringify($scope.deleteTaxrate),
						success:function(data){
								$http.get('rest/basic/taxrate').success(function(data){
										$scope.taxrates= data;
										$scope.$apply();
								});
								
						}	
					})
			}	
		}
	}
	$scope.saveTaxrates = function(){
		$.ajax({
			url:GECO_LOGGEDUSER.getSecondDomain()+"rest/basic/taxrate",
			type:"PUT",
			data:"taxrates="+JSON.stringify($scope.taxrates),
			success:function(data){
				result = JSON.parse(data);
				if (result.type == "success"){	
					$scope.taxratesaved = true;
					$scope.modifyid = 0;
					$scope.$apply();
					$rootScope.confirmSaved();
				}else{
					alert("Errore: "+result.errorName+" Messaggio:"+result.errorMessage);
				}	
					
			},
			error:function(date){
				alert("Errore nel salvataggio");
			}
			
		})
		
	}
	
	
}]);

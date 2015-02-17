/**
 * 
 */
angular.module("rocchi.promoter")
.controller('RocchiPromoterListCtrl',["$scope","$http","$rootScope","$location","AppConfig",function($scope, $http, $rootScope, $location,AppConfig) {
	$scope.location = $location;
	$http.get(AppConfig.ServiceUrls.Promoter).success(
					function(data) {
						$scope.promoters = data;
						
					});
	$scope.deleteElement = function(id) {
		for (var i = 0; i < $scope.promoters.length; i++) {
			if (id == $scope.promoters[i].idPromoter) {
				$scope.deletepromoter = $scope.promoters[i];
				$.ajax({
							url : AppConfig.ServiceUrls.Promoter,
							type : "DELETE",
							data : "promoterobj="+ JSON.stringify($scope.deletepromoter),
							success : function(data) {
								alert("Promoter eliminato con successo");
								$http.get(AppConfig.ServiceUrls.Promoter)
										.success(
												function(
														data) {
													$scope.customers = data;
												});
							}
						})
			}
		}
	}
	

} ])
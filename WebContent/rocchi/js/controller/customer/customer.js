/**
 * 
 */
angular
		.module("rocchi.customer")
		.controller(
				'RocchiCustomerListCtrl',
				[
						"$scope",
						"$http",
						"$rootScope",
						"$location",
						"AppConfig",
						function($scope, $http, $rootScope, $location,
								AppConfig) {
							// $scope.loginuser =
							// GECO_LOGGEDUSER.checkloginuser();
							$scope.location = $location;
							if ($scope.pagesize == null) {
								$scope.pagesize = 99;
								// ScopeFactory.factory.productList.pagesize =
								// 100;
							} else {
								// $scope.pagesize =
								// ScopeFactory.factory.productList.pagesize
							}

							$scope.pageArray = [];
							if ($scope.showFilter == null) {
								$scope.showFilter = false;
							}
							if ($scope.filterCustomer == null) {
								$scope.filterCustomer = {
									"pagefilter" : {}
								};
								$scope.currentCustomerGroup = {};
								$scope.currentBrand = {};
								$scope.currentCategory = {};
								$scope.currentSubCategory = {};
								$scope.currentSupplier = {};
							} else {
								$scope.currentCustomerGroup = $scope.filterCustomer.group;
								$scope.currentBrand = $scope.filterCustomer.brand;
								$scope.currentCategory = $scope.filterCustomer.category;
								$scope.currentSubCategory = $scope.filterCustomer.subcategory;
								$scope.currentSupplier = $scope.filterCustomer.supplier;
							}
							$http
									.get(AppConfig.ServiceUrls.CustomerGroup)
									.success(
											function(data) {
												$scope.groups = data;
												if ($scope.currentGroup != null) {
													for (var i = 0; i < $scope.groups.length; i++) {
														if ($scope.currentCustomerGroup.idGroupCustomer == $scope.groups[i].idGroupCustomer) {
															$scope.currentCustomerGroup = $scope.groups[i];
														}
													}
												}
											});
							$http
									.get('rest/basic/categorycustomer')
									.success(
											function(data) {
												$scope.categorys = data;
												if ($scope.currentCategory != null) {
													for (var i = 0; i < $scope.categorys.length; i++) {
														if ($scope.currentCategory.idCategoryCustomer == $scope.categorys[i].idCategoryCustomer) {
															$scope.currentCategory = $scope.categorys[i];
														}
													}
												}
											});
							$scope.getCustomers = function() {
								$scope.filterCustomer.pagefilter.startelement = (1 - 1)
										* $scope.pagesize;
								$scope.filterCustomer.pagefilter.pageSize = $scope.pagesize;
								$rootScope.filterCustomer = $scope.filterCustomer
								$
										.ajax({
											url : AppConfig.ServiceUrls.ListOfCustomer,
											type : "POST",
											data : "filter="
													+ JSON
															.stringify($scope.filterCustomer),
											success : function(data) {

												$scope.customers = JSON
														.parse(data);

												$scope.$apply();
											}
										})
							}
							$scope.getCustomers();

							$scope.deleteElement = function(id) {
								for (var i = 0; i < $scope.customers.length; i++) {
									if (id == $scope.customers[i].idCustomer) {
										$scope.deletecustomer = $scope.customers[i];
										$
												.ajax({
													url : "rest/registry/customer/",
													type : "DELETE",
													data : "customerobj="
															+ JSON
																	.stringify($scope.deletecustomer),
													success : function(data) {
														alert("Utente eliminato con successo");
														$http
																.get(
																		'rest/registry/customer')
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
							$scope.printElements = function() {
								$http.get('rest/print').success(function(data) {
									window.open(data, '_blank');
								});
							}

						} ])
		.controller(
				'RocchiCustomerDetailCtrl',
				[
						"$scope",
						"$http",
						"$routeParams",
						"AppConfig",
						"AlertsFactory",
						function($scope, $http, $routeParams, AppConfig,
								AlertsFactory) {
							// GECO_LOGGEDUSER.checkloginuser();
							$scope.listsections = [ "Dettagli", "Listini",
									"Destinazioni" ];
							$scope.msg = AlertsFactory;
							$scope.msg.initialize();
							$scope.selectedSection = $scope.listsections[0];
							GECO_validator.startupvalidator();
							$scope.idcustomer = $routeParams.idcustomer;
							/*
							 * $http.get('rest/registry/list').success(function(data){
							 * $scope.lists= data; });
							 * $http.get('rest/basic/payment').success(function(data){
							 * $scope.payments= data; if ($scope.customer !=
							 * null && $scope.customer.payment != null){ for(var
							 * i=0;i<$scope.payments.length;i++ ){ if
							 * ($scope.customer.payment.idPayment ==
							 * $scope.payments[i].idPayment){
							 * $scope.currentPayment = $scope.payments[i]; } } }
							 * });
							 */ $http.get(AppConfig.ServiceUrls.CustomerCategory).success(function(data){
							  $scope.categorys= data; 
							  });
							 
							$http.get(AppConfig.ServiceUrls.CustomerGroup)
									.success(function(data) {
										$scope.groups = data;
									});
							$http.get(AppConfig.ServiceUrls.DetailsOfCustomer+ $scope.idcustomer)
									.success(
											function(data) {
												$scope.customer = data;
												if ($scope.groups !== null
														&& $scope.groups !== undefined) {
													for (var i = 0; i < $scope.groups.length; i++) {
														if ($scope.customer.group != null) {
															if ($scope.customer.group.idGroupCustomer == $scope.groups[i].idGroupCustomer) {
																$scope.currentGroup = $scope.groups[i];
															}
														} else {
															$scope.customer.group = {}
														}
													}
												}
												if ($scope.categorys !== null
														&& $scope.categorys !== undefined) {
													for (var ig = 0; ig < $scope.categorys.length; ig++) {
														if ($scope.customer.category != null) {
															if ($scope.customer.category.idCategoryCustomer == $scope.categorys[ig].idCategoryCustomer) {
																$scope.currentCategory = $scope.categorys[ig];
															}
														} else {
															$scope.customer.category = {};
														}
													}
												}/*
													 * if ($scope.payments !=
													 * null &&
													 * $scope.customer.payment !=
													 * null){ for(var i=0;i<$scope.payments.length;i++ ){
													 * if
													 * ($scope.customer.payment.idPayment ==
													 * $scope.payments[i].idPayment){
													 * $scope.currentPayment =
													 * $scope.payments[i]; } } }
													 * $http.get('rest/basic/taxrate').success(function(data){
													 * $scope.taxrates= data;
													 * for (var itx=0;itx<$scope.taxrates.length;itx++){
													 * if($scope.customer.taxrate){
													 * if
													 * ($scope.customer.taxrate.idtaxrate ==
													 * $scope.taxrates[itx].idtaxrate){
													 * $scope.currentTaxRate =
													 * $scope.taxrates[itx]; } } }
													 * });
													 */
											});
							$scope.getListName = function(list) {
								return list.code + ' ' + list.description + ' '
										+ list.startdate;
							}
							$scope.addListElement = function(customer) {
								customer.lists.push({
									idListCustomer : 0
								});
							}
							$scope.changeListElement = function(list) {

								if (list.list != null) {
									for (var i = 0; i < $scope.lists.length; i++) {
										if (list.list.idList == $scope.lists[i].idList) {
											$scope.currentList = $scope.lists[i];
										}
									}
								}
								$scope.listid = list.idListCustomer
							}
							$scope.saveCustomer = function() {
								$scope.customer.group = $scope.currentGroup;
								$scope.customer.category = $scope.currentCategory;
								$scope.customer.taxrate = $scope.currentTaxRate;
								$scope.msg.initialize();
								$
										.ajax({
											url : AppConfig.ServiceUrls.SaveCustomer,
											type : "PUT",
											data : "customers="
													+ JSON
															.stringify($scope.customer),
											success : function(data) {
												result = JSON.parse(data);
												if (result.type == "success") {
													$scope.customer.idCustomer = result.success;
													$scope.idcustomer = result.success;
													$scope.msg
															.successMessage("CLIENTE SALVATO CON SUCCESSO");
													$scope.$apply();
												} else {
													$scope.msg
															.alertMessage(result.errorMessage);
												}

											},
											error : function(data) {
												$scope.msg.alertMessage(data);
											}
										})
								// }
							};
							$scope.userCustomer = function() {
								$
										.ajax({
											url : "rest/registry/customer/user",
											type : "PUT",
											data : "customers="
													+ JSON
															.stringify($scope.customer)
													+ "&role="
													+ JSON
															.stringify($scope.currentRole),
											success : function(data) {
												result = JSON.parse(data);
												if (result.type == "success") {
													$scope.customer.idCustomer = result.success;
													$scope.idcustomer = result.success;
													$scope.confirmSaved();
													$scope.$apply();
												} else {
													alert("Errore: "
															+ result.errorName
															+ " Messaggio:"
															+ result.errorMessage);
												}
											}
										})
								// }
							};
							$http.get('rest/role/').success(function(data) {
								$scope.roles = data;
							});
						} ]);

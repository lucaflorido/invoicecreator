var gecoControllers = angular.module("gecoControllers",[]);
/**
	LOGIN CONTROLLER
*/
gecoControllers.controller('LoginCtrl',["$scope","$http","$rootScope","$location",function($scope,$http,$rootScope,$location){
	$(".header").css("display","none");
	$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/user/startup').success(function(data){
	});
	$scope.login = {username:"",password:""};
	$scope.loginfunction = function(){
		$.ajax({
			url:GECO_LOGGEDUSER.getSecondDomain()+"rest/user/",
			type:"POST",
			data:"loginobj="+JSON.stringify($scope.login),
			success:function(data){
				var result = $.parseJSON(data);
				if (result.type == 'success'){
					result = result.success;
					if (result.username !== null && result.username != ""){
						$(".myprofilelabel").html(result.username);
						$rootScope.user = result;
						$rootScope.path = result.path;
						$(".header").css("display","");
						$location.path('/welcome');
						$scope.$apply();
					}else{
						
					}
				}else{
					$scope.errorMessage(result.errorMessage);
				}
			}	
		})
	};
	$(".loginbutton").click(function(e){
		$scope.loginfunction();
	});
	
	$scope.loginout = function(){
		$.ajax({
			url:GECO_LOGGEDUSER.getSecondDomain()+"rest/user/logout/",
			type:"GET",
			success:function(data){
					$(".header").css("display","none");
					$(window.location).attr('href', '#/login');
			}	
		})
	}
	$("#logoutbutton").click(function(e){
		$scope.loginout();
	})
	$("#enterpassword").bind('keypress', function(e) {
		var code = e.keyCode || e.which;
		 if(code == 13) { //Enter keycode
		   //Do something
		   $scope.loginfunction();
		 }
	});
	
	$(".redobutton").click(function(e){
		alert($("ifname").attr("required"));
	})
	/*$http.get('rest/user').success(function(data){
		$scope.users= data;
	});*/
	
	
	
}]);
gecoControllers.controller('WelcomeCtrl',['$scope',function($scope){
	GECO_LOGGEDUSER.checkloginuser();
	
}]);
gecoControllers.controller('UserListCtrl',["$scope","$http",function($scope,$http){
    $scope.loginuser = GECO_LOGGEDUSER.checkloginuser();
	$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/user').success(function(data){
		$scope.users= data;
	});
	$scope.deleteElement = function(id){
		for(var i=0;i<$scope.users.length;i++){
			if (id == $scope.users[i].iduser){
				$scope.deleteUser = $scope.users[i];
				$.ajax({
						url:GECO_LOGGEDUSER.getSecondDomain()+"rest/user/",
						type:"DELETE",
						data:"userobj="+JSON.stringify($scope.deleteUser),
						success:function(data){
							alert("Utente eliminato con successo");
							$http.get('rest/user').success(function(data){
								$scope.users= data;
							});
						}	
					})
			}	
		}
	}
	$scope.printElements = function(){
		$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/print').success(function(data){
					window.open(data, '_blank');
		});
	}
	
}]);

gecoControllers.controller('UserDetailCtrl',['$scope', '$routeParams','$http',function($scope,$routeParams,$http){
	GECO_LOGGEDUSER.checkloginuser();
	GECO_validator.startupvalidator();
	$scope.userId= $routeParams.userId ;
	
	$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/role/').success(function(data){
		$scope.roles= data;
		$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/user/'+$scope.userId).success(function(data){
			$scope.user= data;
			for (var i=0;i<$scope.roles.length;i++){
				if($scope.roles[i].idrole == $scope.user.role.idrole){
					$scope.currentRole = $scope.roles[i];
				}
			}
			$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/registry/companylist').success(function(data){
				$scope.companies = data;
				for (var i=0;i<$scope.companies.length;i++){
				if($scope.user && $scope.user.company !== undefined && $scope.user.company !== null && $scope.companies[i].idCompany == $scope.user.company.idCompany){
					$scope.currentCompany = $scope.companies[i];
				}
			}
			});
		});
	});
	
	$(".savebutton").click(function(e){
	    if (GECO_validator.requiredFields()== true && GECO_validator.emailFields()==true){
			$scope.user.role = $scope.currentRole;
			$.ajax({
				url:GECO_LOGGEDUSER.getSecondDomain()+"rest/user/",
				type:"PUT",
				data:"loginobj="+JSON.stringify($scope.user),
				success:function(data){
					alert("success");
				}	
			})
		}
	} );
	
}]);

gecoControllers.controller('MyProfileCtrl',['$scope', '$routeParams','$http',function($scope,$routeParams,$http){
	GECO_LOGGEDUSER.checkloginuser();
	$scope.myuserId= $routeParams.myuserId ;
	
	$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/role/').success(function(data){
		$scope.roles= data;
		$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/user/'+$scope.myuserId).success(function(data){
			$scope.user= data;
			for (var i=0;i<$scope.roles.length;i++){
				if($scope.roles[i].idrole == $scope.user.role.idrole){
					$scope.currentRole = $scope.roles[i];
				}
			}
		});
	});
	
	$(".savebutton").click(function(e){
		$scope.user.role = $scope.currentRole;
		$.ajax({
			url:GECO_LOGGEDUSER.getSecondDomain()+"rest/user/",
			type:"PUT",
			data:"loginobj="+JSON.stringify($scope.user),
			success:function(data){
				alert("success");
			}	
		})
	} );
	$(".changepasswordbutton").click(function(e){
		$scope.user.password = $("#oldpassword").val();
		$scope.user.newpassword = $("#newpassword").val();
		$.ajax({
			url:GECO_LOGGEDUSER.getSecondDomain()+"rest/user/changepassword/",
			type:"PUT",
			data:"userobj="+JSON.stringify($scope.user),
			success:function(data){
				alert("success");
			}	
		})
		
	} );
}]);
/*****
ROLE
***/
gecoControllers.controller('RoleCtrl',["$scope","$http",function($scope,$http){
    $scope.loginuser = GECO_LOGGEDUSER.checkloginuser();
	$scope.rolesaved = true;
	$http.get(GECO_LOGGEDUSER.getSecondDomain()+'rest/role').success(function(data){
		$scope.roles= data;
	});
	$scope.modifyid = 0;
	$scope.modifyRoleElement = function(id){
		$scope.modifyid = id;
	}
	$scope.addRoleElement = function(id){
		$scope.rolesaved = false;
		$scope.roles.push({idrole:0});
	}
	$scope.deleteRoleElement = function(id){
		for(var i=0;i<$scope.roles.length;i++){
			if (id == $scope.roles[i].idrole){
				$scope.deleteRole = $scope.roles[i];
				$.ajax({
						url:GECO_LOGGEDUSER.getSecondDomain()+"rest/role/",
						type:"DELETE",
						data:"roleobj="+JSON.stringify($scope.deleteRole),
						success:function(data){
								$http.get('rest/role').success(function(data){
										$scope.roles= data;
								});
								
						}	
					})
			}	
		}
	}
	$scope.saveRoles = function(){
		$.ajax({
			url:GECO_LOGGEDUSER.getSecondDomain()+"rest/role/",
			type:"PUT",
			data:"roles="+JSON.stringify($scope.roles),
			success:function(data){
					$http.get('rest/role').success(function(data){
										$scope.roles= data;
										$scope.rolesaved = true;
										$scope.modifyid = 0;
								});
					
			}	
		})
		
	}
	
	
}]);



/*****
TAXRATE
**
gecoControllers.controller('TaxrateCtrl',["$scope","$http",function($scope,$http){
    $scope.loginuser = GECO_LOGGEDUSER.checkloginuser();
	$scope.taxratesaved = true;
	$http.get('rest/basic/taxrate').success(function(data){
		$scope.taxrates= data;
	});
	$scope.modifyid = 0;
	$scope.modifyTaxrateElement = function(id){
		$scope.modifyid = id;
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
						url:"rest/basic/taxrate/",
						type:"DELETE",
						data:"taxrateobj="+JSON.stringify($scope.deleteTaxrate),
						success:function(data){
								$http.get('rest/basic/taxrate').success(function(data){
										$scope.taxrates= data;
								});
								
						}	
					})
			}	
		}
	}
	$scope.saveTaxrates = function(){
		$.ajax({
			url:"rest/basic/taxrate",
			type:"PUT",
			data:"taxrates="+JSON.stringify($scope.taxrates),
			success:function(data){
					$http.get('rest/basic/taxrate').success(function(data){
										$scope.taxrates= data;
										$scope.taxratesaved = true;
										$scope.modifyid = 0;
								});
					
			}	
		})
		
	}
	
	
}]);*/

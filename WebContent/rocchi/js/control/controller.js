var gecoControllers = angular.module("gecoControllers",[]);
/**
	LOGIN CONTROLLER
*/
gecoControllers.controller('LoginCtrl',["$scope","$http","$rootScope","$location","PermissionFactory","AlertsFactory","AppConfig","$cookies",function($scope,$http,$rootScope,$location,PermissionFactory,AlertsFactory,AppConfig,$cookies){
	$rootScope.viewheader = false;
	$scope.msg = AlertsFactory;
	$scope.msg.initialize();
	$scope.step = 0;
	/*$http.get('/InvoiceCreator/rest/user/startup').success(function(data){
	});*/
	if (!navigator.cookieEnabled){
		 alert("Attenzione i cookie non sono abilitati...alcune informazioni potrebbero essere perse");
		 $http.post(AppConfig.ServiceUrls.DraftInit,"").success(function(result){
			if(result.type == "success"){
				$scope.draft = result.success
				$http.post(AppConfig.ServiceUrls.ProductPublic,AppConfig.Const.CompanyId).success(function(result){
					$scope.prodlist = result;
				});
			}
		 });
	}else{
		var cookie = $cookies.get("PROChart");
		$http.post(AppConfig.ServiceUrls.DraftInit,cookie).success(function(result){
			if(result.type == "success"){
				$scope.draft = result.success
				$cookies.put("PROChart",$scope.draft.id);
				$http.post(AppConfig.ServiceUrls.ProductPublic,AppConfig.Const.CompanyId).success(function(result){
					$scope.prodlist = result;
					
				});
			}
		});
	}
	
	$scope.loginfunction = function(){
		$http.post(AppConfig.ServiceUrls.Login,$scope.login).success(function(result){
			
			if (result.type == 'success'){
				result = result.success;
				if (result.username !== null && result.username != ""){
					$(".myprofilelabel").html(result.username);
					$rootScope.user = result;
					$rootScope.path = result.path;
					PermissionFactory.setupPermission(result.path);
					$rootScope.viewheader = true;
					$scope.msg.initialize();
					$location.path('/welcome');
					
				}else{
					
				}
			}else{
				$scope.msg.alertMessage(result.errorMessage);
			}
		}).error(function(error){
			$scope.msg.alertMessage(AppConfig.Const.ServerProblem)
		});
		
	};
	$scope.addToChart = function(product){
		$http.post(AppConfig.ServiceUrls.DraftAdd+$scope.draft.id,product).success(function(result){
			if(result.type == "success"){
				$scope.draft = result.success;
				product.added= true;
			}
		});
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
gecoControllers.controller('WelcomeCtrl',['$scope','$rootScope','$location',function($scope,$rootScope,$location){
	GECO_LOGGEDUSER.checkloginuser();
	$scope.location = $location;
	$scope.logout = function(){
		$.ajax({
			url:"rest/user/logout/",
			type:"GET",
			success:function(data){
					$(".header").css("display","none");
					$location.path('/login');
			}	
		})
	}

}]);
gecoControllers.controller('ParametersCtrl',['$scope','$rootScope','$location',function($scope,$rootScope,$location){
	
}]);
gecoControllers.controller('UserListCtrl',["$scope","$http",function($scope,$http){
    $scope.loginuser = GECO_LOGGEDUSER.checkloginuser();
	$http.get('rest/user').success(function(data){
		$scope.users= data;
	});
	$scope.deleteElement = function(id){
		for(var i=0;i<$scope.users.length;i++){
			if (id == $scope.users[i].iduser){
				$scope.deleteUser = $scope.users[i];
				$.ajax({
						url:"rest/user/",
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
		$http.get('rest/print').success(function(data){
					window.open(data, '_blank');
		});
	}
	
}]);

gecoControllers.controller('UserDetailCtrl',['$scope', '$routeParams','$http',function($scope,$routeParams,$http){
	GECO_LOGGEDUSER.checkloginuser();
	GECO_validator.startupvalidator();
	$scope.userId= $routeParams.userId ;
	
	$http.get('rest/role/').success(function(data){
		$scope.roles= data;
		$http.get('rest/user/'+$scope.userId).success(function(data){
			$scope.user= data;
			for (var i=0;i<$scope.roles.length;i++){
				if($scope.roles[i].idrole == $scope.user.role.idrole){
					$scope.currentRole = $scope.roles[i];
				}
			}
		});
	});
	
	$(".savebutton").click(function(e){
	    if (GECO_validator.requiredFields()== true && GECO_validator.emailFields()==true){
			$scope.user.role = $scope.currentRole;
			$.ajax({
				url:"rest/user/",
				type:"PUT",
				data:"loginobj="+JSON.stringify($scope.user),
				success:function(data){
					alert("success");
				}	
			})
		}
	} );
}]);

gecoControllers.controller('MyProfileCtrl',['$scope', '$routeParams','$http','ModalFactory',function($scope,$routeParams,$http,ModalFactory){
	GECO_LOGGEDUSER.checkloginuser();
	$scope.myuserId= $routeParams.myuserId ;
	$scope.isuser = true;
	$http.get('rest/role/').success(function(data){
		$scope.roles= data;
		$http.get('rest/user/'+$scope.myuserId).success(function(data){
			$scope.user= data;
			for (var i=0;i<$scope.roles.length;i++){
				if($scope.roles[i].idrole == $scope.user.role.idrole){
					$scope.currentRole = $scope.roles[i];
				}
			}
		});
	});
	
	$scope.saveuser = function(){
		$scope.user.role = $scope.currentRole;
		$.ajax({
			url:"rest/user/",
			type:"PUT",
			data:"loginobj="+JSON.stringify($scope.user),
			success:function(data){
				$scope.confirmSaved();
			}	
		})
	};
	$scope.changepassword = function(){
		$scope.user.password = $("#oldpassword").val();
		$scope.user.newpassword = $("#newpassword").val();
		$.ajax({
			url:"rest/user/changepassword/",
			type:"PUT",
			data:"userobj="+JSON.stringify($scope.user),
			success:function(data){
				$scope.confirmSaved();
			}	
		})
		
	};
	$scope.changeView = function(){
		$scope.isuser = !$scope.isuser;
	}
	$scope.addMilConfig = function(){
		if ($scope.user.company.mailconfig == undefined || $scope.user.company.mailconfig == null ){
			$scope.user.company.mailconfig = [];
		}
		$scope.user.company.mailconfig.push({});
	}
	$scope.testemail = function(mailconfig){
		ModalFactory.sendMail($scope.user,mailconfig,$scope.emailService);
		
		
	}
	$scope.emailService = function(user,mailconfig){
		$.ajax({
			url:"rest/email/test/",
			type:"POST",
			data:"user="+JSON.stringify(user)+"&mailconfig="+JSON.stringify(mailconfig),
			success:function(data){
				
				var results = JSON.parse(data);
				if (results.type == "success"){	
						$scope.products = results.success;
						$scope.$apply();
						$scope.errorMessage("Verificare ricezione email");
					}else{
						$scope.errorMessage("Errore: "+results.errorName+" Messaggio:"+results.errorMessage);
					}	
			}	
		});
	};
}]);
/*****
ROLE
***/
gecoControllers.controller('RoleCtrl',["$scope","$http",function($scope,$http){
    $scope.loginuser = GECO_LOGGEDUSER.checkloginuser();
	$scope.rolesaved = true;
	$http.get('rest/role').success(function(data){
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
						url:"rest/role/",
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
			url:"rest/role/",
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
LOGOUT
***/
gecoControllers.controller('StartupCtrl',["$scope","$rootScope","$http","$location","AppConfig","PermissionFactory","LoaderFactory",function($scope,$rootScope,$http,$location,AppConfig,PermissionFactory,LoaderFactory){
    $rootScope.viewheader = false;
    $scope.auth = PermissionFactory;
    $scope.loader = LoaderFactory;
    $scope.conf_permission = AppConfig.Permissions;
	$scope.logout = function(){
		$http.get(AppConfig.ServiceUrls.Logout).then(
				function(result){
					$rootScope.viewheader = false;
					$location.path('/login');
				}
		);
		
	}
	$http.get(AppConfig.ServiceUrls.CheckUser).then(
			function(result){
				if (result.data.username != "" && result.data.username != null){
					//checkrole(result);
					
					$rootScope.viewheader = true;
				}else{
					
					$location.path('/login');
					
				}
			}
	);
	$scope.loginfunction = function(){
		$http.post(AppConfig.ServiceUrls.Login,$scope.login).success(function(result){
			
			if (result.type == 'success'){
				result = result.success;
				if (result.username !== null && result.username != ""){
					$(".myprofilelabel").html(result.username);
					$rootScope.user = result;
					$rootScope.path = result.path;
					PermissionFactory.setupPermission(result.path);
					$rootScope.viewheader = true;
					//$scope.msg.initialize();
					$location.path('/welcome');
					
				}else{
					
				}
			}else{
				$scope.msg.alertMessage(result.errorMessage);
			}
		}).error(function(error){
			$scope.msg.alertMessage(AppConfig.Const.ServerProblem)
		});
		
	};
	
}]);



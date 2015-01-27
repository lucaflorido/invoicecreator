var gecoControllers = angular.module("gecoControllers",[]);
/**
	LOGIN CONTROLLER
*/
gecoControllers.controller('LoginCtrl',["$scope","$http","$rootScope","$location",function($scope,$http,$rootScope,$location){
	$(".header").css("display","none");
	$http.get('rest/user/startup').success(function(data){
	});
	$scope.login = {username:"",password:""};
	$scope.loginfunction = function(){
		$.ajax({
			url:"rest/user/",
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
	/*$(".loginbutton").click(function(e){
		$scope.loginfunction();
	});*/
	
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
gecoControllers.controller('LogOutCtrl',["$scope","$http",function($scope,$http){
    
	// scope per il logout
	
}]);



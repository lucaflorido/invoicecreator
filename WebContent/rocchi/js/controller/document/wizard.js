/**
 * 
 */
/**
 * 
 */
angular.module("rocchi.documents")
 .controller('RocchiWizardCtrl',["$scope","$http","$stateParams","$location","$rootScope","$state","AppConfig","WizardFactory",function($scope,$http,$stateParams,$location,$rootScope,$state,AppConfig,WizardFactory){
	 $scope.tabs=[{title:"Cliente",template:"template/document/wizard/stepone.html",name:"step1",active:true,disable:false},
		             {title:"Prodotto",template:"template/document/wizard/steptwo.html",name:"step2",active:false,disable:true}
		             
		             ];
		$scope.wiz = WizardFactory;
		$scope.wiz.initialize($scope.tabs);
		$scope.wiz.getCustomers();
		
		$scope.setSelection = function(tab){
			$scope.wiz.goToStepByName(tab.name);
			
		}
		$scope.head = {};
		$scope.gotoStep2 = function(){
			$scope.wiz.head.list = $scope.wiz.head.customer.lists[0].list;
			$scope.wiz.goToStep(1);
			
			
		}
		$scope.gotoStep3 = function(){
			$scope.wiz.saveHead().then(function(){
				$scope.wiz.goToStep(2);
			});
				
		}
		$scope.changeCustomer = function(customer){
			$http.get(AppConfig.ServiceUrls.DetailsOfCustomer+ customer.idCustomer).success(function(data) {
				$scope.wiz.head.customer = data;
			});
		}
		
}]).controller("WizardStepsCtrl",["$scope","WizardFactory",function($scope,WizardFactory){
	$scope.wiz = WizardFactory;
	//factory.wiz.getCustomers();
}]).factory("WizardFactory",["$http", "$q","AppConfig","$state",function($http, $q,AppConfig,$state){
	var factory = {};
	factory.customerlist = [];
	factory.productlist = [];
	factory.head = {};
	factory.tabs = [];
	factory.selected = "";
	factory.globalstatus = "";
	factory.STATUS_ERROR = 3;
	factory.STATUS_WARNING = 2;
	factory.STATUS_ADDED = 1;
	factory.STATUS_EMPTY = 0;
	factory.opencart = false;
	factory.warningProd = [];
	factory.errorProd = [];
	var makeinactivetabs = function(){
		for(var i=0;i <factory.tabs.length;i++){
			factory.tabs[i].active = false;
		}
	}
	var getTabIndexByName = function(name){
		for(var i=0;i <factory.tabs.length;i++){
			if (factory.tabs[i].name == name){
				return i;
			}
		}
	}
	var getTabNameByIndex = function(index){
		
			return factory.tabs[index].name;
				
	}
	factory.getTabByName = function(name){
		for(var i=0;i <factory.tabs.length;i++){
			if (factory.tabs[i].name == name){
				return factory.tabs[i];
			}
		}
	}
	factory.initialize = function(t){
		factory.tabs = t;
		factory.selected = getTabNameByIndex(0);
		factory.head = {};
		factory.customerlist = [];
		factory.productlist = [];
		factory.opencart = false;
		factory.globalstatus = 0;
	}
	factory.getTab = function(index){
		return factory.tabs[index];
	}
	factory.goToStep = function(index){
		makeinactivetabs();
		var t = factory.getTab(index);
		t.active = true;
		t.disable = false;
		factory.selected = t.name;
	}
	factory.goToStepByName = function(name){
		
		var t = factory.getTabByName(name);
		t.active = true;
		t.disable = false;
		factory.selected = t.name;
	}
	factory.getCustomers = function(){
		var deferred = $q.defer();
		$http.get(AppConfig.ServiceUrls.ListOfCustomer).then(function(result){
			factory.customerlist = result.data;
			deferred.resolve();
		});
		return deferred.promise;
	}
	factory.getProducts = function(value){
		var deferred = $q.defer();
		$http.post(AppConfig.ServiceUrls.SearchProduct+value,factory.head).then(function(result){
			factory.productlist = result.data;
			if (factory.head.rows && factory.head.rows.length > 0){
				factory.globalstatus = 1;
			}else{
				factory.globalstatus = 0;
			}
			factory.errorProd = [];
			deferred.resolve();
		});
		return deferred.promise;
	}
	factory.addProduct = function(prod){
		if (prod.status == factory.STATUS_WARNING){
			var addrow = {"um":prod,"h":factory.head};	
			$http.post(AppConfig.ServiceUrls.AddRow,addrow).then(function(result){
				factory.head = result.data.success;
				prod.status = factory.STATUS_ADDED ;
				factory.warningProd = $.grep(factory.warningProd,function(a){return a !== prod.idUnitMeasureProduct});
				if(factory.errorProd.length == 0 && factory.warningProd.length ==0){
					factory.globalstatus = prod.status;
				}
				
				
			},function(result){
				prod.status = factory.STATUS_ERROR;
				 factory.setGlobalStatus(prod.status);
				 factory.warningProd = $.grep(factory.warningProd,function(a){return a !== prod.idUnitMeasureProduct});
				 factory.errorProd.push(prod.idUnitMeasureProduct);
			});
		}
		
	}
	factory.quantityChange = function(prod){
		
		 if (isNaN(prod.quantity)) 
		  {
			 prod.status = factory.STATUS_ERROR;
			 factory.setGlobalStatus(prod.status);
			 factory.warningProd = $.grep(factory.warningProd,function(a){return a !== prod.idUnitMeasureProduct});
			 factory.errorProd.push(prod.idUnitMeasureProduct);
		  }else{
			  prod.status = factory.STATUS_WARNING ;
			  factory.setGlobalStatus(prod.status);
			  factory.errorProd = $.grep(factory.errorProd,function(a){return a !== prod.idUnitMeasureProduct});
			  factory.warningProd.push(prod.idUnitMeasureProduct);
			  if(factory.errorProd.length == 0 ){
					factory.globalstatus = factory.STATUS_WARNING;
			  }
		  }
	}
	factory.saveHead = function(){
		var deferred = $q.defer();
		$http.put(AppConfig.ServiceUrls.AddRow,factory.head).then(function(result){
			factory.head = result.data.success;
			deferred.resolve();
		});
		return deferred.promise;
	}
	factory.setGlobalStatus = function(newStatus){
		if (newStatus > factory.globalstatus){
			factory.globalstatus = newStatus;
		}
	}
	factory.openChart = function(){
		factory.opencart = true;
	}
	factory.closeChart = function(){
		if (factory.head.idHead > 0){
			factory.initialize(factory.tabs)
		}
		factory.opencart = false;
	}
	factory.reload = function(){
		factory.initialize(factory.tabs)
		$state.reload();
	}
	factory.printHead = function(){
		$http.get(AppConfig.ServiceUrls.PrintHead+factory.head.idHead).then(function(result){
			var deviceAgent = navigator.userAgent;
			var ios = deviceAgent.toLowerCase().match(/(iphone|ipod|ipad|android|webos|blackberry|iemobile|opera mini)/);
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				// some code..
			}
			if (ios) {
				// This is the line that matters
				$scope.openTab(result.data.url);
			} else {
				// Your code that works for desktop browsers
				var url = result.data.url;
				window.open(url);
			}
		});
	};
	factory.calculateRow = function(row){
		if (row.quantity != "" && row.quantity != null && isNaN(row.quantity) == false ){
			var totrowobj = {};
			totrowobj.qta = row.quantity;
			totrowobj.taxrate = row.product.taxrate.value;
			totrowobj.price = row.price;
			$http.post(AppConfig.ServiceUrls.RowTotal,totrowobj).then(function(result){
				var totrow = result.data;
				row.total = totrow.total;
				row.amount = totrow.amount;
				row.taxamount = totrow.taxamount;
				factory.calculateTotal();
			});
			/*$.ajax({
					url:"rest/documenthelp/rowtotal",
					type:"POST",
					data:"row="+JSON.stringify($scope.totrowobj),
					success:function(data){
						$scope.totrow = $.parseJSON(data);
						row.total = $scope.totrow.total;
						row.amount = $scope.totrow.amount;
						row.taxamount = $scope.totrow.taxamount;
						$scope.$apply();
						$scope.calculateTotal();
					}	
				});*/
		}
	}
	factory.deleteElement = function(row){
		if (row.idRow == 0){
			factory.head.rows = $.grep(factory.head.rows,function(a){  return a.idRow != row.idRow && a.productcode != row.productcode ;})
			
			$scope.$apply();
		}else{
			$http.delete(AppConfig.ServiceUrls.DeleteRow+row.idRow).then(function(result){
				if (result.data.type == "success"){	
					factory.head.rows = $.grep(factory.head.rows,function(a){  return a.idRow != row.idRow && a.productcode != row.productcode ;})
					
				}else{
					alert("Errore: "+result.data.errorName+" Messaggio:"+result.data.errorMessage);
				}
			});
			/*$.ajax({
				url:AppConfig.ServiceUrls.DeleteRow+row.idRow,
				type:"DELETE",
				success:function(data){
					result = JSON.parse(data);
					if (result.type == "success"){	
							$scope.head.rows = $.grep($scope.head.rows,function(a){  return a != $rootScope.deleteObj;})
							$scope.currentRow = null;
							$scope.$apply();
							
						}else{
							alert("Errore: "+result.errorName+" Messaggio:"+result.errorMessage);
						}
				}	
			});*/
		}
	}
	factory.calculateTotal = function(){
		var totrowobj = {};
		totrowobj.rows = factory.head.rows;
		$http.post(AppConfig.ServiceUrls.HeadTotal,totrowobj).then(function(result){
			var totrow = result.data;
			factory.head.total =totrow.total;
			factory.head.total4 =totrow.total4;
			factory.head.total10 =totrow.total10;
			factory.head.total20 =totrow.total20;
			factory.head.amount =totrow.amount;
			factory.head.amount4 =totrow.amount4;
			factory.head.amount10 =totrow.amount10;
			factory.head.amount20 =totrow.amount20;
			factory.head.taxamount =totrow.taxamount;
			factory.head.taxamount4 =totrow.taxamount4;
			factory.head.taxamount10 =totrow.taxamount10;
			factory.head.taxamount20 =totrow.taxamount20;
		});
		/*$.ajax({
				url:"rest/documenthelp/headtotal",
				type:"POST",
				data:"head="+JSON.stringify($scope.totrowobj),
				success:function(data){
					$scope.totrow = $.parseJSON(data);
					$scope.head.total =$scope.totrow.total;
					$scope.head.total4 =$scope.totrow.total4;
					$scope.head.total10 =$scope.totrow.total10;
					$scope.head.total20 =$scope.totrow.total20;
					$scope.head.amount =$scope.totrow.amount;
					$scope.head.amount4 =$scope.totrow.amount4;
					$scope.head.amount10 =$scope.totrow.amount10;
					$scope.head.amount20 =$scope.totrow.amount20;
					$scope.head.taxamount =$scope.totrow.taxamount;
					$scope.head.taxamount4 =$scope.totrow.taxamount4;
					$scope.head.taxamount10 =$scope.totrow.taxamount10;
					$scope.head.taxamount20 =$scope.totrow.taxamount20;
					$scope.addRow=true;
					$scope.$apply();
				}	
			})*/
	}
	return factory;
	
}]);


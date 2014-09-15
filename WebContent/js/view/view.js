var AO_view = function(){

		function openCloseMenu(){
			var cssClass = $("#subnavigator_components").css("display");
			if (cssClass == "none"){
				$("#subnavigator_components").css("display","")
				$(".main-container").removeClass("close");
				$(".main-container").addClass("open");
			}else{
				$("#subnavigator_components").css("display","none")
				$(".main-container").removeClass("open");
				$(".main-container").addClass("close");
			}
		}
		function openPopUpInsert(){
			
			switch(AO_model.selectedArea){
				case "zone":
					createpopup();
					AO_zone.insertPopup();
					$( "#dialog" ).dialog("open")
					break;
				case "TipologiaAgenti":
					createpopup();
					AO_TipologiaAgenti.insertPopup();
					$( "#dialog" ).dialog("open")
					break;
			}
		}
		function mainMenuItemClick(id){
			$(".leaves").css("display","none")
			switch(id){
				case "anagrafiche":
					$(".leaves.anagrafiche").css("display","")
					break;
				case "parametri":
					$(".leaves.parametri").css("display","")
					break;
				case "visite":
					$(".leaves.visite").css("display","")
					break;	
			}
		}
		
	var startupLogin = function(){
		//MENU ITEM CLICK
		$(".menuitem").click(function(e) {
			var id = e.currentTarget.id
			id = id.replace("mi_","");
			AO_model.selectedArea = id;
			AO_controller.selectedMenuItemClick(id)
		});
		$(".root").unbind("click");
		$(".root").click(function(e){
			var id=e.currentTarget.id
			id = id.replace("_menu","");
			mainMenuItemClick(id)
		});
		$(".menudiv").click(function(){
			openCloseMenu();
		});
		$("#closeNavigator").click(function(){
			openCloseMenu();
		});
		$( ".addone" ).click(function() {
			openPopUpInsert();
		});
		//createPopUpIU
	}
	var startup = function(){
		AO_controller.startupLogin();
		//createPopUpIU
	}
	return{
		"startup":startup
	}
}();
(function (){
	AO_view.startup();
})();
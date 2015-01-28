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
				case "tipologiaclienti":
					createpopup();
					AO_clientiTipo.insertPopup();
					$( "#dialog" ).dialog("open")
					break;
				case "statusclienti":
					createpopup();
					AO_clientiStatus.insertPopup();
					$( "#dialog" ).dialog("open")
					break;
				case "collaboratori":
					createpopupTwoColumns();
					AO_collaboratori.insertPopup();
					//$( "#dialog" ).dialog("open")
					break;	
				case "clienti":
					createpopupTwoColumns();
					AO_clienti.insertPopup();
				break;	
				case "statusditte":
					createpopup();
					AO_ditteStatus.insertPopup();
					$( "#dialog" ).dialog("open")
					break;
				case "provvigioni":
					createpopup();
					AO_RegimiProvvigionali.insertPopup();
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
		
	var startupLoggedin = function(){
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
		//$( "#dialog" ).dialog();
		AO_controller.startupLogin();
		//createPopUpIU
	}
	var createpopup = function(){
		$( "#dialog" ).remove();
		$('<div  id="dialog"><div class="dialogTitleBar"><div class="dialogCloseButton"></div></div><div class="dialogContent"><div></div>').appendTo($('body'))
		$( "#dialog" ).dialog( {position: { my: "center", at: "center",of:$('body') } });
		$(".dialogCloseButton").click(function(){
			$( "#dialog" ).dialog("close");
		})
	}
	var createpopupTwoColumns = function(){
		//$( "#dialog" ).remove();
		$("#FormContainer").children().remove();
		$('<div  id="content" class="detail"><div class="detailContent two_columns"><div></div>').appendTo($("#FormContainer"))
		//$( "#dialog" ).dialog({ height:600,width: 600, position: { my: "center", at: "center",of:$('body') } });
		//$(".dialogCloseButton").click(function(){
			//$( "#dialog" ).dialog("close");
		//})
	}
	var createDatePicker = function(datepicker){
		datepicker.datepicker({ dateFormat: "dd/mm/yy",dayNamesMin: [ "Do", "Lu", "Ma", "Me", "Gi", "Ve", "Sa" ] ,firstDay: 1,monthNames: [ "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" ]  });
	}
	return{
		"startup":startup,
		"startupLoggedin":startupLoggedin,
		"createpopup":createpopup,
		"createpopupTwoColumns": createpopupTwoColumns,
		"createDatePicker":createDatePicker
	}
}();
(function (){
	//AO_view.startup();
	AO_login.createMainTemplate()
	AO_view.createpopupTwoColumns();
	AO_clienti.insertPopup();
})();
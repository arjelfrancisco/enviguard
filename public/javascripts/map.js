//script.js capstone

var map;

var polys = [];
var geocoder;
var selectedRegions = [];
var directionsService;
var directionsDisplay;

var patrolRoute = [];
var routeCoordinates = [];

//for latlngbounds
var allRoutes = [];
var latlngbounds;

//for markers
var lstMarkers = [];
var markers = [];
var objMarkers;
var marker;

var lstPatrolIds = []; //list of patrol ids

//for polylines
var poly;

//for datatable
var datatable;
var table;

var infowindow;

var bounds;

//marker
	var small = 0;
	var large = 0;
	
$(function () {
	
	inputChecker();
	
	//hide divs first
	$("#divPatrolId").hide();
	$("#divPatrollerName").hide();
	$("#divPatrolName").hide();
	$("#divDateRange").hide();
	$("#divPatrolStatus").hide();
	$("#patrolDetails").hide();
	$("#divRegions").hide();
	$("#btnView").hide();
	$("#btnClear").hide();
	$("#divInfo").hide();
	$("#btnTest").hide();
		

	$("#btnTest").click(function () {
	
		//
			
window.location.hash = "test";
	
		//$("#modalPic").modal("show");
		
	});

	$("#viewCategory").change(function () {
		$("#divPatrolId").hide();
		$("#divPatrollerName").hide();
		$("#divPatrolName").hide();
		$("#divDateRange").hide();
		$("#divPatrolStatus").hide();
		$("#patrolDetails").hide();
		$("#divRegions").hide();
		
		$("#txtPatrolID").val("");
		$("#txtPatrollerName").val("");
		$("#txtPatrolName").val("");
		$("#txtDateFrom").val("");
		$("#txtDateTo").val("");
		
		
		
		var category = $("#viewCategory").val();
		if(category == "all"){
			$("#btnView").show();
			$("#btnClear").show();
		}
		
		else if(category == "patrolId"){
			$("#divPatrolId").show();
			$("#btnView").show();
			$("#btnClear").show();
		}
		
		else if(category == "patrollerName"){
			$("#divPatrollerName").show();
			$("#btnView").show();
			$("#btnClear").show();
		}
		
		else if(category == "date"){
			$("#divDateRange").show();
			$("#btnView").show();
			$("#btnClear").show();
		}
		
		else if(category == "status"){
			$("#divPatrolStatus").show();
			$("#btnView").show();
			$("#btnClear").show();
		}
		
		else if(category == "region"){
			
			populateRegions();
			$("#divRegions").show();
			$("#btnView").show();
			$("#btnClear").show();
			
		}
		else if(category == "patrolName"){
			$("#divPatrolName").show();
			$("#btnView").show();
			$("#btnClear").show();
		}
		else{
			
		$("#btnView").hide();
		$("#btnClear").hide();
		}
	});
	


	$("#btnView").click(function () {
		clearMarkers();
		allRoutes = [];
		
		for(var i=0; i<polys.length; i++){
			polys[i].setMap(null);
		}
		
		//manage categories
		
		
		var category = $("#viewCategory").val();
		
		if(category == "all"){
			getAllRoutes();
		}
		
		else if(category == "patrolId"){
			getRoutesById();
		}
		
		else if(category == "patrollerName"){
			getRoutesByPatrollerName();
		}
		
		else if(category == "date"){
			getRoutesByDate();
		}
		
		else if(category == "status"){
			
			getRoutesByStatus();
		}
		
		else if(category == "region"){
			//getAllRoutes();
			getRoutesByRegion();
		}
		
		else if(category == "patrolName"){
			getRoutesByPatrolName();
		}
		
	
	});
	



	$("#btnClear").click(function () {
	
		for(var i=0; i<polys.length; i++){
			polys[i].setMap(null);
		}
		clearMarkers();
		if(infowindow){
		infowindow.close();
		}
		
		$("#divInfo").hide();
		
		$("#viewCategory").val("");
		$("#divPatrolId").hide();
		$("#divPatrollerName").hide();
		$("#divPatrolName").hide();
		$("#divDateRange").hide();
		$("#divPatrolStatus").hide();
		$("#patrolDetails").hide();
		$("#divRegions").hide();
	});
});




function inputChecker(){
	
	//$("#txtPatrolID").mask("999999");
	//$("#txtPatrollerName").mask("");
}

function validateInputs(txtFields){
	
	var alertContent = "";
	
	for(var i=0; i<txtFields.length; i++){
		
		if( $("#txtPatrollerName").val() == ""){
		
			alertContent = " Not all fields have a value ";
			
		}
	}
	
	if(alertContent != ""){
		alert(alertContent);
		return false;
	}
	
	
	
	return true;
	
}

function populateRegions(){
	var drpDwn = 'Regions: <select id="viewByRegion"  style="width: 100%;" >' +
					'<option value = "">-- Select --</option>';
		$.ajax({
		url: '/getDistinctRegions',
		type: 'GET',
		
		success: function(data) {
			for(var i=0; i<data.length; i++){
				
				drpDwn += '<option value = ' + data[i] + '>'+ data[i] + '</option>';
			}
			drpDwn += '</select>';
			$("#divRegions").html(drpDwn);
			
		},
		error: function(e) {
			alert("error in /getDistinctRegions");
		}
		
	});
	
}

function getAllRoutes() {
	var category = $("#viewCategory").val();
	
	
	$.ajax({
		url: '/getAllRoutes',
		type: 'GET',
		
		success: function(data) {
			patrolRoute = [];
			if(data == ""){
				alert("No Patrol Routes yet!");
			}
			else{
				
			
			patrolRoute = data;
			manageRoutes(data);
			}
			
			
		},
		error: function(e) {
			alert("error in /getAllRoutes");
		}
		
	});
	
	
}

//diata ginamit
/*
function groupByRegion(routes,region) {
	var latlng;
	
	//a length ng array ng patrol_location
	for(var a=1; a<2; a++){
		
		latlng = {lat: parseFloat(routes[a].lat), lng: parseFloat(routes[a].lng)};
		
		geocoder.geocode({'location': latlng}, function(results, status) {
			
			if (status === google.maps.GeocoderStatus.OK) {
				alert(a);
				if (results[1]) {
					var address = results[1].address_components;
					for(var i=0; i<address.length; i++){
						if(address[i].types[0] == "administrative_area_level_1"){
							
							if(region  == address[i].short_name){
								alert(a);
								patrolRoute.push(routes[a]);
								
							}
						}
					}
					  
			
				} else {
					window.alert('No results found');
				}
				
			} else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {    
            setTimeout(function() {
                groupByRegion(routes,region);
            }, 200);
			} else {
				window.alert('Geocoder failed due to: ' + status);
			}
			
			
		});
		
	}
	
	//alert(JSON.stringify(patrolRoute));
	manageRoutes(patrolRoute);
   
}
*/
function manageRoutes(data){

	console.log("Manage Routes Data: " + JSON.stringify(data));
	var count = 0;
	var contentString = ""; //infowindow
	polys = [];
	
	var flag = true;

	var markerBounds = [];
	
	$.ajax({
		url: '/getPatrolIds',
		type: 'GET',
		
		success: function(patrolIds) {
			
			lstPatrolIds = patrolIds;
			
			
			for(var a = 0; a <= patrolIds.length; a++){
				
				flag = true;
				
				
				
				for(var i = 0; i < data.length; i++){
						
				
					if(patrolIds[a] == data[i].patrolId){
						
						if(flag){
							
							small = data[i].id;
							large = data[i].id;
							flag = false;
						}
							if(data[i].id < small){
								small = data[i].id;
							}
							else if(data[i].id > large){
								large = data[i].id;
							}
						
						
							
						
						
						
					//create polylines per ids
					var pos;
					pos = {lat: data[i].lat, lng: data[i].lng}
					routeCoordinates.push(pos);
					
					pos1 = new google.maps.LatLng(data[i].lat, data[i].lng);
					allRoutes.push(pos1);
					
					//contentString = "Patrol Id: " + data[i].patrolId + "<br>" +
					//				"TimeStamp: " + data[i].timestamp;
					
					
					//generateMarkers(data[i].id,data[i].patrolId,pos,data[i].timestamp,i*200);
					
					
							
					}
							
				}
				
				//alert(small + " - " + large);
				
				
				for(var x=0; x<data.length; x++){
					
					if(data[x].id === small || data[x].id === large ){
					
						var pos;
						pos = {lat: data[x].lat, lng: data[x].lng};
						
						
						generateMarkers(data[x].id,data[x].patrolId,pos,data[x].timestamp,x*200);
					}
				}
				
				color = '#' + ("000000" + Math.random().toString(16).slice(2, 8).toUpperCase()).slice(-6);
				poly = new google.maps.Polyline({
				//editable: true,
				strokeColor: color,
				strokeOpacity: 0.8,
				strokeWeight: 4,
				path: routeCoordinates,
				id: a
						  
				});
				
				if(poly != ""){
					console.log("Poly Generated with id: " + poly.id);
				
				}
				else{
					console.log("Poly is not generated");
				}
				
				polys.push(poly);
				poly.setMap(map);
				
				
				 
				
				
				markerBounds.push(routeCoordinates);
					
				routeCoordinates = [];
				
			
			}
			
			latlngbounds = new google.maps.LatLngBounds();
			
			for(var b=0; b<allRoutes.length; b++){
				latlngbounds.extend(allRoutes[b]);
			}
			
			
			map.setZoom(12);
			map.setCenter(latlngbounds.getCenter());
			map.fitBounds(latlngbounds); 
			
		},
		error: function(e) {
			alert("error in /getPatrolIds");
		}
		
	});
	
}


function getRoutesById(){
	var id = $("#txtPatrolID").val();
	
	$.ajax({
		url: '/getRoutesById/'+ id,
		type: 'GET',
		
		success: function(data) {
			patrolRoute = [];
			patrolRoute = data;
			
			manageRoutes(data);
			
		},
		error: function(e) {
			alert("error in /getRoutesById");
		}
		
	});
}

function getRoutesByPatrollerName() {
	var name = $("#txtPatrollerName").val();
	
	if( name == ""){
		alert("Please input values on patroller name.");
	}
	else{
	
		$.ajax({
			url: '/getRoutesByPatrollerName/'+ name,
			type: 'GET',
			
			success: function(data) {
				
				if(data != ""){
				
				patrolRoute = [];
				patrolRoute = data;
				
				
				manageRoutes(data);
				}
				
				else{
					alert("No Data for: " + name);
				}
				
			},
			error: function(e) {
				alert("error in /getRoutesByPatrollerName");
			}
			
		});
	}
	
}

function getRoutesByPatrolName() {
	var name = $("#txtPatrolName").val();
	if(name == ""){
		
		alert("Please input values on Patrol Name.");
	}
	else{
		
		$.ajax({
			url: '/getRoutesByPatrolName/'+ name,
			type: 'GET',
			
			success: function(data) {
				
				if(data != ""){
				
				patrolRoute = [];
				patrolRoute = data;
				
				
				manageRoutes(data);
				}
				
				else{
					alert("No Data for: " + name);
				}
				
			},
			error: function(e) {
				alert("error in /getRoutesByPatrolName");
			}
			
		});
		
	}
	
}

function getRoutesByDate() {
	var start = $("#txtDateFrom").val();
	var end = $("#txtDateTo").val();
	
	var alertContent = "";
	
	if(start == ""){
		
		alertContent +="Please input values on start date.\n";
		
	}
	
	if(end == "") {
		
		alertContent +="Please input values on end date.\n";
	}
	
	if(alertContent == ""){
				
		var d = new Date(start);
		start = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
		
		d = new Date(end);
		end = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate() + " 23:59:59";
		
		
		
		
		$.ajax({
			url: '/getRoutesByDate/'+ start + '/' + end,
			type: 'GET',
			
			success: function(data) {
				
				if(data == ""){
					alert("No Patrol Routes for: " + start + " to " + end);
				}
				else{
					patrolRoute = [];
					patrolRoute = data;
					
					manageRoutes(data);
				}
			},
			error: function(e) {
				alert("error in /getRoutesByDate");
			}
			
		});
	}
	else{
		
		alert(alertContent);
	}
}

function getRoutesByStatus() {
	
	
	var status = $("#viewPatrolStatus").val();
	

	
	$.ajax({
		url: '/getRoutesByStatus/'+ status,
		type: 'GET',
		
		success: function(data) {
			patrolRoute = [];
			patrolRoute = data;
			
			//alert(JSON.stringify(data));
			manageRoutes(data);
			
		},
		error: function(e) {
			alert("error");
		}
		
	});
	
}

function getRoutesByRegion(){
	
	var region = $("#viewByRegion").val();
		
	if(region == ""){
		alert("Please select value on regions.");
	}
	else{
	
		$.ajax({
			url: '/getRoutesByRegion/'+ region,
			type: 'GET',
			
			success: function(data) {
				patrolRoute = [];
				patrolRoute = data;
				
				//alert(JSON.stringify(data));
				manageRoutes(data);
				
			},
			error: function(e) {
				alert("error in /getRoutesByRegion");
			}
			
		});
	}
}

//generate and animate

function generateMarkers(id,patrolId,position,timestamp,timeout){
	marker = new google.maps.Marker(); 
	var content = "";
	
	console.log("Generating markers...");
	objMarkers = {
		id: id,
		patrolId: patrolId,
		position: position
	};

	lstMarkers.push(objMarkers);
	
	if(id == small){
		content += "Start of Patrol: " + patrolId;
	}
	else{
		content += "End of Patrol: " + patrolId;
	}
	
	var icon = {
    url: "https://cdn1.iconfinder.com/data/icons/Map-Markers-Icons-Demo-PNG/128/Map-Marker-Marker-Outside-Chartreuse.png", // url
    scaledSize: new google.maps.Size(50, 50), // scaled size
    
};
	
	 window.setTimeout(function() {
          markers.push(marker = new google.maps.Marker({
            position: position,
            map: map,
			id: patrolId,
			info: content,
            animation: google.maps.Animation.DROP,
			icon: icon,
          }));
		 
		  marker.addListener('click', showDetails);
		  
        }, timeout);
		

			

}
// add clear poly
function clearMarkers() {
        for (var i = 0; i < markers.length; i++) {
          markers[i].setMap(null);
        }	
        markers = [];
		
		map.setZoom(6);
		map.setCenter({lat: 13.5467, lng: 120.007});
}

function showDetails(markerData){

	$("#divInfo").show();
	drawTable(this.id);
	
	//generate infowindow
	
	infowindow = new google.maps.InfoWindow({
	  content: this.info,
	  maxWidth: 200
	});
	infowindow.open(map,this);
	
	/*di na ata ginagamit
	var htmlContent = "<b>Patrol Details:</b><br>";
	
	for(var i=0; i<lstMarkers.length; i++){
		
		
		if(this.id == lstMarkers[i].id){
			//select sa database ulit
			//patrols table
			var id = this.id;
	
			//Patrols
			$.ajax({
				url: '/getPatrolById/'+ id,
				type: 'GET',
			}).done(function(patrolData) {
					htmlContent = htmlContent + " Patrol Name: " + patrolData.patrolName;
					htmlContent += " <br>Start Date: " + patrolData.startDate;
					htmlContent += " <br>End Date: " + patrolData.endDate;
					
					//$("#patrolDetails").html(htmlContent);
					
					
					//patrollers
					$.ajax({
						url: '/getPatrollersByPatrolId/'+ id,
						type: 'GET',
					}).done(function(patrollerData){
						var htmlPatrollerList= " ";
						htmlPatrollerList += "<ul> Patrollers:";
							for(var i=0; i<patrollerData.length; i++){
								htmlPatrollerList += " <li> " + patrollerData[i].name + " </li>";
							}
							htmlPatrollerList += "</ul>"
							htmlContent += htmlPatrollerList;
							$("#patrolDetails").html(htmlContent);
							$("#patrolDetails").show();
							
					}).fail(function (jqXHR, textStatus){
						alert("error");
					});
						
					
				
			}).fail(function (jqXHR, textStatus){
				alert("error");
			});
				
			
			
			
			return;
		}
	}
	*/
	
	
}
function drawTable(id){
	
	var htmlContent1 = '<table class="table table-striped">' +
						'<thead></thead><tbody>';
	//alert(id);
	$.ajax({
		url: '/getPatrolById/'+ 1,
		type: 'GET',
		}).done(function(patrolData){
			//alert(JSON.stringify(patrolData));
			htmlContent1 += '<tr><td>Patrol Name: </td> <td>' + patrolData.patrolName +' </td></tr>';
			htmlContent1 += '<tr><td>Region: </td> <td>' + patrolRoute[0].region +' </td></tr>';
		$.ajax({
		url: '/getPatrollersByPatrolId/'+ 1,
		type: 'GET',
		}).done(function(patrollersData){
			//alert(JSON.stringify(patrollersData));
			htmlContent1 += '<tr><td colspan="2"> Patrollers: </td></tr>';
			for(var i=0;i<patrollersData.length;i++){
			htmlContent1 += '<tr><td colspan="2" ><i class="panel-title-icon fa  fa-flag"></i>'+ patrollersData[i].name +' </td></tr>';
			}
			htmlContent1 += '</tbody></table>';
		$("#info_div").html(htmlContent1);
		
		}).fail(function (jqXHR, textStatus){
			alert("error");
		});
		
		}).fail(function (jqXHR, textStatus){
			alert("error");
		});
	
	
		
		
	
	
	datatable = new google.visualization.DataTable();
	
	datatable.addColumn('number', ' ');
	//datatable.addColumn('number', 'Patrol ID');
	datatable.addColumn('string', 'Observation Type');
	datatable.addColumn('date', 'Start Date');
	datatable.addColumn('date', 'End Date');
	//datatable.addColumn('number', 'ID');
	
	
	
	$.ajax({
		url: '/getObservationsByPatrolId/'+ id,
		type: 'GET',
		}).done(function(datas){
			
			
			//datas = JSON.parse(datas);
			//alert(JSON.stringify(datas[0]));
				//datatable.addRow(datas[0]);
			 
				datatable.addRows(datas.length);
			  
			  for(var i=0; i<datas.length; i++){
				  
				datatable.setCell(i,0,datas[i].id);
				datatable.setCell(i,1,datas[i].observationType);
				
				var startDate = new Date(datas[i].startDate);
				datatable.setCell(i,2,startDate);
				var endDate = new Date(datas[i].endDate);
				datatable.setCell(i,3,endDate);
			  }

			table.draw(datatable, { width: '100%', height: '100%'});
		
		}).fail(function (jqXHR, textStatus){
			alert("error");
		});
	
}

function initTable() {
datatable = new google.visualization.DataTable();
   table = new google.visualization.Table(document.getElementById('table_div'));
  

google.visualization.events.addListener(table, 'select', function() {
    var row = table.getSelection()[0].row;
	
	var htmlContent = '<table class="table table-striped">' +
						'<thead></thead><tbody>';
	//$("#infoContent").html(datatable.getValue(row,0));
	var id = 0;
	id = parseInt(datatable.getValue(row,0));
	
	
	
$.ajax({
		url: '/getObservationByObsId/'+ id,
		type: 'GET',
		}).done(function(datas){
			
			
			if(datas.observationType == "FOREST_CONDITION"){
				
				
				htmlContent += '<tr><td>Forest Condition Type: </td> <td>' + datas.forestConditionType +' </td></tr>';
				htmlContent += '<tr><td>Presence of Regenerants: </td> <td>' + datas.presenceOfRegenerants +' </td></tr>';
				htmlContent += '<tr><td>Density of Regenerants: </td> <td>' + datas.densityOfRegenerants +' </td></tr>';
			}
			else if(datas.observationType == "WILDLIFE"){
				htmlContent += '<tr><td>Wildlife Observation Type: </td> <td>' + datas.wildlifeObservationType +' </td></tr>';
					htmlContent += '<tr><td>Species: </td> <td>' + datas.species +' </td></tr>';
					htmlContent += '<tr><td>Species Type: </td> <td>' + datas.speciesType +' </td></tr>';
				if(datas.wildlifeObservationType == "DIRECT"){
					
					if(datas.species == "FLORA"){
						htmlContent += '<tr><td>Diameter: </td> <td>' + datas.diameter +' </td></tr>';
						htmlContent += '<tr><td>Number of Trees: </td> <td>' + datas.noOfTrees +' </td></tr>';
						htmlContent += '<tr><td>Observed Through Gathering: </td> <td>' + datas.observedThrougGathering +' </td></tr>';
						htmlContent += '<tr><td>Other Tree Specied Observed: </td> <td>' + datas.otherTreeSpeciedObserved +' </td></tr>';
					}
					else{
						htmlContent += '<tr><td>Number of Male Adults: </td> <td>' + datas.noOfMaleAdults +' </td></tr>';
						htmlContent += '<tr><td>Number of Female Adults: </td> <td>' + datas.noOfFemaleAdults +' </td></tr>';
						htmlContent += '<tr><td>Number of Young: </td> <td>' + datas.noOfYoung +' </td></tr>';
						htmlContent += '<tr><td>Number of Undetermined: </td> <td>' + datas.noOfUndetermined +' </td></tr>';
						htmlContent += '<tr><td>Action Taken: </td> <td>' + datas.actionTaken +' </td></tr>';
						htmlContent += '<tr><td>Observed Through Hunting: </td> <td>' + datas.observedThroughHunting +' </td></tr>';
					}
					
				}
				else {
					htmlContent += '<tr><td>Evidences: </td> <td>' + datas.evidences +' </td></tr>';
				}
			}
			else if(datas.observationType == "THREATS"){
				htmlContent += '<tr><td>Threat Type: </td> <td>' + datas.threatType +' </td></tr>';
				htmlContent += '<tr><td>Distance of Threat From Waypoint: </td> <td>' + datas.distanceOfThreatFromWaypoint +' </td></tr>';
				htmlContent += '<tr><td>Bearing of Threat From Waypoint: </td> <td>' + datas.bearingOfThreatFromWaypoint +' </td></tr>';
				htmlContent += '<tr><td>Response to Threat: </td> <td>' + datas.responseToThreat +' </td></tr>';
				htmlContent += '<tr><td>Notes: </td> <td>' + datas.note +' </td></tr>';
			}
			else if(datas.observationType == "OTHER_OBSERVATIONS") {
				htmlContent += '<tr><td>Notes: </td> <td>' + datas.note +' </td></tr>';
				//htmlContent += '<tr><td>Threat Type: </td> <td>' + datas.threatType +' </td></tr>';
			}
			
			htmlContent += '</tbody></table>';
				$.ajax({
				url: '/getImageLists/' + id,
				type: 'GET',
				
				success: function(imageData) {
						
						htmlContent += '<div class="container-fluid">';
						htmlContent += 'GALLERY:';
						
					
							
							
							htmlContent += '<div class="card-body card-padding">';
							
							htmlContent += '<div class="lightbox photos">';
						if(imageData != ""){
							for(var i=0; i<imageData.length; i++){
										
									
										
										htmlContent += '<div data-src="showImage/' + imageData[i].id + '" class="col-md-2 col-sm-4 col-xs-5">';
										htmlContent += '<div class="lightbox-item p-item">';
										
										htmlContent += '<img src="showImage/' + imageData[i].id + '" />';
										htmlContent += '</div></div>';
									
							}
							
						}
						else{
							htmlContent += "No Photos Available.";
						}
						htmlContent += '</div></div></div>';
						
						$("#infoContent").html(htmlContent);
						
						 if ($('.lightbox')[0]) {
							
						$('.lightbox').lightGallery({
							
						enableTouch: true
						});
						 
						}
					
					
				},
				error: function(e) {
					alert("error");
				}
				
			});
			
			
			
			//alert(JSON.stringify(datas));
			
		
			
			//alert(datas.observationType);
			
		}).fail(function (jqXHR, textStatus){
			alert(JSON.stringify(jqXHR));
		});
	
	
	$("#myModal").modal("show");
	
    //	alert('You selected ' + datatable.getValue(row, 0));
  });
  

 
}

function initMap() {
	
	var mapDiv = document.getElementById('map');
	map = new google.maps.Map(mapDiv, {
	center: {lat: 13.5467, lng: 120.007},
	zoom: 6
	});

	directionsService = new google.maps.DirectionsService;
    directionsDisplay = new google.maps.DirectionsRenderer;

	geocoder = new google.maps.Geocoder;
	
	

	
}


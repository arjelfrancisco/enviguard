//script.js capstone

var map;

var polys = [];
var geocoder;
var selectedRegions = [];
var directionsService;
var directionsDisplay;

//for distance
var distance;
var totalDistance = 0;
var objDistance = new Object();
var lstDistance = [];



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
			//$("#btnClear").show();
		}
		
		else if(category == "patrolId"){
			$("#divPatrolId").show();
			$("#btnView").show();
			//$("#btnClear").show();
		}
		
		else if(category == "patrollerName"){
			populatePatrollerNames();
			$("#divPatrollerName").show();
			$("#btnView").show();
			//$("#btnClear").show();
		}
		
		else if(category == "date"){
			$("#divDateRange").show();
			$("#btnView").show();
			//$("#btnClear").show();
		}
		
		else if(category == "status"){
			$("#divPatrolStatus").show();
			$("#btnView").show();
			//$("#btnClear").show();
		}
		
		else if(category == "region"){
			
			populateRegions();
			$("#divRegions").show();
			$("#btnView").show();
			//$("#btnClear").show();
			
		}
		else if(category == "patrolName"){
			populatePatrols();
			$("#divPatrolName").show();
			$("#btnView").show();
			//$("#btnClear").show();
		}
		else{
			
		$("#btnView").hide();
		$("#btnClear").hide();
		}
	});
	


	$("#btnView").click(function () {
		clearMarkers();
		allRoutes = [];
		$("#divInfo").hide();
		
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
		$("#btnView").hide();
		$("#btnClear").hide();
	});
});



function validateInputs(txtFields){
	
	var alertContent = "";
	
	for(var i=0; i<txtFields.length; i++){
		
		if( $("#txtPatrollerName").val() == ""){
		
			alertContent = " Not all fields have a value ";
			
			
		}
	}
	
	if(alertContent != ""){
		var title = "WARNING";
			  var body = alertContent;
			  
			  $("#warningTitle").html(title);
			  $("#warningBody").html(body);
			  
			  $("#warningModal").modal("show");
		return false;
	}
	
	
	
	return true;
	
}
 
 

function populateRegions(){
	var drpDwn = '<select id="viewByRegion" class="btn btn-success" >' +
					'<option value = "none">-- Select Region --</option>';
		$.ajax({
		url: '/getDistinctRegions',
		type: 'GET',
		
		success: function(data) {
			for(var i=0; i<data.length; i++){
				
				drpDwn += '<option value = "' + data[i] + '">'+ data[i] + '</option>';
			}
			drpDwn += '</select>';
			
			$("#divRegions").html(drpDwn);
			
			$("#viewByRegion").change( function() {
				
			
				var region = $("#viewByRegion").val();
				
				if(region == "none"){
					$("#divStreet").hide();
					$("#divCity").hide();
				}
				else{
					$("#divCity").show();
					$("#divStreet").hide();
					
					var drpDwnCity = '<select id="viewByCity" class="btn btn-info" >' +
						'<option value = "none">-- Select City --</option>';
						
						
						
					$.ajax({
					url: '/getCity/"' + region + '"' ,
					type: 'GET',
					
					success: function(data) {
						for(var i=0; i<data.length; i++){
							//alert(JSON.stringify(data));
							drpDwnCity += '<option value = "' + data[i].city + '">'+ data[i].city + '</option>';
						}
						drpDwnCity += '</select>';
						
						$("#divCity").html(drpDwnCity);
						
						
						$("#viewByCity").change( function() {
							
							var city =  $("#viewByCity").val();
							
							if(city == "none"){
								$("#divStreet").hide();
								
							}else{
								
								$("#divStreet").show();
								var drpDwnStreet = '<select id="viewByStreet" class="btn btn-warning" >' +
												'<option value = "none">-- Select Street --</option>';
								
								$.ajax({
									url: '/getStreet/"' + region + '"/"' + city + '"' ,
									type: 'GET',
									
									success: function(streetData) {
										
										for(var i=0; i<streetData.length; i++){
											//alert(JSON.stringify(data));
											drpDwnStreet += '<option value = "' + streetData[i].street + '">'+ streetData[i].street + '</option>';
										}
										drpDwnStreet += '</select>';
										$("#divStreet").html(drpDwnStreet);
										
									},
									error: function(jqXHR, textStatus, errorThrown) {
										var title = "ERROR";
										var body ="error in /getStreet <br>" + jqXHR.responseText;
										  
										$("#dangerTitle").html(title);
										$("#dangerBody").html(body);
										
										$("#dangerModal").modal("show");
									
										
									}
										
									});
							
							}								
							});
						
						
						
						},
					error: function(jqXHR, textStatus, errorThrown) {
						var title = "ERROR";
						var body ="error in /getCity <br>" + jqXHR.responseText;
						  
						$("#dangerTitle").html(title);
						$("#dangerBody").html(body);
						
						$("#dangerModal").modal("show");
					
						
					}
						
					});
				}
				
				});
			
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			var title = "ERROR";
			var body ="error in /getDistinctRegions: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
		
			
		}
		
	});
	
}

function populatePatrollerNames(){
	
	var drpDwn = '<select id="viewByPatroller" class="btn btn-success" >' +
					'<option value = "">-- Select Patroller --</option>';
		$.ajax({
		url: '/getPatrollers',
		type: 'GET',
		
		success: function(data) {
			for(var i=0; i<data.length; i++){
				
				drpDwn += '<option value = "' + data[i].name + '">'+ data[i].name + '</option>';
			}
			drpDwn += '</select>';
			$("#divPatrollerName").html(drpDwn);
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			var title = "ERROR";
			var body ="error in /getPatrollers: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
		
			
		}
		
	});
	
}

function populatePatrols(){
	
	var drpDwn = '<select id="viewByPatrols" class="btn btn-success" >' +
					'<option value = "">-- Select Patrol --</option>';
		$.ajax({
		url: '/getPatrols',
		type: 'GET',
		
		success: function(data) {
			for(var i=0; i<data.length; i++){
				
				drpDwn += '<option value = "' + data[i].patrolName + '">'+ data[i].patrolName + '</option>';
			}
			drpDwn += '</select>';
			$("#divPatrolName").html(drpDwn);
			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			var title = "ERROR";
			var body ="error in /getPatrollers: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
		
			
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
				
				var title = "WARNING";
				var body = "No Patrol Routes yet!";
				  
				$("#warningTitle").html(title);
				$("#warningBody").html(body);
				
				$("#warningModal").modal("show");
				
				
			}
			else{
				
			
			patrolRoute = data;
			manageRoutes(data);
			}
			
			
		},
		error: function(jqXHR, textStatus, errorThrown) { 
			var title = "ERROR";
			var body ="error in /getAllRoutes: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
			

		}
		
	});
	
	
}


function manageRoutes(data){
	var count = 0;
	var contentString = ""; //infowindow
	polys = [];
	
	var flag = true;
	var cntTimeOut = 0;
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
					
					}
							
				}
				
				
				for(var x=0; x<data.length; x++){
					
					if(data[x].id === small || data[x].id === large ){
					
						cntTimeOut++;
						
						var pos;
						pos = {lat: data[x].lat, lng: data[x].lng};
						
						console.log(JSON.stringify(data.length));
						
						generateMarkers(data[x].id,data[x].patrolId,pos,data[x].timestamp,cntTimeOut*200);
					}
				}
				
				color = '#' + ("000000" + Math.random().toString(16).slice(2, 8).toUpperCase()).slice(-6);
				poly = new google.maps.Polyline({
				//editable: true,
				strokeColor: color,
				strokeOpacity: 0.8,
				strokeWeight: 4,
				path: routeCoordinates,
				id: patrolIds[a]
						  
				});
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
		error: function(jqXHR, textStatus, errorThrown) {
			var title = "ERROR";
			var body ="error in /getPatrolIds: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
			
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
		error: function(jqXHR, textStatus, errorThrown) {
			var title = "ERROR";
			var body ="error in /getRoutesById: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
			
		}
		
	});
}

function getRoutesByPatrollerName() {
	var name = $("#viewByPatroller").val();
	
	if( name == ""){
		
	  var title = "WARNING";
			  var body = "Please input values on patroller name.";
			  
			  $("#warningTitle").html(title);
			  $("#warningBody").html(body);
			  
			  $("#warningModal").modal("show");
	
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
					var title = "WARNING";
					var body = name + "doesn't have patrols or doesn't exists.";
				  
					$("#warningTitle").html(title);
					$("#warningBody").html(body);
					
					$("#warningModal").modal("show");
				}
				
			},
			error: function(jqXHR, textStatus, errorThrown) {
						var title = "ERROR";
					  var body ="error in /getRoutesByPatrollerName: <br>" + jqXHR.responseText;
					  
					  $("#dangerTitle").html(title);
					  $("#dangerBody").html(body);
					  
					  $("#dangerModal").modal("show");
				
			}
			
		});
	}
	
}

function getRoutesByPatrolName() {
	var name = $("#viewByPatrols").val();
	if(name == ""){
	
	  
	   var title = "WARNING";
			  var body = "Please input values on Patrol Name.";
			  
			  
			  $("#warningTitle").html(title);
			  $("#warningBody").html(body);
			  
			  $("#warningModal").modal("show");
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
					
					
				var title = "WARNING";
			  var body = "No Data for: " + name;
			  
			  $("#warningTitle").html(title);
			  $("#warningBody").html(body);
			  
			  $("#warningModal").modal("show");
				}
				
			},
			error: function(e) {
				var title = "ERROR";
				  var body ="error in /getRoutesByPatrolName";
				  
				  $("#dangerTitle").html(title);
				  $("#dangerBody").html(body);
				  
				  $("#dangerModal").modal("show");
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
		
		alertContent +="<br>Please input values on end date.\n";
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
					
					
					var title = "WARNING";
					var body ="No Patrol Routes for: " + start + " to " + end
					  
					$("#warningTitle").html(title);
					$("#warningBody").html(body);
					
					$("#warningModal").modal("show");
					
				}
				else{
					patrolRoute = [];
					patrolRoute = data;
					
					manageRoutes(data);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var title = "ERROR";
				var body ="error in /getRoutesByDate: <br>" + jqXHR.responseText;
				  
				$("#dangerTitle").html(title);
				$("#dangerBody").html(body);
				
				$("#dangerModal").modal("show");
				
			}
			
		});
	}
	else{
		var title = "WARNING";
			  var body = alertContent;
			  
			  
			  $("#warningTitle").html(title);
			  $("#warningBody").html(body);
			  
			  $("#warningModal").modal("show");
		
		
		
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
		error: function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.responseText);
		}
		
	});
	
}

function getRoutesByRegion(){
	
	var region = $("#viewByRegion").val();
	var city = "";
	city = $("#viewByCity").val();
	var street = "";
	street = $("#viewByStreet").val();
		
	if(region == ""){
		
		var title = "WARNING";
			  var body = "Please select value on regions.";
			  
			  
			  $("#warningTitle").html(title);
			  $("#warningBody").html(body);
			  
			  $("#warningModal").modal("show");
	}
	else{
	
		$.ajax({
			url: '/getRoutesByRegion/'+ region + '/' + city + '/' + street ,
			type: 'GET',
			
			success: function(data) {
				patrolRoute = [];
				patrolRoute = data;
				
				//alert(JSON.stringify(data));
				manageRoutes(data);
				
			},
			error: function(jqXHR, textStatus, errorThrown) {
				
				var title = "ERROR";
				var body ="error in /getRoutesByRegion: <br>" + jqXHR.responseText;
				  
				$("#dangerTitle").html(title);
				$("#dangerBody").html(body);
				
				$("#dangerModal").modal("show");
				
			}
			
		});
	}
}

//generate and animate

function generateMarkers(id,patrolId,position,timestamp,timeout){
	marker = new google.maps.Marker(); 
	var content = "";
	
	
	objMarkers = {
		id: id,
		patrolId: patrolId,
		position: position
	};

	//lstMarkers.push(objMarkers);
	
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
	//generate distance
	
	//generate distance
	
	
	var aTag = $("a[name='infoDiv']");
	$('html,body').animate({scrollTop: aTag.offset().top},'slow');
	drawTable(this.id);
	
	//generate infowindow
	
	infowindow = new google.maps.InfoWindow({
	  content: this.info,
	  maxWidth: 200
	});
	infowindow.open(map,this);
}
function drawTable(id){
	
	var htmlContent1 = '<table class="table table-striped">' +
						'<thead></thead><tbody>';
	//alert(id);
	
	
	$.ajax({
		url: '/getPatrolById/'+ id,
		type: 'GET',
		}).done(function(patrolData){
			htmlContent1 += '<tr><td><b>Patrol Name: <b></td> <td>' + patrolData.patrolName +' </td></tr>';
			htmlContent1 += '<tr><td><b>Region: <b></td> <td>' + patrolRoute[0].region +' </td></tr>';
			htmlContent1 += '<tr><td><b>Patroller Name: <b></td> <td>' + patrolData.patrollerName +' </td></tr>';
			htmlContent1 += '<tr><td><b>Distance Covered: <b></td><td>' + patrolData.distance + ' KM </td></tr>';
			
	
	
		$.ajax({
		url: '/getPatrollersByPatrolId/'+ id,
		type: 'GET',
		}).done(function(patrollersData){
			//htmlContent1 += '<tr><td colspan="2"> Patrollers: </td></tr>';
			for(var i=0;i<patrollersData.length;i++){
			//htmlContent1 += '<tr><td colspan="2" ><i class="panel-title-icon fa  fa-flag"></i>'+ patrollersData[i].name +' </td></tr>';
			}
			htmlContent1 += '</tbody></table>';
		$("#info_div").html(htmlContent1);
		
		
		}).fail(function (jqXHR, textStatus){
			
			
			
			var title = "ERROR";
			var body ="error in /getPatrollersByPatrolId: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
			
			
		});
		
		
		}).fail(function (jqXHR, textStatus){
			
			var title = "ERROR";
			var body ="error in /getPatrolById: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
		});
	
	
		
		
	
	
	datatable = new google.visualization.DataTable();
	
	datatable.addColumn('number', ' ');
	//datatable.addColumn('number', 'Patrol ID');
	datatable.addColumn('string', 'Observation Type');
	datatable.addColumn('datetime', 'Start Date');
	datatable.addColumn('datetime', 'End Date');
	//datatable.addColumn('number', 'ID');
	
	
	
	$.ajax({
		url: '/getObservationsByPatrolId/'+ id,
		type: 'GET',
		}).done(function(datas){
			
			
			
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
			var title = "ERROR";
			var body ="error in /getObservationsByPatrolId: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
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
				error: function(jqXHR, textStatus) {
					var title = "ERROR";
					var body ="error in /getImageLists: <br>" + jqXHR.responseText;
					  
					$("#dangerTitle").html(title);
					$("#dangerBody").html(body);
					
					$("#dangerModal").modal("show");
				}
				
			});
			
		
			
			
		}).fail(function (jqXHR, textStatus){
			var title = "ERROR";
			var body ="error in /getObservationByObsId: <br>" + jqXHR.responseText;
			  
			$("#dangerTitle").html(title);
			$("#dangerBody").html(body);
			
			$("#dangerModal").modal("show");
		});
	
	
	$("#myModal").modal("show");
	
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
	distance = new google.maps.DistanceMatrixService;

	geocoder = new google.maps.Geocoder;
	
}


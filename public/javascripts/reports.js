
$(function () {
	
	populateCharts();
	
	populateForestCondition();
	populateThreat();
	populateWildlife();
	
	$("#hide").hide();

	
});



function populateCharts(){
	
	
	
	$('#datatableThreats').DataTable({
		dom: 'Bfrtip',
        buttons: [
            'print'
        ]
		
    });
	
	
	
	
	
	//---------------
	//density of regenerants
	
		$.ajax({
		url: '/generateReports/forest_condition_observations/density_of_regenerants' ,
		type: 'GET',
		beforeSend: function (){
			  
			  
		},
		
		success: function(reportData) {
		
			
				init.push(function () {
						Morris.Donut({
							element: 'graphForest',
							data: reportData,
							colors: PixelAdmin.settings.consts.COLORS,
							resize: true,
							labelColor: '#888',
							formatter: function (y) { return y }
						});
					});
					
					
	
			},
			error: function(e) {
				alert("error");
			}
			
		});
	

	$.ajax({
	url: '/generateReports/forest_condition_observations/presence_of_regenerants' ,
		type: 'GET',
		
		success: function(reportData) {
			
			for(var i=0; i<reportData.length; i++){
				
				if(reportData[i].label == 1){
					reportData[i].label = true;
				}
				else{
					reportData[i].label = false;
				}
			}
			
					init.push(function () {
						Morris.Bar({
							element: 'reportPresence',
							data: reportData,
							xkey: 'label',
							ykeys: ['value'],
							labels: ['value'],
							barRatio: 0.4,
							xLabelAngle: 35,
							hideHover: 'auto',
							barColors: PixelAdmin.settings.consts.COLORS,
							gridLineColor: '#cfcfcf',
							resize: true
						});
					});

	
		},
		error: function(e) {
			alert("error");
		}
		
	});
	
	
	$.ajax({
	url: '/generateReports/threat_observations/threat_type' ,
		type: 'GET',
		
		success: function(reportData) {
			
			for(var i=0; i<reportData.length; i++){
				
				
			}
			
					init.push(function () {
						Morris.Bar({
							element: 'reportThreat',
							data: reportData,
							xkey: 'label',
							ykeys: ['value'],
							labels: ['value'],
							barRatio: 0.4,
							xLabelAngle: 35,
							hideHover: 'auto',
							barColors: PixelAdmin.settings.consts.COLORS,
							gridLineColor: '#cfcfcf',
							resize: true
						});
					});

	
		},
		error: function(e) {
			alert("error");
		}
		
	});
	
	$.ajax({
		url: '/generateReports/wildlife_observations/wildlife_observation_type' ,
		type: 'GET',
		
		success: function(reportData) {
		
			
				init.push(function () {
						Morris.Donut({
							element: 'reportWildType',
							data: reportData,
							colors: PixelAdmin.settings.consts.COLORS,
							resize: true,
							labelColor: '#888',
							formatter: function (y) { return y }
						});
					});
					
					
	
		},
		error: function(e) {
			alert("error");
		}
		
	});
	$.ajax({
		url: '/generateReports/wildlife_observations/species' ,
		type: 'GET',
		
		success: function(reportData) {
		
			
				init.push(function () {
						Morris.Donut({
							element: 'reportSpecies',
							data: reportData,
							colors: PixelAdmin.settings.consts.COLORS,
							resize: true,
							labelColor: '#888',
							formatter: function (y) { return y }
						});
					});
					
					
	
		},
		error: function(e) {
			alert("error");
		}
		
	});
	
	
	
}

function populateForestCondition(){
	
	$('#forestDTOld').hide();
	$.ajax({
		url: '/getForestCondition' ,
		type: 'GET',
		
		success: function(forestConditionData) {
			
			
			//init datatable
			$('#tblForestCondition').DataTable( {
				dom: 'Bfrtip',
				buttons: [
					'print'
				],
				data: forestConditionData.dataset,
				columns: forestConditionData.columns
			});
			
			
		

		},
		error: function(e) {
			alert("error");
		}
		
	});
	
	
	
	
	
	
	init.push(function () {
		$('#datatableForest').dataTable( );
		$('#datatableForest .forest').text('test');
		$('#datatableForest .dataTables_filter input').attr('placeholder', 'Search...');
	});
	$('#datatableForest').DataTable({
		dom: 'Bfrtip',
        buttons: [
            'print'
        ]
		
    });
	

	
	var jsonRequest = {
					filterType: ""
			}
	
	$.ajax({
		  type: "POST",
		  url: "/getForestConditionReports",
		  data:  JSON.stringify(jsonRequest),
		  contentType: 'application/json',
		  success: function(resultData){
			
			  var forestDT = $("#datatableForest").DataTable();
			  for(var i=0; i<resultData.length; i++){
				  forestDT.row.add( [
						resultData[i].patrolName,
						resultData[i].forestConditionType,
						resultData[i].presenceOfRegenerants,
						resultData[i].densityOfRegenerants,
						resultData[i].startDate,
						resultData[i].endDate
					] ).draw( false );
			  }
			 // alert(JSON.stringify(resultData));
		  },
		error: function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.responseText);
			
			
		}
	});
	
}

function populateThreat(){
		
	//new threats datatable

	$('#threatDTOld').hide();
	$.ajax({
		url: '/getThreat' ,
		type: 'GET',
		
		success: function(threatData) {
			
			
			//init datatable
			$('#tblThreat').DataTable( {
				dom: 'Bfrtip',
				buttons: [
					'print'
				],
				data: threatData.dataset,
				columns: threatData.columns
			});
			
			
		

		},
		error: function(e) {
			alert("error");
		}
		
	});
	

	
	//new threats datatable
	

	
	init.push(function () {
		$('#datatableThreat').dataTable( );
		$('#datatableThreat .dataTables_filter input').attr('placeholder', 'Search...');
	});
	$('#datatableThreat').DataTable({
		dom: 'Bfrtip',
        buttons: [
            'print'
        ],
		
		
    });
	

	
	var jsonRequest = {
					filterType: ""
			}
	
	$.ajax({
		  type: "POST",
		  url: "/getThreatReport",
		  data:  JSON.stringify(jsonRequest),
		  contentType: 'application/json',
		  success: function(resultData){
			  var threatDT = $('#datatableThreat').DataTable();
			  for(var i=0; i<resultData.length; i++){
				  threatDT.row.add( [
						resultData[i].patrolName,
						resultData[i].threatType,
						resultData[i].distance,
						resultData[i].bearing,
						resultData[i].response,
						resultData[i].note,
						resultData[i].startDate,
						resultData[i].endDate,
						resultData[i].region
					] ).draw( false );
			  }
			 // alert(JSON.stringify(resultData));
		  },
		error: function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.responseText);
			
			
		}
	});
	
}

function populateWildlife(){
	
	$.ajax({
		url: '/getWildlife' ,
		type: 'GET',
		
		success: function(wildlifeData) {
			
			
			//init datatable
			$('#tblWildlife').DataTable( {
				dom: 'Bfrtip',
				buttons: [
					'print'
				],
				data: wildlifeData.dataset,
				columns: wildlifeData.columns
			});
			
			
		

		},
		error: function(e) {
			alert("error");
		}
		
	});

	
	
	
	$.ajax({
		url: '/generateReports/wildlife_observations/species_type' ,
		type: 'GET',
		
		success: function(reportData) {
		
			
				init.push(function () {
						Morris.Donut({
							element: 'reportSpeciesType',
							data: reportData,
							colors: PixelAdmin.settings.consts.COLORS,
							resize: true,
							labelColor: '#888',
							formatter: function (y) { return y }
						});
					});
					
					
	
		},
		error: function(e) {
			alert("error");
		}
		
	});
	
}


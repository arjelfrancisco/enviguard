$(function () {
	
	populateCharts();
});



function populateCharts(){
	
	//density of regenerants
	
		$.ajax({
		url: '/generateReports/forest_condition_observations/density_of_regenerants' ,
		type: 'GET',
		
		success: function(reportData) {
		
			
				init.push(function () {
						Morris.Donut({
							element: 'reportDensity',
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
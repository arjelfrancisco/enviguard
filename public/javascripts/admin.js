

$(function () {
	
populatePatrollers();
populateThreats();
populateSpecies();



});



		

function populatePatrollers(){
	var patrollerTable = "<table class='table table-striped' id='patrollers-table'>";
	patrollerTable += "<thead>";
	patrollerTable += "<th>Patroller ID</th>";
	patrollerTable += "<th>Patroller Name</th>";
	patrollerTable += '<th><div style="float:right" ><button title="Add" class="btn btn-info btn-rounded" id="btnAddPatrollers"><i class="panel-title-icon fa  fa-plus"></i></button></div></th>';
	patrollerTable += "</thead>";
	patrollerTable += "<tbody>";
	
	$.ajax({
	url: '/getPatrollers',
		type: 'GET',
		
		success: function(patrollersData) {
			//alert(JSON.stringify(patrollersData));
			
			for(var i=0; i<patrollersData.length; i++){
				patrollerTable += "<tr>";
				patrollerTable += "<td> " + patrollersData[i].id + "</td>" ;
				patrollerTable += "<td><a href='#' class='name' id='" + patrollersData[i].name + "' data-pk='" + patrollersData[i].id +  "' data-type='text' name ='" + patrollersData[i].id +"' data-placement='right' data-placeholder='Required' data-title='Enter Patroller Name'> " + patrollersData[i].name + "</a></td>" ;
				patrollerTable += '<td align="right"><button title="Save" class="btn btn-success btn-rounded"onClick="updatePatroller(\'' + patrollersData[i].name + '\')"><i class="panel-title-icon fa  fa-save"></i></button> <button title="Delete" class="btn btn-danger  btn-rounded" onClick="deletePatroller(' + patrollersData[i].id +')"><i class="panel-title-icon fa  fa-trash-o"></i></button></td>';
				patrollerTable += "</tr>";
				
			}
			
			patrollerTable += "</tbody>";
			patrollerTable += "</table>";
			
			
			
			$("#patrollerDiv").html(patrollerTable);
			
			$('.name').editable({
				validate: function(value) {
					if($.trim(value) == '') return 'This field is required';
				}
				
			});
			
			$('#btnAddPatrollers').on('click', function () {
				bootbox.prompt({
					title: "Please input Patroller Name",
					callback: function(result) {
						if (result === null) {
							//alert("Prompt dismissed");
						} else {
							//alert("Hi " + result + "!");
							var newPatroller = {
									name: result
							}
							
							$.ajax({
								  type: "POST",
								  url: "/addPatrollers",
								  data:  JSON.stringify(newPatroller),
								  contentType: 'application/json',
								  success: function(resultData){
									  //alert("Save Complete");
									  var title = "Successfully Added!";
									  var body = "Patroller " + result + " is now added as patroller.";
									  
									  $("#successTitle").html(title);
									  $("#successBody").html(body);
									  
									  $("#successModal").modal("show");
									  
									  populatePatrollers();
								  },
								error: function(jqXHR, textStatus, errorThrown) {
									//alert(jqXHR.responseText);
									 var title = "ERROR";
									  var body = jqXHR.responseText;
									  
									  $("#dangerTitle").html(title);
									  $("#dangerBody").html(body);
									  
									  $("#dangerModal").modal("show");
									
								}
							});
							
							
						}
					},
					className: "bootbox-sm"
				});
			});

			
			

		
	
		},
		error: function(e) {
			alert("error");
			console.log(e);
		}
		
	});
	
	
}

function updatePatroller(name){
	
	bootbox.confirm({
		message: "Are you sure you want to save the data?",
		callback: function(result) {
			if(result){
				var patrollerTxt = document.getElementById(name);
				
				var id = patrollerTxt.name;
				var newName = patrollerTxt.text;

				var patroller = {
									id: id,
									name: newName
								}
					$.ajax({
						  type: "PUT",
						  url: "/updatePatroller/" + id,
						  data:  JSON.stringify(patroller),
						  contentType: 'application/json',
						  success: function(resultData){
							 // alert("Update Complete");
							  
							  var title = "Successfully Updated!";
							  var body = "Patroller with a patrol id of " + id + " is updated.";
							  
							  $("#successTitle").html(title);
							  $("#successBody").html(body);
							  
							  $("#successModal").modal("show");
							  
							  
							  populatePatrollers();
						  },
						error: function(jqXHR, textStatus, errorThrown) {
							//alert(jqXHR.responseText);
							
							 var title = "ERROR";
							  var body = jqXHR.responseText;
							  
							  $("#dangerTitle").html(title);
							  $("#dangerBody").html(body);
							  
							  $("#dangerModal").modal("show");
						}
					});	
			}
		},
		className: "bootbox-sm"
	});


	

	
}

function deletePatroller(id){
	bootbox.confirm({
			message: "Are you sure you want to delete this patroller?",
			callback: function(result) {
				if(result){
					$.ajax({
						  type: "DELETE",
						  url: "/deletePatroller/" + id,
						  success: function(resultData){
							  //alert(resultData);
							  var title = "Successfully Deleted!";
							  var body = "Patroller with a patrol id of " + id + " is deleted.";
							  
							  $("#successTitle").html(title);
							  $("#successBody").html(body);
							  
							  $("#successModal").modal("show");
							  
							  populatePatrollers();
						  },
						error: function(jqXHR, textStatus, errorThrown) {
							//alert(jqXHR.responseText);
							var title = "ERROR";
							  var body = jqXHR.responseText;
							  
							  $("#dangerTitle").html(title);
							  $("#dangerBody").html(body);
							  
							  $("#dangerModal").modal("show");
						}
					});	
				}
			},
			className: "bootbox-sm"
		});


	
	
}


function populateThreats(){
	var patrollerTable = "<table class='table table-striped' id='threats-table'>";
	patrollerTable += "<thead>";
	patrollerTable += "<th>Threat ID</th>";
	patrollerTable += "<th>Threat Name</th>";
	patrollerTable += '<th><div style="float:right" ><button title="Add" class="btn btn-info btn-rounded" id="btnAddThreat"><i class="panel-title-icon fa  fa-plus"></i></button></div></th>';
	patrollerTable += "</thead>";
	patrollerTable += "<tbody>";
	
	$.ajax({
	url: '/getThreatTypes',
		type: 'GET',
		
		success: function(threatData) {
			//alert(JSON.stringify(threatData));
			
			for(var i=0; i<threatData.length; i++){
				patrollerTable += "<tr>";
				patrollerTable += "<td> " + threatData[i].id + "</td>" ;
				patrollerTable += "<td><a href='#' class='threatName' id='" + threatData[i].name + "' data-pk='" + threatData[i].id +  "' data-type='text' name ='" + threatData[i].id +"' data-placement='right' data-placeholder='Required' data-title='Enter Threat Name'> " + threatData[i].name + "</a></td>" ;
				patrollerTable += '<td align="right"><button title="Save" class="btn btn-success btn-rounded" onClick="updateThreat(\'' + threatData[i].name + '\')"><i class="panel-title-icon fa  fa-save"></i></button> <button title="Delete" class="btn btn-danger  btn-rounded" onClick="deleteThreat(' + threatData[i].id +')"><i class="panel-title-icon fa  fa-trash-o"></i></button></td>';
				patrollerTable += "</tr>";
				
			}
			
			patrollerTable += "</tbody>";
			patrollerTable += "</table>";
			
			
			$("#threatsDiv").html(patrollerTable);
			
			$('.threatName').editable({
				validate: function(value) {
					if($.trim(value) == '') return 'This field is required';
				}
				
			});
			
			$('#btnAddThreat').on('click', function () {
				bootbox.prompt({
					title: "Please input Threat Name",
					callback: function(result) {
						if (result === null) {
							//alert("Prompt dismissed");
						} else {
							//alert("Hi " + result + "!");
							var newThreat = {
									name: result
							}
							
							$.ajax({
								  type: "POST",
								  url: "/addThreatType",
								  data:  JSON.stringify(newThreat),
								  contentType: 'application/json',
								  success: function(resultData){
									  //alert("Save Complete");
									  var title = "Successfully Added!";
									  var body = result + " is now added as threat.";
									  
									  $("#successTitle").html(title);
									  $("#successBody").html(body);
									  
									  $("#successModal").modal("show");
									  
									  populateThreats();
								  },
								error: function(jqXHR, textStatus, errorThrown) {
									//alert(jqXHR.responseText);
									 var title = "ERROR";
									  var body = jqXHR.responseText;
									  
									  $("#dangerTitle").html(title);
									  $("#dangerBody").html(body);
									  
									  $("#dangerModal").modal("show");
									
								}
							});
							
							
						}
					},
					className: "bootbox-sm"
				});
			});

			
			

		
	
		},
		error: function(e) {
			alert("error");
			console.log(e);
		}
		
	});
	
	
}



function updateThreat(name){
	
	bootbox.confirm({
		message: "Are you sure you want to save the data?",
		callback: function(result) {
			if(result){
				var threatTxt = document.getElementById(name);
				var id = threatTxt.name;
				var newName = threatTxt.text;

				var threat = {
									id: id,
									name: newName
								}
					$.ajax({
						  type: "PUT",
						  url: "/updateThreat/" + id,
						  data:  JSON.stringify(threat),
						  contentType: 'application/json',
						  success: function(resultData){
							 // alert("Update Complete");
							  
							  var title = "Successfully Updated!";
							  var body = "Threat with a threat id of " + id + " is updated.";
							  
							  $("#successTitle").html(title);
							  $("#successBody").html(body);
							  
							  $("#successModal").modal("show");
							  
							  
							  populateThreats();
						  },
						error: function(jqXHR, textStatus, errorThrown) {
							//alert(jqXHR.responseText);
							
							 var title = "ERROR";
							  var body = jqXHR.responseText;
							  
							  $("#dangerTitle").html(title);
							  $("#dangerBody").html(body);
							  
							  $("#dangerModal").modal("show");
						}
					});	
			}
		},
		className: "bootbox-sm"
	});
}

function deleteThreat(id){
	bootbox.confirm({
			message: "Are you sure you want to delete this threat?",
			callback: function(result) {
				if(result){
					$.ajax({
						  type: "DELETE",
						  url: "/deleteThreat/" + id,
						  success: function(resultData){
							  //alert(resultData);
							  var title = "Successfully Deleted!";
							  var body = "Threat with a threat id of " + id + " is deleted.";
							  
							  $("#successTitle").html(title);
							  $("#successBody").html(body);
							  
							  $("#successModal").modal("show");
							  
							  populateThreats();
						  },
						error: function(jqXHR, textStatus, errorThrown) {
							//alert(jqXHR.responseText);
							var title = "ERROR";
							  var body = jqXHR.responseText;
							  
							  $("#dangerTitle").html(title);
							  $("#dangerBody").html(body);
							  
							  $("#dangerModal").modal("show");
						}
					});	
				}
			},
			className: "bootbox-sm"
		});


	
	
}

function populateSpecies(){
	var speciesTable = "<table class='table table-striped' id='species-table'>";
	speciesTable += "<thead>";
	speciesTable += "<th>Species ID</th>";
	speciesTable += "<th>Species Name</th>";
	speciesTable += "<th>Species Type</th>";
	speciesTable += '<th><div style="float:right" ><button title="Add" class="btn btn-info btn-rounded" id="btnAddSpecies"><i class="panel-title-icon fa  fa-plus"></i></button></div></th>';
	speciesTable += "</thead>";
	speciesTable += "<tbody>";
	
	$.ajax({
	url: '/getSpeciesTypes',
		type: 'GET',
		
		success: function(speciesData) {
			//alert(JSON.stringify(speciesData));
			//return;
			
			for(var i=0; i<speciesData.length; i++){
				speciesTable += "<tr id='speciesRow" + speciesData[i].id + "'>";
				speciesTable += "<td id='speciesId" + speciesData[i].id + "'> " + speciesData[i].id + "</td>" ;
				speciesTable += "<td><a href='#' class='speciesName' id='speciesName" + speciesData[i].name + "' data-type='text' data-placement='right' data-placeholder='Required' data-title='Enter Species Name'> " + speciesData[i].name + "</a></td>" ;
				speciesTable += "<td><a href='#' class='speciesType' name='test' id='speciesType" + speciesData[i].species + "' data-type='select' data-placement='right' data-placeholder='Required' data-title='Enter Species Type'> " + speciesData[i].species + "</a></td>" ;
				speciesTable += '<td align="right"><button title="Save" class="btn btn-success btn-rounded" onClick="updateSpecies('+ (i+1) +')"><i class="panel-title-icon fa  fa-save"></i></button> <button title="Delete" class="btn btn-danger  btn-rounded" onClick="deleteSpecies(' + speciesData[i].id +')"><i class="panel-title-icon fa  fa-trash-o"></i></button></td>';
				speciesTable += "</tr>";
				
			}
			
			speciesTable += "</tbody>";
			speciesTable += "</table>";
			
			
			
			$("#speciesDiv").html(speciesTable);
			
			$('.speciesName').editable({
				validate: function(value) {
					if($.trim(value) == '') return 'This field is required';
				}
				
			});
			var val;
			$('.speciesType').editable({
				
				prepend: 'SELECT',
				source: [
					{value: 'BIRD', text: 'BIRD'},
					{value: 'MAMMAL', text: 'MAMMAL'},
					{value: 'REPTILE', text: 'REPTILE'},
					{value: 'FLORA', text: 'FLORA'}
				],
				validate: function(value) {
					if($.trim(value) == '') return 'This field is required';
				}
				
			});
			
			
			$('#btnAddSpecies').on('click', function () {
				bootbox.confirm({
					title: "Please Input Species Details",	
					message: 	"<div class='container-fluid'>" +
								"<div class='col-md-6'> Species Name: <input type = 'text' id='txtSpeciesName'/><br><br>" +
								" Species Type: <select id='slctSpeciesType'></div>" +
								" <div class='col-md-6'><option value = ''>Select --</option>" +
								" <option value = 'BIRD'> BIRD </option>" +
								" <option value = 'MAMMAL'> MAMMAL </option>" +
								" <option value = 'REPTILE'> REPTILE </option>" +
								" <option value = 'FLORA'> FLORA </option></div>" +
								" <div></select>  ",
					callback: function(result) {
						if (result === null) {
							//alert("Prompt dismissed");
						} else {
							var name = $("#txtSpeciesName").val();
							var type = $("#slctSpeciesType").val();
							
							var newSpecies = {
									name: name,
									species: type
							}
							
							$.ajax({
								  type: "POST",
								  url: "/addSpeciesType",
								  data:  JSON.stringify(newSpecies),
								  contentType: 'application/json',
								  success: function(resultData){
									  //alert("Save Complete");
									  var title = "Successfully Added!";
									  var body = name + " is now added as species.";
									  
									  $("#successTitle").html(title);
									  $("#successBody").html(body);
									  
									  $("#successModal").modal("show");
									  
									  populateSpecies();
								  },
								error: function(jqXHR, textStatus, errorThrown) {
									//alert(jqXHR.responseText);
									 var title = "ERROR";
									  var body = jqXHR.responseText;
									  
									  $("#dangerTitle").html(title);
									  $("#dangerBody").html(body);
									  
									  $("#dangerModal").modal("show");
									
								}
							});
							
							
						}
						
					},
					className: "bootbox-sm"
				});

			});

			
			

		
	
		},
		error: function(e) {
			alert("error");
			console.log(e);
		}
		
	});
	
	
}

function updateSpecies(row){

	var table = $("#species-table");
	var id;
	var name;
	var species;
	
	

	var data = [];
    table.find('tr').each(function (rowIndex, r) {
        var cols = [];
		if(rowIndex == row){
			$(this).find('th,td').each(function (colIndex, c) {
				cols.push(c.textContent);
				switch(colIndex){
					
					case 0:
						id = c.textContent;
						break;
					case 1:
						name = c.textContent;
						break;
					case 2:
						species = c.textContent;
						break;
					default:
						break;
					
				}
			});
			data.push(cols);
		}
		
        
    });
    
	var species = {
			id: id,
			name: name,
			species: species
			}
			
	bootbox.confirm({
		message: "Are you sure you want to save the data?",
		callback: function(result) {
			if(result){
				
					$.ajax({
						  type: "PUT",
						  url: "/updateSpecies", 
						  data:  JSON.stringify(species),
						  contentType: 'application/json',
						  success: function(resultData){
							 // alert("Update Complete");
							  
							  var title = "Successfully Updated!";
							  var body = "Species with a threat id of " + id + " is updated.";
							  
							  $("#successTitle").html(title);
							  $("#successBody").html(body);
							  
							  $("#successModal").modal("show");
							  
							  
							  populateSpecies();
						  },
						error: function(jqXHR, textStatus, errorThrown) {
							//alert(jqXHR.responseText);
							
							 var title = "ERROR";
							  var body = jqXHR.responseText;
							  
							  $("#dangerTitle").html(title);
							  $("#dangerBody").html(body);
							  
							  $("#dangerModal").modal("show");
						}
					});	
			}
		},
		className: "bootbox-sm"
	});

	
}


function deleteSpecies(id){
	bootbox.confirm({
			message: "Are you sure you want to delete this species?",
			callback: function(result) {
				if(result){
					$.ajax({
						  type: "DELETE",
						  url: "/deleteSpecies/" + id ,
						  success: function(resultData){
							  //alert(resultData);
							  var title = "Successfully Deleted!";
							  var body = "Species with a species id of " + id + " is deleted.";
							  
							  $("#successTitle").html(title);
							  $("#successBody").html(body);
							  
							  $("#successModal").modal("show");
							  
							  populateSpecies();
						  },
						error: function(jqXHR, textStatus, errorThrown) {
							//alert(jqXHR.responseText);
							var title = "ERROR";
							  var body = jqXHR.responseText;
							  
							  $("#dangerTitle").html(title);
							  $("#dangerBody").html(body);
							  
							  $("#dangerModal").modal("show");
						}
					});	
				}
			},
			className: "bootbox-sm"
		});


	
	
}

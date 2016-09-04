$(function () {
	
	
		$("#username_id").keyup(function(event){
			if(event.keyCode == 13){
				$("#btnSignIn").click();
			}
		});
	
		$("#password_id").keyup(function(event){
			if(event.keyCode == 13){
				$("#btnSignIn").click();
			}
		});
	
	
		$("#btnSignIn").on('click', function () {
			signIn();
		});
});

function signIn(){
	
	
	var user = $("#username_id").val();
	var pass = $("#password_id").val();
	
	var thisUser = {
		username: user,
		password: pass
		
	};
	
	$.ajax({
		  type: "POST",
		  url: "/logIn",
		  data:  JSON.stringify(thisUser),
		  contentType: 'application/json',
		
		  success: function(resultData){
			  
			window.location.href = "/";
		  },
		error: function(jqXHR, textStatus, errorThrown) {
			//alert(jqXHR.responseText);
			
			var title = "ERROR";
		  var body = jqXHR.responseText;
		  
		  $("#warningTitle").html(title);
		  $("#warningBody").html(body);
		  
		  $("#warningModal").modal("show");
			
			
		}
	});
	
}
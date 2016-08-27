$(function () {
	
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
			alert(jqXHR.responseText);
			
		}
	});
	
}
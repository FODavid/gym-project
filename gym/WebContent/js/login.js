$(document).ready(function(){
	$('#login').click(function(e){
		
		var email = $('input[name="email"]').val();
		var password = $('input[name="password"]').val();
		
		if(email.trim().length <= 0 || password.trim().length <= 0){
			alert("Please enter an email and password");
			return;
		}
		
		var data = {email: email, password: password};
	
		$.ajax({
		    type: "POST",
		    url: "http://localhost:8080/gym/api/auth/login/",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: JSON.stringify(data),
		    contentType: "application/json",
		    dataType: "json",
		    success: function(data){
		    	console.log(data);
		    	if(data.loginID <= 0)
		    		alert("Invalid details provided");
		    	else{
		    		redirectUser(data);
		    	}
		    },
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
		
		e.preventDefault();
	});
});



	function redirectUser(data){
		localStorage.setItem('currentUser', JSON.stringify(data));
		switch(data.loginUser.userType){
		case "manager": location.href = 'managementPage.html';break;
		case "staff": location.href = 'staffLandingPage.html';break;
		default :location.href = 'customerLandingPage.html' ;break;
		}
	}
$(document).ready(function(){
	$('#register').click(function(e){
		
		var firstName = $('input[name="custFirst"]').val();
		var lastName = $('input[name="custLast"]').val();
		var phone = $('input[name="custPhone"]').val();
		var email = $('input[name="email"]').val();
		var password = $('input[name="psw"]').val();
		var category = $('select[name="custCat"]').val();
		
		if(firstName.trim().length <= 0 || lastName.trim().length <= 0
				|| phone.trim().length <= 0 
				|| email.trim().length <= 0
				|| password.trim().length <= 0){
			alert("Please fill all fields");
			return;
		}
		
		var data = {custFirst: firstName, 
					custLast: lastName,
					custPhone: "",
					custEmail: email,
					custPass: password,
					custMember:true, 
					categoryID: category};
	
		$.ajax({
		    type: "POST",
		    url: "http://localhost:8080/gym/api/customers/",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: JSON.stringify(data),
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		    	location.href = 'login.html';
		    },
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
		
		e.preventDefault();
	});
});
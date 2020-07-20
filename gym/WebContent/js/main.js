
var currentCustomer = JSON.parse(localStorage.getItem('currentUser'));
var registeredClasses = [];
var allServices = [];
var selectedServices = [];


if(currentCustomer == undefined || currentCustomer == null)
	window.location.href = 'login.html';



function updateUser(){
	$('#details h4.name').html((currentCustomer.customer.custFirst)+" "+(currentCustomer.customer.custLast));
	   $('#details h5.member span').html(currentCustomer.customer.custMember ? 'YES' : 'NO');
	   $('#details h6.balance span').html(currentCustomer.customer.cusBalance);
	   localStorage.setItem('currentUser', JSON.stringify(currentCustomer));
}


      
$(document).ready(function(){
	
	if(currentCustomer.customer.custMember)
		$('#membership-holder').remove();

	updateUser();
	
	$('#confirmButton').click(function(){
		if(confirm("Confirm Payment!")){
			 $('#confirmButton').prop('disabled', true);
			 
			 // change text in button to processing ...
			 $('#confirmButton').html('Processing...');
			
			 
			 // ajax to insert membership
			 var memberObj = {
					 membID : 0,
					 custID : currentCustomer.customer.custID,
					 paid:true,
					 amountPaid: 0,
					 dateJoined:null
			 };
			 
			 $.ajax({
				    type: "POST",
				    url: "http://localhost:8080/gym/api/memberships",
				    // The key needs to match your method's input parameter (case-sensitive).
				    data: JSON.stringify(memberObj),
				    contentType: "application/json; charset=utf-8",
				    dataType: "json",
				    success: function(data){
				    	// update local storage to set user as member
				    	$('#membership-holder').remove();
				    	
				    	currentCustomer.customer.custMember = true;
				    	currentCustomer.customer.cusBalance = 250; 
				    	updateUser();
				    
				    	alert('Successfull you currently have a balance of 250 Euro');
				    },
				    error: function(errMsg) {
				        alert(errMsg);
				        $('#confirmButton').prop('disabled', false);
				        $('#confirmButton').html('Subscribe');
				    }
				});
				
			 
			 
		 }
	});
	
	loadClasses();
	 
	 $('#btnCheckOut').on('click',function(){
		 var classes = [];
		 
		 $('#tbodyRegister').find('tr').each(function(i,item){
			 var $row = $(item);
			 var serviceId = $(item).attr('data-serviceId');
			 var weekDiff = parseInt($(item).find('td input.totWeeks').val());
			 var weekPrice = parseFloat($(item).find('td input.totWeeks').attr('data-week-price'));
			 
			 var originalAmount = weekDiff * weekPrice;
			 var expectedAmount = parseFloat($(item).find('td input.totWeeks').attr('data-price'));
			 
			 var startDate = new Date($(item).find('.start_date').attr('data-start'));
			 var actualEndDate = new Date($(item).find('.start_date').attr('data-end'));
				
			 var checkStart = new Date(startDate);
			 var endDate = new Date(checkStart.setDate(checkStart.getDate() + weekDiff * 7));
			
			 
			 if(endDate > actualEndDate)
				 endDate = actualEndDate;
			 
			 var classObj = {
				userClassesID:0,
				custID:currentCustomer.customer.custID,
				serviceID:serviceId,
				startDate:startDate,
				endDate:endDate,
				expectedAmount:expectedAmount,
				originalAmount:originalAmount,
				walkinCus: !currentCustomer.customer.custMember
			 };
			 
			 classes.push(classObj);
			 // post to user class resource
			 
		 });
		 
		 var owning = parseFloat($('#txtBalanceProcess').text());
		 if(owning < 0){
			 if(confirm("Please confirm payment of "+owning)){
				 // add payment
				 addPayment(owning);
				 postClassesToServer(classes);
			 }
			 else{
				
				 postClassesToServer(classes);
			 }
		 }
		 else{
			 console.log(classes);
			 postClassesToServer(classes);
		 }
		 
		
		 
	 });
	 
	 $(document).on('change','.totWeeks',function(){
		 var $input = $(this);
		 var price = parseFloat($input.attr('data-week-price'));
		 var weekDiff = parseInt($input.val());
		 var totalPrice = calculateTotal(weekDiff,price);
		 $input.attr('data-price',totalPrice);
		 $input.parent().find('span').html(totalPrice + ' '+ (weekDiff == 12 ?  '( 20% Disc)' : ''));
		 resetCart();
	 });
	 
	 $(document).on("click", '.un-register',function(){
		 var $button = $(this);
		 var serviceId = $button.closest('.service').attr('data-id');
		 var userClassId = 0;
		 // registeredClasses
		 // loop through registered classes
		      // check if registeredclass service id = serviceId
		           //if yes set userClassId = registeredclass userclassid and terminate loop
		           // if no continue loop
		 
		 //outside loop send ajax delete to delete userclass
		 // in sucess of ajax call loadclasses() 
		
	 })
	 
	 // register im
	 $(document).on("click", '.add-register',function(){
		 var $button = $(this);
		 
		 var serviceId = $button.closest('.service').attr('data-id');
		 var service = null;
		 for(var i = 0; i < allServices.length; i++) {
			    if (allServices[i].serviceID == parseInt(serviceId)) {
			    	service = allServices[i];
			        break;
			    }
			}
		 
		 if(service.categoryID != currentCustomer.customer.categoryID){
			 alert('Sorry you cannot take this class');
			 return;
		 }
		 else{
			 var found = false;
			 for(var i = 0; i < selectedServices.length; i++) {
				    if (selectedServices[i].serviceID == service.serviceID) {
				        found = true;
				        break;
				    }
				}
			 
			 if(found){
				 alert('Already selected');
			 }
			 else{
				 selectedServices.push(service);
				 var weekDiff = calculateWeekDifference(service.endDate);
				 var totPrice = calculateTotal(weekDiff, service.servicePrice);
				 
				 var html = '<tr data-serviceId="'+serviceId+'"><td><b>'+service.serviceName+'</b></td>'+
						'<td class="Amount">'+String(service.servicePrice) +' per week</td>'+
						'<td class="start_date" data-start="'+service.startDate+'" data-end="'+service.endDate+'">'+service.startDate+' to '+service.endDate+'</td>'+
						'<td>'+service.servicePrice * 12 +'</td>'+
						'<td><input data-week-price="'+service.servicePrice+'" data-price="'+totPrice+'"'+
						'class="totWeeks" value="'+weekDiff+'" min="1" max="'+weekDiff+'" type="number"/> <span> Total: '+
						totPrice + (weekDiff == 12 ?  '( 20% Disc)' : '') + '</span></td>'+
						'<td><button ="'
					'</tr>';
				 
				 $('#tbodyRegister').append(html);
				 $('#registerPanel').removeClass('hidden');
				 resetCart();
			 }
			
		 }
	 });
	
});

function calculateTotal(weeks, price){
	if(weeks != 12)
		return weeks * price;
	else
		return (weeks * price) - (weeks * price * 0.20);
}

function postClassesToServer(data){
	// loop through user classes and post each to server
	var totalSpent = 0;
	for(var i = 0; i < data.length; i++){
		// ajax post
		postUserClass(data[i]);
		totalSpent = totalSpent + data[i].expectedAmount;
	}
	
	 $('#tbodyRegister').html('');
	 $('#registerPanel').addClass('hidden');
}

function addPayment(owning){
	var paymentObj = {
		custID : currentCustomer.customer.custID,
		userAccountID:0,
		amountPaid: owning,
		datePaid: new Date(),
		bonusCredit: false,
		remark:'Payment for class registration'
	};
	
	
	
	 $.ajax({
		    type: "POST",
		    url: "http://localhost:8080/gym/api/useraccount",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: JSON.stringify(paymentObj),
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		    	// update current of user
		    	curBalance = currentCustomer.customer.cusBalance;
		    	currentCustomer.customer.cusBalance = curBalance + parseFloat(owning); 
		    	updateUser();
		    },
		    error: function(errMsg) {
		        alert(errMsg);
		    }
		});

}

function postUserClass(data){
	 $.ajax({
		    type: "POST",
		    url: "http://localhost:8080/gym/api/userclass",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: JSON.stringify(data),
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		    	loadClasses();
		    	curBalance = currentCustomer.customer.cusBalance;
		    	currentCustomer.customer.cusBalance = curBalance - data.expectedAmount; 
		    	updateUser();
		    },
		    error: function(errMsg) {
		        alert(errMsg);
		    }
		});

}

function loadClasses(){
	// get userClasses
	 $.ajax({
		    type: "GET",
		    url: "http://localhost:8080/gym/api/userclass/"+currentCustomer.customer.custID,
		    // The key needs to match your method's input parameter (case-sensitive).
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){ 
		    	registeredClasses = data;
		    	getAllServices();
		    }
	 });
}

function resetCart(){
	 var total = 0;
	 $('#tbodyRegister').find('tr td .totWeeks').each(function(i,item){
		 var $input = $(item);
		 var value = parseFloat($input.attr('data-price'));
		  total = total + value;
	 });
	
	 $('#txtSpend').html(total);
	 $('#txtBalance').html(currentCustomer.customer.cusBalance);
	 $('#txtBalanceProcess').html(currentCustomer.customer.cusBalance - total);
}

function calculateWeekDifference(endDate){
	var now = new Date();
	var Difference_In_Time = new Date(endDate).getTime() -  now.getTime(); 
  
// To calculate the no. of days between two dates 
	var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24); 
	var weekDiff = Math.ceil(Difference_In_Days/7);
	return (weekDiff > 12) ? 12 : weekDiff;
}




function getAllServices(){
	// ajax to get services
	 $.ajax({
		    type: "GET",
		    url: "http://localhost:8080/gym/api/services",
		    // The key needs to match your method's input parameter (case-sensitive).
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){ // call back function
		    	$("#serviceList").html('');
		    	allServices = data;
		    		$.each(data, function(index, service){

		    	var registrationHtML = 	'<button type="button" class="btn btn-success add-register">Register</button>';
		    	if(validateUserHasClass(service))
		    		registrationHtML = '<b>REGISTERED</b> <button type="button" class="btn btn-danger un-register">Un Register</button>';
		    			
		    	var html ='<div class="col-sm-4 service" data-id='+service.serviceID+'>'+
					'<div class="thumbnail" class ="kids">'+
						'<img '+
							'class="image-height "'+
							'src="'+getImageUrl(service)+'"'+
							' alt="'+service.serviceName+'">'+
						'<div class="caption">'+
							'<h3 class ="name">'+service.serviceName+'</h3>'+
							'<p class="price">Class Price: '+service.servicePrice+' <span></span></p>'+
						    '<p class="limit">Class Limit: '+service.serviceLimit+'<span></span></p>'+
							'<p>'+registrationHtML+'</p>'
					
						'</div>'+
					'</div>'+
				'</div>';
		    	
		    	$("#serviceList").append(html);
		    		
		    	});
		    
		    }
		    });
	
}

function getImageUrl(service){
	if(service.categoryID == 1){
		return 'https://businesscork.ie/wp-content/uploads/2018/05/Youth-Picture.jpg';
	}
	else if(service.categoryID == 3){
		// find old people
		return 'https://res.cloudinary.com/display97/image/upload/66055.jpg';
	}
	else{
		var imageUrl = '';
		switch(service.serviceName){
		case 'Zumba' : imageUrl = 'https://zlife.zumba.com/wp-content/uploads/2014/06/B2Studio_.jpg';break;
		case 'Aerobics' : imageUrl = 'https://goodspaguide.co.uk/images/uploads/Features/Aerobics/aerobics-class.jpg';break;
		case 'Spinning' : imageUrl = 'https://dta0yqvfnusiq.cloudfront.net/everybodybalance/2016/08/spin_group_cropped-160807-57a75575e2ebd-1200x597.jpg';break;
		}
	 return imageUrl;
	}
}

function validateUserHasClass(service){
	
	var found = false;
	for(var i = 0; i < registeredClasses.length; i++) {
	    if (registeredClasses[i].serviceID == service.serviceID) {
	        found = true;
	        break;
	    }
	}
	 return found;
}


(function(){
	  $('.carousel-showmanymoveone .item').each(function(){
	    var itemToClone = $(this);

	    for (var i=1;i<6;i++) {
	      itemToClone = itemToClone.next();

	      // wrap around if at end of item collection
	      if (!itemToClone.length) {
	        itemToClone = $(this).siblings(':first');
	      }

	      // grab item, clone, add marker class, add to collection
	      itemToClone.children(':first-child').clone()
	        .addClass("cloneditem-"+(i))
	        .appendTo($(this));
	    }
	  });
	}());




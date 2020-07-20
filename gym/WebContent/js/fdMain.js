var rootURL = "http://localhost:8080/gym/api/services";
var currentService;

function openCity(evt, cityName) {
	// Declare all variables
	var i, tabcontent, tablinks;

	// Get all elements with class="tabcontent" and hide them
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	// Get all elements with class="tablinks" and remove the class "active"
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}

	// Show the current tab, and add an "active" class to the button that opened
	// the tab
	document.getElementById(cityName).style.display = "block";
	evt.currentTarget.className += " active";
}

var search = function(searchKey) {
	if (searchKey == '') {
		findAll();
	} else {
		findByName(searchKey);
	}
};

var newService = function() {
	$('#btnDelete').hide();
	currentService = {};
	formReset(); // Display empty form
};

var findAll = function() {
	console.log('findAll');
	$.ajax({
		type : 'GET',
		url : rootURL,
		dataType : "json",
		success : renderList
	});
};

var findData = function() {
	console.log('findAll');
	$.ajax({
		type : 'GET',
		url : rootURL,
		dataType : "json",
		success : renderDataTable
	});
};

var findByName = function(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type : 'GET',
		url : rootURL + '/search/' + searchKey,
		dataType : "json",
		success : renderList
	});
};

var findById = function(serviceId) {
	console.log('findById: ' + serviceId);
	$.ajax({
		type : 'GET',
		url : rootURL + '/' + serviceId,
		dataType : "json",
		success : function(data) {
			$('#btnDelete').show();
			console.log('findById success: ' + data.serviceName);
			currentMovie = data;
			renderDetails(currentService);
		}
	});
};

var addService = function() {
	console.log('addService');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL,
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Service created successfully');
			$('#btnDelete').show();
			$('#serviceId').val(data.serviceID);
			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('addService error: ' + textStatus);
		}
	});
};

var updateService = function() {
	console.log('updateService');
	$.ajax({
		type : 'PUT',
		contentType : 'application/json',
		url : rootURL + '/' + $('#serviceId').val(),
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Service updated successfully');
			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('updateService error: ' + textStatus);
		}
	});
};

var deleteService = function() {
	console.log('deleteService');
	$.ajax({
		type : 'DELETE',
		url : rootURL + '/' + $('#serviceId').val(),
		success : function(data, textStatus, jqXHR) {
			alert('Service deleted successfully');
			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('deleteService error');
		}
	});
};

function renderList(data) { 
	var list = data;

	$('#serviceList li').remove();
	$.each(data, function(index, service) {
		$('#serviceList').append(
				'<li><a href="#" id="' + service.serviceID + '">' + service.serviceName
						+ '</a></li>');
	});

	output = '<div class="row">';
	$.each(list, function(index, service) {
			var img = "pics/pics/" + service.picture;
			output += ('<div class="col-sm-6 col-md-4 col-lg-3"><div class="card"><img src=' + '"' + img	+ '"'
			+ 'height="150"><p>Name: ' + service.serviceName + '</p><p>Service Insturtor: '
			+ service.serviceInstructorFirst + " "+service.serviceInstructorLast + '</p><p>Price: ' + service.servicePrice + '</p></div></div>');
		});
	output += '</div>';
	$('#productList').append(output);
}

function renderDataTable(data){
	var list = data;
	
	console.log("response");
	$('#table_body tr').remove();
	$.each(list, function(index, service) {
			$('#table_body').append('<tr><td>' + service.serviceID + '</td><td>' + service.serviceName + '</td><td>'
				+ service.servicePrice + '</td><td>' + service.serviceLimit	+ '</td><td>' + service.startDate
				+ '</td><td><a data-toggle="modal" href="#theOscars" id="' + service.serviceID + '">Edit</a></td></tr>');
			});
	$('#table_id').DataTable();
}

var renderDetails = function(service) {
	$('#serviceId').val(service.serviceID);
	$('#name').val(service.serviceName);
	$('#servicePrice').val(service.servicePrice);
	$('#serviceLimit').val(service.serviceLimit);
	$('#serviceInstructorFirst').val(service.serviceInstructorFirst);
	$('#serviceInstructorLast').val(service.serviceInstructorLast);
	$('#categoryID').val(service.categoryID);
	$('#startDate').val(service.startDate);
	$('#endDate').val(service.endDate);
	
};

var formToJSON = function() {
	var serviceId = $('#serviceId').val();
	return JSON.stringify({
		"serviceID" : serviceId == "" ? null : serviceId,
		"serviceName" : $('#name').val(),
		"servicePrice" : $('#servicePrice').val(),
		"serviceLimit" : $('#serviceLimit').val(),
		"serviceInstructorFirst" : $('#serviceInstructorFirst').val(),
		"serviceInstructorLast" : $('#serviceInstructorLast').val(),
		"categoryID" : $('#categoryID').val(),
		"startDate" : $('#startDate').val(),
		"endDate" : $('#endDate').val()
	});
};

var formReset = function(){
	$('#serviceId').val("");
	$('#name').val("");
	$('#servicePrice').val("");
	$('#serviceLimit').val("");
	$('#serviceInstructorFirst').val("");
	$('#serviceInstructorLast').val("");
	$('#categoryID').val("");
	$('#startDate').val("");
	$('#endDate').val("");
}

$(document).ready(function() {
	$('#Home').show();
	
	$('#admin').click(function(){
		findData();
	});
	
	// Nothing to delete in initial application state
	$('#btnDelete').hide();

	// Register listeners
	$('#btnSearch').click(function() {
		search($('#searchKey').val());
		return false;
	});

	// Trigger search when pressing 'Return' on search key input field
	$('#searchKey').keypress(function(e) {
		if (e.which == 13) {
			search($('#searchKey').val());
			e.preventDefault();
			return false;
		}
	});

	$('#btnAdd').click(function() {
		newService();
		return false;
	});

	$('#btnSave').click(function() {
		if ($('#serviceId').val() == '')
			addService();
		else
			updateMovie();
			location.reload();
		return false;
	});

	$('#btnDelete').click(function() {
		deleteService();
		location.reload();
		return false;
	});

	$(document).on("click", '#table_body a', function() {
		findById(this.id);
	});

	$(document).on("click", '#serviceList a', function() { 
		findById(this.id);
	});

	// Replace broken images with generic oscars photo
	$("img").error(function() {
		$(this).attr("src", "pics/pics/logo.jpg");
	});

	// reset form
	$('#serviceId').val("");
	$('#name').val("");
	$('#servicePrice').val("");
	$('#serviceLimit').val("");
	$('#serviceInstructorFirst').val("");
	$('#serviceInstructorLast').val("");
	$('#categoryID').val("");
	$('#startDate').val("");
	$('#endDate').val("");

	findAll();
});

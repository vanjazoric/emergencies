$(window).load(function() {
	profile();
});

this.getParameterByName = function(name, url) {
	if (!url)
		url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
			.exec(url);
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
};

var profile = function() {
	window.location.search;
	var username = this.getParameterByName('username');
	$.ajax({
		type : "POST",
		url : "api/users/profile",
		data : {
			"username" : username
		},
		success : function(user) {
			if (user.role == "VOLUNTEER"){
				var state;
				if (user.isBlocked == true){
				state = "Blokiran";
				}
			}
			$('#picture').append("<img src=./images/" + user.photo + ">");
			$('#name').append("<p>&emsp;" + user.firstName + " " 
			+ user.lastName + " ["+state+"]</p>");
			$('#profile').css('margin-top', '3%');
			$('#info').append("<img class=\"icon\" src=./images/name.png>");
			$('#info').append("<p>&emsp;"+ user.username+"</p><br><br>");
			$('#info').append("<img class=\"icon\" src=./images/email.png>");
			$('#info').append("<p>&emsp;"+ user.email + "</p><br><br>");
			$('#info').append("<img class=\"icon\" src=./images/phone.png>");
			$('#info').append("<p>&emsp;"+ user.phone + "</p><br><br>");
			$('#info').append("<img class=\"icon\" src=./images/location.png>");
			if (user.role == "VOLUNTEER"){
				$('#info').append("<p>&emsp;"+ user.territory.name + "</p><br><br>");
			$.ajax({
				type : "GET",
				url : "api/users/loginTest",
				success : function(response) {
					if (response != null && response.role == "ADMIN"){
						if (user.isBlocked == false){
							$('#block').append("<button id='blockUser' " 
							+ "class='btn btn-primary btn-sm'>Blokiraj</button>");
							document.getElementById("blockUser").addEventListener("click", function() {
						blockUser(username); 
						}, false);
						}
						else{
							$('#block').append("<button id='blockUser' " 
							+ "class='btn btn-primary btn-sm'>Odblokiraj</button>");
							document.getElementById("blockUser").addEventListener("click", function() {
						unblockUser(username);
						}, false);
					}
					}
					if (response != null && response.role == "VOLUNTEER"){
						if(response.username == username){
							$('#info').append("<a href='emergencies.html'>Moje vanredne situacije</a>");
						}
					}
					}
				});
			}
			}
		});				
}

function blockUser(username){
	if (confirm('Da li ste sigurni da želite da blokirate korisnika?\n'+
			'Neće mu biti dozvoljene aktivnosti.')) {
	$.ajax({
		type : "POST",
		url : "api/users/blockUser",
		data : {
			"username" : username
		},
		success : function(response) {
			$('#blockUser').html("Odblokiraj");
		}});
	}else{
		return false;
	}
}

function unblockUser(username){
	if (confirm('Da li ste sigurni da želite da odblokirate korisnika\n'+
			'Biće mu dozvoljene aktivnosti.')) {
	$.ajax({
		type : "POST",
		url : "api/users/unblockUser",
		data : {
			"username" : username
		},
		success : function(response) {
			$('#blockUser').html("Blokiraj");
		}});
	}else{
		return false;
	}
}
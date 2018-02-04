$(window).load(function() {
	loginTest();
});

$(document).ready(function() {
	$("#addEm").click(function(e) {
		e.preventDefault();
		$
		.ajax({
			url : 'api/users/loginTest',
			method : 'get',
			dataType : 'json',
			success : function(response) {
				if (response != null && response.isBlocked==true) {
					alert("Ne možete prijaviti vanrednu situaciju jer ste blokirani.");
				}
	}
		})
	})});

var loginTest = function() {
	$
			.ajax({
				url : 'api/users/loginTest',
				method : 'get',
				dataType : 'json',
				success : function(response) {
					if (response == null) {
						$('#log').append(
								"<a href=\"login.html\">Log In</a>");
					} else {
						$('#log').append(
								"<a href=\"rest/user/logout\">Log Out</a>");
						if (response.role == "ADMIN") {
							$('#teritories').append("<a class 'nav-link' href='territories.html'>Teritorije</a>");
							$('#user').html(
									"<a href=\"profile.html?userName="
											+ response.userName + "\">"
											+ response.firstName + " "
											+ response.lastName + " [admin]</a>");
						} else if (response.role == "VOLUNTEER") {
							if (response.isBlocked == true){
								$('#user').append(
										"<a href=\"profile.html?userName="
												+ response.userName + "\">"
												+ response.firstName + " "
												+ response.lastName + " [blocked]</a>");
							}
							else{
							$('#user').append(
									"<a href=\"profile.html?userName="
											+ response.userName + "\">" + response.firstName
											+ " " + response.lastName + "</a>");		
							}
							}
					}
				},
				error : function(err) {
					alert("neka greska");
				}

			});
};


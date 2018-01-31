$(window).load(function() {
	loadUsers();
	loginTest();
});

var loadUsers = function() {
	$.ajax({
		url : 'api/users',
		method : 'get',
		dataType : 'json',
		success : function(response) {
		},
		error : function(err) {
		}
	});
};

var loginTest = function() {
	$
			.ajax({
				url : 'api/users/loginTest',
				method : 'get',
				dataType : 'json',
				success : function(response) {
					if (response.role != null) {
						window.location.replace("index.html");
						$('#loggedIn').append(response.username.toString());
						$('#log')
								.append(
										"<a class='nav-link' href=\"api/users/login\">Log In</a>");
					}
				},
				error : function(err) {
				}
			});
};

$(document)
		.ready(
				function() {
					$("#loginForm")
							.submit(
									function(event) {
										var username = $("#inputUserName")
												.val();
										var password = $("#inputPassword")
												.val();
										$
												.ajax({
													type : "POST",
													url : "api/users/login",
													data : {
														"username" : username,
														"password" : password
													},
													contentType : "application/x-www-form-urlencoded; charset=utf-8",
													success : function(response) {
														if (response != null) {
															var user = JSON
																	.stringify(response);
															window.location
																	.replace("index.html");
														} else {
															$('#inputUserName')
																	.val('');
															$('#inputPassword')
																	.val('');
															$('#errorLogin')
																	.append(
																			"<img style=\"height=\"20"
																					+ " width=\"20\" src=\"./images/error.png\"/>");
															$('#errorLogin')
																	.append(
																			"<p style=\"color:red;display: inline;\">&ensp;The username or password is incorrect.</p>");

														}
													}
												});
										event.preventDefault();
									})
				});
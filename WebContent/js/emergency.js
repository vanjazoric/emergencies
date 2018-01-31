$(window).load(function() {
	//loginTest();
	getTerritories();
});

var loginTest = function() {
	$
			.ajax({
				url : 'api/users/loginTest',
				method : 'get',
				dataType : 'json',
				success : function(response) {
					if (response.role == null) {
						window.location.replace("login.html");
					} else {
						$('#loggedIn').append("<a class='nav-link active'>"+response.username.toString()+"</a>");
						$('#log')
								.append(
										"<a class='nav-link' href=\"api/users/logout\">Log Out</a>");
					}
				},
				error : function(err) {
				}
			});
};
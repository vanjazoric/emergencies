$(window).load(function() {
	loadEmergencies();
});

var loadEmergencies = function() {
	$
			.ajax({
				url : 'api/emergencies/volunteer',
				method : 'GET',
				success : function(response) {
					var str = JSON.stringify(response);
					$
							.ajax({
								type : "GET",
								url : "api/users/loginTest",
								success : function(response) {
									var us = JSON.stringify(response);
									emergenciesView(str, us);
								}
							});
				},
				error : function(err) {
					alert(err);
				}
			})
};
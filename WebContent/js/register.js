$(window).load(function() {
	getTerritories();
});

$(document).on(
		'keyup',
		'#confirmPassword',
		function() {
			var x = $("#confirmPassword").val();
			var pass = $('#password').val();
			if (x != pass) {
				$("#confirmPassword").css("background-color", "#ffd9b3").css(
						"border-color", "#ff5c33");
				$("#match").text("Lozinke se ne poklapaju.");
				$("#match").css("visibility", "visible")
						.css("color", "#ff5c33");
			} else {
				$("#confirmPassword").css("background-color", "#ccffcc").css(
						"border-color", "#009900");
				$("#match").text("Lozinke se poklapaju.");
				$("#match").css("visibility", "visible").css("color", "green");
			}
		});

$(document).on(
		'blur',
		'#username',
		function() {
			var username = $("#username").val();
			$.ajax({
				url : 'api/users/checkUsername',
				method : 'post',
				data : {
					"username" : username
				},
				success : function(response) {
					if (response == "false"){
						alert("To korisničko ime već postoji.");
						$("#username").val('');
					}
					}
		});});
			
			$(document).on(
					'blur',
					'#email',
					function() {
						var email = $("#email").val();
						$.ajax({
							url : 'api/users/checkEmail',
							method : 'post',
							data : {
								"email" : email
							},
							success : function(response) {
								if (response == "false"){
									alert("Ta email adresa već postoji.");
									$("#email").val('');}
								}
					});
					});

var getTerritories = function() {
	$.ajax({
		url : 'api/territories',
		method : 'get',
		dataType : 'json',
		success : function(response) {
			var str = JSON.stringify(response);
			var territories = JSON.parse(str);
			
			  territories.sort(function(a, b) { return
			  a.name.localeCompare(b.name); });
			 
			for (var i = 0; i < territories.length; i++) {
				$('#territory').append(
						"<option>" + territories[i].name + "</option>");
			}
		},
		error : function(err) {
			alert(err);
		}
	});
}
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

$(window).load(function() {
	getTerritories();
});
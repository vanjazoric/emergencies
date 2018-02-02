$(window).load(function() {
	loadEmergencies();

});

var setVolunteer = function(emergencyId) {
	var username = $('#addVolunteer').val();
	if (username == null) {
		alert("Odaberite volontera!");
	} else {
		$.ajax({
			type : "POST",
			url : "api/emergencies/update",
			data : {
				"emergencyId" : emergencyId,
				"username" : username
			},
			success : function(response) {
				location.reload(true);
			}
		});
	}
}

var loadVolunteers = function(id) {
	$.ajax({
		url : "api/volunteers/" + id,
		method : 'GET',
		success : function(response) {
			var str = JSON.stringify(response);
			var volunteers = JSON.parse(str);
			for (var i = 0; i < volunteers.length; i++) {
				$("#addVolunteer" + id + id).append(
						"<option value='" + volunteers[i].username + "'><b>"
								+ volunteers[i].username
								+ "</b>&nbsp;&bull;&nbsp;"
								+ volunteers[i].firstName + " "
								+ volunteers[i].lastName + "</option>");
			}
		},
		error : function(err) {
			alert(err);
		}
	})

}

var loadEmergencies = function() {
	$
			.ajax({
				url : 'api/emergencies',
				method : 'GET',
				success : function(response) {
					var str = JSON.stringify(response);
					$
							.ajax({
								type : "GET",
								url : "api/users/loginTest",
								success : function(response) {
									if (us != null) {
										$('#loggedIn').append(
												"<a class='nav-link active'>"
														+ response.username
																.toString()
														+ "</a>");
										$('#log')
												.append(
														"<a class='nav-link' href=\"api/users/logout\">Log Out</a>");
									}
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

var emergenciesView = function(e, us) {
	if (us != null) {
		var user = JSON.parse(us);
	}
	var emergencies = JSON.parse(e);
	for (var i = 0; i < emergencies.length; i++) {
		if (emergencies[i].state == "ARCHIVED") {
			// $("#commButton"+emergencies[i].id).hide();
			// $("#"+emergencies[i].id+i).hide();
		}
		var date = DateFormat.format.date(new Date(emergencies[i].date),
				"dd MMM yyyy, HH:mm");
		$('#emergencies')
				.append(
						"<div id=emergency"
								+ emergencies[i].id
								+ " class='emergency'><i id='urgency"
								+ emergencies[i].id
								+ "' class='fa fa-circle'></i><i class='description'>"
								+ emergencies[i].description
								+ "</i><table class='data'><tr><td class='locationTd'>Lokacija: <b>"
								+ emergencies[i].location
								+ "</b></td><td class='imgTd' rowspan='4'><img class='imgE' src='./images/"
								+ emergencies[i].photo + "'></td></tr>"
								+ "<tr><td>Teritorija: "
								+ emergencies[i].territory.name
								+ "</td></tr><tr><td>Datum: " + date
								+ "</td></tr><tr><td>Opština: "
								+ emergencies[i].municipality
								+ "</td></tr><tr></tr><tr><td id='hasVolunteer"
								+ emergencies[i].id
								+ "'></td><td><div id='addVolunteer"
								+ emergencies[i].id
								+ "'></div></td></tr></table>");
		renderEmergency(emergencies, i, us);
		loadVolunteers(emergencies[i].id);
		/*
		 * for (var j = 0; j < emergencies[i].comments.length; j++) { var date =
		 * DateFormat.format.date(new Date( emergencies[i].comments[j].date),
		 * "dd MMM yyyy, HH:mm:ss"); $("#" + emergencies[i].id) .append( "<div
		 * id=" + emergencies[i].id + emergencies[i].comments[j].id + ">" + "<img
		 * style=\" object-fit: cover; width:35px;height:35px;\" src=./images/" +
		 * emergencies[i].comments[j].user.picture + ">&emsp;<a
		 * class='profileView' " + "href=\"profile.html?userName=" +
		 * emergencies[i].comments[j].user.userName + "\">" +
		 * emergencies[i].comments[j].user.userName + "</a>&emsp;&#8226;&emsp;<p style=\"display:inline\">" +
		 * date + "</p><div class=\"deleteComment\" " + "id=\"deleteComment" +
		 * emergencies[i].comments[j].id + "\">" + "</div><div
		 * class=\"rating\" id=rating" + emergencies[i].comments[j].id + "></div><p class=\"commentText\">" +
		 * emergencies[i].comments[j].text + "</p><hr></div>");
		 * renderComment(emergencies, i, j, us); }
		 */
	}
}

function renderEmergency(emergencies, i, us) {
	if (emergencies[i].urgency == "CRVENO") {
		$("#urgency" + emergencies[i].id).css('color', 'red');
	} else if (emergencies[i].urgency == "NARANDŽASTO") {
		$("#urgency" + emergencies[i].id).css('color', 'orange');
	} else if (emergencies[i].urgency == "PLAVO") {
		$("#urgency" + emergencies[i].id).css('color', 'blue');
	}
	if (us != null) {
		var user = JSON.parse(us);
		if (user.role == "ADMIN") {
			$("#addVolunteer" + emergencies[i].id)
					.append(
							"<select id='addVolunteer"
									+ emergencies[i].id
									+ emergencies[i].id
									+ "'>"
									+ "<option selected disabled>Izaberi...</option></select>"
									+ "<input class='btn btn-primary btn-sm' onclick='setVolunteer("
									+ emergencies[i].id
									+ ")' id='addV' type='submit' value='Postavi volontera'>");
		}
	}
	if (emergencies[i].volunteer == null) {
		$("#hasVolunteer" + emergencies[i].id).append("Volonter: /");
	} else {
		$("#hasVolunteer" + emergencies[i].id).html(
				"Volonter:" + emergencies[i].volunteer.username);
	}
	/*
	 * if (emergencies[i].isBlocked == false) { $("#disableComments" +
	 * emergencies[i].id).append( "<a href='#' " + "onclick=\"disableComments(" +
	 * i + "," + emergencies[i].id + ")\">Disable comments</a>"); } else {
	 * $("#disableComments" + emergencies[i].id).append( "<a href='#' " +
	 * "onclick=\"enableComments(" + i + "," + emergencies[i].id + ")\">Enable
	 * comments</a>"); } }
	 */
};
/*
 * function renderComment(emergencies, i, j, us) { if (us != undefined) { var
 * user = JSON.parse(us); if (user.role == "REGISTERED_USER") {
 * 
 * $("#deleteComment" + emergencies[i].comments[j].id).append(
 * "&emsp;&#8226;&emsp;<a href='#'" + "onclick = \"deleteComment(" +
 * emergencies[i].id + "," + emergencies[i].comments[j].id + ")\">Delete</a>"); }
 * if (user.role == "ADMINISTRATOR") { $("#deleteComment" +
 * emergencies[i].comments[j].id).append( "&emsp;&#8226;&emsp;<a href='#'" +
 * "onclick = \"deleteComment(" + emergencies[i].id + "," +
 * emergencies[i].comments[j].id + ")\">Delete</a>"); } } };
 */
$(window).load(function() {
	loadEmergencies();

});

function sort(a, b) {
	if (a.firstname < b.firstname)
		return -1;
	if (a.firstname > b.firstname)
		return 1;
	return 0;
};

var getTerritories = function() {
	$.ajax({
		url : 'api/territories',
		method : 'get',
		dataType : 'json',
		success : function(response) {
			renderTerritories();
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
	return this;
};


var setVolunteer = function(id) {
	var username = $("#addVolunteer" + id + id).val();
	if (username == null) {
		alert("Odaberite volontera!");
	} else {
		$.ajax({
			type : "POST",
			url : "api/emergencies/update",
			data : {
				"emergencyId" : id,
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
		if (emergencies[i].state == "ACTIVE") {
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
								+ "</b></td><td id='archive"+emergencies[i].id+"'></td></tr>"
								+ "<tr><td>Teritorija: "
								+ emergencies[i].territory.name
								+ "</td><td class='imgTd' rowspan='3'><img class='imgE' src='./images/"
								+ emergencies[i].photo
								+ "'></td></tr><tr><td>Datum: "
								+ date
								+ "</td></tr><tr><td>Opština: "
								+ emergencies[i].municipality
								+ "</td></tr><tr></tr><tr><td id='hasVolunteer"
								+ emergencies[i].id
								+ "'></td><td><div id='addVolunteer"
								+ emergencies[i].id
								+ "'></div></td></tr><tr><td colspan='2'><br></td></tr><tr><td class='activeE"
								+ emergencies[i].id
								+ "'></td><td class='commButton"
								+ emergencies[i].id
								+ "'></td></tr></table><br><div id="
								+ emergencies[i].id + "></div></div>");
		renderEmergency(emergencies, i, us);
		loadVolunteers(emergencies[i].id);

		for (var j = 0; j < emergencies[i].comments.length; j++) {
			var date = DateFormat.format.date(new Date(
					emergencies[i].comments[j].date), "dd MMM yyyy, HH:mm:ss");
			$("#" + emergencies[i].id)
					.append("<div id='"
									+ emergencies[i].id
									+ emergencies[i].comments[j].id
									+ "'><img class='thumbnail' src='./images/"
									+ emergencies[i].comments[j].user.photo
									+ "'>"
									+ "&emsp;<a class='profileView' "
									+ "href=\"profile.html?userName="
									+ emergencies[i].comments[j].user.username
									+ "\">"
									+ emergencies[i].comments[j].user.username
									+ "</a>&emsp;&#8226;&emsp;<p style=\"display:inline\">"
									+ date + "</p><p class=\"commentText\">"
									+ emergencies[i].comments[j].text
									+ "</p><hr></div>");

			renderComment(emergencies, i, j, us);
		}
	}
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

	if (emergencies[i].state == "ACTIVE" && us != null) {
		$(".activeE" + emergencies[i].id).append(
				"<input type=\"text\"" + "id=" + emergencies[i].id + i
						+ " size=55px;>&emsp;");
		$(".commButton" + emergencies[i].id).append(
				"<input type=\"button\" value=\"Dodaj komentar\" "
						+ "id=commButton" + emergencies[i].id
						+ " class=\"btn btn-primary btn-sm\"" + "onclick=\"comment("
						+ emergencies[i].id + i + "," + emergencies[i].id
						+ ")\">");
	}

	if (us != null) {
		var user = JSON.parse(us);
		if (user.role == "ADMIN") {
			$("#archive" + emergencies[i].id).append(
					"<a style='text-decoration: underline; 'href='#' onclick ='archive("
					+emergencies[i].id+")'>Arhiviraj</a>");
			$("#addVolunteer" + emergencies[i].id)
					.append(
							"<select id='addVolunteer"
									+ emergencies[i].id
									+ emergencies[i].id
									+ "'>"
									+ "<option selected disabled>Izaberi...</option></select>"
									+ "<input class='btn btn-secondary btn-sm' onclick='setVolunteer("
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
};

function renderComment(emergencies, i, j, us) {
	/*
	 * if (us != undefined) { var user = JSON.parse(us); if (user.role ==
	 * "REGISTERED_USER") {
	 * 
	 * $("#deleteComment" + emergencies[i].comments[j].id).append(
	 * "&emsp;&#8226;&emsp;<a href='#'" + "onclick = \"deleteComment(" +
	 * emergencies[i].id + "," + emergencies[i].comments[j].id + ")\">Delete</a>"); }
	 * if (user.role == "ADMINISTRATOR") { $("#deleteComment" +
	 * emergencies[i].comments[j].id).append( "&emsp;&#8226;&emsp;<a href='#'" +
	 * "onclick = \"deleteComment(" + emergencies[i].id + "," +
	 * emergencies[i].comments[j].id + ")\">Delete</a>"); } }
	 */
};

function comment(id1, id2) {
	var date = DateFormat.format.date(new Date(), "MMM dd yyyy HH:mm:ss");
	var commentText = document.getElementById(id1).value;
	document.getElementById(id1).value = "";
	$
			.ajax({
				type : "GET",
				url : "api/users/loginTest",
				success : function(user) {
					if (user.role != "ADMIN") {
						if (user.isBlocked == true) {
							alert("Ne možete dodati komentar jer ste blokirani.");
							return false;
						}
					}
					$
							.ajax({
								type : "POST",
								url : "api/comments",
								data : {
									"comment" : commentText,
									"id" : id2,
									"username" : user.username,
									"date" : date
								},
								success : function(response) {
									var comm = JSON.stringify(response);
									var comment = JSON.parse(comm);
									$("#" + id2)
											.append(
													"<div id=c"
															+ id2
															+ comment.id
															+ ">"
															+ "<img class='thumbnail' src=./images/"
															+ user.photo
															+ ">&emsp;<a href='#' class='profileView' >"
															+ user.username
															+ "</a>&emsp;&#8226;&emsp;<p style=\"display:inline\">"
															+ date
															+ "</p><p class=\"commentText\">"
															+ commentText
															+ "</p><hr></div>");
								}
							});
				}
			});
};

var archive = function(id){
if (confirm('Vanredna situacija će biti arhivirana.')) {
	$.ajax({
		type : 'POST',
		url : 'api/emergencies/archive',
		data : {
			"id" : id
		},
		success : function(response){
			alert(response);
			if (response == "true"){
				location.reload(true);
			}
		}
	});
}
	else{
		return false;
	}
}

function loadFilteredByDate(date) {
	var d = DateFormat.format.date(date, "dd MMM yyyy, HH:mm");
	$.ajax({
		url : 'api/emmergencies/filterDate',
		method : 'POST',
		data : {
			"date" : d
		},
		success : function(response) {
			var str = JSON.stringify(response);
			if (str.length > 2){
				$.ajax({
					type : "GET",
					url : "api/users/loginTest",
					success : function(response) {
						var us = JSON.stringify(response);
						snippetsView(str,us);
					}
				});
			}
			else{
				alert("Ne postoji vanredna situacija za izabrani datum.");
			}
		},
		error : function(err) {
			alert(err);
		}
	});
};

function loadFilteredByUrgency(urg) {
	$.ajax({
		url : 'api/emergencies/filterUrgency',
		method : 'POST',
		data : {
			"urgency" : urg
		},
		success : function(response) {
			var str = JSON.stringify(response);
			if (str.length > 2){
				$.ajax({
					type : "GET",
					url : "api/users/loginTest",
					success : function(response) {
						var us = JSON.stringify(response);
						snippetsView(str,us);
					}
				});
			}
			else{
				alert("Ne postoji vanredna situacija sa zadatom hitnošću.");
			}
		},
		error : function(err) {
			alert(err);
		}
	});
};

$(document).ready(function() {
	$("#addEm").click(function(e) {
	$.ajax({
		url : 'api/emergencies/filterTerritory',
		method : 'POST',
		data : {
			"ter" : ter
		},
		success : function(response) {
			var str = JSON.stringify(response);
			if (str.length > 2){
				$.ajax({
					type : "GET",
					url : "api/users/loginTest",
					success : function(response) {
						var us = JSON.stringify(response);
						snippetsView(str,us);
					}
				});
			}
			else{
				alert("Ne postoji vanredna situacija sa zadatom teritorijom.");
			}
		},
		error : function(err) {
			alert(err);
		}
	});
})});
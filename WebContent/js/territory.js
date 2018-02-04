$(window).load(function() {
	loginTest();
});

var loginTest = function() {
	$.ajax({
		url : 'api/users/loginTest',
		method : 'get',
		dataType : 'json',
		success : function(response) {
				if (response == null || response.role != "ADMIN") {
				window.location.href = "index.html";
			} else {
				getTerritories();
			}
		}
	})
};

var getTerritories = function() {
	$.ajax({
		url : 'api/territories',
		method : 'get',
		dataType : 'json',
		success : function(response) {
			var str = JSON.stringify(response);
			var territories = JSON.parse(str);
			renderTerritories(territories);
		},
		error : function(err) {
			alert(err);
		}
	});
};

var renderTerritories = function(territories) {
	$('#territories')
			.append(
					"<a style='margin-left:30%;' href='addTerritory.html'>Dodaj novu teritoriju</a>");
	for (var i = 0; i < territories.length; i++) {
		$('#territories')
				.append(
						"<div id='territory"
								+ territories[i].id
								+ "' class='territory'><table class='dataT'><tr><td><img id='imgT' src=\"./images/delete.png\" "
								+ "onclick=\"deleteTerritory("
								+ territories[i].id
								+ ")\"></td><td></td><td></td></tr><tr><td>Naziv: "
								+ territories[i].name
								+ "</td><td><img id='imgEdit' src=\"./images/edit.png\"><input type='text' id='editName"
								+ territories[i].id
								+ "'></td><td><input type='button' value='Sačuvaj' class='btn btn-primary btn-sm' onclick='changeName("
								+ territories[i].id
								+ ")'></td></tr>"
								+ "<tr><td>Površina: "
								+ territories[i].area
								+ "</td><td><img id='imgEdit' src=\"./images/edit.png\"><input type='text' id='editArea"
								+ territories[i].id
								+ "'></td><td><input type='button' value='Sačuvaj' class='btn btn-primary btn-sm' onclick='changeArea("
								+ territories[i].id
								+ ")'></td></tr><tr><td>Broj stanovnika: "
								+ territories[i].population
								+ "</td><td><img id='imgEdit' src=\"./images/edit.png\"><input type='text' id='editPopulation"
								+ territories[i].id
								+ "'></td><td><input type='button' class='btn btn-primary btn-sm' value='Sačuvaj' onclick='changePopulation("
								+ territories[i].id
								+ ")'></td></tr></table></div>");
	}
}

var deleteTerritory = function(id) {
	if (confirm('Teritorija će biti obrisana.')) {
		$.ajax({
			type : "POST",
			url : "api/territories/delete",
			data : {
				"id" : id
			},
			success : function(response) {
				$("#territory" + id).hide(1000);
			}
		});
	} else {
		return false;
	}
};

var changeName = function(id){
	var name = $("#editName"+id).val();
	$.ajax({
		type: "POST",
		url: "api/territories/changeName",
		data: {
			"id" : id,
			"name" : name
		},
		success: function(response){
			if (response=="true"){
				location.reload();
			}
		}
	});
}

var changeArea = function(id){
	var area = $("#editArea"+id).val();
	$.ajax({
		type: "POST",
		url: "api/territories/changeArea",
		data: {
			"id" : id,
			"area" : area
		},
		success: function(response){
			if (response==true){
				location.reload();
			}
		}
	});
}

var changePopulation = function(id){
	var population = $("#editPopulation"+id).val();
	$.ajax({
		type: "POST",
		url: "api/territories/changePopulation",
		data: {
			"id" : id,
			"population" : population
		},
		success: function(response){
			if (response==true){
				location.reload();
			}
		}
	});
}

var addTerritory = function(id){
	$.ajax({
		type: "POST",
		url: "api/territories/delete",
		data: {
			"id" : id,
		},
		success: function(response){
			if (response==true){
				window.location.href="territories.html"
			}
		}
	});
}
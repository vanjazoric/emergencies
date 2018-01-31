var getTerritories = function() {
	$.ajax({
		url : 'api/territories',
		method : 'get',
		dataType : 'json',
		success : function(response) {
			var str = JSON.stringify(response);
			var territories = JSON.parse(str);
			
			  territories.sort(function(a, b) { return
			  a.territory.localeCompare(b.lterritory); });
			 
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
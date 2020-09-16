$(document).ready(function() {

	$(document).on("click", "input[type='checkbox']", function(event) {
		var checkedCheckbox = [];
		if ($("#encheresouvertes").is(':checked')) {
			checkedCheckbox.push($("#encheresouvertes").attr('id'))
		}
		if ($("#enchereswin").is(':checked')) {
			checkedCheckbox.push($("#enchereswin").attr('id'))
		}
		if ($("#encheresencours").is(':checked')) {
			checkedCheckbox.push($("#encheresencours").attr('id'))
		}
		if ($("#ventesnon").is(':checked')) {
			checkedCheckbox.push($("#ventesnon").attr('id'))
		}
		if ($("#ventesencours").is(':checked')) {
			checkedCheckbox.push($("#ventesencours").attr('id'))
		}
		if ($("#ventesend").is(':checked')) {
			checkedCheckbox.push($("#ventesend").attr('id'))
		}
		var json = JSON.stringify(checkedCheckbox);
		$.ajax({
			url : 'ServletHome',
			data : {
				test : json
			},
			success : function(responseText) {

			}
		});
	});

	$('#categories').on('change', function() {
		$.ajax({
			url : 'ServletHome',
			data : {
				categorie : this.value
			},
			success : function(responseText) {

			}
		});
	});

});

$(document).on("click", ".pagination li", function() {
	var checkedCheckbox = [];
	if ($("#encheresouvertes").is(':checked')) {
		checkedCheckbox.push($("#encheresouvertes").attr('id'))
	}
	if ($("#enchereswin").is(':checked')) {
		checkedCheckbox.push($("#enchereswin").attr('id'))
	}
	if ($("#encheresencours").is(':checked')) {
		checkedCheckbox.push($("#encheresencours").attr('id'))
	}
	if ($("#ventesnon").is(':checked')) {
		checkedCheckbox.push($("#ventesnon").attr('id'))
	}
	if ($("#ventesencours").is(':checked')) {
		checkedCheckbox.push($("#ventesencours").attr('id'))
	}
	if ($("#ventesend").is(':checked')) {
		checkedCheckbox.push($("#ventesend").attr('id'))
	}
	var json = JSON.stringify(checkedCheckbox);
	$.ajax({
		url : 'ServletHome',
		data : {
			page : this.value,
			test : json
		},
		success : function(data) {
			liste = "";
			liste = $(data).find('#liste').html();
			$('#liste').filter(function() {
				return $(this).val() == "";
			});
			$('#liste').html(liste);
		}
	});
});
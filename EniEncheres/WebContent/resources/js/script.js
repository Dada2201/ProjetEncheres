alert('toto');

$(document).ready(function(){
	$('#achats').click(function() {
	   if($('#achats').is(':checked')) { 
		    $( "#encheresouvertes").prop("disabled", false);
		    $( "#encheresouvertes").prop("checked", false);
		    $( "#enchereswin").prop("disabled", false);
		    $( "#encheresencours").prop("disabled", false);
		    $( "#ventesnon").prop("disabled", true);
		    $( "#ventesencours").prop("disabled", true);
		    $( "#ventesend").attr("disabled", true);
		    $( "#ventesend").prop('checked', false);
		    $( "#ventesnon").prop('checked', false);
		    $( "#ventesencours").prop('checked', false);


		}
	   });
	$('#ventes').click(function(){
		   if($('#ventes').is(':checked')){
			    $( "#encheresouvertes").prop("disabled", true);
			    $( "#encheresouvertes").prop("checked", false);
			    $( "#enchereswin").prop("disabled", true);
			    $( "#encheresencours").prop("disabled", true);
			    $( "#enchereswin").prop("checked", false);
			    $( "#encheresencours").prop("checked", false);
			    $( "#ventesend").prop('checked', false);
			    $( "#ventesnon").prop('checked', false);
			    $( "#ventesencours").prop('checked', false);
			    $( "#ventesend").prop("disabled", false);
			    $( "#ventesnon").prop("disabled", false);
			    $( "#ventesencours").prop("disabled", false);
	}
	});
	

})
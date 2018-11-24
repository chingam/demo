var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.messagelog = d4.eo.messagelog || {};

(window.jQuery)
$(document).ready(function() {
	$("#loadingmask2").show();
	d4.eo.messagelog.init();
	$("#loadingmask2").hide();
});

d4.eo.messagelog.init = function() {
	$('#logTable').DataTable(
			{
				"bLengthChange": false,
				"bSort" : false
			});
}
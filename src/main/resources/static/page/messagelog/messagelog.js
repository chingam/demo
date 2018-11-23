var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.messagelog = d4.eo.messagelog || {};

(window.jQuery)
$(document).ready(function() {
	d4.eo.messagelog.init();
});

d4.eo.messagelog.init = function() {
	$('#logTable').DataTable(
			{
				"bLengthChange": false,
				"bSort" : false
			});
}
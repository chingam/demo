var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.role = d4.eo.role || {};
var dataTB;

(window.jQuery)
$(document).ready(function() {
	d4.eo.role.init();
});

d4.eo.role.init = function() {
	$("#custbtnNextblock").on("click", d4.eo.role.next);
	$("#custbtnSave").on("click", d4.eo.role.save);
}

d4.eo.role.next = function() {
	var fm = $('form#mainform');
	var roleCode = $("#roleCode option:selected").val();
	var formCode = $("#formCode").val();
	var url = fm.attr("action") + "/all?roleCode=" + roleCode + "&formCode=" + formCode;
	d4.eo.role.load(url);
}

d4.eo.role.load = function(url) {
	$("#loadingmask2").show();
	if (dataTB != null) {
		dataTB.destroy();
	}
	if (url != null) {
		$("#dataList tbody tr").remove();
		$("#formsection").append("<div id='datalistBody'></div>")
		$("#datalistBody").load(url, function(responseTxt, statusTxt, xhr) {
			dataTB = $('#dataList').DataTable(
					{
						"bFilter" : false,
						"bLengthChange": false,
						"bSort" : false
					});
		});
	}
	$("#loadingmask2").hide();
}

d4.eo.role.save = function() {
	$("input[name='canOpen']", dataTB.rows().nodes()).each( function() {
		if ($(this).is(':checked')) {
			console.log($(this));
//			bookCodes.push("bookCodes="+ $(this).attr("data-booking"));
		}
	} );
}
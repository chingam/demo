var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.regitryquery = d4.eo.regitryquery || {};
var dataTB;

d4.eo.regitryquery.init = function() {
	$("#custbtnNextblock").on("click", d4.eo.regitryquery.next);
}

d4.eo.regitryquery.next = function() {
	var fm = $('form#mainform');
//	var roleCode = $("#roleCode option:selected").val();
//	var formCode = $("#formCode").val();
	var url = fm.attr("action") + "/all";
	d4.eo.regitryquery.load(url);
}

d4.eo.regitryquery.load = function(url) {
	$("#loadingmask2").show();
	if (dataTB != null) {
		dataTB.destroy();
	}
	if (url != null) {
		$("#metaDataList tbody tr").remove();
		$("#formsection").append("<div id='datalistBody'></div>")
		$("#datalistBody").load(url, function(responseTxt, statusTxt, xhr) {
			dataTB = $('#metaDataList').DataTable(
					{
						"bFilter" : false,
						"bLengthChange": false,
						"bSort" : false
					});
		});
	}
	$("#loadingmask2").hide();
}

var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.regitryquery = d4.eo.regitryquery || {};
var dataTB;

d4.eo.regitryquery.init = function() {
	$("#custbtnNextblock").on("click", d4.eo.regitryquery.next);
}

d4.eo.regitryquery.next = function() {
	var fm = $('form#mainform');
	var patnerId = $("#patnerId option:selected").val();
	var returnType = $("#returnType option:selected").val();
	var messageType = $("#messageType option:selected").val();
	var searchData = $("#searchData").val();
	
	$("#loadingmask2").show();
	var url = fm.attr("action") + "/all?search=" + searchData + "&returntype=" + returnType + "&messagetype=" + messageType + "&patnerid=" + patnerId;
	d4.eo.regitryquery.load(url);
}

d4.eo.regitryquery.load = function(url) {
	if (dataTB != null) {
		dataTB.destroy();
	}
	if (url != null) {
		$("#metaDataList tbody tr").remove();
		$("#formsection").append("<div id='datalistBody'></div>")
		$("#datalistBody").load(url, function(responseTxt, statusTxt, xhr) {
			$("#loadingmask2").hide();
			
		});
	}
}

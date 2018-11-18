var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.regitryquery = d4.eo.regitryquery || {};
var dataTB;

d4.eo.regitryquery.init = function() {
	$("#custbtnNextblock").on("click", d4.eo.regitryquery.next);
	$("#messageType").on("change", d4.eo.regitryquery.selectMessageType);
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
			console.log(responseTxt);
			if (responseTxt.startsWith('{"message"')) {
				$("#datalistBody").remove();
				var obj = JSON.parse(responseTxt);
				showScreenMessage("alert-danger", obj.message, "");
			}
			
		});
	}
}
d4.eo.regitryquery.selectMessageType = function() {
	var selectValue = $(this).val();
	if (selectValue == "103" || selectValue == "111") {
		$("#searchLabel").text("Uuid");
		$("#searchData").attr("placeholder", "Uuid");
	} else if (selectValue == "104") {
		$("#searchLabel").text("Unique id");
		$("#searchData").attr("placeholder", "Unique id");
	} else if (selectValue == "104" || selectValue == "105" || selectValue == "106" || selectValue == "107" || selectValue == "108" || selectValue == "109" || selectValue == "110") {
		$("#searchLabel").text("Unique id");
		$("#searchData").attr("placeholder", "Unique id");
	} else {
		$("#searchLabel").text("Patient no");
		$("#searchData").attr("placeholder", "Patient no");
	}
}

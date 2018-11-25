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
	var otherData = $("#orData").val();
	
	var selectType = messageType;
	if ((selectType == "111" || selectType == "103") && searchData == '') {
		showScreenMessage("alert-danger", "Uuid field is required", "");
		return;
	} else if ((selectType == "98" || selectType == "100" || selectType == "101" || selectType == "102") && searchData == ''){
		showScreenMessage("alert-danger", "Patient no field is required", "");
		return;
	} else if ((selectType == "104" || selectType == "105" || selectType == "106" || selectType == "107" || selectType == "108" || selectType == "109" || selectType == "110") && searchData == '' && otherData == ''){
		showScreenMessage("alert-danger", "Unique id field or Uuid field is required", "");
		return;
	}
	
//	else if ((selectType == "110" || selectType == "109" || selectType == "106" selectType == "105") && searchData == '') {
//		showScreenMessage("alert-danger", "Unique id field is required", "");
//		return;
//	} 
	
	$("#loadingmask2").show();
	var url = fm.attr("action") + "/all?search=" + searchData + "&returntype=" + returnType + "&messagetype=" + messageType + "&patnerid=" + patnerId + "&other=" + otherData;
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
			//console.log(responseTxt);
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
		$("#otherOption").hide();
	} 
	else if (selectValue == "99") {
		$("#otherOption").show();
		$("#orDataLabel").text("Entry references");
		$("#orData").attr("placeholder", "Entry references");
	} 
	else if (selectValue == "98" || selectValue == "100" || selectValue == "101" || selectValue == "102") {
		$("#searchLabel").text("Patient no");
		$("#searchData").attr("placeholder", "Patient no");
		$("#otherOption").hide();
	} 
	else {
		$("#searchLabel").text("Unique id");
		$("#searchData").attr("placeholder", "Unique id");
		$("#otherOption").show();
		$("#orDataLabel").text("Uuid");
		$("#orData").attr("Uuid", "Uuid");
		
	}
}

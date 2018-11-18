var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.retrieve = d4.eo.retrieve || {};
var dataTB;

d4.eo.retrieve.init = function() {
	$("#custbtnNextblock").on("click", d4.eo.retrieve.next);
	$("#patnerId").on("change", d4.eo.retrieve.selectCongig);
}

d4.eo.retrieve.next = function() {
	var fm = $('form#mainform');
	var documentUniqueId = $("#documentUniqueId").val();
	var homeCommunityId = $("#homeCommunityId").val();
	var repositoryUniqueId = $("#repositoryUniqueId").val();
	var patnerId = $("#patnerId option:selected").val();
	
	$("#loadingmask2").show();//documentUniqueId, homeCommunityId, repositoryUniqueId, patnerId
	var url = fm.attr("action") + "/all?documentUniqueId=" + documentUniqueId + "&homeCommunityId=" + homeCommunityId + "&repositoryUniqueId=" + repositoryUniqueId + "&patnerId=" + patnerId;
	d4.eo.retrieve.load(url);
}

d4.eo.retrieve.load = function(url) {
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

d4.eo.retrieve.selectCongig = function() {
	$("#homeCommunityId").val($(this).find(':selected').data("homecommunityid"));
	$("#repositoryUniqueId").val($(this).find(':selected').data("repositoryuniqueid"));
}

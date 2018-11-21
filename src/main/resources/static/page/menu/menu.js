var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.menu = d4.eo.menu || {};

d4.eo.menu.menuInit = function() {
	$("#engdate").text(moment().format('DD/MM/YYYY'));
	$(".menuButton").on("click", d4.eo.menu.getLink);
}

d4.eo.menu.getLink =function() {
	$("#loadingmask2").show();
	var linkId = $(this).data("id");
	var hText;
	if (linkId == 1) {
		hText = "Transaction";
	} else if (linkId == 2) {
		hText = "Query";
	} else if (linkId == 3) {
		hText = "Report";
	} else if (linkId == 4) {
		hText = "Setup";
	}
	$("#headerText").text(hText);
	var menuUrl= "/menu/link?linkId=" + linkId;
	$('#menuSection').load(menuUrl, function(responseTxt, statusTxt, xhr) {
		$("#loadingmask2").hide();
	});
}
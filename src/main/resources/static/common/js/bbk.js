//var m4 = m4 || {};
//m4.eo = m4.eo || {};
//m4.eo.missingapi = m4.eo.missingapi || {};
//var dataTableObj;
//
//(window.jQuery)
//$(document).ready(function(){
//	m4.eo.missingapi.init();
//});
//
//
//m4.eo.missingapi.init = function() {
//	$("input[type='search']").on("keyup", m4.eo.missingapi.resetSelectItem);
//	$("#generateList").on("click", m4.eo.missingapi.generateList);
//	$("#sendEmail").on("click", m4.eo.missingapi.sendEmail);
//}
//
//m4.eo.missingapi.initializeDataTable = function() {
//	dataTableObj = $("#listdata1").dataTable({"columnDefs": [ {
//		"targets": 'no-sort',
//		"orderable": false,
//	} ], "order": [[ 2, "asc" ]]});
//
//	m4.eo.missingapi.populateSelectOption();
//
//	$.fn.dataTableExt.afnFiltering.push(function(settings, data, dataIndex) {
//		var bookingValue = $('#apiStatus option:selected').val();
//		var columnValue = data[4];
//		if (columnValue == bookingValue) {
//			return true;
//		} else if (columnValue != bookingValue && bookingValue == 'all') {
//			return true;
//		}
//		return false;
//	});
//
//	$("#apiStatus", $("body")).on("change", m4.eo.missingapi.filterAPIStatus);
//	$("#selectAll").on("click", m4.eo.missingapi.selectAll);
//}
//
//m4.eo.missingapi.populateSelectOption = function() {
//	var selectOption = $("#apiStatus");
//	var option = "<option value='all'>All</option>";
//	var optionText=[];
//	$('.stat', dataTableObj.fnGetNodes()).each( function() {
//		optionText.push($(this).text());
//	} );
//	
//	var unique = optionText.filter(function(itm, i, optionText) {
//		return i == optionText.indexOf(itm);
//	});
//
//	$.each(unique, function(i,v){
//		option += "<option value='"+ v +"'>" + v +"</option>";
//	});
//	selectOption.empty();
//	selectOption.append(option);
//}
//
//m4.eo.missingapi.filterAPIStatus = function() {
//	dataTableObj.fnDraw();
//	m4.eo.missingapi.resetSelectItem();
//}
//
//m4.eo.missingapi.selectAll = function() {
//	var ref = $("#selectAll").is(':checked');
//	var rows = dataTableObj._('tr', {"filter":"applied"});
//	if (ref) {
//		if (rows.length < dataTableObj.fnGetNodes().length) {
//			rows.each(function (row) {
//				$('input[data-booking="' + row[0] + '"]', dataTableObj.fnGetNodes()).prop('checked', true);
//			});
//		} else {
//			$('input', dataTableObj.fnGetNodes()).prop('checked', true);
//		}
//	} else {
//		$('input', dataTableObj.fnGetNodes()).prop('checked', false);
//	}
//}
//
//m4.eo.missingapi.sendEmail = function() {
//	if(!$("#tmpl").valid()) {
//		return false;
//	}
//	var url = $(this).attr("data-remote");
//	var tmpl = $("#tmpl option:selected").val();
//	var bookCodes = [];
//	
//	$('input', dataTableObj.fnGetNodes()).each( function() {
//		if ($(this).is(':checked')) {
//			console.log($(this).attr("data-booking"));
//			bookCodes.push("bookCodes="+ $(this).attr("data-booking"));
//		}
//	} );
//	m4.ui.block();
//	$.post(url + "?tmpl=" + tmpl, bookCodes.join("&"), function(data, status, jqXHR) {
//		parseAndShowMessage(data);
//	}).error(function(data, status, jqXHR) {
//		showError(jqXHR);
//	}).always(function(){
//		m4.ui.unblock();
//	});
//}
//
//m4.eo.missingapi.resetSelectItem = function() {
//	if($('#selectAll').is(':checked')) {
//		$('#selectAll').trigger("click");
//	}
//}
//
//m4.eo.missingapi.generateList = function() {
//	var fromObj = $("#from");
//	var toObj = $("#to");
//	if(!fromObj.valid() || !toObj.valid()) {
//		return false;
//	}
//	
//	var date1 = moment(fromObj.val(), 'DD-MMM-YYYY');
//	var date2 = moment(toObj.val(), 'DD-MMM-YYYY');
//	var timeDiff = date2.diff(date1);
//	var diffDays = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
//	if (diffDays > 30) {
//		showError("More than 30 days of departure date range is not allowed");
//		return false;
//	}
//	
//	m4.ui.block();
//	$.get($("#generateList").attr("data-remote") +"?from="+ $("#from").val() + "&to=" + $("#to").val(), function(rs) {
//		var table = $("#showData");
//		m4.eo.missingapi.destroyDataTable("#listdata1");
//		table.empty();
//		table.html(rs);
//		
//		m4.eo.missingapi.initializeDataTable();
//		if ($("#listdata1" + " tbody tr").length != 0) {
//			$("#tmpldiv").show();
//		}
//		
//	}).always(function(){
//		m4.ui.unblock();
//	});
//}
//m4.eo.missingapi.destroyDataTable = function(target) {
//	try {
//		if ($(target + " tbody tr").length != 0) {
//			$(target).dataTable().fnDestroy();
//			$(target + " tbody tr").remove();
//		}
//	} catch (e) {
//		console.log("Error while destroying data table.....");
//		console.log(e);
//	}
//}

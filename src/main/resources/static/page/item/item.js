var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.item = d4.eo.item || {};
var dataTableObj;

(window.jQuery)
$(document).ready(function(){
	d4.eo.item.init();
});


d4.eo.item.init = function() {
	d4.eo.item.load("item/find/all");
	$("#saveBtn").on("click", d4.eo.item.save);
}

d4.eo.item.save = function() {
	var fm = $('form#mainform');
	var validator = fm.validate();
	if(!validator.form()) {
		return false;
	}
	$("#loadingmask2").show();
	var formData = fm.serializeArray();
	$.post("/item", formData, function(data, status, jqXHR) {
		console.log(data);
		d4.eo.item.load(data.redirect);
	}).done(function() {
		console.log( "second success" );
	}).fail(function() {
		console.log( "error" );
	}).always(function() {
		console.log( "finished" );
		$("#loadingmask2").hide();
	});
}

d4.eo.item.edit = function() {
	$("#loadingmask2").show();
	var url = $(this).attr("data-url");
	$.get( url, function(data) {
		$("#itemId").val(data.data.id);
		$("#name").val(data.data.name);
		$("#description").val(data.data.description);
	}).done(function() {
		console.log( "second success" );
	}).fail(function() {
		console.log( "error" );
	}).always(function() {
		console.log( "finished" );
		$("#loadingmask2").hide();
	});
}

d4.eo.item.archive = function() {
	$("#loadingmask2").show();
	var url = $(this).attr("data-url");
	$.get( url, function(data) {
		d4.eo.item.load("item/find/all");
	}).done(function() {
		console.log( "second success" );
	}).fail(function() {
		console.log( "error" );
	}).always(function() {
		console.log( "finished" );
		$("#loadingmask2").hide();
	});
}

d4.eo.item.load = function(url) {
	$("#loadingmask2").show();
	$.get( url, function(data) {
		d4.eo.item.destroyDataTable("#listdata1");
		$("#view").html(data);
		
		$("#listdata1").DataTable({
			"columnDefs": [ {
			"targets": 'no-sort',
			"orderable": false,
			} ]
		});
		$(".deleteBt").bind("click", d4.eo.item.archive);
		$(".editBt").bind("click", d4.eo.item.edit);
		console.log( "success" );
		})
		.done(function() {
			console.log( "second success" );
		})
		.fail(function() {
			console.log( "error" );
		})
		.always(function() {
			console.log( "finished" );
			$("#loadingmask2").hide();
		});
}

d4.eo.item.destroyDataTable = function(target) {
	try {
		if ($(target + " tbody tr").length != 0) {
			$(target).dataTable().fnDestroy();
			$(target + " tbody tr").remove();
		}
	} catch (e) {
		console.log("Error while destroying data table.....");
		console.log(e);
	}
}
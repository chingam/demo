var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.sales = d4.eo.sales || {};
var dataTableObj;

(window.jQuery)
$(document).ready(function(){
	d4.eo.sales.init();
});


d4.eo.sales.init = function() {
	
	$("#disc").on("keyup", d4.eo.sales.discount);
	$("#qtd").on("keyup", d4.eo.sales.quantity);
	$("#saveBtn").on("click", d4.eo.sales.save);
	d4.eo.sales.searchProduct();
}

d4.eo.sales.save = function() {
	var fm = $('form#mainform');
	var validator = fm.validate();
	if(!validator.form()) {
		return false;
	}
	$("#loadingmask2").show();
	var formData = fm.serializeArray();
	$.post("/sales", formData, function(responseData, status, jqXHR) {
		d4.eo.sales.shoAlert("#overview", "body", responseData.status, responseData.message);
		d4.eo.sales.reset();
	}).done(function() {
		console.log( "second success" );
	}).fail(function() {
		console.log( "error" );
	}).always(function() {
		console.log( "finished" );
		$("#loadingmask2").hide();
	});
}

d4.eo.sales.shoAlert = function(target, des, tag, message) {
	var cs, ic, posi;
	if ($("#success-alert") != null) {
		$("#success-alert").remove();
	}
	if (tag == "error") {
		cs = "alert-danger";
		ic = "glyphicon glyphicon-exclamation-sign";
	} else if (tag == "success") {
		cs = "alert-success";
		ic = "glyphicon glyphicon-ok";
	} else if (tag == "info") {
		cs = "alert-info";
		ic = "glyphicon glyphicon-info-sign";
	} else {
		cs = "alert-warning";
		ic = "glyphicon glyphicon-info-sign";
	}
	if (des == "modal") {
		posi = "absolute";
	} else {
		posi = "relative";
	}

	var html = "<div class=' navbar-fixed-top col-md-12 col-sm-12 alert "
			+ cs
			+ " fade in' id='success-alert'"
			+ "style='display:none; position: "
			+ posi
			+ ";  padding: .3%; margin-bottom: 0px; z-index: 122;margin-top: -16.8px;'>"
			+ "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>"
			+ "<span class='" + ic + "'></span><span> <strong>" + message
			+ " </strong></span></div>";

	$(target).prepend(html);

	if (target != ".modal-body") {
		$('#success-alert').css("width", $(".container").width() + "px").css(
				"top",
				($("body > div.navbar").height() + $("div.subnav").height())
						+ "px").css("left",
				($('body').width() - $(".container").width()) / 2 + "px");
	}

	$("#success-alert").show(500);
	//		app.alertMessageCloseTimeout("#success-alert");
	//		setTimeout(function() {
	//			$("#success-alert").remove();
	//		}, 15000);

}
d4.eo.sales.reset = function() {
	$("input[type=text], input[type=hidden], textarea").val("");
}

d4.eo.sales.quantity = function() {
	var qt = $("#qtd").val();
	var total = $("#total").val();
	$("#gtotal").val(total);
	if (qt == "" || qt == null) {
		return;
	}
	var dis = $("#disc").val();
	dis = (dis == "") ? 0 : dis;
	var totls = parseInt(total) * parseInt(qt) - parseInt(dis);
	$("#gtotal").val(totls + ".00");
}

d4.eo.sales.discount = function() {
	var qt = $("#disc").val();
	var total = $("#total").val();
	$("#gtotal").val(total);
	if (qt == "" || qt == null) {
		return false;
	}
	var totls = parseInt(total) - parseInt(qt);
	$("#gtotal").val(totls + ".00");
}

d4.eo.sales.searchProduct = function() {

	var searchField = $( ".multicolumn-search" );
	searchField.autocomplete({
		source: function(responseData1, response) {
			$.ajax({
				url: searchField.data("search-url") + searchField.val(),
				delay: 500,
				dataType: "json",
				success: function(responseData) {
					console.log(responseData);
					var resultsList = [];
					var headerrecord = {};
					if (responseData.headings) {
						headerrecord.headings = responseData.headings;
						resultsList.push(headerrecord);
					}
					if ($.isArray(responseData.values)) {
						responseData = responseData.values;
					}
					for (var i = 0; i < responseData.length; i++) {
						record = {};
						record.key = responseData[i].key;
						record.multiColumn = false;
						if ($.isArray(responseData[i].value)) {
							record.multiColumn = true;
							record.value = [];
							for (var j = 0; j < responseData[i].value.length; j++) {
								record.value[j] = responseData[i].value[j];
							}
						} else {
							record.value = responseData[i].value;
						}
						record.data = responseData[i].data;
						resultsList.push(record);
					}
					
					setTimeout(function() {
						response(resultsList);
						$(".multicolumn-search").parents('.input-group').find('span.input-group-addon i.fa').removeClass('fa-repeat autocompletesearching').addClass('fa-search');
					}, 1000);
				}
			});
		},
		create: function () {
			$(this).data('ui-autocomplete')._renderItem = function (ul, data) {
			console.log("Call product search");
			if(data.multiColumn || data.headings){
				if (data.headings) {
					var headingHTML = "";
					for(var i = 0; i < data.headings.length; i++){
						if(data.headings[i]){
							headingHTML = headingHTML + "<div class='heading'>" + data.headings[i] + "</div>";
						}
					}
					return $("<li class='columnautocomplete ui-menu-item disabled'></li>")
					.data("item.autocomplete", data)
					.append(headingHTML)
					.appendTo(ul);//li.columnautocomplete
				}
				else {
					var content = "<a>";
					var dataLines = data.value;
					for(var i = 0; i < dataLines.length; i++){
						var value = (dataLines[i] !== '' || dataLines[i] !== undefined) ? dataLines[i]: "-";
						content = content + "<div class='col cell"+ i+ "'>" + value + "</div>";
					}
					content = content + "</a>";
					var el = $("<li class='columnautocomplete'></li>").data("item.autocomplete", data)							
					.append(content)
					.appendTo(ul);
					$(ul).find("li:odd").addClass("odd");
					return el; 
				}
			}else{
				return $( "<li>" )
				.attr( "data-value", data.value )
				.append( $( "<a>" ).text( data.label ) )
				.appendTo( ul );
			}
};
		},
		select: function(event, ui){
			setTimeout(function() {
				$( ".multicolumn-search" ).val(ui.item.key);
				$( ".multicolumn-val" ).val(ui.item.data[0]);
				$( ".multicolumn-desc" ).val(ui.item.key);
				$( "#total" ).val(ui.item.value[2]);
				$( "#gtotal" ).val(ui.item.value[2]);
				$("#qtd").val("1");
			}, 10);
			console.log(ui.item.value[0]);
},
		search: function(event, ui) {
			$(this).parents('.input-group').find('span.input-group-addon i.fa').removeClass('fa-search').addClass('fa-repeat autocompletesearching');
		}
		});

}
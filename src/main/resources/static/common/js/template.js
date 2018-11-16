var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.employee = d4.eo.employee || {};
var dataTB;

(window.jQuery)
$(document).ready(function() {
	d4.eo.employee.init();
//	d4.eo.menu.menuInit();
});

d4.eo.employee.init = function() {
	$("#btnBack").on("click", d4.eo.employee.back);
	$("#btnSave").on("click", d4.eo.employee.save);
	$("#btnDelete").on("click", d4.eo.employee.deletedata);
	$("#btnNew").on("click", d4.eo.employee.clean);
	$("#btnClear").on("click", d4.eo.employee.clear);
	$("#btnEnterQuery").on("click", d4.eo.employee.query);
	$("#btnExecuteQuery").on("click", d4.eo.employee.exequery);
	$("#btnCancelQuery").on("click", d4.eo.employee.cancelquery);
}

d4.eo.employee.findById = function() {
	$("#loadingmask2").show();
	var fm = $('form#mainform');
	var url = fm.attr("action") + "/find?code=" + $(this).find("td").eq(0).data("code");
	$('#formdiv').load(url, function(responseTxt, statusTxt, xhr) {
		$("#loadingmask2").hide();
	});
}

d4.eo.employee.clean = function() {
	$("input[type=text], input[type=hidden][name!='_csrf'], textarea").val("");
	if (dataTB != null) {
		dataTB.destroy();
	}
	$("#datalistBody").remove();
}

d4.eo.employee.clear = function() {
	$("input[type=text], input[type=hidden][name!='_csrf'], textarea").val("");
}

d4.eo.employee.query = function() {
	$("input[type=text]").first().focus();
	$("#btnSave").hide();
	$("#btnDelete").hide();
	$("#btnNew").hide();
	$("#btnExecuteQuery").show();
	$("#btnCancelQuery").show();
}

d4.eo.employee.exequery = function() {
	var fm = $('form#mainform');
	var url = fm.attr("action") + '/find/' + $("input[type=text]").first().val();
	d4.eo.employee.load(url);
}
d4.eo.employee.cancelquery = function() {
	$("input[type=text]").first().focus();
	$("#btnSave").show();
	$("#btnDelete").show();
	$("#btnNew").show();
	$("#btnEnterQuery").show();
	$("#btnExecuteQuery").hide();
	$("#btnCancelQuery").hide();
}

d4.eo.employee.save = function() {
	var fm = $('form#mainform');
	var validator = fm.validate();
	if (!validator.form()) {
		return;
	}
	var formData = fm.serializeArray();
	var url = fm.attr("action");
	$("#loadingmask2").show();

	$.ajax({
		url : url,
		type : "POST",
		delay : 500,
		data : formData,
		success : function(responseData) {
			if (responseData.status !== "success") {
				showScreenMessage("alert-danger", responseData.message, "");
				$("#loadingmask2").hide();
				return;
			}
			showScreenMessage("alert-success", responseData.message, "");
			d4.eo.employee.load(url + "/all");
			$("#loadingmask2").hide();
			
		},
		error: function (errormessage) {
			console.log(errormessage);
		}
	});
	
}

d4.eo.employee.deletedata = function() {
	if (confirm('Are you sure.Do you want to delete?')) {
		var fm = $('form#mainform');
		var validator = fm.validate();
		if (!validator.form()) {
			return;
		}
		var formData = fm.serializeArray();
		var url = fm.attr("action");
		var deleteUrl = url + '/delete';
		$("#loadingmask2").show();

		$.ajax({
			url : deleteUrl,
			type : "POST",
			delay : 500,
			data : formData,
			success : function(responseData) {
				if (responseData.status !== "success") {
					showScreenMessage("alert-danger", responseData.message, "");
					$("#loadingmask2").hide();
					return;
				}
				showScreenMessage("alert-success", responseData.message, "");
				d4.eo.employee.load(url + "/all");
				$("#loadingmask2").hide();
				
			},
			error: function (errormessage) {
				console.log(errormessage);
			}
		});
		
}
}

d4.eo.employee.load = function(url) {
	$("#loadingmask2").show();
	if (dataTB != null) {
		dataTB.destroy();
	}
	if (url != null) {
		$("#tbl tbody tr").remove();
		$("#formsection").append("<div id='datalistBody'></div>")
		$("#datalistBody").load(url, function(responseTxt, statusTxt, xhr) {
			dataTB = $('#tbl').DataTable(
					{
						"bFilter" : false,
						"bLengthChange": false
					});
			$('#tbl tbody tr').bind('click', d4.eo.employee.findById);
		});
	}
	$("#loadingmask2").hide();
}

d4.eo.employee.back = function() {
	window.location.href = "/menu";
}


function showScreenMessage(alertClass, message, triggerelement) {
    $('.headeralert').remove();
    var div = "<div class='headeralert alert "+alertClass+" role='alert' style='font-size: 15px;border: 1px solid;width: 40%; position:fixed;z-index: 1000;float: right;right: 0px;'>"
    + message
    + "<button type='button' class='close preventmessages' data-dismiss='alert' aria-label='Close'>"
    + "<span aria-hidden='true'>&times;</span>"
    + "</button>"
    + "</div>";
    
    $("div#banner").append(div);
//    headeralertfix();
    $('.headeralert').find('.preventmessages').on('click', function () {
        if (triggerelement) {
            triggerelement.data('preventalert', 'Y');
        }
    });
    $('.headeralert').show(500);
    alertremoval($('.headeralert'));
    // Set timer to remove banner
    alertMessageCloseTimeout($('.headeralert'));
}

function alertMessageCloseTimeout(element) {
    window.setTimeout(function () {
        element.remove();
    }, 20000);
}

function alertremoval(target) {
    $('.alertremoval', target).on('click', function() {
        $(this).parents('div.alert').remove();
    });
}


d4.eo.employee.search = function() {
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
			}, 10);
			console.log(ui.item.value[0]);
},
		search: function(event, ui) {
			$(this).parents('.input-group').find('span.input-group-addon i.fa').removeClass('fa-search').addClass('fa-repeat autocompletesearching');
		}
		});


}
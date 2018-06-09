document.onreadystatechange = function () {
	var state = document.readyState
	if (state == 'complete') {
		$("#loadingmask2").css("display", "none");
	}
}
$(document).ready(function() {
	if ($("#loadingmask2").length > 0) {
		$("#loadingmask2").css("display", "none");
	}
});
//
//var app = {
//		save : function () {
//			$("#msaveBtn").on("click", function() {
//				var fm = $('form#mainform');
//				var validator = fm.validate();
//				if(!validator.form()) {
//					return;
//				}
//				var formData = fm.serializeArray();
//				var url = fm.attr("action");
//				$("#loadingmask2").show();
//				$.ajax({
//					url: url,
//					type: "POST",
//					delay: 500,
//					data: formData,
//					success: function(responseData) {
//						if (responseData.status === "error") {
//							app.showAlert(".modal-body", "modal", responseData.status, responseData.message);
//							$("#loadingmask2").hide();
//							return;
//						}
//						setTimeout(function() {
//							app.loadData(url + "/all");
//							$("#myModal").modal("hide");
//							app.showAlert("#overview", "body", responseData.status, responseData.message);
//						}, 1000);
//					}
//				});
//			});
//		},
//		editBtn : function () {
//			$("body").on("click", ".editBtn" , function() {
//				console.log($(this).data("url"));
//				$("#loadingmask2").show();
//				$.ajax({
//					url: $(this).data("url"),
//					type: "GET",
//					delay: 500,
//					success: function(responseData) {
//						$("div.modal-body").empty();
//						$("div.modal-body").html(responseData);
//						$("#myModal").modal("show");
//						$("#loadingmask2").hide();
//					}
//				});
//			});
//		},
//		addBtn : function () {
//			$("#addBtn").on("click", function() {
//				$("input[type=text], input[type=hidden], textarea").val("");
//			});
//		},
//		deleteBtn : function () {
//			$("body").on("click", ".deleteBtn" , function() {
//				var url = $(this).data("url");
//				if (confirm('Are you sure.Do you want to delete?')) {
//					$("#loadingmask2").show();
//					$.ajax({
//						url: url,
//						type: 'DELETE',
//						delay: 500,
//						success: function(responseData) {
//							setTimeout(function() {
//								app.loadData("/product/all");
//								$("#myModal").modal("hide");
//								app.showAlert("#overview", "body", responseData.status, responseData.message);
//							}, 1000);
//						}
//					});
//				
//				}
//			});
//		},
//		showAlert : function (target, des, tag, message) {
//			var cs, ic, posi;
//			if ($("#success-alert") != null) {
//				$("#success-alert").remove();
//			}
//			if (tag == "error"){
//				cs = "alert-danger";
//				ic = "glyphicon glyphicon-exclamation-sign";
//			} else if (tag == "success") {
//				cs = "alert-success";
//				ic = "glyphicon glyphicon-ok";
//			} else if (tag == "info") {
//				cs = "alert-info";
//				ic = "glyphicon glyphicon-info-sign";
//			} else {
//				cs = "alert-warning";
//				ic = "glyphicon glyphicon-info-sign";
//			}
//			if (des == "modal") {
//				posi = "absolute";
//			} else {
//				posi = "relative";
//			}
//			
//			var html = "<div class=' navbar-fixed-top col-md-12 col-sm-12 alert "+cs+" fade in' id='success-alert'"
//			+ "style='display:none; position: "+posi+";  padding: .3%; margin-bottom: 0px; z-index: 122;margin-top: -16.8px;'>"
//			+"<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>"
//			+ "<span class='"+ic+"'></span><span> <strong>"+message+" </strong></span></div>";
//			
//			$(target).prepend(html);
//			
//			if (target != ".modal-body") {
//				$('#success-alert').css("width", $(".container").width() + "px")
//				.css("top", ($("body > div.navbar").height() + $("div.subnav").height()) + "px")
//				.css("left", ($('body').width() - $(".container").width()) / 2 + "px");
//			}
//			
//			
//			$("#success-alert").show(500);
//			app.alertMessageCloseTimeout("#success-alert");
////			setTimeout(function() {
////				$("#success-alert").remove();
////			}, 15000);
//			
//		},
//		alertMessageCloseTimeout : function(element) {
//			setTimeout(function() {
//				$("#success-alert").remove();
//			}, 15000);
//		},
//		loadData : function(url) {
//			$("#loadingmask2").show();
//			if(dataTB != null) {
//				dataTB.destroy();
//			}
//			if (url != null) {
//				$("#datalist tbody tr").remove();
//				$("#datalistBody").load(url, function(responseTxt, statusTxt, xhr) {
//					dataTB = $('#datalist').DataTable({
//						"columnDefs": [ {
//					          "targets": 'no-sort',
//					          "orderable": false,
//					    } ]
//					});
//				});
//			}
//			$("#loadingmask2").hide();
//		}
//}

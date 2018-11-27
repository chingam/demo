var d4 = d4 || {};
d4.eo = d4.eo || {};
d4.eo.messagelog = d4.eo.messagelog || {};

(window.jQuery)
$(document).ready(function() {
	d4.eo.messagelog.init();
	function countdown(callback) {
	    var bar = document.getElementById('progress'),
	    time = 0, max = 5,
	    int = setInterval(function() {
	        bar.style.width = Math.floor(100 * time++ / max) + '%';
	        if (time - 1 == max) {
	            clearInterval(int);
	            // 600ms - width animation time
	            callback && setTimeout(callback, 600);
	        }
	    }, 20000);
	}

	countdown(function() {
		location.reload();
	});

//	setInterval(function(){
//		location.reload();
//	}, 100000);
});

d4.eo.messagelog.init = function() {
	$("#loadingmask2").show();
	$('#logTable').DataTable(
			{
				"bLengthChange": false,
				"bSort" : false
			});
	d4.eo.messagelog.load("http://localhost:8080/xds/service");
}

d4.eo.messagelog.load = function(url) {
	$('#server').load(url, function(responseTxt, statusTxt, xhr) {
//		$("#loadingmask2").hide();
		$("#server").find(".heading").eq(1).remove();
		$("#server").find("table").eq(1).remove();
		$("#server link").remove();
		$("#server table tr").css({"font-weight":"bold", "background-color": "#549454"});
		console.log(responseTxt);
		$("#loadingmask2").hide();
	});
}
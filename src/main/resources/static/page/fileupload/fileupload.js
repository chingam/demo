var d4 = d4 || {};
d4.fileupload = d4.fileupload || {};

(window.jQuery)
$(document).ready(function(){
	d4.fileupload.init();
});


d4.fileupload.init = function() {
	$("#uploadId").on("change", d4.fileupload.save);
}

d4.fileupload.save = function() {
	console.log($(this).val());
	var formData = new FormData();
	formData.append('file', $('#uploadId')[0].files[0]);
		$.ajax({
			url : "/fileupload",
			type : "POST",
			data : formData,
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				$("#fileNameId").val((data.message));
			},
			error : function(data) {
				alert(data.message);
			}
		});
		return;

}
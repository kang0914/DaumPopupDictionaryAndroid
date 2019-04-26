/* comment is only alphabetical. */
/* input a text to textbox when get in focus */
$('input#q.tf_keyword').focus(function() {
	var keyword = $('input#q.tf_keyword').attr('value');
	$('input#q.tf_keyword').val(keyword);
});
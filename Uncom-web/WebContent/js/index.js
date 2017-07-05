$(function(){
	$('#home-page').on('click',function(){
		var html_obj=$.post('/Uncom/register_Page.action',{request:'home_page'},function(response){
			document.getElementById("content_row").innerHTML=response;
			
		})
	})
	
	$('#login_btn').on('click',function(){
		 $.post('/Uncom/login_Page.action',{request:'login_page'},function(response){
		 	document.getElementById('login_page_row').innerHTML=response
		 })
	})
		
	
	$('#register_btn').on('click',function(){
		$.post('/Uncom/register_Page.action',{request:'register_page'},function(response){
			document.getElementById('login_page_row').innerHTML=response;
		})
	})
})


function articlePage(tht){
	alert($(tht).attr("data-article"))
}

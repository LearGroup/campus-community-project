$(function(){
	$('#home-page').on('click',function(){
		var html_obj=$.post('/Uncom/register_Page.action',{request:'home_page'},function(response){
			$("#content-row").show(200,function(){
				document.getElementById("content_row").innerHTML=response;
			})
			
		})
	})
	
	$('#login_btn').on('click',function(){
		 $.post('/Uncom/login_Page.action',{request:'login_page'},function(response){
		 	$("#login_page_row").hide(0,function(){
		 		document.getElementById('login_page_row').innerHTML=response
		 	})
			$("#login_page_row").slideDown(200)
		 })
	})
		
	
	$('#register_btn').on('click',function(){
		$.post('/Uncom/register_Page.action',{request:'register_page'},function(response){
			$("#login_page_row").hide(0,function(){
		 		document.getElementById('login_page_row').innerHTML=response
		 	})
			$("#login_page_row").slideDown(200)
		})
	})
	
	statusTest()
})


function articlePage(tht){
	alert($(tht).attr("data-article"))
}


function statusTest(){
	$("#exit_btn").html()=="登出"
	$("#exit_btn").attr("href",'/Uncom/login_Logout.action')
}

var time=null;
function login(data){
	var sors;
	var sor;
	
	if(!$("#login_input_username").val()){
		$("#login_input_username_div").attr("class","form-group-sm has-error")
		$("#login_input_username").attr("placeholder","账号不能为空")
		$("#login_input_username").focus();	
		return 0;
	}
	if($("#login_input_username").val()){
		
		if($("#login_input_username").val().indexOf('@')!=-1){
			sors="邮箱"
			sor="email"
			$("#login_input_username_div").attr("class","form-group-sm has-success")
		}
		else{
			sors="手机号码"
			sor="phone"
			if($("#login_input_username").val().length!=11){
				$("#login_input_username").val("")
				$("#login_input_username_div").attr("class","form-group-sm has-error")
				$("#login_input_username").attr("placeholder","手机号码格式不正确")
				$("#login_input_username").focus();
				return 0;
				}
			else{
				$("#login_input_username_div").attr("class","form-group-sm has-success")
				}
			}
	   }
	if(!$("#login_input_password").val()){
		$("#login_input_password_div").attr("class","form-group-sm has-error")
		$("#login_input_password").attr("placeholder","密码不能为空")
		$("#login_input_password").focus();
		return 0;
	}
	else{
		$("#login_input_password_div").attr("class","form-group-sm has-success")
		clearTimeout(time);
		time=setTimeout(function(){
			console.debug(sor);
			$.post("/Uncom/login_Login.action",{use:sor,user:$("#login_input_username").val(),password:$("#login_input_password").val()},function(date){
			console.debug(date);
		if(date==0){
		
			$("#login_input_username_div").attr("class","form-group-sm has-error ")
			$("#login_input_password_div").attr("class","form-group-sm has-error")
			$("#login_input_password").val("")
			$("#login_input_password").attr("placeholder","账号或密码错误")
			$("#login_input_password").focus();
			
		}else{
			var sorce=date.toString();
			var username=""
			for(var i=0;i<sorce.length;i++){
				if(sorce[i]=="1"){
					
				}else{
					username=username+sorce[i]
				}
				
			}
			if(data==0){
			$("#login_input_username_div").attr("class","form-group-sm ")
			$("#login_input_password_div").attr("class","form-group-sm ")
			$("#login_btn").unbind("click")
			$("#register_btn").unbind("click")
			$("#login_btn").attr("id","user_btn")
			$("#register_btn").attr("id","exit_btn")
			$("#exit_btn").html("登出")
			$("#exit_btn").attr("href",'/Uncom/login_Logout.action')
			$("#user_btn").html(username)
			$("#login_page_row").hide(200)
			$("#login_page_row").empty()
			$("#")
			}else if(data==1){
				window.location.href="index.jsp"
			}
			
		}
	})
		},200)
	}
}

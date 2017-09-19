var time = null;

function login(data) {
	var sors;
	var sor;

	if(!$("#login_input_username").val()) {
		$("#login_input_username_div").attr("class", "form-group-sm has-error")
		$("#login_input_username").attr("placeholder", "账号不能为空")
		$("#login_input_username").focus();
		return 0;
	}
	if($("#login_input_username").val()) {

		if($("#login_input_username").val().indexOf('@') != -1) {
			sors = "邮箱"
			sor = "email"
			$("#login_input_username_div").attr("class", "form-group-sm has-success")
		} else {
			sors = "手机号码"
			sor = "phone"
			if($("#login_input_username").val().length != 11) {
				$("#login_input_username").val("")
				$("#login_input_username_div").attr("class", "form-group-sm has-error")
				$("#login_input_username").attr("placeholder", "手机号码格式不正确")
				$("#login_input_username").focus();
				return 0;
			} else {
				$("#login_input_username_div").attr("class", "form-group-sm has-success")
			}
		}
	}
	if(!$("#login_input_password").val()) {
		$("#login_input_password_div").attr("class", "form-group-sm has-error")
		$("#login_input_password").attr("placeholder", "密码不能为空")
		$("#login_input_password").focus();
		return 0;
	} else {
		$("#login_input_password_div").attr("class", "form-group-sm has-success")
		clearTimeout(time);
		time = setTimeout(function() {
			$.post("/login", {
				use: sor,
				username: $("#login_input_username").val(),
				password: $("#login_input_password").val()
			}, function(date) {
				console.log(date)
				if(date.length == 0) {

					$("#login_input_username_div").attr("class", "form-group-sm has-error ")
					$("#login_input_password_div").attr("class", "form-group-sm has-error")
					$("#login_input_password").val("")
					$("#login_input_password").attr("placeholder", "账号或密码错误")
					$("#login_input_password").focus();

				} else {

					var username =date[0].username
					if(data == 0) {
						console.log("exit")
						$("#login_page_row").hide(200)
						$("#login_page_row").empty()
						$('#login_page_row').data("userName",date[0].username)
						$("#login_page_row").data("userId",date[0].id)
						$('#myModal').modal('hide')
					thi.pullCurrentMessageList(thi)

						return 1
					} else if(data == 1) {
						window.location.href = "index.html"
					}

				}
			})
		}, 200)
	}

}

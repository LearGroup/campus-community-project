<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		  <div class="col-xs-1 col-sm-4 col-md-12"></div>
		<div class="col-xs-10 col-sm-8 col-sm-offset-2 col-md-offset-4 col-md-4">
			<div class="login_box col-xs-12">
				<div class="row sigin_header">
					<a style="text-decoration: none;" href="#">登陆</a>
				</div>
				<div class="row sigin_content" style="padding-top: 5%;">
					<form class="form-horizontal" >
					<div class="form-group-sm 	">
						<div class="col-xs-12 col-lg-8 col-lg-offset-2" style="padding-bottom: 4%;">
						<span style="left: 5%;" class="glyphicon glyphicon-user form-control-feedback"></span>
            			<input class="form-control " style="padding-left:10%" type="text" formaction="login_checking.jsp" name="login_username" class="form-control" id="login_input_username" placeholder=" 手机号或邮箱" />
            		</div>
					</div>
					<div class="form-group-sm ">
						<div class="col-xs-12 col-lg-8 col-lg-offset-2">
						<span style="left: 5%;" class="glyphicon glyphicon-lock form-control-feedback form-group-lg form-group-sm"></span>
            			<input class="form-control " style="padding-left:10%" type="text" formaction="login_checking.jsp" name="login_username" class="form-control" id="login_input_username" placeholder=" 密码" />
            		</div>
					</div>
					<div class="form-group-sm ">
						<div class="col-xs-12 col-lg-8 col-lg-offset-2" style="padding-bottom: 4%;">
						<label class="checkbox-inline col-xs-12">
							<input type="checkbox" />记住我
							<a class="col-xs-offset-5 col-sm-offset-6  col-lg-offset-6" style="right: 4%;">忘记密码?</a>
						</label>
						<input type="button" onclick="login(0)" class="btn btn-group-lg col-xs-12" value="登陆" style="margin-top: 2%;background-color: #0095DD;font-size: 18px;padding: 4px;color: #fff;"/>
						
            		</div>
					
				
            		
				</div>
					</form>
				<div class="row sigin_footer">
					
				</div>
			</div>
			
		</div>
		
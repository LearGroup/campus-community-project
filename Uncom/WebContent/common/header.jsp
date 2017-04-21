<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="row header">
	
				<div class="col-xs-4 col-sm-2 header-logo" ><a style="color: #fff;text-decoration: none;" href="">原创文字</a></div>
				<div class="col-xs-4 col-sm-1  header-item" id="home-page"><a style="color: #fff;text-decoration: none;" href="">首页</a></div>
				<div class="col-xs-4 col-sm-2  header-item"><a style="color: #fff;text-decoration: none;" href="">原创故事</a></div>
				<div class="col-xs-4 col-sm-2  header-item"><a style="color: #fff;text-decoration: none;" href="">热门专题</a></div>
				<div class="col-xs-4 col-sm-2  header-item"><a style="color: #fff;text-decoration: none;" href="">美文日赏</a></div>
				<div class="col-xs-4 col-sm-1  header-item"><a style="color: #fff;text-decoration: none;" href="">电台</a></div>
				<c:choose>
	                <c:when  test="${empty sessionScope.username}">
	                           <div class="col-xs-12 col-sm-2  header-item-login"><a style="color: #fff;" href="#" id="login_btn">登陆</a><a style="color: #fff;text-decoration: none;"> | </a><a style="color: #fff;" href="#" id="register_btn">注册</a></div>
	                </c:when>
	                <c:otherwise>
	                            <div class="col-xs-12 col-sm-2  header-item-login"><a style="color: #fff;" href="#" id="user_btn">${sessionScope.username}</a><a style="color: #fff;text-decoration: none;"> | </a><a style="color: #fff;" href="" id="exit_btn">登出</a></div>
	                </c:otherwise>	
	            </c:choose>
			</div>
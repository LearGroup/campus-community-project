<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="renderer" content="webkit" />
	    <meta charset="utf-8" name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<title>Welcome</title>
		<link rel="stylesheet" href="css/index.css" />
		<link rel="stylesheet" href="css/login.css" />
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<link rel="stylesheet" href="css/bootstrap-theme.min.css" />
	</head>
<body>
	   
		<script type="text/javascript" src="js/jquery-3.1.1.min.js" ></script>
		<script type="text/javascript" src="js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="js/index.js" ></script>
		<script type="text/javascript" src="js/login.js" ></script>
		<script type="text/javascript" src="js/register.js" ></script>
		    <script type="text/javascript" src="js/country.js" ></script>
		<div class="container-fluid">
			<%@include file="common/header.jsp" %>
			<div class="row" id="login_page_row">
				
			</div>
			<div class="row" id="content_row">
					<div id="carousel-example-generic" class="banner carousel slide" data-ride="carousel" >
						<ol class="carousel-indicators" >
							<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
							<li data-target="#carousel-example-generic" data-slide-to="1" ></li>
							<li data-target="#carousel-example-generic" data-slide-to="2" ></li>	
						</ol>
						<div class="carousel-inner" style="text-align: center;" >
							<div class="item active" >
								<img style="height: 360px;width:100%;"  alt="First slide" src="img/1a5473e034df1a314653a8d9aa9b7f8d7e8708371023296f85a943647a1bba68.jpg"/>
							</div>
							<div class="item">
								<img style="height: 360px;width:100%;" alt="Seconde slide" src="img/1d663d4aae46fa25590054832a38317cd2f06a69f4a26a5f658f3f22966121fe.jpg" />
							</div>
							<div class="item" >
								<img style="height: 360px;width:100%;"  alt="Third slide" src="img/4-1.jpg" />
							</div>
						</div>
					<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left"></span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right"></span>
					</a>
				</div>

				
				<script>
					
					$('.carousel').carousel({
						interval:5000
					})
				</script>
			</div>
			
			
			<div class="row list-container">
				
			
				<div class="col-xs-12 col-lg-6 content-box" onclick="window.open('/Uncom/article/99d64054-3f29-4f41-8f05-3835aa068c25.action')">
					<div class="row" >
						<div class="col-lg-1 hidden-xs" ></div>
						<div class="col-xs-12 col-lg-11" >
							<div class="row author">
							<div class="col-xs-12 col-lg-12" >
								<div class="avatar"></div>
								<div class="name" >
										<a class="username">陈广平</a>
									    <span class="time" data-shared-at="2017-02-11T11:05:57+08:00">6 小时前</span>
									</div>
							</div>
						</div>
						<div class="row article"  >
							  <div class="col-xs-1 hidden-lg"></div>
								<div class="col-xs-5 col-lg-9" >
									<div class="hidden-lg hidden-title-style"></div>
								<a class="title">读古典诗词</a>
							    <p class="abstract hidden-xs">先在这儿给大家拜个晚年，祝大家鸡年大吉！一转眼就到了元宵节，这是个黄道吉日，择日不如撞日，花白和历史专题主编子曰少怀一拍即合，决定一起办征文活动啦！这是【人物】专题和【历史】... </p>
							    </div>
							<div class="warp-img col-xs-2 col-lg-1" ></div>
						</div>
						<div class="row  tag-footer">
							<div class="meta col-xs-12">
								<a class="collection-tag"><small style="font-size:12px;">艺术·文化</small></a>
									<a class="font-color">
										<span class="ic-list-read glyphicon glyphicon-eye-open"></span>3232
									</a>
									<a class="font-color">
										<span class="ic-list-read glyphicon glyphicon-commentsglyphicon glyphicon-comment"></span>54
									</a>
									<a class="font-color">
										<span class="ic-list-read glyphicon glyphicon-heart"></span>154
									</a>
							</div>
						</div>
						</div>
						
						
					</div>
				</div>
				
			</div>
			<%@include file="common/footer.jsp" %>
		</div>
		
		
	</body>
</html>
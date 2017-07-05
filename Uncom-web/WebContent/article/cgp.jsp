<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="../css/article.css" />
		<link rel="stylesheet" href="../css/bootstrap.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="/common/header.jsp" %>
<div class="container">
			<div class="row article">
			<div class="hidden-xs col-lg-2"></div>
			<div class="col-xs-12 col-lg-8">
				<div class="row">
					<div class="col-xs-12">
						<h1 class="title">${requestScope.articleName }</h1>
					</div>
				</div>
			<!--作者信息-->
			<div class="row">
				<div class="col-xs-2 col-lg-1">
					<div class="avatar"></div>
				</div>
				<div class="col-xs-10 col-lg-11 a-m ">
				  <div class="row author">
				    <div class="info col-xs-10">
				     	<small class="tag ">作者</small>
					    <span class="author_name">${requestScope.authorName }</span>
					    <div class="btn btn-xs btn-success">
					     	<span class="glyphicon glyphicon-plus"></span>
						    <span style="font-size: 13px;">关注</span>
					    </div>
				    </div>
			       </div>
		     <div class="row  meta">
				<div class="col-xs-12">
				 <small class="publish-time font-color" style="font-weight: 1150;">2016.11.05 16:49</small>
				 <small class="wordage hidden-xs">字数 1551</small>
				 <small class="read-count hidden-xs">阅读 ${requestScope.articleReadCount }</small>
				 <small class="like-count hidden-xs">喜欢 ${requestScope.artucleLikeCount }</small>
				 <small class="commments-count hidden-xs 	">评论 ${requestScope.articleComments }</small>
				</div>
				
			</div>
				</div>
				
			</div>
			
			<!--文章数据信息-->
			<!--文章内容-->
			
			<div class="row article-content">
				<c:out value="${requestScope.articleContent}"  escapeXml="false" />
				
			</div>
			</div>
			<div class="hidden-xs col-lg-2"></div>
			
		</div>
		</div>
		
</body>
</html>
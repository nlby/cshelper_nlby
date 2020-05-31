<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 页面头部 -->
<header class="main-header">
	<!-- Logo -->
	<a href="${pageContext.request.contextPath}/pages/main.jsp" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>数据</b></span> <!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>数据</b>后台管理</span>
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span>
		</a>

		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">

				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <img
						src="${pageContext.request.contextPath}/uploads/${userId}"
						class="user-image" alt="User Image"> <span class="hidden-xs">
						${username}
					</span>

				</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header"><img
							src="${pageContext.request.contextPath}/uploads/${userId}"
							class="img-circle" alt="User Image"></li>

						<!-- Menu Footer-->
						<li class="user-footer">
							<form action="${pageContext.request.contextPath}/file/uploadAvatar.do" method="post" enctype="multipart/form-data">
								<input hidden="hidden" name="id" value="${userId}">
							<div class="pull-left">
								上传头像<input type="file" name="upload" class="btn btn-default btn-flat"/>
							</div>
							<div class="pull-left">
								<input type="submit"  class="btn btn-default btn-flat"/>
<%--								<a href="#" class="btn btn-default btn-flat">保存</a>--%>
							</div>
							<div class="pull-right">
								<a href="${pageContext.request.contextPath}/pages/login.jsp"
									class="btn btn-default btn-flat">注销</a>
							</div>
							</form>
						</li>
					</ul></li>

			</ul>
		</div>
	</nav>
</header>
<!-- 页面头部 /-->
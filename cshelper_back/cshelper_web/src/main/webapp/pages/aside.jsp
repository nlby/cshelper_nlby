<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">

		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="${pageContext.request.contextPath}/uploads/${userId}"
					 class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p>${username}</p>
			</div>
		</div>
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">菜单</li>
			<li id="admin-index"><a
				href="${pageContext.request.contextPath}/pages/main.jsp"><i
					class="fa fa-dashboard"></i> <span>首页</span></a></li>

			<li class="treeview"><a href="#"> <i class="fa fa-cogs"></i>
					<span>Web端</span> <span class="pull-right-container"> <i
						class="fa fa-angle-left pull-right"></i>
				</span>


			</a>
				<ul class="treeview-menu">

					<li id="system-setting"><a
						href="${pageContext.request.contextPath}/pages/editor.jsp"> <i
							class="fa fa-circle-o"></i> 编辑日志
					</a></li>
					<li id="system-setting"><a
						href="${pageContext.request.contextPath}/blog/findAll.do"> <i
							class="fa fa-circle-o"></i> 浏览日志
					</a></li>

				</ul></li>
			<li class="treeview"><a href="#"> <i class="fa fa-cube"></i>
					<span>移动端</span> <span class="pull-right-container"> <i
						class="fa fa-angle-left pull-right"></i>
				</span>
			</a>
				<ul class="treeview-menu">

					<li id="system-setting"><a
						href="${pageContext.request.contextPath}/note/findAll.do">
							<i class="fa fa-circle-o"></i> 笔记管理
					</a></li>
					<li id="system-setting"><a
						href="${pageContext.request.contextPath}/bookmark/findAll.do?page=1&size=10"> <i
							class="fa fa-circle-o"></i> 书签管理
					</a></li>
					<li id="system-setting"><a
							href="${pageContext.request.contextPath}/code/findAll_web.do">
						<i class="fa fa-circle-o"></i> 题目管理
					</a></li>

				</ul></li>

		</ul>

		<embed src="https://jxiaoc.github.io/animeMusic/demo.html" width="100%" height="50%" >
	</section>
	<!-- /.sidebar -->
</aside>
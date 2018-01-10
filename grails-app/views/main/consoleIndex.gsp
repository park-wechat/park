<%--
  Created by IntelliJ IDEA.
  User: oswaldl
  Date: 7/22/2014
  Time: 5:29 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- <meta name="layout" content="main"/> -->
    <title>移动端后台</title>
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'common_2.css')}" />
    <r:external uri="/js/jquery-1.10.2.js" />
    <r:external uri="/js/common_2.js" />
</head>
<body>
<div class="wrapper">
    <!-- top -->
    <div class="sys_top">
        <div class="sys_top_menu">
            <div class="menu_left">
                欢迎您 <strong>${user.username}</strong> ${dateStr} <span class="white"> | </span><g:link controller="logout">[注销]</g:link>
            </div>
            <%--            <div class="menu_right">--%>
            <%--	            <a href="index_2.html">后台首页</a><span class="white"> | </span><a href="../index.html">前台首页</a>--%>
            <%--            </div>--%>
        </div>
        <div class="sys_top_banner">
            <div class="menu_right">
                <input class="search" type="text" placeholder="输入关键字回车搜索" value="" />
            </div>
            <div class="sys_logo">移动应用后台管理</div>
        </div>
    </div>
    <!-- content -->
    <div class="sys_content">
        <div class="content_menu">
            <div class="menu_title">导航菜单 <span class="cosp">展开全部</span></div>
            <ul class="menu_inner">
                <li>
                    <a href="${createLink(controller:'user',action:'index')}" target="main_frame">用户管理</a>
                </li>
                <li>
                    <a href="${createLink(controller:'application',action:'index')}" target="main_frame">应用管理</a>
                </li>
                <li>
                    <a href="${createLink(controller:'applicationUser',action:'index')}" target="main_frame">用户应用权限管理</a>
                </li>
                <li>
                    <a href="${createLink(controller:'connection',action:'index')}" target="main_frame">链接管理</a>
                </li>
                <li>
                    <a href="${createLink(controller:'newsNotice',action:'index')}" target="main_frame">新闻培训</a>
                </li>
                <li>
                    <a href="${createLink(controller:'training',action:'index')}" target="main_frame">培训</a>
                </li>
                <li>
                    <a href="${createLink(controller:'appVersion',action:'updateVersion')}" target="main_frame">APP版本控制发布</a>
                </li>
            </ul>
        </div>
        <div class="content_main">
            <div class="main_inner">
                <iframe class="main_iframe" id="main_frame" name="main_frame" frameborder="0"></iframe>
            </div>
        </div>
    </div>
    <!-- bottom -->
    <div class="sys_bottom">
        <div class="bottom_copyright">版权所有&nbsp;&copy;&nbsp;2014&nbsp;深圳市中易科技有限公司</div>
        <div class="bottom_version">当前版本 1.0</div>
    </div>
</div>
<script>
    HC.initMenu();
    HC.initCosp();
</script>

</body>
</html>
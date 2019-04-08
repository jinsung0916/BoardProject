<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<header>
    <div class="home-menu pure-menu pure-menu-horizontal">
        <a class="pure-menu-heading" href="/myapp/board/list">Board</a>
        <ul class="pure-menu-list">
            <sec:authorize access="isAnonymous()">
            	<li class="pure-menu-item"><a href="/myapp/customLogin">Login</a></li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
            	<li class="pure-menu-item"><a href="/myapp/logout">Logout</a></li>
            </sec:authorize>
        </ul>
    </div>
</header>
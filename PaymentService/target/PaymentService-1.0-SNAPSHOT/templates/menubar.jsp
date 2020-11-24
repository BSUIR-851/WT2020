<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- MENU BAR -->
<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand" href="${contextPath}">
            <i class="fa fa-line-chart"></i>
            PayS
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a href="#" class="nav-link smoothScroll">About</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link smoothScroll">Contact</a>
                </li>
                <c:if test="${sessionScope.userId == null}">
                    <li class="nav-item">
                        <a href="${contextPath}/login" class="nav-link contact">Log In</a>
                    </li>
                    <li class="nav-item">
                        <a href="${contextPath}/signup" class="nav-link contact">Sign Up</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.userId != null}">
                    <li class="nav-item">
                        <a href="${contextPath}/logout" class="nav-link contact">Log Out</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

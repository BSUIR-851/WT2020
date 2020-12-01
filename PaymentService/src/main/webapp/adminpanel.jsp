<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<!-- TITLE AND STYLES -->
<c:import url="templates/header.jsp">
    <c:param name="title" value="Admin panel | PayS"/>
</c:import>

<body>

<!-- MENU BAR -->
<c:import url="templates/menubar.jsp"/>

<!-- HERO -->
<section class="hero hero-bg d-flex justify-content-center align-items-center pt-5">
    <c:if test="${sessionScope.userId == null || sessionScope.privilegeLevel != 1}">
        <c:redirect url="/"/>
    </c:if>
    <c:if test="${sessionScope.userId != null && sessionScope.privilegeLevel == 1}">
        <div class="container-fluid ml-5 mr-5">
            <div class="row align-items-start">
                <div class="col-lg-12 d-flex flex-column justify-content-center align-items-center">
                    <div class="hero-text w-100">
                        <h1 class="text-white pt-0 mt-0" data-aos="fade-up">ADMIN PANEL:</h1>
                        <ul class="nav nav-tabs" data-aos="fade-up">
                            <li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#users">Users</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#requestedBlockedBankAccounts">Requests for unblock</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane fade show active" id="users">
                                <h2 class="text-white pt-0 mt-0" data-aos="fade-up">Users:</h2>
                                <div class="table100 ver2 m-b-10" data-aos="fade-up">
                                    <div class="table100-head">
                                        <table>
                                            <thead>
                                            <tr class="row100 head">
                                                <th class="cell100 column1-admin">№</th>
                                                <th class="cell100 column2-admin">Username</th>
                                                <th class="cell100 column3-admin">Email</th>
                                                <th class="cell100 column4-admin">First name</th>
                                                <th class="cell100 column5-admin">Last name</th>
                                                <th class="cell100 column6-admin">Make admin</th>
                                                <th class="cell100 column7-admin">Delete</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>

                                    <div class="table100-body js-pscroll">
                                        <table>
                                            <tbody>
                                            <c:set var="num" value="${1}"/>
                                            <c:forEach var="user" items="${users}">
                                                <tr class="row100 body">
                                                    <th class="cell100 column1-admin">${num}</th>
                                                    <td class="cell100 column2-admin">${user.getUsername()}</td>
                                                    <td class="cell100 column3-admin">${user.getEmail()}</td>
                                                    <td class="cell100 column4-admin">${user.getFirstName()}</td>
                                                    <td class="cell100 column5-admin">${user.getLastName()}</td>
                                                    <td class="cell100 column6-admin">
                                                        <a href="${contextPath}/adminmakeadmin?userId=${user.getId()}" class="custom-btn-small btn-bg-bordered btn mt-1">Make admin</a>
                                                    </td>
                                                    <td class="cell100 column7-admin">
                                                        <a href="${contextPath}/admindeleteuser?userId=${user.getId()}" class="custom-btn-small btn-bg-bordered btn mt-1">Delete</a>
                                                    </td>
                                                </tr>
                                                <c:set var="num" value="${num + 1}"/>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="requestedBlockedBankAccounts">
                                <h2 class="text-white pt-0 mt-0" data-aos="fade-up">Requests for unblock bank accounts:</h2>
                                <div class="table100 ver2 m-b-10" data-aos="fade-up">
                                    <div class="table100-head">
                                        <table>
                                            <thead>
                                            <tr class="row100 head">
                                                <th class="cell100 column1-admin">№</th>
                                                <th class="cell100 column2-admin">Number</th>
                                                <th class="cell100 column3-admin">Balance</th>
                                                <th class="cell100 column4-admin">Unblock</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>

                                    <div class="table100-body js-pscroll">
                                        <table>
                                            <tbody>
                                            <c:set var="num" value="${1}"/>
                                            <c:forEach var="requestedBlockedBankAccount" items="${requestedBlockedBankAccounts}">
                                                <tr class="row100 body">
                                                    <th class="cell100 column1-admin">${num}</th>
                                                    <td class="cell100 column2-admin">${requestedBlockedBankAccount.getNumber()}</td>
                                                    <td class="cell100 column3-admin">${requestedBlockedBankAccount.getBalance()}</td>
                                                    <td class="cell100 column4-admin">
                                                        <a href="${contextPath}/adminunblockbankaccount?userId=${requestedBlockedBankAccount.getId()}" class="custom-btn-small btn-bg-bordered btn mt-1">Unblock</a>
                                                    </td>
                                                </tr>
                                                <c:set var="num" value="${num + 1}"/>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</section>

<!-- FOOTER -->
<c:import url="templates/footer.jsp"/>

<!-- SCRIPTS -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/aos.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/smoothscroll.js"></script>
<script src="js/custom.js"></script>

</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="col-lg-3 d-flex flex-column justify-content-center align-items-center">
    <div class="hero-text w-100">
        <h1 class="text-white pt-0 mt-0" data-aos="fade-up">Information:</h1>
        <div class="table100 ver2 m-b-10" data-aos="fade-up">
            <div class="table100-head">
                <table>
                    <thead>
                    <tr class="row100 head">
                        <th class="cell100 column1-info">Role:</th>
                        <th class="cell100 column2-info">USER</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div class="table100-body js-pscroll">
                <table>
                    <tbody>
                        <tr class="row100 body">
                            <th class="cell100 column1-info">Username:</th>
                            <td class="cell100 column2-info">${user.getUsername()}</td>
                        </tr>
                        <tr class="row100 body">
                            <th class="cell100 column1-info">BAN:</th>
                            <td class="cell100 column2-info">${bankAccount.getNumber()}</td>
                        </tr>
                        <tr class="row100 body">
                            <th class="cell100 column1-info">Balance:</th>
                            <td class="cell100 column2-info">${bankAccount.getBalance()}</td>
                        </tr>
                        <tr class="row100 body">
                            <th class="cell100 column1-info">Cards:</th>
                            <td class="cell100 column2-info">${cards.size()}</td>
                        </tr>
                        <tr class="row100 body">
                            <th class="cell100 column1-info">BAN status:</th>
                            <c:if test="${isBlocked}">
                                <td class="cell100 column2-info">BLOCKED</td>
                            </c:if>
                            <c:if test="${!isBlocked}">
                                <td class="cell100 column2-info">Not blocked</td>
                            </c:if>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col">
            <c:if test="${isBlocked}">
                <a href="${contextPath}/requestforunblock" class="custom-btn btn-bg-bordered btn mt-3" data-aos="fade-up" data-aos-delay="100">Unblock</a>
            </c:if>
            <c:if test="${!isBlocked}">
                <a href="${contextPath}/blockbankaccount" class="custom-btn btn-bg-bordered btn mt-3" data-aos="fade-up" data-aos-delay="100">Block</a>
            </c:if>
            <a href="${contextPath}/edituserinfo" class="custom-btn btn-bg-bordered btn mt-3" data-aos="fade-up" data-aos-delay="100">Edit data</a>
            <a href="${contextPath}/addfunds" class="custom-btn btn-bg-bordered btn mt-3" data-aos="fade-up" data-aos-delay="100">Add funds</a>
        </div>
    </div>
</div>
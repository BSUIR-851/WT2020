<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<c:import url="templates/header.jsp">
    <c:param name="title" value="Request for unblock bank account | Pays"/>
</c:import>

<body>

<!-- MENU BAR -->
<c:import url="templates/menubar.jsp"/>

<section class="contact section-padding-half">
    <div class="container">
        <div class="row">
            <c:if test="${sessionScope.userId == null}">
                <c:redirect url="/"/>
            </c:if>
            <c:if test="${sessionScope.userId != null}">
                <c:if test="${unblockRes == 0 || unblockRes == null}">
                    <div class="col-lg-6 mx-auto col-md-7 col-12 pt-0 mt-0 text-center" data-aos="fade-up">
                        <c:if test="${errmsg == null}">
                            <h1 class="mb-4">Do you really want to <strong>unblock</strong> a <strong>bank account</strong>?</h1>
                        </c:if>
                        <c:if test="${errmsg != null}">
                            <h5 class="mb-4 mx-auto" data-aos="fade-up">${errmsg}</h5>
                        </c:if>
                    </div>
                    <div class="col-lg-8 mx-auto col-md-10 col-12">
                        <form action="requestforunblock" method="post" class="contact-form" data-aos="fade-up" data-aos-delay="100" role="form">
                            <div class="col-lg-5 mx-auto col-7">
                                <button type="submit" class="form-control" id="submit-button" name="submit">Unblock bank account</button>
                            </div>
                        </form>
                    </div>
                </c:if>
                <c:if test="${unblockRes != 0 && bankAccount != null}">
                    <div class="col-lg-6 mx-auto col-md-7 col-12 pt-0 mt-0 text-center" data-aos="fade-up">
                        <h1 class="mb-4">Request to unblock the bank account was sent <strong>successfully</strong>!</h1>
                        <h3 class="mb-4">Wait until the administrator has unblocked your bank account.</h3>
                        <h4 class="mb-4">Number: <strong>${bankAccount.getNumber()}</strong></h4>
                        <a href="${contextPath}" class="custom-btn">Back to main</a>
                    </div>
                </c:if>
            </c:if>
        </div>
    </div>
</section>

<!-- FOOTER -->
<c:import url="templates/footer.jsp"/>


<!-- SCRIPTS -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/aos.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>
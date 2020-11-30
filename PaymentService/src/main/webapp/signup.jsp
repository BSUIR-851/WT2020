<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<c:import url="templates/header.jsp">
    <c:param name="title" value="Sign Up | Pays"/>
</c:import>

<body>

    <!-- MENU BAR -->
    <c:import url="templates/menubar.jsp"/>

    <!-- REGISTRATION FORM -->
    <section class="contact section-padding-half">
        <div class="container">
            <div class="row">
                <c:if test="${sessionScope.userId == null}">
                    <c:if test="${regRes == 0}">
                        <div class="col-lg-6 mx-auto col-md-7 col-12 pt-0 mt-0 text-center" data-aos="fade-up">
                            <c:if test="${errmsg == null}">
                                <h1 class="mb-4">Hey there, let's <strong>create</strong> an <strong>account</strong></h1>
                            </c:if>
                            <c:if test="${errmsg != null}">
                                <h5 class="mb-4 mx-auto" data-aos="fade-up">${errmsg}</h5>
                            </c:if>
                        </div>
                        <div class="col-lg-8 mx-auto col-md-10 col-12">
                            <form action="signup" method="post" class="contact-form" data-aos="fade-up" data-aos-delay="100" role="form">
                                <div class="col-lg-6 col-12 mx-auto">
                                    <input required type="text" class="form-control" name="username" placeholder="Username">
                                    <input required type="email" class="form-control" name="email" placeholder="Email">
                                    <input required type="password" class="form-control" name="pass" placeholder="Your password">
                                    <input required type="text" class="form-control" name="first-name" placeholder="First name">
                                    <input required type="text" class="form-control" name="last-name" placeholder="Last name">
                                </div>

                                <div class="col-lg-5 mx-auto col-7">
                                    <button type="submit" class="form-control" id="submit-button" name="submit">Sign Up</button>
                                </div>
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${regRes != 0}">
                        <div class="col-lg-6 mx-auto col-md-7 col-12 pt-0 mt-0 text-center" data-aos="fade-up">
                            <h1 class="mb-4">Registration completed <strong>successfully</strong>!</h1>
                            <a href="${contextPath}" class="custom-btn">Back to main</a>
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.userId != null}">
                    <c:redirect url="/"/>
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
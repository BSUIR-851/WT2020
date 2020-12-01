<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<c:import url="templates/header.jsp">
    <c:param name="title" value="Add funds | Pays"/>
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
                <c:if test="${makeRes == 0 || makeRes == null}">
                    <div class="col-lg-6 mx-auto col-md-7 col-12 pt-0 mt-0 text-center" data-aos="fade-up">
                        <h1 class="mb-4">Please, fill data to make <strong>transaction</strong>.</h1>
                        <c:if test="${errmsg != null}">
                            <h5 class="mb-4 mx-auto" data-aos="fade-up">${errmsg}</h5>
                        </c:if>
                    </div>
                    <div class="col-lg-8 mx-auto col-md-10 col-12 text-center">
                        <form action="maketransaction" method="post" class="contact-form" data-aos="fade-up" data-aos-delay="100" role="form">
                            <div class="col-lg-6 col-12 mx-auto">
                                <label>
                                    From:
                                    <select required name="scard-id" class="form-control">
                                        <option selected value="0">Select your card</option>
                                        <c:forEach var="card" items="${cards}">
                                            <option value="${card.getId()}">${card.getNumber()}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                                <label>
                                    To:
                                    <input required type="text" class="form-control" name="dcard-number">
                                </label>
                                <input required type="number" class="form-control" name="funds-amount" pattern="\d+(\.\d{2})" placeholder="0.00" step="0.01">
                                <label>
                                    PIN:
                                    <input required type="number" class="form-control" name="pin-scard" pattern="\d{4}" placeholder="9999">
                                </label>
                                <label>
                                    CVV:
                                    <input required type="number" class="form-control" name="cvv-scard" pattern="\d{3}" placeholder="999">
                                </label>
                            </div>
                            <div class="col-lg-5 mx-auto col-7">
                                <button type="submit" class="form-control" id="submit-button" name="submit">Add funds</button>
                            </div>
                        </form>
                    </div>
                </c:if>
                <c:if test="${makeRes != null && makeRes != 0 && scardNumber != null && dcardNumber != null && fundsAmount != null}">
                    <div class="col-lg-6 mx-auto col-md-7 col-12 pt-0 mt-0 text-center" data-aos="fade-up">
                        <h1 class="mb-4">Transaction completed <strong>successfully</strong>!</h1>
                        <h4 class="mb-4">Source card: <strong>${scardNumber}</strong></h4>
                        <h4 class="mb-4">Destination card: <strong>${dcardNumber}</strong></h4>
                        <h4 class="mb-4">Payment amount: <strong>${fundsAmount}</strong></h4>
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
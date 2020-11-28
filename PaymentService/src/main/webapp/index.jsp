<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<!-- TITLE AND STYLES -->
<c:import url="templates/header.jsp">
    <c:param name="title" value="Payment service | PayS"/>
</c:import>

<body>

    <!-- MENU BAR -->
    <c:import url="templates/menubar.jsp"/>

    <!-- HERO -->
    <section class="hero hero-bg d-flex justify-content-center align-items-center pt-5">
        <div class="container">
            <c:if test="${sessionScope.userId == null}">
                <div class="row">
                    <div class="col-lg-6 col-md-10 col-12 d-flex flex-column justify-content-center align-items-center">
                        <div class="hero-text">
                            <h1 class="text-white" data-aos="fade-up">We are ready for your payments</h1>
                            <a href="${contextPath}/signup" class="custom-btn btn-bg btn mt-3" data-aos="fade-up" data-aos-delay="100">Let do it together!</a>
                        </div>
                    </div>
                    <div class="col-lg-6 col-12">
                        <div class="hero-image" data-aos="fade-up" data-aos-delay="300">
                            <img src="images/working-girl.png" class="img-fluid" alt="working girl">
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.userId != null}">
                <div class="row">
                    <c:if test="${cards.size() != 0}">
                        <div class="col-lg-12 d-flex flex-column justify-content-center align-items-center">
                            <div class="hero-text w-100">
                                <h1 class="text-white pt-0 mt-0" data-aos="fade-up">Your cards:</h1>
                                <div class="table100 ver2 m-b-10" data-aos="fade-up">
                                    <div class="table100-head">
                                        <table>
                                            <thead>
                                                <tr class="row100 head">
                                                    <th class="cell100 column1">№</th>
                                                    <th class="cell100 column2">Number</th>
                                                    <th class="cell100 column3">Balance</th>
                                                    <th class="cell100 column4">Status</th>
                                                    <th class="cell100 column5">Expire date</th>
                                                    <th class="cell100 column6">Block card</th>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div>

                                    <div class="table100-body js-pscroll">
                                        <table>
                                            <tbody>
                                            <c:set var="num" value="${1}"/>
                                            <c:forEach var="card" items="${cards}">
                                                <tr class="row100 body">
                                                    <th class="cell100 column1">${num}</th>
                                                    <td class="cell100 column2">${card.getNumber()}</td>
                                                    <td class="cell100 column3">${card.getBalance()}</td>
                                                    <td class="cell100 column4">Valid</td>
                                                    <td class="cell100 column5">${card.getExpireDate()}</td>
                                                    <td class="cell100 column6">
                                                        <a href="${contextPath}/blockcard?cardid=${card.getId()}" class="custom-btn-small btn-bg-bordered btn-sm">Block card</a>
                                                    </td>
                                                </tr>
                                                <c:set var="num" value="${num + 1}"/>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <a href="${contextPath}/createcard" class="custom-btn btn-bg-bordered btn mt-3" data-aos="fade-up" data-aos-delay="100">Create card</a>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${cards.size() == 0}">
                        <div class="col-lg-6 col-md-10 col-12 d-flex flex-column justify-content-center align-items-center">
                            <div class="hero-text">
                                <h1 class="text-white" data-aos="fade-up">You <strong>don't</strong> have any cards.</h1>
                                <a href="${contextPath}/createcard" class="custom-btn btn-bg btn mt-3" data-aos="fade-up" data-aos-delay="100">Create card</a>
                            </div>
                        </div>
                        <div class="col-lg-6 col-12">
                            <div class="hero-image" data-aos="fade-up" data-aos-delay="300">
                                <img src="images/bw-creditcard.png" class="img-fluid" alt="creditcard">
                            </div>
                        </div>
                    </c:if>
                </div>
            </c:if>
        </div>
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
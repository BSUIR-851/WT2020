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
     <section class="hero hero-bg d-flex justify-content-center align-items-center">
           <div class="container">
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
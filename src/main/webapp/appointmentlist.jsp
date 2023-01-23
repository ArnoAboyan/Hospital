<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<c:set var="currentAddressPage" value="controller?command=appointmentpagecommand&page=1" scope="session"></c:set>

<html>
<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Admin Panel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body style=" margin-bottom: 170px; background-repeat: no-repeat; background-attachment: fixed; background-image: url('https://phonoteka.org/uploads/posts/2022-01/1643186349_1-phonoteka-org-p-svetlii-belii-fon-1.jpg');">
<header>
  <nav class="navbar navbar-expand-lg" style="background-color: #e3f2fd;">
    <div class="container-fluid">
      <a class="navbar-brand" >Hospital</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
              data-bs-target="#navbarNavDarkDropdown" aria-controls="navbarNavDarkDropdown" aria-expanded="false"
              aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
        <ul class="navbar-nav">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
               aria-expanded="false">
              <fmt:message key="admin_jsp.Appointments"/>
            </a>
            <ul class="dropdown-menu dropdown-menu-dark">
              <li><a class="dropdown-item" href="controller?command=adminpagecommand&page=1"><fmt:message key="admin_jsp.Doctors"/></a></li>
              <li><a class="dropdown-item" href="controller?command=patientlistcommand&page=1"><fmt:message key="admin_jsp.Patients"/></a></li>
              <li><a class="dropdown-item" href="controller?command=appointmentpagecommand&page=1"><fmt:message key="admin_jsp.Appointments"/></a></li>

            </ul>
          </li>
        </ul>
      </div>
      <form action="controller" method="get">
        <input type="hidden" name="command" value="logout">
        <button type="submit" class="btn btn-outline-secondary" style="background-color: #e3f2fd;">logout</button>
      </form>
    </div>
  </nav>
</header>


<section class="appointments flex" >

  <div class="container-sm" >
    <div class="row">
      <div class="col-md-12">
        <table class="table table-sm">
          <thead>
          <tr>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" ><fmt:message key="admin_jsp.Doctor"/></a></th>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" ><fmt:message key="admin_jsp.Category"/></a></th>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" ><fmt:message key="admin_jsp.Patient"/></a></th>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" href="controller?command=appointmentpagecommand&page=1&sort=appointments_data"><fmt:message key="admin_jsp.Appointmentu25BC"/></a></th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="appointment" items="${appointment}">
            <tr>
            <tr>
              <td>${appointment.appointmentDoctorName} ${appointment.appointmentDoctorSurname}</td>
              <td>${appointment.appointmentDoctorCategory}</td>
              <td>${appointment.appointmentPatientName} ${appointment.appointmentPatientSurname}</td>
              <td>${appointment.appointmentData}</td>
              <td>
                <button type="button" class="btn btn-danger btn-sm " data-bs-toggle="modal"
                        data-bs-target="#deletePatientModal${appointment.appointmentId}">X
                </button>
                <div class="modal" id="deletePatientModal${appointment.appointmentId}" tabindex="-1">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title"><fmt:message key="admin_jsp.Delete"/></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        <div class="h4 pb-2 mb-4 text-danger border-bottom border-danger">
                          <fmt:message key="admin_jsp.Attention!"/>
                        </div>
                        >
                        <a class="text-secondary text-decoration-none"><fmt:message key="admin_jsp.Delete"/>:
                            ${appointment.appointmentDoctorName} ${appointment.appointmentDoctorSurname} | ${appointment.appointmentPatientName} ${appointment.appointmentPatientSurname}
                          ?</a>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                          <fmt:message key="admin_jsp.Close"/>
                        </button>
                        <form action="controller" method="get">
                          <input type="hidden" name="command" value="deleteappointmentcommand">
                          <input type="hidden" name="appointmentid" value=${appointment.appointmentId}>
                          <button type="submit" class="btn btn-primary"><fmt:message key="admin_jsp.Yes"/></button>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>

</section>

<%--PAGINATION--%>
<div class="catalog-pagination">
  <nav aria-label="page-navigation">
    <ul class="pagination justify-content-center">
      <c:choose>
        <c:when test="${sort == null}">
          <c:choose>
            <c:when test="${page - 1 > 0}">
              <li class="page-item">
                <a href="controller?command=appointmentpagecommand&page=${page-1}" class="btn btn-primary btn-sm">⮜</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-secondary btn-sm">⮜</a>
              </li>
            </c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${page + 1 <= countPage}">
              <li class="page-item">
                <a href="controller?command=appointmentpagecommand&page=${page+1}" class="btn btn-primary btn-sm">⮞</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-secondary btn-sm">⮞</a>
              </li>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:choose>
            <c:when test="${page - 1 > 0}">
              <li class="page-item">
                <a href="controller?command=appointmentpagecommand&page=${page-1}&sort=${sort}" class="btn btn-primary btn-sm">⮜</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-secondary btn-sm">⮜</a>
              </li>
            </c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${page + 1 <= countPage}">
              <li class="page-item">
                <a href="controller?command=appointmentpagecommand&page=${page+1}&sort=${sort}" class="btn btn-primary btn-sm">⮞</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-secondary btn-sm">⮞</a>
              </li>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
    </ul>
  </nav>
</div>
</div>

<%--FOOTER--%>
<footer class="pt-1 my-md-1 pt-md-1 border-top abs "
        style="position: fixed;
        bottom: -10px;
        height: 70px;
        background: #e3f2fd;
        width: 100%;
        left: 0;">
  <div class="container text-center text-md-left">
    <div class="d-flex">
      <div class="p-2 flex-grow-1">Make by Arno</div>
      <div class="p-2">099 111 22 33</div>
      <div class="p-2"><mylib:languages></mylib:languages></div>
    </div>
  </div>
</footer>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
        integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V"
        crossorigin="anonymous"></script>
</body>
</html>

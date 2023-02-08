<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<c:set var="currentAddressPage" value="controller?command=patientlistbydoctor&patientsfordoctorid=${doctorid}&page=1" scope="session"></c:set>

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
      <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <form action="controller" method="get">
          <input type="hidden" name="command" value="logout">
          <button type="submit" class="btn btn-outline-secondary" style="background-color: #e3f2fd;"><fmt:message key="admin_jsp.logout"/></button>
        </form>
      </div>
    </div>
    </div>
  </nav>
</header>


<section class="adminpage-patientbydoctorid flex">
  <h3> <fmt:message key="admin_jsp.Welcome"/>: ${doctor.doctorName} ${doctor.doctorSurname} <fmt:message key="admin_jsp.yourpatients"/>: <span class="badge bg-info" style="background-color: #e3f2fd;">${numberOfPatients}</span></h3>
  <div class="container-sm">
    <div class="row">
      <div class="col-md-12">
        <table class="table table-sm">
          <thead>
          <tr>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" href="controller?command=patientlistbydoctor&page=1&sort=patient_name&patientsfordoctorid=${doctor.doctorId}"><fmt:message key="admin_jsp.Name"/></a></th>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" href="controller?command=patientlistbydoctor&page=1&sort=patient_surname&patientsfordoctorid=${doctor.doctorId}"><fmt:message key="admin_jsp.Surname"/></a></th>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" href="controller?command=patientlistbydoctor&page=1&sort=patient_date_of_birth&patientsfordoctorid=${doctor.doctorId}"><fmt:message key="admin_jsp.DateOfBirth"/></a></th>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" href="controller?command=patientlistbydoctor&page=1&sort=phone&patientsfordoctorid=${doctor.doctorId}"><fmt:message key="admin_jsp.Phone"/></a></th>
            <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info" style="background-color: #e3f2fd;" href="controller?command=patientlistbydoctor&page=1&sort=gender&patientsfordoctorid=${doctor.doctorId}"><fmt:message key="admin_jsp.Gender"/></a></th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="patient" items="${patientsbydoctor}">
            <tr>
            <tr>
              <td>${patient.patientName}</td>
              <td>${patient.patientSurname}</td>
              <td>${patient.patientDateOfBirth}</td>
              <td>${patient.patientPhone}</td>
              <td>${patient.patientGender}</td>
              <td>


                <form action="controller" method="get">
                  <input type="hidden" name="command" value="hospitalcardbypatientid">
                  <input type="hidden" name="patientid" value=${patient.patientId}>
                  <input type="hidden" name="doctorid" value=${doctorid}>
                  <button type="submit" class="btn btn-outline-primary" ><fmt:message key="admin_jsp.Card"/></button>
                </form>
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
                <a href="controller?command=patientlistbydoctor&page=${page-1}&patientsfordoctorid=${doctor.doctorId}" class="btn btn-outline-primary btn-sm">⮜</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-outline-secondary btn-sm disabled">⮜</a>
              </li>
            </c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${page + 1 <= countPage}">
              <li class="page-item">
                <a href="controller?command=patientlistbydoctor&page=${page+1}&patientsfordoctorid=${doctor.doctorId}" class="btn btn-outline-primary btn-sm">⮞</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-outline-secondary btn-sm disabled">⮞</a>
              </li>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:choose>
            <c:when test="${page - 1 > 0}">
              <li class="page-item">
                <a href="controller?command=patientlistbydoctor&page=${page-1}&sort=${sort}&patientsfordoctorid=${doctor.doctorId}" class="btn btn-outline-primary btn-sm">⮜</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-outline-secondary btn-sm disabled">⮜</a>
              </li>
            </c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${page + 1 <= countPage}">
              <li class="page-item">
                <a href="controller?command=patientlistbydoctor&page=${page+1}&sort=${sort}&patientsfordoctorid=${doctor.doctorId}" class="btn btn-outline-primary btn-sm">⮞</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="btn btn-outline-secondary btn-sm disabled">⮞</a>
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
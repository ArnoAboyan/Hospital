<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<c:set var="currentAddressPage" value="controller?command=hospitalcardlist&page=1" scope="session"></c:set>

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
            <a class="navbar-brand">Hospital</a>
        </div>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="logout">
            <button type="submit" class="btn btn-outline-secondary" style="background-color: #e3f2fd;"><fmt:message
                    key="admin_jsp.logout"/></button>
        </form>
        </div>
    </nav>
</header>

<%--SORT--%>

<section class="patientpage-patient flex">
    <div class="container-sm">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-sm">
                    <thead>
                    <tr>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=hospitalcardlist&page=1&sort=patientid"><fmt:message key="admin_jsp.PatientsArrowDown"/></a>
                        </th>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=hospitalcardlist&page=1&sort=doctorid"><fmt:message key="admin_jsp.DoctorArrowDown"/></a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="hospitallist" items="${allHospitalCards}">
                        <tr>
                        <tr>
                            <td>${hospitallist.patientNameById} ${hospitallist.patientSurnameById}</td>
                            <td>${hospitallist.doctorNameById} ${hospitallist.doctorSurnameById}
                                | ${hospitallist.doctorCategoryById}
                            </td>
                              <td>
                                <form action="controller" method="get">
                                    <input type="hidden" name="command" value="hospitalcardbyid">
                                    <input type="hidden" name="hospitalCardId" value=${hospitallist.hospitalCardId}>
                                    <button type="submit" class="btn btn-primary"><fmt:message
                                            key="admin_jsp.Card"/></button>
                                </form>
                            </td>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>


<!-- Modal -->
<div class="modal fade" id="addPatientModal" tabindex="-1" aria-labelledby="addPatientModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel"><fmt:message key="admin_jsp.AddPatient"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <div class="mb-3">
                        <input type="hidden" name="command" value="addpatientcommand">
                        <input type="text" name="name" class="form-control" id="InputName" aria-describedby="nameHelp"
                               required minlength="3" maxlength="18"
                               placeholder="<fmt:message key="admin_jsp.patientlistEnterpatientname"/>"
                               pattern="[A-Za-z]{3,}" title="Three or more letters">
                    </div>
                    <div class="mb-3">
                        <input type="text" class="form-control"
                               placeholder="<fmt:message key="admin_jsp.patientlistEnterpatientsurname"/>"
                               id="InputSurname"
                               name="surname" required minlength="3"
                               maxlength="18" pattern="[A-Za-z]{3,}" title="Three or more letters">
                    </div>
                    <div class="mb-3">
                        <div>
                            <label for="phone"><fmt:message key="admin_jsp.patientlistEnteraphonenumber"/></label>
                            <input type="number" id="phone" name="phone"
                                   placeholder="0991234567"
                                   pattern="[0-9]{10}" required></div>
                    </div>
                    <div class="mb-3">
                        <div>
                            <label>
                                <fmt:message key="admin_jsp.patientlistEnteryourbirthday"/>
                                <input type="date" name="birthday" required>
                            </label>
                        </div>
                    </div>
                    <div class="mb-3">
                        <div>
                            <input type="radio" id="male" name="gender" value="male" required>
                            <label for="male"><fmt:message key="admin_jsp.patientlistMale"/></label>
                        </div>
                        <div>
                            <input type="radio" id="female" name="gender" value="female" required>
                            <label for="female"><fmt:message key="admin_jsp.patientlistFemale"/></label>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-secondary btn-lg">Add</button>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

<%--PAGINATION--%>
<div class="catalog-pagination">
    <nav aria-label="page-navigation">
        <ul class="pagination justify-content-center">
            <c:choose>
                <c:when test="${sort == null}">
                    <c:choose>
                        <c:when test="${page - 1 > 0}">
                            <li class="page-item">
                                <a href="controller?command=hospitalcardlist&page=${page-1}"
                                   class="btn btn-outline-primary btn-sm">⮜</a>
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
                                <a href="controller?command=hospitalcardlist&page=${page+1}"
                                   class="btn btn-outline-primary btn-sm">⮞</a>
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
                                <a href="controller?command=hospitalcardlist&page=${page-1}&sort=${sort}"
                                   class="btn btn-outline-primary btn-sm">⮜</a>
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
                                <a href="controller?command=hospitalcardlist&page=${page+1}&sort=${sort}"
                                   class="btn btn-outline-primary btn-sm">⮞</a>
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
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<c:set var="currentAddressPage" value="controller?command=adminpagecommand&page=1" scope="session"></c:set>

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
    <nav class="navbar navbar-expand-lg " style="background-color: #e3f2fd;">
        <div class="container-fluid">
            <a class="navbar-brand">Hospital</a>
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
                            <fmt:message key="admin_jsp.Doctors"/>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark">
                            <li><a class="dropdown-item" href="controller?command=adminpagecommand&page=1"><fmt:message
                                    key="admin_jsp.Doctors"/></a>
                            </li>
                            <li><a class="dropdown-item"
                                   href="controller?command=patientlistcommand&page=1"><fmt:message
                                    key="admin_jsp.Patients"/></a></li>
                            <li><a class="dropdown-item"
                                   href="controller?command=appointmentpagecommand&page=1"><fmt:message
                                    key="admin_jsp.Appointments"/></a></li>
                        </ul>
                    </li>
                </ul>
                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal"
                        data-bs-target="#addDoctorModal">
                    <fmt:message key="admin_jsp.doctorAddDoctor"/>
                </button>
<%--                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal"--%>
<%--                        data-bs-target="#addNurseModal">--%>
<%--                    add nurse--%>
<%--                </button>--%>
            </div>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="logout">
                <button type="submit" class="btn btn-outline-secondary" style="background-color: #e3f2fd;"><fmt:message
                        key="admin_jsp.logout"/>
                </button>
            </form>
        </div>
    </nav>
</header>


<section class="adminpage-doctor flex">
    <div class="container-sm">
        <div class="row">
            <div class="col-md-12 ">
                <table class="table table-sm ">
                    <thead>
                    <tr>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=adminpagecommand&page=1&sort=doctor_name"><fmt:message
                                key="admin_jsp.Name"/></a>
                        </th>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=adminpagecommand&page=1&sort=doctor_surname"><fmt:message
                                key="admin_jsp.Surname"/></a></th>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=adminpagecommand&page=1&sort=login"><fmt:message
                                key="admin_jsp.doctorLogin"/></a></th>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=adminpagecommand&page=1&sort=password"><fmt:message
                                key="admin_jsp.doctorPassword"/></a></th>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=adminpagecommand&page=1&sort=category_id"><fmt:message
                                key="admin_jsp.doctorCategory"/></a></th>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=adminpagecommand&page=1&sort=role_id"><fmt:message
                                key="admin_jsp.doctorRole"/></a>
                        </th>
                        <th scope="col"><a class="list-group-item list-group-item-action list-group-item-info"
                                           style="background-color: #e3f2fd;"
                                           href="controller?command=adminpagecommand&page=1&sort=countofpatients"><fmt:message
                                key="admin_jsp.countOfPatients"/></a>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="doctor" items="${allDoctors}">
                    <tr>
                    <tr>
                            <%-- <th scope="row">1</th>--%>
                        <td>${doctor.doctorName}</td>
                        <td>${doctor.doctorSurname}</td>
                        <td>${doctor.login}</td>
                        <td>*******</td>
                        <td>${doctor.category}</td>
                        <td>${doctor.role}</td>
                        <td>${doctor.countOfPatients}</td>
                        <td>
                                <%-- MODAL DELETE--%>
                            <button type="button" class="btn btn-danger btn-sm " data-bs-toggle="modal"
                                    data-bs-target="#deleteDoctorModal${doctor.doctorId}">X
                            </button>
                            <div class="modal " id="deleteDoctorModal${doctor.doctorId}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title "><fmt:message
                                                    key="admin_jsp.doctorDeleteDoctor"/></h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="h4 pb-2 mb-4 text-danger border-bottom border-danger">
                                                <fmt:message key="admin_jsp.Attention!"/>
                                            </div>
                                            >
                                            <a class="text-secondary text-decoration-none "><fmt:message
                                                    key="admin_jsp.Delete"/>
                                                    ${doctor.doctorName} ${doctor.doctorSurname}
                                                ?</a>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                <fmt:message key="admin_jsp.Close"/>
                                            </button>
                                            <form action="controller" method="get">
                                                <input type="hidden" name="command" value="deletedoctorcommand">
                                                <input type="hidden" name="deletedoctor" value=${doctor.doctorId}>
                                                <button type="submit" class="btn btn-primary"><fmt:message
                                                        key="admin_jsp.Yes"/></button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <td>
                                <%--  MODAL UPDATE--%>
                            <button type="button" class="btn btn-info btn-sm " data-bs-toggle="modal"
                                    data-bs-target="#updateDoctorModal${doctor.doctorId}">Update
                            </button>
                            <div class="modal " id="updateDoctorModal${doctor.doctorId}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title ">Update Doctor</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">

                                            <form action="controller" method="get">
                                                <div class="mb-3">
                                                    <input type="hidden" name="command" value="updatedoctorcommand">
                                                    <input type="hidden" name="doctorid" value=${doctor.doctorId}>
                                                    <label for="changeInputName"><fmt:message key="admin_jsp.doctorWritename"/></label>
                                                    <input type="text" name="name" class="form-control"
                                                           id="changeInputName" aria-describedby="nameHelp"
                                                           required minlength="3" maxlength="20"
                                                           title="min 3 - max 20 letters"
                                                           pattern="[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}"
                                                           value="${doctor.doctorName}">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="changeInputSurname"><fmt:message key="admin_jsp.doctorWritesurname"/></label>
                                                    <input type="test" class="form-control" id="changeInputSurname"
                                                           name="surname" required minlength="3"
                                                           maxlength="20" pattern="[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}"
                                                           title="min 3 - max 20 letters"
                                                           value="${doctor.doctorSurname}">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="changeInputEmail1"><fmt:message key="admin_jsp.doctorWritelogin"/></label>
                                                    <input type="text" name="login" class="form-control"
                                                           id="changeInputEmail1"
                                                           aria-describedby="emailHelp" required minlength="4"
                                                           maxlength="20"
                                                           pattern="[A-Za-z]{3,20}"
                                                           title="min 3 - max 20 english letters"
                                                           value="${doctor.login}">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="changeInputPassword1"><fmt:message key="admin_jsp.doctorWritepassword"/></label>
                                                    <input type="text" class="form-control" id="changeInputPassword1"
                                                           name="password" required
                                                           minlength="4" maxlength="18"
                                                           pattern="[a-zA-Z0-9]{3,20}"
                                                           title="min 3 - max 20 english letters and numbers"
                                                           value="${doctor.password}">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="changeselectrole"><fmt:message key="admin_jsp.doctorSelectrole"/></label>
                                                    <select class="form-select" name="role"
                                                            id="changeselectrole"
                                                            required>
                                                        <option selected>${doctor.role}</option>
                                                        <option value="doctor">doctor</option>
                                                        <option value="nurse">nurse</option>
                                                    </select>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="changeselectCategory"><fmt:message key="admin_jsp.doctorSelectcategory"/></label>
                                                    <select class="form-select" name="category"
                                                            id="changeselectCategory"
                                                            required>
                                                        <option selected>${doctor.category}</option>
                                                        <option value="onkologist">onkologist</option>
                                                        <option value="traumatologist">traumatologist</option>
                                                        <option value="ophthalmologist">ophthalmologist</option>
                                                        <option value="dentist">dentist</option>
                                                        <option value="psychiatrist">psychiatrist</option>
                                                        <option value="therapist">therapist</option>
                                                        <option value="pediatrician">pediatrician</option>
                                                        <option value="nurse">NURSE</option>
                                                    </select>
                                                    <div class="modal-footer">
                                                        <button type="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
            </div>
            <td>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="patientlistbydoctor">
                    <input type="hidden" name="patientsfordoctorid" value=${doctor.doctorId}>
                    <input type="hidden" name="page" value=1>
                    <button type="submit" class="btn btn-primary btn-sm"><fmt:message
                            key="admin_jsp.doctorPatients"/></button>
                </form>
            </td>
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


<!-- Modal ADD DOCTOR -->
<div class="modal fade" id="addDoctorModal" tabindex="-1" aria-labelledby="addDoctorModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <div class="mb-3">
                        <input type="hidden" name="command" value="adddoctorcommand">
                        <input type="text" name="name" class="form-control" id="InputName" aria-describedby="nameHelp"
                               required minlength="3" maxlength="20"
                               title="Three or more letters"
                               pattern="[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}"
                               placeholder="<fmt:message key="admin_jsp.doctorWritename"/>">
                    </div>
                    <div class="mb-3">
                        <input type="test" class="form-control" id="InputSurname" name="surname" required
                               name="surname" required minlength="3"
                               maxlength="20" pattern="[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}" title="Three or more letters"
                               placeholder="<fmt:message key="admin_jsp.doctorWritesurname"/>">
                    </div>
                    <div class="mb-3">
                        <input type="login" name="login" class="form-control" id="exampleInputEmail1"
                               aria-describedby="emailHelp" required minlength="4" maxlength="20"
                               pattern="[A-Za-z]{3,20}" title="Three or more english letters"
                               placeholder="<fmt:message key="admin_jsp.doctorWritelogin"/>">
                    </div>
                    <div class="mb-3">
                        <input type="password" class="form-control" id="exampleInputPassword1" name="password" required
                               minlength="4" maxlength="18"
                               pattern="[a-zA-Z0-9]{3,20}" title="Three or more english letters and numbers"
                               placeholder="<fmt:message key="admin_jsp.doctorWritepassword"/>">
                    </div>
                    <div class="mb-3">
                        <select class="form-select" name="role"
                                id="selectrole"
                                required>
                            <option selected><fmt:message key="admin_jsp.doctorSelectrole"/></option>
                            <option value="doctor">doctor</option>
                            <option value="nurse">nurse</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <select class="form-select" name="category" id="selectCategory"
                                aria-label="Default select example">
                            <option selected><fmt:message key="admin_jsp.doctorSelectcategory"/></option>
                            <option value="onkologist">onkologist</option>
                            <option value="traumatologist">traumatologist</option>
                            <option value="ophthalmologist">ophthalmologist</option>
                            <option value="dentist">dentist</option>
                            <option value="psychiatrist">psychiatrist</option>
                            <option value="therapist">therapist</option>
                            <option value="pediatrician">pediatrician</option>
                            <option value="nurse">NURSE</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary "><fmt:message key="admin_jsp.Add"/></button>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--<!-- Modal ADD NURSE -->--%>
<%--<div class="modal fade" id="addNurseModal" tabindex="-1" aria-labelledby="addNurseModal" aria-hidden="true">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>

<%--            <div class="modal-header">--%>
<%--                <h1 class="modal-title fs-5" id="addNurseModal1">Modal title</h1>--%>
<%--                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>--%>
<%--            </div>--%>
<%--            <div class="modal-body">--%>
<%--                <form action="controller" method="post">--%>
<%--                    <div class="mb-3">--%>
<%--                        <input type="hidden" name="command" value="adddoctorcommand">--%>
<%--                        <input type="text" name="name" class="form-control" id="InputNameNurse"--%>
<%--                               aria-describedby="nameHelp"--%>
<%--                               required minlength="4" maxlength="18"--%>
<%--                               placeholder="<fmt:message key="admin_jsp.doctorWritename"/>">--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <input type="test" class="form-control" id="InputSurnameNurse" name="surname" required--%>
<%--                               minlength="3"--%>
<%--                               maxlength="18" placeholder="<fmt:message key="admin_jsp.doctorWritesurname"/>">--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <input type="login" name="login" class="form-control" id="InputEmailNurse"--%>
<%--                               aria-describedby="emailHelp" required minlength="4" maxlength="18"--%>
<%--                               placeholder="<fmt:message key="admin_jsp.doctorWritelogin"/>">--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <input type="password" class="form-control" id="InputPasswordNurse" name="password" required--%>
<%--                               minlength="4" maxlength="18"--%>
<%--                               placeholder="<fmt:message key="admin_jsp.doctorWritepassword"/>">--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <input type="hidden" class="form-control" id="InputRoleNurse" name="role" value="nurse">--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <input type="hidden" class="form-control" id="selectCategoryNurse" name="category"--%>
<%--                               value="nurse">--%>
<%--                    </div>--%>
<%--                    <button type="submit" class="btn btn-secondary btn-lg"><fmt:message key="admin_jsp.Add"/></button>--%>
<%--                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">--%>
<%--                    </div>--%>
<%--                </form>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>


<%--PAGINATION--%>
<div class="catalog-pagination">
    <nav aria-label="page-navigation">
        <ul class="pagination justify-content-center">
            <c:choose>
                <c:when test="${sort == null}">
                    <c:choose>
                        <c:when test="${page - 1 > 0}">
                            <li class="page-item">
                                <a href="controller?command=adminpagecommand&page=${page-1}"
                                   class="btn btn-primary btn-sm">⮜</a>
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
                                <a href="controller?command=adminpagecommand&page=${page+1}"
                                   class="btn btn-primary btn-sm">⮞</a>
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
                                <a href="controller?command=adminpagecommand&page=${page-1}&sort=${sort}"
                                   class="btn btn-primary btn-sm">⮜</a>
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
                                <a href="controller?command=adminpagecommand&page=${page+1}&sort=${sort}"
                                   class="btn btn-primary btn-sm">⮞</a>
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
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:useBean id="hospitalcard" scope="request" type="entitys.HospitalCard"/>
<jsp:useBean id="doctor" scope="request" type="entitys.Doctor"/>
<jsp:useBean id="patient" scope="request" type="entitys.Patient"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<c:set var="currentAddressPage"
       value="controller?command=hospitalcardbyid&hospitalCardId=${hospitalcard.hospitalCardId}"
       scope="session"></c:set>

<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body style="margin-top: 100px; margin-bottom: 170px; background-repeat: no-repeat; background-attachment: fixed; background-image: url('https://phonoteka.org/uploads/posts/2022-01/1643186349_1-phonoteka-org-p-svetlii-belii-fon-1.jpg');">
<header>
    <nav class="navbar navbar-expand-lg"
         style="position: fixed; left: 0; width: 100%; top: 0; background-color: #e3f2fd;">
        <div class="container-fluid">
            <a class="navbar-brand">Hospital</a>
            <div class="container-fluid">
                <a href="controller?command=hospitalcardlist&page=1" style="background-color: #e3f2fd;"
                   class="btn btn-outline-secondary" role="button"><fmt:message key="admin_jsp.hospitalcard<Back"/></a>
                </button>
            </div>
        </div>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="logout">
            <button type="submit" class="btn btn-outline-secondary" style="background-color: #e3f2fd;"><fmt:message
                    key="admin_jsp.logout"/></button>
        </form>
        </div>
    </nav>
</header>


<section class="adminpage-hospitalcard flex">

    <div class="container">
        <div class="row">
            <div class="col-12 fs-2 text-center "><fmt:message key="admin_jsp.hospitalcardHOSPITALCARD"/></div>
            <div class="col-4 fs-4"><fmt:message
                    key="admin_jsp.Doctor"/>:<br>${doctor.doctorName} ${doctor.doctorSurname} | ${doctor.category}</div>
            <div class="col-4 fs-4"></div>
            <div class="col-4 fs-4"><fmt:message
                    key="admin_jsp.Patient"/>:<br>${patient.patientName} ${patient.patientSurname}<br>${patient.patientDateOfBirth}<br>${patient.patientPhone}
            </div>
            <div class="col-9 fs-6 fw-bold"><fmt:message key="admin_jsp.hospitalcardPROCEDURE"/></div>
            <div class="col-9 fs-8 "><p> ${hospitalcard.procedures} </p></div>
            <div class="col-9 fs-6 fw-bold"><fmt:message key="admin_jsp.hospitalcardMEDICATION"/></div>
            <div class="col-9 fs-8"><p>${hospitalcard.medications} </p></div>
            <div class="col-9 fs-6 fw-bold"><fmt:message key="admin_jsp.hospitalcardOPERATION"/></div>
            <div class="col-9 fs-8"><p>${hospitalcard.operations} </p></div>
            <div class="col-9 fs-6 fw-bold"><fmt:message key="admin_jsp.hospitalcardDIAGNOSIS"/></div>
            <div class="col-9 fs-8"><p>${hospitalcard.diagnosis} </p></div>
        </div>
    </div>

    <!-- Modal visit -->
    <div class="modal fade" id="visitModal" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel1"><fmt:message key="admin_jsp.hospitalcardVisitHistory"/></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:forEach var="visitcard" items="${visitcard}">
                        <option value=${visitcard.appointmentId}>
                                ${visitcard.appointmentData}
                        </option>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>


</section>


<div class="container-sm">
    <!-- Button trigger modal -->
    <div class="container text-center">
        <div class="row gx-5">
            <div class="col">
                <div class="p-3 ">
                    <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal"
                            data-bs-target="#changecardModal">
                        <fmt:message key="admin_jsp.hospitalcardChangeCard"/>
                    </button>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                        <button type="button" class="btn btn-outline-dark " data-bs-toggle="modal"
                                data-bs-target="#visitModal">
                            <fmt:message key="admin_jsp.hospitalcardVisitHistory"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- Modal -->
<div class="modal fade" id="changecardModal" tabindex="-1" aria-labelledby="changecardModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel"><fmt:message
                        key="admin_jsp.hospitalcardChangeCard"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <div class="accordion" id="accordionExample">
                        <div class="accordion-item">
                            <input type="hidden" name="command" value="changehospitalcardcommand">
                            <input type="hidden" name="doctorid" value="${doctor.doctorId}">
                            <input type="hidden" name="patientid" value="${patient.patientId}">
                            <input type="hidden" name="hospitalCardId" value="${hospitalcard.hospitalCardId}">
                            <input type="hidden" name="diagnosis" value="${hospitalcard.diagnosis}">
                            <input type="hidden" name="operations" value="${hospitalcard.operations}">

                            <h2 class="accordion-header" id="headingOne">
                                <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    <fmt:message key="admin_jsp.hospitalcardPROCEDURE"/>
                                </button>
                            </h2>
                            <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne"
                                 data-bs-parent="#accordionExample">
                                <div class="accordion-body">
                                    <textarea class="form-control" name="procedures" rows="5"
                                              id="procedures">${hospitalcard.procedures}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingTwo">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    <fmt:message key="admin_jsp.hospitalcardMEDICATION"/>
                                </button>
                            </h2>
                            <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo"
                                 data-bs-parent="#accordionExample">
                                <div class="accordion-body">
                                    <textarea class="form-control" name="medications" rows="5"
                                              id="medications">${hospitalcard.medications}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-secondary btn-lg"><fmt:message
                            key="admin_jsp.hospitalcardSave"/></button>
                </form>
            </div>
        </div>
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

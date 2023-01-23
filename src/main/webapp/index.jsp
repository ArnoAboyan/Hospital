<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<c:set var="currentAddressPage" value="index.jsp" scope="session"></c:set>


<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body style=" margin-bottom: 170px; background-repeat: no-repeat; background-attachment: fixed; background-image: url('https://phonoteka.org/uploads/posts/2022-01/1643186349_1-phonoteka-org-p-svetlii-belii-fon-1.jpg');">
<header>
    <nav class="navbar navbar-expand-lg" style="background-color: #e3f2fd;">
        <div class="container-fluid">
            <a class="navbar-brand" >Hospital</a>
        </div>
    </nav>
</header>
<div class="container-fluid h-100">
    <div class="row align-items-center h-100">
        <div class="col-sm-12">
            <div class="row justify-content-center">
                <div class="col-6">
                    <form class="login-form" action="controller" method="post">
                        <div class="mb-3">
                            <input type="hidden" name="command" value="login">
                            <input type="login" name="login" class="form-control" id="exampleInputEmail1" placeholder="<fmt:message key="admin_jsp.doctorWritelogin"/>" aria-describedby="emailHelp" required minlength="4" maxlength="18">
                        </div>
                        <div class="mb-3">

                            <input type="password" class="form-control" id="exampleInputPassword1" placeholder="<fmt:message key="admin_jsp.doctorWritepassword"/>" name="password" required minlength="4" maxlength="18">
                        </div>
                        <button type="submit" class="btn btn-secondary btn-lg"><fmt:message key="admin_jsp.Login"/></button>
<%--                        <a class="btn btn-info justify-content-center" href="registration.jsp">registration</a>--%>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
<%--                            <a class="btn btn-secondary btn-sm" href="index.jsp" role="button">IndexPage____</a>--%>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
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

</body>
</html>

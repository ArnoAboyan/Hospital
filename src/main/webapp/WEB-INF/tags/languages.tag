<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="locales">
    <form action="changeLocale.jsp" method="get">
            <input type="hidden" name="command" value="changeLanguage">
            <c:forEach items="${applicationScope.locales}" var="locale">
                <c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
                    <button class="btn btn-outline-secondary " style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;" type="submit" value="${locale.key}"
                            name="locale">${locale.key}</button>
            </c:forEach>
    </form>
</div>


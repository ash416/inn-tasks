<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="person" class="part1.lesson22.entity.Person" />
    <c:set target="${person}" property="name" value="Anonim" />
    <jsp:setProperty name="person" property="birthDate" value="01.01.1900" />


<h1>Adding a new student</h1>
<form method="post" action="${pageContext.request.contextPath}/person" autocomplete="off">
    <div class="form-group">
        <label for="name">Name</label>
        <input name="name" type="text" class="form-control" id="name" value="<jsp:getProperty name="person" property="name" />">
    </div>
    <div class="form-group">
        <label for="birth">Password</label>
        <input name="birth" type="text" class="form-control" id="birth" value="<jsp:getProperty name="person" property="birthDate" />">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>


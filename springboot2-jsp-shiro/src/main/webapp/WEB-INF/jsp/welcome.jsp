<%--
  Created by IntelliJ IDEA.
  User: cheng
  Date: 2019-09-19
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="css/bootstrap-3.3.7.css"/>
</head>
<body>
${welcome}
<br/>
${fn:length(welcome)}
    <div class="panel">

    </div>

</body>
</html>

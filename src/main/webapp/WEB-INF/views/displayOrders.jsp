<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ page errorPage="error.jsp" %>  
<%@ page session = "true" %>
<!DOCTYPE html>
<html>
    <head>
        <title> Orders </title>
    </head>
    <body>
        <%
            String role = (String)session.getAttribute("role");
        %>
        <center>
            <h2> Orders </h2>
        </center>
        <table width = "100%">
            <tr>
                <td>
                    <div align = "left">
                        <button onclick="goBack()"> Go Back </button> <br>
                    </div>
                </td>
                <td>
                    <div align = "right">
                        <form action = "/DVDStore/logout" method = "Post">
                            <button type = "submit"> Logout </button>
                        </form>
                    </div>
                </td>
            </tr>
        </table>
        <div align="center">
            <table>
                <tr>
                    <th> ID&nbsp; </th>
                    <th> DVD Name&nbsp; </th>
                    <th> Language&nbsp; </th>
                    <th> Rating&nbsp; </th>
                    <th> Quantity&nbsp; </th>
                    <th> Delivery Date&nbsp; </th>
                    <th> Price&nbsp; </th>
                    <th align = "center"> Delivery Address </th>
                </tr>
                <c:forEach items="${orders}" var="order">
                    <form:form action="OrderController" method="Post">
                        <input type="hidden" name="id" value="${order.id}"/>
                        <input type="hidden" name="customerId" 
                            value="${order.customerId}"/>
                        <c:set var="dvd" value="${order.dvd}"/>
                        <c:set var="address" value="${order.address}"/>
                        <tr>
                            <td>${order.id}</td>
                            <td>${dvd.name}</td>
                            <td>${dvd.language}</td>
                            <td>${dvd.rating}</td>
                            <td>${order.quantity}</td>
                            <td>${order.deliveryDate}</td>
                            <td>${order.price}</td>
                            <td>${address.street},${address.city},
                                ${address.zipcode},${address.state}</td>
                            <td>
                                <c:if test = "${role eq 'customer'}">
                                    <button type = "submit" formaction = 
                                        "/DVDStore/OrderController/delete"> 
                                        Delete </button>
                                </c:if>
                            </td>
                        </tr>
                    </form:form>
                </c:forEach>
            </table>
        </div>
    </body>
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
    <c:if test = "${null != alertMessage}">
        <script type="text/javascript">
            alert("${alertMessage}");
        </script>
    </c:if>
</html>

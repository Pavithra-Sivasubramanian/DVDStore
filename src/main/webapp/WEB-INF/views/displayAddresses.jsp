<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ page errorPage="error.jsp" %>  
<%@ page session = "true" %>
<!DOCTYPE html>
<html>
    <head>
        <title> Addresses </title>
    </head>
    <body>
        <%
            String role = (String)session.getAttribute("role");
        %>
        <center>
            <h2> Addresses </h2>
        </center>
        <table width = "100%">
            <tr>
                <td>
                    <div align = "left">
                        <c:if test = "${role eq 'admin'}">
                            <form action ="/DVDStore/CustomerController/display"
                                method = "Post">
                                <button type = "submit"> Go Back </button> <br>
                            </form>
                        </c:if>
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
                <tr/><tr>
                    <th> Street&nbsp; </th>
                    <th> City&nbsp; </th>
                    <th> Zipcode&nbsp; </th>
                    <th> State&nbsp; </th>
                </tr>
                <c:forEach items="${addresses}" var="address">
                    <form:form action = "AddressController" method = "Post">
                        <input type="hidden" name="id" value="${address.id}"/>
                        <input type="hidden" name="customerId" 
                            value="${address.customerId}"/>
                        <tr>
                            <td>${address.street}&nbsp;</td>
                            <td>${address.city}&nbsp;</td>
                            <td>${address.zipcode}&nbsp;</td>
                            <td>${address.state}&nbsp;</td>
                            <td>
                                <c:if test = "${role eq 'customer'}">
                                    <button type = "submit" name = "action" 
                                        value = "edit"> Update </button>
                                    <button type = "submit" name = "action"
                                        value = "delete"> Delete </button>
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

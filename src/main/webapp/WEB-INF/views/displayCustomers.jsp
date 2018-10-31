<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ page errorPage="error.jsp" %>  
<!DOCTYPE html>
<html>
    <head>
        <title> Customers </title>
    </head>
    <body>
        <center>
            <h2> Customers </h2>
        </center>
        <table width = "100%">
            <tr>
                <td>
                    <div align = "left">
                        <form action = "/DVDStore/DvdController/display" 
                            method = "Post">
                            <button type = "submit"> Go Back </button> <br>
                        </form>
                    </div>
                </td>
                <td align = "right">
                    <form:form action="/DVDStore/CustomerController/search" 
                        method="Post">
                        <input type="text" name="id" 
                            placeholder="Enter Customer Id">
                        <button type="submit"> Search </button>
                    </form:form>
                </td>
                <td align = "right">
                    <form:form action="/DVDStore/CustomerController/deletedAccounts" 
                        method="Post">
                        <button type = "submit"> Deactivated Accounts </button>
                    </form:form>
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
                    <th colspan="5"> Customers</th>
                </tr>
                <tr>
                    <th> ID&nbsp; </th>
                    <th> Name&nbsp; </th>
                    <th> Contact No&nbsp; </th>
                    <th> Email&nbsp; </th>
                </tr>
                <c:forEach items="${customers}" var="customer">
                    <form:form action="CustomerController" method="Post">
                        <input type="hidden" name="id" value="${customer.id}"/>
                        <tr>
                            <td>${customer.id}</td>
                            <td>${customer.name}</td>
                            <td>${customer.contactNo}</td>
                            <td>${customer.email}</td>
                            <td>
                                <button type = "submit" formaction = "addresses"> 
                                    Addresses </button>
                            </td>
                            <td>
                                <button type = "submit" formaction = "orders"> 
                                    Orders </button>
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

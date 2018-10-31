<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ page errorPage="error.jsp" %> 
<%@ page session = "true" %> 
<!DOCTYPE html>
<html>
    <head>
        <title> Customer </title>
    </head>
    <body>
        <center>
            <h2> Customer </h2>
        </center> 
        <table width = "100%">
            <tr>
                <td>
                    <div align = "left">
                        <c:if test = "${role eq 'customer'}">
                            <form action ="/DVDStore">
                                <button type = "submit"> Go Back </button> <br>
                            </form>
                        </c:if>
                        <c:if test = "${role eq 'admin'}">
                            <form action ="/DVDStore/CustomerController/display"
                                method = "Post">
                                <button type = "submit"> Go Back </button> <br>
                            </form>
                        </c:if>
                    </div>
                </td>
                <td width = "8%">
                    <div align = "center">
                        <form:form action ="OrderController" method = "post">
                            <c:if test = "${role eq 'customer'}">
                                <td width = "12%">
                                    <button type = "submit" formaction = 
                                        "/DVDStore/OrderController/add"> 
                                        Purchase DVD</button>
                                </td>
                                <td>
                                    <button type = "submit" formaction = 
                                        "/DVDStore/OrderController/display">
                                        Display Orders</button>
                                </td> 
                            </c:if>
                        </form:form>
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
                <c:set var="customer" value="${customer}"/>
                <tr>
                    <th> ID </th>
                    <td align = "center">${customer.id}</td>
                </tr>
                <tr>
                    <th> Name </th>
                    <td align = "center">${customer.name}</td>    
                </tr>
                <tr>
                    <th> Contact No </th>
                    <td align = "center">${customer.contactNo}</td>
                </tr>
                <tr>
                    <th> Email </th>
                    <td align = "center">${customer.email}</td>
                </tr>
                <form:form action="CustomerController" method="Post">
                    <input type="hidden" name="id" value="${customer.id}"/>
                    <tr>
                        <td/>
                        <th> Addresses </th>
                        <tr>
                            <th> Street&nbsp; </th>
                            <th> City&nbsp; </th>
                            <th> Zipcode&nbsp; </th>
                            <th> State&nbsp; </th>
                        </tr>
                        <c:forEach items="${customer.addresses}" var="address">
                            <tr>
                                <td>${address.street}</td>
                                <td align = "center">${address.city}</td>
                                <td>${address.zipcode}</td>
                                <td>${address.state}</td>
                            </tr>
                        </c:forEach>
                    </tr>
                    <tr>
                        <td/><td align ="center">
                            <c:if test = "${role eq 'customer'}">
                                <button type = "submit" formaction = 
                                    "/DVDStore/CustomerController/edit"> 
                                    Update Personal Details </button>
                                <button type = "submit" formaction = 
                                    "/DVDStore/CustomerController/delete">
                                    Deactivate account</button>
                            </c:if>
                            <c:if test = "${role eq 'admin'}">
                                <button type = "submit" formaction = 
                                    "/DVDStore/OrderController/orders">
                                    Display Orders</button>
                            </c:if>
                        </td>
                    </tr>
                </form:form>
            </table>
        </div>
        <form action = "/DVDStore/AddressController/add" method = "Post">
            <center>
                <c:if test = "${role eq 'customer'}">
                    <button type = "submit"> Add new Address </button>
                </c:if>
            </center>
        </form>
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

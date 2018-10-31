<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
<%@ page import="java.util.Date" %>
 
<!DOCTYPE html>
<html>
    <head>
        <title> Order </title> 
    </head>
    <body>
        <center>
            <caption>
                <h3>
                    <c:if test = "${null != order}">
                        Edit Order
                    </c:if>
                    <c:if test = "${null == order}">
                        Purchase DVD
                    </c:if>
                </h3>
            </caption>
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
        <div align = "center">
            <form:form action = "OrderController" method="Post">
                <table>
                    <input type = "hidden" name = "id" value = "${order.id}" />
                    <tr>
                        <th> Select a DVD: </th>
                        <td>
                            <select name = "selectedDvd">
                                <c:forEach items="${dvds}" var="dvd">
                                    <option value="${dvd.id}"> 
                                        ${dvd.name}, ${dvd.language},
                                        ${dvd.rating}, ${dvd.releaseDate}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>         
                    <tr>
                        <th> Quantity: </th>
                        <td>
                            <form:input path = "quantity" 
                                value = "${order.quantity}" 
                                pattern = "^([1-9][0-9]*)$" 
                                required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <c:set var = "now" value = "<%=new Date()%>" />
                        <th> Delivery Date: </th>
                        <td> 
                            
                            <input type = "date" name = "deliveryDate"
                                min = "<fmt:formatDate pattern = 'yyyy-MM-dd' 
                                value = '${now}'/>"
                                value = "${order.deliveryDate}" 
                                required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Select a Address: </th>
                        <td>
                            <select name="selectedAddress">
                                <c:forEach items="${addresses}" var="address">
                                    <option value="${address.id}" >
                                        ${address.street}, ${address.city}, 
                                        ${address.zipcode}, ${address.state}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                </table>
                <center>
                    <button type = "submit" 
                        formaction = "/DVDStore/OrderController/insert">
                        Save </button>
                </center>
            </form:form>
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

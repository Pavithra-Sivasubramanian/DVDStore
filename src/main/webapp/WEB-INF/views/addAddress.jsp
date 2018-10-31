<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ page session = "true" %> 
<!DOCTYPE html>
<html>
    <head>
        <title> Address </title> 
    </head>
    <body>
        <center>
            <caption>
                <h3>
                    <c:if test = "${null != address}">
                        Edit Address
                    </c:if>
                    <c:if test = "${null == address}">
                        Add New Address
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
            <form:form action = "AddressController" method="Post">
                <table>
                    <input type = "hidden" name = "customerId" 
                        value = "${customerId}"/>
                    <c:if test = "${null != address}">
                        <form:hidden path = "id" 
                            value = "${address.id}" />
                    </c:if>           
                    <tr>
                        <th>Street: </th>
                            <td>
                                <form:input path = "street"
                                    pattern="^[A-Za-z0-9/, ]+$" 
                                    value = "${address.street}" 
                                    required = "required"/>
                            </td>
                    </tr>
                    <tr>
                        <th>City: </th>
                            <td>
                                <form:input path = "city"
                                    pattern="^[A-Za-z ]+$" 
                                    value = "${address.city}" 
                                    required = "required"/>
                            </td>
                    </tr>
                    <tr>
                        <th>Zipcode: </th>
                            <td>
                                <form:input path = "zipcode"
                                    pattern = "^[1-9][0-9]{5}$"
                                    value = "${address.zipcode}" 
                                    required = "required"/>
                            </td>
                    </tr>
                    <tr>
                        <th>State: </th>
                            <td>
                                <form:input path = "state"
                                    pattern="^[A-Za-z ]+$" 
                                    value = "${address.state}" 
                                    required = "required"/>
                            </td>
                    </tr>
                </table>
                <center>
                    <c:if test = "${null != address}">
                        <button type = "submit" formaction = "update">
                            Save </button>
                    </c:if>
                    <c:if test = "${null == address}"> 
                        <button type = "submit" 
                            formaction = "/DVDStore/AddressController/insert">
                            Save </button>
                    </c:if>
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

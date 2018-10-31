<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
<!DOCTYPE html>
<html>
    <head>
        <title> Customer </title> 
    </head>
    <body>
        <center>
            <caption>
                <h3>
                    <c:if test = "${null != customer}">
                        Edit Personal Details
                    </c:if>
                    <c:if test = "${null == customer}">
                        Add New Customer
                    </c:if>
                </h3>
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
            <form:form action = "CustomerController" method="Post">
                <table>
                    <c:if test = "${null != customer}">
                        <input type = "hidden" name = "id" 
                            value = "${customer.id}"/>
                    </c:if>           
                    <tr>
                        <th>Name: </th>
                            <td>
                                <input type = "text" name = "name"
                                    value = "${customer.name}" 
                                    placeholder = "Enter Name"
                                    pattern="^[A-Za-z -]+$" 
                                    required = "required"/>
                            </td>
                    </tr>
                    <tr>
                        <th>Password: </th>
                            <td>
                                <input type = "password" name = "password"
                                    value = "${customer.password}" placeholder = 
                                    "Password - Minimum 6 characters"
                                    pattern = ".{6,}" 
                                    required = "required"/>
                            </td>
                    </tr>
                    <tr>
                        <th>Contact No: </th>
                            <td>
                                <input type = "text" name = "contactNo"
                                    placeholder = "Enter Contact No"
                                    value = "${customer.contactNo}" 
                                    pattern = "[6-9][0-9]{9}" 
                                    required = "required"/>
                            </td>
                    </tr>
                    <tr>
                        <th>Email: </th>
                            <td>
                                <input type = "text" name = "email"
                                    placeholder = "Enter Email ID"
                                    value = "${customer.email}" pattern
                                    = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                                    required = "required"/>
                            </td>
                    </tr>
                    <c:if test="${null == customer}">
                        <tr>
                            <th>Address: </th>
                                <td>
                                    <form:input path = "addresses[0].street" 
                                        name = "street"
                                        placeholder = "Enter Street" 
                                        pattern="^[A-Za-z0-9/, ]+$" 
                                        required = "required"/>
                                </td>
                            </tr>
                            <tr>
                                <td/><td>
                                    <form:input path = "addresses[0].city"
                                        name = "city"
                                        placeholder = "Enter City" 
                                        pattern="^[A-Za-z ]+$" 
                                        required = "required"/>
                                </td>
                            </tr>
                            <tr>
                                <td/><td>
                                    <form:input path = "addresses[0].zipcode"
                                        name = "zipcode"
                                        placeholder = "Enter Zipcode"
                                        pattern = "^[1-9][0-9]{5}$" 
                                        required = "required"/>
                                </td>
                            </tr>
                            <tr>
                                <td/><td>
                                    <form:input path = "addresses[0].state"
                                        name = "state"
                                        placeholder = "Enter State" 
                                        pattern="^[A-Za-z ]+$" 
                                        required = "required"/>
                                </td>
                            </tr>
                        </tr>
                    </c:if>
                    <c:if test="${null != customer}">
                    <table>
                        <tr/><tr>
                            <th> Street&nbsp; </th>
                            <th> City&nbsp; </th>
                            <th> Zipcode&nbsp; </th>
                            <th> State&nbsp; </th>
                        </tr>
                        <c:forEach items="${customer.addresses}" var="address">
                            <input type = "hidden" name = "addressId" 
                                value = "${address.id}"/>
                            <tr>
                                <td>${address.street}&nbsp;</td>
                                <td>${address.city}&nbsp;</td>
                                <td>${address.zipcode}&nbsp;</td>
                                <td>${address.state}&nbsp;</td>
                                <td>
                                    <button type = "submit" formaction = 
                                        "/DVDStore/AddressController/delete">
                                        Delete </button>
                                    </td>
                                </tr>
                        </c:forEach>
                    </table>
                    </c:if>
                </table>
                <center>
                    <c:if test = "${null != customer}">
                        <button type = "submit" 
                            formaction = "/DVDStore/CustomerController/update">
                            Save </button>
                    </c:if>
                    <c:if test = "${null == customer}"> 
                        <button type = "submit" 
                            formaction = "insert"> 
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ page import = "com.ideas2it.dvdstore.util.DateUtil" %>
<%@ page errorPage="error.jsp" %>  
<!DOCTYPE html>
<html>
    <head>
        <title> DVD </title>
        
    </head>
    <body>
        <center>
            <h2> DVD </h2>
        </center>
        <table width = "100%">
            <tr>
                <td>
                    <div align = "left">
                        <form action ="/DVDStore">
                            <button type = "submit"> Go Back </button> <br>
                        </form>
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
        <center>
            <form:form action="DvdController" method="Post">
                <input type="text" name="name" placeholder="Enter DVD Name">
                <button type="submit" formmethod = "Post"
                    formaction = "/DVDStore/DvdController/search">
                    Search </button>
            </form:form>
        </center> 
        <div align="center">
            <table>
                <tr>
                    <th> ID&nbsp; </th>
                    <th> Name&nbsp; </th>
                    <th> Language&nbsp; </th>
                    <th> Rating&nbsp; </th>
                    <th> Quantity&nbsp; </th>
                    <th> Released&nbsp; </th>
                    <th> Price&nbsp; </th>
                    <th> Categories&nbsp; </th>
                </tr>
                <c:forEach items="${dvds}" var="dvd">
                    <form:form action="DvdController" method="Post">
                        <input type="hidden" name="id" value="${dvd.id}"/>
                        <tr>
                            <td align = "center">${dvd.id}</td>
                            <td >${dvd.name}</td>
                            <td>${dvd.language}</td>
                            <td>${dvd.rating}</td>
                            <td>${dvd.quantity}</td>
                            <td align = "center">
                                ${dvd.releaseDate}
                            </td>
                            <td>${dvd.price}</td>
                            <td colspan ="4">
                            <c:forEach var="category" items="${dvd.genre}">
                                ${category.name}&nbsp;
                            </c:forEach>
                            </td>
                            <td>
                                <button formaction = "/DVDStore/DvdController/edit" 
                                    type = "submit"> Update </button>
                            </td>
                            <td>
                                <button formaction = "/DVDStore/DvdController/delete" 
                                    type = "submit"> Delete </button>
                            </td>
                        </tr>
                    </form:form>
                </c:forEach>
            </table>
        </div>
        <center>
            <form:form action="DvdController" method="Post">
                <button formaction = "/DVDStore/DvdController/add"
                    type = submit> Add new DVD </button>
                <button formaction = "/DVDStore/DvdController/getDeletedDvds" 
                    type = submit> Trash </button>
            </form:form>
            <form:form action = "/DVDStore/CategoryController/display" 
                method = "Post">
                <button type = "submit"> View Categories </button>
            </form:form>
            <form:form action = "/DVDStore/CustomerController/display" 
                method = "Post">
                <button type = "submit"> View Customers </button>
            </form:form>
        </center>
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
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
                        <form action = "/DVDStore/DvdController/display" 
                            method = "Post">
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
        <div align="center">
            <table>
                <tr>
                    <th> ID&nbsp; </th>
                    <th> Name&nbsp; </th>
                    <th> Language&nbsp; </th>
                    <th> Rating&nbsp; </th>
                    <th> Quantity&nbsp; </th>
                    <th> Release Date&nbsp; </th>
                    <th> Price&nbsp; </th>
                    <th> Categories&nbsp; </th>
                </tr>
                <c:forEach items="${dvds}" var="dvd">
                    <form:form action="DvdController" method="Post">
                        <input type="hidden" name="id" value="${dvd.id}"/>
                        <tr>
                            <td>${dvd.id}</td>
                            <td>${dvd.name}</td>
                            <td>${dvd.language}</td>
                            <td>${dvd.rating}</td>
                            <td>${dvd.quantity}</td>
                            <td>${dvd.releaseDate}</td>
                            <td>${dvd.price}</td>
                            <td>
                            <c:forEach var="category" items="${dvd.genre}">
                                ${category.name}&nbsp;
                            </c:forEach>
                            </td>
                            <td>
                                <button type = "submit" formaction = "restore"> 
                                    Restore </button>
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

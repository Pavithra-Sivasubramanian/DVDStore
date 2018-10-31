<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ page errorPage="error.jsp" %>  
<!DOCTYPE html>
<html>
    <head>
        <title> Category </title>
    </head>
    <body>
        <center>
            <h2> CATEGORY </h2>
        </center>
        <table width = "100%">
            <tr>
                <td>
                    <div align = "left">
                        <form action = "/DVDStore/CategoryController/display" 
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
                    <th> ID </th>
                    <th> Name </th>
                </tr>
                <c:forEach items="${categories}" var="category">
                    <form:form action="CategoryController" method="Post">
                        <input type="hidden" name="id" value="${category.id}"/>
                        <tr>
                            <td><c:out value = "${category.id}"/></td>
                            <td><c:out value = "${category.name}"/></td>
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

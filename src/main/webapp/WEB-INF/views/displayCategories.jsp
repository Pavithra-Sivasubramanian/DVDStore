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
            <table cellpadding="10">
                <tr>
                    <th> ID </th>
                    <th> Name </th>
                </tr>
                <c:forEach items="${categories}" var="category">
                    <form:form action="CategoryController" method="Post">
                        <input type="hidden" name="id" value="${category.id}"/>
                        <tr>
                            <td>${category.id}</td>
                            <td>${category.name}</td>
                            <td>
                                <button type = "submit" formaction = "edit"> 
                                    Update </button>
                            </td>
                            <td>
                                <button type = "submit" formaction = "delete"> 
                                    Delete </button>
                            </td>
                            <td>
                                <button type = "submit" formaction = "displayDvds"> 
                                    Display DVDs </button>
                            </td>
                        </tr>
                    </form:form>
                </c:forEach>
            </table>
        </div>
        <center>
            <form:form action="CategoryController" method="Post">
                <button type = submit formaction = "add"> 
                    Add new Category</button>
                <button type = submit formaction = "getDeletedCategories"> 
                    Restore Category </button>
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
                    

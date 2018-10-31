<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
<!DOCTYPE html>
<html>
    <head>
        <title> Category </title> 
    </head>
    <body>
        <center>
            <caption>
                <h3>
                    <c:if test = "${null != category}">
                        Edit Category
                    </c:if>
                    <c:if test = "${null == category}">
                        Add New Category
                    </c:if>
                </h3>
            </caption>
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
        <div align = "center">
            <form:form action = "CategoryController" method="Post">
            <table>
                    <c:if test = "${null != category}">
                        <input type="hidden" name="id" value = "${category.id}"/>
                    </c:if>  
                    <tr>
                        <th>Name: </th>
                            <td>
                                <form:input path= "name"
                                    value = "${category.name}" 
                                    pattern = "^[A-Za-z- ]+$" 
                                    required = "required"/>
                            </td>
                    </tr>  
                </table>
                <center>
                    <c:if test = "${null != category}">
                        <button type = "submit" formaction = "update">
                            Save </button>
                    </c:if>
                    <c:if test = "${null == category}"> 
                        <button type = "submit" formaction = "insert">
                            Save </button>
                    </c:if>
                </center>
                </form:form>
        </div>  
        
        <center>
            <form:form action="CategoryController" method="Post">
                <button type = submit formaction = "display"> 
                    Display Categories </button>
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

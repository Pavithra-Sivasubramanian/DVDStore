<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt"%>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
    <head>
        <title> DVD </title> 
    </head>
    <body>
        <center>
            <caption>
                <h3>
                    <c:if test = "${null != dvd}">
                        Edit DVD
                    </c:if>
                    <c:if test = "${null == dvd}">
                        Add New DVD
                    </c:if>
                </h3>
            </caption>
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
        <div align = "center">
            <form:form action = "DVDController" method="Post">
                <table>
                    <c:if test = "${null != dvd}">
                        <form:hidden path ="id" value = "${dvd.id}"/>
                    </c:if>           
                    <tr>
                        <th> Name: </th>
                        <td>
                            <form:input path = "name"
                                value = "${dvd.name}" 
                                pattern="^[A-Za-z0-9 -]+$" 
                                required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Language: </th>
                        <td>
                            <form:input path = "language"
                                value = "${dvd.language}" 
                                pattern="[a-zA-Z][a-zA-Z]*" 
                                required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Rating: </th>
                        <td>
                            <form:input path = "rating" type = "number" 
                                value = "${dvd.rating}" step = "0.01" min = "0" 
                                max = "5" required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Quantity: </th>
                        <td>
                            <form:input path = "quantity" type = "text" 
                                value = "${dvd.quantity}" 
                                pattern = "^([1-9][0-9]*)$" 
                                required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Release Date: </th>
                        <c:set var = "now" value = "<%=new Date()%>" />
                        <td>
                            <form:input path = "releaseDate" type = "date" 
                                value = "${dvd.releaseDate}" min = "1870-01-01"
                                max = "<fmt:formatDate pattern = 'yyyy-MM-dd' 
                                value = '${now}'/>"
                                placeholder = "dd/mm/yyyy" 
                                required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Price: </th>
                        <td>
                            <form:input path = "price" type = "text" 
                                value = "${dvd.price}" step = "0.01" min = "0"
                                required = "required"/>
                        </td>
                    </tr>
                    <tr>
                        <th> Categories: </th>
                        <td>
                            <c:forEach items="${categories}" var="category">
                                <input type = "checkbox" name = "category" 
                                    value = "${category.id}"
                                    <c:forEach items="${dvd.genre}"
                                        var = "dvdCategory">     
                                        <c:if test="${category.id eq dvdCategory.id}"> 
                                            checked </c:if>
                                    </c:forEach> />
                                    ${category.name}<br>
                            </c:forEach>
                        </td>
                    </tr>
                </table>
                <center>
                    <c:if test = "${null != dvd}">
                        <button type = "submit" formaction = "update">
                            Save </button>
                    </c:if>
                    <c:if test = "${null == dvd}"> 
                        <button type = "submit" formaction = "insert">
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

        function validateCheckBoxes() {
            var categories = document.getElementsByName("category");
            for (var i=0; i<categories.length; i++) {
                if (categories[i].checked) {
                    return true;
                } 
            }
            alert("Please select atleast one checkbox");
            return false;
        }
    </script>
    <c:if test = "${null != alertMessage}">
        <script type="text/javascript">
            alert("${alertMessage}");
        </script>
    </c:if>
</html>

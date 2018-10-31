<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
    <head>
        <title> User Login Page </title>
    </head>
    <body>
        <div align = "center">
            <h1> DVD STORE </h1>
            <h3> Login Page </h3>
            <form action = "UserController" method = "Post">
                <table>
                    <tr>
                        <th> User Name </th>
                        <td>
                            <input type = "text" name = "email" 
                                placeholder = "Enter email ID" pattern =
                                "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                                required/>
                        </td>
                    </tr>
                    <tr>
                        <th> Password </th>
                        <td>
                            <input type = "password" name = "password" 
                                placeholder = "Enter password" required/>
                        </td>
                    </tr>
                    <tr>
                        <td/><td>
                            <button type = "submit" 
                                formaction = "/DVDStore/login">
                                Login </button>
                        </td>
                    </tr>
                </table>
            </form>
            <form action = "add" method = "Post"> 
                <button type = "submit"> Create new Account </button>
            </form>
        </div>
    </body>
    <c:if test = "${null != alertMessage}">
        <script type="text/javascript">
            alert("${alertMessage}");
        </script>
    </c:if>
</html>

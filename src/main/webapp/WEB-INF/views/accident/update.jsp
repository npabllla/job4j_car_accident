<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Редактирование</title>

    <script>
        function validate() {
            let elements = [$('#name'), $('#description'), $('#address'), $('#rIds')]
            for (let i = 0; i < elements.length; i++) {
                if (elements[i].val() == "") {
                    alert(elements[i].attr("placeholder"))
                    return false;
                }
            }
            return true;
        }
    </script>

    <style>
        .card {
            margin: 0 auto;
            width: 400px;
            height: 350px
        }

        .select, .input {
            width: 250px;
        }
    </style>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card">
            <div class="card-header">
                <h4>Редактирование нарушения</h4>
            </div>
            <div class="card-body">
                <form class="form" action="<c:url value='/save?id=${accident.id}'/>" method='POST'>
                    <table>
                        <tr>
                            <td>Название:</td>
                            <td><input class="input" id="name" type='text' name='name' value="${accident.name}"
                                       placeholder="Введите название"></td>
                        </tr>
                        <tr>
                            <td>Описание:</td>
                            <td><input class="input" id="description" type='text' name='description'
                                       value="${accident.description}" placeholder="Введите описание"></td>
                        </tr>
                        <tr>
                            <td>Адрес:</td>
                            <td><input class="input" id="address" type='text' name='address'
                                       value="${accident.address}" placeholder="Введите адрес"></td>
                        </tr>
                        <tr>
                            <td>Тип:</td>
                            <td>
                                <select class="select" name="type.id">
                                    <c:forEach var="type" items="${types}">
                                        <c:choose>
                                            <c:when test="${type.id == accident.type.id}">
                                                <option value="${type.id}" selected>${type.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${type.id}">${type.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                        </tr>
                        <tr>
                            <td>Статьи:</td>
                            <td>
                                <select class="select" id="rIds" name="rIds" multiple placeholder="Выберите статью/статьи">
                                    <c:forEach var="rule" items="${rules}">
                                        <c:choose>
                                            <c:when test="${accident.rules.contains(rule)}">
                                                <option value="${rule.id}" selected>${rule.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${rule.id}">${rule.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                        </tr>
                    </table>
                    <div class="card-form" style="margin-top: 10px">
                        <button name="submit" type="submit" value="Сохранить" class="btn btn-primary floated"
                                onclick="return validate()">Сохранить
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
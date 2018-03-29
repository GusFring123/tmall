<%--
  Created by IntelliJ IDEA.
  User: UTRA
  Date: 2018/3/20
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <script src="../js/jquery/2.0.0/jquery.min.js"></script>
    <script src="../js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../css/bootstrap/3.3.6/bootstrap.css">
    <link rel="stylesheet" href="../css/back/style.css">
    <script>
        /**
         * 通用判断是否为空方法
         * @param id
         * @param name
         * @returns {boolean}
         */
        function checkEmpty(id, name) {
            var value = $("#" + id).val();
            if (value.length === 0) {
                alert(name + "不能为空");
                $("#" + id).focus();
                return false;
            }
            return true;
        }

        /**
         * 判断是否是数字
         * @param id
         * @param name
         * @returns {boolean}
         */
        function checkNumber(id, name) {
            if (checkEmpty(id, name)) {
                var value = $("#" + id).val();
                if (isNaN(value)) {
                    alert(name + "不能为空");
                    $("#" + id).focus();
                    return false;
                }
            }
            return true;
        }

        /**
         * 判断是否是整数
         * @param id
         * @param name
         * @returns {boolean}
         */
        function checkInt(id, name) {
            var value = $("#" + id).val();
            if (checkEmpty(id, name)) {
                if (parseInt(value) !== value) {
                    alert(name + "必须是整数");
                    $("#" + id).focus();
                    return false;
                }
            }
            return true;
        }

        /**
         * 为每个a标签添加 删除确认
         */
        $(function () {
            $("a").click(function () {
                var deleteLink = $(this).attr("deleteLink");
                console.log(deleteLink);
                if (true === deleteLink) {
                    var confirmDelete = confirm("确认要删除？");
                    if (confirmDelete) {
                        return true;
                    }
                }
                return false;
            })
        })
    </script>
</head>
<body>

</body>
</html>

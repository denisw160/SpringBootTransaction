<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%-- Title --%>
    <title>Spring Boot Transaction Demo</title>

    <%-- Favicon --%>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/favicon.ico"/>">

    <%-- Activate live reload --%>
    <%--suppress JSUnresolvedLibraryURL --%>
    <script src="http://localhost:35729/livereload.js"></script>

    <%-- Jquery, Bootstrap and Fontawesome --%>
    <script src="<c:url value="/webjars/jquery/3.6.0/jquery.min.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap/5.0.1/js/bootstrap.bundle.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.0.1/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/webjars/font-awesome/5.15.2/css/all.min.css"/>">

    <%-- Activate source code highlighting --%>
    <link rel="stylesheet" href="<c:url value="/lib/highlight/dracula.css"/>">
    <script src="<c:url value="/lib/highlight/highlight.pack.js"/>"></script>

    <%-- Include custom javascript and style --%>
    <script src="<c:url value="/js/functions.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>

<div class="col-lg-8 mx-auto p-3 py-md-5">
    <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
        <a href="<c:url value="/"/>" class="d-flex align-items-center text-dark text-decoration-none">
            <i class="fas fa-rocket fs-2 pe-3 text-primary"></i>
            <span class="fs-4 fw-bold">Starter template</span>
        </a>
    </header>

    <main>
        <%-- Introduction --%>
        <div class="row pb-3 mb-5 border-bottom">
            <h1>Get started with Spring Boot Transactions</h1>
            <p class="fs-5">
                This demo is intended to give an overview of how (JPA) transaction behaves with the
                <code>@Transactional</code> annotation in various situations. The buttons can be used to test the
                individual
                scenarios. The result is then displayed in the table. At the beginning the application shows 10 sample
                records. These can then be reset at any time.
            </p>
            <div class="mt-1 d-flex justify-content-end">
                <button class="btn btn-danger px-2" type="button" onclick="ExecuteAction.execute('/reset')">
                    <i class="fas fa-recycle"></i> Reset samples
                </button>
                <button class="btn btn-dark px-2 ms-1" type="button" onclick="window.location.href='/h2'">
                    <i class="fas fa-database"></i> Show database
                </button>
            </div>
        </div>

        <%-- Include test scenarios --%>
        <jsp:include page="scenario.jsp"/>

        <%-- Table with the current data --%>
        <div class="row pb-3 mb-5 border-bottom">
            <a id="dataTable"><h3>Current data</h3></a>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Unique</th>
                    <th scope="col">String</th>
                    <th scope="col">Int</th>
                    <th scope="col">Date</th>
                    <th scope="col">Created at</th>
                    <th scope="col">Updated at</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${samples.size() > 0}">
                    <c:forEach var="sample" items="${samples}" varStatus="loop">
                        <tr>
                            <th scope="row">${loop.index+1}</th>
                            <td>${sample.colUnique}</td>
                            <td>${sample.colString}</td>
                            <td>${sample.colInt}</td>
                            <td>
                                <fmt:formatDate value="${sample.colDate}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${sample.createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${sample.updatedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${samples.size() == 0}">
                    <td colspan="7">No data found</td>
                </c:if>
                </tbody>
            </table>
        </div>

        <%-- Include source code --%>
        <jsp:include page="source.jsp"/>

    </main>
    <footer class="pt-3 my-3 text-muted border-top">
        Created with Spring Boot, Bootstrap and Fontawesome &middot; &copy; 2021
    </footer>
</div>

<%-- AlertScreen --%>
<div class="alert-overlay d-flex justify-content-center align-items-center">
    <div class="alert alert-warning alert-dismissible fade show" style="max-width: 75%">
        <div id="alert-msg" class="alert-msg">
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut
            labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et
            ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
        </div>
        <div class="alert-refresh">
            <button class="btn" type="button" onclick="AlertScreen.reload()">
                <i class="fas fa-sync fa-fw text-black-50"></i>
            </button>
        </div>
        <button class="btn-close" type="button" onclick="AlertScreen.hide()"></button>
    </div>
</div>

<%-- WaitScreen --%>
<div class="wait-overlay d-flex justify-content-center align-items-center">
    <div>
        <%-- Spinner animation --%>
        <div class="lds-ellipsis">
            <div></div>
            <div></div>
            <div></div>
            <div></div>
        </div>
        <%-- Wait text --%>
        <div style="color: white">Please wait...</div>
    </div>
</div>

<script>
    $(document).ready(function () {
        console.log("document ready!");
        hljs.highlightAll();
    });
</script>

</body>
</html>

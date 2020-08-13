<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mura Web Interface</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>

<div class="container" style="margin-top: 5%; margin-bottom: 5%">
<h1>Mura Web Interface</h1>
<#if message??>
<br>
<div class="alert alert-success" role="alert">${message}</div>
</#if>
<br>
<a class="btn btn-primary btn-lg" href="/analysis" role="button">Start an Analysis</a>
<hr>
<#list analyses as analysis>
    <h1>${analysis.getRepoName()}</h1>
    <h3>${analysis.getId()}</h3>
    <h3>${analysis.getGitRepo()}</h3>
    <h3>${analysis.getReport()}</h3>
    <h3>${analysis.getMutants()}</h3>
    <h3>${analysis.isSuccessful()}</h3>
    <h3>${analysis.getErrorMessage()}</h3>
</#list>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>

</body>
</html>
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
    <h1>MuRa Web Interface</h1>
    <br>
    <i>Web interface for <a href="https://github.com/ZhongXiLu/MuRa" target="_blank">MuRa</a></i>
    <br><br>
    <#if message??>
    <div class="alert alert-success" role="alert">${message}</div>
    <br>
    </#if>
    <a class="btn btn-primary btn-lg" href="/analysis" role="button">Start an Analysis</a>
    <hr>
    <#list analyses as analysis>
    <div class="card
    <#if !analysis.isFinished()>
        border-dark
    <#elseif analysis.isSuccessful()>
        border-success
    <#else>
        border-danger
    </#if>
    ">
        <h5 class="card-header">
            ${analysis.getRepoName()}
            <#if !analysis.isFinished()>
                <span class="badge badge-dark">currently analyzing</span>
            <#elseif analysis.isSuccessful()>
                <span class="badge badge-success">finished</span>
            <#else>
                <span class="badge badge-danger">failed: ${analysis.getErrorMessage()}</span>
            </#if>
        </h5>
        <div class="card-body">
            <table class="table table-borderless">
                <tr>
                    <td><b>Start time</b></td>
                    <td>${analysis.getStartTime()}</td>
                </tr>
                <tr>
                    <td><b>Git repository</b></td>
                    <td><a href="${analysis.getGitRepo()}" target="_blank">${analysis.getGitRepo()}</a></td>
                </tr>
                <#if analysis.isFinished()>
                <tr>
                    <td><b>Report link</b></td>
                    <td><a href='${"/analysis/" + analysis.getRepoName() + "/" + analysis.getReport()}'>${analysis.getReport()}</a></td>

                </tr>
                <tr>
                    <td><b>Mutants link</b></td>
                    <td><a href='${"/analysis/" + analysis.getRepoName() + "/" + analysis.getMutants()}'>${analysis.getMutants()}</a></td>
                </tr>
                </#if>
            </table>
        </div>
    </div>
    <br>
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
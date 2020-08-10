<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>

<body>

<div class="container" style="margin-top: 5%; margin-bottom: 5%"">
<h1>MuRa Web Interface</h1>

<form action="/analysis" method="post">

    <div class="form-group col-sm-10">
        <label for="gitDir">Git Repository</label>
        <input type="text" class="form-control" id="gitDir" name="gitDir">
        <small class="form-text text-muted">Link to the git repository</small>
    </div>

    <div class="accordion" id="formAccordion">
        <div class="card">
            <div class="card-header" id="headingOne">
                <h2 class="mb-0">
                    <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse"
                            data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        Project Settings
                    </button>
                </h2>
            </div>

            <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#formAccordion">
                <div class="card-body">
                    <div class="form-group col-sm-10">
                        <div class="custom-control custom-switch">
                            <input type="checkbox" class="custom-control-input" id="maven" name="maven" checked>
                            <label class="custom-control-label" for="maven">Does your project use <b>Maven</b>?</label>
                        </div>
                    </div>

                    <div id="mavenSettings" style="display: none">
                        <em>Non-Maven projects are highly experimental and not guaranteed to work.</em>
                        <br><br>

                        <div class="form-group col-sm-10">
                            <label for="classFiles">Class Files</label>
                            <input type="text" class="form-control" id="classFiles" name="classFiles" value="target/classes">
                            <small class="form-text text-muted">Path to the directory that contains all the class
                                files</small>
                        </div>

                        <div class="form-group col-sm-10">
                            <label for="classPath">Class Path</label>
                            <input type="text" class="form-control" id="classPath" name="classPath"
                                   value="target/classes/:target/test-classes/">
                            <small class="form-text text-muted">The complete class path of the project (<b>it is very
                                likely that you need to add your project's dependencies here</b>)</small>
                        </div>

                        <div class="form-group col-sm-10">
                            <label for="sourcePath">Source Path</label>
                            <input type="text" class="form-control" id="sourcePath" name="sourcePath" value="src/main/java/">
                            <small class="form-text text-muted">Path to the directory that contains all the source
                                files</small>
                        </div>

                        <div class="form-group col-sm-10">
                            <label for="testDir">Test Directory</label>
                            <input type="text" class="form-control" id="testDir" name="testDir" value="src/test/java">
                            <small class="form-text text-muted">Path to the directory that contains all the test
                                files</small>
                        </div>
                    </div>

                    <div id="mavenSettings2">
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="singleModule" name="singleModule" checked>
                                <label class="custom-control-label" for="singleModule">Is your project a <b>single</b>
                                    module project?</label>
                            </div>
                        </div>

                        <div id="moduleSettings" style="display: none">
                            <div class="form-group col-sm-10">
                                <label for="module">Module</label>
                                <input type="text" class="form-control" id="module" name="module" value="core">
                                <small class="form-text text-muted">The module to analyze (currently only one is
                                    supported)</small>
                            </div>
                        </div>
                    </div>

                    <div class="form-group col-sm-10">
                        <div class="custom-control custom-switch">
                            <input type="checkbox" class="custom-control-input" id="junit" name="junit" checked>
                            <label class="custom-control-label" for="junit">Does your project use <b>JUnit4</b> for its
                                tests?</label>
                        </div>
                    </div>

                    <div id="testSettings" style="display: none">
                        <div class="form-group col-sm-10">
                            <label for="testRunner">Test Runner</label>
                            <input type="text" class="form-control" id="testRunner" name="testRunner" value="org.junit.runner.JUnitCore">
                            <small class="form-text text-muted">The test runner class</small>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header" id="headingTwo">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse"
                                data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseOne">
                            Ranking Settings
                        </button>
                    </h2>
                </div>

                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#formAccordion">
                    <div class="card-body">
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="CBO" name="CBO" checked>
                                <label class="custom-control-label" for="CBO">CBO (coupling between objects)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="DIT" name="DIT" checked>
                                <label class="custom-control-label" for="DIT">DIT (depth of inheritance tree)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="WMC" name="WMC" checked>
                                <label class="custom-control-label" for="WMC">WMC (weighted method count)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="RFC" name="RFC" checked>
                                <label class="custom-control-label" for="RFC">RFC (response for a class)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="NOC" name="NOC" checked>
                                <label class="custom-control-label" for="NOC">NOC (number of children)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="CK" name="CK" checked>
                                <label class="custom-control-label" for="CK">CK (general class complexity, measured using the CK metric suite)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="CC" name="CC" checked>
                                <label class="custom-control-label" for="CC">CC (cyclomatic complexity)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="USG" name="USG" checked>
                                <label class="custom-control-label" for="USG">USG (usage count)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="H" name="H" checked>
                                <label class="custom-control-label" for="H">H (history)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="HC" name="HC" checked>
                                <label class="custom-control-label" for="HC">HC (history or changes count)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="HR" name="HR" checked>
                                <label class="custom-control-label" for="HR">HR (history recentness)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="LC" name="LC" checked>
                                <label class="custom-control-label" for="LC">LC (line coverage)</label>
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <div class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="IMP" name="IMP">
                                <label class="custom-control-label" for="IMP">IMP (impact) <i> -- highly recommended to turn this <b>off</b> for performance reasons (turning this on means running every test for each mutant leading to very long run times) and the impact did not have significant influence on the importance of a mutant in an earlier study</i></label>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>

        <br>

        <button type="submit" class="btn btn-primary">Start Analyzing</button>
</form>
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

<script type="text/javascript">
function toggleElementById(elementId) {
    const visible = document.getElementById(elementId).style.display;
    document.getElementById(elementId).style.display = (visible == "" ? "none" : "");
}

$("#maven").change(function() {
    toggleElementById('mavenSettings');
    toggleElementById('mavenSettings2');
});
$("#singleModule").change(function() {
    toggleElementById('moduleSettings');
});
$("#junit").change(function() {
    toggleElementById('testSettings');
});

</script>

</body>
</html>
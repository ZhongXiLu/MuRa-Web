package com.github.muraweb;

public class Analysis {

    private String gitDir;

    private boolean maven;
    private String classFiles;
    private String classPath;
    private String sourcePath;
    private String testDir;

    private boolean singleModule;
    private String module;

    private boolean junit;
    private String testRunner;

    private boolean CBO;
    private boolean DIT;
    private boolean WMC;
    private boolean RFC;
    private boolean NOC;
    private boolean CK;
    private boolean CC;
    private boolean USG;
    private boolean H;
    private boolean HC;
    private boolean HR;
    private boolean LC;
    private boolean IMP;

    public String getGitDir() {
        return gitDir;
    }

    public void setGitDir(String gitDir) {
        this.gitDir = gitDir;
    }

    public boolean isMaven() {
        return maven;
    }

    public void setMaven(boolean maven) {
        this.maven = maven;
    }

    public String getClassFiles() {
        return classFiles;
    }

    public void setClassFiles(String classFiles) {
        this.classFiles = classFiles;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getTestDir() {
        return testDir;
    }

    public void setTestDir(String testDir) {
        this.testDir = testDir;
    }

    public boolean isSingleModule() {
        return singleModule;
    }

    public void setSingleModule(boolean singleModule) {
        this.singleModule = singleModule;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean isJunit() {
        return junit;
    }

    public void setJunit(boolean junit) {
        this.junit = junit;
    }

    public String getTestRunner() {
        return testRunner;
    }

    public void setTestRunner(String testRunner) {
        this.testRunner = testRunner;
    }

    public boolean isCBO() {
        return CBO;
    }

    public void setCBO(boolean CBO) {
        this.CBO = CBO;
    }

    public boolean isDIT() {
        return DIT;
    }

    public void setDIT(boolean DIT) {
        this.DIT = DIT;
    }

    public boolean isWMC() {
        return WMC;
    }

    public void setWMC(boolean WMC) {
        this.WMC = WMC;
    }

    public boolean isRFC() {
        return RFC;
    }

    public void setRFC(boolean RFC) {
        this.RFC = RFC;
    }

    public boolean isNOC() {
        return NOC;
    }

    public void setNOC(boolean NOC) {
        this.NOC = NOC;
    }

    public boolean isCK() {
        return CK;
    }

    public void setCK(boolean CK) {
        this.CK = CK;
    }

    public boolean isCC() {
        return CC;
    }

    public void setCC(boolean CC) {
        this.CC = CC;
    }

    public boolean isUSG() {
        return USG;
    }

    public void setUSG(boolean USG) {
        this.USG = USG;
    }

    public boolean isH() {
        return H;
    }

    public void setH(boolean h) {
        H = h;
    }

    public boolean isHC() {
        return HC;
    }

    public void setHC(boolean HC) {
        this.HC = HC;
    }

    public boolean isHR() {
        return HR;
    }

    public void setHR(boolean HR) {
        this.HR = HR;
    }

    public boolean isLC() {
        return LC;
    }

    public void setLC(boolean LC) {
        this.LC = LC;
    }

    public boolean isIMP() {
        return IMP;
    }

    public void setIMP(boolean IMP) {
        this.IMP = IMP;
    }
}

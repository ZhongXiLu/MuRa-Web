package com.github.muraweb;

import java.util.Objects;

public class AnalysisForm {

    private String gitRepo;

    private boolean singleModule;
    private String module;

    private boolean CK;
    private boolean CC;
    private boolean USG;
    private boolean H;
    private boolean LC;
    private boolean IMP;

    public String getGitRepo() {
        return gitRepo;
    }

    public void setGitRepo(String gitRepo) {
        this.gitRepo = gitRepo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalysisForm analysis = (AnalysisForm) o;
        return gitRepo.equals(analysis.gitRepo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gitRepo);
    }
}

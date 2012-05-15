package org.jenkinsci.plugins.dataprocessing;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

public class RandomDataFile implements Serializable {

    private final int nNumbers;
    private final String fileName;

    @DataBoundConstructor
    public RandomDataFile(int nNumbers, String fileName) {
        this.nNumbers = nNumbers;
        this.fileName = fileName;
    }
    
    public int getnNumbers() {
        return nNumbers;
    }

    public String getFileName() {
        return fileName;
    }
}

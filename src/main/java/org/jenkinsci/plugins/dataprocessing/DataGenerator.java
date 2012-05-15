package org.jenkinsci.plugins.dataprocessing;

import hudson.Extension;
import hudson.Launcher;
import hudson.FilePath.FileCallable;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

public class DataGenerator extends Builder implements Serializable {

    private final List<RandomDataFile> files;

    @DataBoundConstructor
    public DataGenerator(List<RandomDataFile> files) {
        this.files = files;
    }

    public List<RandomDataFile> getFiles() {
        return files;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, final BuildListener listener)
            throws InterruptedException, IOException {
        //execute command on the node where workspace is located
        build.getWorkspace().act(new GenerateCommand(files));
        return true;
    }

  
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public String getDisplayName() {
            return "Generate random data";
        }

        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

    }

}

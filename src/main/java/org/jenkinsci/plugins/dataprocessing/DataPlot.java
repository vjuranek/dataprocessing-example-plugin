package org.jenkinsci.plugins.dataprocessing;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Util;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.File;
import java.io.IOException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class DataPlot extends Recorder {
    
    private final String artifacts;
    private final PlotOptions plotOpts;
    
    @DataBoundConstructor
    public DataPlot(String artifacts, PlotOptions plotOpts){
        System.out.println("Plot opts: " + plotOpts);
        this.artifacts = artifacts;
        this.plotOpts = plotOpts;
    }
    
    public String getArtifacts() {
        return artifacts;
    }

    public PlotOptions getPlotOpts(){
        return plotOpts;
    }

    @Override
    public boolean perform(AbstractBuild<?,?> build, Launcher launcher, BuildListener listener) throws InterruptedException {
        try {
            FilePath ws = build.getWorkspace();
            if (ws==null) { 
                listener.getLogger().println("[Data-processing] Now workspace, no data, bye:-)");
                return true;
            }
           
            String artifacts = build.getEnvironment(listener).expand(this.artifacts);
            File dir = new File(build.getRootDir(), getDescriptor().getStoreDir());
            if(ws.copyRecursiveTo(artifacts,"",new FilePath(dir)) != 0) {
                // add action which actually show the data plot. If build has action, new link appears in left-hand side menu
                // add this action in if we copied any data
                build.addAction(new PlotAction(build.getRootDir(), plotOpts));
            } else{
                listener.error("[Data-processing] No data copied, configuration error?");
            }
        } catch (IOException e) {
            e.printStackTrace(listener.error("[Data-processing] Failed to record data"));
        }
        return true;
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }
    
    @Override
    public DescriptorImpl getDescriptor(){
        return (DescriptorImpl)super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher>{
        
        public static final String DEFAULT_DIR = "plot-data";  // dir where we store the data by default
        private String storeDir;
        
        public DescriptorImpl() {
            super(DataPlot.class);
            load();
        }
        
        @Override
        public boolean configure( StaplerRequest req, JSONObject json ) throws FormException {
            String p = json.getString("storeDir");
            p = Util.fixEmptyAndTrim(p);
            storeDir = (p == null) ? DEFAULT_DIR : p;
            save();
            return super.configure(req, json);
        }
        
        public String getStoreDir(){
            if(storeDir == null)
                return DEFAULT_DIR;
            return storeDir;
        }
        
        public String getDisplayName(){
            return "Highcharts plot";
        }
        
        public boolean isApplicable(Class<? extends AbstractProject> jobType){
            return true;
        }
    }
}

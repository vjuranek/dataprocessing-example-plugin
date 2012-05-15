package org.jenkinsci.plugins.dataprocessing;

import org.kohsuke.stapler.DataBoundConstructor;

public class PlotOptions {

    private final String title;
    private final String description;
    private final String xAxisTitle;
    private final String yAxisTitle;
    
    @DataBoundConstructor
    public PlotOptions(String title, String description, String xAxisTitle, String yAxisTitle){
        System.out.println("Plot pots konstruktor volan");
        this.title = title;
        this.description = description;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getxAxisTitle() {
        return xAxisTitle;
    }

    public String getyAxisTitle() {
        return yAxisTitle;
    }
}

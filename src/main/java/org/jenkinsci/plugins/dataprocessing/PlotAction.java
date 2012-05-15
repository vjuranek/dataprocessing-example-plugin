package org.jenkinsci.plugins.dataprocessing;

import hudson.Util;
import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.Hudson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import org.jenkinsci.plugins.dataprocessing.DataPlot.DescriptorImpl;

public class PlotAction implements Action {
    
    private final PlotOptions plotOpts;
    private final File dataRoot;
    private WeakReference<ArrayList<DataSerie>> dataSeriesRef; // there could be large amount of data, allow GC to remove it when needed!!!
    
    public PlotAction(File dataRoot, PlotOptions plotOpts){
        this.dataRoot = dataRoot;
        this.plotOpts = plotOpts;
    }
    
    public JSONObject getPlotOptsJson(){
        // generate plot option data in JSON format. This object is consumed by plot library (HighCharts in out case)
        return JSONObject.fromObject(plotOpts);
    }
    
    public JSONArray getSeriesJson(){
        // generate actual data in JSON format. This object is consumed by plot library (HighCharts in out case)
        return JSONArray.fromObject(getDataSeries());
    }
    
    public ArrayList<DataSerie> getDataSeries(){
        if(dataSeriesRef != null){
            ArrayList<DataSerie> ds = dataSeriesRef.get();
            if(ds != null)
                return ds;
        }
        ArrayList<DataSerie> ds = loadDataSeries();
        dataSeriesRef = new WeakReference<ArrayList<DataSerie>>(ds);
        return ds;
    }
    
    private ArrayList<DataSerie> loadDataSeries(){
        ArrayList<DataSerie> ds = new ArrayList<DataSerie>();
        DescriptorImpl d = (DescriptorImpl)Hudson.getInstance().getDescriptor(DataPlot.class);
        FileSet fs = Util.createFileSet(new File(dataRoot, d.getStoreDir()), "**/*");
        Iterator<FileResource> it = fs.iterator();
        while(it.hasNext()){
            FileResource f= (FileResource)it.next();
            if(f.getFile().isFile()){
                ds.add(createDataSerieFromFile(f));
            }
        }
        return ds;
    }
    
    private DataSerie createDataSerieFromFile(FileResource f){
        ArrayList<Double> al = new ArrayList<Double>();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(f.getInputStream()));
            String st;
            while( (st = br.readLine()) != null){
                al.add(Double.valueOf(st));
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return new DataSerie(f.getName(), al);
    }
    
    public String getIconFileName(){
        return "/plugin/data-processing-plugin/img/x-office-presentation.png";
    }
    
    public String getDisplayName(){
        return "Data processing plugin";
    }
    
    public String getUrlName(){
        return "data-processing-plugin";
    }

}
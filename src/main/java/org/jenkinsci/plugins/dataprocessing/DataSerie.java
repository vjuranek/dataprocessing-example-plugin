package org.jenkinsci.plugins.dataprocessing;

import java.util.ArrayList;

public class DataSerie {

    private final String name;
    private final ArrayList<Double> data;
    
    public DataSerie(String name, ArrayList<Double> data){
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getData() {
        return data;
    }
    
    
    
}

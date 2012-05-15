package org.jenkinsci.plugins.dataprocessing;

import hudson.FilePath.FileCallable;
import hudson.remoting.VirtualChannel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GenerateCommand implements FileCallable<Boolean> {

    private static final int MAX_RAND_NUM = 100;
    private static final String EOL = "\n";
    
    private final List<RandomDataFile> files;

    public GenerateCommand(List<RandomDataFile> files) {
        this.files = files;
    }

    public Boolean invoke(File f, VirtualChannel channel) {
        for (RandomDataFile file : files) {
            try {
                FileWriter fw = new FileWriter(new File(f, file.getFileName()));
                generate(file.getnNumbers(), fw);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } 
        }
        return true;
    }

    private void generate(int nNumbers, FileWriter fw) throws IOException {
        Random rg = new Random();
        for (int i = 0; i < nNumbers; i++)
            fw.write(rg.nextInt(MAX_RAND_NUM) + EOL);
    }

}

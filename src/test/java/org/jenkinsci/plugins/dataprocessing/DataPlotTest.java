package org.jenkinsci.plugins.dataprocessing;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.dataprocessing.DataPlot.DescriptorImpl;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class DataPlotTest extends HudsonTestCase {
    
    private final String STORE_DIR = "/path/to/store/dir"; 
    
    @Test
    public void testStoreDir() throws ElementNotFoundException, Exception {
        HtmlPage page = createWebClient().goTo("configure"); 
        HtmlTextInput storeDirField = (HtmlTextInput)page.getElementByName("storeDir");
        storeDirField.type(STORE_DIR);
        submit(page.getFormByName("config"));
        assertEquals(STORE_DIR, ((DescriptorImpl)Jenkins.getInstance().getDescriptor(DataPlot.class)).getStoreDir());
    }
}

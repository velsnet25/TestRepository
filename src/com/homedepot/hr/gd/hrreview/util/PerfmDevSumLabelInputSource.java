package com.homedepot.hr.gd.hrreview.util;

/*
 * THIS CLASS HAS BEEN CREATED AS PART OF "WAS to Tomcat Migration Project
 * 
 * Class Name  : PerfmDevSumLabelInputSource.java
 * Application : ConvertToPDF.java
 * 
 */

import org.xml.sax.InputSource;

public class PerfmDevSumLabelInputSource extends InputSource {

    private PerfmDevSumLabel perfmDevSum;

    /**
     * Constructor for the PerfmDevSumLabelInputSource
     * @param perfmDevSum The PerfmDevSumLabel object to use
     */
    public PerfmDevSumLabelInputSource(PerfmDevSumLabel perfmDevSum) {
        this.perfmDevSum = perfmDevSum;
    }

    /**
     * Returns the perfmDevSum.
     * @return PerfmDevSumLabel
     */
    public PerfmDevSumLabel getPerfmDevSumLabel() {
        return perfmDevSum;
    }

    /**
     * Sets the perfmDevSum.
     * @param perfmDevSum The perfmDevSum to set
     */
    public void perfmDevSumLabel(PerfmDevSumLabel perfmDevSum) {
        this.perfmDevSum = perfmDevSum;
    }

}

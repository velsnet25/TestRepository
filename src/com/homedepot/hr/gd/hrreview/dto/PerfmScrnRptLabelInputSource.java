package com.homedepot.hr.gd.hrreview.dto;

/*
 * THIS CLASS HAS BEEN CREATED AS PART OF "WAS to Tomcat Migration Project
 * 
 * Class Name  : PPerfmScrnRptLabelInputSource.java
 * Application : ConvertToPDF.java
 * 
 */

import org.xml.sax.InputSource;

public class PerfmScrnRptLabelInputSource extends InputSource {

    private PerfmScrnHeadLabel perfmHead;

    /**
     * Constructor for the ProjectTeamInputSource
     * @param perfmScrnFootLabel The ProjectTeam object to use
     */
    public PerfmScrnRptLabelInputSource(PerfmScrnHeadLabel perfmScrnHeadLabel) {
        this.perfmHead = perfmScrnHeadLabel;
    }

    /**
     * Returns the projectTeam.
     * @return ProjectTeam
     */
    public PerfmScrnHeadLabel getPerfmScrnHeadLabel() {
        return perfmHead;
    }

    /**
     * Sets the projectTeam.
     * @param projectTeam The projectTeam to set
     */
    public void perfmScrnHeadLabel(PerfmScrnHeadLabel perfmScrnHeadLabel) {
        this.perfmHead = perfmScrnHeadLabel;
    }

}

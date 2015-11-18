<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:output method="xml"/>   
<xsl:strip-space elements="*"/>

<!-- gdm493a report style sheet -->
<xsl:attribute-set name="title-1">
    <xsl:attribute name="background-color">cyan</xsl:attribute>
    <xsl:attribute name="text-align">center</xsl:attribute>
    <xsl:attribute name="font-size">10pt</xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
    <xsl:attribute name="text-align">center</xsl:attribute>
    <xsl:attribute name="border">solid 0.5mm black</xsl:attribute>
    <xsl:attribute name="display-align">center</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="title-2">
    <xsl:attribute name="font-size">8pt</xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
    <xsl:attribute name="text-align">left</xsl:attribute>
    <!--xsl:attribute name="display-align">center</xsl:attribute-->
    <xsl:attribute name="height">4mm</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="tall-row">
    <xsl:attribute name="height">5mm</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="table-1">
    <xsl:attribute name="width">100%</xsl:attribute>
    <xsl:attribute name="table-layout">fixed</xsl:attribute>
    <xsl:attribute name="border-collapse">collapse</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="big-cell">
    <xsl:attribute name="border">solid 0.5mm black</xsl:attribute>
</xsl:attribute-set>

<xsl:attribute-set name="pad">
    <xsl:attribute name="text-indent">1mm</xsl:attribute>
</xsl:attribute-set>

<xsl:template match="/">
  <fo:root>
    <fo:layout-master-set>
      <fo:simple-page-master
                master-name="normal-page"
                page-height="8.5in" page-width="11in"
                margin-top="15mm" margin-bottom="5mm"
                margin-left="0.2in" margin-right="0.2in">
          <fo:region-before extent="5mm"/>
          <fo:region-body margin-top="5mm" margin-bottom="3mm"/>
          <fo:region-after extent="3mm"/>
      </fo:simple-page-master>
    </fo:layout-master-set> 
    
    <xsl:apply-templates/>
        
  </fo:root>
</xsl:template>

<!-- main document layout -->
<xsl:template match="associate">
  <fo:page-sequence 
            master-reference="normal-page">
    <!-- header ***************************************************** -->
    <fo:static-content 
            flow-name="xsl-region-before" 
            font-size="14pt" font-family="Helvetica" font-weight="bold"
            line-height="10pt"
            text-align="center">
      <fo:block>
        ASSOCIATE PROFILE
      </fo:block>
    </fo:static-content>
    
    <!-- footer ******************************************************* -->
    <fo:static-content 
            flow-name="xsl-region-after" 
            font-size="6pt" font-family="Helvetica" 
            line-height="8pt"
            text-align="end">
      <fo:table table-layout="fixed" border-collapse="collapse" width="100%">
          <fo:table-column column-number="1" column-width="proportional-column-width(1)"/>
          <fo:table-column column-number="2" column-width="proportional-column-width(7)"/>
          <fo:table-column column-number="3" column-width="proportional-column-width(1)"/>
        <fo:table-body><fo:table-row>
          <fo:table-cell><fo:block text-align="left">HR-CD-01</fo:block></fo:table-cell>
          <fo:table-cell><fo:block text-align="center">THIS IS A PRIVILEGED REPORT THAT CONTAINS PROPRIETARY INFORMATION
          OF THE HOME DEPOT. UNAUTHORIZED USE/DISCLOSURE IS PROHIBITED</fo:block></fo:table-cell>
          <fo:table-cell><fo:block text-align="right">PRINTED ON <xsl:value-of select="@printed-on"/></fo:block></fo:table-cell>
        </fo:table-row></fo:table-body>
      </fo:table>
    </fo:static-content>

    <!-- body flow ***************************************************** -->
    <fo:flow flow-name="xsl-region-body"
             font-family="Helvetica"
             line-height="10pt"
             font-size="8pt">
      <xsl:choose>
      <xsl:when test="empty">
          <fo:block text-align="center" font-size="14pt" space-before="20mm">
              No Report
          </fo:block>
      </xsl:when>
      <xsl:when test="too-big">
          <fo:block text-align="center" 
                    font-size="16pt" font-weight="bold"
                    padding-before="5mm" space-before="5mm"
                    padding-after="5mm" space-after="5mm"
                    border="solid 2mm black">
              This report has exceded the maximum of 
              <xsl:value-of select="too-big/@max-size"/> pages. 
              Please refine your search to print the rest.
          </fo:block>
      </xsl:when>
      <xsl:otherwise>
          <xsl:call-template name="main-table"/>
      </xsl:otherwise>
      </xsl:choose>
    </fo:flow>
  </fo:page-sequence>
</xsl:template>

<!-- main table************************************************** -->
<xsl:template name="main-table">
  <fo:table xsl:use-attribute-sets="table-1">
          <fo:table-column column-number="1" column-width="proportional-column-width(10)"/>
          <fo:table-column column-number="2" column-width="proportional-column-width(12)"/>
        <fo:table-body>
            <fo:table-row xsl:use-attribute-sets="tall-row">
                <fo:table-cell xsl:use-attribute-sets="title-1"><fo:block>PERSONAL</fo:block></fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="title-1"><fo:block>EDUCATION</fo:block></fo:table-cell>
            </fo:table-row>
            <!-- row 1 -->
            <fo:table-row height="32mm">
                <!-- row 1/2 col 1 * personal -->
                <fo:table-cell number-rows-spanned="3" xsl:use-attribute-sets="big-cell"><xsl:apply-templates select="personel"/></fo:table-cell>
                <!-- row 1 col 2 * education -->
                <fo:table-cell xsl:use-attribute-sets="big-cell"><xsl:apply-templates select="education"/></fo:table-cell>
            </fo:table-row>
            
            <fo:table-row xsl:use-attribute-sets="tall-row">
                <fo:table-cell xsl:use-attribute-sets="title-1"><fo:block>KEY COURSES/CERTIFICATIONS/AWARDS</fo:block></fo:table-cell>
            </fo:table-row>
            <fo:table-row height="28mm">
                <!-- row 2 col 2 * certs -->
                <fo:table-cell xsl:use-attribute-sets="big-cell"><xsl:apply-templates select="key"/></fo:table-cell>
            </fo:table-row>

            <fo:table-row xsl:use-attribute-sets="tall-row">
                <fo:table-cell xsl:use-attribute-sets="title-1"><fo:block>EXTERNAL WORK HISTORY</fo:block></fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="title-1"><fo:block>CAREER PLANNING</fo:block></fo:table-cell>
            </fo:table-row>
            <fo:table-row height="52mm">
                <!-- row 3 col 1 * ext work -->
                <fo:table-cell xsl:use-attribute-sets="big-cell"><xsl:apply-templates select="ext-work"/></fo:table-cell>
                <!-- row 3 col 2 * career planing -->
                <fo:table-cell xsl:use-attribute-sets="big-cell"><xsl:apply-templates select="planning"/></fo:table-cell>
            </fo:table-row>

            <fo:table-row xsl:use-attribute-sets="tall-row">
                <fo:table-cell xsl:use-attribute-sets="title-1"><fo:block>HD WORK HISTORY</fo:block></fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="title-1"><fo:block>MOBILITY/LANGUAGES</fo:block></fo:table-cell>
            </fo:table-row>
            <fo:table-row height="52mm">
                <!-- row 4 col 1 * hd work -->
                <fo:table-cell xsl:use-attribute-sets="big-cell"><xsl:apply-templates select="hd-work"/></fo:table-cell>
                <!-- row 4 col 2 * mobility / lang -->
                <fo:table-cell xsl:use-attribute-sets="big-cell"><xsl:apply-templates select="mobility-lang"/></fo:table-cell>
            </fo:table-row>
        </fo:table-body>
   </fo:table>
</xsl:template>

<!-- personal ***************************************************** -->
<xsl:template match="personel">
<fo:table xsl:use-attribute-sets="table-1">
        <fo:table-column column-number="1" column-width="proportional-column-width(43)"/>
        <fo:table-column column-number="2" column-width="proportional-column-width(34)"/>
        <fo:table-column column-number="3" column-width="proportional-column-width(45)"/>
    <fo:table-body>
        <fo:table-row>
            <fo:table-cell number-rows-spanned="12" display-align="center">
              <xsl:choose>
              <xsl:when test="count(image) = 1">
                    <fo:block height="57mm">
                        <fo:external-graphic src="{image}" scaling="non-uniform" overflow="hidden" text-align="start" />
                    </fo:block>
              </xsl:when>
              <xsl:otherwise>
                <fo:block text-align="center" border="solid 0.1mm" 
                        padding-before="23mm" padding-after="23mm">
                    Associate's picture not found.
                </fo:block>
              </xsl:otherwise>
              </xsl:choose>
            </fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">NAME</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="name"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">TITLE</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="title"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">DIVISION</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="division"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">REGION</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="region"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">LOCATION</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="location"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">DEPARTMENT</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="department"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">ORIGINAL HIRE DATE</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="original-hire-dt"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">DATE CURR POS./LOC.</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="dt-curr-pos-loc"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">DATE CURR TTL</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="dt-curr-ttl"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">STORE NO.</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="store-num"/></fo:block></fo:table-cell>
        </fo:table-row>
        <fo:table-row xsl:use-attribute-sets="tall-row">
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">CITIZENSHIP</fo:block></fo:table-cell>
            <fo:table-cell><fo:block><xsl:value-of select="citizenship"/>
            </fo:block></fo:table-cell>
        </fo:table-row>
    </fo:table-body>
</fo:table>
</xsl:template>

<!-- education ***************************************************** -->
<xsl:template match="education">
<fo:table xsl:use-attribute-sets="table-1"> 
        <fo:table-column column-number="1" column-width="proportional-column-width(47)"/>
        <fo:table-column column-number="2" column-width="proportional-column-width(7)"/>
        <fo:table-column column-number="3" column-width="proportional-column-width(19)"/>
        <fo:table-column column-number="4" column-width="proportional-column-width(52)"/>
        <fo:table-column column-number="5" column-width="proportional-column-width(9)"/>
    <fo:table-body>
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">SCHOOL</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>LOC</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>DEGREE</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>MAJOR</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>GRAD</fo:block></fo:table-cell>
        </fo:table-row>
        <xsl:apply-templates select="ed-line"><xsl:with-param name="spans" select="5"/></xsl:apply-templates>
        <xsl:if test="not(node())" >
         <fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="5">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
 
</xsl:if>
        
    </fo:table-body>
</fo:table>
</xsl:template>

<xsl:template match="ed-line">
<fo:table-row>
    <fo:table-cell><fo:block xsl:use-attribute-sets="pad"><xsl:value-of select="school"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="location"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="degree"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="major"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block text-align="center"><xsl:value-of select="graduated"/></fo:block></fo:table-cell>
</fo:table-row>
</xsl:template>

<!-- key courses/certifications/awards********************************** -->
<xsl:template match="key">
<fo:table xsl:use-attribute-sets="table-1"> 
        <fo:table-column column-number="1" column-width="proportional-column-width(57)"/>
        <fo:table-column column-number="2" column-width="proportional-column-width(48)"/>
        <fo:table-column column-number="3" column-width="proportional-column-width(30)"/>
    <fo:table-body>
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">DESCRIPTION</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>ORGANIZATION</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>COMPLETION DATE</fo:block></fo:table-cell>
        </fo:table-row>
        <xsl:apply-templates select="key-line"><xsl:with-param name="spans" select="5"/></xsl:apply-templates>
         <xsl:if test="not(node())" >
         <fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="5">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
</xsl:if>
    </fo:table-body>
</fo:table>
</xsl:template>

<xsl:template match="key-line">
<fo:table-row>
    <fo:table-cell><fo:block xsl:use-attribute-sets="pad"><xsl:value-of select="substring(description,1,30)"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="substring(organization,1,28)"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block text-align="center"><xsl:value-of select="substring(complete,1,4)"/></fo:block></fo:table-cell>
</fo:table-row>
</xsl:template>

<!-- work ***************************************************** -->
<xsl:template match="ext-work">
<fo:table xsl:use-attribute-sets="table-1">
        <fo:table-column column-number="1" column-width="proportional-column-width(55)"/>
        <fo:table-column column-number="2" column-width="proportional-column-width(35)"/>
        <fo:table-column column-number="3" column-width="proportional-column-width(16)"/>
        <fo:table-column column-number="4" column-width="proportional-column-width(16)"/>
    <fo:table-body>
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">POSITION</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>COMPANY</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>FROM</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>TO</fo:block></fo:table-cell>
        </fo:table-row>
        <xsl:apply-templates select="work-line"><xsl:with-param name="spans" select="5"/></xsl:apply-templates>
         <xsl:if test="not(node())" >
         <fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="5">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
</xsl:if>
    </fo:table-body>
</fo:table>
</xsl:template>

<xsl:template match="hd-work">
<fo:table xsl:use-attribute-sets="table-1"> 
        <fo:table-column column-number="1" column-width="proportional-column-width(55)"/>
        <fo:table-column column-number="2" column-width="proportional-column-width(35)"/>
        <fo:table-column column-number="3" column-width="proportional-column-width(16)"/>
        <fo:table-column column-number="4" column-width="proportional-column-width(16)"/>
    <fo:table-body>
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">POSITION</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>DIVISION</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>FROM</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>TO</fo:block></fo:table-cell>
        </fo:table-row>
        <xsl:apply-templates select="work-line"><xsl:with-param name="spans" select="5"/></xsl:apply-templates>
         <xsl:if test="not(node())" >
         <fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="5">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
</xsl:if>
    </fo:table-body>
</fo:table>
</xsl:template>

<xsl:template match="work-line">
<fo:table-row>
    <fo:table-cell><fo:block xsl:use-attribute-sets="pad"><xsl:value-of select="substring(position,1,25)"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="substring(for,1,18)"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="from"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="to"/></fo:block></fo:table-cell>
</fo:table-row>
</xsl:template>

<!-- career planning ***************************************************** -->
<xsl:template match="planning">
<fo:table xsl:use-attribute-sets="table-1"> 
        <fo:table-column column-number="1" column-width="proportional-column-width(45)"/>
        <fo:table-column column-number="2" column-width="proportional-column-width(47)"/>
        <fo:table-column column-number="3" column-width="proportional-column-width(37)"/>
    <fo:table-body>
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="title-2" number-columns-spanned="3">
                <fo:block xsl:use-attribute-sets="pad">INDIVIDUAL INTERESTS/POTENTIAL TIMING:</fo:block>
            </fo:table-cell>
        </fo:table-row>
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">FUNCTION/BUSINESS</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>TITLE</fo:block></fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>POTENTIAL TIMING</fo:block></fo:table-cell>
        </fo:table-row>
        <xsl:apply-templates select="plan-line"><xsl:with-param name="spans" select="5"/></xsl:apply-templates>
         <xsl:if test="not(node())" >
         <fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="5">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
</xsl:if>
    </fo:table-body>
</fo:table>
</xsl:template>

<xsl:template match="plan-line">
<fo:table-row>
    <fo:table-cell><fo:block xsl:use-attribute-sets="pad"><xsl:value-of select="function"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="title"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="timing"/></fo:block></fo:table-cell>
</fo:table-row>
</xsl:template>

<!-- mobility/language ***************************************************** -->
<xsl:template match="mobility-lang">
<fo:table xsl:use-attribute-sets="table-1"> 
        <fo:table-column column-number="1" column-width="proportional-column-width(1)"/>
        <fo:table-column column-number="2" column-width="proportional-column-width(1)"/>
        <fo:table-column column-number="3" column-width="proportional-column-width(1)"/>
        <fo:table-column column-number="4" column-width="proportional-column-width(1)"/>        
    <fo:table-body>
        <xsl:apply-templates select="mobility"/>
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">DEPENDS ON:</fo:block></fo:table-cell>
            <fo:table-cell number-columns-spanned="3">
                <fo:block overflow="hidden"  height="4mm"><xsl:value-of select="depends"/></fo:block>
            </fo:table-cell>
        </fo:table-row>
        <xsl:apply-templates select="language"/>
    </fo:table-body>
</fo:table>
</xsl:template>

<!-- mobility ***************************************************** -->
<xsl:template match="mobility">
<fo:table-row>
    <fo:table-cell xsl:use-attribute-sets="title-2" number-columns-spanned="4">
        <fo:block xsl:use-attribute-sets="pad">MOBILITY:</fo:block>
    </fo:table-cell>
</fo:table-row>
<fo:table-row>
    <fo:table-cell xsl:use-attribute-sets="title-2" number-columns-spanned="2">
        <fo:block xsl:use-attribute-sets="pad">SHORT TERM:</fo:block>
    </fo:table-cell>
    <fo:table-cell xsl:use-attribute-sets="title-2" number-columns-spanned="2">
        <fo:block>LONG TERM:</fo:block>
    </fo:table-cell>
</fo:table-row>
<xsl:if test="not(node())" >
         <fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="4">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
</xsl:if>
</xsl:template>

<!-- language ***************************************************** -->
<xsl:template match="language">
<fo:table-row>
    <fo:table-cell xsl:use-attribute-sets="title-2" number-columns-spanned="4">
        <fo:block xsl:use-attribute-sets="pad">LANGUAGES:</fo:block>
    </fo:table-cell>
</fo:table-row>
<fo:table-row>
    <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block xsl:use-attribute-sets="pad">LANGUAGE:</fo:block></fo:table-cell>
    <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>READ:</fo:block></fo:table-cell>
    <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>WRITE:</fo:block></fo:table-cell>
    <fo:table-cell xsl:use-attribute-sets="title-2"><fo:block>SPEAK:</fo:block></fo:table-cell>
</fo:table-row>
<xsl:apply-templates select="language-line"><xsl:with-param name="spans" select="4"/></xsl:apply-templates>
<xsl:if test="not(node())" >
         <fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="4">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
</xsl:if>


 
</xsl:template>

<xsl:template match="language-line">
<fo:table-row>
    <fo:table-cell><fo:block xsl:use-attribute-sets="pad" font-weight="bold">
        <xsl:value-of select="translate(substring(language,1,17),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
    </fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="read"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="write"/></fo:block></fo:table-cell>
    <fo:table-cell><fo:block><xsl:value-of select="speak"/></fo:block></fo:table-cell>
</fo:table-row>
</xsl:template>

<xsl:template match="empty">
<xsl:param name="spans"/>
<fo:table-row height="15mm">
    <fo:table-cell display-align="center" number-columns-spanned="{$spans}">
        <fo:block text-align="center" font-weight="bold">*** NO DATA FOR THIS SELECTION ***</fo:block>
    </fo:table-cell>
</fo:table-row>
</xsl:template>

<xsl:template match="/empty">
<fo:flow flow-name="xsl-region-body"
             font-family="Helvetica"
             line-height="10pt"
             space-before="1mm"
             font-size="8pt">
  <fo:page-sequence master-reference="normal-page">
    <fo:block text-align="center" font-size="18pt" font-weight="bold">
        *** NO REPORT ***
    </fo:block>
    </fo:page-sequence>
</fo:flow>
</xsl:template>

</xsl:stylesheet>